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
 * A single macroeconomic calendar event (US only).
 */
public class EconomicCalendarEvent {

    private String eventName;
    private String publishDate;
    private String publishTime;
    private String period;
    private String unit;
    private String priorValue;
    private Boolean isFedRelated;
    private String indicatorType;
    private String frequency;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPriorValue() {
        return priorValue;
    }

    public void setPriorValue(String priorValue) {
        this.priorValue = priorValue;
    }

    public Boolean isFedRelated() {
        return isFedRelated;
    }

    public void setFedRelated(Boolean isFedRelated) {
        this.isFedRelated = isFedRelated;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "EconomicCalendarEvent{" +
                "eventName='" + eventName + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", period='" + period + '\'' +
                ", unit='" + unit + '\'' +
                ", priorValue='" + priorValue + '\'' +
                ", isFedRelated=" + isFedRelated +
                ", indicatorType='" + indicatorType + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
