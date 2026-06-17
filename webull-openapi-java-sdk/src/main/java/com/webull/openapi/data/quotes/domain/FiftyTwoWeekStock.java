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
 * 52-week high/low stock entry.
 */
public class FiftyTwoWeekStock {

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
    /** Ticker symbol */
    private String tickerSymbol;
    /** Exchange code */
    private String exchangeCode;
    /** Latest intraday price for the current trading day */
    private String close;
    /** Trade change for the current trading day */
    private String change;
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
    /** Today's high */
    private String high;
    /** Today's low */
    private String low;
    /** Transaction amount */
    private String turnover;
    /** Amplitude ratio */
    private String amplitude;
    /** This week high/low price */
    @SerializedName("price_1w")
    private String price1w;
    /** 52W high/low price */
    @SerializedName("price_52w")
    private String price52w;
    /** The change ratio since the previous highest/lowest change */
    @SerializedName("change_ratio_52w")
    private String changeRatio52w;
    /** PE TTM */
    private String peTtm;

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

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
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

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
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

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getPrice1w() {
        return price1w;
    }

    public void setPrice1w(String price1w) {
        this.price1w = price1w;
    }

    public String getPrice52w() {
        return price52w;
    }

    public void setPrice52w(String price52w) {
        this.price52w = price52w;
    }

    public String getChangeRatio52w() {
        return changeRatio52w;
    }

    public void setChangeRatio52w(String changeRatio52w) {
        this.changeRatio52w = changeRatio52w;
    }

    public String getPeTtm() {
        return peTtm;
    }

    public void setPeTtm(String peTtm) {
        this.peTtm = peTtm;
    }

    @Override
    public String toString() {
        return "FiftyTwoWeekStock{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", price52w='" + price52w + '\'' +
                ", changeRatio52w='" + changeRatio52w + '\'' +
                ", marketValue='" + marketValue + '\'' +
                '}';
    }
}
