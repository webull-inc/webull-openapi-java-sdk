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

public class StockInstrumentDetail extends Instrument{

    private String category;

    private String status;

    private Boolean fractionable;

    private Boolean overnightTradingSupported;

    private String marginRequirementLong;

    private String marginRequirementShort;

    private Boolean shortable;

    private Boolean marginable;

    private Boolean easyToBorrow;

    private String lotSize;

    public Boolean getFractionable() {
        return fractionable;
    }

    public void setFractionable(Boolean fractionable) {
        this.fractionable = fractionable;
    }

    public Boolean getOvernightTradingSupported() {
        return overnightTradingSupported;
    }

    public void setOvernightTradingSupported(Boolean overnightTradingSupported) {
        this.overnightTradingSupported = overnightTradingSupported;
    }

    public String getMarginRequirementLong() {
        return marginRequirementLong;
    }

    public void setMarginRequirementLong(String marginRequirementLong) {
        this.marginRequirementLong = marginRequirementLong;
    }

    public String getMarginRequirementShort() {
        return marginRequirementShort;
    }

    public void setMarginRequirementShort(String marginRequirementShort) {
        this.marginRequirementShort = marginRequirementShort;
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

    public Boolean getShortable() {
        return shortable;
    }

    public void setShortable(Boolean shortable) {
        this.shortable = shortable;
    }

    public Boolean getMarginable() {
        return marginable;
    }

    public void setMarginable(Boolean marginable) {
        this.marginable = marginable;
    }

    public Boolean getEasyToBorrow() {
        return easyToBorrow;
    }

    public void setEasyToBorrow(Boolean easyToBorrow) {
        this.easyToBorrow = easyToBorrow;
    }

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    @Override
    public String toString() {
        return "StockInstrumentDetail{" +
                "category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", fractionable=" + fractionable +
                ", overnightTradingSupported=" + overnightTradingSupported +
                ", marginRequirementLong='" + marginRequirementLong + '\'' +
                ", marginRequirementShort='" + marginRequirementShort + '\'' +
                ", shortable=" + shortable +
                ", marginable=" + marginable +
                ", easyToBorrow=" + easyToBorrow +
                ", lotSize='" + lotSize + '\'' +
                '}';
    }
}

