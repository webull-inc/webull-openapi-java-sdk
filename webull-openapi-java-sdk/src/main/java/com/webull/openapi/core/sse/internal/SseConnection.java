/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.core.sse.internal;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.exception.ErrorCode;
import com.webull.openapi.core.http.common.HttpStatus;
import com.webull.openapi.core.http.exception.HttpServerException;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.retry.RetriedFailedException;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.Retryable;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.core.sse.internal.exception.SseCancelledException;
import com.webull.openapi.core.sse.internal.exception.SseStreamClosedException;
import com.webull.openapi.core.sse.internal.retry.SseRetryContext;
import com.webull.openapi.core.sse.internal.retry.SynchronousSseRetryable;
import com.webull.openapi.core.sse.message.SseEventType;
import com.webull.openapi.core.sse.message.SseSubscribeResponse;
import com.webull.openapi.core.utils.ExceptionUtils;
import com.webull.openapi.core.utils.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Holds a single SSE subscription: establishes the stream, parses the frames, dispatches them
 * to the observers and re-establishes the stream according to the retry policy.
 *
 * <p>The channel supports no resuming, every reconnection is a brand new subscription with
 * freshly signed headers, so events produced while disconnected are lost.
 */
public class SseConnection {

    private static final Logger logger = LoggerFactory.getLogger(SseConnection.class);

    private static final String FIELD_EVENT = "event";
    private static final String FIELD_DATA = "data";
    private static final char FIELD_SEPARATOR = ':';
    /**
     * Error body of a rejected subscription, e.g. {@code {"error":"..."}}.
     */
    private static final String ERROR_BODY_FIELD = "error";
    /**
     * Bounded to avoid buffering an unexpectedly large error body.
     */
    private static final long MAX_ERROR_BODY_BYTES = 1024L;

    private final OkHttpClient httpClient;
    private final Supplier<Request> requestSupplier;
    private final List<SseObserver> observers;
    private final Retryable<?> retryable;

    private final CompletableFuture<Void> streamingFuture = new CompletableFuture<>();
    private final AtomicReference<Call> currentCall = new AtomicReference<>();
    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    /**
     * Reset on {@code subscribeSuccess} rather than on HTTP 200, since a committed stream may still
     * be rejected by the session mutex, so the attempt counter reflects consecutive failures only.
     */
    private volatile SseRetryContext retryContext;

    public SseConnection(OkHttpClient httpClient,
                         Supplier<Request> requestSupplier,
                         List<SseObserver> observers,
                         RetryPolicy retryPolicy) {
        this.httpClient = httpClient;
        this.requestSupplier = requestSupplier;
        this.observers = observers;
        Supplier<Object> doConnect = () -> {
            this.connect();
            return null;
        };
        this.retryable = new SynchronousSseRetryable(doConnect, retryPolicy);
    }

