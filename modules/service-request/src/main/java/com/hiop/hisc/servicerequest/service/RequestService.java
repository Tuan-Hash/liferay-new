package com.hiop.hisc.servicerequest.service;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.net.URIBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.servicerequest.dto.RenderData;
import com.hiop.hisc.servicerequest.dto.ServiceRequest;

public class RequestService {

  public static RenderData<ServiceRequest> fetchRequests(
      PortletRequest portletRequest, int currentPage, int pageSize)
      throws Exception {
    URIBuilder uriBuilder = new URIBuilder(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/requests");
    uriBuilder.setParameter("page", String.valueOf(currentPage));
    uriBuilder.setParameter("page_size", String.valueOf(pageSize));

    Request request = Request.get(uriBuilder.toString());
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    List<ServiceRequest> serviceRequests = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<ServiceRequest>>() {
        });

    RenderData<ServiceRequest> renderData = new RenderData<>();
    renderData.setCurrentPage(currentPage);
    renderData.setPageSize(pageSize);
    renderData.setCount(dataNode.get("count").asInt());
    renderData.setNextURL(dataNode.get("next").asText());
    renderData.setPreviousURL(dataNode.get("previous").asText());
    renderData.setResults(serviceRequests);

    return renderData;
  }

  public static ServiceRequest fetchRequest(
      PortletRequest portletRequest, String requestId) throws Exception {
    String requestUrl = HosConfigurationUtil.getTenantUrl(portletRequest)
        + "/requests/" + requestId;

    Request request = Request.get(requestUrl);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();

    JsonNode dataNode = new ObjectMapper().readTree(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), ServiceRequest.class);
  }

}
