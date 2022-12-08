package com.hiop.hisc.servicerequest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Catalog implements Serializable {
	private String id;
	private String name;
	private String description;
	@JsonProperty("is_active")
	private boolean isActive;
	private String icon;
	private String image;
	@JsonProperty("json_schema")
	private JsonNode jsonSchema;
	@JsonProperty("ui_schema")
	private JsonNode uiSchema;
	@JsonProperty("form_data")
	private JsonNode formData;

	public Catalog() {
	}

	public Catalog(String id, String name, String description, boolean isActive,
			String icon, String image, JsonNode jsonSchema, JsonNode uiSchema, JsonNode formData) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.icon = icon;
		this.image = image;
		this.jsonSchema = jsonSchema;
		this.uiSchema = uiSchema;
		this.formData = formData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getJsonSchema() {
		return jsonSchema.toString();
	}

	public void setJsonSchema(JsonNode jsonSchema) {
		this.jsonSchema = jsonSchema;
	}

	public String getUiSchema() {
		return uiSchema.toString();
	}

	public void setUiSchema(JsonNode uiSchema) {
		this.uiSchema = uiSchema;
	}

	public String getFormData() {
		return formData.toString();
	}

	public void setFormData(JsonNode formData) {
		this.formData = formData;
	}

}
