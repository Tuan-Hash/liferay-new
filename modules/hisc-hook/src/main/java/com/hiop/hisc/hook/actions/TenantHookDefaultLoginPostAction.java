package com.hiop.hisc.hook.actions;

import com.hiop.hisc.hook.constants.CustomFieldConstants;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author thle
 */
public class TenantHookDefaultLoginPostAction extends Action {

	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ActionException {

		try {
			doRun(httpServletRequest, httpServletResponse);
		} catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	protected void doRun(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		long companyId = PortalUtil.getCompanyId(httpServletRequest);
		String path = PrefsPropsUtil.getString(companyId, PropsKeys.DEFAULT_LANDING_PAGE_PATH);

		if (_log.isInfoEnabled()) {
			_log.info(PropsKeys.DEFAULT_LANDING_PAGE_PATH + StringPool.EQUAL + path);
		}

		HttpSession httpSession = httpServletRequest.getSession();
		User user = (User) httpSession.getAttribute(WebKeys.USER);
		if (user == null) {
			return;
		}

		String className = User.class.getName();
		String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;
		ExpandoValue expandoObiect = ExpandoValueLocalServiceUtil.getValue(
				companyId, className, tableName, CustomFieldConstants.URL_COL, user.getUserId());
		if (expandoObiect != null) {
			path = expandoObiect.getData();
		}

		if (Validator.isNull(path)) {
			return;
		}

		if (path.contains("${liferay:screenName}") || path.contains("${liferay:userId}")) {
			path = StringUtil.replace(path,
					new String[] { "${liferay:screenName}", "${liferay:userId}" },
					new String[] {
							HtmlUtil.escapeURL(user.getScreenName()), String.valueOf(user.getUserId())
					});
		}

		LastPath lastPath = new LastPath(StringPool.BLANK, path);
		httpSession.setAttribute(WebKeys.LAST_PATH, lastPath);
	}

	private static final Log _log = LogFactoryUtil.getLog(TenantHookDefaultLoginPostAction.class);

}