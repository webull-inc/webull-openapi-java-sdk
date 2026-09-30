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
package com.webull.openapi.core.sse.message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class SseSubscribeRequest {

    /**
     * Order + position + option (1 | 2 | 4), aligned with the gRPC SubscribeRequest and the python SDK.
     */
    private static final int DEFAULT_SUBSCRIBE_TYPE = 7;

    private final int subscribeType;
    private final long timestamp;
    private final Set<String> accounts;

    public SseSubscribeRequest(String... accounts) {
        this(new HashSet<>(Arrays.asList(accounts)));
    }

    public SseSubscribeRequest(Set<String> accounts) {
        this(DEFAULT_SUBSCRIBE_TYPE, accounts);
    }

    public SseSubscribeRequest(int subscribeType, Set<String> accounts) {
        this.subscribeType = subscribeType;
        this.timestamp = System.currentTimeMillis();
        this.accounts = accounts;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Set<String> getAccounts() {
        return accounts;
    }
}
