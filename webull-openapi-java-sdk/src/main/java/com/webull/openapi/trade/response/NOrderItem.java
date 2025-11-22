/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
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

import java.util.List;

public class NOrderItem {

    private String clientOrderId;
    private String comboOrderId;
    private String symbol;
    private String side;
    private String status;
    private String orderType;
    private String instrumentType;
    private String orderId;
    private String supportTradingSession;
    private String entrustType;
    private String totalQuantity;
    private String filledQuantity;
    private String remainQuantity;
    private String placeTime;
    private String placeTimeAt;
    private String optionStrategy;
    private String filledTime;
    private String filledTimeAt;
    private String filledPrice;
    private String timeInForce;
    private String limitPrice;
    private String stopPrice;
    private String trailingStopStep;
    private String trailingType;
    private String accountTaxType;
    private List<NOrderItemLeg> legs;

    @Deprecated
    private String quantity;
    @Deprecated
    private String comboType;
    @Deprecated
    private String optionType;
    @Deprecated
    private String optionExpireDate;
    @Deprecated
    private String optionExercisePrice;
    @Deprecated
    private String optionCategory;
    @Deprecated
    private String optionContractMultiplier;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }


    public String getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public String getFilledPrice() {
        return filledPrice;
    }

    public void setFilledPrice(String filledPrice) {
        this.filledPrice = filledPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getSupportTradingSession() {
        return supportTradingSession;
    }

    public void setSupportTradingSession(String supportTradingSession) {
        this.supportTradingSession = supportTradingSession;
    }

    public String getComboOrderId() {
        return comboOrderId;
    }

    public void setComboOrderId(String comboOrderId) {
        this.comboOrderId = comboOrderId;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(String remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(String placeTime) {
        this.placeTime = placeTime;
    }

    public String getFilledTime() {
        return filledTime;
    }

    public void setFilledTime(String filledTime) {
        this.filledTime = filledTime;
    }

    public String getPlaceTimeAt() {
        return placeTimeAt;
    }

    public void setPlaceTimeAt(String placeTimeAt) {
        this.placeTimeAt = placeTimeAt;
    }

    public String getOptionStrategy() {
        return optionStrategy;
    }

    public void setOptionStrategy(String optionStrategy) {
        this.optionStrategy = optionStrategy;
    }

    public String getFilledTimeAt() {
        return filledTimeAt;
    }

    public void setFilledTimeAt(String filledTimeAt) {
        this.filledTimeAt = filledTimeAt;
    }

    public String getTrailingStopStep() {
        return trailingStopStep;
    }

    public void setTrailingStopStep(String trailingStopStep) {
        this.trailingStopStep = trailingStopStep;
    }

    public String getTrailingType() {
        return trailingType;
    }

    public void setTrailingType(String trailingType) {
        this.trailingType = trailingType;
    }

    public List<NOrderItemLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<NOrderItemLeg> legs) {
        this.legs = legs;
    }

    @Deprecated
    public String getComboType() {
        return comboType;
    }

    @Deprecated
    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    @Deprecated
    public String getQuantity() {
        return quantity;
    }

    @Deprecated
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Deprecated
    public String getOptionType() {
        return optionType;
    }

    @Deprecated
    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    @Deprecated
    public String getOptionExpireDate() {
        return optionExpireDate;
    }

    @Deprecated
    public void setOptionExpireDate(String optionExpireDate) {
        this.optionExpireDate = optionExpireDate;
    }

    @Deprecated
    public String getOptionExercisePrice() {
        return optionExercisePrice;
    }

    @Deprecated
    public void setOptionExercisePrice(String optionExercisePrice) {
        this.optionExercisePrice = optionExercisePrice;
    }

    @Deprecated
    public String getOptionCategory() {
        return optionCategory;
    }

    @Deprecated
    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
    }

    @Deprecated
    public String getOptionContractMultiplier() {
        return optionContractMultiplier;
    }

    @Deprecated
    public void setOptionContractMultiplier(String optionContractMultiplier) {
        this.optionContractMultiplier = optionContractMultiplier;
    }

    @Override
    public String toString() {
        return "NOrderItem{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", trailingType='" + trailingType + '\'' +
                ", trailingStopStep='" + trailingStopStep + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", filledPrice='" + filledPrice + '\'' +
                ", status='" + status + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", supportTradingSession='" + supportTradingSession + '\'' +
                ", comboOrderId='" + comboOrderId + '\'' +
                ", entrustType='" + entrustType + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", remainQuantity='" + remainQuantity + '\'' +
                ", placeTime='" + placeTime + '\'' +
                ", placeTimeAt='" + placeTimeAt + '\'' +
                ", filledTime='" + filledTime + '\'' +
                ", filledTimeAt='" + filledTimeAt + '\'' +
                ", optionStrategy='" + optionStrategy + '\'' +
                ", legs='" + legs + '\'' +
                '}';
    }
}
