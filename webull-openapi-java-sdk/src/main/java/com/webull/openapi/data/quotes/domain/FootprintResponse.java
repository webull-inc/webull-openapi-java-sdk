package com.webull.openapi.data.quotes.domain;


import java.util.List;

public class FootprintResponse extends QuotesBasic{

    private List<Footprint> result;

    public List<Footprint> getResult() {
        return result;
    }

    public void setResult(List<Footprint> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "FootprintResponse{" +
                "symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", result=" + result +
                '}';
    }
}