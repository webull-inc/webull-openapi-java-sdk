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
package com.webull.openapi.data.internal.mqtt;

import com.webull.openapi.core.common.ApiModule;
import com.webull.openapi.core.endpoint.EndpointResolver;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.backoff.FixedDelayStrategy;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.data.DataStreamingClient;
import com.webull.openapi.data.internal.mqtt.codec.PublishToMarketDataDecoder;
import com.webull.openapi.data.internal.mqtt.lifecycle.ApiAuthProvider;
import com.webull.openapi.data.internal.mqtt.lifecycle.ApiSubscriptionManager;
import com.webull.openapi.data.internal.mqtt.lifecycle.QuotesSubsLoggingHandler;
import com.webull.openapi.data.internal.mqtt.lifecycle.QuotesSubsReconnectHandler;
import com.webull.openapi.data.quotes.api.subsribe.IMarketStreamingClient;
import com.webull.openapi.data.quotes.api.subsribe.MarketStreamingClient;
import com.webull.openapi.data.quotes.subsribe.IDataStreamingClient;
import com.webull.openapi.data.quotes.subsribe.IDataStreamingClientBuilder;
import com.webull.openapi.data.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsInboundHandler;
import com.webull.openapi.data.quotes.subsribe.message.MarketData;
import com.webull.openapi.data.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.data.quotes.subsribe.retry.QuotesSubsRetryCondition;

import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class DataStreamingClientBuilder implements IDataStreamingClientBuilder {

    private String appKey;
    private String appSecret;
    private String sessionId;
    private String regionId;
    private String http_host;
    private String mqtt_host;
    private int mqtt_port = 1883;
    private long connectTimeoutMillis = 5000;
    private long readTimeoutMillis = 60000;
    private IMarketStreamingClient apiClient;
    private RetryPolicy retryPolicy =
            new RetryPolicy(QuotesSubsRetryCondition.getInstance(), new FixedDelayStrategy(10, TimeUnit.SECONDS));
    private boolean enableTls = true;
    private ProxyConfig proxyConfig;

    private final LinkedList<QuotesSubsHandler> handlers = new LinkedList<>();
    private final LinkedList<QuotesSubsInboundHandler> onMessages = new LinkedList<>();

    private Set<String> symbols;
    private String category;
    private Set<String> subTypes;
    private String depth;
    private Boolean overnightRequired;

    @Override
    public IDataStreamingClientBuilder appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder http_host(String http_host) {
        this.http_host = http_host;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder mqtt_host(String mqtt_host) {
        this.mqtt_host = mqtt_host;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder mqtt_port(int mqtt_port) {
        this.mqtt_port = mqtt_port;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder connectTimeout(long timeoutMillis) {
        this.connectTimeoutMillis = timeoutMillis;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder readTimeout(long timeoutMillis) {
        this.readTimeoutMillis = timeoutMillis;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder apiClient(IMarketStreamingClient apiClient) {
        this.apiClient = apiClient;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder reconnectBy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder enableTls(boolean enableTls) {
        this.enableTls = enableTls;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder proxy(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
        return this;
    }

    @Override
    public IDataStreamingClientBuilder addHandler(QuotesSubsHandler handler) {
        Assert.notNull("handler", handler);
        this.handlers.add(handler);
        return this;
    }

    @Override
    public IDataStreamingClientBuilder onMessage(Consumer<MarketData> consumer) {
        Assert.notNull("consumer", consumer);
        QuotesSubsInboundHandler handler = message -> {
            consumer.accept((MarketData) message);
            // pass on message by default
            return message;
        };
        this.onMessages.add(handler);
        return this;
    }

    @Override
    public IDataStreamingClientBuilder addSubscription(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        this.symbols = symbols;
        this.category = category;
        this.subTypes = subTypes;
        this.depth = depth;
        this.overnightRequired = overnightRequired;
        return this;
    }

    @Override
    public IDataStreamingClient build() {
        if (StringUtils.isBlank(this.mqtt_host)) {
            Assert.notBlank("regionId", regionId);
            this.mqtt_host = EndpointResolver.getDefault().resolve(regionId, ApiModule.of("QUOTES"))
                    .orElseThrow(() -> new ClientException(ErrorCode.ENDPOINT_RESOLVING_ERROR, "Unknown region"));
        }

        if (this.apiClient == null) {
            HttpApiConfig.HttpApiConfigBuilder httpApiConfigBuilder = HttpApiConfig.builder()
                    .appKey(this.appKey)
                    .appSecret(this.appSecret)
                    .regionId(this.regionId);
            if(StringUtils.isNotBlank(http_host)){
                httpApiConfigBuilder.endpoint(http_host);
            }
            HttpApiConfig apiConfig = httpApiConfigBuilder.build();
            this.apiClient = new MarketStreamingClient(apiConfig);
        }

        LinkedList<QuotesSubsHandler> allHandlers = new LinkedList<>();
        AuthProvider authProvider = new ApiAuthProvider(this.sessionId, this.appKey, this.apiClient);

        // add logging handler
        allHandlers.add(new QuotesSubsLoggingHandler());
        // add reconnector
        if (this.retryPolicy != null) {
            QuotesSubsReconnectHandler reconnectHandler = new QuotesSubsReconnectHandler(this.appKey, authProvider, this.retryPolicy);
            allHandlers.add(reconnectHandler);
        }

        // add subscription manager
        ApiSubscriptionManager subscriptionManager;
        if (CollectionUtils.isNotEmpty(this.symbols) || StringUtils.isNotBlank(this.category) || CollectionUtils.isNotEmpty(this.subTypes)) {
            subscriptionManager = new ApiSubscriptionManager(this.apiClient, this.retryPolicy, this.symbols, this.category, this.subTypes, this.depth, this.overnightRequired);
        } else {
            subscriptionManager = new ApiSubscriptionManager(this.apiClient, this.retryPolicy);
        }
        allHandlers.add(subscriptionManager);

        // add decoder
        allHandlers.add(new PublishToMarketDataDecoder());
        // add on message handlers
        allHandlers.addAll(this.onMessages);
        // add other handlers
        allHandlers.addAll(this.handlers);

        return new DataStreamingClient(this.mqtt_host, this.mqtt_port, this.enableTls, this.retryPolicy, authProvider,
                subscriptionManager, allHandlers, this.connectTimeoutMillis, this.readTimeoutMillis, this.proxyConfig);
    }
}
