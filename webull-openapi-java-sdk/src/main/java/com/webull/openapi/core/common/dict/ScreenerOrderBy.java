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

/**
 * Secondary sort field for screener API.
 */
public enum ScreenerOrderBy {

    /**
     * Price change percentage
     */
    CHANGE_RATIO,

    /**
     * 10-day relative volume
     */
    RELATIVE_VOLUME_10D,

    /**
     * Market capitalization
     */
    MARKET_VALUE,

    /**
     * Closing price
     */
    CLOSE,

    /**
     * Current price
     */
    PRICE,

    /**
     * Trailing twelve months P/E ratio
     */
    PE_TTM,

    /**
     * Intraday high
     */
    HIGH,

    /**
     * Intraday low
     */
    LOW,

    /**
     * Price amplitude
     */
    AMPLITUDE,

    /**
     * Turnover amount
     */
    TURNOVER,

    /**
     * Trading volume
     */
    VOLUME
}
