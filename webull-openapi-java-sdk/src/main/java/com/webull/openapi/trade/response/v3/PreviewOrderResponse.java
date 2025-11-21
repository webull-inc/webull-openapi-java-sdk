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

public class PreviewOrderResponse implements Serializable {

    private static final long serialVersionUID = -4507115300178781049L;
    private String estimatedCost;
    private String estimatedTransactionFee;
    private String currency;

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getEstimatedTransactionFee() {
        return estimatedTransactionFee;
    }

    public void setEstimatedTransactionFee(String estimatedTransactionFee) {
        this.estimatedTransactionFee = estimatedTransactionFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PreviewOrderResponse{" +
                "estimatedCost='" + estimatedCost + '\'' +
                ", estimatedTransactionFee='" + estimatedTransactionFee + '\'' +
                '}';
    }
}
