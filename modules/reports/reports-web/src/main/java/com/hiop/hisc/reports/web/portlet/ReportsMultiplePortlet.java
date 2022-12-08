package com.hiop.hisc.reports.web.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.model.Report;
import com.hiop.hisc.reports.service.CategoryLocalServiceUtil;
import com.hiop.hisc.reports.service.ReportLocalServiceUtil;
import com.hiop.hisc.reports.service.permission.ReportsCategoryPermission;
import com.hiop.hisc.reports.web.constants.ReportsPortletKeys;
import com.hiop.hisc.reports.web.dto.DisplayData;
import com.hiop.hisc.reports.web.dto.EmbedConfig;
import com.hiop.hisc.reports.web.util.ReportsUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author huyth
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MultipleReports",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/multiple/view.jsp",
		"javax.portlet.init-param.config-template=/multiple/configuration.jsp",
		"javax.portlet.name=" + ReportsPortletKeys.REPORTS_MULTIPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class ReportsMultiplePortlet extends MVCPortlet {

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String selectedReports = renderRequest.getPreferences().getValue("selectedReports", "");

		if ("".equals(selectedReports)) {
			include("/portlet_not_setup.jsp", renderRequest, renderResponse);
		} else {
			Map<Long, List<Long>> cateMap = serializeSelected(selectedReports);
			List<DisplayData> datas = prepareDisplayData(themeDisplay, cateMap);
			renderRequest.setAttribute("displayData", datas);

			String categoryId = ParamUtil.getString(renderRequest, "categoryId");
			if (!"".equals(categoryId)) {
				DisplayData category = datas.stream()
						.filter(item -> item.getId() == Long.valueOf(categoryId))
						.findFirst().get();

				RenderURL categoryURL = renderResponse.createRenderURL();
				categoryURL.setParameter("categoryId", String.valueOf(category.getId()));
				PortalUtil.addPortletBreadcrumbEntry(themeDisplay.getRequest(), category.getName(),
						categoryURL.toString());

				renderRequest.setAttribute("categoryId", categoryId);

				String reportId = ParamUtil.getString(renderRequest, "reportId");
				if (!"".equals(reportId)) {
					Report report = category.getReports().stream()
							.filter(i -> i.getReportId() == Long.valueOf(reportId))
							.findFirst().get();
					PortalUtil.addPortletBreadcrumbEntry(themeDisplay.getRequest(), report.getName(),
							themeDisplay.getURLCurrent());
					renderRequest.setAttribute("title", report.getName() + " " + category.getName());
					renderRequest.setAttribute("reportId", reportId);
					renderRequest.setAttribute("sources", report.getSources());

					String customSchema = renderRequest
							.getPreferences()
							.getValue(String.valueOf(themeDisplay.getUserId()), "[]");
					renderRequest.setAttribute("customSchema", customSchema);

					String showFilters = renderRequest.getPreferences().getValue("showFilters", "false");
					renderRequest.setAttribute("showFilters", "true".equalsIgnoreCase(showFilters) ? true : false);
				}
			}
		}

		super.render(renderRequest, renderResponse);
	}

	private List<DisplayData> prepareDisplayData(
			ThemeDisplay themeDisplay, Map<Long, List<Long>> cateMap) {

		DynamicQuery query = CategoryLocalServiceUtil.dynamicQuery();
		query.add(PropertyFactoryUtil.forName("companyId").eq(themeDisplay.getCompanyId()));
		query.add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getSiteGroupId()));
		query.add(PropertyFactoryUtil.forName("categoryId").in(cateMap.keySet().toArray()));

		List<Category> categories = CategoryLocalServiceUtil.dynamicQuery(query);

		List<DisplayData> datas = new ArrayList<>();
		for (Category category : categories) {
			if (!ReportsCategoryPermission.contains(
					themeDisplay.getPermissionChecker(), category, "VIEW")) {
				continue;
			}

			DisplayData data = new DisplayData(category);
			query = ReportLocalServiceUtil.dynamicQuery();
			query.add(PropertyFactoryUtil.forName("primaryKey.categoryId").eq(category.getCategoryId()));

			List<Long> reportIDs = cateMap.get(category.getCategoryId());
			if (!reportIDs.isEmpty()) {
				query.add(PropertyFactoryUtil.forName("primaryKey.reportId").in(reportIDs));
			}

			data.setReports(ReportLocalServiceUtil.dynamicQuery(query));

			datas.add(data);
		}
		return datas;
	}

	private Map<Long, List<Long>> serializeSelected(String selectedReports)
			throws JsonProcessingException {
		String[] selected = new ObjectMapper().readValue(selectedReports, String[].class);
		Map<Long, List<Long>> result = new HashMap<>();
		for (String value : selected) {
			String[] ids = value.split("[.]");
			Long cateId = Long.valueOf(ids[0]);
			if (!result.containsKey(cateId)) {
				result.put(cateId, new ArrayList<>());
			}

			if (ids.length > 1) {
				result.get(cateId).add(Long.valueOf(ids[1]));
			}
		}
		return result;
	}

	public void saveCustomReportLayout(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, ReadOnlyException, ValidatorException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String schema = ParamUtil.getString(actionRequest, "customSchema");

		PortletPreferences prefs = actionRequest.getPreferences();
		prefs.setValue(
				String.valueOf(themeDisplay.getUserId()), schema);
		prefs.store();

		JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("msg", "success"));
	}

	public static void getEmbedConfig(
			ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		EmbedConfig embedConfig = ReportsUtil.getEmbedConfig(actionRequest);

		JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(new ObjectMapper().writeValueAsString(embedConfig)));
	}

}