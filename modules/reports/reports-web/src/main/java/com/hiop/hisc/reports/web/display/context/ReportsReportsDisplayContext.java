package com.hiop.hisc.reports.web.display.context;

import javax.servlet.http.HttpServletRequest;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

public class ReportsReportsDisplayContext {

  public ReportsReportsDisplayContext(
      HttpServletRequest httpServletRequest, LiferayPortletResponse liferayPortletResponse,
      long categoryId) {
    this.httpServletRequest = httpServletRequest;
    this.liferayPortletResponse = liferayPortletResponse;
    this.categoryId = categoryId;
  }

  public CreationMenu getCreationMenu() {

    return CreationMenuBuilder.addDropdownItem(
        dropdownItem -> {
          dropdownItem.setHref(
              liferayPortletResponse.createRenderURL(),
              "mvcPath", "/admin/edit_report.jsp",
              "categoryId", categoryId);
          dropdownItem.setLabel(
              LanguageUtil.get(httpServletRequest, "report.add"));
        }).build();
  }

  private final HttpServletRequest httpServletRequest;
  private final LiferayPortletResponse liferayPortletResponse;

  private final long categoryId;
}
