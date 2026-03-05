package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class EventMarket {

    private String seriesId;
    private String seriesSymbol;
    private String seriesName;
    private String eventSymbol;
    private String eventName;
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
    private Boolean fractionable;
    private List<PriceRange> priceRanges;

    public static class PriceRange {
        private String start;
        private String end;
        private String step;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        @Override
        public String toString() {
            return "PriceRange{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    ", step='" + step + '\'' +
                    '}';
        }
    }

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

    public String getEventSymbol() {
        return eventSymbol;
    }

    public void setEventSymbol(String eventSymbol) {
        this.eventSymbol = eventSymbol;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public Boolean getFractionable() {
        return fractionable;
    }

    public void setFractionable(Boolean fractionable) {
        this.fractionable = fractionable;
    }

    public List<PriceRange> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRange> priceRanges) {
        this.priceRanges = priceRanges;
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
                ", fractionable='" + fractionable + '\'' +
                ", priceRanges='" + priceRanges + '\'' +
                '}';
    }
}