package com.hiop.hisc.servicerequest.service;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.servicerequest.dto.Execution;
import com.hiop.hisc.servicerequest.dto.StackstormExecution;

public class ExecutionService {

  public static Execution fetchExecution(
      PortletRequest portletRequest, String executionId) throws Exception {
    String requestUrl = HosConfigurationUtil.getTenantUrl(portletRequest)
        + "/executions/" + executionId;

    Request request = Request.get(requestUrl);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();

    JsonNode dataNode = new ObjectMapper().readTree(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), Execution.class);
  }

  public static List<StackstormExecution> fetchStackstormExecutions(PortletRequest portletRequest,
      String stackstormExecutionId)
      throws Exception {
    String requestUrl = HosConfigurationUtil.getTenantUrl(portletRequest) +
        "/executions/" + stackstormExecutionId + "/children";

    Request request = Request.get(requestUrl);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();

    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    List<StackstormExecution> stackstormExecutions = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<StackstormExecution>>() {
        });

    return stackstormExecutions;
  }

}
