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
package com.webull.openapi.trade.events.subscribe;

import com.webull.openapi.trade.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.trade.grpc.retry.GrpcRetryCondition;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.backoff.BackoffStrategy;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

import java.util.function.Consumer;

public interface ITradeEventClientBuilder {

    ITradeEventClientBuilder appKey(String appKey);

    ITradeEventClientBuilder appSecret(String appSecret);

    ITradeEventClientBuilder host(String host);

    ITradeEventClientBuilder port(int port);

    ITradeEventClientBuilder regionId(String regionId);

    ITradeEventClientBuilder enableTls(boolean enableTls);

    default ITradeEventClientBuilder reconnectBy(BackoffStrategy backoffStrategy) {
        return this.reconnectBy(new RetryPolicy(GrpcRetryCondition.getInstance(), backoffStrategy));
    }

    ITradeEventClientBuilder reconnectBy(RetryPolicy retryPolicy);

    ITradeEventClientBuilder addHandler(GrpcHandler handler);

    ITradeEventClientBuilder onMessage(Consumer<SubscribeResponse> consumer);

    ITradeEventClient build();
}
