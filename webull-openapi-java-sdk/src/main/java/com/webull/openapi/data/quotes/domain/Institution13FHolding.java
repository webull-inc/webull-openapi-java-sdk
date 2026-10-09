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

import com.google.gson.annotations.SerializedName;

/**
 * A single 13F holding line of an institution (US only).
 */
public class Institution13FHolding {

    private String instrumentId;
    private String symbol;
    private String shares;
    private String marketValue;
    private String weight;
    private String sharesChange;
    private String sharesChangeRatio;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSharesChange() {
        return sharesChange;
    }

    public void setSharesChange(String sharesChange) {
        this.sharesChange = sharesChange;
    }

    public String getSharesChangeRatio() {
        return sharesChangeRatio;
    }

    public void setSharesChangeRatio(String sharesChangeRatio) {
        this.sharesChangeRatio = sharesChangeRatio;
    }

    @Override
    public String toString() {
        return "Institution13FHolding{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", shares='" + shares + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", weight='" + weight + '\'' +
                ", sharesChange='" + sharesChange + '\'' +
                ", sharesChangeRatio='" + sharesChangeRatio + '\'' +
                '}';
    }
}
