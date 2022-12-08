package com.hiop.hisc.servicerequest.portlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.hiop.hisc.common.utils.HosConfigurationUtil;
import com.hiop.hisc.servicerequest.constants.ServiceRequestPortletKeys;
import com.hiop.hisc.servicerequest.dto.Catalog;
import com.hiop.hisc.servicerequest.dto.Execution;
import com.hiop.hisc.servicerequest.dto.RenderData;
import com.hiop.hisc.servicerequest.dto.ServiceRequest;
import com.hiop.hisc.servicerequest.service.CatalogService;
import com.hiop.hisc.servicerequest.service.ExecutionService;
import com.hiop.hisc.servicerequest.service.RequestService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author phongnh
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=hisc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/reactapp.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Service Request",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ServiceRequestPortletKeys.SERVICE_REQUEST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class ServiceRequestPortlet extends MVCPortlet {
	private static Log _log = LogFactoryUtil.getLog(ServiceRequestPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		int page = ParamUtil.getInteger(renderRequest, "page");

		if (page == 0) {
			page = 1;
		}

		try {
			RenderData<ServiceRequest> renderData = RequestService.fetchRequests(renderRequest, page, 1);
			renderRequest.setAttribute("total", renderData.getCount());
		} catch (Exception ex) {
			_log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		int page = ParamUtil.getInteger(renderRequest, "page");

		if (page == 0) {
			page = 1;
		}

		try {
			String requestId = ParamUtil.getString(renderRequest, "requestId");
			String catalogId = ParamUtil.getString(renderRequest, "catalogId");

			if (requestId != null && !requestId.isEmpty() && catalogId != null && !catalogId.isEmpty()) {
				String tenantUrl = HosConfigurationUtil.getTenantUrl(renderRequest);
				ServiceRequest serviceRequest = RequestService.fetchRequest(renderRequest, requestId);
				Catalog catalog = CatalogService.fetchCatalog(renderRequest, catalogId);
				Execution execution = ExecutionService.fetchExecution(
						renderRequest, serviceRequest.getExecutionId());

				serviceRequest.setCatalog(catalog);
				serviceRequest.setRelatedExecution(execution);

				renderRequest.setAttribute("serviceRequest", serviceRequest);
				renderRequest.setAttribute("execution", execution);
				renderRequest.setAttribute("tenantUrl", tenantUrl);
				renderRequest.setAttribute("closeURL", ParamUtil.getString(renderRequest, "previousUrl", "/"));
			}
		} catch (Exception ex) {
			_log.error(ex);
			SessionErrors.add(renderRequest, "error500");
		}

		super.render(renderRequest, renderResponse);
	}
}