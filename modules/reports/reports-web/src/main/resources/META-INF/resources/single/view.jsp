<%@ include file="/init.jsp" %>
<%
long groupId = scopeGroupId;
String name = portletDisplay.getRootPortletId();
String primKey = portletDisplay.getResourcePK();
boolean canEdit = permissionChecker.hasPermission(groupId, name, primKey, "CUSTOMIZE");
%>

  <div class="hisc-dashboard-web">
    <liferay-ui:error key="error500" message="Internal server error" />
    <div id="<portlet:namespace />-root"></div>
  </div>

  <portlet:actionURL var="saveCustomReportLayoutURL" name="saveCustomReportLayout">
  </portlet:actionURL>

  <portlet:actionURL var="getEmbedConfigURL" name="getEmbedConfig">
  </portlet:actionURL>

  <script type="text/javascript">
    Reports.default(
      '<portlet:namespace />-root',
      {
        saveURL: '${saveCustomReportLayoutURL}',
        namespace: '<portlet:namespace />',
        originSchema: JSON.parse('${report != null ? report.sources : "[]"}'),
				customSchema: JSON.parse('${customSchema}'),
			  getConfigURL: '${getEmbedConfigURL}',
        showFilters: JSON.parse('${showFilters}'),
				canEdit: <%= canEdit %>
      });
  </script>