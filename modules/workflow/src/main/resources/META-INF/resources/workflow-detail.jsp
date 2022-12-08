<%@ include file="init.jsp" %>

<%
Workflow workflow = (Workflow) request.getAttribute("workflow");
if (workflow != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, workflow.getName(), PortalUtil.getCurrentURL(request));
}
%>

<%
int[] types = {BreadcrumbUtil.ENTRY_TYPE_LAYOUT, BreadcrumbUtil.ENTRY_TYPE_PORTLET};
List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, types);
%>

<script type="text/javascript">
    var portletElementID = '<portlet:namespace />' + '-root';
    var portletDataProps = {
        workflowId: '${ workflow.id }',
        metaSource: ${ workflow.metaYaml },
        workflowSource: ${ workflow.workflowYaml }
    };
    window.Liferay.HiscWorkflow = {
        portletElementID, portletDataProps
    };
</script>

<div class="hisc-workflow-detail">
    <liferay-ui:error key="error500" message="Internal server error" />
    <c:if test="${workflow != null}">
        <liferay-site-navigation:breadcrumb breadcrumbEntries="<%= breadcrumbEntries %>" />
        <div class="card-divider"></div>
        <h2 class="catalog-title">${workflow.name}</h2>
        <div class="catalog-workflow">
            <div id="<portlet:namespace />-root" class="workflow-canvas p-0"></div>
            <button class="btn btn-secondary related-button closed" id="related-sidebar-button"  onclick="collapseRelatedCatalogs()">
                <span class="closed">
                    <clay:icon symbol="angle-left" />
                </span>
                <span class="open">
                    <clay:icon symbol="angle-right" />
                </span>
            </button>  
            
            <div class="workflow-related-list closed" id="reference-related-sidebar">
                <div id="workflow-related-list-content">
                    <div class="related-title">
                        <h4 ><liferay-ui:message key="workflow.related_list" /></h4>
                    </div>
                    <ul class="list-group show-quick-actions-on-hover related-list-item">
                        <c:forEach items="${catalogs.results}" var="catalog">
                            <li class="list-group-item list-group-item-flex" title="${catalog.name}">
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
                                                <span class="text-truncate">${catalog.name}</span>
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
    </c:if>
</div>
<script>
    

    function collapseRelatedCatalogs(e) {
        var button = document.getElementById("related-sidebar-button");
        var sidebar = document.getElementById("reference-related-sidebar");
        if (button.classList.contains("closed")) {
            button.classList.remove("closed");
            button.classList.add("open");
            sidebar.classList.remove("closed");
            sidebar.classList.add("open");
        }
        else {
            button.classList.remove("open");
            button.classList.add("closed");
            sidebar.classList.remove("open");
            sidebar.classList.add("closed");
        }
    }
</script>
