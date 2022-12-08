package com.hiop.hisc.servicerequest.service;

import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.servicerequest.dto.Catalog;

public class CatalogService {

  public static Catalog fetchCatalog(
      PortletRequest portletRequest, String catalogId) throws Exception {
    String requestUrl = HosConfigurationUtil.getTenantUrl(portletRequest)
        + "/catalogs/" + catalogId;

    Request request = Request.get(requestUrl);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), Catalog.class);
  }

}
