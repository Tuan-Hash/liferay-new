package com.hiop.hisc.taglib.internal.servlet;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author huyth
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

  public static ServletContext getServletContext() {
    return _servletContext;
  }

  @Reference(target = "(osgi.web.symbolicname=com.hiop.hisc.taglib)", unbind = "-")
  protected void setServletContext(ServletContext servletContext) {
    _servletContext = servletContext;
  }

  private static ServletContext _servletContext;
}