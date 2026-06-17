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
 * Dividend calendar entry for a stock.
 */
public class DividendCalendar {

    /**
     * Security symbol
     */
    private String symbol;

    /**
     * Market (e.g., US, HK, CN, JP)
     */
    private String market;

    /**
     * Currency code (e.g., USD)
     */
    private String currency;

    /**
     * Dividend amount per share
     */
    private String amount;

    /**
     * Dividend type (e.g., CASH_DIVIDEND, STOCK_DIVIDEND, SCRIP_DIVIDEND,
     * RETURN_OF_CAPITAL, INTEREST_PRINCIPAL_PAYMENT, DIVIDEND_REINVESTMENT,
     * TRUST_INCOME_DISTRIBUTION)
     */
    private String divType;

    /**
     * Declaration date in YYYY-MM-DD format
     */
    private String declareDate;

    /**
     * Ex-dividend date in YYYY-MM-DD format
     */
    private String exDivDate;

    /**
     * Record date in YYYY-MM-DD format
     */
    private String recordDate;

    /**
     * Payment date in YYYY-MM-DD format
     */
    private String payDate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDivType() {
        return divType;
    }

    public void setDivType(String divType) {
        this.divType = divType;
    }

    public String getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(String declareDate) {
        this.declareDate = declareDate;
    }

    public String getExDivDate() {
        return exDivDate;
    }

    public void setExDivDate(String exDivDate) {
        this.exDivDate = exDivDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "DividendCalendar{" +
                "symbol='" + symbol + '\'' +
                ", market='" + market + '\'' +
                ", currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                ", divType='" + divType + '\'' +
                ", declareDate='" + declareDate + '\'' +
                ", exDivDate='" + exDivDate + '\'' +
                ", recordDate='" + recordDate + '\'' +
                ", payDate='" + payDate + '\'' +
                '}';
    }
}
