<%@ include file="/init.jsp" %>
<% 
	renderResponse.setTitle(LanguageUtil.get(request, "serviceCatalogs.title" ));
	ServiceCatalogDisplayContext displayContext = new ServiceCatalogDisplayContext(renderRequest, renderResponse, permissionChecker, resourceBundle);
%>

<clay:management-toolbar 
	showSearch="${total > 0}" 
	showSelectAllButton="<%= false %>"
	selectable="<%= false %>"
/>

<div class="container" id="hisc-service-catalogs-management">
	<div class="service-catalog__list">
		<liferay-ui:search-container delta="10" emptyResultsMessage="serviceCatalog.nodata" total="${total}">
			<liferay-ui:search-container-results results='<%= CatalogAdminService.fetchCatalogs(renderRequest, null, null, searchContainer.getCur(), searchContainer.getDelta(), "name", "ASC").getResults() %>' />
			<liferay-ui:search-container-row className="com.hiop.hisc.servicecatalogs.web.dto.Catalog" keyProperty="entryId" modelVar="entry" escapedModel="<%=true%>">
				<liferay-ui:search-container-column-text name="serviceCatalog.category" value="<%=entry.getCategory().getName() %>" />
				<liferay-ui:search-container-column-text name="serviceCatalog.serviceCatalogs" value="<%=entry.getName() %>" />
				<liferay-ui:search-container-column-text cssClass="table-autofit" valign="right">
					<clay:dropdown-actions
						dropdownItems="<%= displayContext.getDropdownItems(entry) %>"
						propsTransformer="js/ElementsPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</div>