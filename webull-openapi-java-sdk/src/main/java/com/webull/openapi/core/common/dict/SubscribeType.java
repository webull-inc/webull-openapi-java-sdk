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
package com.webull.openapi.core.common.dict;

import com.webull.openapi.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SubscribeType {

    /**
     * Order Book
     */
    QUOTE(0, "quote"),

    /**
     * Market Snapshot
     */
    SNAPSHOT(1, "snapshot"),

    /**
     * Tick-by-Tick
     */
    TICK(2, "tick");

    private final int code;
    private final String type;

    SubscribeType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    private static final SubscribeType[] items = new SubscribeType[SubscribeType.values().length];
    private static Map<String, SubscribeType> typeItemMap = new HashMap<>();

    static {
        for (SubscribeType item : values()) {
            items[item.getCode()] = item;
            typeItemMap.put(item.getType(), item);
        }
    }

    public static Optional<SubscribeType> fromCode(int code) {
        if (code < 0 || code > items.length - 1) {
            return Optional.empty();
        }
        return Optional.ofNullable(items[code]);
    }

    public static Optional<SubscribeType> fromType(String type) {
        if (StringUtils.isBlank(type)) {
            return Optional.empty();
        }
        return Optional.ofNullable(typeItemMap.get(type));
    }
}
