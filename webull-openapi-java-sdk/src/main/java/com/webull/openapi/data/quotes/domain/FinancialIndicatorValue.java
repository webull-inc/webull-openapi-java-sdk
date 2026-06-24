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
 * A single financial indicator value for a specific period.
 */
public class FinancialIndicatorValue {

    private Integer fiscalYear;
    private Integer fiscalPeriod;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FinancialIndicatorValue{" +
                "fiscalYear=" + fiscalYear +
                ", fiscalPeriod=" + fiscalPeriod +
                ", value='" + value + '\'' +
                '}';
    }
}
