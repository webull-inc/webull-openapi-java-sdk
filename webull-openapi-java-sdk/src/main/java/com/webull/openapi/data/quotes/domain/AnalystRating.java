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
 * Analyst rating information for a security.
 */
public class AnalystRating {

    /**
     * Security symbol
     */
    private String symbol;

    /**
     * Security type
     */
    private String category;

    /**
     * Total number of analysts
     */
    private String number;

    /**
     * Under perform count
     */
    private String underPerform;

    /**
     * Buy count
     */
    private String buy;

    /**
     * Sell count
     */
    private String sell;

    /**
     * Strong buy count
     */
    private String strongBuy;

    /**
     * Hold (neutral) count
     */
    private String hold;

    /**
     * Effective start date
     */
    private String effectiveStartDate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnderPerform() {
        return underPerform;
    }

    public void setUnderPerform(String underPerform) {
        this.underPerform = underPerform;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getStrongBuy() {
        return strongBuy;
    }

    public void setStrongBuy(String strongBuy) {
        this.strongBuy = strongBuy;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }

    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    @Override
    public String toString() {
        return "AnalystRating{" +
                "symbol='" + symbol + '\'' +
                ", category='" + category + '\'' +
                ", number='" + number + '\'' +
                ", underPerform='" + underPerform + '\'' +
                ", buy='" + buy + '\'' +
                ", sell='" + sell + '\'' +
                ", strongBuy='" + strongBuy + '\'' +
                ", hold='" + hold + '\'' +
                ", effectiveStartDate='" + effectiveStartDate + '\'' +
                '}';
    }
}
