package com.webull.openapi.trade.request.v3;

import java.io.Serializable;

public class CloseContract implements Serializable {

	private static final long serialVersionUID = -7419816405749522220L;
	private String contractId;
	private String quantity;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CloseContract{" +
			"contractId='" + contractId + '\'' +
			", quantity='" + quantity + '\'' +
			'}';
	}

}
