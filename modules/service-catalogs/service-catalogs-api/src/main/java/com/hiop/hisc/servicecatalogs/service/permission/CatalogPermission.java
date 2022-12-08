package com.hiop.hisc.servicecatalogs.service.permission;

import org.osgi.service.component.annotations.Component;

import com.hiop.hisc.servicecatalogs.model.Catalog;
import com.hiop.hisc.servicecatalogs.service.CatalogLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

@Component(immediate = true, service = CatalogPermission.class)
public class CatalogPermission {

  private CatalogPermission() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean contains(
      PermissionChecker permissionChecker, Catalog catalog, String actionId) {

    return permissionChecker.hasPermission(
        catalog.getGroupId(), Catalog.class.getName(),
        catalog.getCatalogId(), actionId);
  }

  public static boolean contains(
      PermissionChecker permissionChecker, long catalogId, String actionId)
      throws PortalException {

    Catalog category = CatalogLocalServiceUtil.getCatalog(catalogId);
    return contains(permissionChecker, category, actionId);
  }
}