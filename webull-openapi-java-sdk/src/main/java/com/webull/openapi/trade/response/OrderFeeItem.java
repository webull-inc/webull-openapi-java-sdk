package com.webull.openapi.trade.response;

public class OrderFeeItem {

    private String type;

    private String actualValue;

    private String receivableValue;


    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getReceivableValue() {
        return receivableValue;
    }

    public void setReceivableValue(String receivableValue) {
        this.receivableValue = receivableValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderFeeItem{" +
                "type='" + type + '\'' +
                ", actualValue='" + actualValue + '\'' +
                ", receivableValue='" + receivableValue + '\'' +
                '}';
    }
}
