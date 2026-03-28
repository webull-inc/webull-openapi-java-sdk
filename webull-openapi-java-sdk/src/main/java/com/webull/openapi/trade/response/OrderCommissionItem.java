package com.webull.openapi.trade.response;

public class OrderCommissionItem {

    private String actualCommission;

    private String receivableCommission;


    public String getActualCommission() {
        return actualCommission;
    }

    public void setActualCommission(String actualCommission) {
        this.actualCommission = actualCommission;
    }

    public String getReceivableCommission() {
        return receivableCommission;
    }

    public void setReceivableCommission(String receivableCommission) {
        this.receivableCommission = receivableCommission;
    }

    @Override
    public String toString() {
        return "OrderCommissionItem{" +
                "actualCommission='" + actualCommission + '\'' +
                ", receivableCommission='" + receivableCommission + '\'' +
                '}';
    }
}
