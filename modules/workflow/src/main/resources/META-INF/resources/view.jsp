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

<div class="hisc-workflow">
    <liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
    <div class="card-divider"></div>
    <h1><liferay-ui:message key="workflow.title" /></h1>
    <liferay-ui:error key="error500" message="workflow.internalError" />

    <c:if test="${total > 0}">
        <div class="workflow-list">
            <liferay-ui:search-container delta="10" emptyResultsMessage="workflow.nodata" total="${total}">
                <liferay-ui:search-container-results results="<%= WorkflowService.fetchWorkflows(renderRequest, searchContainer.getCur(), searchContainer.getDelta()).getResults() %>" />
                <liferay-ui:search-container-row className="com.hiop.hisc.workflow.dto.Workflow" keyProperty="entryId"
                    modelVar="entry" escapedModel="<%=true%>">
                    <portlet:renderURL var="workflowDetailURL">
                        <portlet:param name="mvcPath" value="/workflow-detail.jsp" />
                        <portlet:param name="workflowId" value="<%= entry.getId() %>" />
                    </portlet:renderURL>
                    <liferay-ui:search-container-column-text name="workflow.name" value="<%=entry.getName() %>" href="${workflowDetailURL}"/>
                    <%
                        Date convertedDate = sourceFormat.parse(entry.getCreatedAt());
                        String createdAt = destFormat.format(convertedDate);
                     %>
                    <liferay-ui:search-container-column-text name="workflow.createdDate" value="<%= createdAt %>" />
                    <liferay-ui:search-container-column-text name="workflow.createdBy" value="<%=entry.getCreatedBy() %>" />
                    <liferay-ui:search-container-column-text name="workflow.public" value='<%=entry.isPublish()? "True" : "False" %>' />
                    <liferay-ui:search-container-column-text name="workflow.active" value='<%=entry.isActive()? "True" : "False" %>' />
                    <liferay-ui:search-container-column-text name="workflow.deleted" value='<%=entry.isDelete()? "True" : "False" %>' />
                </liferay-ui:search-container-row>
                <liferay-ui:search-iterator markupView="lexicon" />
            </liferay-ui:search-container>
        </div>
    </c:if>
</div>
