<%@ include file="/init.jsp" %>
<div class="py-5">
	<div class="">
		<ul class="nav nav-nested">
			<li class="nav-item ">
				<span
					class="nav-link ${ categoryId == null && reportId == null ? 'sidebar-link-active' : 'not-active'}">
					<portlet:renderURL var="allURL"></portlet:renderURL>
					<a class="category-link sidebar-link-all" href="${allURL}">All</a>
				</span>
			</li>

			<c:forEach var="category" items="${displayData}">
				<li class="nav-item">
					<div
						class="nav-link ${ category.id == categoryId && reportId == null ? 'sidebar-link-active' : 'not-active'}">
						<portlet:renderURL var="cateURL">
							<portlet:param name="categoryId" value="${category.id}" />
						</portlet:renderURL>
						<a class="category-link" href="${cateURL}">
							${category.name}
						</a>
						<span class="collapse-icon" href="#report-${category.id}"
							aria-controls="'#report-'+${category.id}" aria-expanded="true"
							data-toggle="liferay-collapse" role="button">
							<span class="collapse-icon-closed">
								<clay:icon symbol="angle-right" />
							</span>
							<span class="collapse-icon-open">
								<clay:icon symbol="angle-down" />
							</span>
					</div>
					<div class="collapse show" id="report-${category.id}">
						<ul class="nav nav-stacked">
							<c:forEach var="report" items="${category.reports}">
								<li
									class="nav-link ${report.reportId == reportId  ? 'sidebar-link-active' : 'sidebar-link-not-active'}">
									<div class="report-link">
										<portlet:renderURL var="reportURL">
											<portlet:param name="mvcPath" value="/multiple/report.jsp" />
											<portlet:param name="categoryId" value="${category.id}" />
											<portlet:param name="reportId" value="${report.reportId}" />
										</portlet:renderURL>
										<a href="${reportURL}">${report.name}</a>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>