<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %>
<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.liferay.portal.kernel.exception.MembershipRequestCommentsException" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Group" %>
<%@ page import="com.liferay.portal.kernel.model.MembershipRequest" %>
<%@ page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.hiop.hisc.ternant.web.internal.TenantDisplayContext" %>
<%-- <%@ page import="com.liferay.portal.liveusers.LiveUsers" %> --%>
<%@ page import="com.liferay.portal.util.PropsValues" %>
<%-- <%@ page import="com.liferay.site.my.sites.web.internal.display.context.SiteMySitesDisplayContext" %>
<%@ page import="com.liferay.site.my.sites.web.internal.display.context.SiteMySitesManagementToolbarDisplayContext" %>
<%@ page import="com.liferay.site.my.sites.web.internal.servlet.taglib.clay.SiteVerticalCard" %> --%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
TenantDisplayContext tenantDisplayContext = new TenantDisplayContext(renderRequest, renderResponse);
%>

<%
	// SiteMySitesDisplayContext siteMySitesDisplayContext = new SiteMySitesDisplayContext(renderRequest, renderResponse);
%>