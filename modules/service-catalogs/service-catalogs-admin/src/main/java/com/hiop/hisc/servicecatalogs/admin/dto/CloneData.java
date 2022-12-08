package com.hiop.hisc.servicecatalogs.admin.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloneData implements Serializable {
	private String originalId;
	private String cloneId;

	public CloneData() {
	}

	public CloneData(String originalId, String cloneId) {
		this.originalId = originalId;
		this.cloneId = cloneId;
	}

	public String getOriginalId() {
		return this.originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getCloneId() {
		return this.cloneId;
	}

	public void setCloneId(String cloneId) {
		this.cloneId = cloneId;
	}

}
