package com.webull.openapi.trade.request.v3;

import java.io.Serializable;

public class NoPartyId implements Serializable {

	private static final long serialVersionUID = 7484835249509085505L;
	private String partyId;
	private String partyIdSource;
	private String partyRole;

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyIdSource() {
		return partyIdSource;
	}

	public void setPartyIdSource(String partyIdSource) {
		this.partyIdSource = partyIdSource;
	}

	public String getPartyRole() {
		return partyRole;
	}

	public void setPartyRole(String partyRole) {
		this.partyRole = partyRole;
	}

	@Override
	public String toString() {
		return "NoPartyId{" +
			"partyId='" + partyId + '\'' +
			", partyIdSource='" + partyIdSource + '\'' +
			", partyRole='" + partyRole + '\'' +
			'}';
	}

}
