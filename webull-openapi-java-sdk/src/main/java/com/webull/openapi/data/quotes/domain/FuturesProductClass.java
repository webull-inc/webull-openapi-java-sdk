package com.webull.openapi.data.quotes.domain;

public class FuturesProductClass {

	private Integer productClassId;
	private String productClassName;

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

	@Override
	public String toString() {
		return "FuturesProductClass{" +
			"productClassId=" + productClassId +
			", productClassName='" + productClassName + '\'' +
			'}';
	}

}
