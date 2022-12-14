/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.hiop.hisc.servicecatalogs.model.impl;

import com.hiop.hisc.servicecatalogs.model.Catalog;
import com.hiop.hisc.servicecatalogs.model.CatalogModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the Catalog service. Represents a row in the &quot;hisc_servicecatalog_Catalog&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CatalogModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CatalogImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CatalogImpl
 * @generated
 */
@JSON(strict = true)
public class CatalogModelImpl
	extends BaseModelImpl<Catalog> implements CatalogModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a catalog model instance should use the <code>Catalog</code> interface instead.
	 */
	public static final String TABLE_NAME = "hisc_servicecatalog_Catalog";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"catalogId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"categoryId", Types.VARCHAR}, {"name", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("catalogId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("categoryId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table hisc_servicecatalog_Catalog (uuid_ VARCHAR(75) null,catalogId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId VARCHAR(75) null,name VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
		"drop table hisc_servicecatalog_Catalog";

	public static final String ORDER_BY_JPQL = " ORDER BY catalog.name ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY hisc_servicecatalog_Catalog.name ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long NAME_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public CatalogModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _catalogId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCatalogId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _catalogId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Catalog.class;
	}

	@Override
	public String getModelClassName() {
		return Catalog.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Catalog, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Catalog, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Catalog, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Catalog)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Catalog, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Catalog, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Catalog)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Catalog, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Catalog, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<Catalog, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Catalog, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Catalog, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Catalog, Object>>();
		Map<String, BiConsumer<Catalog, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Catalog, ?>>();

		attributeGetterFunctions.put("uuid", Catalog::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<Catalog, String>)Catalog::setUuid);
		attributeGetterFunctions.put("catalogId", Catalog::getCatalogId);
		attributeSetterBiConsumers.put(
			"catalogId", (BiConsumer<Catalog, Long>)Catalog::setCatalogId);
		attributeGetterFunctions.put("groupId", Catalog::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<Catalog, Long>)Catalog::setGroupId);
		attributeGetterFunctions.put("companyId", Catalog::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Catalog, Long>)Catalog::setCompanyId);
		attributeGetterFunctions.put("userId", Catalog::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Catalog, Long>)Catalog::setUserId);
		attributeGetterFunctions.put("userName", Catalog::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<Catalog, String>)Catalog::setUserName);
		attributeGetterFunctions.put("createDate", Catalog::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<Catalog, Date>)Catalog::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", Catalog::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Catalog, Date>)Catalog::setModifiedDate);
		attributeGetterFunctions.put("categoryId", Catalog::getCategoryId);
		attributeSetterBiConsumers.put(
			"categoryId", (BiConsumer<Catalog, String>)Catalog::setCategoryId);
		attributeGetterFunctions.put("name", Catalog::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<Catalog, String>)Catalog::setName);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@JSON
	@Override
	public long getCatalogId() {
		return _catalogId;
	}

	@Override
	public void setCatalogId(long catalogId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_catalogId = catalogId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getCategoryId() {
		if (_categoryId == null) {
			return "";
		}
		else {
			return _categoryId;
		}
	}

	@Override
	public void setCategoryId(String categoryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_categoryId = categoryId;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalName() {
		return getColumnOriginalValue("name");
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(Catalog.class.getName()));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Catalog.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Catalog toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Catalog>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CatalogImpl catalogImpl = new CatalogImpl();

		catalogImpl.setUuid(getUuid());
		catalogImpl.setCatalogId(getCatalogId());
		catalogImpl.setGroupId(getGroupId());
		catalogImpl.setCompanyId(getCompanyId());
		catalogImpl.setUserId(getUserId());
		catalogImpl.setUserName(getUserName());
		catalogImpl.setCreateDate(getCreateDate());
		catalogImpl.setModifiedDate(getModifiedDate());
		catalogImpl.setCategoryId(getCategoryId());
		catalogImpl.setName(getName());

		catalogImpl.resetOriginalValues();

		return catalogImpl;
	}

	@Override
	public Catalog cloneWithOriginalValues() {
		CatalogImpl catalogImpl = new CatalogImpl();

		catalogImpl.setUuid(this.<String>getColumnOriginalValue("uuid_"));
		catalogImpl.setCatalogId(
			this.<Long>getColumnOriginalValue("catalogId"));
		catalogImpl.setGroupId(this.<Long>getColumnOriginalValue("groupId"));
		catalogImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		catalogImpl.setUserId(this.<Long>getColumnOriginalValue("userId"));
		catalogImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		catalogImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		catalogImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		catalogImpl.setCategoryId(
			this.<String>getColumnOriginalValue("categoryId"));
		catalogImpl.setName(this.<String>getColumnOriginalValue("name"));

		return catalogImpl;
	}

	@Override
	public int compareTo(Catalog catalog) {
		int value = 0;

		value = getName().compareTo(catalog.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Catalog)) {
			return false;
		}

		Catalog catalog = (Catalog)object;

		long primaryKey = catalog.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<Catalog> toCacheModel() {
		CatalogCacheModel catalogCacheModel = new CatalogCacheModel();

		catalogCacheModel.uuid = getUuid();

		String uuid = catalogCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			catalogCacheModel.uuid = null;
		}

		catalogCacheModel.catalogId = getCatalogId();

		catalogCacheModel.groupId = getGroupId();

		catalogCacheModel.companyId = getCompanyId();

		catalogCacheModel.userId = getUserId();

		catalogCacheModel.userName = getUserName();

		String userName = catalogCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			catalogCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			catalogCacheModel.createDate = createDate.getTime();
		}
		else {
			catalogCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			catalogCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			catalogCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		catalogCacheModel.categoryId = getCategoryId();

		String categoryId = catalogCacheModel.categoryId;

		if ((categoryId != null) && (categoryId.length() == 0)) {
			catalogCacheModel.categoryId = null;
		}

		catalogCacheModel.name = getName();

		String name = catalogCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			catalogCacheModel.name = null;
		}

		return catalogCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Catalog, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Catalog, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Catalog, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply((Catalog)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Catalog>
			_escapedModelProxyProviderFunction =
				ProxyUtil.getProxyProviderFunction(
					Catalog.class, ModelWrapper.class);

	}

	private String _uuid;
	private long _catalogId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _categoryId;
	private String _name;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<Catalog, Object> function = _attributeGetterFunctions.get(
			columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((Catalog)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put("catalogId", _catalogId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("categoryId", _categoryId);
		_columnOriginalValues.put("name", _name);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("uuid_", 1L);

		columnBitmasks.put("catalogId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("categoryId", 256L);

		columnBitmasks.put("name", 512L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private Catalog _escapedModel;

}