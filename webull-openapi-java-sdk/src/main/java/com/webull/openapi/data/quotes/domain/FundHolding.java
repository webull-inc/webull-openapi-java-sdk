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
 * Fund holding entry.
 */
public class FundHolding {

    /**
     * Target symbol
     */
    private String targetSymbol;

    /**
     * Security name
     */
    private String stockName;

    /**
     * Share held percentage
     */
    private String shareHeldPct;

    /**
     * Share held change percentage
     */
    private String shareHeldChgPct;

    /**
     * Maturity date of securities held
     */
    private String maturityDate;

    private String updateTime;

    public String getTargetSymbol() {
        return targetSymbol;
    }

    public void setTargetSymbol(String targetSymbol) {
        this.targetSymbol = targetSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getShareHeldPct() {
        return shareHeldPct;
    }

    public void setShareHeldPct(String shareHeldPct) {
        this.shareHeldPct = shareHeldPct;
    }

    public String getShareHeldChgPct() {
        return shareHeldChgPct;
    }

    public void setShareHeldChgPct(String shareHeldChgPct) {
        this.shareHeldChgPct = shareHeldChgPct;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FundHolding{" +
                "targetSymbol='" + targetSymbol + '\'' +
                ", stockName='" + stockName + '\'' +
                ", shareHeldPct='" + shareHeldPct + '\'' +
                ", shareHeldChgPct='" + shareHeldChgPct + '\'' +
                ", maturityDate='" + maturityDate + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
