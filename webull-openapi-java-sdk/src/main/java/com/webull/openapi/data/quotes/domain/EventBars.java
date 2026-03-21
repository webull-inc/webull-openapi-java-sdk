package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class EventBars extends QuotesBasic {

    private List<Kdata> result;

    public static class Kdata {

        private String open;

        private String close;

        private String high;

        private String low;

        private String volume;

        private String time;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Kdata{" +
                    "open='" + open + '\'' +
                    ", close='" + close + '\'' +
                    ", high='" + high + '\'' +
                    ", low='" + low + '\'' +
                    ", volume='" + volume + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    public List<Kdata> getResult() {
        return result;
    }

    public void setResult(List<Kdata> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EventBars{" +
                "result=" + result +
                ", symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                '}';
    }
}
