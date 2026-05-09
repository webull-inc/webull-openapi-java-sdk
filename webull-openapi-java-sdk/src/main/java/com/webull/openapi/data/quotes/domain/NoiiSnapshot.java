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
 * NOII (Net Order Imbalance Indicator) snapshot data.
 * Provides the latest NOII data during NASDAQ opening/closing auctions.
 * Updated every 5 seconds during auction periods:
 * - Opening auction: 9:28 - 9:30 AM ET (2 minutes)
 * - Closing auction: 3:50 - 4:00 PM ET (10 minutes)
 */
public class NoiiSnapshot {

    /**
     * Instrument unique identifier
     */
    private String instrumentId;

    /**
     * Security symbol
     */
    private String symbol;

    /**
     * Paired shares (matched volume under current conditions)
     */
    private String pairedShares;

    /**
     * Imbalance shares (unmatched buy/sell order quantity)
     */
    private String imbalanceShares;

    /**
     * Imbalance side (direction of imbalance)
     */
    private String imbalanceSide;

    /**
     * Reference price
     */
    private String imbalanceRefPrice;

    /**
     * Indicative match price (current most likely execution price)
     */
    private String imbalanceNearPrice;

    /**
     * Far price (extreme case execution price)
     */
    private String imbalanceFarPrice;

    /**
     * Imbalance action type: PRE_OPEN (opening imbalance), PRE_CLOSE (closing imbalance)
     */
    private String imbalanceActionType;

    /**
     * Timestamp in milliseconds
     */
    private Long imbalanceTime;

    /**
     * Volatility/imbalance status indicator
     */
    private String imbalanceVarIndicator;

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

    public String getPairedShares() {
        return pairedShares;
    }

    public void setPairedShares(String pairedShares) {
        this.pairedShares = pairedShares;
    }

    public String getImbalanceShares() {
        return imbalanceShares;
    }

    public void setImbalanceShares(String imbalanceShares) {
        this.imbalanceShares = imbalanceShares;
    }

    public String getImbalanceSide() {
        return imbalanceSide;
    }

    public void setImbalanceSide(String imbalanceSide) {
        this.imbalanceSide = imbalanceSide;
    }

    public String getImbalanceRefPrice() {
        return imbalanceRefPrice;
    }

    public void setImbalanceRefPrice(String imbalanceRefPrice) {
        this.imbalanceRefPrice = imbalanceRefPrice;
    }

    public String getImbalanceNearPrice() {
        return imbalanceNearPrice;
    }

    public void setImbalanceNearPrice(String imbalanceNearPrice) {
        this.imbalanceNearPrice = imbalanceNearPrice;
    }

    public String getImbalanceFarPrice() {
        return imbalanceFarPrice;
    }

    public void setImbalanceFarPrice(String imbalanceFarPrice) {
        this.imbalanceFarPrice = imbalanceFarPrice;
    }

    public String getImbalanceActionType() {
        return imbalanceActionType;
    }

    public void setImbalanceActionType(String imbalanceActionType) {
        this.imbalanceActionType = imbalanceActionType;
    }

    public Long getImbalanceTime() {
        return imbalanceTime;
    }

    public void setImbalanceTime(Long imbalanceTime) {
        this.imbalanceTime = imbalanceTime;
    }

    public String getImbalanceVarIndicator() {
        return imbalanceVarIndicator;
    }

    public void setImbalanceVarIndicator(String imbalanceVarIndicator) {
        this.imbalanceVarIndicator = imbalanceVarIndicator;
    }

    @Override
    public String toString() {
        return "NoiiSnapshot{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", pairedShares='" + pairedShares + '\'' +
                ", imbalanceShares='" + imbalanceShares + '\'' +
                ", imbalanceSide='" + imbalanceSide + '\'' +
                ", imbalanceRefPrice='" + imbalanceRefPrice + '\'' +
                ", imbalanceNearPrice='" + imbalanceNearPrice + '\'' +
                ", imbalanceFarPrice='" + imbalanceFarPrice + '\'' +
                ", imbalanceActionType='" + imbalanceActionType + '\'' +
                ", imbalanceTime=" + imbalanceTime +
                ", imbalanceVarIndicator='" + imbalanceVarIndicator + '\'' +
                '}';
    }
}
