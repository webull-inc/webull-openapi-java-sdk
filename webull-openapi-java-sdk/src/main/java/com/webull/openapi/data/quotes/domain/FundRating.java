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
 * Fund rating entry.
 */
public class FundRating {

    /**
     * Rating date
     */
    private String ratingDate;

    /**
     * Name of the rating agency
     */
    private String ratingAgency;

    /**
     * Rating cycle (0: Since establishment, 3: 3 years, 5: 5 years, 10: 10 years)
     */
    private String ratingCycle;

    /**
     * Overall rating (1: Low, 2: Below Average, 3: Average, 4: Above Average, 5: High)
     */
    private Integer ratingResults;

    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getRatingAgency() {
        return ratingAgency;
    }

    public void setRatingAgency(String ratingAgency) {
        this.ratingAgency = ratingAgency;
    }

    public String getRatingCycle() {
        return ratingCycle;
    }

    public void setRatingCycle(String ratingCycle) {
        this.ratingCycle = ratingCycle;
    }

    public Integer getRatingResults() {
        return ratingResults;
    }

    public void setRatingResults(Integer ratingResults) {
        this.ratingResults = ratingResults;
    }

    @Override
    public String toString() {
        return "FundRating{" +
                "ratingDate='" + ratingDate + '\'' +
                ", ratingAgency='" + ratingAgency + '\'' +
                ", ratingCycle='" + ratingCycle + '\'' +
                ", ratingResults=" + ratingResults +
                '}';
    }
}
