package com.webull.openapi.data.quotes.domain;

public class FuturesInstrument {

	private String symbol;
	private String instrumentId;
	private String contractId;
	private String exchangeCode;
	private String code;
	private String name;
	private String currency;
	private String contractMonth;
	private String settlementDate;
	private Double size;
	private String unit;
	private Double minTick;
	private String firstNoticeDate;
	private String lastNoticeDate;
	private String firstTradingDate;
	private String lastTradingDate;
	private String contractType;
	private String settlement;

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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getContractMonth() {
		return contractMonth;
	}

	public void setContractMonth(String contractMonth) {
		this.contractMonth = contractMonth;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getMinTick() {
		return minTick;
	}

	public void setMinTick(Double minTick) {
		this.minTick = minTick;
	}

	public String getFirstNoticeDate() {
		return firstNoticeDate;
	}

	public void setFirstNoticeDate(String firstNoticeDate) {
		this.firstNoticeDate = firstNoticeDate;
	}

	public String getLastNoticeDate() {
		return lastNoticeDate;
	}

	public void setLastNoticeDate(String lastNoticeDate) {
		this.lastNoticeDate = lastNoticeDate;
	}

	public String getFirstTradingDate() {
		return firstTradingDate;
	}

	public void setFirstTradingDate(String firstTradingDate) {
		this.firstTradingDate = firstTradingDate;
	}

	public String getLastTradingDate() {
		return lastTradingDate;
	}

	public void setLastTradingDate(String lastTradingDate) {
		this.lastTradingDate = lastTradingDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	@Override
	public String toString() {
		return "FuturesInstrument{" +
			"symbol='" + symbol + '\'' +
			", instrumentId='" + instrumentId + '\'' +
			", contractId='" + contractId + '\'' +
			", exchangeCode='" + exchangeCode + '\'' +
			", code='" + code + '\'' +
			", name='" + name + '\'' +
			", currency='" + currency + '\'' +
			", contractMonth='" + contractMonth + '\'' +
			", settlementDate='" + settlementDate + '\'' +
			", size=" + size +
			", unit='" + unit + '\'' +
			", minTick=" + minTick +
			", firstNoticeDate='" + firstNoticeDate + '\'' +
			", lastNoticeDate='" + lastNoticeDate + '\'' +
			", firstTradingDate='" + firstTradingDate + '\'' +
			", lastTradingDate='" + lastTradingDate + '\'' +
			", contract_type='" + contractType + '\'' +
			", settlement='" + settlement + '\'' +
			'}';
	}

}
