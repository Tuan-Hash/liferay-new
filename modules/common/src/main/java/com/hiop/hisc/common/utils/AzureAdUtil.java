package com.hiop.hisc.common.utils;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import javax.portlet.PortletRequest;

import com.hiop.hisc.common.internal.configuration.AzureAdConfiguration;
import com.hiop.hisc.common.internal.configuration.PowerBiConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

public final class AzureAdUtil {
	private AzureAdUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Acquires access token
	 * 
	 * @param portletRequest
	 * @return AccessToken
	 * @throws ConfigurationException
	 * @throws MalformedURLException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static String getAccessTokenByServicePrincipal(
			PortletRequest portletRequest, String scope)
			throws ConfigurationException, MalformedURLException, InterruptedException, ExecutionException {

		// Get configurations
		AzureAdConfiguration azureAdConfiguration = CompanyConfigurationUtil.getConfiguration(
				AzureAdConfiguration.class, portletRequest);

		// Build Confidential Client App
		ConfidentialClientApplication app = ConfidentialClientApplication
				.builder(azureAdConfiguration.clientId(),
						ClientCredentialFactory.createFromSecret(azureAdConfiguration.clientSecret()))
				.authority(azureAdConfiguration.authEndpoint() + "/" + azureAdConfiguration.tenantId()).build();

		ClientCredentialParameters clientCredentialParameters = ClientCredentialParameters
				.builder(Collections.singleton(scope)).build();

		// Acquire new AAD token
		IAuthenticationResult result = app.acquireToken(clientCredentialParameters).get();

		// Return access token if token is acquired successfully
		if (result != null && result.accessToken() != null && !result.accessToken().isEmpty()) {
			return result.accessToken();
		}

		return null;
	}

	public static String getPowerBiAccessToken(PortletRequest portletRequest)
			throws ConfigurationException, MalformedURLException, InterruptedException, ExecutionException {
		PowerBiConfiguration powerBiConfiguration = CompanyConfigurationUtil.getConfiguration(
				PowerBiConfiguration.class, portletRequest);

		return getAccessTokenByServicePrincipal(portletRequest, powerBiConfiguration.scopeBase());
	}
}