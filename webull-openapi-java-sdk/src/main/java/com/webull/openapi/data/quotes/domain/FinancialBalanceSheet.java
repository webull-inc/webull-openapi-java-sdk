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
 * Balance sheet entry.
 */
public class FinancialBalanceSheet {

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
    /** Total assets */
    private String totalAssets;
    /** Total current assets */
    private String totalCurAssets;
    /** Cash and short-term investments */
    private String cashStInvest;
    /** Cash */
    private String cash;
    /** Cash equivalents */
    private String cashEquiv;
    /** Short-term investments */
    private String stInvest;
    /** Total receivables (net) */
    private String totalRecvNet;
    /** Trade receivables (net) */
    private String arTradeNet;
    /** Total inventory */
    private String totalInv;
    /** Other current assets */
    private String otherCurAssets;
    /** Total non-current assets */
    private String totalNonCurAssets;
    /** Property, plant and equipment (net) */
    private String ppeNet;
    /** Property, plant and equipment (gross) */
    private String ppeGross;
    /** Accumulated depreciation */
    private String accDepre;
    /** Long-term investments */
    private String ltInvest;
    /** Other long-term assets */
    private String otherLtAssets;
    /** Total liabilities */
    private String totalLiab;
    /** Total current liabilities */
    private String totalCurLiab;
    /** Accounts payable */
    private String ap;
    /** Short-term debt */
    private String notesStDebt;
    /** Current portion of long-term debt */
    private String curLtDebtLease;
    /** Other current liabilities */
    private String otherCurLiab;
    /** Total non-current liabilities */
    private String totalNonCurLiab;
    /** Total long-term debt */
    private String totalLtDebt;
    /** Long-term debt */
    private String ltDebt;
    /** Total debt */
    private String totalDebt;
    /** Other liabilities */
    private String otherLiab;
    /** Total equity */
    private String totalEquity;
    /** Total shareholders' equity */
    private String totalShEquity;
    /** Common stock */
    private String commonStock;
    /** Additional paid-in capital */
    private String apic;
    /** Retained earnings */
    private String retainedEarnings;
    /** Other equity */
    private String otherEquity;
    /** Total liabilities and shareholders' equity */
    private String totalLiabShEquity;
    /** Total common shares outstanding */
    private String commonSharesOut;
    /** Advance payment for expenses */
    private String prepaidExpenses;
    /** Accrued expenses */
    private String accruedExpenses;
    /** Net goodwill value */
    private String goodwillNet;
    /** Net value of intangible assets */
    private String intangiblesNet;
    /** Long-term receivable bills */
    private String noteReceLongTerm;
    /** Long-term debt in capital lease transactions */
    private String capitalLeaseObligations;
    /** Minority shareholders' equity */
    private String minorityInterest;
    /** Non-redeemable preferred stocks in total */
    private String nonRedeemablePreferredStock;

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

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getTotalCurAssets() {
        return totalCurAssets;
    }

    public void setTotalCurAssets(String totalCurAssets) {
        this.totalCurAssets = totalCurAssets;
    }

    public String getCashStInvest() {
        return cashStInvest;
    }

