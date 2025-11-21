package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class DepthOfBook {

	private String symbol;
	private String instrumentId;
	private Long quoteTime;
	private List<AskBid> asks;
	private List<AskBid> bids;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public Long getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Long quoteTime) {
		this.quoteTime = quoteTime;
	}

	public List<AskBid> getAsks() {
		return asks;
	}

	public void setAsks(List<AskBid> asks) {
		this.asks = asks;
	}

	public List<AskBid> getBids() {
		return bids;
	}

	public void setBids(List<AskBid> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		return "DepthOfBook{" +
			"symbol='" + symbol + '\'' +
			", instrumentId='" + instrumentId + '\'' +
			", quoteTime=" + quoteTime +
			", asks=" + asks +
			", bids=" + bids +
			'}';
	}

}
