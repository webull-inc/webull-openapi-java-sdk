package com.webull.openapi.trade.request.v3;

import java.io.Serializable;
import java.util.List;

public class TradeOrder implements Serializable {

	private static final long serialVersionUID = 4846716278564624438L;
	private String clientComboOrderId;
	private List<TradeOrderItem> newOrders;
    private List<TradeOrderItem> batchOrders;
	private List<TradeOrderItem> modifyOrders;
	private String clientOrderId;

	public String getClientComboOrderId() {
		return clientComboOrderId;
	}

	public void setClientComboOrderId(String clientComboOrderId) {
		this.clientComboOrderId = clientComboOrderId;
	}

	public List<TradeOrderItem> getNewOrders() {
		return newOrders;
	}

	public void setNewOrders(List<TradeOrderItem> newOrders) {
		this.newOrders = newOrders;
	}

    public List<TradeOrderItem> getBatchOrders() {
        return batchOrders;
    }

    public void setBatchOrders(List<TradeOrderItem> batchOrders) {
        this.batchOrders = batchOrders;
    }

    public List<TradeOrderItem> getModifyOrders() {
		return modifyOrders;
	}

	public void setModifyOrders(List<TradeOrderItem> modifyOrders) {
		this.modifyOrders = modifyOrders;
	}

	public String getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	@Override
	public String toString() {
		return "TradeOrder{" +
			"clientComboOrderId='" + clientComboOrderId + '\'' +
			", newOrders=" + newOrders +
			", modifyOrders=" + modifyOrders +
			", clientOrderId='" + clientOrderId + '\'' +
			'}';
	}

}
