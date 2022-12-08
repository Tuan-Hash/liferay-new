package com.hiop.hisc.reports.web.display.context;

import javax.servlet.http.HttpServletRequest;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

public class ReportsCategoriesDisplayContext {

  public ReportsCategoriesDisplayContext(
      HttpServletRequest httpServletRequest, LiferayPortletResponse liferayPortletResponse) {
    this.httpServletRequest = httpServletRequest;
    this.liferayPortletResponse = liferayPortletResponse;
  }

  public CreationMenu getCreationMenu() {

    return CreationMenuBuilder.addDropdownItem(
        dropdownItem -> {
          dropdownItem.setHref(
              liferayPortletResponse.createRenderURL(),
              "mvcPath", "/admin/edit_category.jsp");
          dropdownItem.setLabel(
              LanguageUtil.get(httpServletRequest, "category.add"));
        }).build();
  }

  private final HttpServletRequest httpServletRequest;
  private final LiferayPortletResponse liferayPortletResponse;
}
