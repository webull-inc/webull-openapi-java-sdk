/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class Quote extends QuotesBasic {

    private Long quoteTime;
    private List<AskBid> asks;
    private List<AskBid> bids;
    private String tradingSession;

    public List<AskBid> getAsks() {
        return asks;
    }

    public void setAsks(List<AskBid> asks) {
        this.asks = asks;
    }

    public List<AskBid> getBids() {
        return bids;
    }

    public Long getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Long quoteTime) {
        this.quoteTime = quoteTime;
    }

    public void setBids(List<AskBid> bids) {
        this.bids = bids;
    }

    public String getTradingSession() {
        return tradingSession;
    }

    public void setTradingSession(String tradingSession) {
        this.tradingSession = tradingSession;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "asks=" + asks +
                ", bids=" + bids +
                ", symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", quoteTime='" + quoteTime + '\'' +
                ", tradingSession='" + tradingSession + '\'' +
                '}';
    }
}
