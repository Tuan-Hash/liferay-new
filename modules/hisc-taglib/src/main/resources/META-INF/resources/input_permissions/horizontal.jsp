<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%--

Do not update the logic in this JSP without also updating
ServiceContext#deriveDefaultPermissions(long, String).

--%>

<div class="permissions-table-container table-responsive" id="<%= uniqueNamespace %>inputPermissionsTable">
	<table class="table table-list">
		<thead>
			<tr>
				<th>
					<liferay-ui:message key="role" />
				</th>

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);
				%>

					<th class="table-column-text-center">
						<%= ResourceActionsUtil.getAction(request, action) %>
					</th>

				<%
				}
				%>

			</tr>
		</thead>

		<%
		for (Role role : roles) {
			String roleName = role.getName();
			String permissionName = "modelPermissions" + roleName;
			if (!uniqueNamespace.equals(namespace)) {
				permissionName = permissionName + StringPool.UNDERLINE + modelName;
			}

			List<String> currentIndividualActions = displayContext.getIndividualActions(role);
			List<String> currentGroupActions = displayContext.getGroupActions(role);
			List<String> currentGroupTemplateActions = displayContext.getGroupTemplateActions(role);
			List<String> currentCompanyActions = displayContext.getCompanyActions(role);
		%>

			<tr>
				<td class="table-title">
					<%= role.getTitle(themeDisplay.getLocale()) %>
				</td>

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					boolean checked = false;
					boolean readonly = false;
					if (currentIndividualActions.contains(action) || currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action) || currentCompanyActions.contains(action)) {
						checked = true;
					} else if (roleName.equals(RoleConstants.GUEST) && !submitted) {
						checked = guestDefaultActions.contains(action);
					} else if (roleName.equals(RoleConstants.OWNER) && !submitted) {
						checked = true;
					} else if (roleName.equals(defaultGroupRole.getName()) && !submitted) {
						checked = groupDefaultActions.contains(action);
					}

					String preselectedMsg = StringPool.BLANK;
					if (currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action)) {
						preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-x";
						readonly = true;
					}
					else if (currentCompanyActions.contains(action)) {
						preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-this-portal-instance";
						readonly = true;
					}

					boolean disabled = false;
					if (roleName.equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(action)) {
						disabled = true;
						checked = false;
					}

					String checkboxFieldName = namespace + permissionName;
					String checkboxFieldId = uniqueNamespace + "modelPermissions" + roleName + StringPool.UNDERLINE + action;
				%>

					<td class="table-column-text-center">
						<label class="sr-only" for="<%= checkboxFieldId %>"><liferay-ui:message arguments="<%= new Object[] {ResourceActionsUtil.getAction(request, action), role.getTitle(themeDisplay.getLocale())} %>" key="give-x-permission-to-users-with-role-x" translateArguments="<%= false %>" /></label>
						<input <%=checked ? "checked" : "" %>
							<%= disabled ? "disabled" : "" %> id="<%= checkboxFieldId %>"
							name="<%= readonly ? "" : checkboxFieldName %>"
							<%= readonly ? "class='lfr-checkbox-preselected lfr-portal-tooltip'" : "" %>
							<%= readonly ? "onclick='return false;'" : "" %>
							title='<%= LanguageUtil.format(request, preselectedMsg, new Object[] {
												role.getTitle(themeDisplay.getLocale()), ResourceActionsUtil.getAction(request, action), modelLabel,
												themeDisplay.getSiteGroupName() }, false) %>'
							type="checkbox" value="<%= action %>" />
					</td>

				<%
				}
				%>

			</tr>

		<%
		}
		%>

	</table>
</div>