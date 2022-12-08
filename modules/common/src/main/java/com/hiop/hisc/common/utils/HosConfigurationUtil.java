package com.hiop.hisc.common.utils;

import javax.portlet.PortletRequest;

import com.hiop.hisc.common.constants.CommonKeys;
import com.hiop.hisc.common.internal.configuration.HosConfiguration;
import com.hiop.hisc.common.services.ExpandoService;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Thang Le
 */
public class HosConfigurationUtil {
  private HosConfigurationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static HosConfiguration getConfiguration(
      PortletRequest portletRequest) throws ConfigurationException {

    return CompanyConfigurationUtil.getConfiguration(
        HosConfiguration.class, portletRequest);
  }

  public static boolean isCreateHosTenantEnabled(PortletRequest portletRequest)
      throws ConfigurationException {
    return getConfiguration(portletRequest).createHosTenantEnabled();
  }

  public static String getServerHost(PortletRequest portletRequest) throws ConfigurationException {
    return getConfiguration(portletRequest).serverHost();
  }

  public static String getAuthToken(PortletRequest portletRequest) throws ConfigurationException {
    return getConfiguration(portletRequest).authToken();
  }

  public static String getTenantUrl(PortletRequest portletRequest) throws Exception {
    ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

    long companyId = themeDisplay.getCompanyId();
    long groupId = themeDisplay.getSiteGroupId();

    String className = Group.class.getName();
    String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;

    ExpandoValue expandoObject = ExpandoService.getExpandoValue(
        companyId,
        className,
        tableName,
        CommonKeys.URL_COL,
        groupId);

    if (expandoObject != null) {
      return expandoObject.getData();
    }

    throw new Exception("HOS's URL not found.");
  }
}
