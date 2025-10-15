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
package com.webull.openapi.core.http.initializer.token;

import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.token.bean.AccessToken;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class TokenService {

    private static final String TOKEN_ARG = "token";

    private final Region region;
    private final HttpApiClient apiClient;

    public TokenService(HttpApiConfig config) {
        this(new HttpApiClient(config));
    }

    public TokenService(HttpApiClient apiClient) {
        this.region = Region.of(apiClient.getConfig().getRegionId())
                .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER,
                        "Must set region id which defined in " + Region.class.getName() + " when using this service."));
        this.apiClient = apiClient;
    }

    /**
     * Create token
     * @param token
     * @return
     */
    public AccessToken createToken(String token) {

        HttpRequest request = new HttpRequest("/auth/token/create", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(token)){
            params.put(TOKEN_ARG, token);
        }
        request.setBody(params);
        return apiClient.request(request).responseType(AccessToken.class).doAction();

    }

    /**
     * Check token
     * @param token
     * @return
     */
    public AccessToken checkToken(String token) {

        Assert.notBlank(TOKEN_ARG, token);
        HttpRequest request = new HttpRequest("/auth/token/check", Versions.V2, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(TOKEN_ARG, token);
        request.setBody(params);
        return apiClient.request(request).responseType(AccessToken.class).doAction();

    }

    /**
     * Refresh token
     * @param token
     * @return
     */
    public AccessToken refreshToken(String token) {

        Assert.notBlank(TOKEN_ARG, token);
        HttpRequest request = new HttpRequest("/auth/token/refresh", Versions.V2, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(TOKEN_ARG, token);
        request.setBody(params);
        return apiClient.request(request).responseType(AccessToken.class).doAction();

    }
}
