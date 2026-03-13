package com.webull.openapi.data.quotes.domain;

import java.util.Set;

public class EventInstrumentParam {

    private String seriesSymbol;
    private String eventSymbol;
    private Set<String> symbols;
    private String expirationDateAfter;
    private String lastInstrumentId;
    private int pageSize = 500;

    public String getSeriesSymbol() {
        return seriesSymbol;
    }

    public void setSeriesSymbol(String seriesSymbol) {
        this.seriesSymbol = seriesSymbol;
    }

    public String getEventSymbol() {
        return eventSymbol;
    }

    public void setEventSymbol(String eventSymbol) {
        this.eventSymbol = eventSymbol;
    }

    public Set<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<String> symbols) {
        this.symbols = symbols;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
