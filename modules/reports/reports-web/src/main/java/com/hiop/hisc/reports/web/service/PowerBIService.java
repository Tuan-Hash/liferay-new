package com.hiop.hisc.reports.web.service;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hiop.hisc.common.internal.configuration.PowerBiConfiguration;
import com.hiop.hisc.common.utils.AzureAdUtil;
import com.hiop.hisc.common.utils.CompanyConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.reports.web.dto.EmbedConfig;
import com.hiop.hisc.reports.web.dto.EmbedToken;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * Service with helper methods to get report's details and multi-resource
 * embed
 * token
 */
public class PowerBIService {

	private String accessToken;
	private String powerBiURL;

	// Prevent instantiation
	public PowerBIService(PortletRequest portletRequest)
			throws ConfigurationException, MalformedURLException, InterruptedException, ExecutionException {

		this.accessToken = "Bearer " + AzureAdUtil.getPowerBiAccessToken(portletRequest);
		this.powerBiURL = CompanyConfigurationUtil
				.getConfiguration(PowerBiConfiguration.class, portletRequest)
				.serverHost();
	}

	/**
	 * Get embed params for a report for a single workspace
	 *
	 * @param {string} accessToken
	 * @param {string} groupId
	 * @param {string} reportId
	 * @param {string} additionalDatasetIds
	 * @return EmbedConfig object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public EmbedConfig getEmbedConfig(String groupId, String reportId)
			throws JsonProcessingException {

		try {
			if (reportId == null || reportId.isEmpty()) {
				throw new RuntimeException("Empty Report Id");
			}

			if (groupId == null || groupId.isEmpty()) {
				throw new RuntimeException("Empty Group Id");
			}

			if (!checkIsExistingGroup(groupId)) {
				throw new RuntimeException("Group is not Existed");
			}

			// Get Report In Group API:
			// https://api.powerbi.com/v1.0/myorg/groups/{groupId}/reports/{reportId}
			StringBuilder urlStringBuilder = new StringBuilder(powerBiURL + "/groups/");
			urlStringBuilder.append(groupId);
			urlStringBuilder.append("/reports/");
			urlStringBuilder.append(reportId);

			// Request header
			Request request = Request.get(urlStringBuilder.toString());
			request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

			Response response = request.execute();

			// Parse string into report object and get Report details
			JsonNode dataNode = JsonUtil.getNode(response.returnContent().toString());

			// Read EmbedConfig data
			EmbedConfig embedConfig = new ObjectMapper().readValue(dataNode.toString(), EmbedConfig.class);

			// Get embed token
			EmbedToken embedToken = getEmbedToken(reportId, embedConfig.getDatasetId());
			embedConfig.setEmbedToken(embedToken);

			return embedConfig;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get Embed token for single report, multiple datasetIds, and optional target
	 * workspaces
	 *
	 * @see <a href="https://aka.ms/MultiResourceEmbedToken">Multi-Resource Embed
	 *      Token</a>
	 * @param {string} accessToken
	 * @param {string} reportId
	 * @param {string} datasetId
	 * @return EmbedToken
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public EmbedToken getEmbedToken(String reportId, String datasetId)
			throws JsonProcessingException {

		try {
			Request request = Request.post(powerBiURL + "/GenerateToken");
			request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

			ArrayNode datasetIds = new ObjectMapper().createArrayNode();
			datasetIds.add(new ObjectMapper().createObjectNode().put("id", datasetId));

			ArrayNode reportIds = new ObjectMapper().createArrayNode();
			reportIds.add(new ObjectMapper().createObjectNode().put("id", reportId));

			ObjectNode dataNode = new ObjectMapper().createObjectNode();
			dataNode.set("datasets", datasetIds);
			dataNode.set("reports", reportIds);

			StringEntity stringEntity = new StringEntity(dataNode.toString(), ContentType.APPLICATION_JSON);
			request.body(stringEntity);

			Response response = request.execute();

			// Convert responseBody string into EmbedToken class object
			EmbedToken embedToken = new ObjectMapper().readValue(
					response.returnContent().asString(), EmbedToken.class);

			return embedToken;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean checkIsExistingGroup(String groupId)
			throws JsonProcessingException {

		try {
			URIBuilder uriBuilder = new URIBuilder(powerBiURL + "/groups");
			uriBuilder.setParameter("$filter", "contains(id,'" + groupId + "')");

			Request request = Request.get(uriBuilder.toString());
			request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

			Response response = request.execute();

			JsonNode dataNode = JsonUtil.getNode(response.returnContent().toString());

			return ((ArrayNode) dataNode.get("value")).size() > 0;
		} catch (Exception e) {
			return false;
		}
	}
}
