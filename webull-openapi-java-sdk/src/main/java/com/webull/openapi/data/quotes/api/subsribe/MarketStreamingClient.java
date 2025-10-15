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
package com.webull.openapi.data.quotes.api.subsribe;

import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.ClientInitializer;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.data.common.ArgNames;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MarketStreamingClient implements IMarketStreamingClient {

    private static final String NOT_SUPPORT_MSG = "Http client not support for this method, please use default grpc client.";

    private final HttpApiClient apiClient;
    private String userId;

    public MarketStreamingClient(HttpApiConfig config) {
        this.apiClient = new HttpApiClient(config);
        ClientInitializer.init(apiClient);
    }

    public MarketStreamingClient(HttpApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    /**
     * Set user ID which will be added as x-user-id header to all requests
     * @param userId user ID
     * @return current client instance
     */
    public MarketStreamingClient setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public void subscribe(String sessionId, Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        Assert.notBlank(ArgNames.SESSION_ID, sessionId);
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notEmpty(ArgNames.SUBTYPES, subTypes);

        HttpRequest request = new HttpRequest("/market-data/streaming/subscribe", Versions.V2, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SESSION_ID, sessionId);
        params.put(ArgNames.SYMBOLS, symbols);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.SUBTYPES, subTypes);
        if(StringUtils.isNotBlank(depth)){
            params.put(ArgNames.DEPTH, depth);
        }
        if(Objects.nonNull(overnightRequired)){
            params.put(ArgNames.OVERNIGHT_REQUIRED, overnightRequired);
        }
        request.setBody(params);
        apiClient.request(request).doVoidAction();

    }

    @Override
    public void unsubscribe(String sessionId, Set<String> symbols, String category, Set<String> subTypes, Boolean unsubscribeAll) {

        Assert.notBlank(ArgNames.SESSION_ID, sessionId);

        HttpRequest request = new HttpRequest("/market-data/streaming/unsubscribe", Versions.V2, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SESSION_ID, sessionId);

        if (CollectionUtils.isNotEmpty(symbols)) {
            params.put(ArgNames.SYMBOLS, symbols);
        }
        if (StringUtils.isNotBlank(category)) {
            params.put(ArgNames.CATEGORY, category);
        }
        if (CollectionUtils.isNotEmpty(subTypes)) {
            params.put(ArgNames.SUBTYPES, subTypes);
        }
        if (unsubscribeAll != null) {
            params.put(ArgNames.UNSUBSCRIBE_ALL, unsubscribeAll);
        }

        request.setBody(params);
        apiClient.request(request).doVoidAction();

    }

}
