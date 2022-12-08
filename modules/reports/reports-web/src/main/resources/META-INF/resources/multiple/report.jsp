<%@ include file="/init.jsp" %>
<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

<div class="report" id="hisc-report">
	<div class="row w-100 ">
		<div class="col-3 report-sidebar">
			<%@ include file="sidebar.jsp" %>
		</div>

		<div class="col-9">
			<div class="report-content">
				<liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
				<div class="report-header">
					<h1>${title}</h1>
				</div>
				<div class="report-list">
					<div id="<portlet:namespace />-root"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<%
long groupId = scopeGroupId;
String name = portletDisplay.getRootPortletId();
String primKey = portletDisplay.getResourcePK();
boolean canEdit = permissionChecker.hasPermission(groupId, name, primKey, "CUSTOMIZE");
%>

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
			originSchema: JSON.parse('${sources != null ? sources : "[]"}'),
			customSchema: JSON.parse('${customSchema}'),
			getConfigURL: '${getEmbedConfigURL}',
			showFilters: JSON.parse('${showFilters}'),
			canEdit: <%= canEdit %>
		});
</script>