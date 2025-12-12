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

import java.io.Serializable;

public class TradeOrderResponse implements Serializable {

    private static final long serialVersionUID = 4034841807131057830L;
    private String clientOrderId;
    private String orderId;
    private String clientComboOrderId;
    private String comboOrderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getComboOrderId() {
        return comboOrderId;
    }

    public void setComboOrderId(String comboOrderId) {
        this.comboOrderId = comboOrderId;
    }

    @Override
    public String toString() {
        return "TradeOrderResponse{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", clientComboOrderId='" + clientComboOrderId + '\'' +
                ", comboOrderId='" + comboOrderId + '\'' +
                '}';
    }
}
