package com.hiop.hisc.hook.services;

import java.util.List;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.net.URIBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.internal.configuration.AzureAdConfiguration;
import com.hiop.hisc.common.utils.CompanyConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.common.utils.JwtUtil;
import com.hiop.hisc.hook.dto.azure.AzureGroup;
import com.hiop.hisc.hook.dto.azure.AzureRole;

public class GraphService {

  private GraphService() {
  }

  public static List<AzureRole> fetchRoles(long companyId, String userId, String accessToken) {
    List<AzureRole> azureRoles = null;

    try {
      AzureAdConfiguration azureAdConfiguration = CompanyConfigurationUtil.getConfiguration(
          AzureAdConfiguration.class, companyId);

      URIBuilder uriBuilder = new URIBuilder(
          azureAdConfiguration.graphqlEndpoint() + "/users/" + userId + "/appRoleAssignments");
      uriBuilder.setParameter("$count", "true");
      uriBuilder.setParameter("$select", "id,resourceDisplayName");

      Request request = Request.get(uriBuilder.toString());
      request.setHeader(HttpHeaders.AUTHORIZATION, JwtUtil.setHeaderAuthorization(accessToken));

      Response response = request.execute();
      JsonNode dataNode = JsonUtil.getNode(response.returnContent().asString());

      azureRoles = new ObjectMapper().readValue(
          JsonUtil.getValueString(dataNode), new TypeReference<List<AzureRole>>() {
          });
    } catch (Exception e) {
      e.printStackTrace();
    }

    return azureRoles;
  }

  public static List<AzureGroup> fetchGroups(long companyId, String userId, String accessToken) {
    List<AzureGroup> azureGroups = null;

    try {
      AzureAdConfiguration azureAdConfiguration = CompanyConfigurationUtil.getConfiguration(
          AzureAdConfiguration.class, companyId);

      URIBuilder uriBuilder = new URIBuilder(
          azureAdConfiguration.graphqlEndpoint()
              + "/users/" + userId + "/memberOf/microsoft.graph.group");
      uriBuilder.setParameter("$count", "true");
      uriBuilder.setParameter("$select", "id,displayName");

      Request request = Request.get(uriBuilder.toString());
      request.setHeader(HttpHeaders.AUTHORIZATION,
          JwtUtil.setHeaderAuthorization(accessToken));

      Response response = request.execute();
      JsonNode dataNode = JsonUtil.getNode(response.returnContent().asString());

      azureGroups = new ObjectMapper().readValue(
          JsonUtil.getValueString(dataNode), new TypeReference<List<AzureGroup>>() {
          });
    } catch (Exception e) {
      e.printStackTrace();
    }

    return azureGroups;
  }

}
