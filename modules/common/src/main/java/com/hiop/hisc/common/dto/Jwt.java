package com.hiop.hisc.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Jwt implements Serializable {
  @JsonProperty("expires_in")
  private String expiresIn;
  @JsonProperty("access_token")
  private String accessToken;

  public Jwt() {
  }

  public Jwt(String expiresIn, String accessToken) {
    this.expiresIn = expiresIn;
    this.accessToken = accessToken;
  }

  public String getExpiresIn() {
    return this.expiresIn;
  }

  public void setExpiresIn(String expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

}
