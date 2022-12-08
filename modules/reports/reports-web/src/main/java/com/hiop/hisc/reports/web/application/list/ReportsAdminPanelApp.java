package com.hiop.hisc.reports.web.application.list;

import com.hiop.hisc.reports.web.constants.ReportsPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author huyth
 */
@Component(immediate = true, property = {
		"panel.app.order:Integer=1",
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_BUILD
}, service = PanelApp.class)
public class ReportsAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ReportsPortletKeys.REPORTS_ADMIN;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Reference(target = "(javax.portlet.name=" + ReportsPortletKeys.REPORTS_ADMIN + ")")
	private Portlet _portlet;

}