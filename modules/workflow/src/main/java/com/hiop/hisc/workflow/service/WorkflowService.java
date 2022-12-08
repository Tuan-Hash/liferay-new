package com.hiop.hisc.workflow.service;

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
import com.hiop.hisc.workflow.dto.RenderData;
import com.hiop.hisc.workflow.dto.Workflow;
import com.hiop.hisc.workflow.util.JsonUtil;

public class WorkflowService {
  private WorkflowService() {
  }

  public static RenderData<Workflow> fetchWorkflows(PortletRequest portletRequest,
      int currentPage, int pageSize) throws Exception {

    URIBuilder uriBuiber = new URIBuilder(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/workflows");
    uriBuiber.setParameter("page", String.valueOf(currentPage));
    uriBuiber.setParameter("page_size", String.valueOf(pageSize));

    Request request = Request.get(uriBuiber.toString());
    request.setHeader(HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));
    Response response = request.execute();

    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());
    List<Workflow> workflows = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<Workflow>>() {
        });

    RenderData<Workflow> renderData = new RenderData<>();
    renderData.setCurrentPage(currentPage);
    renderData.setPageSize(pageSize);
    renderData.setCount(dataNode.get("count").asInt());
    renderData.setNextURL(dataNode.get("next").asText());
    renderData.setPreviousURL(dataNode.get("previous").asText());
    renderData.setResults(workflows);
    return renderData;
  }

  public static Workflow fetchWorkflow(PortletRequest portletRequest, String workflowID)
      throws Exception {

    Request request = Request.get(
        HosConfigurationUtil.getTenantUrl(portletRequest)
            + "/workflows/" + workflowID);
    request.setHeader(HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));
    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());
    return new ObjectMapper().readValue(dataNode.toString(), Workflow.class);
  }
}
