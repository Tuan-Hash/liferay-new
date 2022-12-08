package com.hiop.hisc.reports.web.dto;

import java.util.List;

import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.model.Report;

public class DisplayData {

  private long id;
  private String name;
  private String icon;
  private List<Report> reports;

  public DisplayData(Category category) {
    this.setId(category.getCategoryId());
    this.setName(category.getName());
    this.setIcon(category.getIcon());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public List<Report> getReports() {
    return reports;
  }

  public void setReports(List<Report> reports) {
    this.reports = reports;
  }

}
