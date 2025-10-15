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
package com.webull.openapi.data.internal.mqtt.lifecycle;

import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.data.quotes.api.subsribe.IMarketStreamingClient;
import com.webull.openapi.data.quotes.subsribe.lifecycle.AuthProvider;

public class ApiAuthProvider implements AuthProvider {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuthProvider.class);

    private final String appKey;
    private volatile String sessionId;
    private final IMarketStreamingClient apiClient;

    private volatile boolean isClosed = false;

    public ApiAuthProvider(String sessionId, String appKey, IMarketStreamingClient apiClient) {
        Assert.notBlank("sid", sessionId);
        Assert.notBlank("appKey", appKey);
        Assert.notNull("apiClient", apiClient);
        this.sessionId = sessionId;
        this.appKey = appKey;
        this.apiClient = apiClient;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void close() {
        this.isClosed = true;
    }
}
