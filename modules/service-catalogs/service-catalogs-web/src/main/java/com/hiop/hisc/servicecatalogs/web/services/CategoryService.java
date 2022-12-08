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
import com.hiop.hisc.servicecatalogs.web.dto.Category;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;

public class CategoryService {
  private CategoryService() {
  }

  public static Category fetchCategory(
      PortletRequest portletRequest, String categoryId) throws Exception {
    Request request = Request.get(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/categories/" + categoryId);
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), Category.class);
  }

  public static RenderData<Category> fetchCategories(PortletRequest portletRequest,
      String parentId, String alias, int currentPage, int pageSize) throws Exception {
    URIBuilder uriBuilder = new URIBuilder(
        HosConfigurationUtil.getTenantUrl(portletRequest) + "/categories");
    uriBuilder.setParameter("parent", parentId);
    uriBuilder.setParameter("page", String.valueOf(currentPage));
    uriBuilder.setParameter("page_size", String.valueOf(pageSize));
    uriBuilder.setParameter("is_active", "true");

    if (alias != null || !("").equalsIgnoreCase(alias)) {
      uriBuilder.setParameter("alias", alias);
    }

    Request request = Request.get(uriBuilder.toString());
    request.setHeader(
        HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    List<Category> categories = new ObjectMapper().readValue(
        JsonUtil.getResultsString(dataNode), new TypeReference<List<Category>>() {
        });

    RenderData<Category> renderData = new RenderData<>();
    renderData.setCurrentPage(currentPage);
    renderData.setPageSize(pageSize);
    renderData.setCount(dataNode.get("count").asInt());
    renderData.setNextURL(dataNode.get("next").asText());
    renderData.setPreviousURL(dataNode.get("previous").asText());
    renderData.setResults(categories);

    return renderData;
  }

  public static RenderData<Category> fetchCategoriesWithChildren(PortletRequest portletRequest,
      String parentId, int currentPage, int pageSize) throws Exception {
    RenderData<Category> renderData = fetchCategories(
        portletRequest, parentId, null, currentPage, pageSize);

    for (Category category : renderData.getResults()) {
      URIBuilder uriBuilder = new URIBuilder(
          HosConfigurationUtil.getTenantUrl(portletRequest) + "/categories");
      uriBuilder.setParameter("parent", category.getId());
      uriBuilder.setParameter("page_size", "100");

      Request request = Request.get(uriBuilder.toString());
      request.setHeader(
          HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

      Response response = request.execute();
      JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

      List<Category> subCategories = new ObjectMapper().readValue(
          JsonUtil.getResultsString(dataNode), new TypeReference<List<Category>>() {
          });

      category.setChildren(subCategories);
    }

    return renderData;
  }

}
