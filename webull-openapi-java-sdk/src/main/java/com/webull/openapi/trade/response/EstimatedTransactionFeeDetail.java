package com.webull.openapi.trade.response;

import java.util.List;

public class EstimatedTransactionFeeDetail {

    private OrderCommissionItem commission;

    private List<OrderFeeItem> fees;


    public OrderCommissionItem getCommission() {
        return commission;
    }

    public void setCommission(OrderCommissionItem commission) {
        this.commission = commission;
    }

    public List<OrderFeeItem> getFees() {
        return fees;
    }

    public void setFees(List<OrderFeeItem> fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "EstimatedTransactionFeeDetail{" +
                "commission=" + commission +
                ", fees=" + fees +
                '}';
    }
}
