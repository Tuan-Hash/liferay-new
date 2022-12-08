package com.hiop.hisc.common.internal.settings.definition;

import org.osgi.service.component.annotations.Component;

import com.hiop.hisc.common.internal.configuration.PowerBiConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

/**
 * @author Thang Le
 */
@Component(service = ConfigurationBeanDeclaration.class)
public class PowerBiConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {

  @Override
  public Class<?> getConfigurationBeanClass() {
    return PowerBiConfiguration.class;
  }

}