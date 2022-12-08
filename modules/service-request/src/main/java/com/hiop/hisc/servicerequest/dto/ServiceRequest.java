package com.hiop.hisc.servicerequest.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.hiop.hisc.servicerequest.constants.ServiceRequestPortletKeys;
import com.hiop.hisc.servicerequest.service.impl.RequestServiceImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceRequest implements Serializable {
  private String id;
  private String number;
  private String state;
  @JsonProperty("is_active")
  private boolean isActive;
  private Catalog catalog;
  @JsonProperty("json_schema")
  private JsonNode jsonSchema;
  @JsonProperty("ui_schema")
  private JsonNode uiSchema;
  @JsonProperty("form_data")
  private JsonNode formData;
  @JsonProperty("created_by")
  private String createdBy;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("updated_at")
  private String updatedAt;
  @JsonProperty("external_records")
  private List<ExternalRecord> externalRecords;
  @JsonProperty("execution")
  private String executionId;
  private Execution relatedExecution;

  public ServiceRequest() {
  }

  public ServiceRequest(String id, String number, String state, boolean isActive, Catalog catalog, JsonNode jsonSchema,
      JsonNode uiSchema, JsonNode formData, String executionId) {
    this.id = id;
    this.number = number;
    this.state = state;
    this.isActive = isActive;
    this.catalog = catalog;
    this.jsonSchema = jsonSchema;
    this.uiSchema = uiSchema;
    this.formData = formData;
    this.executionId = executionId;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
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

  public Catalog getCatalog() {
    return this.catalog;
  }

  public void setCatalog(Catalog catalog) {
    this.catalog = catalog;
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

  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<ExternalRecord> getExternalRecords() {
    return this.externalRecords;
  }

  public void setExternalRecords(List<ExternalRecord> externalRecords) {
    this.externalRecords = externalRecords;
  }

  public String getExecutionId() {
    return this.executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public Execution getRelatedExecution() {
    return this.relatedExecution;
  }

  public void setRelatedExecution(Execution relatedExecution) {
    this.relatedExecution = relatedExecution;
  }


  public ExternalRecord getServiceNowRequest() {
    List<ExternalRecord> serviceNowRequests = RequestServiceImpl.findByExternalType(externalRecords,
        ServiceRequestPortletKeys.SERVICENOW_REQUEST);

    if (serviceNowRequests.isEmpty()) {
      return new ExternalRecord();
    }

    return serviceNowRequests.get(0);
  }

}