    public void setCashStInvest(String cashStInvest) {
        this.cashStInvest = cashStInvest;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCashEquiv() {
        return cashEquiv;
    }

    public void setCashEquiv(String cashEquiv) {
        this.cashEquiv = cashEquiv;
    }

    public String getStInvest() {
        return stInvest;
    }

    public void setStInvest(String stInvest) {
        this.stInvest = stInvest;
    }

    public String getTotalRecvNet() {
        return totalRecvNet;
    }

    public void setTotalRecvNet(String totalRecvNet) {
        this.totalRecvNet = totalRecvNet;
    }

    public String getArTradeNet() {
        return arTradeNet;
    }

    public void setArTradeNet(String arTradeNet) {
        this.arTradeNet = arTradeNet;
    }

    public String getTotalInv() {
        return totalInv;
    }

    public void setTotalInv(String totalInv) {
        this.totalInv = totalInv;
    }

    public String getOtherCurAssets() {
        return otherCurAssets;
    }

    public void setOtherCurAssets(String otherCurAssets) {
        this.otherCurAssets = otherCurAssets;
    }

    public String getTotalNonCurAssets() {
        return totalNonCurAssets;
    }

    public void setTotalNonCurAssets(String totalNonCurAssets) {
        this.totalNonCurAssets = totalNonCurAssets;
    }

    public String getPpeNet() {
        return ppeNet;
    }

    public void setPpeNet(String ppeNet) {
        this.ppeNet = ppeNet;
    }

    public String getPpeGross() {
        return ppeGross;
    }

    public void setPpeGross(String ppeGross) {
        this.ppeGross = ppeGross;
    }

    public String getAccDepre() {
        return accDepre;
    }

    public void setAccDepre(String accDepre) {
        this.accDepre = accDepre;
    }

    public String getLtInvest() {
        return ltInvest;
    }

    public void setLtInvest(String ltInvest) {
        this.ltInvest = ltInvest;
    }

    public String getOtherLtAssets() {
        return otherLtAssets;
    }

    public void setOtherLtAssets(String otherLtAssets) {
        this.otherLtAssets = otherLtAssets;
    }

    public String getTotalLiab() {
        return totalLiab;
    }

    public void setTotalLiab(String totalLiab) {
        this.totalLiab = totalLiab;
    }

    public String getTotalCurLiab() {
        return totalCurLiab;
    }

    public void setTotalCurLiab(String totalCurLiab) {
        this.totalCurLiab = totalCurLiab;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getNotesStDebt() {
        return notesStDebt;
    }

    public void setNotesStDebt(String notesStDebt) {
        this.notesStDebt = notesStDebt;
    }

    public String getCurLtDebtLease() {
        return curLtDebtLease;
    }

    public void setCurLtDebtLease(String curLtDebtLease) {
        this.curLtDebtLease = curLtDebtLease;
    }

    public String getOtherCurLiab() {
        return otherCurLiab;
    }

    public void setOtherCurLiab(String otherCurLiab) {
        this.otherCurLiab = otherCurLiab;
    }

    public String getTotalNonCurLiab() {
        return totalNonCurLiab;
    }

    public void setTotalNonCurLiab(String totalNonCurLiab) {
        this.totalNonCurLiab = totalNonCurLiab;
    }

    public String getTotalLtDebt() {
        return totalLtDebt;
    }

    public void setTotalLtDebt(String totalLtDebt) {
        this.totalLtDebt = totalLtDebt;
    }

    public String getLtDebt() {
        return ltDebt;
    }

    public void setLtDebt(String ltDebt) {
        this.ltDebt = ltDebt;
    }

    public String getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(String totalDebt) {
        this.totalDebt = totalDebt;
    }

    public String getOtherLiab() {
        return otherLiab;
    }

    public void setOtherLiab(String otherLiab) {
        this.otherLiab = otherLiab;
    }

    public String getTotalEquity() {
        return totalEquity;
    }

    public void setTotalEquity(String totalEquity) {
        this.totalEquity = totalEquity;
    }

    public String getTotalShEquity() {
        return totalShEquity;
    }

    public void setTotalShEquity(String totalShEquity) {
        this.totalShEquity = totalShEquity;
    }

    public String getCommonStock() {
        return commonStock;
    }

    public void setCommonStock(String commonStock) {
        this.commonStock = commonStock;
    }

    public String getApic() {
        return apic;
    }

    public void setApic(String apic) {
        this.apic = apic;
    }

    public String getRetainedEarnings() {
        return retainedEarnings;
    }

    public void setRetainedEarnings(String retainedEarnings) {
        this.retainedEarnings = retainedEarnings;
    }

    public String getOtherEquity() {
        return otherEquity;
    }

    public void setOtherEquity(String otherEquity) {
        this.otherEquity = otherEquity;
    }

    public String getTotalLiabShEquity() {
        return totalLiabShEquity;
    }

    public void setTotalLiabShEquity(String totalLiabShEquity) {
        this.totalLiabShEquity = totalLiabShEquity;
    }

    public String getCommonSharesOut() {
        return commonSharesOut;
    }

    public void setCommonSharesOut(String commonSharesOut) {
        this.commonSharesOut = commonSharesOut;
    }

    public String getPrepaidExpenses() {
        return prepaidExpenses;
    }

    public void setPrepaidExpenses(String prepaidExpenses) {
        this.prepaidExpenses = prepaidExpenses;
    }

    public String getAccruedExpenses() {
        return accruedExpenses;
    }

    public void setAccruedExpenses(String accruedExpenses) {
        this.accruedExpenses = accruedExpenses;
    }

    public String getGoodwillNet() {
        return goodwillNet;
    }

    public void setGoodwillNet(String goodwillNet) {
        this.goodwillNet = goodwillNet;
    }

    public String getIntangiblesNet() {
        return intangiblesNet;
    }

    public void setIntangiblesNet(String intangiblesNet) {
        this.intangiblesNet = intangiblesNet;
    }

    public String getNoteReceLongTerm() {
        return noteReceLongTerm;
    }

    public void setNoteReceLongTerm(String noteReceLongTerm) {
        this.noteReceLongTerm = noteReceLongTerm;
    }

    public String getCapitalLeaseObligations() {
        return capitalLeaseObligations;
    }

    public void setCapitalLeaseObligations(String capitalLeaseObligations) {
        this.capitalLeaseObligations = capitalLeaseObligations;
    }

    public String getMinorityInterest() {
        return minorityInterest;
    }

    public void setMinorityInterest(String minorityInterest) {
        this.minorityInterest = minorityInterest;
    }

    public String getNonRedeemablePreferredStock() {
        return nonRedeemablePreferredStock;
    }

    public void setNonRedeemablePreferredStock(String nonRedeemablePreferredStock) {
        this.nonRedeemablePreferredStock = nonRedeemablePreferredStock;
    }

    @Override
    public String toString() {
        return "FinancialBalanceSheet{" +
                "fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", totalCurAssets='" + totalCurAssets + '\'' +
                ", cashStInvest='" + cashStInvest + '\'' +
                ", cash='" + cash + '\'' +
                ", cashEquiv='" + cashEquiv + '\'' +
                ", stInvest='" + stInvest + '\'' +
                ", totalRecvNet='" + totalRecvNet + '\'' +
                ", arTradeNet='" + arTradeNet + '\'' +
                ", totalInv='" + totalInv + '\'' +
                ", otherCurAssets='" + otherCurAssets + '\'' +
                ", totalNonCurAssets='" + totalNonCurAssets + '\'' +
                ", ppeNet='" + ppeNet + '\'' +
                ", ppeGross='" + ppeGross + '\'' +
                ", accDepre='" + accDepre + '\'' +
                ", ltInvest='" + ltInvest + '\'' +
                ", otherLtAssets='" + otherLtAssets + '\'' +
                ", totalLiab='" + totalLiab + '\'' +
                ", totalCurLiab='" + totalCurLiab + '\'' +
                ", ap='" + ap + '\'' +
                ", notesStDebt='" + notesStDebt + '\'' +
                ", curLtDebtLease='" + curLtDebtLease + '\'' +
                ", otherCurLiab='" + otherCurLiab + '\'' +
                ", totalNonCurLiab='" + totalNonCurLiab + '\'' +
                ", totalLtDebt='" + totalLtDebt + '\'' +
                ", ltDebt='" + ltDebt + '\'' +
                ", totalDebt='" + totalDebt + '\'' +
                ", otherLiab='" + otherLiab + '\'' +
                ", totalEquity='" + totalEquity + '\'' +
                ", totalShEquity='" + totalShEquity + '\'' +
                ", commonStock='" + commonStock + '\'' +
                ", apic='" + apic + '\'' +
                ", retainedEarnings='" + retainedEarnings + '\'' +
                ", otherEquity='" + otherEquity + '\'' +
                ", totalLiabShEquity='" + totalLiabShEquity + '\'' +
                ", commonSharesOut='" + commonSharesOut + '\'' +
                ", prepaidExpenses='" + prepaidExpenses + '\'' +
                ", accruedExpenses='" + accruedExpenses + '\'' +
                ", goodwillNet='" + goodwillNet + '\'' +
                ", intangiblesNet='" + intangiblesNet + '\'' +
                ", noteReceLongTerm='" + noteReceLongTerm + '\'' +
                ", capitalLeaseObligations='" + capitalLeaseObligations + '\'' +
                ", minorityInterest='" + minorityInterest + '\'' +
                ", nonRedeemablePreferredStock='" + nonRedeemablePreferredStock + '\'' +
                '}';
    }
}
