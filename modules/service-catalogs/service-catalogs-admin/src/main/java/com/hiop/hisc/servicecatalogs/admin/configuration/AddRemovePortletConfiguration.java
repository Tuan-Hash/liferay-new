package com.hiop.hisc.servicecatalogs.admin.configuration;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.hiop.hisc.servicecatalogs.admin.constants.ServiceCatalogAdminPortletKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

@Component(immediate = true, property = {
		"javax.portlet.name=" + ServiceCatalogAdminPortletKeys.SERVICE_CATALOG_ADMIN,
		"path=-"
}, service = PortletConfigurationIcon.class)
public class AddRemovePortletConfiguration extends BasePortletConfigurationIcon {

	@Override
	public String getId() {
		return "addRemovePortletConfiguration";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(
				getResourceBundle(getLocale(portletRequest)),
				"serviceCatalog.addOrRemove");
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

		return new AggregateResourceBundle(
				resourceBundle, super.getResourceBundle(locale));
	}

	@Override
	public String getURL(PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		return PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
						portletRequest,
						themeDisplay.getPortletDisplay().getId(),
						themeDisplay.getPlid(),
						"0"))
				.setMVCPath("/view_root_categories.jsp")
				.setWindowState(LiferayWindowState.POP_UP)
				.buildPortletURL()
				.toString();
	}

	@Override
	public double getWeight() {
		return 101;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	@Reference
	private Language _language;
}
