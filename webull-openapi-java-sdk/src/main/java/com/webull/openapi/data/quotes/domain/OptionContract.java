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

import java.util.List;

/**
 * Option contract detail returned by the option contracts query API.
 */
public class OptionContract {

    private String instrumentId;
    private String symbol;
    private String name;
    private String status;
    private String tradableStatus;
    private String expirationDate;
    private String rootSymbol;
    private String underlyingSymbol;
    private String underlyingInstrumentId;
    private String underlyingType;
    private String optionType;
    private String style;
    private String strikePrice;
    private String multiplier;
    private String settlementMethod;
    private String expiredCycle;
    private Boolean ppind;
    private String currency;
    private String defType;
    private List<String> listedExchanges;
    private List<OptionContractDeliverable> deliverables;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTradableStatus() {
        return tradableStatus;
    }

    public void setTradableStatus(String tradableStatus) {
        this.tradableStatus = tradableStatus;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRootSymbol() {
        return rootSymbol;
    }

    public void setRootSymbol(String rootSymbol) {
        this.rootSymbol = rootSymbol;
    }

    public String getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    public void setUnderlyingSymbol(String underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    public String getUnderlyingInstrumentId() {
        return underlyingInstrumentId;
    }

    public void setUnderlyingInstrumentId(String underlyingInstrumentId) {
        this.underlyingInstrumentId = underlyingInstrumentId;
    }

    public String getUnderlyingType() {
        return underlyingType;
    }

    public void setUnderlyingType(String underlyingType) {
        this.underlyingType = underlyingType;
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

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    public String getExpiredCycle() {
        return expiredCycle;
    }

    public void setExpiredCycle(String expiredCycle) {
        this.expiredCycle = expiredCycle;
    }

    public Boolean getPpind() {
        return ppind;
    }

    public void setPpind(Boolean ppind) {
        this.ppind = ppind;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDefType() {
        return defType;
    }

    public void setDefType(String defType) {
        this.defType = defType;
    }

    public List<String> getListedExchanges() {
        return listedExchanges;
    }

    public void setListedExchanges(List<String> listedExchanges) {
        this.listedExchanges = listedExchanges;
    }

    public List<OptionContractDeliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<OptionContractDeliverable> deliverables) {
        this.deliverables = deliverables;
    }

    @Override
    public String toString() {
        return "OptionContract{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", tradableStatus='" + tradableStatus + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", rootSymbol='" + rootSymbol + '\'' +
                ", underlyingSymbol='" + underlyingSymbol + '\'' +
                ", underlyingInstrumentId='" + underlyingInstrumentId + '\'' +
                ", underlyingType='" + underlyingType + '\'' +
                ", optionType='" + optionType + '\'' +
                ", style='" + style + '\'' +
                ", strikePrice='" + strikePrice + '\'' +
                ", multiplier='" + multiplier + '\'' +
                ", settlementMethod='" + settlementMethod + '\'' +
                ", expiredCycle='" + expiredCycle + '\'' +
                ", ppind=" + ppind +
                ", currency='" + currency + '\'' +
                ", defType='" + defType + '\'' +
                ", listedExchanges=" + listedExchanges +
                ", deliverables=" + deliverables +
                '}';
    }
}
