<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.hiop.hisc.reports.web.util.ReportsUtil" %>
<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
String reports = ReportsUtil.getAllReportsJson(themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId()).toString();
%>

<clay:container-fluid cssClass="container-form-lg entry-body reports-multiple-configuration">
  <liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />
  <aui:form action="<%= configurationActionURL %>" method="post" name="fm">
    <div class="lfr-form-content">
      <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

      <liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />
      <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

      <aui:fieldset>
        <div id="<portlet:namespace />select-reports"></div>
      </aui:fieldset>

      <aui:button-row>
        <aui:button type="submit" />
        <aui:button type="cancel" />
      </aui:button-row>
    </div>
  </aui:form>
</clay:container-fluid>

<script type="text/javascript">
  Reports.default(
    '<portlet:namespace />select-reports',
    {
      data: JSON.parse('<%= reports %>'),
      selected: JSON.parse('<%= (String)portletPreferences.getValue("selectedReports", "[]") %>'),
      outputId: '<portlet:namespace />selectedReports',
      type: 'multipleConfig',
      showFilters: JSON.parse('<%= (String)portletPreferences.getValue("showFilters", "false") %>'),
      outputStatus: '<portlet:namespace />showFilters'
    });
</script>