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
 * Income statement entry.
 */
public class FinancialIncome {

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
    /** Total revenue */
    private String totalRevenue;
    /** Revenue */
    private String revenue;
    /** Total cost of revenue */
    private String costOfRevenue;
    /** Gross profit */
    private String grossProfit;
    /** Operating expenses */
    private String opex;
    /** Selling, general and administrative expenses */
    private String sgaExp;
    /** Research and development expenses */
    private String rndExp;
    /** Operating income */
    private String opIncome;
    /** Other net income */
    private String otherNetIncome;
    /** Net income before tax */
    private String ebt;
    /** Income tax */
    private String incomeTax;
    /** Net income after tax */
    private String eat;
    /** Net income before extraordinary items */
    private String niPreExtra;
    /** Total extraordinary items */
    private String extraItems;
    /** Net income */
    private String netIncome;
    /** Income available to common shareholders including extraordinary items */
    private String niCommonInclExtra;
    /** Income available to common shareholders excluding extraordinary items */
    private String niCommonExclExtra;
    /** Diluted net income */
    private String dilutedNi;
    /** Diluted weighted average shares */
    private String dilutedAvgShares;
    /** Diluted EPS including extraordinary items */
    private String dilutedEpsInclExtra;
    /** Diluted EPS excluding extraordinary items */
    private String dilutedEpsExclExtra;
    /** Dividends per share */
    private String dps;
    /** Diluted normalized EPS */
    private String dilutedNormEps;
    /** Operating profit */
    private String opProfit;
    /** Earnings after tax (alternative) */
    private String eatAlt;
    /** Earnings before tax (alternative) */
    private String ebtAlt;
    /** Unusual expense (income) */
    private String unusualExpenseIncome;
    /** Interest expense (income) net non-operating */
    private String interIncExpseNetNonOper;
    /** Gain (loss) on sale of assets */
    private String gainLossOnSaleOfAssets;
    /** Minority interest */
    private String minorityInterest;

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

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getCostOfRevenue() {
        return costOfRevenue;
    }

