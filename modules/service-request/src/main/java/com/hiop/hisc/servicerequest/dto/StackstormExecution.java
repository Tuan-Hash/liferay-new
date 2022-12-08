package com.hiop.hisc.servicerequest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StackstormExecution implements Serializable {
	private String id;
	private String name;
	private String status;
	private ArrayNode children;
	@JsonProperty("user")
	private String userId;
	@JsonProperty("start_time")
	private String startTime;

	public StackstormExecution() {
	}

	public StackstormExecution(String id, String name, String status, ArrayNode children, String userId,
			String startTime) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.children = children;
		this.userId = userId;
		this.startTime = startTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayNode getChildren() {
		return this.children;
	}

	public void setChildren(ArrayNode children) {
		this.children = children;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
