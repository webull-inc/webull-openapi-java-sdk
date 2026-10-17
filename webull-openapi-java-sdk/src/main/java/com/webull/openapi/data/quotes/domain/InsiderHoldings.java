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
 * Insider ownership statistics overview for a US stock.
 */
public class InsiderHoldings {

    private String symbol;
    private String updateDate;
    private Integer insidersCount;
    private Integer insidersCountChange;
    private String totalSharesHeld;
    private String totalSharesHeldChange;
    private String ownershipPct;
    private String ownershipPctChange;
    private Integer compareMonths;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getInsidersCount() {
        return insidersCount;
    }

    public void setInsidersCount(Integer insidersCount) {
        this.insidersCount = insidersCount;
    }

    public Integer getInsidersCountChange() {
        return insidersCountChange;
    }

    public void setInsidersCountChange(Integer insidersCountChange) {
        this.insidersCountChange = insidersCountChange;
    }

    public String getTotalSharesHeld() {
        return totalSharesHeld;
    }

    public void setTotalSharesHeld(String totalSharesHeld) {
        this.totalSharesHeld = totalSharesHeld;
    }

    public String getTotalSharesHeldChange() {
        return totalSharesHeldChange;
    }

    public void setTotalSharesHeldChange(String totalSharesHeldChange) {
        this.totalSharesHeldChange = totalSharesHeldChange;
    }

    public String getOwnershipPct() {
        return ownershipPct;
    }

    public void setOwnershipPct(String ownershipPct) {
        this.ownershipPct = ownershipPct;
    }

    public String getOwnershipPctChange() {
        return ownershipPctChange;
    }

    public void setOwnershipPctChange(String ownershipPctChange) {
        this.ownershipPctChange = ownershipPctChange;
    }

    public Integer getCompareMonths() {
        return compareMonths;
    }

    public void setCompareMonths(Integer compareMonths) {
        this.compareMonths = compareMonths;
    }

    @Override
    public String toString() {
        return "InsiderHoldings{" +
                "symbol='" + symbol + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", insidersCount=" + insidersCount +
                ", insidersCountChange=" + insidersCountChange +
                ", totalSharesHeld='" + totalSharesHeld + '\'' +
                ", totalSharesHeldChange='" + totalSharesHeldChange + '\'' +
                ", ownershipPct='" + ownershipPct + '\'' +
                ", ownershipPctChange='" + ownershipPctChange + '\'' +
                ", compareMonths=" + compareMonths +
                '}';
    }
}
