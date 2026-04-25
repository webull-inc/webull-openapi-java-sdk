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
package com.webull.openapi.trade.response.v2;

import com.webull.openapi.trade.response.CommonPositionInfo;

import java.util.List;


public class AccountPositionsInfo {

    private String positionId;

    private String currency;

    private String symbol;

    private String symbolName;

    private String optionStrategy;

    private String quantity;

    private String availableQuantity;

    private String costPrice;

    private String lastPrice;

    private String unrealizedProfitLoss;

    private String unrealizedProfitLossRate;

    private String instrumentId;

    private String instrumentType;

    private String marketValue;

    private String dayRealizedProfitLoss;

    private String proportion;

    private String name;

    private String accountTaxType;

    private String baseCurrency;

    private String fxRate;

    private String baseCurrencyMarketValue;

    private String exchangeCode;

    private List<CommonPositionInfo> legs;

    @Deprecated
    private List<CommonPositionInfo> items;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOptionStrategy() {
        return optionStrategy;
    }

    public void setOptionStrategy(String optionStrategy) {
        this.optionStrategy = optionStrategy;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(String unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public String getUnrealizedProfitLossRate() {
        return unrealizedProfitLossRate;
    }

    public void setUnrealizedProfitLossRate(String unrealizedProfitLossRate) {
        this.unrealizedProfitLossRate = unrealizedProfitLossRate;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getDayRealizedProfitLoss() {
        return dayRealizedProfitLoss;
    }

    public void setDayRealizedProfitLoss(String dayRealizedProfitLoss) {
        this.dayRealizedProfitLoss = dayRealizedProfitLoss;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public List<CommonPositionInfo> getLegs() {
        return legs;
    }

    public void setLegs(List<CommonPositionInfo> legs) {
        this.legs = legs;
    }

    @Deprecated
    public List<CommonPositionInfo> getItems() {
        return items;
    }

    @Deprecated
    public void setItems(List<CommonPositionInfo> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
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
        return "AccountPositionsInfo{" +
                "positionId='" + positionId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", name='" + name + '\'' +
                ", optionStrategy='" + optionStrategy + '\'' +
                ", currency='" + currency + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", availableQuantity='" + availableQuantity + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", unrealizedProfitLossRate='" + unrealizedProfitLossRate + '\'' +
                ", dayRealizedProfitLoss='" + dayRealizedProfitLoss + '\'' +
                ", proportion='" + proportion + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", fxRate='" + fxRate + '\'' +
                ", baseCurrencyMarketValue='" + baseCurrencyMarketValue + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", legs=" + legs +
                '}';
    }

}
