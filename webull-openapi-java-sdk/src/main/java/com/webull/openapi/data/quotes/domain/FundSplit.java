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
 * Fund split entry.
 */
public class FundSplit {

    /**
     * Split date (date-time string)
     */
    private String splitDate;

    /**
     * Split type (e.g., MERGE, SPLIT)
     */
    private String splitType;

    /**
     * Split ratio (e.g., "1:2")
     */
    private String splitRatio;

    /**
     * Split from value
     */
    private Double from;

    /**
     * Split to value
     */
    private Double to;

    public String getSplitDate() {
        return splitDate;
    }

    public void setSplitDate(String splitDate) {
        this.splitDate = splitDate;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public String getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(String splitRatio) {
        this.splitRatio = splitRatio;
    }

    public Double getFrom() {
        return from;
    }

    public void setFrom(Double from) {
        this.from = from;
    }

    public Double getTo() {
        return to;
    }

    public void setTo(Double to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "FundSplit{" +
                "splitDate='" + splitDate + '\'' +
                ", splitType='" + splitType + '\'' +
                ", splitRatio='" + splitRatio + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
