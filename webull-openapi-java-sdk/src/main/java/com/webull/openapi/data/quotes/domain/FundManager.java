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
 * Fund manager details.
 */
public class FundManager {

    /** Name */
    private String name;
    /** Start date of employment */
    private String startDate;
    /** End date of employment */
    private String endDate;
    /** Whether currently incumbent (1 = yes) */
    private Integer isIncumbent;
    /** Term-based returns */
    private String tenureReturn;
    /** Fund Manager Position */
    private String title;
    /** Period of Service */
    private String tenureYears;
    /** Term days */
    private Integer tenureDays;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getIsIncumbent() {
        return isIncumbent;
    }

    public void setIsIncumbent(Integer isIncumbent) {
        this.isIncumbent = isIncumbent;
    }

    public String getTenureReturn() {
        return tenureReturn;
    }

    public void setTenureReturn(String tenureReturn) {
        this.tenureReturn = tenureReturn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(String tenureYears) {
        this.tenureYears = tenureYears;
    }

    public Integer getTenureDays() {
        return tenureDays;
    }

    public void setTenureDays(Integer tenureDays) {
        this.tenureDays = tenureDays;
    }

    @Override
    public String toString() {
        return "FundManager{" +
                "name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isIncumbent=" + isIncumbent +
                ", tenureReturn='" + tenureReturn + '\'' +
                ", title='" + title + '\'' +
                ", tenureYears='" + tenureYears + '\'' +
                ", tenureDays=" + tenureDays +
                '}';
    }
}
