<%@ include file="/init.jsp" %>

<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.model.Group"%>
<%@page import="com.liferay.portal.kernel.model.Resource"%>
<%@page import="com.liferay.portal.kernel.model.Role"%>
<%@page import="com.liferay.portal.kernel.model.RoleConstants"%>
<%@page import="com.liferay.portal.kernel.security.permission.ActionKeys" %>
<%@page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %>
<%@page import="com.liferay.portal.kernel.service.permission.RolePermissionUtil"%>
<%@page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.util.PropsValues"%>

<%@page import="com.hiop.hisc.taglib.display.context.InputPermissionsDisplayContext"%>

<%
String[] exclude = {
  RoleConstants.ADMINISTRATOR,
  RoleConstants.SITE_ADMINISTRATOR,
  RoleConstants.SITE_OWNER
};
%>