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
 * A single institution holding change record (US only).
 */
public class InstitutionHoldingChange {

    private String symbol;
    private String buyPrice;
    private String dateFiled;
    private String holdingRate;
    private String reportDate;
    private String shares;
    private String sharesChange;
    private String sharesChangeRate;
    private String updateTime;
    private String value;
    private String valueChange;
    private String valueRate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(String dateFiled) {
        this.dateFiled = dateFiled;
    }

    public String getHoldingRate() {
        return holdingRate;
    }

    public void setHoldingRate(String holdingRate) {
        this.holdingRate = holdingRate;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getSharesChange() {
        return sharesChange;
    }

    public void setSharesChange(String sharesChange) {
        this.sharesChange = sharesChange;
    }

    public String getSharesChangeRate() {
        return sharesChangeRate;
    }

    public void setSharesChangeRate(String sharesChangeRate) {
        this.sharesChangeRate = sharesChangeRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueChange() {
        return valueChange;
    }

    public void setValueChange(String valueChange) {
        this.valueChange = valueChange;
    }

    public String getValueRate() {
        return valueRate;
    }

    public void setValueRate(String valueRate) {
        this.valueRate = valueRate;
    }

    @Override
    public String toString() {
        return "InstitutionHoldingChange{" +
                "symbol='" + symbol + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", dateFiled='" + dateFiled + '\'' +
                ", holdingRate='" + holdingRate + '\'' +
                ", reportDate='" + reportDate + '\'' +
                ", shares='" + shares + '\'' +
                ", sharesChange='" + sharesChange + '\'' +
                ", sharesChangeRate='" + sharesChangeRate + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", value='" + value + '\'' +
                ", valueChange='" + valueChange + '\'' +
                ", valueRate='" + valueRate + '\'' +
                '}';
    }
}
