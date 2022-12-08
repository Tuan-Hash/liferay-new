/**
 * 
 */
package com.hiop.hisc.ternant.web.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thle
 *
 */
public class TenantDisplayContext {

	/**
	 * 
	 */
	public TenantDisplayContext(RenderRequest renderRequest, RenderResponse renderResponse) {
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<Group> getGroups() {
		ThemeDisplay themeDisplay = (ThemeDisplay) _renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		LinkedHashMap<String, Object> groupParams = LinkedHashMapBuilder.<String, Object>put("site", Boolean.TRUE)
				.build();

		groupParams.put("usersGroups", themeDisplay.getUserId());
		groupParams.put("active", Boolean.TRUE);

		return GroupLocalServiceUtil.search(themeDisplay.getCompanyId(), groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
}
