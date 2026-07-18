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
package com.webull.openapi.data.quotes.domain;

/**
 * Option contract deliverable information.
 * Represents the delivery configuration for an option contract.
 * After corporate actions (CA), a single contract may correspond to multiple underlyings plus cash.
 */
public class OptionContractDeliverable {

    private String assetType;
    private String symbol;
    private String instrumentId;
    private String amount;
    private String allocationPercentage;
    private String settlementType;
    private String settlementMethod;
    private String settlementStatus;

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAllocationPercentage() {
        return allocationPercentage;
    }

    public void setAllocationPercentage(String allocationPercentage) {
        this.allocationPercentage = allocationPercentage;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    @Override
    public String toString() {
        return "OptionContractDeliverable{" +
                "assetType='" + assetType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", amount='" + amount + '\'' +
                ", allocationPercentage='" + allocationPercentage + '\'' +
                ", settlementType='" + settlementType + '\'' +
                ", settlementMethod='" + settlementMethod + '\'' +
                ", settlementStatus='" + settlementStatus + '\'' +
                '}';
    }
}
