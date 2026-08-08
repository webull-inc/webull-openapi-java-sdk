package com.webull.openapi.trade.response.v3;

import java.io.Serializable;

/**
 * OrderExecution
 *
 * @author sulinfeng
 * @version 2026/07/30 15:00
 **/
public class OrderExecution implements Serializable {
    private static final long serialVersionUID = 7773597979831220775L;

    private String executionId;

    private String orderId;

    private String clientOrderId;

    private String symbol;

    private String executionTime;

    private String status;

    private String side;

    private String orderType;

    private String totalQuantity;

    private String limitPrice;

    private String filledQuantity;

    private String filledPrice;

    private String leavesQty;

    private String totalFilledQty;

    private String executionType;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public String getFilledPrice() {
        return filledPrice;
    }

    public void setFilledPrice(String filledPrice) {
        this.filledPrice = filledPrice;
    }

    public String getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(String leavesQty) {
        this.leavesQty = leavesQty;
    }

    public String getTotalFilledQty() {
        return totalFilledQty;
    }

    public void setTotalFilledQty(String totalFilledQty) {
        this.totalFilledQty = totalFilledQty;
    }

    public String getExecutionType() {
        return executionType;
    }

    public void setExecutionType(String executionType) {
        this.executionType = executionType;
    }

    @Override
    public String toString() {
        return "OrderExecution{" +
                "executionId='" + executionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", status='" + status + '\'' +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", filledPrice='" + filledPrice + '\'' +
                ", leavesQty='" + leavesQty + '\'' +
                ", totalFilledQty='" + totalFilledQty + '\'' +
                ", executionType='" + executionType + '\'' +
                '}';
    }
}
