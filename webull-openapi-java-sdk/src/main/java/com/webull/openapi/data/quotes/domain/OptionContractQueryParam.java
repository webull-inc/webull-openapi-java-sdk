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

import java.math.BigDecimal;

/**
 * Query parameters for option contracts API.
 * Used to query option contract list by underlying symbols, status, expiration date, etc.
 */
public class OptionContractQueryParam {

    private String category;
    private String underlyingSymbols;
    private String status;
    private String startDate;
    private String endDate;
    private String rootSymbol;
    private String optionSymbol;
    private String optionType;
    private String style;
    private BigDecimal strikePriceGte;
    private BigDecimal strikePriceLte;
    private Boolean ppind;
    private Boolean showDeliverables;
    private int pageSize = 10;
    private String lastInstrumentId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnderlyingSymbols() {
        return underlyingSymbols;
    }

    public void setUnderlyingSymbols(String underlyingSymbols) {
        this.underlyingSymbols = underlyingSymbols;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRootSymbol() {
        return rootSymbol;
    }

    public void setRootSymbol(String rootSymbol) {
        this.rootSymbol = rootSymbol;
    }

    public String getOptionSymbol() {
        return optionSymbol;
    }

    public void setOptionSymbol(String optionSymbol) {
        this.optionSymbol = optionSymbol;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public BigDecimal getStrikePriceGte() {
        return strikePriceGte;
    }

    public void setStrikePriceGte(BigDecimal strikePriceGte) {
        this.strikePriceGte = strikePriceGte;
    }

    public BigDecimal getStrikePriceLte() {
        return strikePriceLte;
    }

    public void setStrikePriceLte(BigDecimal strikePriceLte) {
        this.strikePriceLte = strikePriceLte;
    }

    public Boolean getPpind() {
        return ppind;
    }

    public void setPpind(Boolean ppind) {
        this.ppind = ppind;
    }

    public Boolean getShowDeliverables() {
        return showDeliverables;
    }

    public void setShowDeliverables(Boolean showDeliverables) {
        this.showDeliverables = showDeliverables;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLastInstrumentId() {
        return lastInstrumentId;
    }

    public void setLastInstrumentId(String lastInstrumentId) {
        this.lastInstrumentId = lastInstrumentId;
    }
}
