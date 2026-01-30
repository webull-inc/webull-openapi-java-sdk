package com.webull.openapi.data.quotes.domain;

public class EventSeries {

    private String category;
    private Integer instrumentId;
    private String symbol;
    private String name;
    private String frequency;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
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
                ", instrumentId=" + instrumentId +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }

}
