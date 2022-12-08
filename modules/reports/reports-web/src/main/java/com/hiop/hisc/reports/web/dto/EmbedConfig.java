package com.hiop.hisc.reports.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedConfig implements Serializable {
	private String id;
	private String datasetId;
	private String embedUrl;
	private EmbedToken embedToken;

	public EmbedConfig() {
	}

	public EmbedConfig(String id, String datasetId, String embedUrl) {
		this.id = id;
		this.datasetId = datasetId;
		this.embedUrl = embedUrl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatasetId() {
		return this.datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getEmbedUrl() {
		return this.embedUrl;
	}

	public void setEmbedUrl(String embedUrl) {
		this.embedUrl = embedUrl;
	}

	public EmbedToken getEmbedToken() {
		return this.embedToken;
	}

	public void setEmbedToken(EmbedToken embedToken) {
		this.embedToken = embedToken;
	}

}
