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
package com.webull.openapi.trade.response.v3;

import java.io.Serializable;

public class AccountPositionDetailsInfo implements Serializable {

    private static final long serialVersionUID = -4070295270850453418L;

    private String id;
    private String accountId;
    private String quantity;
    private String marketValue;
    private String symbol;
    private String symbolName;
    private String currency;
    private String accountTaxType;
    private String instrumentId;
    private String contractId;
    private String holdType;
    private String marginType;
    private String averagePrice;
    private String unrealizedPl;
    private String baseCurrency;
    private String fxRate;
    private String baseCurrencyMarketValue;
    private String exchangeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getUnrealizedPl() {
        return unrealizedPl;
    }

    public void setUnrealizedPl(String unrealizedPl) {
        this.unrealizedPl = unrealizedPl;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getFxRate() {
        return fxRate;
    }

    public void setFxRate(String fxRate) {
        this.fxRate = fxRate;
    }

    public String getBaseCurrencyMarketValue() {
        return baseCurrencyMarketValue;
    }

    public void setBaseCurrencyMarketValue(String baseCurrencyMarketValue) {
        this.baseCurrencyMarketValue = baseCurrencyMarketValue;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @Override
    public String toString() {
        return "AccountPositionDetailsInfo{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", currency='" + currency + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", holdType='" + holdType + '\'' +
                ", marginType='" + marginType + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                ", unrealizedPl='" + unrealizedPl + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", fxRate='" + fxRate + '\'' +
                ", baseCurrencyMarketValue='" + baseCurrencyMarketValue + '\'' +
                ", exchangeCode=" + exchangeCode +
                '}';
    }

}
