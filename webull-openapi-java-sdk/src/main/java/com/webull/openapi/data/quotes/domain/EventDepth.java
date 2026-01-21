package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class EventDepth extends QuotesBasic {

    private Long quoteTime;
    private List<AskBid> yesBids;
    private List<AskBid> noBids;

    public void setQuoteTime(Long quoteTime) {
        this.quoteTime = quoteTime;
    }

    public void setYesBids(List<AskBid> yesBids) {
        this.yesBids = yesBids;
    }

    public void setNoBids(List<AskBid> noBids) {
        this.noBids = noBids;
    }

    @Override
    public String toString() {
        return "EventDepth{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", quoteTime=" + quoteTime +
                ", yesBids=" + yesBids +
                ", noBids=" + noBids +
                '}';
    }

    public static class AskBid {
        private String price;
        private String size;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "AskBid{" +
                    "price='" + price + '\'' +
                    ", size='" + size + '\'' +
                    '}';
        }
    }

}
