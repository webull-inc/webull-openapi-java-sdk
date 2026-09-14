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

import java.io.Serializable;

/**
 * Cash entry of a transfer record.
 * Present for ACATS transfers; null for crypto transfers which carry no cash.
 */
public class TransferCashInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Signed cash amount as a string; a negative value indicates an outgoing transfer.
     */
    private String amount;

    /**
     * Currency of the cash amount, e.g. USD, HKD.
     */
    private String currency;

    /**
     * Settlement date of the cash entry; empty when not yet settled.
     */
    private String settlementDate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public String toString() {
        return "TransferCashInfo{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                '}';
    }
}
