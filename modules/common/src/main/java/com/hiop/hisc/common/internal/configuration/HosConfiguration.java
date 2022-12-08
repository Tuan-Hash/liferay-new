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
	id = "com.hiop.hisc.common.internal.configuration.HosConfiguration",
	localization = "content/Language", name = "hos-configuration-name"
)
public interface HosConfiguration {
  @Meta.AD(
		deflt = "false", name = "create-hos-tenant-enabled",
		description = "create-hos-tenant-enabled-help", required = false
	)
	public boolean createHosTenantEnabled();

  @Meta.AD(deflt = "http://127.0.0.1", name = "server-host", required = false)
	public String serverHost();

  @Meta.AD(deflt = "Basic c3lzYWRtaW46c3lzYWRtaW4=", name = "auth-token", required = false)
	public String authToken();
}
