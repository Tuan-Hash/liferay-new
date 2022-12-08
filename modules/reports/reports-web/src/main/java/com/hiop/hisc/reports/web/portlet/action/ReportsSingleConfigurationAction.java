package com.hiop.hisc.reports.web.portlet.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.hiop.hisc.reports.web.constants.ReportsPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

@Component(configurationPolicy = ConfigurationPolicy.OPTIONAL, property = "javax.portlet.name="
    + ReportsPortletKeys.REPORTS_SINGLE, service = ConfigurationAction.class)
public class ReportsSingleConfigurationAction extends DefaultConfigurationAction {

  @Override
  public void processAction(
      PortletConfig portletConfig, ActionRequest actionRequest,
      ActionResponse actionResponse)
      throws Exception {

    setPreference(
        actionRequest, "selectedReport",
        ParamUtil.getString(actionRequest, "selectedReport"));

    setPreference(
        actionRequest, "showFilters",
        ParamUtil.getString(actionRequest, "showFilters", "false"));

    super.processAction(portletConfig, actionRequest, actionResponse);
  }

}