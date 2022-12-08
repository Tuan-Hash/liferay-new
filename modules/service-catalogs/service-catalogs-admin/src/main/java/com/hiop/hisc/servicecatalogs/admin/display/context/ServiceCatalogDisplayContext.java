package com.hiop.hisc.servicecatalogs.admin.display.context;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.hiop.hisc.servicecatalogs.service.CatalogLocalServiceUtil;
import com.hiop.hisc.servicecatalogs.service.permission.CatalogPermission;
import com.hiop.hisc.servicecatalogs.web.dto.Catalog;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

public class ServiceCatalogDisplayContext {

  public ServiceCatalogDisplayContext(
      RenderRequest renderRequest, RenderResponse renderResponse,
      PermissionChecker permissionChecker, ResourceBundle resourceBundle) {

    this.httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
    this.renderResponse = renderResponse;
    this.permissionChecker = permissionChecker;
    this.resourceBundle = resourceBundle;

    this.themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
  }

  public List<DropdownItem> getDropdownItems(Catalog catalog) throws PortalException {
    com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper = CatalogLocalServiceUtil
        .fetchCatalogByUuidAndGroupId(catalog.getId(), themeDisplay.getSiteGroupId());

    if (catalogMapper == null) {
      catalogMapper = CatalogLocalServiceUtil.createCatalog(0);
      catalogMapper.setUuid(catalog.getId());
      catalogMapper.setName(catalog.getName());
      catalogMapper.setCategoryId(catalog.getCategory().getId());
      catalogMapper.setGroupId(themeDisplay.getSiteGroupId());
      catalogMapper = CatalogLocalServiceUtil.addEntry(catalogMapper);
    }

    boolean hasPermissions = hasPermissionsPermission(catalogMapper.getCatalogId());
    boolean hasDeletePermission = hasDeletePermission(catalogMapper.getCatalogId());

    return DropdownItemListBuilder.add(
        () -> hasPermissions,
        getPermissionsActionUnsafeConsumer(catalogMapper)).add(
            () -> hasDeletePermission,
            getDeleteEntryActionUnsafeConsumer(catalog))
        .build();
  }

  private boolean hasPermissionsPermission(long catalogId) {
    try {
      return CatalogPermission.contains(
          permissionChecker, catalogId, ActionKeys.PERMISSIONS);
    } catch (PortalException portalException) {
      return ReflectionUtil.throwException(portalException);
    }
  }

  private boolean hasDeletePermission(long catalogId) {
    try {
      return CatalogPermission.contains(
          permissionChecker, catalogId, ActionKeys.DELETE);
    } catch (PortalException portalException) {
      return ReflectionUtil.throwException(portalException);
    }
  }

  private UnsafeConsumer<DropdownItem, Exception> getDeleteEntryActionUnsafeConsumer(
      Catalog catalog) {

    return dropdownItem -> {
      dropdownItem.putData("action", "delete");
      dropdownItem.putData(
          "deleteURL",
          PortletURLBuilder
              .createActionURL(renderResponse)
              .setActionName("deleteCatalog")
              .setParameter("catalogId", catalog.getId())
              .setParameter("categoryId", catalog.getCategory().getId())
              .setParameter("workflowId", catalog.getWorkflowId())
              .buildString());
      dropdownItem.setLabel(
          LanguageUtil.get(httpServletRequest, "serviceCatalog.delete"));
    };
  }

  private UnsafeConsumer<DropdownItem, Exception> getPermissionsActionUnsafeConsumer(
      com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper) {

    return dropdownItem -> {
      dropdownItem.putData("action", "permissions");
      dropdownItem.putData(
          "permissionsURL", getPermissionsURL(catalogMapper));
      dropdownItem.setLabel(
          LanguageUtil.get(httpServletRequest, "serviceCatalog.permissions"));
    };
  }

  private String getPermissionsURL(com.hiop.hisc.servicecatalogs.model.Catalog catalogMapper) {
    try {
      return PermissionsURLTag.doTag(
          StringPool.BLANK, com.hiop.hisc.servicecatalogs.model.Catalog.class.getName(),
          catalogMapper.getName(),
          null, String.valueOf(catalogMapper.getCatalogId()),
          LiferayWindowState.POP_UP.toString(), null,
          httpServletRequest);
    } catch (Exception exception) {
      return ReflectionUtil.throwException(exception);
    }
  }

  private final HttpServletRequest httpServletRequest;
  private final PermissionChecker permissionChecker;
  private final RenderResponse renderResponse;
  private final ResourceBundle resourceBundle;
  private final ThemeDisplay themeDisplay;
}
