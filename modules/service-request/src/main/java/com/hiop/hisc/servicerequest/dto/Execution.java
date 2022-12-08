package com.hiop.hisc.servicerequest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Execution implements Serializable {
	private String id;
	@JsonProperty("st2execution_id")
	private String st2ExecutionId;
	@JsonProperty("is_active")
	private boolean isActive;

	public Execution() {
	}

	public Execution(String id, String st2ExecutionId, boolean isActive) {
		this.id = id;
		this.st2ExecutionId = st2ExecutionId;
		this.isActive = isActive;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSt2ExecutionId() {
		return this.st2ExecutionId;
	}

	public void setSt2ExecutionId(String st2ExecutionId) {
		this.st2ExecutionId = st2ExecutionId;
	}

	public boolean isIsActive() {
		return this.isActive;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
