package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class EventTick extends QuotesBasic {

    private List<Tick> result;

    public static class Tick {

        private String time;

        private String yesPrice;

        private String noPrice;

        private String volume;

        private String side;

        private String tradeId;

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getNoPrice() {
            return noPrice;
        }

        public void setNoPrice(String noPrice) {
            this.noPrice = noPrice;
        }

        public String getYesPrice() {
            return yesPrice;
        }

        public void setYesPrice(String yesPrice) {
            this.yesPrice = yesPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Tick{" +
                    "time='" + time + '\'' +
                    ", yesPrice='" + yesPrice + '\'' +
                    ", noPrice='" + noPrice + '\'' +
                    ", volume='" + volume + '\'' +
                    ", side='" + side + '\'' +
                    ", tradeId='" + tradeId + '\'' +
                    '}';
        }
    }

    public List<Tick> getResult() {
        return result;
    }

    public void setResult(List<Tick> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EventTick{" +
                "result=" + result +
                '}';
    }
}
