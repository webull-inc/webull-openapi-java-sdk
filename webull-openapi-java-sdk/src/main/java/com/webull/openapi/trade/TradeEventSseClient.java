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
package com.webull.openapi.trade;

import com.webull.openapi.core.exception.ClientException;
import com.webull.openapi.core.exception.ErrorCode;
import com.webull.openapi.core.http.okhttp.OkHttpClientBuilder;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.core.sse.ISseClient;
import com.webull.openapi.core.sse.ISseClientBuilder;
import com.webull.openapi.core.sse.ISseSubscription;
import com.webull.openapi.core.sse.internal.CancellableSseSubscription;
import com.webull.openapi.core.sse.internal.SseConnection;
import com.webull.openapi.core.sse.internal.SseObserver;
import com.webull.openapi.core.sse.internal.auth.SseSignatureHeaders;
import com.webull.openapi.core.sse.internal.lifecycle.proxy.SseHandlerProxyFactory;
import com.webull.openapi.core.sse.lifecycle.SseHandler;
import com.webull.openapi.core.sse.message.SseSubscribeRequest;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.trade.events.internal.TradeEventSseClientBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TradeEventSseClient implements ISseClient {

    private static final String SUBSCRIBE_PATH = "/subscribe/sse";
    private static final String ACCEPT = "Accept";
    private static final String CONTENT_TYPE_EVENT_STREAM = "text/event-stream";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * The server rejects a larger body with 400.
     */
    private static final int MAX_BODY_BYTES = 1024 * 1024;
    /**
     * A single stream needs a single connection.
     */
    private static final int MAX_IDLE_CONNECTIONS = 1;

    private final AtomicBoolean subscribing = new AtomicBoolean(false);
    private final AtomicReference<SseConnection> activeConnection = new AtomicReference<>();

    private final String appKey;
    private final String appSecret;
    private final String subscribeUrl;
    private final RetryPolicy retryPolicy;
    private final OkHttpClient httpClient;
    private final LinkedList<SseObserver> observers;

    public static ISseClientBuilder builder() {
        return new TradeEventSseClientBuilder();
    }

    /**
     * Authenticated by the app key signature only, same as the gRPC {@link TradeEventClient}.
     */
    public TradeEventSseClient(String appKey,
                               String appSecret,
                               String host,
                               int port,
                               boolean enableTls,
                               long connectTimeoutMillis,
                               long readTimeoutMillis,
                               RetryPolicy retryPolicy,
                               List<SseHandler> handlers) {
        Assert.notBlank("appKey", appKey);
        Assert.notBlank("appSecret", appSecret);
        Assert.notBlank("host", host);
        Assert.inRange("port", port, 0, 65535);
        Assert.nonnegative("connectTimeoutMillis", connectTimeoutMillis);
        Assert.nonnegative("readTimeoutMillis", readTimeoutMillis);
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.subscribeUrl = (enableTls ? "https" : "http") + "://" + host + ":" + port + SUBSCRIBE_PATH;
        this.retryPolicy = retryPolicy != null ? retryPolicy : RetryPolicy.never();
        this.httpClient = OkHttpClientBuilder.builder()
                .connectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS)
                // the stream is idle between heartbeats, a dedicated client keeps the shared pool untouched
                .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
                .connectionPool(MAX_IDLE_CONNECTIONS)
                .build();
        if (CollectionUtils.isNotEmpty(handlers)) {
            this.observers = new LinkedList<>(SseHandlerProxyFactory.getInstance().create(handlers));
        } else {
            this.observers = new LinkedList<>();
        }
    }

    @Override
    public ISseSubscription subscribe(SseSubscribeRequest request) {
        Assert.notNull("request", request);
        Assert.notEmpty("accounts", request.getAccounts());
        request.getAccounts().forEach(account -> Assert.notBlank("account", account));

        // try lock
        if (!this.subscribing.compareAndSet(false, true)) {
            throw new ClientException(ErrorCode.INVALID_STATE, "Client is already subscribed");
        }
        // the signature is calculated over these exact bytes, so they are reused as-is on reconnecting
        byte[] bodyBytes = JsonSerializer.toJson(request).getBytes(StandardCharsets.UTF_8);
        if (bodyBytes.length > MAX_BODY_BYTES) {
            this.subscribing.set(false);
            throw new ClientException(ErrorCode.INVALID_PARAMETER, "Subscribe request body exceeds 1MB");
        }

        SseConnection connection = new SseConnection(this.httpClient,
                () -> this.buildRequest(bodyBytes), this.observers, this.retryPolicy);
        this.activeConnection.set(connection);

        ISseSubscription subscription = new CancellableSseSubscription(connection);
        // release lock
        subscription.completable().whenComplete((ignore, ex) -> this.subscribing.compareAndSet(true, false));

        try {
            connection.connect();
        } catch (RuntimeException ex) {
            // nothing enqueued, the future would never complete and keep the lock held
            this.activeConnection.compareAndSet(connection, null);
            this.subscribing.set(false);
            throw ex;
        }
        return subscription;
    }

    @Override
    public void close() {
        SseConnection connection = this.activeConnection.getAndSet(null);
        if (connection != null) {
            connection.cancel();
        }
        this.httpClient.dispatcher().cancelAll();
        this.httpClient.dispatcher().executorService().shutdown();
        this.httpClient.connectionPool().evictAll();
    }

    private Request buildRequest(byte[] bodyBytes) {
        Request.Builder builder = new Request.Builder()
                .url(this.subscribeUrl)
                .post(RequestBody.create(MEDIA_TYPE_JSON, bodyBytes))
                .header(ACCEPT, CONTENT_TYPE_EVENT_STREAM);
        // a fresh signature on every attempt, expired credentials are never reused
        SseSignatureHeaders.build(this.appKey, this.appSecret, bodyBytes).forEach(builder::header);
        return builder.build();
    }
}
