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
 * Option  snapshot data.
 * Provides the latest Option  data during NASDAQ opening/closing auctions.
 * Updated every 5 seconds during auction periods:
 * - Opening auction: 9:28 - 9:30 AM ET (2 minutes)
 * - Closing auction: 3:50 - 4:00 PM ET (10 minutes)
 */
public class OptionSnapshot extends QuotesBasic {

    /** Latest trade price */
    @SerializedName("price")
    private String price;

    /** Opening price */
    @SerializedName("open")
    private String open;

    /** Highest price */
    @SerializedName("high")
    private String high;

    /** Lowest price */
    @SerializedName("low")
    private String low;

    /** Closing price */
    @SerializedName("close")
    private String close;

    /** Previous closing price */
    @SerializedName("pre_close")
    private String preClose;

    /** Trading volume */
    @SerializedName("volume")
    private String volume;

    /** Price change */
    @SerializedName("change")
    private String change;

    /** Price change ratio */
    @SerializedName("change_ratio")
    private String changeRatio;

    /** Last trade time (timestamp in milliseconds) */
    @SerializedName("last_trade_time")
    private String lastTradeTime;

    /** Quote time (timestamp in milliseconds) */
    @SerializedName("quote_time")
    private String quoteTime;

    /** Best bid price */
    @SerializedName("bid")
    private String bid;

    /** Best ask price */
    @SerializedName("ask")
    private String ask;

    /** Best bid size */
    @SerializedName("bid_size")
    private String bidSize;

    /** Best ask size */
    @SerializedName("ask_size")
    private String askSize;

    /** Deal amount (turnover) */
    @SerializedName("deal_amount")
    private String dealAmount;

    /** Strike price of the option */
    @SerializedName("strike_price")
    private String strikePrice;

    /** Implied volatility */
    @SerializedName("imp_vol")
    private String impVol;

    /** Open interest */
    @SerializedName("open_interest")
    private String openInterest;

    /** Greek: gamma - rate of change of delta */
    @SerializedName("gamma")
    private String gamma;

    /** Greek: delta - sensitivity to underlying price */
    @SerializedName("delta")
    private String delta;

    /** Greek: theta - sensitivity to time decay */
    @SerializedName("theta")
    private String theta;

    /** Greek: vega - sensitivity to volatility */
    @SerializedName("vega")
    private String vega;

    /** Greek: rho - sensitivity to interest rate */
    @SerializedName("rho")
    private String rho;

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

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
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

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(String quoteTime) {
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

    public String getBidSize() {
        return bidSize;
    }

    public void setBidSize(String bidSize) {
        this.bidSize = bidSize;
    }

    public String getAskSize() {
        return askSize;
    }

    public void setAskSize(String askSize) {
        this.askSize = askSize;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getImpVol() {
        return impVol;
    }

    public void setImpVol(String impVol) {
        this.impVol = impVol;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(String openInterest) {
        this.openInterest = openInterest;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getTheta() {
        return theta;
    }

    public void setTheta(String theta) {
        this.theta = theta;
    }

    public String getVega() {
        return vega;
    }

    public void setVega(String vega) {
        this.vega = vega;
    }

    public String getRho() {
        return rho;
    }

    public void setRho(String rho) {
        this.rho = rho;
    }

    @Override
    public String toString() {
        return "OptionSnapshot{" +
                "symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", price='" + price + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", preClose='" + preClose + '\'' +
                ", volume='" + volume + '\'' +
                ", change='" + change + '\'' +
                ", changeRatio='" + changeRatio + '\'' +
                ", lastTradeTime='" + lastTradeTime + '\'' +
                ", quoteTime='" + quoteTime + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                ", bidSize='" + bidSize + '\'' +
                ", askSize='" + askSize + '\'' +
                ", dealAmount='" + dealAmount + '\'' +
                ", strikePrice='" + strikePrice + '\'' +
                ", impVol='" + impVol + '\'' +
                ", openInterest='" + openInterest + '\'' +
                ", gamma='" + gamma + '\'' +
                ", delta='" + delta + '\'' +
                ", theta='" + theta + '\'' +
                ", vega='" + vega + '\'' +
                ", rho='" + rho + '\'' +
                '}';
    }
}
