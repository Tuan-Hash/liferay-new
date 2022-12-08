package com.hiop.hisc.servicecatalogs.admin.portlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.servicecatalogs.admin.constants.ServiceCatalogAdminPortletKeys;
import com.hiop.hisc.servicecatalogs.admin.dto.CloneData;
import com.hiop.hisc.servicecatalogs.admin.service.CatalogAdminService;
import com.hiop.hisc.servicecatalogs.admin.service.CategoryAdminService;
import com.hiop.hisc.servicecatalogs.admin.service.WorkflowAdminService;
import com.hiop.hisc.servicecatalogs.web.dto.Catalog;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author phongnh
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=ServiceCatalogAdmin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ServiceCatalogAdminPortletKeys.SERVICE_CATALOG_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
}, service = Portlet.class)
public class ServiceCatalogAdminPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		int page = ParamUtil.getInteger(renderRequest, "page", 1);

		if (page == 0) {
			page = 1;
		}

		try {
			RenderData<Catalog> renderData = CatalogAdminService.fetchCatalogs(renderRequest, null, null, page, 1,
					"name", "ASC");
			renderRequest.setAttribute("total", renderData.getCount());
		} catch (Exception ex) {
			_log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		try {
			String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

			if ("/view_root_categories.jsp".equals(mvcPath)) {
				String initData = new ObjectMapper().writeValueAsString(fetchRootCatalogs(renderRequest));
				renderRequest.setAttribute(("initData"), initData);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		super.render(renderRequest, renderResponse);
	}

	/**
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void cloneCatalogs(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException, SystemException {

		JsonNode dataNode = new ObjectMapper().readTree(ParamUtil.getString(actionRequest, "catalogs"));

		List<Catalog> catalogList = new ObjectMapper().readValue(
				dataNode.toString(), new TypeReference<List<Catalog>>() {
				});

		Collection<String> categoryIds = catalogList
				.stream().map(c -> c.getCategory().getId()).distinct()
				.collect(Collectors.toList());

		List<CloneData> cloneCategoryList = CategoryAdminService.cloneCategories(actionRequest, actionResponse,
				categoryIds);

		CatalogAdminService.cloneCatalogs(actionRequest, actionResponse, catalogList, cloneCategoryList);

		JSONPortletResponseUtil.writeJSON(actionRequest, actionResponse, JSONUtil.put("msg", "success"));
	}

	public void deleteCatalog(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String categoryId = ParamUtil.getString(actionRequest, "categoryId");

		CatalogAdminService.deleteCatalog(actionRequest, actionResponse);
		WorkflowAdminService.deleteWorkflow(actionRequest, actionResponse);
		CategoryAdminService.deleteCategory(actionRequest, actionResponse, categoryId);
	}

	public void fetchCatalogs(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		int page = ParamUtil.getInteger(actionRequest, "page");
		int pageSize = ParamUtil.getInteger(actionRequest, "page_size");
		String sort = ParamUtil.getString(actionRequest, "sort");
		String sortType = ParamUtil.getString(actionRequest, "sort_type");

		RenderData<Catalog> renderData = CatalogAdminService.fetchRootCatalogs(actionRequest, null, null, page,
				pageSize, sort, sortType);

		JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(new ObjectMapper().writeValueAsString(renderData)));
	}

	private static RenderData<Catalog> fetchRootCatalogs(RenderRequest renderRequest) throws Exception {
		RenderData<Catalog> renderData = CatalogAdminService.fetchRootCatalogs(renderRequest, null, null, 1, 100,
				"name", "ASC");

		String alias = renderData.getResults()
				.stream().map(c -> c.getAlias()).distinct()
				.collect(Collectors.joining(","));

		RenderData<Catalog> renderCatalog = CatalogAdminService.fetchCatalogs(renderRequest, null, alias, 1, 100,
				"name", "ASC");

		List<Catalog> filteredCatalogs = renderData
				.getResults()
				.stream()
				.filter(c -> renderCatalog
						.getResults()
						.stream()
						.filter(r -> r.getAlias().equalsIgnoreCase(c.getAlias()))
						.findFirst()
						.orElse(null) == null)
				.collect(Collectors.toList());

		renderData.setCount(filteredCatalogs.size());
		renderData.setResults(filteredCatalogs);

		return renderData;
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceCatalogAdminPortlet.class);

}