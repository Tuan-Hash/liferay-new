package com.hiop.hisc.common.services;

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiop.hisc.common.internal.configuration.HosConfiguration;
import com.hiop.hisc.common.utils.JsonUtil;

public class TenantService {
  private TenantService() {
  }

  public static String createHosTenant(String tenantName, HosConfiguration hosConfiguration)
      throws Exception {
    String requestUrl = hosConfiguration.serverHost() + "/api/v1/tenant";

    Request request = Request.post(requestUrl);
    request.setHeader(HttpHeaders.AUTHORIZATION, hosConfiguration.authToken());

    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.addTextBody("tenant", tenantName);

    request.body(builder.build());

    ClassicHttpResponse response = (ClassicHttpResponse) request.execute().returnResponse();
    HttpEntity entity = response.getEntity();
    Content content = new Content(
        EntityUtils.toByteArray(entity), ContentType.parse(entity.getContentType()));

    JsonNode dataNode = JsonUtil.getNode(content.asString());

    if (response.getCode() == 201) {
      return hosConfiguration.serverHost() + dataNode.get("data").get("url").textValue();
    } else if (response.getCode() == 400) {
      throw new Exception(dataNode.get("tenant").get(0).asText());
    }

    throw new Exception("Internal server error");
  }

}
