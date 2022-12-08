package com.hiop.hisc.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessToken implements Serializable {
	private JsonNode header;
	private JsonNode payload;
	private String signature;

	public AccessToken() {
	}

	public AccessToken(JsonNode header, JsonNode payload, String signature) {
		this.header = header;
		this.payload = payload;
		this.signature = signature;
	}

	public JsonNode getHeader() {
		return this.header;
	}

	public void setHeader(JsonNode header) {
		this.header = header;
	}

	public JsonNode getPayload() {
		return this.payload;
	}

	public void setPayload(JsonNode payload) {
		this.payload = payload;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
