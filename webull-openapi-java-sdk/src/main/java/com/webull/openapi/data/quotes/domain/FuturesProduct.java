package com.webull.openapi.data.quotes.domain;

public class FuturesProduct {

	private String code;
	private String name;
	private Integer productClassId;
	private String productClassName;
	private String exchangeCode;

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

	public Integer getProductClassId() {
		return productClassId;
	}

	public void setProductClassId(Integer productClassId) {
		this.productClassId = productClassId;
	}

	public String getProductClassName() {
		return productClassName;
	}

	public void setProductClassName(String productClassName) {
		this.productClassName = productClassName;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	@Override
	public String toString() {
		return "FuturesProduct{" +
			"code='" + code + '\'' +
			", name='" + name + '\'' +
			", productClassId=" + productClassId +
			", productClassName='" + productClassName + '\'' +
			", exchangeCode='" + exchangeCode + '\'' +
			'}';
	}

}
