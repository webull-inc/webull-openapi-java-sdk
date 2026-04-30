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
 * Stock information from screener API (gainers/losers/most active).
 */
public class ScreenerStock {

    private String instrumentId;
    private String symbol;
    private String name;
    private String exchangeCode;
    private String currencyCode;
    private String preClose;
    private String open;
    private String high;
    private String low;
    private String close;
    private String price;
    private String change;
    private String changePercent;
    private String volume;
    private String turnoverAmount;
    private String turnoverRate;
    private String marketValue;
    private String amplitude;
    private String relativeVolume10d;
    private String peTtm;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
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

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(String turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getRelativeVolume10d() {
        return relativeVolume10d;
    }

    public void setRelativeVolume10d(String relativeVolume10d) {
        this.relativeVolume10d = relativeVolume10d;
    }

    public String getPeTtm() {
        return peTtm;
    }

    public void setPeTtm(String peTtm) {
        this.peTtm = peTtm;
    }

    @Override
    public String toString() {
        return "ScreenerStock{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", preClose='" + preClose + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", price='" + price + '\'' +
                ", change='" + change + '\'' +
                ", changePercent='" + changePercent + '\'' +
                ", volume='" + volume + '\'' +
                ", turnoverAmount='" + turnoverAmount + '\'' +
                ", turnoverRate='" + turnoverRate + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", amplitude='" + amplitude + '\'' +
                ", relativeVolume10d='" + relativeVolume10d + '\'' +
                ", peTtm='" + peTtm + '\'' +
                '}';
    }
}
