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
 * NOII (Net Order Imbalance Indicator) bar data.
 * Provides imbalance information during NASDAQ opening/closing auctions.
 */
public class NoiiBar {

    /**
     * Instrument unique identifier
     */
    private String instrumentId;

    /**
     * Security symbol
     */
    private String symbol;

    /**
     * Imbalance data timestamp (milliseconds)
     */
    private Long imbalanceTime;

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

    public Long getImbalanceTime() {
        return imbalanceTime;
    }

    public void setImbalanceTime(Long imbalanceTime) {
        this.imbalanceTime = imbalanceTime;
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

    @Override
    public String toString() {
        return "NoiiBar{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", imbalanceTime=" + imbalanceTime +
                ", imbalanceRefPrice='" + imbalanceRefPrice + '\'' +
                ", imbalanceNearPrice='" + imbalanceNearPrice + '\'' +
                ", imbalanceFarPrice='" + imbalanceFarPrice + '\'' +
                ", imbalanceActionType='" + imbalanceActionType + '\'' +
                '}';
    }
}
