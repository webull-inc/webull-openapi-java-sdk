/*
 * Copyright 2022 Webull
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
package com.webull.openapi.trade.response;

import java.io.Serializable;

/**
 * Transfer record for the list endpoint (summary view).
 * One record represents a single physical transfer. Detailed fields such as
 * contra broker, cash and position entries are returned only by the detail
 * endpoint via {@link TransferDetail}.
 */
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Target account id.
     */
    private String accountId;

    /**
     * Transfer method: ACATS or CRYPTO_TRANSFER.
     */
    private String transferMethod;

    /**
     * Transfer record id.
     */
    private String transferId;

    /**
     * Transfer direction: INCOMING or OUTGOING.
     */
    private String direction;

    /**
     * Transfer status: PENDING, COMPLETED, REJECTED, FAILED, CANCELLED.
     */
    private String status;

    /**
     * Failure/rejection reason; only returned when status is REJECTED or FAILED.
     */
    private String failureReason;

    /**
     * ACATS associated control number (logical transfer grouping key); not supported for crypto.
     */
    private String acatsAssociatedNumber;

    /**
     * Transfer type: FULL, PARTIAL, RESIDUAL, RECLAIM, OTHER.
     */
    private String acatsTransferTypes;

    /**
     * Transfer create/apply time.
     */
    private String createTime;

    /**
     * Last status update time of the transfer record.
     */
    private String updateTime;

    /**
     * Transfer settlement date; empty when not completed.
     */
    private String transferSettlementDate;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(String transferMethod) {
        this.transferMethod = transferMethod;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getAcatsAssociatedNumber() {
        return acatsAssociatedNumber;
    }

    public void setAcatsAssociatedNumber(String acatsAssociatedNumber) {
        this.acatsAssociatedNumber = acatsAssociatedNumber;
    }

    public String getAcatsTransferTypes() {
        return acatsTransferTypes;
    }

    public void setAcatsTransferTypes(String acatsTransferTypes) {
        this.acatsTransferTypes = acatsTransferTypes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTransferSettlementDate() {
        return transferSettlementDate;
    }

    public void setTransferSettlementDate(String transferSettlementDate) {
        this.transferSettlementDate = transferSettlementDate;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "accountId='" + accountId + '\'' +
                ", transferMethod='" + transferMethod + '\'' +
                ", transferId='" + transferId + '\'' +
                ", direction='" + direction + '\'' +
                ", status='" + status + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", acatsAssociatedNumber='" + acatsAssociatedNumber + '\'' +
                ", acatsTransferTypes='" + acatsTransferTypes + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", transferSettlementDate='" + transferSettlementDate + '\'' +
                '}';
    }
}
