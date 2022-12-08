<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ include file="/init.jsp" %>

<%
List<Group> groups = tenantDisplayContext.getGroups();
%>

<div id="hisc-tenant">
	<c:if test="<%= groups.size() == 0 %>">
		<liferay-frontend:empty-result-message
			title="<%= LanguageUtil.get(request, "tenant.nodata") %>"
			/>
	</c:if>

	<ul class="card-page card-page-equal-height">
		<%
		for (Group item: groups) {
			String siteImageURL = item.getLogoURL(themeDisplay, false);
			String rowURL = rowURL = item.getDisplayURL(themeDisplay, false);
		%>
		<li class="card-page-item card-page-item-asset">
			<clay:image-card
				icon="sites"
				stickerIcon="none"
				imageSrc="<%= siteImageURL %>"
				href="<%= rowURL %>"
				title="<%= HtmlUtil.escape(item.getDescriptiveName(locale)) %>"
			/>
		</li>
		<%
		}
		%>
	</ul>
</div>