package com.hiop.hisc.servicecatalogs.web.services;

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
import com.hiop.hisc.servicecatalogs.web.dto.Catalog;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;

public class CatalogService {
  private CatalogService() {
  }

  public static Catalog fetchCatalog(
      PortletRequest portletRequest, String catalogId) throws Exception {
    Request request = Request.get(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/catalogs/" + catalogId);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), Catalog.class);
  }

  public static RenderData<Catalog> fetchCatalogs(PortletRequest portletRequest,
      String categoryId, int currentPage, int pageSize, String sortBy, String sortType)
      throws Exception {
    URIBuilder uriBuilder = new URIBuilder(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/catalogs");
    uriBuilder.setParameter("category", categoryId);
    uriBuilder.setParameter("page", String.valueOf(currentPage));
    uriBuilder.setParameter("page_size", String.valueOf(pageSize));
    uriBuilder.setParameter("sort_by", sortBy);
    uriBuilder.setParameter("sort_type", sortType);

    Request request = Request.get(uriBuilder.toString());
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    List<Catalog> catalogs = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<Catalog>>() {
        });

    RenderData<Catalog> renderData = new RenderData<>();
    renderData.setCurrentPage(currentPage);
    renderData.setPageSize(pageSize);
    renderData.setCount(dataNode.get("count").asInt());
    renderData.setNextURL(dataNode.get("next").asText());
    renderData.setPreviousURL(dataNode.get("previous").asText());
    renderData.setResults(catalogs);

    return renderData;
  }
}
