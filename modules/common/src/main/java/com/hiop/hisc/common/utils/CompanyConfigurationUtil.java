package com.hiop.hisc.common.utils;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

public class CompanyConfigurationUtil {
  private CompanyConfigurationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> T getConfiguration(
      Class<T> clazz, PortletRequest portletRequest) throws ConfigurationException {

    ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    long companyId = themeDisplay.getCompanyId();

    return ConfigurationProviderUtil.getCompanyConfiguration(
        clazz, companyId);
  }

  public static <T> T getConfiguration(
      Class<T> clazz, long companyId) throws ConfigurationException {

    return ConfigurationProviderUtil.getCompanyConfiguration(
        clazz, companyId);
  }
}
