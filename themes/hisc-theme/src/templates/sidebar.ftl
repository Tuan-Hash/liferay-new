<#if is_setup_complete>
	<a class="${logo_css_class} align-items-center d-md-inline-flex d-sm-none d-none logo-md" href="${site_default_url}" title="<@liferay.language_format arguments="" key="go-to-x" />">
		<img alt="${logo_description}" height="52" width="52" src="${site_logo}" />
	</a>
	<div class="hisc-sidebar__nav">
		<#if has_navigation>
			<@site_navigation_menu_main default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
		</#if>
	</div>
</#if>