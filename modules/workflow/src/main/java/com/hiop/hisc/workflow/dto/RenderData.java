package com.hiop.hisc.workflow.dto;

import java.util.List;

public class RenderData<T> {
  private int count;
  private int pageSize;
  private int currentPage;
  private String nextURL;
  private String previousURL;
  private List<T> results;

  public RenderData() {
  }

  public RenderData(int count, int pageSize, int currentPage,
      String nextURL, String previousURL) {
    this.count = count;
    this.pageSize = pageSize;
    this.currentPage = currentPage;
    this.nextURL = nextURL;
    this.previousURL = previousURL;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public String getNextURL() {
    return nextURL;
  }

  public void setNextURL(String nextURL) {
    this.nextURL = nextURL;
  }

  public String getPreviousURL() {
    return previousURL;
  }

  public void setPreviousURL(String previousURL) {
    this.previousURL = previousURL;
  }

  public List<T> getResults() {
    return results;
  }

  public void setResults(List<T> results) {
    this.results = results;
  }
}
