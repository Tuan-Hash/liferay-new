package com.hiop.hisc.servicecatalogs.admin.service;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.dto.MultiData;
import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.common.utils.JsonUtil;
import com.hiop.hisc.workflow.dto.Workflow;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

public class WorkflowAdminService {

	public static Workflow fetchRootWorkflow(
			PortletRequest portletRequest, String workflowId) throws Exception {
		Request request = Request.get(
				HosConfigurationUtil.getServerHost(portletRequest)
						+ "/hitachi/api/v1/workflows/" + workflowId);
		request.setHeader(
				HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(portletRequest));

		Response response = request.execute();
		JsonNode dataNode = JsonUtil.getDataNode(response.returnContent().asString());

		return new ObjectMapper().readValue(dataNode.toString(), Workflow.class);
	}

	public static boolean deleteWorkflow(ActionRequest actionRequest, ActionResponse actionResponse) {
		try {
			String workflowId = ParamUtil.getString(actionRequest, "workflowId");

			Request request = Request.delete(
					HosConfigurationUtil.getTenantUrl(actionRequest) + "/workflows/" + workflowId);
			request.setHeader(
					HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

			Response response = request.execute();
			JsonNode dataNode = JsonUtil.getNode(response.returnContent().asString());

			return !dataNode.get("message").toString().equalsIgnoreCase("");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Workflow cloneWorkflow(
			ActionRequest actionRequest, ActionResponse actionResponse, String workflowId) {
		try {
			Workflow rootWorkflow = fetchRootWorkflow(actionRequest, workflowId);
			return createWorkflow(actionRequest, actionResponse, rootWorkflow);
		} catch (Exception e) {
			return null;
		}
	}

	public static Workflow createWorkflow(
			ActionRequest actionRequest, ActionResponse actionResponse, Workflow rootWorkflow) {
		try {
			Request request = Request.post(
					HosConfigurationUtil.getTenantUrl(actionRequest) + "/workflows");
			request.setHeader(
					HttpHeaders.AUTHORIZATION, HosConfigurationUtil.getAuthToken(actionRequest));

			String email = PortalUtil.getUser(actionRequest).getEmailAddress();

			StringEntity workEntity = buildWorkflowEntity(rootWorkflow, email);
			request.body(workEntity);

			Response response = request.execute();

			List<MultiData<Workflow>> multiWorkflows = new ObjectMapper().readValue(
					response.returnContent().asString(), new TypeReference<List<MultiData<Workflow>>>() {
					});

			return multiWorkflows.get(0).getData();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static StringEntity buildWorkflowEntity(Workflow workflow, String email) {
		JsonNode dataNode = new ObjectMapper().createObjectNode()
				.put("user", email)
				.put("meta_yaml", workflow.getMetaYaml().asText())
				.put("workflow_yaml", workflow.getWorkflowYaml().asText())
				.put("is_public", true);

		return new StringEntity("[" + dataNode.toString() + "]", ContentType.APPLICATION_JSON);
	}

}
