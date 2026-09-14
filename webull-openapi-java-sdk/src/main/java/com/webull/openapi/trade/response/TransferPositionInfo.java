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
 * Position (holding) entry of a transfer record.
 * Quantity fields are strings to preserve up to 20 decimal places without precision loss.
 */
public class TransferPositionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Asset type: EQUITY, OPTION, FUTURES, CRYPTO, EVENT, MUTUAL_FUND, BOND.
     */
    private String assetType;

    /**
     * Asset symbol.
     */
    private String symbol;

    /**
     * Asset name; null for crypto.
     */
    private String name;

    /**
     * CUSIP for cross-broker alignment; returned for equities, null for crypto.
     */
    private String cusip;

    /**
     * Transferred quantity (actual received/sent amount).
     */
    private String quantity;

    /**
     * Fee quantity (user-side gas fee, in the same asset). Only returned for crypto coin-out, otherwise "0" or null.
     */
    private String feeQuantity;

    /**
     * Total account change = quantity + feeQuantity for crypto coin-out; otherwise equals quantity.
     */
    private String totalQuantity;

    /**
     * Transfer price (cost basis, incoming only).
     */
    private String transferPrice;

    /**
     * Settlement date of the position; empty when not yet settled.
     */
    private String settlementDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFeeQuantity() {
        return feeQuantity;
    }

    public void setFeeQuantity(String feeQuantity) {
        this.feeQuantity = feeQuantity;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTransferPrice() {
        return transferPrice;
    }

    public void setTransferPrice(String transferPrice) {
        this.transferPrice = transferPrice;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public String toString() {
        return "TransferPositionInfo{" +
                "assetType='" + assetType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", cusip='" + cusip + '\'' +
                ", quantity='" + quantity + '\'' +
                ", feeQuantity='" + feeQuantity + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", transferPrice='" + transferPrice + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                '}';
    }
}
