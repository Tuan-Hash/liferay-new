package com.hiop.hisc.workflow.portlet;

import com.hiop.hisc.workflow.dto.Workflow;
import com.hiop.hisc.workflow.service.WorkflowService;
import com.hiop.hisc.workflow.service.CatalogService;
import com.hiop.hisc.workflow.dto.Catalog;
import com.hiop.hisc.workflow.dto.RenderData;

import com.hiop.hisc.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author huyth
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Workflow",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + WorkflowPortletKeys.WORKFLOW,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class WorkflowPortlet extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(WorkflowPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			RenderData<Workflow> renderData = WorkflowService.fetchWorkflows(renderRequest, 1, 1);
			renderRequest.setAttribute("total", renderData.getCount());
		} catch (Exception ex) {
			log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String workflowId = ParamUtil.getString(renderRequest, "workflowId");
		if (!workflowId.isEmpty()) {
			try {
				Workflow workflow = WorkflowService.fetchWorkflow(renderRequest, workflowId);
				renderRequest.setAttribute("workflow", workflow);
				RenderData<Catalog> catalogs = CatalogService.fetchCatalogs(renderRequest, workflowId);
				renderRequest.setAttribute("catalogs", catalogs);
			} catch (Exception ex) {
				log.error(ex);
				SessionErrors.add(renderRequest, "error500");
			}
		}
		super.render(renderRequest, renderResponse);
	}
}