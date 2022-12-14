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

package com.hiop.hisc.reports.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the Category service. Represents a row in the &quot;hisc_report_Category&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.hiop.hisc.reports.model.impl.CategoryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.hiop.hisc.reports.model.impl.CategoryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Category
 * @generated
 */
@ProviderType
public interface CategoryModel
	extends BaseModel<Category>, ShardedModel, StagedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a category model instance should use the {@link Category} interface instead.
	 */

	/**
	 * Returns the primary key of this category.
	 *
	 * @return the primary key of this category
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this category.
	 *
	 * @param primaryKey the primary key of this category
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this category.
	 *
	 * @return the uuid of this category
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this category.
	 *
	 * @param uuid the uuid of this category
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the category ID of this category.
	 *
	 * @return the category ID of this category
	 */
	public long getCategoryId();

	/**
	 * Sets the category ID of this category.
	 *
	 * @param categoryId the category ID of this category
	 */
	public void setCategoryId(long categoryId);

	/**
	 * Returns the group ID of this category.
	 *
	 * @return the group ID of this category
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this category.
	 *
	 * @param groupId the group ID of this category
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this category.
	 *
	 * @return the company ID of this category
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this category.
	 *
	 * @param companyId the company ID of this category
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this category.
	 *
	 * @return the user ID of this category
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this category.
	 *
	 * @param userId the user ID of this category
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this category.
	 *
	 * @return the user uuid of this category
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this category.
	 *
	 * @param userUuid the user uuid of this category
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the create date of this category.
	 *
	 * @return the create date of this category
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this category.
	 *
	 * @param createDate the create date of this category
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this category.
	 *
	 * @return the modified date of this category
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this category.
	 *
	 * @param modifiedDate the modified date of this category
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this category.
	 *
	 * @return the name of this category
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this category.
	 *
	 * @param name the name of this category
	 */
	public void setName(String name);

	/**
	 * Returns the description of this category.
	 *
	 * @return the description of this category
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this category.
	 *
	 * @param description the description of this category
	 */
	public void setDescription(String description);

	/**
	 * Returns the icon of this category.
	 *
	 * @return the icon of this category
	 */
	@AutoEscape
	public String getIcon();

	/**
	 * Sets the icon of this category.
	 *
	 * @param icon the icon of this category
	 */
	public void setIcon(String icon);

	@Override
	public Category cloneWithOriginalValues();

}