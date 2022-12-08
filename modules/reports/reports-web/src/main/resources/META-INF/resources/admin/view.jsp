<%@ include file="init.jsp" %>
<% 
renderResponse.setTitle(LanguageUtil.get(request, "categories.title" ));
ReportsCategoriesDisplayContext displayContext=new ReportsCategoriesDisplayContext(request, liferayPortletResponse);
%>

<clay:management-toolbar showSearch="<%= false %>" showSelectAllButton="<%= false %>" selectable="<%= false %>"
	creationMenu="<%= displayContext.getCreationMenu() %>" />

<div class="container" id="hisc-report-management">
	<liferay-ui:search-container delta="10" emptyResultsMessage="category.nodata" total="${fn:length(categories)}">
		<liferay-ui:search-container-results results="${categories}" />
		<liferay-ui:search-container-row className="com.hiop.hisc.reports.model.Category"
			keyProperty="categoryId" modelVar="category" escapedModel="<%=true%>">
			<portlet:renderURL var="listReportURL">
				<portlet:param name="mvcPath" value="/admin/view_reports.jsp" />
				<portlet:param name="categoryId" value="${category.categoryId}" />
			</portlet:renderURL>
			<liferay-ui:search-container-column-text name="category.name" value="<%= category.getName() %>"
				href="${listReportURL}" cssClass="table-cell-expand" />

			<liferay-ui:search-container-column-text name="category.description"
				value="<%= category.getDescription() %>" cssClass="table-cell-expand" />

			<liferay-ui:search-container-column-text cssClass="table-autofit">
				<div class="action-list d-flex justify-content-lg-around" style="width: 40px;">
					<portlet:renderURL var="editURL">
						<portlet:param name="mvcPath" value="/admin/edit_category.jsp" />
						<portlet:param name="categoryId" value="${category.categoryId}" />
					</portlet:renderURL>
					<div class="edit-icon">
						<a href="${editURL}">
							<clay:icon symbol="pencil" />
						</a>
					</div>

					<portlet:actionURL name="deleteCategory" var="deleteURL">
						<portlet:param name="categoryId" value="${category.categoryId}" />
					</portlet:actionURL>
					<div class="delete-icon">
						<c:set var="confirmMessage"><liferay-ui:message key="category.confirm-delete" arguments="${category.name}" /></c:set>
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