    /**
     * Opens the event stream. Returns as soon as the request is enqueued, the stream itself is
     * consumed on a HTTP dispatcher thread.
     */
    public void connect() {
        if (this.cancelled.get() || this.streamingFuture.isDone()) {
            return;
        }
        Request request = this.requestSupplier.get();
        if (logger.isDebugEnabled()) {
            logger.debug("Subscribing sse events, url={}, requestId={}.",
                    request.url(), request.header(Headers.REQUEST_ID));
        }
        Call call = this.httpClient.newCall(request);
        this.currentCall.set(call);
        // cancel() may run between the check above and the set, in which case it has cancelled
        // the previous call only. Re-checking after the set guarantees one side sees the other,
        // otherwise this stream would outlive the subscription and hold the app key session.
        if (this.cancelled.get()) {
            call.cancel();
            return;
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException ex) {
                onStreamEnd(ex);
            }

            @Override
            public void onResponse(Call call, Response response) {
                onStreamEnd(consume(response));
            }
        });
    }

    /**
     * @return the streaming future, completed exceptionally once reconnecting is given up.
     */
    public CompletableFuture<Void> streamingFuture() {
        return this.streamingFuture;
    }

    /**
     * Cancels the subscription. No reconnection is attempted afterwards.
     */
    public void cancel() {
        if (this.cancelled.compareAndSet(false, true)) {
            Call call = this.currentCall.get();
            if (call != null) {
                call.cancel();
            }
        }
        if (!this.streamingFuture.isDone()) {
            this.streamingFuture.cancel(false);
        }
    }

    /**
     * Reads the response to its end.
     *
     * @return the cause of the stream end, never null since the server never ends the stream
     * in a graceful way.
     */
    private Throwable consume(Response response) {
        try (Response res = response) {
            String requestId = res.request().header(Headers.REQUEST_ID);
            ResponseBody body = res.body();
            if (!res.isSuccessful()) {
                return new HttpServerException(ErrorCode.UNKNOWN_SERVER_ERROR, errorMessage(res),
                        res.code(), requestId);
            }
            if (body == null) {
                return new SseStreamClosedException("Sse response body is absent");
            }
            return readStream(body, requestId);
        } catch (Exception ex) {
            return ex;
        }
    }

    private Throwable readStream(ResponseBody body, String requestId) {
        String eventName = null;
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(body.byteStream(), StandardCharsets.UTF_8))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.isEmpty()) {
                    Throwable rejected = this.dispatch(eventName, data.toString(), requestId);
                    if (rejected != null) {
                        return rejected;
                    }
                    eventName = null;
                    data.setLength(0);
                    continue;
                }
                if (line.charAt(0) == FIELD_SEPARATOR) {
                    // comment line, the server heartbeat `: heartbeat` arrives this way
                    continue;
                }
                int separatorIndex = line.indexOf(FIELD_SEPARATOR);
                String field = separatorIndex < 0 ? line : line.substring(0, separatorIndex);
                String value = separatorIndex < 0 ? "" : line.substring(separatorIndex + 1);
                if (!value.isEmpty() && value.charAt(0) == ' ') {
                    value = value.substring(1);
                }
                if (FIELD_EVENT.equals(field)) {
                    eventName = value;
                } else if (FIELD_DATA.equals(field)) {
                    if (data.length() > 0) {
                        data.append('\n');
                    }
                    data.append(value);
                }
                // `id` is always empty and `retry` is unused by this channel
            }
            return new SseStreamClosedException("Sse stream closed by server");
        } catch (Exception ex) {
            return ex;
        }
    }

    /**
     * Frames are classified by the {@code event:} name, the {@code eventType} in data is omitted
     * when zero and therefore not reliable.
     *
     * @return non-null to end the stream, i.e. the connection was rejected by the session mutex
     */
    private Throwable dispatch(String eventName, String data, String requestId) {
        if (data.isEmpty()) {
            return null;
        }
        Optional<SseEventType> maybeType = SseEventType.fromEventName(eventName);
        if (!maybeType.isPresent()) {
            logger.warn("Unknown sse event={}, ignored.", eventName);
            return null;
        }
        SseEventType type = maybeType.get();
        SseSubscribeResponse response;
        try {
            response = JsonSerializer.fromJson(data, SseSubscribeResponse.class);
        } catch (Exception ex) {
            // a single malformed frame must not tear down the stream
            logger.error("Unable to parse sse frame, event={}.", eventName, ex);
            return null;
        }
        if (response == null) {
            logger.error("Empty sse frame data, event={}.", eventName);
            return null;
        }
        switch (type) {
            case SubscribeSuccess:
                // stream established, previous failures are no longer consecutive
                this.retryContext = null;
                break;
            case NumOfConnExceed:
                // same as the HTTP 429 of the pre-check, has to be retried with backoff
                return new HttpServerException(ErrorCode.TOO_MANY_REQUESTS, response.getPayload(),
                        HttpStatus.TOO_MANY_REQUESTS, requestId);
            case Event:
            case SubscribeExpired:
                break;
        }
        for (SseObserver observer : this.observers) {
            try {
                observer.onNext(type, response);
            } catch (Exception ex) {
                logger.error("Sse handler={} failed on event={}, type={}.",
                        observer.getClass(), eventName, response.getEventType(), ex);
            }
        }
        return null;
    }

    private void onStreamEnd(Throwable cause) {
        Throwable endCause = this.cancelled.get() ? new SseCancelledException() : cause;
        for (SseObserver observer : this.observers) {
            try {
                observer.onError(endCause);
            } catch (Exception ex) {
                logger.error("Sse handler={} failed on stream end.", observer.getClass(), ex);
            }
        }

        int attempts = this.retryContext != null ? this.retryContext.getRetriesAttempted() + 1 : 1;
        this.retryContext = new SseRetryContext(attempts, endCause);

        Throwable finalCause = endCause;
        if (!this.streamingFuture.isDone()) {
            try {
                this.retryable.retry(this.retryContext);
                return;
            } catch (RetriedFailedException ex) {
                finalCause = ex.getCause();
            }
        }

        // end of retry
        Throwable rootCause = ExceptionUtils.getRootCause(finalCause);
        if (rootCause instanceof SseCancelledException) {
            if (!this.streamingFuture.isDone()) {
                this.streamingFuture.cancel(false);
            }
        } else {
            logger.error("Sse event session terminated, reconnecting given up.", finalCause);
            this.streamingFuture.completeExceptionally(finalCause);
        }
    }

    private String errorMessage(Response response) {
        String body;
        try {
            body = response.peekBody(MAX_ERROR_BODY_BYTES).string();
        } catch (IOException ex) {
            logger.debug("Unable to read sse error body.", ex);
            return response.message();
        }
        try {
            Map<String, String> error = JsonSerializer.fromJson(body,
                    new TypeToken<Map<String, String>>(){}.getType());
            if (error != null && StringUtils.isNotBlank(error.get(ERROR_BODY_FIELD))) {
                return error.get(ERROR_BODY_FIELD);
            }
        } catch (Exception ex) {
            logger.debug("Sse error body is not json, body={}.", body);
        }
        return body;
    }
}
