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
 * Company profile information.
 */
public class CompanyProfile {

    /**
     * Security symbol
     */
    private String symbol;

    /**
     * Security type
     */
    private String category;

    /**
     * Company name
     */
    private String companyName;

    /**
     * Date of incorporation
     */
    private String establishDate;

    /**
     * The current market where the target is located
     */
    private String exhibitionCode;

    /**
     * Company profile description
     */
    private String profile;

    /**
     * Number of employees
     */
    private String employees;

    /**
     * Headquarters address
     */
    private String address;

    /**
     * Company CEO
     */
    private String ceo;

    /**
     * Company industries
     */
    private List<String> industries;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getExhibitionCode() {
        return exhibitionCode;
    }

    public void setExhibitionCode(String exhibitionCode) {
        this.exhibitionCode = exhibitionCode;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public List<String> getIndustries() {
        return industries;
    }

    public void setIndustries(List<String> industries) {
        this.industries = industries;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "symbol='" + symbol + '\'' +
                ", category='" + category + '\'' +
                ", companyName='" + companyName + '\'' +
                ", establishDate='" + establishDate + '\'' +
                ", exhibitionCode='" + exhibitionCode + '\'' +
                ", profile='" + profile + '\'' +
                ", employees='" + employees + '\'' +
                ", address='" + address + '\'' +
                ", ceo='" + ceo + '\'' +
                ", industries=" + industries +
                '}';
    }
}
