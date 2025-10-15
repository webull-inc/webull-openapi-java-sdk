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
package com.webull.openapi.data.quotes.subsribe;

import com.webull.openapi.data.quotes.api.subsribe.IMarketStreamingClient;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.data.quotes.subsribe.message.MarketData;
import com.webull.openapi.data.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.data.quotes.subsribe.retry.QuotesSubsRetryCondition;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.backoff.BackoffStrategy;

import java.util.Set;
import java.util.function.Consumer;

public interface IDataStreamingClientBuilder {

    IDataStreamingClientBuilder appKey(String appKey);

    IDataStreamingClientBuilder appSecret(String appSecret);

    IDataStreamingClientBuilder sessionId(String sessionId);

    IDataStreamingClientBuilder http_host(String http_host);

    IDataStreamingClientBuilder mqtt_host(String mqtt_host);

    IDataStreamingClientBuilder mqtt_port(int mqtt_port);

    IDataStreamingClientBuilder regionId(String regionId);

    IDataStreamingClientBuilder connectTimeout(long timeoutMillis);

    IDataStreamingClientBuilder readTimeout(long timeoutMillis);

    IDataStreamingClientBuilder apiClient(IMarketStreamingClient apiClient);

    default IDataStreamingClientBuilder reconnectBy(BackoffStrategy backoffStrategy) {
        return this.reconnectBy(new RetryPolicy(QuotesSubsRetryCondition.getInstance(), backoffStrategy));
    }

    IDataStreamingClientBuilder reconnectBy(RetryPolicy retryPolicy);

    IDataStreamingClientBuilder enableTls(boolean enableTls);

    IDataStreamingClientBuilder proxy(ProxyConfig proxyConfig);

    IDataStreamingClientBuilder addHandler(QuotesSubsHandler handler);

    IDataStreamingClientBuilder onMessage(Consumer<MarketData> consumer);

    IDataStreamingClientBuilder addSubscription(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired);

    IDataStreamingClient build();
}
