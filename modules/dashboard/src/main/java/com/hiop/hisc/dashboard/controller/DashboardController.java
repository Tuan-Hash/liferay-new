package com.hiop.hisc.dashboard.controller;

import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;

/**
 * @author nphong
 */
@Controller
@RequestMapping("VIEW")
public class DashboardController {

	@Autowired
	private MessageSource messageSource;

	@RenderMapping
	public String prepareView(ModelMap modelMap, PortletRequest portletRequest, PortletPreferences portletPreferences,
			Locale locale) {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String siteGroupName;

		try {
			siteGroupName = themeDisplay.getSiteGroupName();
		} catch (PortalException e) {
			siteGroupName = themeDisplay.getCompany().getName();
			e.printStackTrace();
		}

		modelMap.addAttribute("siteGroupName", siteGroupName);

		User user = themeDisplay.getUser();

		if (!user.getFirstName().isEmpty()) {
			modelMap.addAttribute("firstName", user.getFirstName());
		} else {
			modelMap.addAttribute("firstName", messageSource.getMessage("dashboard.guest", null, locale));
		}

		String companyLogo = themeDisplay.getCompanyLogo();
		modelMap.addAttribute("companyLogo", companyLogo);

		modelMap.addAttribute(
				"iframeSrc",
				portletPreferences.getValue("iframeSrc", null));

		return "dashboard";
	}

	private static final Logger _logger = LoggerFactory.getLogger(
			DashboardController.class);

}