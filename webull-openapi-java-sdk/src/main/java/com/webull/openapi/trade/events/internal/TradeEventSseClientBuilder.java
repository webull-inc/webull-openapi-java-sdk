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
package com.webull.openapi.trade.events.internal;

import com.webull.openapi.core.common.ApiModule;
import com.webull.openapi.core.endpoint.EndpointResolver;
import com.webull.openapi.core.exception.ClientException;
import com.webull.openapi.core.exception.ErrorCode;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.backoff.JitterExponentialBackoffStrategy;
import com.webull.openapi.core.sse.ISseClient;
import com.webull.openapi.core.sse.ISseClientBuilder;
import com.webull.openapi.core.sse.internal.lifecycle.SseLoggingHandler;
import com.webull.openapi.core.sse.internal.retry.SseRetryCondition;
import com.webull.openapi.core.sse.lifecycle.SseHandler;
import com.webull.openapi.core.sse.lifecycle.SseInboundHandler;
import com.webull.openapi.core.sse.message.SseSubscribeResponse;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.trade.TradeEventSseClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TradeEventSseClientBuilder implements ISseClientBuilder {

    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 5_000L;
    /**
     * The server heartbeat interval is 20s by default, nothing arriving within two intervals
     * means the connection is dead and has to be re-established.
     */
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = 40_000L;

    private String appKey;
    private String appSecret;
    private String regionId;
    private String host;
    private int port = 443;
    private boolean enableTls = true;
    private long connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;
    private long readTimeoutMillis = DEFAULT_READ_TIMEOUT_MILLIS;

    /**
     * Backs off exponentially up to 60s and never gives up on 429, since the session of the same
     * app key may be held until its TTL (3 heartbeats, ~60s) expires after a hard disconnection.
     */
    private RetryPolicy retryPolicy = new RetryPolicy(SseRetryCondition.getInstance(),
            new JitterExponentialBackoffStrategy(1, 60, TimeUnit.SECONDS));

    private final LinkedList<SseHandler> handlers = new LinkedList<>();
    private final LinkedList<SseInboundHandler> onMessages = new LinkedList<>();

    @Override
    public ISseClientBuilder appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    @Override
    public ISseClientBuilder appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    @Override
    public ISseClientBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public ISseClientBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public ISseClientBuilder regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    @Override
    public ISseClientBuilder enableTls(boolean enableTls) {
        this.enableTls = enableTls;
        return this;
    }

    @Override
    public ISseClientBuilder connectTimeout(long connectTimeout, TimeUnit timeUnit) {
        Assert.notNull("timeUnit", timeUnit);
        this.connectTimeoutMillis = timeUnit.toMillis(connectTimeout);
        return this;
    }

    @Override
    public ISseClientBuilder readTimeout(long readTimeout, TimeUnit timeUnit) {
        Assert.notNull("timeUnit", timeUnit);
        this.readTimeoutMillis = timeUnit.toMillis(readTimeout);
        return this;
    }

    @Override
    public ISseClientBuilder reconnectBy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    @Override
    public ISseClientBuilder addHandler(SseHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    @Override
    public ISseClientBuilder onMessage(Consumer<SseSubscribeResponse> consumer) {
        this.onMessages.add(consumer::accept);
        return this;
    }

    @Override
    public ISseClient build() {
        if (StringUtils.isBlank(this.host)) {
            Assert.notBlank("regionId", this.regionId);
            this.host = EndpointResolver.getDefault().resolve(this.regionId, ApiModule.of("EVENTS"))
                    .orElseThrow(() -> new ClientException(ErrorCode.ENDPOINT_RESOLVING_ERROR, "Unknown region"));
        }
        List<SseHandler> allHandlers = new ArrayList<>();
        // add event logging handler
        allHandlers.add(new SseLoggingHandler());
        // add other handlers
        allHandlers.addAll(this.handlers);
        // add on message handlers
        allHandlers.addAll(this.onMessages);
        return new TradeEventSseClient(
                this.appKey,
                this.appSecret,
                this.host,
                this.port,
                this.enableTls,
                this.connectTimeoutMillis,
                this.readTimeoutMillis,
                this.retryPolicy,
                allHandlers);
    }
}
