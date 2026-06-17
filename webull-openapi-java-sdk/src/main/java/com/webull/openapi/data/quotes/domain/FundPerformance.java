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
 * Fund performance data.
 */
public class FundPerformance {

    /**
     * Currency
     */
    private String currency;

    /**
     * End date
     */
    private String endDate;

    /**
     * One month return
     */
    @SerializedName("return_1m")
    private String return1m;

    /**
     * Three month return
     */
    @SerializedName("return_3m")
    private String return3m;

    /**
     * Six month return
     */
    @SerializedName("return_6m")
    private String return6m;

    /**
     * One year return
     */
    @SerializedName("return_1y")
    private String return1y;

    /**
     * Three year return
     */
    @SerializedName("return_3y")
    private String return3y;

    /**
     * Five year return
     */
    @SerializedName("return_5y")
    private String return5y;

    /**
     * Ten year return
     */
    @SerializedName("return_10y")
    private String return10y;

    /**
     * Since inception return
     */
    @SerializedName("return_si")
    private String returnSi;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReturn1m() {
        return return1m;
    }

    public void setReturn1m(String return1m) {
        this.return1m = return1m;
    }

    public String getReturn3m() {
        return return3m;
    }

    public void setReturn3m(String return3m) {
        this.return3m = return3m;
    }

    public String getReturn6m() {
        return return6m;
    }

    public void setReturn6m(String return6m) {
        this.return6m = return6m;
    }

    public String getReturn1y() {
        return return1y;
    }

    public void setReturn1y(String return1y) {
        this.return1y = return1y;
    }

    public String getReturn3y() {
        return return3y;
    }

    public void setReturn3y(String return3y) {
        this.return3y = return3y;
    }

    public String getReturn5y() {
        return return5y;
    }

    public void setReturn5y(String return5y) {
        this.return5y = return5y;
    }

    public String getReturn10y() {
        return return10y;
    }

    public void setReturn10y(String return10y) {
        this.return10y = return10y;
    }

    public String getReturnSi() {
        return returnSi;
    }

    public void setReturnSi(String returnSi) {
        this.returnSi = returnSi;
    }

    @Override
    public String toString() {
        return "FundPerformance{" +
                "currency='" + currency + '\'' +
                ", endDate='" + endDate + '\'' +
                ", return1m='" + return1m + '\'' +
                ", return3m='" + return3m + '\'' +
                ", return6m='" + return6m + '\'' +
                ", return1y='" + return1y + '\'' +
                ", return3y='" + return3y + '\'' +
                ", return5y='" + return5y + '\'' +
                ", return10y='" + return10y + '\'' +
                ", returnSi='" + returnSi + '\'' +
                '}';
    }
}
