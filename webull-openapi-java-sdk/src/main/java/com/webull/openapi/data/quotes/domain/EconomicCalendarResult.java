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
package com.webull.openapi.data.quotes.domain;

import java.util.List;

/**
 * Response of the macro economic calendar API.
 */
public class EconomicCalendarResult {

    private String paginationKey;
    private List<EconomicCalendarEvent> data;

    /**
     * Returns the pagination key for fetching the next page.
     * Null indicates no more pages available.
     */
    public String getPaginationKey() {
        return paginationKey;
    }

    public void setPaginationKey(String paginationKey) {
        this.paginationKey = paginationKey;
    }

    public List<EconomicCalendarEvent> getData() {
        return data;
    }

    public void setData(List<EconomicCalendarEvent> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EconomicCalendarResult{" +
                "paginationKey='" + paginationKey + '\'' +
                ", data=" + data +
                '}';
    }
}
