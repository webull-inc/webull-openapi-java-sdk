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
 * Market sector entry.
 */
public class MarketSector {

    /** Sector ID */
    private String id;
    /** Sector name */
    private String name;
    /** Price change ratio relative to previous close */
    private String changeRatio;
    /** Trading volume within the current statistical period */
    private String volume;
    /** Market value within the current statistical period */
    private String marketValue;
    /** Number of stocks that have fallen */
    private String declined;
    /** Number of stocks that have risen */
    private String advanced;
    /** Number of stocks with a price change of 0 */
    private String flat;
    /** Leading stocks in the industry */
    private List<LeadingStock> data;

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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
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

    public List<LeadingStock> getData() {
        return data;
    }

    public void setData(List<LeadingStock> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarketSector{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", volume='" + volume + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", declined='" + declined + '\'' +
                ", advanced='" + advanced + '\'' +
                ", flat='" + flat + '\'' +
                ", data=" + data +
                '}';
    }
}
