package com.hiop.hisc.common.utils;

import javax.portlet.PortletRequest;

import com.hiop.hisc.common.internal.configuration.PowerBiConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Thang Le
 */
public class PowerBiConfigurationUtil {
  private PowerBiConfigurationUtil() {
    throw new IllegalStateException("Utility class");
  }

  private static PowerBiConfiguration getConfiguration(
      PortletRequest portletRequest) throws ConfigurationException {

    ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    long companyId = themeDisplay.getCompanyId();

    return ConfigurationProviderUtil.getCompanyConfiguration(
        PowerBiConfiguration.class, companyId);
  }

  public static String getServerHost(PortletRequest portletRequest)
      throws ConfigurationException {
    return getConfiguration(portletRequest).serverHost();
  }

  public static String getScopeBase(PortletRequest portletRequest)
      throws ConfigurationException {
    return getConfiguration(portletRequest).scopeBase();
  }
}
