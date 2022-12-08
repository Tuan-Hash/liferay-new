package com.hiop.hisc.taglib.ui;

import com.hiop.hisc.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author huyth
 */
public class InputPermissionsTag extends IncludeTag {

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		setServletContext(ServletContextUtil.getServletContext());
	}

	public String getFormName() {
		return _formName;
	}

	public String getModelName() {
		return _modelName;
	}

	public String getModelLabel() {
		return _modelLabel;
	}

	public String getEntryId() {
		return _entryId;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	public void setModelLabel(String modelLabel) {
		_modelLabel = modelLabel;
	}

	public void setEntryId(String entryId) {
		_entryId = entryId;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:formName", _formName);
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:groupDefaultActions",
				ResourceActionsUtil.getModelResourceGroupDefaultActions(_modelName));
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:guestDefaultActions",
				ResourceActionsUtil.getModelResourceGuestDefaultActions(_modelName));
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:guestUnsupportedActions",
				ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
						_modelName));
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:modelName", _modelName);
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:entryId", _entryId);
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:modelLabel", _modelLabel);
		httpServletRequest.setAttribute(
				"hisc-ui:input-permissions:supportedActions",
				ResourceActionsUtil.getModelResourceActions(_modelName));
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/input_permissions/page.jsp";

	private String _formName = "fm";
	private String _modelName;
	private String _entryId;
	private String _modelLabel;

}