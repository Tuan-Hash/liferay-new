<%@ include file="init.jsp" %>
<%
Category category = (Category) request.getAttribute("category");
renderResponse.setTitle(category.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(renderResponse.createRenderURL().toString());

ReportsReportsDisplayContext displayContext = new ReportsReportsDisplayContext(request, liferayPortletResponse, category.getCategoryId());
%>

<clay:management-toolbar showSearch="<%= false %>" showSelectAllButton="<%= false %>" selectable="<%= false %>"
	creationMenu="<%= displayContext.getCreationMenu() %>" />

<div class="container" id="hisc-report-management">
	<liferay-ui:search-container delta="10" emptyResultsMessage="report.nodata" total="${fn:length(reports)}">
		<liferay-ui:search-container-results results="${reports}" />
		<liferay-ui:search-container-row className="com.hiop.hisc.reports.model.Report"
			keyProperty="reportId" modelVar="report" escapedModel="<%=true%>">
			<liferay-ui:search-container-column-text name="report.name" value="<%= report.getName() %>"
				cssClass="table-cell-expand" />

			<liferay-ui:search-container-column-text name="report.description"
				value="<%= report.getDescription() %>" cssClass="table-cell-expand" />

			<liferay-ui:search-container-column-text cssClass="table-autofit">
				<div class="action-list d-flex justify-content-lg-around" style="width: 40px;">
					<portlet:renderURL var="editURL">
                        <portlet:param name="mvcPath" value="/admin/edit_report.jsp" />
                        <portlet:param name="categoryId" value="${category.categoryId}" />
                        <portlet:param name="reportId" value="${report.reportId}" />
                    </portlet:renderURL>
					<div class="edit-icon">
						<a href="${editURL}">
							<clay:icon symbol="pencil" />
						</a>
					</div>

					<portlet:actionURL name="deleteReport" var="deleteURL">
                        <portlet:param name="mvcPath" value="/admin/view_reports.jsp" />
                        <portlet:param name="categoryId" value="${category.categoryId}" />
                        <portlet:param name="reportId" value="${report.reportId}" />
                    </portlet:actionURL>
					<div class="delete-icon">
						<c:set var="confirmMessage"><liferay-ui:message key="report.confirm-delete" arguments="${report.name}" /></c:set>
						<liferay-ui:icon-delete
						confirmation="${confirmMessage}"
						showIcon="true"
						message="Delete"
						url="${deleteURL}"
						/>
					</div>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>
