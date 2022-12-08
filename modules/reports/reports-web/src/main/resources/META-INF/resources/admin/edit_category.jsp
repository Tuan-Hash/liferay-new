<%@ include file="init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(renderResponse.createRenderURL().toString());

Category category = (Category) request.getAttribute("category");
String title = category != null ? category.getName() : LanguageUtil.get(request, "category.new");
renderResponse.setTitle(title);
%>

<c:if test="${category == null}">
    <portlet:actionURL var="actionURL" name="createCategory" />
</c:if>
<c:if test="${category != null}">
    <portlet:actionURL var="actionURL" name="updateCategory">
        <portlet:param name="categoryId" value="${category.categoryId}" />
    </portlet:actionURL>
</c:if>
<portlet:renderURL var="cancelURL"></portlet:renderURL>

<clay:container-fluid cssClass="container-form-lg entry-body">
    <aui:form action="${actionURL}" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm">
        <div class="lfr-form-content">
            <aui:fieldset-group markupView="lexicon">
                <aui:input label="category.name" name="name" required="<%= true %>"
                    value="${category != null ? category.name : ''}" />
                <aui:input label="category.description" name="description"
                    value="${category != null ? category.description : ''}" />
                <aui:input label="category.icon" name="icon" value="${category != null ? category.icon : ''}" />

                <aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="category.permissions">
                    <hisc-ui:input-permissions modelName="<%= Category.class.getName() %>" entryId="${category.categoryId}" modelLabel="Report Categories" />
                </aui:fieldset>

                <div class="btn-group">
                    <div class="btn-group-item">
                        <aui:button name="saveButton" type="submit" value="${category != null ? 'Update' : 'Add'}" />
                    </div>
                    <div class="btn-group-item">
                        <aui:button href="${cancelURL}" type="cancel" />
                    </div>
                </div>
            </aui:fieldset-group>
        </div>
    </aui:form>
</clay:container-fluid>