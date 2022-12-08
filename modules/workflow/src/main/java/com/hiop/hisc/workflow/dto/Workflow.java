package com.hiop.hisc.workflow.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Workflow implements Serializable {
	private String id;
	private String name;
	@JsonProperty("created_by")
	private String createdBy;
	@JsonProperty("created_at")
	private String createdAt;
    @JsonProperty("updated_at")
	private String updatedAt;
    @JsonProperty("is_active")
	private boolean active;
    @JsonProperty("is_deleted")
	private boolean delete;
    @JsonProperty("is_public")
	private boolean publish;
    @JsonProperty("meta_yaml")
    private JsonNode metaYaml;
    @JsonProperty("workflow_yaml")
    private JsonNode workflowYaml;

    public Workflow(String id, String name, String createdBy, String createdAt, String updatedAt, boolean active, boolean delete, boolean publish, JsonNode metaYaml, JsonNode workflowYaml) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.delete = delete;
        this.publish = publish;
        this.metaYaml = metaYaml;
        this.workflowYaml = workflowYaml;
    }

    public Workflow() {
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return String return the createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return String return the updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return boolean return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return boolean return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return boolean return the publish
     */
    public boolean isPublish() {
        return publish;
    }

    /**
     * @param publish the publish to set
     */
    public void setPublish(boolean publish) {
        this.publish = publish;
    }


    /**
     * @return JsonNode return the metaYaml
     */
    public JsonNode getMetaYaml() {
        return metaYaml;
    }

    /**
     * @param metaYaml the metaYaml to set
     */
    public void setMetaYaml(JsonNode metaYaml) {
        this.metaYaml = metaYaml;
    }

    /**
     * @return JsonNode return the workflowYaml
     */
    public JsonNode getWorkflowYaml() {
        return workflowYaml;
    }

    /**
     * @param workflowYaml the workflowYaml to set
     */
    public void setWorkflowYaml(JsonNode workflowYaml) {
        this.workflowYaml = workflowYaml;
    }

}
