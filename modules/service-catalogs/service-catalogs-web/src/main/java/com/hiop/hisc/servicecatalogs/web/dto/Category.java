package com.hiop.hisc.servicecatalogs.web.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements Serializable {
  private String id;
  private String name;
  private String description;
  @JsonProperty("is_active")
  private boolean isActive;
  private String icon;
  private String image;
  @JsonProperty("parent")
  private String parentId;
  private List<Category> children;
  private List<Catalog> catalogs;
  private String alias;

  public Category() {
  }

  public Category(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category(String id, String name, String description, boolean isActive,
      String icon, String image, String parentId, String alias) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.isActive = isActive;
    this.icon = icon;
    this.image = image;
    this.parentId = parentId;
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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public List<Category> getChildren() {
    return children;
  }

  public void setChildren(List<Category> children) {
    this.children = children;
  }

  public List<Catalog> getCatalogs() {
    return catalogs;
  }

  public void setCatalogs(List<Catalog> catalogs) {
    this.catalogs = catalogs;
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

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

}
