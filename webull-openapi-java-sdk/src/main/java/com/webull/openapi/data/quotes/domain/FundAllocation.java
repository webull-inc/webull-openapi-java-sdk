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
 * Fund allocation entry.
 */
public class FundAllocation {

    /**
     * End date
     */
    private String date;

    /**
     * Total assets
     */
    private String aum;

    /**
     * Cash allocation
     */
    private FundAsset cash;

    /**
     * Bond allocation
     */
    private FundAsset bond;

    /**
     * Stock allocation
     */
    private FundAsset stock;

    /**
     * Preferred stock allocation
     */
    private FundAsset preferred;

    /**
     * Convertible allocation
     */
    private FundAsset convertible;

    /**
     * Other net worth
     */
    private FundAsset other;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAum() {
        return aum;
    }

    public void setAum(String aum) {
        this.aum = aum;
    }

    public FundAsset getCash() {
        return cash;
    }

    public void setCash(FundAsset cash) {
        this.cash = cash;
    }

    public FundAsset getBond() {
        return bond;
    }

    public void setBond(FundAsset bond) {
        this.bond = bond;
    }

    public FundAsset getStock() {
        return stock;
    }

    public void setStock(FundAsset stock) {
        this.stock = stock;
    }

    public FundAsset getPreferred() {
        return preferred;
    }

    public void setPreferred(FundAsset preferred) {
        this.preferred = preferred;
    }

    public FundAsset getConvertible() {
        return convertible;
    }

    public void setConvertible(FundAsset convertible) {
        this.convertible = convertible;
    }

    public FundAsset getOther() {
        return other;
    }

    public void setOther(FundAsset other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "FundAllocation{" +
                "date='" + date + '\'' +
                ", aum='" + aum + '\'' +
                ", cash=" + cash +
                ", bond=" + bond +
                ", stock=" + stock +
                ", preferred=" + preferred +
                ", convertible=" + convertible +
                ", other=" + other +
                '}';
    }
}
