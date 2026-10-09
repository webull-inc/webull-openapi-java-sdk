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
 * A single insider transaction record (US only).
 */
public class InsiderTransaction {

    private String insiderName;
    private String transactionDate;
    private String side;
    private String shares;
    private String price;
    private String sharesAfter;

    public String getInsiderName() {
        return insiderName;
    }

    public void setInsiderName(String insiderName) {
        this.insiderName = insiderName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSharesAfter() {
        return sharesAfter;
    }

    public void setSharesAfter(String sharesAfter) {
        this.sharesAfter = sharesAfter;
    }

    @Override
    public String toString() {
        return "InsiderTransaction{" +
                "insiderName='" + insiderName + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", side='" + side + '\'' +
                ", shares='" + shares + '\'' +
                ", price='" + price + '\'' +
                ", sharesAfter='" + sharesAfter + '\'' +
                '}';
    }
}
