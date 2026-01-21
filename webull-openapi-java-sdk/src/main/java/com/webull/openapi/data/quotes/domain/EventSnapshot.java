package com.webull.openapi.data.quotes.domain;

public class EventSnapshot extends QuotesBasic {

    private String name;
    private String price;
    private String volume;
    private Long lastTradeTime;
    private String openInterest;
    private String yesBid;
    private String yesBidSize;
    private String yesAsk;
    private String yesAskSize;
    private String noBid;
    private String noBidSize;
    private String noAsk;
    private String noAskSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Long getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Long lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(String openInterest) {
        this.openInterest = openInterest;
    }

    public String getYesBid() {
        return yesBid;
    }

    public void setYesBid(String yesBid) {
        this.yesBid = yesBid;
    }

    public String getYesBidSize() {
        return yesBidSize;
    }

    public void setYesBidSize(String yesBidSize) {
        this.yesBidSize = yesBidSize;
    }

    public String getYesAsk() {
        return yesAsk;
    }

    public void setYesAsk(String yesAsk) {
        this.yesAsk = yesAsk;
    }

    public String getYesAskSize() {
        return yesAskSize;
    }

    public void setYesAskSize(String yesAskSize) {
        this.yesAskSize = yesAskSize;
    }

    public String getNoBid() {
        return noBid;
    }

    public void setNoBid(String noBid) {
        this.noBid = noBid;
    }

    public String getNoBidSize() {
        return noBidSize;
    }

    public void setNoBidSize(String noBidSize) {
        this.noBidSize = noBidSize;
    }

    public String getNoAsk() {
        return noAsk;
    }

    public void setNoAsk(String noAsk) {
        this.noAsk = noAsk;
    }

    public String getNoAskSize() {
        return noAskSize;
    }

    public void setNoAskSize(String noAskSize) {
        this.noAskSize = noAskSize;
    }

    @Override
    public String toString() {
        return "EventSnapshot{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", lastTradeTime=" + lastTradeTime +
                ", openInterest='" + openInterest + '\'' +
                ", yesBid='" + yesBid + '\'' +
                ", yesBidSize='" + yesBidSize + '\'' +
                ", yesAsk='" + yesAsk + '\'' +
                ", yesAskSize='" + yesAskSize + '\'' +
                ", noBid='" + noBid + '\'' +
                ", noBidSize='" + noBidSize + '\'' +
                ", noAsk='" + noAsk + '\'' +
                ", noAskSize='" + noAskSize + '\'' +
                '}';
    }
}