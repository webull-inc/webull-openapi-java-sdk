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
 * Market sector detail response.
 */
public class MarketSectorDetail {

    /** Sector ID */
    private String id;
    /** Sector name */
    private String name;
    /** Price change ratio relative to previous close */
    private String changeRatio;
    /** Number of stocks that have fallen */
    private String declined;
    /** Number of stocks that have risen */
    private String advanced;
    /** Number of stocks with a price change of 0 */
    private String flat;
    /** List of stocks in the industry */
    private List<MarketSectorStock> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChangeRatio() {
        return changeRatio;
    }

    public void setChangeRatio(String changeRatio) {
        this.changeRatio = changeRatio;
    }

    public String getDeclined() {
        return declined;
    }

    public void setDeclined(String declined) {
        this.declined = declined;
    }

    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public List<MarketSectorStock> getData() {
        return data;
    }

    public void setData(List<MarketSectorStock> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarketSectorDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", declined='" + declined + '\'' +
                ", advanced='" + advanced + '\'' +
                ", flat='" + flat + '\'' +
                ", data=" + data +
                '}';
    }
}
