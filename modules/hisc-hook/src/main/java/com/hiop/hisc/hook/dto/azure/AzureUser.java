package com.hiop.hisc.hook.dto.azure;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AzureUser implements Serializable {
	@JsonProperty("oid")
	private String userId;
	@JsonProperty("upn")
	private String email;
	private String name;

	public AzureUser() {
	}

	public AzureUser(String userId, String email, String name) {
		this.userId = userId;
		this.email = email;
		this.name = name;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
