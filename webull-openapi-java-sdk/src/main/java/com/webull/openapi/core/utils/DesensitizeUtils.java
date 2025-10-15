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

public class DesensitizeUtils {

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

    public static String desensitizeToken(String token) {
        return desensitize(token, 6, 6);
    }

}
