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
package com.webull.openapi.trade.response;


public class CommonPositionInfo {

    private String itemId;

    private String side;

    private String quantity;

    private String availableQuantity;

    private String symbol;

    private String costPrice;

    private String lastPrice;

    private String unrealizedProfitLoss;

    private String accountTaxType;

    private String optionType;

    private String optionCategory;

    private String optionExpireDate;

    private String optionExercisePrice;

    private String optionContractMultiplier;

    private String optionContractDeliverable;

    private String instrumentType;

    private String marketValue;

    private String dayProfitLoss;

    private String dayRealizedProfitLoss;

    private String expirationType;

    private String proportion;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(String unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
    }

    public String getOptionExpireDate() {
        return optionExpireDate;
    }

    public void setOptionExpireDate(String optionExpireDate) {
        this.optionExpireDate = optionExpireDate;
    }

    public String getOptionExercisePrice() {
        return optionExercisePrice;
    }

    public void setOptionExercisePrice(String optionExercisePrice) {
        this.optionExercisePrice = optionExercisePrice;
    }

    public String getOptionContractMultiplier() {
        return optionContractMultiplier;
    }

    public void setOptionContractMultiplier(String optionContractMultiplier) {
        this.optionContractMultiplier = optionContractMultiplier;
    }

    public String getOptionContractDeliverable() {
        return optionContractDeliverable;
    }

    public void setOptionContractDeliverable(String optionContractDeliverable) {
        this.optionContractDeliverable = optionContractDeliverable;
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

    public String getDayProfitLoss() {
        return dayProfitLoss;
    }

    public void setDayProfitLoss(String dayProfitLoss) {
        this.dayProfitLoss = dayProfitLoss;
    }

    public String getDayRealizedProfitLoss() {
        return dayRealizedProfitLoss;
    }

    public void setDayRealizedProfitLoss(String dayRealizedProfitLoss) {
        this.dayRealizedProfitLoss = dayRealizedProfitLoss;
    }

    public String getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(String expirationType) {
        this.expirationType = expirationType;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    @Override
    public String toString() {
        return "CommonPositionInfo{" +
                "itemId='" + itemId + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", side='" + side + '\'' +
                ", quantity='" + quantity + '\'' +
                ", availableQuantity='" + availableQuantity + '\'' +
                ", symbol='" + symbol + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", dayProfitLoss='" + dayProfitLoss + '\'' +
                ", dayRealizedProfitLoss='" + dayRealizedProfitLoss + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", optionType='" + optionType + '\'' +
                ", optionCategory='" + optionCategory + '\'' +
                ", optionExpireDate='" + optionExpireDate + '\'' +
                ", optionExercisePrice='" + optionExercisePrice + '\'' +
                ", optionContractMultiplier='" + optionContractMultiplier + '\'' +
                ", optionContractDeliverable='" + optionContractDeliverable + '\'' +
                ", expirationType='" + expirationType + '\'' +
                ", proportion='" + proportion + '\'' +
                '}';
    }
}
