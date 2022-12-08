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

package com.hiop.hisc.servicecatalogs.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Catalog}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Catalog
 * @generated
 */
public class CatalogWrapper
	extends BaseModelWrapper<Catalog>
	implements Catalog, ModelWrapper<Catalog> {

	public CatalogWrapper(Catalog catalog) {
		super(catalog);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("catalogId", getCatalogId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("categoryId", getCategoryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long catalogId = (Long)attributes.get("catalogId");

		if (catalogId != null) {
			setCatalogId(catalogId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String categoryId = (String)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public Catalog cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the catalog ID of this catalog.
	 *
	 * @return the catalog ID of this catalog
	 */
	@Override
	public long getCatalogId() {
		return model.getCatalogId();
	}

	/**
	 * Returns the category ID of this catalog.
	 *
	 * @return the category ID of this catalog
	 */
	@Override
	public String getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the company ID of this catalog.
	 *
	 * @return the company ID of this catalog
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this catalog.
	 *
	 * @return the create date of this catalog
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this catalog.
	 *
	 * @return the group ID of this catalog
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this catalog.
	 *
	 * @return the modified date of this catalog
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this catalog.
	 *
	 * @return the name of this catalog
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this catalog.
	 *
	 * @return the primary key of this catalog
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this catalog.
	 *
	 * @return the user ID of this catalog
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this catalog.
	 *
	 * @return the user name of this catalog
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this catalog.
	 *
	 * @return the user uuid of this catalog
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this catalog.
	 *
	 * @return the uuid of this catalog
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the catalog ID of this catalog.
	 *
	 * @param catalogId the catalog ID of this catalog
	 */
	@Override
	public void setCatalogId(long catalogId) {
		model.setCatalogId(catalogId);
	}

	/**
	 * Sets the category ID of this catalog.
	 *
	 * @param categoryId the category ID of this catalog
	 */
	@Override
	public void setCategoryId(String categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the company ID of this catalog.
	 *
	 * @param companyId the company ID of this catalog
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this catalog.
	 *
	 * @param createDate the create date of this catalog
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this catalog.
	 *
	 * @param groupId the group ID of this catalog
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this catalog.
	 *
	 * @param modifiedDate the modified date of this catalog
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this catalog.
	 *
	 * @param name the name of this catalog
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this catalog.
	 *
	 * @param primaryKey the primary key of this catalog
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this catalog.
	 *
	 * @param userId the user ID of this catalog
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this catalog.
	 *
	 * @param userName the user name of this catalog
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this catalog.
	 *
	 * @param userUuid the user uuid of this catalog
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this catalog.
	 *
	 * @param uuid the uuid of this catalog
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CatalogWrapper wrap(Catalog catalog) {
		return new CatalogWrapper(catalog);
	}

}