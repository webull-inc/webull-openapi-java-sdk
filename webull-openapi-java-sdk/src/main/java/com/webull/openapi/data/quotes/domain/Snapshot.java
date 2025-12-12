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

public class Snapshot extends QuotesBasic {

    private String tradeTime;
    private String price;
    private String open;
    private String high;
    private String low;
    private String preClose;
    private String volume;
    private String change;
    private String changeRatio;
    private String close;
    private Long lastTradeTime;
    private String ovnPrice;
    private String ovnHigh;
    private String ovnLow;
    private String ovnVolume;
    private String ovnChange;
    private String ovnChangeRatio;
    private Long ovnLastTradeTime;
    private String extendHourChange;
    private String extendHourChangeRatio;
    private String extendHourVolume;
    private String extendHourLastPrice;
    private Long extendHourLastTradeTime;
    private String tradingSession;
    private String openInterest;
    private Long quoteTime;
    private String bid;
    private String ask;
    private String askSize;
    private String bidSize;
    private String settleDate;
    private String settlePrice;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public Long getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Long lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getExtendHourLastPrice() {
        return extendHourLastPrice;
    }

    public void setExtendHourLastPrice(String extendHourLastPrice) {
        this.extendHourLastPrice = extendHourLastPrice;
    }

    public Long getExtendHourLastTradeTime() {
        return extendHourLastTradeTime;
    }

    public void setExtendHourLastTradeTime(Long extendHourLastTradeTime) {
        this.extendHourLastTradeTime = extendHourLastTradeTime;
    }

    public String getOvnPrice() {
        return ovnPrice;
    }

    public void setOvnPrice(String ovnPrice) {
        this.ovnPrice = ovnPrice;
    }

    public String getOvnHigh() {
        return ovnHigh;
    }

    public void setOvnHigh(String ovnHigh) {
        this.ovnHigh = ovnHigh;
    }

    public String getOvnLow() {
        return ovnLow;
    }

    public void setOvnLow(String ovnLow) {
        this.ovnLow = ovnLow;
    }

    public String getOvnVolume() {
        return ovnVolume;
    }

    public void setOvnVolume(String ovnVolume) {
        this.ovnVolume = ovnVolume;
    }

    public String getOvnChange() {
        return ovnChange;
    }

    public void setOvnChange(String ovnChange) {
        this.ovnChange = ovnChange;
    }

    public String getOvnChangeRatio() {
        return ovnChangeRatio;
    }

    public void setOvnChangeRatio(String ovnChangeRatio) {
        this.ovnChangeRatio = ovnChangeRatio;
    }

    public Long getOvnLastTradeTime() {
        return ovnLastTradeTime;
    }

    public void setOvnLastTradeTime(Long ovnLastTradeTime) {
        this.ovnLastTradeTime = ovnLastTradeTime;
    }

    public String getExtendHourChange() {
        return extendHourChange;
    }

    public void setExtendHourChange(String extendHourChange) {
        this.extendHourChange = extendHourChange;
    }

    public String getExtendHourChangeRatio() {
        return extendHourChangeRatio;
    }

    public void setExtendHourChangeRatio(String extendHourChangeRatio) {
        this.extendHourChangeRatio = extendHourChangeRatio;
    }

    public String getExtendHourVolume() {
        return extendHourVolume;
    }

    public void setExtendHourVolume(String extendHourVolume) {
        this.extendHourVolume = extendHourVolume;
    }

    public String getTradingSession() {
        return tradingSession;
    }

    public void setTradingSession(String tradingSession) {
        this.tradingSession = tradingSession;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(String openInterest) {
        this.openInterest = openInterest;
    }

    public Long getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Long quoteTime) {
        this.quoteTime = quoteTime;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAskSize() {
        return askSize;
    }

    public void setAskSize(String askSize) {
        this.askSize = askSize;
    }

    public String getBidSize() {
        return bidSize;
    }

    public void setBidSize(String bidSize) {
        this.bidSize = bidSize;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "tradeTime='" + tradeTime + '\'' +
                ", price='" + price + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", preClose='" + preClose + '\'' +
                ", volume='" + volume + '\'' +
                ", change='" + change + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", close='" + close + '\'' +
                ", lastTradeTime='" + lastTradeTime + '\'' +
                ", extendHourLastPrice='" + extendHourLastPrice + '\'' +
                ", extendHourLastTradeTime='" + extendHourLastTradeTime + '\'' +
                ", ovnHigh='" + ovnHigh + '\'' +
                ", ovnLow='" + ovnLow + '\'' +
                ", ovnVolume='" + ovnVolume + '\'' +
                ", ovnChange='" + ovnChange + '\'' +
                ", ovnChangeRatio='" + ovnChangeRatio + '\'' +
                ", ovnLastTradeTime='" + ovnLastTradeTime + '\'' +
                ", extendHourChange='" + extendHourChange + '\'' +
                ", extendHourChangeRatio='" + extendHourChangeRatio + '\'' +
                ", extendHourVolume='" + extendHourVolume + '\'' +
                ", tradingSession='" + tradingSession + '\'' +
                ", openInterest='" + openInterest + '\'' +
                ", quoteTime='" + quoteTime + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                ", askSize='" + askSize + '\'' +
                ", bidSize='" + bidSize + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", settlePrice='" + settlePrice + '\'' +
                '}';
    }
}
