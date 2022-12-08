package com.hiop.hisc.servicecatalogs.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.servicecatalogs.admin.dto.CloneData;
import com.hiop.hisc.servicecatalogs.web.dto.Category;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;
import com.hiop.hisc.servicecatalogs.web.services.CategoryService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

public class CategoryAdminService {

  public static Category fetchRootCategory(
      PortletRequest portletRequest, String categoryId) throws Exception {
    Request request = Request.get(
        HosConfigurationUtil.getServerHost(portletRequest) +
            "/hitachi/api/v1/categories/" + categoryId);
    request.setHeader(HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

    Response response = request.execute();
    JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

    return new ObjectMapper().readValue(dataNode.toString(), Category.class);
  }

  public static boolean deleteCategory(
      ActionRequest actionRequest, ActionResponse actionResponse, String categoryId) {

    try {
      int totalCatalogs = CatalogAdminService
          .fetchCatalogs(actionRequest, categoryId, null, 1, 1, "name", "ASC")
          .getCount();

      if (totalCatalogs > 0) {
        return false;
      }

      Category category = CategoryService.fetchCategory(actionRequest, categoryId);

      Request request = Request.delete(
          HosConfigurationUtil.getTenantUrl(actionRequest) + "/categories/" + categoryId);
      request.setHeader(
          HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

      Response response = request.execute();
      JsonNode dataNode = JsonUtil.getNode(response.returnContent().asString());

      boolean deleted = !dataNode.get("message").toString().equalsIgnoreCase("");

      if (deleted && category.getParentId() != null) {
        return deleteCategory(actionRequest, actionResponse, category.getParentId());
      }

      return deleted;
    } catch (Exception e) {
      return false;
    }
  }

  public static List<CloneData> cloneCategories(
      ActionRequest actionRequest, ActionResponse actionResponse, Collection<String> categoryIds) {

    List<CloneData> cloneDataList = new ArrayList<>();

    for (String categoryId : categoryIds) {
      CloneData cloneData = new CloneData();

      Category clonedCategory = cloneCategory(actionRequest, actionResponse, categoryId, null);

      if (clonedCategory != null) {
        cloneData.setOriginalId(categoryId);
        cloneData.setCloneId(clonedCategory.getId());

        cloneDataList.add(cloneData);
      }

    }

    return cloneDataList;
  }

  public static Category cloneCategory(
      ActionRequest actionRequest, ActionResponse actionResponse, String categoryId, String alias) {
    try {
      Category category = fetchRootCategory(actionRequest, categoryId);

      if (alias == null || "".equalsIgnoreCase("")) {
        alias = category.getAlias();
      }

      RenderData<Category> categories = CategoryService.fetchCategories(actionRequest, null,
          alias, 1, 1);

      if (categories.getCount() > 0) {
        return categories.getResults().get(0);
      }

      if (category.getParentId() != null) {
        String cloneId = cloneCategory(
            actionRequest,
            actionResponse,
            category.getParentId(),
            category.getAlias()).getId();

        category.setParentId(cloneId);
      }

      return createCategory(actionRequest, actionResponse, category);
    } catch (Exception e) {
      return null;
    }
  }

  public static Category createCategory(
      ActionRequest actionRequest, ActionResponse actionResponse, Category rootCategory) {
    try {

      Request request = Request.post(
          HosConfigurationUtil.getTenantUrl(actionRequest) + "/categories");
      request.setHeader(
          HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

      String email = PortalUtil.getUser(actionRequest).getEmailAddress();

      StringEntity categoryEntity = buildCategoryEntity(rootCategory, email);
      request.body(categoryEntity);

      Response response = request.execute();
      JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

      return new ObjectMapper().readValue(dataNode.toString(), Category.class);
    } catch (Exception e) {
      return null;
    }
  }

  private static StringEntity buildCategoryEntity(Category category, String email) {
    JsonNode dataNode = new ObjectMapper().createObjectNode()
        .put("user", email)
        .put("name", category.getName())
        .put("description", category.getDescription())
        .put("parent", category.getParentId())
        .put("icon", category.getIcon())
        .put("image", category.getImage());

    return new StringEntity(dataNode.toString(), ContentType.APPLICATION_JSON);
  }

}
