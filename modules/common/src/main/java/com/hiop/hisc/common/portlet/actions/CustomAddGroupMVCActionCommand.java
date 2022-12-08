package com.hiop.hisc.common.portlet.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.hiop.hisc.common.internal.configuration.HosConfiguration;
import com.hiop.hisc.common.services.ExpandoService;
import com.hiop.hisc.common.services.TenantService;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

@Component(immediate = true, property = {
    "javax.portlet.name=com_liferay_site_admin_web_portlet_SiteAdminPortlet",
    "mvc.command.name=/site_admin/add_group",
    "service.ranking:Integer=100"
}, service = MVCActionCommand.class)
public class CustomAddGroupMVCActionCommand extends BaseMVCActionCommand {

  @Override
  protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse)
      throws Exception {
    String name = ParamUtil.getString(actionRequest, "name");
    long companyId = PortalUtil.getCompanyId(actionRequest);

    HosConfiguration hosConfiguration = ConfigurationProviderUtil.getCompanyConfiguration(
        HosConfiguration.class, companyId);
    if (!hosConfiguration.createHosTenantEnabled()) {
      mvcActionCommand.processAction(actionRequest, actionResponse);
      return;
    }

    Group group = GroupLocalServiceUtil.fetchGroup(companyId, name);
    if (group != null) {
      JSONPortletResponseUtil.writeJSON(
          actionRequest, actionResponse,
          JSONUtil.put("error", "Please enter a unique name."));
      return;
    }

    try {
      String hosTenantUrl = TenantService.createHosTenant(name, hosConfiguration);

      // Create site
      mvcActionCommand.processAction(actionRequest, actionResponse);
      group = GroupLocalServiceUtil.fetchGroup(companyId, name);
      if (group == null) {
        return;
      }

      String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;
      String className = Group.class.getName();
      ExpandoService.updateExpandoValue(companyId, className, tableName,
          "hos_tenant_url", group.getGroupId(), hosTenantUrl);
    } catch (Exception ex) {
      JSONPortletResponseUtil.writeJSON(
          actionRequest, actionResponse,
          JSONUtil.put("error", ex.getMessage()));
    }
  }

  @Reference(target = "(component.name=com.liferay.site.admin.web.internal.portlet.action.AddGroupMVCActionCommand)")
  protected MVCActionCommand mvcActionCommand;
}