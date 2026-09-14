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
import java.util.List;

/**
 * Transfer record for the detail endpoint (full view).
 * One record represents a single physical transfer, with its own transfer id,
 * create time and asset list. Covers both ACATS and crypto transfer methods;
 * fields specific to one method are null for the other.
 */
public class TransferDetail implements Serializable {

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
     * Transfer status: PENDING, COMPLETED, REJECTED, FAILED, CANCELLED .
     */
    private String status;

    /**
     * Failure/rejection reason; only returned when status is REJECTED or FAILED.
     */
    private String failureReason;

    /**
     * ACATS control number; not supported for crypto.
     */
    private String acatsControlNumber;

    /**
     * ACATS associated control number (logical transfer grouping key); not supported for crypto.
     */
    private String acatsAssociatedNumber;

    /**
     * Transfer type: FULL, PARTIAL, RESIDUAL, RECLAIM, OTHER.
     */
    private String acatsTransferTypes;

    /**
     * Contra broker; returned for ACATS, null for crypto.
     */
    private ContraBroker contraBroker;

    /**
     * On-chain transaction hash; returned for crypto, null for ACATS.
     */
    private String cryptoTransactionHash;

    /**
     * On-chain source address; returned for crypto, null for ACATS.
     */
    private String cryptoFromAddress;

    /**
     * On-chain destination address; returned for crypto, null for ACATS.
     */
    private String cryptoToAddress;

    /**
     * Cash entries; null for crypto which carries no cash.
     */
    private List<TransferCashInfo> cash;

    /**
     * Position (holding) entries.
     */
    private List<TransferPositionInfo> positions;

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

    public String getAcatsControlNumber() {
        return acatsControlNumber;
    }

    public void setAcatsControlNumber(String acatsControlNumber) {
        this.acatsControlNumber = acatsControlNumber;
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

    public ContraBroker getContraBroker() {
        return contraBroker;
    }

    public void setContraBroker(ContraBroker contraBroker) {
        this.contraBroker = contraBroker;
    }

    public String getCryptoTransactionHash() {
        return cryptoTransactionHash;
    }

    public void setCryptoTransactionHash(String cryptoTransactionHash) {
        this.cryptoTransactionHash = cryptoTransactionHash;
    }

    public String getCryptoFromAddress() {
        return cryptoFromAddress;
    }

    public void setCryptoFromAddress(String cryptoFromAddress) {
        this.cryptoFromAddress = cryptoFromAddress;
    }

    public String getCryptoToAddress() {
        return cryptoToAddress;
    }

    public void setCryptoToAddress(String cryptoToAddress) {
        this.cryptoToAddress = cryptoToAddress;
    }

    public List<TransferCashInfo> getCash() {
        return cash;
    }

    public void setCash(List<TransferCashInfo> cash) {
        this.cash = cash;
    }

    public List<TransferPositionInfo> getPositions() {
        return positions;
    }

    public void setPositions(List<TransferPositionInfo> positions) {
        this.positions = positions;
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
        return "TransferDetail{" +
                "accountId='" + accountId + '\'' +
                ", transferMethod='" + transferMethod + '\'' +
                ", transferId='" + transferId + '\'' +
                ", direction='" + direction + '\'' +
                ", status='" + status + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", acatsControlNumber='" + acatsControlNumber + '\'' +
                ", acatsAssociatedNumber='" + acatsAssociatedNumber + '\'' +
                ", acatsTransferTypes='" + acatsTransferTypes + '\'' +
                ", contraBroker=" + contraBroker +
                ", cryptoTransactionHash='" + cryptoTransactionHash + '\'' +
                ", cryptoFromAddress='" + cryptoFromAddress + '\'' +
                ", cryptoToAddress='" + cryptoToAddress + '\'' +
                ", cash=" + cash +
                ", positions=" + positions +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", transferSettlementDate='" + transferSettlementDate + '\'' +
                '}';
    }

    /**
     * Contra broker of an ACATS transfer.
     */
    public static class ContraBroker implements Serializable {

        private static final long serialVersionUID = 1L;

        private String brokerName;

        private String dtcNumber;

        private String accountType;

        private String accountNumber;

        public String getBrokerName() {
            return brokerName;
        }

        public void setBrokerName(String brokerName) {
            this.brokerName = brokerName;
        }

        public String getDtcNumber() {
            return dtcNumber;
        }

        public void setDtcNumber(String dtcNumber) {
            this.dtcNumber = dtcNumber;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        @Override
        public String toString() {
            return "ContraBroker{" +
                    "brokerName='" + brokerName + '\'' +
                    ", dtcNumber='" + dtcNumber + '\'' +
                    ", accountType='" + accountType + '\'' +
                    ", accountNumber='" + accountNumber + '\'' +
                    '}';
        }
    }
}
