/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.trade.response.v3;

import java.io.Serializable;
import java.util.List;

public class TradeBatchPlaceResponse implements Serializable {

    private static final long serialVersionUID = 4039283707131057830L;
    private Integer total;
    private Integer success;
    private Integer failed;
    private List<OrderResp> batchOrders;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public List<OrderResp> getBatchOrders() {
        return batchOrders;
    }

    public void setBatchOrders(List<OrderResp> batchOrders) {
        this.batchOrders = batchOrders;
    }

    public static class OrderResp {
        private String clientOrderId;
        private String orderId;
        private String errorCode;
        private String message;

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

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "OrderResp{" +
                    "clientOrderId='" + clientOrderId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", errorCode='" + errorCode + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeBatchPlaceResponse{" +
                "total=" + total +
                ", success=" + success +
                ", failed=" + failed +
                ", batchOrders=" + batchOrders +
                '}';
    }
}
