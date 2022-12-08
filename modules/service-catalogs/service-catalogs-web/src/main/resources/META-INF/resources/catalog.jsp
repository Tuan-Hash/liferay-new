<%@ include file="/init.jsp" %>

<% 
	Category category=(Category) request.getAttribute("category"); 
	if (category !=null) {
		PortalUtil.addPortletBreadcrumbEntry(request, category.getName(), PortalUtil.getCurrentURL(request)); 
	} 
%>

<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

<div class="hisc-service-catalog-catalogs">
	<liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
	<div class="card-divider"></div>

	<h1 class="c-mt-4">${category.name}</h1>
	<liferay-ui:error key="error500" message="Internal server error" />

	<c:if test='<%= SessionErrors.isEmpty(request) %>'>
		<div class="card-row c-p-2">
			<div class="panel w-100" role="tablist">
				<button aria-controls="collapsePanel" aria-expanded="false"
					class="btn btn-unstyled panel-header panel-header-link collapse-icon collapse-icon-middle"
					data-target="#collapsePanel${category.id}" data-toggle="liferay-collapse" role="tab">
					<span class="panel-title">Items</span>
					<span class="collapse-icon-closed">
						<clay:icon symbol="angle-right" />
					</span>
					<span class="collapse-icon-open">
						<clay:icon symbol="angle-down" />
					</span>
				</button>
				<div class="panel-collapse collapse show" id="collapsePanel${category.id}" role="tabpanel">
					<div class="panel-body">
						<ul class="list-group show-quick-actions-on-hover">
							<c:forEach items="${renderData.results}" var="catalog">
								<li class="list-group-item list-group-item-flex">
									<div class="autofit-col">
										<div class="sticker sticker-secondary">
											<span class="inline-item">
												<c:choose>
													<c:when test='${catalog.image != null && catalog.image != ""}'>
														<img class="card-item-first" src="${catalog.image}" />
													</c:when>
													<c:when test='${catalog.icon != null && catalog.icon != ""}'>
														<clay:icon symbol="${catalog.icon}" />
													</c:when>
													<c:otherwise>
														<clay:icon symbol="hdd" />
													</c:otherwise>
												</c:choose>
											</span>
										</div>
									</div>
									<div class="autofit-col autofit-col-expand">
										<section class="autofit-section">
											<div class="list-group-title">
												<span class="text-truncate-inline">
													<portlet:renderURL var="requestformURL">
														<portlet:param name="mvcPath" value="/request.jsp" />
														<portlet:param name="catalogId" value="${catalog.id}" />
													</portlet:renderURL>
													<a class="text-truncate" href="${requestformURL}">${catalog.name}</a>
												</span>
											</div>
											<p class="list-group-subtext">
												<span class="text-truncate-inline">
													<span class="text-truncate">${catalog.description}</span>
												</span>
											</p>
										</section>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>