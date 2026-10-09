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
 * Market sentiment (fear & greed) index with current and historical comparison points (US only).
 */
public class FearGreedIndex {

    private String timeZone;
    private SentimentPoint current;
    private SentimentPoint previousDay;
    private SentimentPoint oneWeekAgo;
    private SentimentPoint oneMonthAgo;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public SentimentPoint getCurrent() {
        return current;
    }

    public void setCurrent(SentimentPoint current) {
        this.current = current;
    }

    public SentimentPoint getPreviousDay() {
        return previousDay;
    }

    public void setPreviousDay(SentimentPoint previousDay) {
        this.previousDay = previousDay;
    }

    public SentimentPoint getOneWeekAgo() {
        return oneWeekAgo;
    }

    public void setOneWeekAgo(SentimentPoint oneWeekAgo) {
        this.oneWeekAgo = oneWeekAgo;
    }

    public SentimentPoint getOneMonthAgo() {
        return oneMonthAgo;
    }

    public void setOneMonthAgo(SentimentPoint oneMonthAgo) {
        this.oneMonthAgo = oneMonthAgo;
    }

    @Override
    public String toString() {
        return "FearGreedIndex{" +
                "timeZone='" + timeZone + '\'' +
                ", current=" + current +
                ", previousDay=" + previousDay +
                ", oneWeekAgo=" + oneWeekAgo +
                ", oneMonthAgo=" + oneMonthAgo +
                '}';
    }

    /**
     * A single sentiment data point (date, score, grade, description).
     */
    public static class SentimentPoint {
        private String date;
        private String time;
        private Integer fearGreedIndex;
        private String grade;
        private String describe;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getFearGreedIndex() {
            return fearGreedIndex;
        }

        public void setFearGreedIndex(Integer fearGreedIndex) {
            this.fearGreedIndex = fearGreedIndex;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        @Override
        public String toString() {
            return "SentimentPoint{" +
                    "date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", fearGreedIndex=" + fearGreedIndex +
                    ", grade='" + grade + '\'' +
                    ", describe='" + describe + '\'' +
                    '}';
        }
    }
}
