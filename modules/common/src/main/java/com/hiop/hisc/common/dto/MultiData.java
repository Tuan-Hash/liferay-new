package com.hiop.hisc.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultiData<T> implements Serializable {
	private int index;
	private T data;

	public MultiData() {
	}

	public MultiData(int index, T data) {
		this.index = index;
		this.data = data;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
