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
 * Financial alert data.
 */
public class FinancialAlert {

    /** Date of release of financial reports */
    private String startDate;
    /** The date for releasing the financial report */
    private String endDate;
    /** Fiscal year */
    private Integer fiscalYear;
    /** Fiscal quarter (1: First Quarter, 2: Second Quarter, 3: Third Quarter, 4: Fourth Quarter, 5: Pre-release) */
    private Integer fiscalPeriod;
    /** Currency */
    private String currency;
    /** Current period projected earnings per share */
    private String epsEst;
    /** Earnings per share for the same period last year */
    private String epsLy;
    /** Current period projected revenue */
    private String revEst;
    /** Revenue for the corresponding period of the previous year */
    private String revLy;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEpsEst() {
        return epsEst;
    }

    public void setEpsEst(String epsEst) {
        this.epsEst = epsEst;
    }

    public String getEpsLy() {
        return epsLy;
    }

    public void setEpsLy(String epsLy) {
        this.epsLy = epsLy;
    }

    public String getRevEst() {
        return revEst;
    }

    public void setRevEst(String revEst) {
        this.revEst = revEst;
    }

    public String getRevLy() {
        return revLy;
    }

    public void setRevLy(String revLy) {
        this.revLy = revLy;
    }

    @Override
    public String toString() {
        return "FinancialAlert{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", currency='" + currency + '\'' +
                ", epsEst='" + epsEst + '\'' +
                ", epsLy='" + epsLy + '\'' +
                ", revEst='" + revEst + '\'' +
                ", revLy='" + revLy + '\'' +
                '}';
    }
}
