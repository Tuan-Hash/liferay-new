package com.hiop.hisc.reports.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedToken implements Serializable {
	private String token;
	private String tokenId;
	private String expiration;

	public EmbedToken() {
	}

	public EmbedToken(String token, String tokenId, String expiration) {
		this.token = token;
		this.tokenId = tokenId;
		this.expiration = expiration;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getExpiration() {
		return this.expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

}
