/**
 * Copyright (c) 2000-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiop.hisc.dashboard.controller;

import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.WindowState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hiop.hisc.dashboard.dto.Preferences;
import com.liferay.portletmvc4spring.bind.annotation.ActionMapping;
import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;

/**
 * @author Neil Griffin
 */
@Controller
@RequestMapping("EDIT")
public class PreferencesController {

	private static final Logger logger = LoggerFactory.getLogger(PreferencesController.class);

	@Autowired
	private LocalValidatorFactoryBean localValidatorFactoryBean;

	@Autowired
	private MessageSource messageSource;

	@RenderMapping
	public String prepareView(MimeResponse mimeResponse, Model model, PortletPreferences portletPreferences) {

		model.addAttribute("preferences", new Preferences(portletPreferences.getValue("iframeSrc", null)));

		// Thymeleaf
		model.addAttribute("mainFormActionURL", mimeResponse.createActionURL());

		return "preferences";
	}

	@ActionMapping(params = { "action=reset" })
	public void resetPreferences(PortletPreferences portletPreferences, ModelMap modelMap, Locale locale,
			ActionResponse actionResponse) {

		try {
			Enumeration<String> preferenceNames = portletPreferences.getNames();

			while (preferenceNames.hasMoreElements()) {
				String preferenceName = preferenceNames.nextElement();

				if (!portletPreferences.isReadOnly(preferenceName)) {
					portletPreferences.reset(preferenceName);
				}
			}

			portletPreferences.store();
			actionResponse.setPortletMode(PortletMode.VIEW);
			actionResponse.setWindowState(WindowState.NORMAL);
			modelMap.addAttribute("globalInfoMessage",
					messageSource.getMessage("dashboard.your-request-processed-successfully", null, locale));
		} catch (Exception e) {
			modelMap.addAttribute("globalErrorMessage",
					messageSource.getMessage("dashboard.an-unexpected-error-occurred", null, locale));
			logger.error(e.getMessage(), e);
		}
	}

	@ActionMapping(params = { "action=submit" })
	public void submitPreferences(@ModelAttribute("preferences") Preferences preferences, BindingResult bindingResult,
			ModelMap modelMap, Locale locale, PortletPreferences portletPreferences, ActionResponse actionResponse) {

		localValidatorFactoryBean.validate(preferences, bindingResult);

		if (!bindingResult.hasErrors()) {

			try {
				portletPreferences.setValue("iframeSrc", preferences.getIframeSrc());
				portletPreferences.store();
				actionResponse.setPortletMode(PortletMode.VIEW);
				actionResponse.setWindowState(WindowState.NORMAL);
				modelMap.addAttribute("globalInfoMessage",
						messageSource.getMessage("dashboard.your-request-processed-successfully", null, locale));
			} catch (Exception e) {
				modelMap.addAttribute("globalErrorMessage",
						messageSource.getMessage("dashboard.an-unexpected-error-occurred", null, locale));
				logger.error(e.getMessage(), e);
			}
		}
	}
}
