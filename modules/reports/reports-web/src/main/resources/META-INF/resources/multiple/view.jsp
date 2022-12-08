<%@ include file="/init.jsp" %>
<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

<div class="report" id="hisc-report">
    <div class="row w-100 ">
        <div class="col-3 report-sidebar">
            <%@ include file="sidebar.jsp" %>
        </div>

        <div class="col-9">

            <div class="report-content px-5">
                <liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
                <div class="report-header">
                    <c:if test="${categoryId == null}">
                        <h1>Report</h1>
                    </c:if>
                    <c:forEach var="category" items="${displayData}">
                        <c:if test="${categoryId == category.id}">
                            <h1>${category.name}</h1>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="report-list">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Category</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="category" items="${displayData}">
                                <c:forEach var="report" items="${category.reports}">
                                    <c:if test="${categoryId == null || categoryId == category.id}">
                                        <tr>
                                            <td>
                                                <span>
                                                    <i class="${report.icon}"></i>
                                                </span>
                                                <portlet:renderURL var="reportURL">
                                                    <portlet:param name="mvcPath" value="/multiple/report.jsp" />
                                                    <portlet:param name="categoryId" value="${category.id}" />
                                                    <portlet:param name="reportId" value="${report.reportId}" />
                                                </portlet:renderURL>
                                                <a href="${reportURL}">
                                                    ${report.name}
                                                </a>
                                            </td>
                                            <td>
                                                <div class="report-list-category">
                                                    <div>
                                                        <span>
                                                            <i class="${category.icon}"></i>
                                                        </span>
                                                        <span>
                                                            ${category.name}
                                                        </span>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>