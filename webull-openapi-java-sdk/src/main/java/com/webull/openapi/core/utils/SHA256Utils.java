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
package com.webull.openapi.core.utils;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 Digest Utility Class
 * Provides digest calculation functionality based on the SHA256 algorithm
 */
public final class SHA256Utils {

    private static final String SHA256 = "SHA-256";

    private SHA256Utils() {
    }

    // 将字节数组转换为16进制字符串
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String sha256(String body) {
        try {
            // 获取 SHA-256 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 计算 SHA-256
            byte[] hash = digest.digest(body.getBytes(StandardCharsets.UTF_8));

            // 转成十六进制字符串返回
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is not available", e);
        }
    }

    public static String sha256(byte[] body) {
        try {
            // 获取 SHA-256 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 计算 SHA-256
            byte[] hash = digest.digest(body);

            // 转成十六进制字符串返回
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is not available", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(sha256("123456"));
    }
}