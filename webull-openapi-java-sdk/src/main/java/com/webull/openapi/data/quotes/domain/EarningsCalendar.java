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
 * Earnings calendar entry for a stock.
 */
public class EarningsCalendar {

    /**
     * Fiscal year
     */
    private Integer fiscalYear;

    /**
     * Fiscal period (quarter)
     */
    private Integer fiscalPeriod;

    /**
     * Currency code (e.g., USD)
     */
    private String currency;

    /**
     * Expected publish date in YYYY-MM-DD format
     */
    private String expectedPublishDate;

    /**
     * Actual EPS
     */
    private String epsActual;

    /**
     * Estimated EPS
     */
    private String epsEst;

    /**
     * Actual revenue
     */
    private String revActual;

    /**
     * Estimated revenue
     */
    private String revEst;

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

    public String getExpectedPublishDate() {
        return expectedPublishDate;
    }

    public void setExpectedPublishDate(String expectedPublishDate) {
        this.expectedPublishDate = expectedPublishDate;
    }

    public String getEpsActual() {
        return epsActual;
    }

    public void setEpsActual(String epsActual) {
        this.epsActual = epsActual;
    }

    public String getEpsEst() {
        return epsEst;
    }

    public void setEpsEst(String epsEst) {
        this.epsEst = epsEst;
    }

    public String getRevActual() {
        return revActual;
    }

    public void setRevActual(String revActual) {
        this.revActual = revActual;
    }

    public String getRevEst() {
        return revEst;
    }

    public void setRevEst(String revEst) {
        this.revEst = revEst;
    }

    @Override
    public String toString() {
        return "EarningsCalendar{" +
                "fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", currency='" + currency + '\'' +
                ", expectedPublishDate='" + expectedPublishDate + '\'' +
                ", epsActual='" + epsActual + '\'' +
                ", epsEst='" + epsEst + '\'' +
                ", revActual='" + revActual + '\'' +
                ", revEst='" + revEst + '\'' +
                '}';
    }
}
