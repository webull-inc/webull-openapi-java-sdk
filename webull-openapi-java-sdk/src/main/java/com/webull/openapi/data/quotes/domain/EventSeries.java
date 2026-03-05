package com.webull.openapi.data.quotes.domain;

public class EventSeries {

    private String category;
    private String seriesId;
    private String symbol;
    private String name;
    private String frequency;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "EventSeries{" +
                "category='" + category + '\'' +
                ", seriesId=" + seriesId +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }

}
