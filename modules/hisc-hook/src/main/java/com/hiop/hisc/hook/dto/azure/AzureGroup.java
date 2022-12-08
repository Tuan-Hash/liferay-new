package com.hiop.hisc.hook.dto.azure;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AzureGroup implements Serializable {
	private String id;
	private String displayName;

	public AzureGroup() {
	}

	public AzureGroup(String id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
