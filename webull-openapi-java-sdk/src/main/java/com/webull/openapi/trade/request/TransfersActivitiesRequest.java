package com.webull.openapi.trade.request;

import java.io.Serializable;

public class TransfersActivitiesRequest implements Serializable {

	private static final long serialVersionUID = 8723617558934621135L;
	private String accountId;
	private String transferMethod;
	private String direction;
	private String status;
	private String acatsTransferTypes;
	private String startTime;
	private String endTime;
	private String paginationKey;

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

	public String getAcatsTransferTypes() {
		return acatsTransferTypes;
	}

	public void setAcatsTransferTypes(String acatsTransferTypes) {
		this.acatsTransferTypes = acatsTransferTypes;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPaginationKey() {
		return paginationKey;
	}

	public void setPaginationKey(String paginationKey) {
		this.paginationKey = paginationKey;
	}

	@Override
	public String toString() {
		return "TransfersActivitiesRequest{" +
			"accountId='" + accountId + '\'' +
			", transferMethod='" + transferMethod + '\'' +
			", direction='" + direction + '\'' +
			", status='" + status + '\'' +
			", acatsTransferTypes='" + acatsTransferTypes + '\'' +
			", startTime='" + startTime + '\'' +
			", endTime='" + endTime + '\'' +
			", paginationKey='" + paginationKey + '\'' +
			'}';
	}

}
