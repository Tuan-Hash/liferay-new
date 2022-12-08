package com.hiop.hisc.servicecatalogs.admin.application.list;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.hiop.hisc.servicecatalogs.admin.constants.ServiceCatalogAdminPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

/**
 * @author phongnh
 */
@Component(immediate = true, property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		"service.ranking:Integer=100"
}, service = PanelApp.class)
public class ServiceCatalogAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ServiceCatalogAdminPortletKeys.SERVICE_CATALOG_ADMIN;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Reference(target = "(javax.portlet.name=" + ServiceCatalogAdminPortletKeys.SERVICE_CATALOG_ADMIN + ")")
	private Portlet _portlet;

}