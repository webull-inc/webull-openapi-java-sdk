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
package com.webull.openapi.core.http.initializer.config;

import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.config.bean.ApiConfig;
import com.webull.openapi.core.http.initializer.token.bean.AccessToken;

public class ConfigService {

    private final HttpApiClient apiClient;

    public ConfigService(HttpApiConfig config) {
        this(new HttpApiClient(config));
    }

    public ConfigService(HttpApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get config
     * @return
     */
    public ApiConfig getConfig() {

        HttpRequest request = new HttpRequest("/openapi/config", Versions.V2, HttpMethod.GET);
        return apiClient.request(request).responseType(ApiConfig.class).doAction();

    }
}
