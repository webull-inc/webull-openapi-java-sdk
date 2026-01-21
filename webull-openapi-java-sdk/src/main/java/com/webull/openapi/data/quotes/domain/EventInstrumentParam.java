package com.webull.openapi.data.quotes.domain;

public class EventInstrumentParam {

    private String category;
    private String seriesSymbol;
    private String expirationDateAfter;
    private String lastInstrumentId;
    private int pageSize = 500;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSeriesSymbol() {
        return seriesSymbol;
    }

    public void setSeriesSymbol(String seriesSymbol) {
        this.seriesSymbol = seriesSymbol;
    }

    public String getExpirationDateAfter() {
        return expirationDateAfter;
    }

    public void setExpirationDateAfter(String expirationDateAfter) {
        this.expirationDateAfter = expirationDateAfter;
    }

    public String getLastInstrumentId() {
        return lastInstrumentId;
    }

    public void setLastInstrumentId(String lastInstrumentId) {
        this.lastInstrumentId = lastInstrumentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
