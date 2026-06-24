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

/**
 * Stock entry in market sector detail.
 */
public class MarketSectorStock {

    /** Security ID */
    private String instrumentId;
    /** Security category */
    private String category;
    /** Currency code */
    private String currency;
    /** Security name */
    private String name;
    /** Security symbol */
    private String symbol;
    /** Exchange code */
    private String exchangeCode;
    /** Latest intraday price for the current trading day */
    private String close;
    /** Price change ratio relative to previous close */
    private String changeRatio;
    /** The latest price for the current trading day */
    private String price;
    /** Trade volume */
    private String volume;
    /** Market value */
    private String marketValue;
    /** Turnover rate */
    private String turnoverRate;
    /** Amplitude ratio */
    private String amplitude;
    /** Today's high */
    private String high;
    /** Today's low */
    private String low;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getChangeRatio() {
        return changeRatio;
    }

    public void setChangeRatio(String changeRatio) {
        this.changeRatio = changeRatio;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    @Override
    public String toString() {
        return "MarketSectorStock{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", marketValue='" + marketValue + '\'' +
                '}';
    }
}
