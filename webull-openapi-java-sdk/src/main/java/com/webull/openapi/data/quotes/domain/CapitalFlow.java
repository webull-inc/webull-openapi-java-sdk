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
 * Capital flow data for a single date.
 */
public class CapitalFlow {

    /**
     * Date in YYYYMMDD format
     */
    private String date;

    /**
     * Large order inflow amount
     */
    private String largeIn;

    /**
     * Large order outflow amount
     */
    private String largeOut;

    /**
     * Medium order inflow amount
     */
    private String mediumIn;

    /**
     * Medium order outflow amount
     */
    private String mediumOut;

    /**
     * Small order inflow amount
     */
    private String smallIn;

    /**
     * Small order outflow amount
     */
    private String smallOut;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLargeIn() {
        return largeIn;
    }

    public void setLargeIn(String largeIn) {
        this.largeIn = largeIn;
    }

    public String getLargeOut() {
        return largeOut;
    }

    public void setLargeOut(String largeOut) {
        this.largeOut = largeOut;
    }

    public String getMediumIn() {
        return mediumIn;
    }

    public void setMediumIn(String mediumIn) {
        this.mediumIn = mediumIn;
    }

    public String getMediumOut() {
        return mediumOut;
    }

    public void setMediumOut(String mediumOut) {
        this.mediumOut = mediumOut;
    }

    public String getSmallIn() {
        return smallIn;
    }

    public void setSmallIn(String smallIn) {
        this.smallIn = smallIn;
    }

    public String getSmallOut() {
        return smallOut;
    }

    public void setSmallOut(String smallOut) {
        this.smallOut = smallOut;
    }

    @Override
    public String toString() {
        return "CapitalFlow{" +
                "date='" + date + '\'' +
                ", largeIn='" + largeIn + '\'' +
                ", largeOut='" + largeOut + '\'' +
                ", mediumIn='" + mediumIn + '\'' +
                ", mediumOut='" + mediumOut + '\'' +
                ", smallIn='" + smallIn + '\'' +
                ", smallOut='" + smallOut + '\'' +
                '}';
    }
}
