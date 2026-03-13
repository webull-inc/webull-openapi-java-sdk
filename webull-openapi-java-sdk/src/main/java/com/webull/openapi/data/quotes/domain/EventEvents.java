package com.webull.openapi.data.quotes.domain;

public class EventEvents {

    private String seriesId;
    private String symbol;
    private String name;
    private String status;
    private String shortName;
    private String strikeDate;
    private String strikePeriod;
    private boolean mutuallyExclusive;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStrikeDate() {
        return strikeDate;
    }

    public void setStrikeDate(String strikeDate) {
        this.strikeDate = strikeDate;
    }

    public String getStrikePeriod() {
        return strikePeriod;
    }

    public void setStrikePeriod(String strikePeriod) {
        this.strikePeriod = strikePeriod;
    }

    public boolean isMutuallyExclusive() {
        return mutuallyExclusive;
    }

    public void setMutuallyExclusive(boolean mutuallyExclusive) {
        this.mutuallyExclusive = mutuallyExclusive;
    }

    @Override
    public String toString() {
        return "EventEvents{" +
                "seriesId='" + seriesId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", shortName='" + shortName + '\'' +
                ", strikeDate='" + strikeDate + '\'' +
                ", strikePeriod='" + strikePeriod + '\'' +
                ", mutuallyExclusive='" + mutuallyExclusive + '\'' +
                '}';
    }
}
