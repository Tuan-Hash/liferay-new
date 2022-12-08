<%@ include file="/init.jsp" %>

<%

Catalog catalog = (Catalog) request.getAttribute("catalog");
if (catalog != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, catalog.getCategory().getName(), (String) request.getAttribute("cancelURL"));
	PortalUtil.addPortletBreadcrumbEntry(request, catalog.getName(), PortalUtil.getCurrentURL(request));
}
%>

<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

	<script type="text/javascript">
		var portletElementID = '<portlet:namespace />' + '-root';
		var portletDataProps = {
			catalogId: '${ catalog.id }',
			schema: ${ catalog.jsonSchema },
			uiSchema: ${ catalog.uiSchema },
			initData: ${ catalog.formData },
			cancelURL: '${cancelURL}',
			submitURL: '${submitURL}',
			};
		window.Liferay.Hisc = {
			portletElementID, portletDataProps
		};
	</script>

	<div id="hisc-sc-request-form">
		<liferay-ui:error key="error500" message="Internal server error" />
		<c:if test="${catalog != null}">
			<liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
			<div class="card-divider"></div>
			<h2 class="catalog-title">${catalog.name}</h2>
			<div id="<portlet:namespace />-root" class="request-form"></div>
		</c:if>
	</div>