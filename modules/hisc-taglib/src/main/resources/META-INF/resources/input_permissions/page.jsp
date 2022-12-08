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

<%@ include file="init.jsp" %>

<%
String uniqueNamespace = namespace + PortalUtil.getUniqueElementId(request, namespace, StringPool.BLANK);

if (!uniqueNamespace.endsWith(StringPool.UNDERLINE)) {
	uniqueNamespace = uniqueNamespace.concat(StringPool.UNDERLINE);
}
%>

<c:choose>
	<c:when test="<%= user.getDefaultUser() %>">
		<liferay-ui:message key="not-available" />
	</c:when>
	<c:otherwise>
		<%
		List<Role> _roles = RoleLocalServiceUtil.getRoles(
			themeDisplay.getCompanyId(), RoleConstants.TYPES_REGULAR_AND_SITE);

		List<Role> roles = _roles.stream()
			.filter(r -> !Arrays.asList(exclude).contains(r.getName()))
			.collect(Collectors.toList());

		Group siteGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getSiteGroupId());
		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(siteGroup.getGroupId());

		List supportedActions = (List)request.getAttribute("hisc-ui:input-permissions:supportedActions");
		List groupDefaultActions = (List)request.getAttribute("hisc-ui:input-permissions:groupDefaultActions");
		List guestDefaultActions = (List)request.getAttribute("hisc-ui:input-permissions:guestDefaultActions");
		List guestUnsupportedActions = (List)request.getAttribute("hisc-ui:input-permissions:guestUnsupportedActions");
		
		String modelName = (String)request.getAttribute("hisc-ui:input-permissions:modelName");
		String entryId = (String)request.getAttribute("hisc-ui:input-permissions:entryId");
		String modelLabel = (String)request.getAttribute("hisc-ui:input-permissions:modelLabel");
		boolean submitted = !"".equals(entryId);
		InputPermissionsDisplayContext displayContext = new InputPermissionsDisplayContext(request, modelName, entryId);
		%>
	
		<%@ include file="horizontal.jsp" %>
	
		<input type="hidden" id="<%= uniqueNamespace %>inputPermissionsViewRole" name="<%= namespace %>inputPermissionsViewRole" value="EMPTY" />
		<input type="hidden" id="<%= uniqueNamespace %><%= RoleConstants.ADMINISTRATOR %>" name="<%= namespace %>modelPermissions<%= RoleConstants.ADMINISTRATOR %>" value="VIEW" />
	</c:otherwise>
</c:choose>