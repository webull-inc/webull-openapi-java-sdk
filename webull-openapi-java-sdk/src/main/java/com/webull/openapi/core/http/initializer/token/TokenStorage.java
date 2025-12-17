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


import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.StringUtils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TokenStorage {

    private static final Logger logger = LoggerFactory.getLogger(TokenStorage.class);

    private static final String DEFAULT_TOKEN_PATH = "conf";
    private static final String DEFAULT_TOKEN_FILE = "token.txt";
    private static final String DEFAULT_ENV_TOKEN_DIR = "WEBULL_OPENAPI_TOKEN_DIR";
    private static final Set<Character> WINDOWS_INVALID_CHARS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList('|', '?', '*', '<', '>', '"')));
    private static final Set<Character> POSIX_INVALID_CHARS =
            Collections.unmodifiableSet(new HashSet<>(Collections.singletonList('\0')));

    private final Path storageTokenDir;
    private final Path tokenFile;

    /**
     * Constructor
     * @param customTokenDir
     */
    public TokenStorage(String customTokenDir) {
        // Resolve the final storage directory (priority: custom direction > environment variable > default).
        this.storageTokenDir = resolveDir(customTokenDir);
        // Full path validation
        validatePathAccess(this.storageTokenDir);
        // Ensure the directory exists
        ensureDirExists();
        // Full path
        this.tokenFile = storageTokenDir.resolve(DEFAULT_TOKEN_FILE);
        logger.info("TokenStorage initialized path:{}.", tokenFile);
        // Check file exists
        checkFileExists();
    }

    /**
     * Resolve the final storage directory (priority: custom direction > environment variable > default).
     * @param customTokenDir
     * @return
     */
    private Path resolveDir(String customTokenDir) {
        String rawDir;

        if (StringUtils.isNotBlank(customTokenDir)) {
            // custom direction
            rawDir = customTokenDir.trim();
            logger.info("TokenStorage uses the custom configuration, token_storage_dir:{}.", rawDir);
        }else if ((rawDir = System.getenv(DEFAULT_ENV_TOKEN_DIR)) != null && StringUtils.isNotBlank(rawDir)) {
            // environment variable
            rawDir = rawDir.trim();
            logger.info("TokenStorage uses environment variable configuration, {}:{}.", DEFAULT_ENV_TOKEN_DIR, rawDir);
        }else {
            // default
            rawDir = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + DEFAULT_TOKEN_PATH;
            logger.info("TokenStorage uses the default configuration, {}.", rawDir);
        }

        // Resolve path
        String resolvedDir = rawDir.replace("~", System.getProperty("user.dir"));
        validatePathSyntax(resolvedDir);

        try{
            return Paths.get(resolvedDir).toAbsolutePath().normalize();
        }catch (Exception e){
            logger.error("TokenStorage failed to get path, path:{}", resolvedDir, e);
            throw new ClientException(ErrorCode.STORAGE_TOKEN_ERROR, e);
        }

    }

    /**
     * Syntax validity check
     * @param resolvedDir
     */
    private void validatePathSyntax(String resolvedDir) {

        String os = System.getProperty("os.name").toLowerCase();
        Set<Character> invalidChars = os.contains("windows") ? WINDOWS_INVALID_CHARS : POSIX_INVALID_CHARS;
        for (char c : resolvedDir.toCharArray()) {
            if (invalidChars.contains(c)) {
                String msg = String.format("TokenStorage path contains illegal characters '%c', path:%s.",
                        c, resolvedDir);
                logger.warn(msg);
                break;
            }
        }

    }

    /**
     * Accessibility/permission check
     * @param path
     */
    private void validatePathAccess(Path path) {

        if (Files.exists(path)) {
            if (!Files.isDirectory(path)) {
                String msg = String.format("TokenStorage path already exists but is not a directory, path:%s.", path);
                logger.warn(msg);
                return;
            }
            if (!Files.isReadable(path) || !Files.isWritable(path)) {
                String msg = String.format("TokenStorage directory has no read/write permission. Please check the configuration, path:%s.", path);
                logger.warn(msg);
            }
        }else {
            Path parentDir = path.getParent();
            if (parentDir == null) {
                String msg = String.format("TokenStorage has no valid parent directory for the path, path:%s.", path);
                logger.warn(msg);
                return;
            }
            if (!Files.exists(parentDir)) {
                String msg = String.format("TokenStorage parent directory does not exist, unable to create directory. Please check the configuration, parent path:%s.",
                        parentDir.toAbsolutePath());
                logger.warn(msg);
                return;
            }
            if (!Files.isReadable(parentDir) || !Files.isWritable(parentDir) || !Files.isExecutable(parentDir)) {
                String msg = String.format("TokenStorage parent directory has no read/write permission. Please check the configuration, parent path:%s.",
                        parentDir.toAbsolutePath());
                logger.warn(msg);
            }
        }
    }

    /**
     * Ensure the directory exists
     */
    private void ensureDirExists() {
        if (!Files.exists(storageTokenDir)) {
            try {
                Files.createDirectories(storageTokenDir);
            } catch (IOException e) {
                logger.error("TokenStorage failed to create directory, file:{}", storageTokenDir, e);
                throw new ClientException(ErrorCode.STORAGE_TOKEN_ERROR, e);
            }
        }
    }

    /**
     * Check file exists
     */
    private void checkFileExists() {
        if (Files.exists(tokenFile)) {
            String msg = String.format("TokenStorage Note: The token file already exists, the latest token configuration will be overwritten and written to the token file after successful 2FA verification. path:%s.", tokenFile);
            logger.warn(msg);
        }
    }

    public Path getTokenFile() {
        return tokenFile;
    }
}
