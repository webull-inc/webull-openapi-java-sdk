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
package com.webull.openapi.trade.response.v2;

import com.webull.openapi.trade.response.NOrderItem;

import java.util.List;

public class OrderHistory {

    private String clientOrderId;
    private String clientComboOrderId;
    private String comboOrderId;
    private String comboType;
    private List<NOrderItem> orders;

    @Deprecated
    private String orderId;
    @Deprecated
    private String side;
    @Deprecated
    private String orderType;
    @Deprecated
    private String timeInForce;
    @Deprecated
    private String stopPrice;
    @Deprecated
    private String limitPrice;
    @Deprecated
    private String quantity;
    @Deprecated
    private String filledQuantity;
    @Deprecated
    private String status;
    @Deprecated
    private String supportTradingSession;
    @Deprecated
    private String optionStrategy;
    @Deprecated
    private String comboInstrumentType;
    @Deprecated
    private List<NOrderItem> items;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getClientComboOrderId() {
        return clientComboOrderId;
    }

    public void setClientComboOrderId(String clientComboOrderId) {
        this.clientComboOrderId = clientComboOrderId;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public String getComboOrderId() {
        return comboOrderId;
    }

    public void setComboOrderId(String comboOrderId) {
        this.comboOrderId = comboOrderId;
    }

    public List<NOrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<NOrderItem> orders) {
        this.orders = orders;
    }

    @Deprecated
    public String getSide() {
        return side;
    }

    @Deprecated
    public void setSide(String side) {
        this.side = side;
    }

    @Deprecated
    public String getOrderType() {
        return orderType;
    }

    @Deprecated
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Deprecated
    public String getTimeInForce() {
        return timeInForce;
    }

    @Deprecated
    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    @Deprecated
    public String getStopPrice() {
        return stopPrice;
    }

    @Deprecated
    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    @Deprecated
    public String getLimitPrice() {
        return limitPrice;
    }

    @Deprecated
    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
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
    public String getFilledQuantity() {
        return filledQuantity;
    }

    @Deprecated
    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    @Deprecated
    public String getStatus() {
        return status;
    }

    @Deprecated
    public void setStatus(String status) {
        this.status = status;
    }

    @Deprecated
    public String getSupportTradingSession() {
        return supportTradingSession;
    }

    @Deprecated
    public void setSupportTradingSession(String supportTradingSession) {
        this.supportTradingSession = supportTradingSession;
    }

    @Deprecated
    public String getOptionStrategy() {
        return optionStrategy;
    }

    @Deprecated
    public void setOptionStrategy(String optionStrategy) {
        this.optionStrategy = optionStrategy;
    }

    @Deprecated
    public String getComboInstrumentType() {
        return comboInstrumentType;
    }

    @Deprecated
    public void setComboInstrumentType(String comboInstrumentType) {
        this.comboInstrumentType = comboInstrumentType;
    }

    @Deprecated
    public List<NOrderItem> getItems() {
        return items;
    }

    @Deprecated
    public void setItems(List<NOrderItem> items) {
        this.items = items;
    }

    @Deprecated
    public String getOrderId() {
        return orderId;
    }

    @Deprecated
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", comboOrderId='" + comboOrderId + '\'' +
                ", clientComboOrderId='" + clientComboOrderId + '\'' +
                ", comboType='" + comboType + '\'' +
                ", orders='" + orders + '\'' +
                '}';
    }
}
