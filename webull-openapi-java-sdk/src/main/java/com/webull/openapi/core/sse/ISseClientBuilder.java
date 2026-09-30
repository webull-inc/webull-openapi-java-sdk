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
package com.webull.openapi.core.sse;

import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.backoff.BackoffStrategy;
import com.webull.openapi.core.sse.internal.retry.SseRetryCondition;
import com.webull.openapi.core.sse.lifecycle.SseHandler;
import com.webull.openapi.core.sse.message.SseSubscribeResponse;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface ISseClientBuilder {

    ISseClientBuilder appKey(String appKey);

    ISseClientBuilder appSecret(String appSecret);

    ISseClientBuilder host(String host);

    ISseClientBuilder port(int port);

    ISseClientBuilder regionId(String regionId);

    ISseClientBuilder enableTls(boolean enableTls);

    ISseClientBuilder connectTimeout(long connectTimeout, TimeUnit timeUnit);

    /**
     * Idle timeout of the event stream. The server sends a comment line heartbeat periodically,
     * so no data arriving within this duration means the connection is dead and should be
     * re-established.
     */
    ISseClientBuilder readTimeout(long readTimeout, TimeUnit timeUnit);

    default ISseClientBuilder reconnectBy(BackoffStrategy backoffStrategy) {
        return this.reconnectBy(new RetryPolicy(SseRetryCondition.getInstance(), backoffStrategy));
    }

    ISseClientBuilder reconnectBy(RetryPolicy retryPolicy);

    ISseClientBuilder addHandler(SseHandler handler);

    ISseClientBuilder onMessage(Consumer<SseSubscribeResponse> consumer);

    ISseClient build();
}
