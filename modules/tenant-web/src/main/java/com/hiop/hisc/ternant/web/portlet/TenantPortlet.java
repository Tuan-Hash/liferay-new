package com.hiop.hisc.ternant.web.portlet;

import com.hiop.hisc.ternant.web.constants.TenantPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author thle
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Tenant",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TenantPortletKeys.TENANT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TenantPortlet extends MVCPortlet {
}