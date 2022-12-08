package com.hiop.hisc.reports.web.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.model.Report;
import com.hiop.hisc.reports.service.CategoryLocalServiceUtil;
import com.hiop.hisc.reports.service.ReportLocalServiceUtil;
import com.hiop.hisc.reports.service.persistence.ReportPK;
import com.hiop.hisc.reports.web.constants.ReportNameConstant;
import com.hiop.hisc.reports.web.constants.ReportsPortletKeys;
import com.hiop.hisc.reports.web.dto.EmbedConfig;
import com.hiop.hisc.reports.web.util.ReportsUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author huyth
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
		"javax.portlet.display-name=ReportAdmin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + ReportsPortletKeys.REPORTS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",

}, service = Portlet.class)
public class ReportsAdminPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();
		long groupId = themeDisplay.getSiteGroupId();

		DynamicQuery query = CategoryLocalServiceUtil.dynamicQuery();
		query.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
		query.add(PropertyFactoryUtil.forName("groupId").eq(groupId));

		List<Category> categories = CategoryLocalServiceUtil.dynamicQuery(query);
		renderRequest.setAttribute("categories", categories);

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");
		String categoryId = ParamUtil.getString(renderRequest, "categoryId");
		String reportId = ParamUtil.getString(renderRequest, "reportId");
		List<Report> reports = new ArrayList<>();
		String otherName = "";
		List<String> listDevice = new ArrayList<>(Arrays.asList(ReportNameConstant.LIST_DEVICE));
		List<String> listDeviceCheck = new ArrayList<>(Arrays.asList(ReportNameConstant.LIST_DEVICE));

		if (categoryId != null) {
			try {
				Category category = CategoryLocalServiceUtil.getCategory(Long.parseLong(categoryId));
				renderRequest.setAttribute("category", category);

				Resource permResource = ResourceLocalServiceUtil.getResource(
						themeDisplay.getCompanyId(), Category.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(category.getCategoryId()));
				renderRequest.setAttribute("permResource", permResource);
				
				DynamicQuery query = ReportLocalServiceUtil.dynamicQuery();
				query.add(PropertyFactoryUtil.forName("primaryKey.categoryId").eq(Long.parseLong(categoryId)));
				reports = ReportLocalServiceUtil.dynamicQuery(query);
				renderRequest.setAttribute("reports", reports);
				Iterator<String> i = listDevice.iterator();

				while (i.hasNext()) {
					String device = i.next();
					for (Report item : reports) {
						if (device.equals(item.getName())) {
							i.remove();
						}
					}
				}

				if (!"".equals(reportId)) {
					Report report = ReportLocalServiceUtil
							.getReport(new ReportPK(Long.parseLong(reportId), Long.parseLong(categoryId)));
					Boolean isInDevice = false;
					for (String device : listDeviceCheck) {
						if ( !(report.getName()).equals(device) ){
							otherName = "Other";
						}
						else {
							isInDevice = true;
						}
					}

					if (isInDevice) {
						otherName = report.getName();
						listDevice.add(otherName);
					}
					renderRequest.setAttribute("report", report);
				}
			} catch (Exception ex) {
				// pass
			}
		}
		Collections.sort(listDevice);
		JSONArray devices = JSONFactoryUtil.createJSONArray(listDevice);
		renderRequest.setAttribute("listDevice", listDevice);
		renderRequest.setAttribute("devices", devices);
		renderRequest.setAttribute("otherName", otherName);
		renderRequest.setAttribute("reports", reports);
		super.render(renderRequest, renderResponse);
	}

	public void createCategory(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Category.class.getName(), actionRequest);

		long groupId = themeDisplay.getSiteGroupId();
		long userId = themeDisplay.getUserId();
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String icon = ParamUtil.getString(actionRequest, "icon");

		Category category = CategoryLocalServiceUtil.createCategory(0);
		category.setGroupId(groupId);
		category.setUserId(userId);
		category.setName(name);
		category.setDescription(description);
		category.setIcon(icon);

		CategoryLocalServiceUtil.addEntry(category, serviceContext);

		sendRedirect(actionRequest, actionResponse);
	}

	public void updateCategory(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Category.class.getName(), actionRequest);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String icon = ParamUtil.getString(actionRequest, "icon");

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		Category category = CategoryLocalServiceUtil.getCategory(categoryId);
		category.setName(name);
		category.setDescription(description);
		category.setIcon(icon);

		CategoryLocalServiceUtil.updateEntry(category, serviceContext);

		sendRedirect(actionRequest, actionResponse);
	}

	public void deleteCategory(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Category.class.getName(), actionRequest);

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		CategoryLocalServiceUtil.deleteEntry(categoryId, serviceContext);

		sendRedirect(actionRequest, actionResponse);
	}

	public void createReport(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getSiteGroupId();
		long userId = themeDisplay.getUserId();
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String icon = ParamUtil.getString(actionRequest, "icon");
		String sources = ParamUtil.getString(actionRequest, "sources");
		String other = ParamUtil.getString(actionRequest, "other");

		DynamicQuery query = ReportLocalServiceUtil.dynamicQuery();
		query.setProjection(ProjectionFactoryUtil.max("primaryKey.reportId"));
		List<Long> max = ReportLocalServiceUtil.dynamicQuery(query);
		long reportId = 0;
		if (!max.isEmpty() && max.get(0) != null) {
			reportId = max.get(0) + 1;
		}

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		Report report = ReportLocalServiceUtil.createReport(new ReportPK(reportId, categoryId));
		report.setGroupId(groupId);
		report.setUserId(userId);
		if (name.equals("Other")) {
			report.setName(other);
		}
		else {
			report.setName(name);
		}
		report.setDescription(description);
		report.setIcon(icon);
		report.setSources(sources);

		ReportLocalServiceUtil.addReport(report);

		sendRedirect(actionRequest, actionResponse);
	}

	public void updateReport(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException, SystemException {
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String icon = ParamUtil.getString(actionRequest, "icon");
		String sources = ParamUtil.getString(actionRequest, "sources");
		String other = ParamUtil.getString(actionRequest, "other");

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		long reportId = ParamUtil.getLong(actionRequest, "reportId");
		Report report = ReportLocalServiceUtil.getReport(new ReportPK(reportId, categoryId));
		if (name.equals("Other")) {
			report.setName(other);
		}
		else {
			report.setName(name);
		}
		report.setDescription(description);
		report.setIcon(icon);
		report.setSources(sources);

		ReportLocalServiceUtil.updateReport(report);

		sendRedirect(actionRequest, actionResponse);
	}

	public void deleteReport(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		long reportId = ParamUtil.getLong(actionRequest, "reportId");
		ReportLocalServiceUtil.deleteReport(new ReportPK(reportId, categoryId));
	}

	public static void getEmbedConfig(
			ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		EmbedConfig embedConfig = ReportsUtil.getEmbedConfig(actionRequest);

		JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(new ObjectMapper().writeValueAsString(embedConfig)));
	}

}