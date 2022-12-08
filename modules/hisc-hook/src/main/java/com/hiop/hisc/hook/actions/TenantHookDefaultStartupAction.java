package com.hiop.hisc.hook.actions;

import com.hiop.hisc.common.services.ExpandoService;
import com.hiop.hisc.hook.constants.CustomFieldConstants;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

/**
 * @author thle
 */
public class TenantHookDefaultStartupAction extends SimpleAction {

	@Override
	public void run(String[] lifecycleEventIds) throws ActionException {
		long companyId = CompanyThreadLocal.getCompanyId(); // or use ids[0]
		// CUSTOM_FIELDS . Here you can give any name by
		// Default is CUSTOM_FIELDS
		String tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;

		// Add Default tenant url for user
		String columnName = CustomFieldConstants.URL_COL;
		String className = User.class.getName(); // com.liferay.portal.model.User
		ExpandoTable expandoTable = ExpandoService.addExpandoTable(companyId, className, tableName);

		if (expandoTable != null) {
			ExpandoService.addExpandoColumn(
					companyId, expandoTable.getTableId(), className, tableName, columnName,
					ExpandoColumnConstants.STRING, "");
		}

		// Add HOS url for site
		columnName = CustomFieldConstants.HOS_URL_COL;
		className = Group.class.getName(); // com.liferay.portal.model.Group
		expandoTable = ExpandoService.addExpandoTable(companyId, className, tableName);

		if (expandoTable != null) {
			ExpandoService.addExpandoColumn(
					companyId, expandoTable.getTableId(), className, tableName, columnName,
					ExpandoColumnConstants.STRING, "");
		}

		String[] defaultStringArr = {};
		// Add verified tenant roles for site
		columnName = CustomFieldConstants.ROLE_COL;
		className = Role.class.getName(); // com.liferay.portal.model.Group
		expandoTable = ExpandoService.addExpandoTable(companyId, className, tableName);

		if (expandoTable != null) {
			ExpandoService.addExpandoColumn(
					companyId, expandoTable.getTableId(), className, tableName, columnName,
					ExpandoColumnConstants.STRING_ARRAY, defaultStringArr);
		}

		// Add verified tenant groups for site
		columnName = CustomFieldConstants.USER_GROUP_COL;
		className = UserGroup.class.getName(); // com.liferay.portal.model.Group
		expandoTable = ExpandoService.addExpandoTable(companyId, className, tableName);

		if (expandoTable != null) {
			ExpandoService.addExpandoColumn(
					companyId, expandoTable.getTableId(), className, tableName, columnName,
					ExpandoColumnConstants.STRING_ARRAY, defaultStringArr);
		}
	}

}