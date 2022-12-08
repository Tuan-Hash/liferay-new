<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${html_title}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<div class="d-flex flex-column min-vh-100">
	<@liferay.control_menu />

	<div class="d-flex flex-column flex-fill position-relative" id="wrapper">
		<#if show_header>
			<header id="hisc-topbar">
				<div class="navbar navbar-classic navbar-top py-3">
					<div class="container-fluid user-personal-bar">
						<div class="align-items-center autofit-row">
							<#assign preferences = freeMarkerPortletPreferences.getPreferences({"portletSetupPortletDecoratorId": "barebone", "destination": "/search"}) />

							<div class="autofit-col autofit-col-expand">
								<#if show_header_search>
									<div class="justify-content-md-end mr-4 navbar-form" role="search">
										<@liferay.search_bar default_preferences="${preferences}" />
									</div>
								</#if>
							</div>

							<div class="autofit-col c-mr-sm-3">
								<#if themeDisplay.getSiteGroupId() != 20121>
									<a class="btn btn-secondary" href="/">
										<span class="inline-item inline-item-before">
											<svg class="lexicon-icon lexicon-icon-blogs" focusable="false" role="presentation">
												<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#sites" />
											</svg>
										</span>
										${languageUtil.get(locale, "Select your tenant")}
									</a>
								</#if>
							</div>

							<div class="autofit-col">
								<@liferay.user_personal_bar />
							</div>
						</div>
					</div>
				</div>
			</header>
		</#if>

		<div id="hisc-sidebar">
			<#include "${full_templates_path}/sidebar.ftl" />
		</div>

		<section class="${portal_content_css_class} flex-fill" id="content">
			<h2 class="sr-only" role="heading" aria-level="1">${the_title}</h2>

			<#if selectable>
				<@liferay_util["include"] page=content_include />
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				<@liferay_theme["wrap-portlet"] page="portlet.ftl">
					<@liferay_util["include"] page=content_include />
				</@>
			</#if>
		</section>

		<#if show_footer>
			<footer id="footer" role="contentinfo">
				<div class="container">
					<div class="row">
						<div class="col-md-12 text-center text-md-left">
							<@liferay.language_format
								arguments='<a href="https://www.hitachi.com/" rel="external">Hitachi, Ltd.</a>'
								key="powered-by-x"
							/>
						</div>
					</div>
				</div>
			</footer>
		</#if>
	</div>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

</body>

</html>