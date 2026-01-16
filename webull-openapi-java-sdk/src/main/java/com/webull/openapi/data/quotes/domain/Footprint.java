package com.webull.openapi.data.quotes.domain;

import java.util.Map;

public class Footprint {

    private String time;

    private String tradingSession;

    private String total;

    private String delta;

    private String buyTotal;

    private String sellTotal;

    private Map<String, String> buyDetail;

    private Map<String, String> sellDetail;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTradingSession() {
        return tradingSession;
    }

    public void setTradingSession(String tradingSession) {
        this.tradingSession = tradingSession;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getBuyTotal() {
        return buyTotal;
    }

    public void setBuyTotal(String buyTotal) {
        this.buyTotal = buyTotal;
    }

    public String getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(String sellTotal) {
        this.sellTotal = sellTotal;
    }

    public Map<String, String> getBuyDetail() {
        return buyDetail;
    }

    public void setBuyDetail(Map<String, String> buyDetail) {
        this.buyDetail = buyDetail;
    }

    public Map<String, String> getSellDetail() {
        return sellDetail;
    }

    public void setSellDetail(Map<String, String> sellDetail) {
        this.sellDetail = sellDetail;
    }

    @Override
    public String toString() {
        return "Footprint{" +
                "time='" + time + '\'' +
                ", tradingSession='" + tradingSession + '\'' +
                ", total='" + total + '\'' +
                ", delta='" + delta + '\'' +
                ", buyTotal='" + buyTotal + '\'' +
                ", sellTotal='" + sellTotal + '\'' +
                ", buyDetail=" + buyDetail +
                ", sellDetail=" + sellDetail +
                '}';
    }
}