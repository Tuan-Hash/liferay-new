package com.hiop.hisc.reports.service.permission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.service.CategoryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

@Component(immediate = true, service = ReportsCategoryPermission.class)
public class ReportsCategoryPermission {

  public static boolean contains(
      PermissionChecker permissionChecker, Category category, String actionId) {

    return permissionChecker.hasPermission(
        category.getGroupId(), Category.class.getName(),
        category.getCategoryId(), actionId);
  }

  public static boolean contains(
      PermissionChecker permissionChecker, long categoryId, String actionId)
      throws PortalException {

    Category category = CategoryLocalServiceUtil.getCategory(categoryId);
    return contains(permissionChecker, category, actionId);
  }

  @Reference(target = "(model.class.name=com.hiop.hisc.reports.model.Category)", unbind = "-")
  protected void setEntryModelPermission(
      ModelResourcePermission<Category> modelResourcePermission) {

    _categoryModelResourcePermission = modelResourcePermission;
  }

  private static ModelResourcePermission<Category> _categoryModelResourcePermission;

}