    public void setCostOfRevenue(String costOfRevenue) {
        this.costOfRevenue = costOfRevenue;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getOpex() {
        return opex;
    }

    public void setOpex(String opex) {
        this.opex = opex;
    }

    public String getSgaExp() {
        return sgaExp;
    }

    public void setSgaExp(String sgaExp) {
        this.sgaExp = sgaExp;
    }

    public String getRndExp() {
        return rndExp;
    }

    public void setRndExp(String rndExp) {
        this.rndExp = rndExp;
    }

    public String getOpIncome() {
        return opIncome;
    }

    public void setOpIncome(String opIncome) {
        this.opIncome = opIncome;
    }

    public String getOtherNetIncome() {
        return otherNetIncome;
    }

    public void setOtherNetIncome(String otherNetIncome) {
        this.otherNetIncome = otherNetIncome;
    }

    public String getEbt() {
        return ebt;
    }

    public void setEbt(String ebt) {
        this.ebt = ebt;
    }

    public String getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(String incomeTax) {
        this.incomeTax = incomeTax;
    }

    public String getEat() {
        return eat;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public String getNiPreExtra() {
        return niPreExtra;
    }

    public void setNiPreExtra(String niPreExtra) {
        this.niPreExtra = niPreExtra;
    }

    public String getExtraItems() {
        return extraItems;
    }

    public void setExtraItems(String extraItems) {
        this.extraItems = extraItems;
    }

    public String getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }

    public String getNiCommonInclExtra() {
        return niCommonInclExtra;
    }

    public void setNiCommonInclExtra(String niCommonInclExtra) {
        this.niCommonInclExtra = niCommonInclExtra;
    }

    public String getNiCommonExclExtra() {
        return niCommonExclExtra;
    }

    public void setNiCommonExclExtra(String niCommonExclExtra) {
        this.niCommonExclExtra = niCommonExclExtra;
    }

    public String getDilutedNi() {
        return dilutedNi;
    }

    public void setDilutedNi(String dilutedNi) {
        this.dilutedNi = dilutedNi;
    }

    public String getDilutedAvgShares() {
        return dilutedAvgShares;
    }

    public void setDilutedAvgShares(String dilutedAvgShares) {
        this.dilutedAvgShares = dilutedAvgShares;
    }

    public String getDilutedEpsInclExtra() {
        return dilutedEpsInclExtra;
    }

    public void setDilutedEpsInclExtra(String dilutedEpsInclExtra) {
        this.dilutedEpsInclExtra = dilutedEpsInclExtra;
    }

    public String getDilutedEpsExclExtra() {
        return dilutedEpsExclExtra;
    }

    public void setDilutedEpsExclExtra(String dilutedEpsExclExtra) {
        this.dilutedEpsExclExtra = dilutedEpsExclExtra;
    }

    public String getDps() {
        return dps;
    }

    public void setDps(String dps) {
        this.dps = dps;
    }

    public String getDilutedNormEps() {
        return dilutedNormEps;
    }

    public void setDilutedNormEps(String dilutedNormEps) {
        this.dilutedNormEps = dilutedNormEps;
    }

    public String getOpProfit() {
        return opProfit;
    }

    public void setOpProfit(String opProfit) {
        this.opProfit = opProfit;
    }

    public String getEatAlt() {
        return eatAlt;
    }

    public void setEatAlt(String eatAlt) {
        this.eatAlt = eatAlt;
    }

    public String getEbtAlt() {
        return ebtAlt;
    }

    public void setEbtAlt(String ebtAlt) {
        this.ebtAlt = ebtAlt;
    }

    public String getUnusualExpenseIncome() {
        return unusualExpenseIncome;
    }

    public void setUnusualExpenseIncome(String unusualExpenseIncome) {
        this.unusualExpenseIncome = unusualExpenseIncome;
    }

    public String getInterIncExpseNetNonOper() {
        return interIncExpseNetNonOper;
    }

    public void setInterIncExpseNetNonOper(String interIncExpseNetNonOper) {
        this.interIncExpseNetNonOper = interIncExpseNetNonOper;
    }

    public String getGainLossOnSaleOfAssets() {
        return gainLossOnSaleOfAssets;
    }

    public void setGainLossOnSaleOfAssets(String gainLossOnSaleOfAssets) {
        this.gainLossOnSaleOfAssets = gainLossOnSaleOfAssets;
    }

    public String getMinorityInterest() {
        return minorityInterest;
    }

    public void setMinorityInterest(String minorityInterest) {
        this.minorityInterest = minorityInterest;
    }

    @Override
    public String toString() {
        return "FinancialIncome{" +
                "fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", totalRevenue='" + totalRevenue + '\'' +
                ", revenue='" + revenue + '\'' +
                ", costOfRevenue='" + costOfRevenue + '\'' +
                ", grossProfit='" + grossProfit + '\'' +
                ", opex='" + opex + '\'' +
                ", sgaExp='" + sgaExp + '\'' +
                ", rndExp='" + rndExp + '\'' +
                ", opIncome='" + opIncome + '\'' +
                ", otherNetIncome='" + otherNetIncome + '\'' +
                ", ebt='" + ebt + '\'' +
                ", incomeTax='" + incomeTax + '\'' +
                ", eat='" + eat + '\'' +
                ", niPreExtra='" + niPreExtra + '\'' +
                ", extraItems='" + extraItems + '\'' +
                ", netIncome='" + netIncome + '\'' +
                ", niCommonInclExtra='" + niCommonInclExtra + '\'' +
                ", niCommonExclExtra='" + niCommonExclExtra + '\'' +
                ", dilutedNi='" + dilutedNi + '\'' +
                ", dilutedAvgShares='" + dilutedAvgShares + '\'' +
                ", dilutedEpsInclExtra='" + dilutedEpsInclExtra + '\'' +
                ", dilutedEpsExclExtra='" + dilutedEpsExclExtra + '\'' +
                ", dps='" + dps + '\'' +
                ", dilutedNormEps='" + dilutedNormEps + '\'' +
                ", opProfit='" + opProfit + '\'' +
                ", eatAlt='" + eatAlt + '\'' +
                ", ebtAlt='" + ebtAlt + '\'' +
                ", unusualExpenseIncome='" + unusualExpenseIncome + '\'' +
                ", interIncExpseNetNonOper='" + interIncExpseNetNonOper + '\'' +
                ", gainLossOnSaleOfAssets='" + gainLossOnSaleOfAssets + '\'' +
                ", minorityInterest='" + minorityInterest + '\'' +
                '}';
    }
}
