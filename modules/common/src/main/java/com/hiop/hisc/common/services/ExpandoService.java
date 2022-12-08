package com.hiop.hisc.common.services;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

public class ExpandoService {

	private ExpandoService() {
	}

	public static ExpandoTable addExpandoTable(long companyId, String className, String tableName) {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getDefaultTable(companyId, className);
		} catch (NoSuchTableException e) {
			try {
				expandoTable = ExpandoTableLocalServiceUtil.addTable(companyId, className, tableName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expandoTable;
	}

	public static <T> ExpandoColumn addExpandoColumn(
			long companyId,
			long tableId,
			String className,
			String tableName,
			String columnName,
			int columnType,
			T defaultData) {

		ExpandoColumn expandoColumn = null;

		try {
			expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(
					companyId, className, tableName, columnName);

			if (expandoColumn != null && defaultData != null) {
				expandoColumn = ExpandoColumnLocalServiceUtil.updateColumn(
						expandoColumn.getColumnId(),
						columnName,
						columnType,
						defaultData);
			}

			if (expandoColumn == null) {
				expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
						tableId, columnName, columnType, defaultData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expandoColumn;
	}

	public static ExpandoValue getExpandoValue(
			long companyId,
			String className,
			String tableName,
			String columnName,
			long classPK) {

		return ExpandoValueLocalServiceUtil.getValue(
				companyId,
				className,
				tableName,
				columnName,
				classPK);
	}

	public static ExpandoValue updateExpandoValue(
			long companyId,
			String className,
			String tableName,
			String columnName,
			long classPK,
			String value) throws PortalException {

		ExpandoValue expandoObject = ExpandoValueLocalServiceUtil.getValue(
				companyId,
				className,
				tableName,
				columnName,
				classPK);

		if (expandoObject != null) {
			expandoObject.setData(value);
			ExpandoValueLocalServiceUtil.updateExpandoValue(expandoObject);
		} else {
			ExpandoValueLocalServiceUtil.addValue(
					companyId, className, tableName, columnName, classPK, value);
		}

		return expandoObject;
	}

}
