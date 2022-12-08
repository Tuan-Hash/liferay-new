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
	id = "com.hiop.hisc.common.internal.configuration.AzureAdConfiguration",
	localization = "content/Language", name = "azure-ad-configuration-name"
)
public interface AzureAdConfiguration {

  @Meta.AD(deflt = "https://login.microsoftonline.com", name = "auth-endpoint", required = false)
	public String authEndpoint();

  @Meta.AD(name = "tenant-id", required = false)
	public String tenantId();

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(name = "client-secret", required = false)
	public String clientSecret();

	@Meta.AD(deflt = "https://graph.microsoft.com/beta", name = "graphql-endpoint", required = false)
	public String graphqlEndpoint();
}
