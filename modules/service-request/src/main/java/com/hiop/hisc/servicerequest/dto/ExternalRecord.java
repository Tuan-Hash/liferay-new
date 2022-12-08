package com.hiop.hisc.servicerequest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalRecord implements Serializable {
	private String id;
	@JsonProperty("external_id")
	private String externalId;
	@JsonProperty("external_type")
	private String externalType;
	@JsonProperty("external_state")
	private String externalState;
	@JsonProperty("request")
	private String requestId;

	public ExternalRecord() {
	}

	public ExternalRecord(String id, String externalId, String externalType, String externalState, String requestId) {
		this.id = id;
		this.externalId = externalId;
		this.externalType = externalType;
		this.externalState = externalState;
		this.requestId = requestId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExternalId() {
		if (this.externalId == null) {
			return "";
		}

		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalType() {
		return this.externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}

	public String getExternalState() {
		return this.externalState;
	}

	public void setExternalState(String externalState) {
		this.externalState = externalState;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
