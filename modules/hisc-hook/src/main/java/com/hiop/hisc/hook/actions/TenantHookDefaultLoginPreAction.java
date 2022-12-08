package com.hiop.hisc.hook.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hiop.hisc.common.dto.Jwt;
import com.hiop.hisc.common.services.ExpandoService;
import com.hiop.hisc.common.utils.JwtUtil;
import com.hiop.hisc.hook.constants.CustomFieldConstants;
import com.hiop.hisc.hook.dto.azure.AzureGroup;
import com.hiop.hisc.hook.dto.azure.AzureRole;
import com.hiop.hisc.hook.services.GraphService;
import com.hiop.hisc.hook.utils.AzureUtil;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

public class TenantHookDefaultLoginPreAction extends Action {

	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ActionException {

		try {
			doRun(httpServletRequest, httpServletResponse);
		} catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	protected void doRun(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		HttpSession httpSession = httpServletRequest.getSession();
		User user = (User) httpSession.getAttribute(WebKeys.USER);

		if (user != null && user.isNew()) {
			assignInfoToNewUserLoggedBySSO(httpServletRequest);
		}

	}

	protected void assignInfoToNewUserLoggedBySSO(
			HttpServletRequest httpServletRequest) throws Exception {
		Jwt jwToken = JwtUtil.getJWToken(httpServletRequest);

		if (jwToken == null) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();
		User user = (User) httpSession.getAttribute(WebKeys.USER);

		assignRoleToNewUserLoggedBySSO(companyId, user.getUserId(), jwToken.getAccessToken());
		assignGroupToNewUserLoggedBySSO(companyId, user.getUserId(), jwToken.getAccessToken());
	}

	protected void assignRoleToNewUserLoggedBySSO(long companyId, long userId, String accessToken) {
		List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);

		if (roles == null) {
			return;
		}

		String azureUserId = AzureUtil.getUserId(accessToken);
		List<AzureRole> azureRoles = GraphService.fetchRoles(companyId, azureUserId, accessToken);

		if (azureRoles.size() == 0) {
			return;
		}

		List<Role> integratedUserRoles = new ArrayList<Role>();

		for (Role role : roles) {
			Collection<String> roleValue = getAcceptedRoleValue(companyId, role.getRoleId());

			boolean hasExistingRoleInSystem = azureRoles
					.stream()
					.anyMatch(azureRole -> {
						String azureRoleName = azureRole.getResourceDisplayName().trim();
						String roleName = role.getName().trim();

						return azureRoleName.equalsIgnoreCase(roleName) || roleValue.contains(azureRoleName);
					});

			if (!hasExistingRoleInSystem) {
				continue;
			}

			integratedUserRoles.add(role);
		}

		if (integratedUserRoles.isEmpty()) {
			return;
		}

		try {
			RoleLocalServiceUtil.addUserRoles(userId, integratedUserRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void assignGroupToNewUserLoggedBySSO(long companyId, long userId, String accessToken)
			throws PortalException {
		List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(companyId);

		if (userGroups == null) {
			return;
		}

		String azureUserId = AzureUtil.getUserId(accessToken);
		List<AzureGroup> azureGroups = GraphService.fetchGroups(companyId, azureUserId, accessToken);

		if (azureGroups.isEmpty()) {
			return;
		}

		List<UserGroup> integratedUserGroups = new ArrayList<>();

		for (UserGroup userGroup : userGroups) {
			Collection<String> groupValue = getAcceptedGroupValue(companyId, userGroup.getUserGroupId());

			boolean hasExistingGroupInSystem = azureGroups
					.stream()
					.anyMatch(azureGroup -> {
						String azureGroupName = azureGroup.getDisplayName().trim();
						String userGroupName = userGroup.getName().trim();

						return azureGroupName.equalsIgnoreCase(userGroupName) || groupValue.contains(azureGroupName);
					});

			if (!hasExistingGroupInSystem) {
				continue;
			}

			integratedUserGroups.add(userGroup);
		}

		if (integratedUserGroups.isEmpty()) {
			return;
		}

		UserGroupLocalServiceUtil.addUserUserGroups(userId, integratedUserGroups);
	}

	protected Collection<String> getAcceptedRoleValue(long companyId, long roleId) {
		try {
			String className = Role.class.getName();
			String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;
			String columnName = CustomFieldConstants.ROLE_COL;

			ExpandoValue expandoObject = ExpandoService.getExpandoValue(
					companyId, className, tableName, columnName, roleId);

			if (expandoObject != null) {
				return Arrays
						.asList(expandoObject.getStringArray()).stream().map(String::trim)
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	protected Collection<String> getAcceptedGroupValue(long companyId, long userGroupId) {
		try {
			String className = UserGroup.class.getName();
			String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;
			String columnName = CustomFieldConstants.USER_GROUP_COL;

			ExpandoValue expandoObject = ExpandoService.getExpandoValue(
					companyId, className, tableName, columnName, userGroupId);

			if (expandoObject != null) {
				return Arrays
						.asList(expandoObject.getStringArray()).stream().map(String::trim)
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

}