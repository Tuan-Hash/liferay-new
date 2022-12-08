package com.hiop.hisc.common.internal.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Thang Le
 */
@ExtendedObjectClassDefinition(
	category = "hisc", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.hiop.hisc.common.internal.configuration.PowerBiConfiguration",
	localization = "content/Language", name = "power-bi-configuration-name"
)
public interface PowerBiConfiguration {

  @Meta.AD(deflt = "https://api.powerbi.com/v1.0/myorg", name = "server-host", required = false)
	public String serverHost();

  @Meta.AD(
		deflt = "https://analysis.windows.net/powerbi/api/.default", 
		name = "scope-base", required = false
	)
	public String scopeBase();
}
