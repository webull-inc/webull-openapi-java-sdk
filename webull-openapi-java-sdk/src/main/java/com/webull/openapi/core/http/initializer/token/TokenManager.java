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


import com.webull.openapi.core.common.dict.TokenStatus;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.http.initializer.ClientInitializer;
import com.webull.openapi.core.http.initializer.token.bean.AccessToken;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.DesensitizeUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TokenManager {

    private static final Logger logger = LoggerFactory.getLogger(ClientInitializer.class);

    private static final String TOKEN_FILE_NAME = "conf/token.txt";
    private static final String CONF_ENV_TOKEN_DIR = System.getenv("WEBULL_OPENAPI_TOKEN_DIR");
    private static final String DEFAULT_ENV_TOKEN_DIR = System.getProperty("user.dir");
    private final Path tokenFilePath;

    public TokenManager() {

        Path dirPath;

        if (StringUtils.isNotBlank(CONF_ENV_TOKEN_DIR)) {
            dirPath = Paths.get(CONF_ENV_TOKEN_DIR);
        } else {
            // If no directory is specified, the current running directory is used.
            dirPath = Paths.get(DEFAULT_ENV_TOKEN_DIR);
        }

        this.tokenFilePath = dirPath.resolve(TOKEN_FILE_NAME);
    }


    public String initToken(HttpApiClient apiClient) {

        AccessToken localAccessToken = loadTokenFromLocal();
        String localToken = Objects.nonNull(localAccessToken) ? localAccessToken.getToken() : null;
        AccessToken serverAccessToken = fetchTokenFromServer(apiClient, localToken);

        saveTokenToLocal(serverAccessToken);

        if(!Objects.equals(TokenStatus.NORMAL.name(), serverAccessToken.getStatus())){
            String msg = String.format("initToken status not verified error. token:%s expires:%s status:%s",
                    DesensitizeUtils.desensitizeToken(serverAccessToken.getToken()), serverAccessToken.getExpires(), serverAccessToken.getStatus());
            logger.warn(msg);
            throw new ClientException(ErrorCode.INIT_TOKEN_ERROR, msg);
        }

        String accessToken = serverAccessToken.getToken();
        apiClient.setToken(accessToken);

        return accessToken;
    }

    private AccessToken loadTokenFromLocal() {

        if (!Files.exists(tokenFilePath)) {
            logger.info("loadTokenFromLocal local file does not exist, file:{}.", tokenFilePath.toString());
            return null;
        }

        logger.info("loadTokenFromLocal reading token from file... files:{} ...", tokenFilePath.toString());
        try (BufferedReader reader = Files.newBufferedReader(tokenFilePath)) {
            String token = reader.readLine();
            String expires = reader.readLine();
            String status = reader.readLine();
            logger.info("loadTokenFromLocal read local token result. token:{} expires:{} status:{}",
                    DesensitizeUtils.desensitizeToken(token), expires, status);
            AccessToken accessToken = new AccessToken();
            accessToken.setToken(token);
            return accessToken;
        } catch (IOException e) {
            logger.error("loadTokenFromLocal error, file:{}", tokenFilePath.toString(), e);
            throw new ClientException(ErrorCode.LOAD_TOKEN_ERROR, e);
        }

    }

    private void saveTokenToLocal(AccessToken serverAccessToken) {
        try {
            logger.info("saveTokenToLocal writing token to local file. token:{} expires:{} status:{} files:{}",
                    tokenFilePath.toString(), DesensitizeUtils.desensitizeToken(serverAccessToken.getToken()), serverAccessToken.getExpires(), serverAccessToken.getStatus());
            Files.createDirectories(tokenFilePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(tokenFilePath)) {
                writer.write(serverAccessToken.getToken());
                writer.newLine();
                writer.write(serverAccessToken.getExpires().toString());
                writer.newLine();
                writer.write(serverAccessToken.getStatus());
            }
        } catch (IOException e) {
            logger.error("saveTokenToLocal error, files:{}", tokenFilePath.toString(), e);
            throw new ClientException(ErrorCode.SAVE_TOKEN_ERROR, e);
        }
    }

    private AccessToken fetchTokenFromServer(HttpApiClient apiClient, String localToken){

        TokenService tokenService = new TokenService(apiClient);
        AccessToken createAccessToken = createToken(tokenService, localToken);
        if(Objects.equals(TokenStatus.NORMAL.name(), createAccessToken.getStatus())){
            logger.info("fetchTokenFromServer createToken status is verified, no further check required, returning directly. token:{} expires:{} status:{}",
                    DesensitizeUtils.desensitizeToken(createAccessToken.getToken()), createAccessToken.getExpires(), createAccessToken.getStatus());
            return createAccessToken;
        }

        return checkToken(apiClient.getConfig(), tokenService, createAccessToken);

    }

    private AccessToken createToken(TokenService tokenService, String localToken){
        AccessToken createAccessToken = tokenService.createToken(localToken);

        logger.info("fetchTokenFromServer createToken result is [{}]", createAccessToken);

        if(Objects.isNull(createAccessToken)){
            logger.error("fetchTokenFromServer error createToken result is null");
            throw new ClientException(ErrorCode.CREATE_TOKEN_ERROR, "CreateToken result is null.");
        }
        if(StringUtils.isBlank(createAccessToken.getToken())){
            logger.error("fetchTokenFromServer error createToken token is blank");
            throw new ClientException(ErrorCode.CREATE_TOKEN_ERROR, "CreateToken token is blank.");
        }
        if(Objects.isNull(createAccessToken.getExpires())){
            logger.error("fetchTokenFromServer error createToken expires is null");
            throw new ClientException(ErrorCode.CREATE_TOKEN_ERROR, "CreateToken expires is null.");
        }
        if(StringUtils.isBlank(createAccessToken.getStatus())){
            logger.error("fetchTokenFromServer error createToken status is null");
            throw new ClientException(ErrorCode.CREATE_TOKEN_ERROR, "CreateToken status is null.");
        }
        return createAccessToken;
    }

    public AccessToken checkToken(HttpApiConfig config, TokenService tokenService, AccessToken createAccessToken) {

        long durationMillis = config.getTokenCheckDurationSeconds() * 1000L ;
        long internalMillis = config.getTokenCheckIntervalSeconds() * 1000L ;
        long startTime = System.currentTimeMillis();
        logger.info("fetchTokenFromServer started checkToken loop. token:{} durationSeconds:{} intervalSeconds:{}",
                DesensitizeUtils.desensitizeToken(createAccessToken.getToken()), config.getTokenCheckDurationSeconds(), config.getTokenCheckIntervalSeconds());

        while (true) {
            long loopStart = System.currentTimeMillis();
            AccessToken checkAccessToken = tokenService.checkToken(createAccessToken.getToken());

            if(Objects.isNull(checkAccessToken)){
                logger.error("fetchTokenFromServer error checkToken result is null");
                throw new ClientException(ErrorCode.CHECK_TOKEN_ERROR, "CheckToken result is null.");
            }
            logger.info("fetchTokenFromServer result of current checkToken loop. token:{} expires:{} status:{}",
                    DesensitizeUtils.desensitizeToken(checkAccessToken.getToken()), checkAccessToken.getExpires(), checkAccessToken.getStatus());

            if(StringUtils.isBlank(checkAccessToken.getToken())){
                logger.error("fetchTokenFromServer error checkToken token is blank");
                throw new ClientException(ErrorCode.CHECK_TOKEN_ERROR, "CheckToken token is blank.");
            }
            if(Objects.isNull(checkAccessToken.getExpires())){
                logger.error("fetchTokenFromServer error checkToken expires is null");
                throw new ClientException(ErrorCode.CHECK_TOKEN_ERROR, "CheckToken expires is null.");
            }
            if(StringUtils.isBlank(checkAccessToken.getStatus())){
                logger.error("fetchTokenFromServer error checkToken status is null");
                throw new ClientException(ErrorCode.CHECK_TOKEN_ERROR, "CheckToken status is null.");
            }

            if (Objects.equals(TokenStatus.INVALID.name(), checkAccessToken.getStatus()) || Objects.equals(TokenStatus.EXPIRED.name(), checkAccessToken.getStatus())) {
                String msg = String.format("fetchTokenFromServer checkToken status invalidate. status:%s", checkAccessToken.getStatus());
                logger.error(msg);
                throw new ClientException(ErrorCode.CHECK_TOKEN_ERROR, msg);
            }

            if(Objects.equals(TokenStatus.NORMAL.name(), checkAccessToken.getStatus())){
                logger.info("fetchTokenFromServer checkToken status is verified, no further check required, returning directly. token:{} expires:{} status:{}",
                        DesensitizeUtils.desensitizeToken(checkAccessToken.getToken()), checkAccessToken.getExpires(), checkAccessToken.getStatus());
                return checkAccessToken;
            }

            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= durationMillis) {
                break;
            }
            logger.info("fetchTokenFromServer status not verified, checkToken loop will start, waiting {} seconds... (elapsed {}s / {}s). For more details, please visit the OpenAPI official website.",
                    config.getTokenCheckIntervalSeconds(), elapsed / 1000, config.getTokenCheckDurationSeconds());

            long currElapsed = System.currentTimeMillis() - loopStart;
            long sleepTime = internalMillis - currElapsed;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.warn("fetchTokenFromServer checkToken sleep abnormality skip and continue.", e);
                }
            }
        }
        logger.info("fetchTokenFromServer checkToken loop completed. reached the maximum retries without passing validation, returning createAccessToken. For more details, please visit the OpenAPI official website.");
        return createAccessToken;

    }

}
