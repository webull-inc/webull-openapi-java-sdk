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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DesensitizeUtils {

    private static final String MASK = "******";

    private static final Set<String> TOKEN_KEYS = new HashSet<>(Arrays.asList(
            "token", "access_token", "accesstoken", "x-access-token", "refresh_token", "refreshtoken"));

    private static final Set<String> SECRET_KEYS = new HashSet<>(Arrays.asList(
            "app_key", "appkey", "x-app-key", "app_secret", "appsecret", "secret_key", "secretkey",
            "secret", "signature", "x-signature", "authorization"));

    // Matches "key":"value" and the escaped form \\"key\\":\\"value\\" used by JSON nested in a JSON string.
    private static final Pattern SENSITIVE_JSON_PATTERN = Pattern.compile(
            "((\\\\{0,2}\")([A-Za-z_\\-]+)\\2\\s*:\\s*\\2)([^\"\\\\]*)(\\2)");

    // Matches unquoted pairs such as token=abc or x-app-key: abc in plain text bodies and query strings.
    private static final Pattern SENSITIVE_PLAIN_PATTERN = buildPlainPattern();

    private static Pattern buildPlainPattern() {
        List<String> keys = new ArrayList<>(TOKEN_KEYS);
        keys.addAll(SECRET_KEYS);
        keys.sort((x, y) -> y.length() - x.length());
        StringBuilder sb = new StringBuilder("(?i)(?<![\\w-])((?:");
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(Pattern.quote(keys.get(i)));
        }
        sb.append(")\\s*[:=]\\s*)((?:(?:bearer|basic)\\s+)?[^\\s&,;\"'\\\\}\\])]+)");
        return Pattern.compile(sb.toString());
    }

    public static String desensitize(String content, int keepLength, int maskLength) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        int totalLength = content.length();

        if (totalLength <= keepLength * 2) {
            return content;
        }

        String frontPart = content.substring(0, Math.min(keepLength, totalLength));

        String rearPart = "";
        if (keepLength > 0 && totalLength >= keepLength) {
            rearPart = content.substring(totalLength - keepLength);
        }

        StringBuilder maskBuilder = new StringBuilder();
        for (int i = 0; i < maskLength; i++) {
            maskBuilder.append('*');
        }

        return frontPart + maskBuilder + rearPart;
    }

    /**
     * Keep only the first 6 characters of a token, e.g. {@code 28b9f3******}.
     */
    public static String desensitizeToken(String token) {
        if (token == null || token.isEmpty()) {
            return token;
        }
        if (token.length() <= 6) {
            return MASK;
        }
        return token.substring(0, 6) + MASK;
    }

    /**
     * Mask an app key, app secret or signature: first 4 + *** + last 4.
     * Values shorter than 12 characters are fully masked.
     */
    public static String desensitizeSecret(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        int length = value.length();
        if (length < 12) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append('*');
            }
            return sb.toString();
        }
        return value.substring(0, 4) + "***" + value.substring(length - 4);
    }

    private static String maskByKey(String key, String value) {
        String lowerKey = key.toLowerCase(Locale.ROOT);
        if (TOKEN_KEYS.contains(lowerKey)) {
            return desensitizeToken(value);
        }
        if (SECRET_KEYS.contains(lowerKey)) {
            return desensitizeSecret(value);
        }
        return value;
    }

    /**
     * Mask the values of sensitive fields (token, app key, app secret, signature) inside a JSON string.
     * Returns the input unchanged when it is null or empty.
     */
    public static String maskSensitiveJsonValues(String json) {
        if (json == null || json.isEmpty()) {
            return json;
        }
        try {
            Matcher matcher = SENSITIVE_JSON_PATTERN.matcher(json);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String masked = maskByKey(matcher.group(3), matcher.group(4));
                matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group(1) + masked + matcher.group(5)));
            }
            matcher.appendTail(sb);
            Matcher plain = SENSITIVE_PLAIN_PATTERN.matcher(sb.toString());
            StringBuffer result = new StringBuffer();
            while (plain.find()) {
                String key = plain.group(1).replaceAll("[\\s:=]", "");
                plain.appendReplacement(result,
                        Matcher.quoteReplacement(plain.group(1) + maskByKey(key, plain.group(2))));
            }
            plain.appendTail(result);
            return result.toString();
        } catch (Exception e) {
            // never leak raw content when masking fails
            return MASK;
        }
    }

    /**
     * Return a copy of the given headers with sensitive values (app key, signature, access token) masked.
     */
    public static Map<String, String> maskHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return headers;
        }
        Map<String, String> masked = new HashMap<>(headers);
        for (Map.Entry<String, String> entry : masked.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                entry.setValue(maskByKey(entry.getKey(), entry.getValue()));
            }
        }
        return masked;
    }

}
