package com.webull.openapi.data.quotes.domain;

public class EventMarket {

    private String seriesId;
    private String seriesSymbol;
    private String seriesName;
    private String instrumentId;
    private String symbol;
    private String name;
    private String yesCondition;
    private String lastTradingDate;
    private String status;
    private String tradableStatus;
    private Boolean canCloseEarly;
    private String expectedExpDate;
    private String latestExpDate;
    private String payoutDate;

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesSymbol() {
        return seriesSymbol;
    }

    public void setSeriesSymbol(String seriesSymbol) {
        this.seriesSymbol = seriesSymbol;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
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

    public String getYesCondition() {
        return yesCondition;
    }

    public void setYesCondition(String yesCondition) {
        this.yesCondition = yesCondition;
    }

    public String getLastTradingDate() {
        return lastTradingDate;
    }

    public void setLastTradingDate(String lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTradableStatus() {
        return tradableStatus;
    }

    public void setTradableStatus(String tradableStatus) {
        this.tradableStatus = tradableStatus;
    }

    public Boolean getCanCloseEarly() {
        return canCloseEarly;
    }

    public void setCanCloseEarly(Boolean canCloseEarly) {
        this.canCloseEarly = canCloseEarly;
    }

    public String getExpectedExpDate() {
        return expectedExpDate;
    }

    public void setExpectedExpDate(String expectedExpDate) {
        this.expectedExpDate = expectedExpDate;
    }

    public String getLatestExpDate() {
        return latestExpDate;
    }

    public void setLatestExpDate(String latestExpDate) {
        this.latestExpDate = latestExpDate;
    }

    public String getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(String payoutDate) {
        this.payoutDate = payoutDate;
    }

    @Override
    public String toString() {
        return "EventMarket{" +
                "seriesId='" + seriesId + '\'' +
                ", seriesSymbol='" + seriesSymbol + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", yesCondition='" + yesCondition + '\'' +
                ", lastTradingDate='" + lastTradingDate + '\'' +
                ", status='" + status + '\'' +
                ", tradableStatus='" + tradableStatus + '\'' +
                ", canCloseEarly=" + canCloseEarly +
                ", expectedExpDate='" + expectedExpDate + '\'' +
                ", latestExpDate='" + latestExpDate + '\'' +
                ", payoutDate='" + payoutDate + '\'' +
                '}';
    }
}