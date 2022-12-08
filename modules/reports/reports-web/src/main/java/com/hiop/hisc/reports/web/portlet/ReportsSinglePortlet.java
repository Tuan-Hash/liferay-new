package com.hiop.hisc.reports.web.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.reports.model.Report;
import com.hiop.hisc.reports.service.ReportLocalServiceUtil;
import com.hiop.hisc.reports.service.persistence.ReportPK;
import com.hiop.hisc.reports.web.constants.ReportsPortletKeys;
import com.hiop.hisc.reports.web.dto.EmbedConfig;
import com.hiop.hisc.reports.web.util.ReportsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author huyth
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SingleReport",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/single/view.jsp",
		"javax.portlet.init-param.config-template=/single/configuration.jsp",
		"javax.portlet.name=" + ReportsPortletKeys.REPORTS_SINGLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class ReportsSinglePortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String selectedReport = renderRequest.getPreferences().getValue("selectedReport", "");

		if ("".equals(selectedReport)) {
			include("/portlet_not_setup.jsp", renderRequest, renderResponse);
		} else {
			try {
				JsonNode data = new ObjectMapper().readTree(selectedReport);
				Report report = ReportLocalServiceUtil
						.getReport(new ReportPK(data.get("id").asLong(), data.get("categoryId").asLong()));
				renderRequest.setAttribute("report", report);

				String customSchema = renderRequest
						.getPreferences()
						.getValue(String.valueOf(themeDisplay.getUserId()), "[]");
				renderRequest.setAttribute("customSchema", customSchema);

				String showFilters = renderRequest.getPreferences().getValue("showFilters", "false");
				renderRequest.setAttribute("showFilters", "true".equalsIgnoreCase(showFilters) ? true : false);
			} catch (PortalException e) {
				e.printStackTrace();
			}

			super.doView(renderRequest, renderResponse);
		}
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