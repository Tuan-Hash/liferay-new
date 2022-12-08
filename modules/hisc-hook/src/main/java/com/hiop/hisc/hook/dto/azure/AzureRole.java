package com.hiop.hisc.hook.dto.azure;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AzureRole implements Serializable {
	private String id;
	private String resourceDisplayName;

	public AzureRole() {
	}

	public AzureRole(String id, String resourceDisplayName) {
		this.id = id;
		this.resourceDisplayName = resourceDisplayName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceDisplayName() {
		return this.resourceDisplayName;
	}

	public void setResourceDisplayName(String resourceDisplayName) {
		this.resourceDisplayName = resourceDisplayName;
	}

}
