package com.hiop.hisc.workflow.service;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.workflow.dto.Catalog;
import com.hiop.hisc.workflow.dto.RenderData;
import com.hiop.hisc.workflow.util.JsonUtil;

public class CatalogService {
  private CatalogService() {
  }

  public static RenderData<Catalog> fetchCatalogs(PortletRequest portletRequest, String workflowID)
      throws Exception {

    Request request = Request.get(
        HosConfigurationUtil.getTenantUrl(portletRequest)
            + "/catalogs?workflow=" + workflowID);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));
    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());
    List<Catalog> catalogs = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<Catalog>>() {
        });

    RenderData<Catalog> renderData = new RenderData<>();
    renderData.setResults(catalogs);
    return renderData;
  }

}
