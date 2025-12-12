package com.webull.openapi.trade.request.v3;

import java.io.Serializable;

public class OptionOrderItemLeg implements Serializable {

	private static final long serialVersionUID = -8045193494209303008L;
	private String id;
	private String clientOrderId;
	private String orderId;
	private String side;
	private String quantity;
	private String market;
	private String instrumentType;
	private String symbol;
	private String strikePrice;
	private String optionExpireDate;
	private String optionType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(String strikePrice) {
		this.strikePrice = strikePrice;
	}

	public String getOptionExpireDate() {
		return optionExpireDate;
	}

	public void setOptionExpireDate(String optionExpireDate) {
		this.optionExpireDate = optionExpireDate;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	@Override
	public String toString() {
		return "OptionOrderItemLeg{" +
			"id='" + id + '\'' +
			", clientOrderId='" + clientOrderId + '\'' +
			", orderId='" + orderId + '\'' +
			", side='" + side + '\'' +
			", quantity='" + quantity + '\'' +
			", market='" + market + '\'' +
			", instrumentType='" + instrumentType + '\'' +
			", symbol=" + symbol +
			", strikePrice='" + strikePrice + '\'' +
			", optionExpireDate='" + optionExpireDate + '\'' +
			", optionType='" + optionType + '\'' +
			'}';
	}

}
