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

public class CryptoInstrumentDetail extends Instrument{

    private String category;

    private String status;

    private String lotSize;

    private String minTradeAmt;

    private String maxTradeAmt;

    private String minTradeQty;

    private String maxTradeQty;

    private String priceStep;

    public String getMinTradeAmt() {
        return minTradeAmt;
    }

    public void setMinTradeAmt(String minTradeAmt) {
        this.minTradeAmt = minTradeAmt;
    }

    public String getMaxTradeAmt() {
        return maxTradeAmt;
    }

    public void setMaxTradeAmt(String maxTradeAmt) {
        this.maxTradeAmt = maxTradeAmt;
    }

    public String getMinTradeQty() {
        return minTradeQty;
    }

    public void setMinTradeQty(String minTradeQty) {
        this.minTradeQty = minTradeQty;
    }

    public String getMaxTradeQty() {
        return maxTradeQty;
    }

    public void setMaxTradeQty(String maxTradeQty) {
        this.maxTradeQty = maxTradeQty;
    }

    public String getPriceStep() {
        return priceStep;
    }

    public void setPriceStep(String priceStep) {
        this.priceStep = priceStep;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    @Override
    public String toString() {
        return "CryptoInstrumentDetail{" +
                "category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", lotSize='" + lotSize + '\'' +
                ", minTradeAmt='" + minTradeAmt + '\'' +
                ", maxTradeAmt='" + maxTradeAmt + '\'' +
                ", minTradeQty='" + minTradeQty + '\'' +
                ", maxTradeQty='" + maxTradeQty + '\'' +
                ", priceStep='" + priceStep + '\'' +
                '}';
    }
}

