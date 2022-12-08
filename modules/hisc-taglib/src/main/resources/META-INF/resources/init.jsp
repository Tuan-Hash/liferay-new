<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.taglib.aui.AUIUtil" %>
<%@ page import="com.liferay.taglib.util.TagResourceBundleUtil"%>

<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.stream.Collectors"%>

<liferay-frontend:defineObjects />
<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
String namespace = AUIUtil.getNamespace(liferayPortletRequest, liferayPortletResponse);

ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(request, locale);
pageContext.setAttribute("resourceBundle", resourceBundle);
%>
