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
 * Response from screener API (gainers/losers/most active).
 */
public class ScreenerResponse {

    private Boolean hasMore;
    private List<ScreenerStock> data;

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ScreenerStock> getData() {
        return data;
    }

    public void setData(List<ScreenerStock> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ScreenerResponse{" +
                "hasMore=" + hasMore +
                ", data=" + data +
                '}';
    }
}
