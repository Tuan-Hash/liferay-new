<%@ include file="/init.jsp" %>

<%
ServiceRequest serviceRequest = (ServiceRequest) request.getAttribute("serviceRequest");
if (serviceRequest != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, serviceRequest.getNumber(), PortalUtil.getCurrentURL(request));
}
%>

<%
    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sourceFormat.setTimeZone(themeDisplay.getUser().getTimeZone());
%>

<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

	<div id="hisc-sr-request-form" class="hisc-request-detail">
		<liferay-ui:error key="error500" message="Internal server error" />
		<c:if test="${serviceRequest != null}">
			<liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
			<div class="card-divider"></div>
			<div class="request-body">
				<div class="request-section">
					<label class="request-label">Service Catalog</label>
					<input class="request-input" type="text" value="${serviceRequest.catalog.name}" readonly />
				</div>
				<div class="request-section">
					<label class="request-label">State</label>
					<input class="request-input" type="text" value="${serviceRequest.state}" readonly />
				</div>
				<div class="request-section">
					<label class="request-label">Created By</label>
					<input class="request-input" type="text" value="${serviceRequest.createdBy}" readonly />
				</div>
				<%
					Date createdDate = sourceFormat.parse(serviceRequest.getCreatedAt());
					String createdAt = destFormat.format(createdDate);
				%>
				<div class="request-section">
					<label class="request-label">Created Date</label>
					<input class="request-input" type="text" value="<%= createdAt %>" readonly />
				</div>
				<%
					Date updatedDate = sourceFormat.parse(serviceRequest.getUpdatedAt());
					String updatedAt = destFormat.format(updatedDate);
				%>
				<div class="request-section">
					<label class="request-label">Last Modified Date</label>
					<input class="request-input" type="text" value="<%= updatedAt %>" readonly />
				</div>
				<div class="request-detail">
					<label class="request-label">Detail Information</label>
					<div id="<portlet:namespace />-form" class="request-form"></div>
				</div>
				<div class="request-external-systems">
					<div id="<portlet:namespace />-list" class="request-list"></div>
				</div>
				<div class="request-footer">
					<div class="request-footer__buttons">
						<aui:button value="Close" href="${closeURL}" type="cancel"  />
					</div>
				</div>
			</div>
		</c:if>
	</div>

	<script type="text/javascript">
		Requests.default(
		  '<portlet:namespace />-form',
		  {
			catalogId: '${serviceRequest.catalog.id}',
			schema: ${serviceRequest.catalog.jsonSchema},
			uiSchema: ${serviceRequest.catalog.uiSchema},
			initData: ${serviceRequest.formData},
		  });
	</script>

	<script type="text/javascript">
		Requests.default(
		  '<portlet:namespace />-list',
		  {
			executionId: '${execution.st2ExecutionId}',
			tenantUrl: '${tenantUrl}',
			type: "listConfig",
		  });
	</script>