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

import com.webull.openapi.trade.response.CommonPositionInfo;

import java.io.Serializable;
import java.util.List;

public class AccountPositionsInfo implements Serializable {

    private static final long serialVersionUID = -4070295270850453418L;
    private String positionId;
    private String currency;
    private String symbol;
    private String optionStrategy;
    private String quantity;
    private String costPrice;
    private String lastPrice;
    private String unrealizedProfitLoss;
    private String unrealizedProfitLossRate;
    private String instrumentType;
    private String marketValue;
    private String dayRealizedProfitLoss;
    private String proportion;
    private String eventOutcome;
    private List<CommonPositionInfo> legs;

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

    public String getEventOutcome() {
        return eventOutcome;
    }

    public void setEventOutcome(String eventOutcome) {
        this.eventOutcome = eventOutcome;
    }

    public List<CommonPositionInfo> getLegs() {
        return legs;
    }

    public void setLegs(List<CommonPositionInfo> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "AccountPositionsInfo{" +
                "positionId='" + positionId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", optionStrategy='" + optionStrategy + '\'' +
                ", currency='" + currency + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", unrealizedProfitLossRate='" + unrealizedProfitLossRate + '\'' +
                ", dayRealizedProfitLoss='" + dayRealizedProfitLoss + '\'' +
                ", proportion='" + proportion + '\'' +
                ", eventOutcome='" + eventOutcome + '\'' +
                ", legs=" + legs +
                '}';
    }

}
