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

public class NOrderItemLeg {

    private String id;
    private String quantity;
    private String side;
    private String market;
    private String instrumentType;
    private String symbol;
    private String optionType;
    private String optionExpireDate;
    private String strikePrice;
    private String optionCategory;
    private String optionContractMultiplier;
    private String optionContractDeliverable;
    private String expirationType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptionExpireDate() {
        return optionExpireDate;
    }

    public void setOptionExpireDate(String optionExpireDate) {
        this.optionExpireDate = optionExpireDate;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
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

    public String getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(String expirationType) {
        this.expirationType = expirationType;
    }

    @Override
    public String toString() {
        return "NOrderItemLeg{" +
                "id='" + id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", side='" + side + '\'' +
                ", market='" + market + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", optionType=" + optionType +
                ", optionExpireDate=" + optionExpireDate +
                ", strikePrice='" + strikePrice + '\'' +
                ", optionCategory='" + optionCategory + '\'' +
                ", optionContractMultiplier='" + optionContractMultiplier + '\'' +
                ", optionContractDeliverable='" + optionContractDeliverable + '\'' +
                ", expirationType='" + expirationType + '\'' +
                '}';
    }

}
