package com.hiop.hisc.taglib.display.context;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

public class InputPermissionsDisplayContext {
  public InputPermissionsDisplayContext(
      HttpServletRequest httpServletRequest, String modelName, String entryId) {

    _httpServletRequest = httpServletRequest;
    _modelName = modelName;
    _entryId = entryId;

    ThemeDisplay themeDisplay = (ThemeDisplay) _httpServletRequest.getAttribute(
        WebKeys.THEME_DISPLAY);

    _groupId = themeDisplay.getScopeGroupId();
    _companyId = themeDisplay.getCompanyId();

    _actions = ResourceActionsUtil.getModelResourceActions(_modelName);
  }

  public List<String> getIndividualActions(Role role) throws PortalException {

    if (_entryId == null) {
      return new ArrayList<>();
    }

    Resource permResource = ResourceLocalServiceUtil.getResource(
        _companyId, _modelName,
        ResourceConstants.SCOPE_INDIVIDUAL, _entryId);

    return ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(
        permResource.getCompanyId(), permResource.getName(),
        permResource.getScope(), permResource.getPrimKey(),
        role.getRoleId(), _actions);
  }

  public List<String> getGroupActions(Role role) throws PortalException {
    return ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(
        _companyId, _modelName,
        ResourceConstants.SCOPE_GROUP, String.valueOf(_groupId),
        role.getRoleId(), _actions);
  }

  public List<String> getGroupTemplateActions(Role role) throws PortalException {
    return ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(
        _companyId, _modelName,
        ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
        role.getRoleId(), _actions);
  }

  public List<String> getCompanyActions(Role role) throws PortalException {
    return ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(
        _companyId, _modelName,
        ResourceConstants.SCOPE_COMPANY, String.valueOf(_companyId),
        role.getRoleId(), _actions);
  }

  private final HttpServletRequest _httpServletRequest;

  private final long _companyId;
  private final long _groupId;
  private final String _modelName;
  private final String _entryId;
  private final List<String> _actions;
}
