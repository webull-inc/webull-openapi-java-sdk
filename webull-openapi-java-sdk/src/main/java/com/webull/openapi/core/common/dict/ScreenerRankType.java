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
 * Rank type for gainers/losers screener API.
 * Time period for ranking by price change percentage.
 */
public enum ScreenerRankType {

    /**
     * Pre-market session
     */
    PRE_MARKET,

    /**
     * After-market session
     */
    AFTER_MARKET,

    /**
     * 3 minutes
     */
    MIN_3,

    /**
     * 5 minutes
     */
    MIN_5,

    /**
     * 1 day
     */
    DAY_1,

    /**
     * 5 days
     */
    DAY_5,

    /**
     * 1 month
     */
    MONTH_1,

    /**
     * 3 months
     */
    MONTH_3,

    /**
     * 52 weeks
     */
    WEEK_52
}
