package com.hiop.hisc.servicecatalogs.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Catalog implements Serializable {
  private String id;
  private Category category;
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
  @JsonProperty("workflow_id")
  private String workflowId;
  private String alias;

  public Catalog() {
  }

  public Catalog(String id, Category category, String name, String description, boolean isActive,
      String icon, String image, JsonNode jsonSchema, JsonNode uiSchema, JsonNode formData, String workflowId,
      String alias) {
    this.id = id;
    this.category = category;
    this.name = name;
    this.description = description;
    this.isActive = isActive;
    this.icon = icon;
    this.image = image;
    this.jsonSchema = jsonSchema;
    this.uiSchema = uiSchema;
    this.formData = formData;
    this.workflowId = workflowId;
    this.alias = alias;
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

  public JsonNode getJsonSchema() {
    return this.jsonSchema;
  }

  public void setJsonSchema(JsonNode jsonSchema) {
    this.jsonSchema = jsonSchema;
  }

  public JsonNode getUiSchema() {
    return this.uiSchema;
  }

  public void setUiSchema(JsonNode uiSchema) {
    this.uiSchema = uiSchema;
  }

  public JsonNode getFormData() {
    return this.formData;
  }

  public void setFormData(JsonNode formData) {
    this.formData = formData;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public boolean getIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public String getWorkflowId() {
    return this.workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

}
