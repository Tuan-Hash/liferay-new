package com.hiop.hisc.reports.service.permission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

@Component(immediate = true, service = {})
public class ReportsPermission {
  public static final String RESOURCE_NAME = "com.hiop.hisc.reports";

  public static boolean contains(
      PermissionChecker permissionChecker, long groupId, String actionId) {

    return _portletResourcePermission.contains(
        permissionChecker, groupId, actionId);
  }

  @Reference(target = "(resource.name=" + RESOURCE_NAME + ")", unbind = "-")
  protected void setPortletResourcePermission(
      PortletResourcePermission portletResourcePermission) {

    _portletResourcePermission = portletResourcePermission;
  }

  private static PortletResourcePermission _portletResourcePermission;
}