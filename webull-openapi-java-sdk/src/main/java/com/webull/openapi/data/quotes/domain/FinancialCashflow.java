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

import com.google.gson.annotations.SerializedName;

/**
 * Cash flow statement entry.
 */
public class FinancialCashflow {

    /** Fiscal year */
    private Integer fiscalYear;
    /** Fiscal period (0=FY, 1=Q1, 2=Q2, 3=Q3, 4=Q4) */
    private Integer fiscalPeriod;
    /** Report end date */
    private String endDate;
    /** Currency */
    private String currency;
    /** Publish date */
    private String publishDate;
    /** Cash from operating activities */
    private String cfo;
    /** Net income */
    private String netIncome;
    /** Depreciation and amortization */
    private String dna;
    /** Deferred taxes */
    private String deferredTax;
    /** Non-cash items */
    private String nonCashItems;
    /** Changes in working capital */
    private String wcChange;
    /** Cash from investing activities */
    private String cfi;
    /** Capital expenditures */
    private String capex;
    /** Other investing cash flow items */
    private String otherCfiItems;
    /** Cash from financing activities */
    private String cff;
    /** Financing cash flow items */
    private String cffItems;
    /** Net issuance or repurchase of stock */
    @SerializedName("net_stock_iss_ret")
    private String netStockIssNet;
    /** Net issuance or repayment of debt */
    @SerializedName("net_debt_iss_ret")
    private String netDebtIssNet;
    /** Foreign exchange effects on cash */
    private String fxEffects;
    /** Net change in cash */
    private String netChangeCash;
    /** Cash interest paid */
    private String interestPaid;
    /** Cash taxes paid */
    private String taxesPaid;

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Integer getFiscalPeriod() {
        return fiscalPeriod;
    }

    public void setFiscalPeriod(Integer fiscalPeriod) {
        this.fiscalPeriod = fiscalPeriod;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCfo() {
        return cfo;
    }

    public void setCfo(String cfo) {
        this.cfo = cfo;
    }

    public String getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public String getDeferredTax() {
        return deferredTax;
    }

    public void setDeferredTax(String deferredTax) {
        this.deferredTax = deferredTax;
    }

    public String getNonCashItems() {
        return nonCashItems;
    }

    public void setNonCashItems(String nonCashItems) {
        this.nonCashItems = nonCashItems;
    }

    public String getWcChange() {
        return wcChange;
    }

    public void setWcChange(String wcChange) {
        this.wcChange = wcChange;
    }

    public String getCfi() {
        return cfi;
    }

    public void setCfi(String cfi) {
        this.cfi = cfi;
    }

    public String getCapex() {
        return capex;
    }

    public void setCapex(String capex) {
        this.capex = capex;
    }

    public String getOtherCfiItems() {
        return otherCfiItems;
    }

    public void setOtherCfiItems(String otherCfiItems) {
        this.otherCfiItems = otherCfiItems;
    }

    public String getCff() {
        return cff;
    }

    public void setCff(String cff) {
        this.cff = cff;
    }

    public String getCffItems() {
        return cffItems;
    }

    public void setCffItems(String cffItems) {
        this.cffItems = cffItems;
    }

    public String getNetStockIssNet() {
        return netStockIssNet;
    }

    public void setNetStockIssNet(String netStockIssNet) {
        this.netStockIssNet = netStockIssNet;
    }

    public String getNetDebtIssNet() {
        return netDebtIssNet;
    }

    public void setNetDebtIssNet(String netDebtIssNet) {
        this.netDebtIssNet = netDebtIssNet;
    }

    public String getFxEffects() {
        return fxEffects;
    }

    public void setFxEffects(String fxEffects) {
        this.fxEffects = fxEffects;
    }

    public String getNetChangeCash() {
        return netChangeCash;
    }

    public void setNetChangeCash(String netChangeCash) {
        this.netChangeCash = netChangeCash;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getTaxesPaid() {
        return taxesPaid;
    }

    public void setTaxesPaid(String taxesPaid) {
        this.taxesPaid = taxesPaid;
    }

    @Override
    public String toString() {
        return "FinancialCashflow{" +
                "fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", cfo='" + cfo + '\'' +
                ", netIncome='" + netIncome + '\'' +
                ", dna='" + dna + '\'' +
                ", deferredTax='" + deferredTax + '\'' +
                ", nonCashItems='" + nonCashItems + '\'' +
                ", wcChange='" + wcChange + '\'' +
                ", cfi='" + cfi + '\'' +
                ", capex='" + capex + '\'' +
                ", otherCfiItems='" + otherCfiItems + '\'' +
                ", cff='" + cff + '\'' +
                ", cffItems='" + cffItems + '\'' +
                ", netStockIssNet='" + netStockIssNet + '\'' +
                ", netDebtIssNet='" + netDebtIssNet + '\'' +
                ", fxEffects='" + fxEffects + '\'' +
                ", netChangeCash='" + netChangeCash + '\'' +
                ", interestPaid='" + interestPaid + '\'' +
                ", taxesPaid='" + taxesPaid + '\'' +
                '}';
    }
}
