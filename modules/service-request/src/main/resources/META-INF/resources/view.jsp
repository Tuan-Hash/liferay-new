<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ include file="init.jsp" %>
<%
    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sourceFormat.setTimeZone(themeDisplay.getUser().getTimeZone());
%>

<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

<div class="hisc-request">
    <liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
    <div class="card-divider"></div>
    <h1><liferay-ui:message key="request.title" /></h1>
    <liferay-ui:error key="error500" message="request.internalError" />

    <c:if test="${total > 0}">
        <div class="request-list">
            <liferay-ui:search-container delta="10" emptyResultsMessage="request.nodata" total="${total}">
                <liferay-ui:search-container-results results="<%= RequestService.fetchRequests(renderRequest, searchContainer.getCur(), searchContainer.getDelta()).getResults() %>" />
                <liferay-ui:search-container-row className="com.hiop.hisc.servicerequest.dto.ServiceRequest" keyProperty="entryId" modelVar="entry" escapedModel="<%=true%>">
                    <portlet:renderURL var="requestDetailURL">
                        <portlet:param name="mvcPath" value="/request.jsp" />
                        <portlet:param name="requestId" value="<%= entry.getId() %>" />
                        <portlet:param name="catalogId" value="<%= entry.getCatalog().getId() %>" />
                        <portlet:param name="previousUrl" value="<%= themeDisplay.getURLCurrent() %>" />
                    </portlet:renderURL>
                    <portlet:renderURL var="externalRequestDetailURL">
                        <portlet:param name="mvcPath" value="/request.jsp" />
                        <portlet:param name="requestId" value="<%= entry.getServiceNowRequest().getRequestId() %>" />
                        <portlet:param name="catalogId" value="<%= entry.getCatalog().getId() %>" />
                        <portlet:param name="previousUrl" value="<%= themeDisplay.getURLCurrent() %>" />
                    </portlet:renderURL>
                    <liferay-ui:search-container-column-text name="request.number" value="<%=entry.getNumber() %>" href="${requestDetailURL}" />
                    <liferay-ui:search-container-column-text name="request.serviceNowNumber" value="<%=entry.getServiceNowRequest().getExternalId() %>" href="${externalRequestDetailURL}" />
                    <liferay-ui:search-container-column-text name="request.name" value="<%=entry.getCatalog().getName() %>" />
                    <%
                        String state = entry.getState();
                        String stateColor = "";

                        if (state.equals("New")) {
                            stateColor = "orange";
                        }

                        if (state.equals("In approval")) {
                            stateColor = "cyan";
                        }

                        if (state.equals("In progress")) {
                            stateColor = "blue";
                        }

                        if (state.equals("Failed")) {
                            stateColor = "red";
                        }

                        if (state.equals("Canceled")) {
                            stateColor = "orange";
                        }

                        if (state.equals("Pause")) {
                            stateColor = "grey";
                        }

                        if (state.equals("Pending")) {
                            stateColor = "grey";
                        }

                        if (state.equals("Running")) {
                            stateColor = "grey";
                        }

                        if (state.equals("Rejected")) {
                            stateColor = "red";
                        }

                        if (state.equals("Success")) {
                            stateColor = "green";
                        }
					%>
                    <liferay-ui:search-container-column-text name="request.state">
                        <clay:label label="<%=entry.getState() %>" cssClass="<%= stateColor %>" />
                    </liferay-ui:search-container-column-text>
                    <liferay-ui:search-container-column-text name="request.createdBy" value="<%=entry.getCreatedBy() %>" />
					<%
						Date createdDate = sourceFormat.parse(entry.getCreatedAt());
						String createdAt = destFormat.format(createdDate);
					%>
                    <liferay-ui:search-container-column-text name="request.createdDate" value="<%= createdAt %>" />
                    <%
						Date updatedDate = sourceFormat.parse(entry.getUpdatedAt());
						String updatedAt = destFormat.format(updatedDate);
					%>
					<liferay-ui:search-container-column-text name="request.lastModifiedDate" value="<%= updatedAt %>" />
                </liferay-ui:search-container-row>
                <liferay-ui:search-iterator markupView="lexicon" />
            </liferay-ui:search-container>
        </div>
    </c:if>
</div>
