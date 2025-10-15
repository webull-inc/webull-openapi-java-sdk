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
package com.webull.openapi.core.http.initializer;

import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.initializer.token.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ClientInitializer.class);

    /**
     * Client Initialization
     * @param apiClient
     */
    public static void init(HttpApiClient apiClient){
        initToken(apiClient);
    }

    /**
     * Initialize token
     * @param apiClient
     */
    private static void initToken(HttpApiClient apiClient){

        if(!checkRegionTokenEnable(apiClient)){
            return;
        }

        TokenManager tokenManager = new TokenManager();
        tokenManager.initToken(apiClient);

    }

    /**
     * Check whether token checking is enabled in the specified region
     * @param apiClient
     * @return
     */
    private static boolean checkRegionTokenEnable(HttpApiClient apiClient){
        if(Objects.isNull(apiClient)){
            logger.warn("CheckRegionTokenEnable apiClient is null return false");
            return false;
        }

        if(Objects.isNull(apiClient.getConfig())){
            logger.warn("CheckRegionTokenEnable apiConfig is null return false");
            return false;
        }

        if(StringUtils.isBlank(apiClient.getConfig().getRegionId())){
            logger.warn("CheckRegionTokenEnable regionId is blank return false");
            return false;
        }

        List<String> enableRegionIds = new ArrayList<>();
        enableRegionIds.add(Region.hk.name());

        boolean result = enableRegionIds.contains(apiClient.getConfig().getRegionId());
        logger.info("CheckRegionTokenEnable result is {}, enable regionIds is {}.", result, enableRegionIds);
        return result;
    }
}
