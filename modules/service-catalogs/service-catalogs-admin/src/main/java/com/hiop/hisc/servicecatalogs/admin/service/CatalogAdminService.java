package com.hiop.hisc.servicecatalogs.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.servicecatalogs.admin.dto.CloneData;
import com.hiop.hisc.servicecatalogs.web.dto.Catalog;
import com.hiop.hisc.servicecatalogs.web.dto.Category;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;
import com.hiop.hisc.workflow.dto.Workflow;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.hiop.hisc.servicecatalogs.service.CatalogLocalServiceUtil;

public class CatalogAdminService {
	private CatalogAdminService() {
	}

	public static RenderData<Catalog> fetchRootCatalogs(
			PortletRequest portletRequest, String categoryId, String alias, int currentPage,
			int pageSize, String sortBy, String sortType) throws Exception {

		try {
			URIBuilder uriBuilder = new URIBuilder(
					HosConfigurationUtil.getServerHost(portletRequest) + "/hitachi/api/v1/catalogs");
			uriBuilder.setParameter("categories", categoryId);
			uriBuilder.setParameter("page", String.valueOf(currentPage));
			uriBuilder.setParameter("page_size", String.valueOf(pageSize));
			uriBuilder.setParameter("sort_by", sortBy);
			uriBuilder.setParameter("sort_type", sortType);

			Request request = Request.get(uriBuilder.toString());
			request.setHeader(HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

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
		} catch (Exception e) {
			return new RenderData<>();
		}
	}

	public static RenderData<Catalog> fetchCatalogs(
			PortletRequest portletRequest, String categoryId, String alias, int currentPage,
			int pageSize, String sortBy, String sortType) {

		try {
			URIBuilder uriBuilder = new URIBuilder(HosConfigurationUtil.getTenantUrl(portletRequest) + "/catalogs");
			uriBuilder.setParameter("categories", categoryId);
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
		} catch (Exception e) {
			return new RenderData<>();
		}
	}

	public static boolean deleteCatalog(ActionRequest actionRequest, ActionResponse actionResponse) {
		try {
			String catalogId = ParamUtil.getString(actionRequest, "catalogId");

			Request request = Request.delete(
					HosConfigurationUtil.getTenantUrl(actionRequest) + "/catalogs/" + catalogId);
			request.setHeader(
					HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

			Response response = request.execute();
			JsonNode dataNode = JsonUtil.getNode(response.returnContent().asString());

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper = CatalogLocalServiceUtil
					.fetchCatalogByUuidAndGroupId(catalogId, themeDisplay.getSiteGroupId());
			CatalogLocalServiceUtil.deleteEntry(catalogMapper.getCatalogId());
			return !dataNode.get("message").toString().equalsIgnoreCase("");
		} catch (Exception e) {
			return false;
		}
	}

	public static List<Catalog> cloneCatalogs(
			ActionRequest actionRequest, ActionResponse actionResponse,
			List<Catalog> catalogList, List<CloneData> cloneCategoryList) {

		List<Catalog> catalogs = new ArrayList<>();

		for (Catalog catalog : catalogList) {
			Catalog replacedCatalog = replaceCatalog(actionRequest, actionResponse, catalog, cloneCategoryList);
			Catalog clonedCatalog = createCatalog(actionRequest, actionResponse, replacedCatalog);
			catalogs.add(clonedCatalog);
		}

		return catalogs;
	}

	public static Catalog createCatalog(ActionRequest actionRequest, ActionResponse actionResponse,
			Catalog rootCatalog) {
		try {
			Request request = Request.post(
					HosConfigurationUtil.getTenantUrl(actionRequest) + "/catalogs");
			request.setHeader(
					HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

			String email = PortalUtil.getUser(actionRequest).getEmailAddress();

			StringEntity catalogEntity = buildCatalogEntity(rootCatalog, email);
			request.body(catalogEntity);

			Response response = request.execute();
			JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());
			Catalog newCatalog = new ObjectMapper().readValue(dataNode.toString(), Catalog.class);

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			com.hiop.hisc.servicecatalogs.model.Catalog catalog = CatalogLocalServiceUtil.createCatalog(0);
			catalog.setUuid(newCatalog.getId());
			catalog.setName(newCatalog.getName());
			catalog.setGroupId(themeDisplay.getSiteGroupId());
			catalog.setCategoryId(newCatalog.getCategory().getId());
			CatalogLocalServiceUtil.addEntry(catalog);

			return newCatalog;
		} catch (Exception e) {
			return null;
		}
	}

	private static StringEntity buildCatalogEntity(Catalog catalog, String email) {
		ObjectNode dataNode = new ObjectMapper().createObjectNode();

		dataNode.put("user", email);
		dataNode.put("category", catalog.getCategory().getId());
		dataNode.put("workflow", catalog.getWorkflowId());
		dataNode.put("name", catalog.getName());
		dataNode.put("description", catalog.getDescription());
		dataNode.put("icon", catalog.getIcon());
		dataNode.put("image", catalog.getImage());

		dataNode.set("json_schema", catalog.getJsonSchema());
		dataNode.set("ui_schema", catalog.getUiSchema());
		dataNode.set("form_data", catalog.getFormData());

		return new StringEntity(dataNode.toString(), ContentType.APPLICATION_JSON);
	}

	public static Catalog replaceCatalog(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Catalog catalog, List<CloneData> cloneCategoryList) {

		Workflow clonedWorkflow = WorkflowAdminService.cloneWorkflow(
				actionRequest,
				actionResponse,
				catalog.getWorkflowId());

		catalog.setWorkflowId(clonedWorkflow.getId());

		CloneData cloneCategory = cloneCategoryList
				.stream().filter(c -> c.getOriginalId().equalsIgnoreCase(catalog.getCategory().getId()))
				.findFirst().get();

		catalog.setCategory(new Category(cloneCategory.getCloneId(), catalog.getCategory().getName()));

		return catalog;
	}

}