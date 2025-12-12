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
package com.webull.openapi.trade.response.v3;

import com.webull.openapi.trade.response.NOrderItem;

import java.io.Serializable;
import java.util.List;

public class OrderHistory implements Serializable {

    private static final long serialVersionUID = 8824079461674846027L;
    private String clientOrderId;
    private String clientComboOrderId;
    private String comboType;
    private List<NOrderItem> orders;

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

    public List<NOrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<NOrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", clientComboOrderId='" + clientComboOrderId + '\'' +
                ", comboType='" + comboType + '\'' +
                ", orders='" + orders + '\'' +
                '}';
    }
}
