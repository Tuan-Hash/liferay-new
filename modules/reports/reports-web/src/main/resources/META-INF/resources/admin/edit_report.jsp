<%@ include file="init.jsp" %>

<%
Category category = (Category) request.getAttribute("category");
PortletURL backURL = liferayPortletResponse.createRenderURL();
backURL.setParameter("mvcPath", "/admin/view_reports.jsp");
backURL.setParameter("categoryId", String.valueOf(category.getCategoryId()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

Report report = (Report) request.getAttribute("report");
String title = report != null ? report.getName() : LanguageUtil.get(request, "report.new");
renderResponse.setTitle(title);
%>

<portlet:actionURL var="getEmbedConfigURL" name="getEmbedConfig">
</portlet:actionURL>

<c:if test="${report == null}">
    <portlet:actionURL var="actionURL" name="createReport">
		<portlet:param name="mvcPath" value="/admin/view_reports.jsp" />
		<portlet:param name="categoryId" value="${category.categoryId}" />
	</portlet:actionURL>
</c:if>

<c:if test="${report != null}">
    <portlet:actionURL var="actionURL" name="updateReport">
		<portlet:param name="mvcPath" value="/admin/view_reports.jsp" />
        <portlet:param name="categoryId" value="${category.categoryId}" />
	    <portlet:param name="reportId" value="${report.reportId}" />
    </portlet:actionURL>
</c:if>

<clay:container-fluid cssClass="container-form-lg entry-body reports-edit-report">

    <aui:form action="${actionURL}" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm" id="fm">
        <div class="lfr-form-content">
            <aui:fieldset-group markupView="lexicon">
                <aui:select id="report-dropdown" name="name" label="report.name" required="<%= true %>" value="${report != null ? otherName : ''}">
                    <c:forEach var="divice" items="${listDevice}">
                        <aui:option value="${divice}">${divice}</aui:option>
                    </c:forEach>
                    <aui:option  value="Other">Other</aui:option>
                </aui:select>
                <aui:input name="other" label="Other Name" type="text" value="${report != null ? report.name : ''}" id="report-other">
                    <aui:validator name="required"  errorMessage="The Other Name field is required.">
                        function() {
                            return AUI.$('#<portlet:namespace />report-other').is(":visible");
                              }
                    </aui:validator>
                    <aui:validator name="maxLength" errorMessage="The max length of this field is 255 characters.">
                    255
                    </aui:validator>
                    <aui:validator errorMessage="The value already exists." 
                    name="custom">
                        function(val, fieldNode, ruleValue) {
                            if (AUI.$('#<portlet:namespace />report-other').is(":hidden")) {
                                return true;
                            }
                            var report = <%= report %>;
                            if (report && val == report.name) {
                                return true;
                            }
                            var valid = true;
                            var reports = ${reports};
                            var devices = ${devices};
                            reports.forEach((report) => {
                                if (report.name == val) {
                                    valid = false;
                                }
                            })
                            devices.forEach((device) => {
                                if (device == val) {
                                    valid = false;
                                }
                            })
                            
                            return valid;
                        }
                    </aui:validator>
                </aui:input>
                <aui:input label="report.description" name="description"
                    value="${report != null ? report.description : ''}" />
                <aui:input label="report.icon" name="icon" value="${report != null ? report.icon : ''}" />
                <aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="report.sources">
                    <aui:input type="hidden" name="sources" value="${report != null ? report.sources : '[]'}" />
                    <div id="<portlet:namespace />-sources-layout" class="sources-layout"></div>
                </aui:fieldset>

                <div class="btn-group">
                    <div class="btn-group-item">
                        <aui:button name="saveButton" type="submit" value="${report != null ? 'Update' : 'Add'}" />
                    </div>
                    <div class="btn-group-item">
                        <aui:button href="<%= backURL.toString() %>" type="cancel" />
                    </div>
                </div>
            </aui:fieldset-group>
        </div>
    </aui:form>
</clay:container-fluid>

<script type="text/javascript">
    Reports.default(
        '<portlet:namespace />-sources-layout',
        {
            isAdmin: true,
            namespace: '<portlet:namespace />',
            outputId: '<portlet:namespace />sources',
            originSchema: JSON.parse('${report != null ? report.sources : "[]"}'),
            getConfigURL: '${getEmbedConfigURL}'
        }
    );
</script>

<aui:script>
AUI().use('event', 'node', function(A) {
    var dropdown = A.one('#<portlet:namespace />report-dropdown');
    if (A.one("#<portlet:namespace />report-dropdown").get('value') != "Other") {
        A.one("label[for=<portlet:namespace />report-other]").hide();
	    A.one('#<portlet:namespace />report-other').hide();
    }
    else {
        A.one("label[for=<portlet:namespace />report-other]").show();
	    A.one('#<portlet:namespace />report-other').show();
    }
	
	dropdown.on('change', function(event) {

		if (A.one("#<portlet:namespace />report-dropdown").get('value') != "Other") {
            A.one("label[for=<portlet:namespace />report-other]").hide();
			A.one('#<portlet:namespace />report-other').hide();
            A.one("#<portlet:namespace />report-otherHelper").hide();

		}
		else {
			A.one('#<portlet:namespace />report-other').show();
            A.one("label[for=<portlet:namespace />report-other]").show();
            A.one('#<portlet:namespace />report-other').val("");
		} 

	});

});
</aui:script>