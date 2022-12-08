package com.hiop.hisc.servicecatalogs.web.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import org.osgi.service.component.annotations.Component;

import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.servicecatalogs.service.CatalogLocalServiceUtil;
import com.hiop.hisc.servicecatalogs.service.permission.CatalogPermission;
import com.hiop.hisc.servicecatalogs.web.constants.ServiceCatalogPortletKeys;
import com.hiop.hisc.servicecatalogs.web.dto.Catalog;
import com.hiop.hisc.servicecatalogs.web.dto.Category;
import com.hiop.hisc.servicecatalogs.web.dto.RenderData;
import com.hiop.hisc.servicecatalogs.web.services.CatalogService;
import com.hiop.hisc.servicecatalogs.web.services.CategoryService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author huyth
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Test",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ServiceCatalogPortletKeys.SERVICE_CATALOG,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class ServiceCatalogPortlet extends MVCPortlet {
	private static Log _log = LogFactoryUtil.getLog(ServiceCatalogPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		int page = ParamUtil.getInteger(renderRequest, "page");
		if (page == 0)
			page = 1;
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		try {
			RenderData<Category> renderData = CategoryService.fetchCategoriesWithChildren(
					renderRequest, "null", page, 10);

			DynamicQuery query = CatalogLocalServiceUtil.dynamicQuery();
			query.add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getSiteGroupId()));
			query.add(PropertyFactoryUtil.forName("companyId").eq(themeDisplay.getCompanyId()));
			List<com.hiop.hisc.servicecatalogs.model.Catalog> catalogs = CatalogLocalServiceUtil.dynamicQuery(query);

			List<String> availCategoriesId = catalogs.stream()
					.filter(catalog -> CatalogPermission.contains(themeDisplay.getPermissionChecker(), catalog,
							ActionKeys.VIEW))
					.map(i -> i.getCategoryId())
					.collect(Collectors.toList());
			List<Category> validCategories = new ArrayList<>();
			for (Category category : renderData.getResults()) {
				if (category.getChildren() == null || category.getChildren().isEmpty()) {
					continue;
				}

				List<Category> children = new ArrayList<>();
				for (Category subCategory : category.getChildren()) {
					if (availCategoriesId.contains(subCategory.getId())) {
						children.add(subCategory);
					}
				}

				if (!children.isEmpty()) {
					category.setChildren(children);
					validCategories.add(category);
				}
			}

			renderData.setResults(validCategories);
			renderRequest.setAttribute("renderData", renderData);
		} catch (Exception ex) {
			_log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		int page = ParamUtil.getInteger(renderRequest, "page");
		if (page == 0)
			page = 1;

		try {
			String categoryId = ParamUtil.getString(renderRequest, "categoryId");
			if (categoryId != null && !categoryId.isEmpty()) {
				Category category = CategoryService.fetchCategory(renderRequest, categoryId);
				renderRequest.setAttribute("category", category);

				RenderData<Catalog> renderData = CatalogService.fetchCatalogs(
						renderRequest, categoryId, page, 10, "name", "ASC");

				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				Iterator<Catalog> iterator = renderData.getResults().iterator();
				while (iterator.hasNext()) {
					Catalog catalog = iterator.next();
					com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper = CatalogLocalServiceUtil
							.fetchCatalogByUuidAndGroupId(catalog.getId(), themeDisplay.getSiteGroupId());
					if (catalogMapper != null && !CatalogPermission.contains(themeDisplay.getPermissionChecker(),
							catalogMapper, ActionKeys.VIEW)) {
						iterator.remove();
					}
				}
				renderRequest.setAttribute("renderData", renderData);
			}

			String catalogId = ParamUtil.getString(renderRequest, "catalogId");
			if (catalogId != null && !catalogId.isEmpty()) {
				Catalog catalog = CatalogService.fetchCatalog(renderRequest, catalogId);
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long groupId = ParamUtil.getLong(renderRequest, "groupId");
				com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper = CatalogLocalServiceUtil
						.fetchCatalogByUuidAndGroupId(catalog.getId(), groupId);
				if (catalogMapper != null
						&& !CatalogPermission.contains(
								themeDisplay.getPermissionChecker(), catalogMapper, ActionKeys.VIEW)) {
					SessionErrors.add(renderRequest, PrincipalException.MustHavePermission.class);

					include("/error.jsp", renderRequest, renderResponse);
					return;
				}
				renderRequest.setAttribute("catalog", catalog);

				String cancelURL = PortletURLBuilder
						.createRenderURL(renderResponse)
						.setMVCPath("/catalog.jsp")
						.setParameter("categoryId", catalog.getCategory().getId())
						.buildString();
				renderRequest.setAttribute("cancelURL", cancelURL);

				renderRequest.setAttribute(
						"submitURL", HosConfigurationUtil.getTenantUrl(renderRequest) + "/requests");

			}
		} catch (Exception ex) {
			_log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.render(renderRequest, renderResponse);
	}
}