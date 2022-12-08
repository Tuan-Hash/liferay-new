package com.hiop.hisc.common.internal.configuration.category;

import org.osgi.service.component.annotations.Component;

import com.liferay.configuration.admin.category.ConfigurationCategory;

/**
 * @author Thang Le
 */
@Component(service = ConfigurationCategory.class)
public class HiscConfigurationCategory implements ConfigurationCategory {

  @Override
  public String getCategoryIcon() {
    return "cog";
  }

  @Override
  public String getCategoryKey() {
    return "hisc";
  }

  @Override
  public String getCategorySection() {
    return "platform";
  }

}