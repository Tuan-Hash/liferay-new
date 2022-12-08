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

	<div class="hisc-service-catalog-categories">
		<liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
		<div class="card-divider"></div>

		<div class="list-categories c-mt-4">
			<liferay-ui:error key="error500" message="Internal server error" />
			<c:forEach items="${renderData.results}" var="category">
				<div class="card card-horizontal">
					<div class="card-row">
						<div class="autofit-col category-img">
							<c:choose>
								<c:when test='${category.image != null && category.image != ""}'>
									<img alt="thumbnail" class="card-item-first" src="${category.image}" />
								</c:when>
								<c:when test='${category.icon != null && category.icon != ""}'>
									<clay:icon symbol="${category.icon}" />
								</c:when>
								<c:otherwise>
									<clay:icon symbol="liferay-ac" />
								</c:otherwise>
							</c:choose>
						</div>
						<div class="autofit-col autofit-col-expand autofit-col-gutters">
							<section class="autofit-section">
								<h3 class="card-title">${category.name}</h3>
								<p class="card-text">${category.description}</p>
							</section>
						</div>
					</div>

					<c:if test="${fn:length(category.children) > 0}">
						<div class="card-row c-p-2">
							<div class="panel w-100" role="tablist">
								<button aria-controls="collapsePanel" aria-expanded="false"
									class="btn btn-unstyled panel-header panel-header-link collapse-icon collapse-icon-middle"
									data-target="#collapsePanel${category.id}" data-toggle="liferay-collapse" role="tab">
									<span class="panel-title">Related Categories</span>
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
											<c:forEach items="${category.children}" var="subCategory">												
												<li class="list-group-item list-group-item-flex">
													<div class="autofit-col autofit-col-expand">
														<section class="autofit-section">
															<div class="list-group-title">
																<span class="text-truncate-inline">
																	<portlet:renderURL var="subCategoryURL">
																		<portlet:param name="mvcPath" value="/catalog.jsp" />
																		<portlet:param name="categoryId" value="${subCategory.id}" />
																	</portlet:renderURL>
																	<a class="text-truncate" href="${subCategoryURL}">${subCategory.name}</a>
																</span>
															</div>
															<p class="list-group-subtext">
																<span class="text-truncate-inline">
																	<span class="text-truncate">${subCategory.description}</span>
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
			</c:forEach>
		</div>
	</div>