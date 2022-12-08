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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Report}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Report
 * @generated
 */
public class ReportWrapper
	extends BaseModelWrapper<Report> implements ModelWrapper<Report>, Report {

	public ReportWrapper(Report report) {
		super(report);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("reportId", getReportId());
		attributes.put("categoryId", getCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("icon", getIcon());
		attributes.put("sources", getSources());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long reportId = (Long)attributes.get("reportId");

		if (reportId != null) {
			setReportId(reportId);
		}

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String icon = (String)attributes.get("icon");

		if (icon != null) {
			setIcon(icon);
		}

		String sources = (String)attributes.get("sources");

		if (sources != null) {
			setSources(sources);
		}
	}

	@Override
	public Report cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the category ID of this report.
	 *
	 * @return the category ID of this report
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the company ID of this report.
	 *
	 * @return the company ID of this report
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this report.
	 *
	 * @return the create date of this report
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this report.
	 *
	 * @return the description of this report
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this report.
	 *
	 * @return the group ID of this report
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the icon of this report.
	 *
	 * @return the icon of this report
	 */
	@Override
	public String getIcon() {
		return model.getIcon();
	}

	/**
	 * Returns the modified date of this report.
	 *
	 * @return the modified date of this report
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this report.
	 *
	 * @return the name of this report
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this report.
	 *
	 * @return the primary key of this report
	 */
	@Override
	public com.hiop.hisc.reports.service.persistence.ReportPK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the report ID of this report.
	 *
	 * @return the report ID of this report
	 */
	@Override
	public long getReportId() {
		return model.getReportId();
	}

	/**
	 * Returns the sources of this report.
	 *
	 * @return the sources of this report
	 */
	@Override
	public String getSources() {
		return model.getSources();
	}

	/**
	 * Returns the user ID of this report.
	 *
	 * @return the user ID of this report
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this report.
	 *
	 * @return the user uuid of this report
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this report.
	 *
	 * @return the uuid of this report
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
	 * Sets the category ID of this report.
	 *
	 * @param categoryId the category ID of this report
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the company ID of this report.
	 *
	 * @param companyId the company ID of this report
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this report.
	 *
	 * @param createDate the create date of this report
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this report.
	 *
	 * @param description the description of this report
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this report.
	 *
	 * @param groupId the group ID of this report
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the icon of this report.
	 *
	 * @param icon the icon of this report
	 */
	@Override
	public void setIcon(String icon) {
		model.setIcon(icon);
	}

	/**
	 * Sets the modified date of this report.
	 *
	 * @param modifiedDate the modified date of this report
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this report.
	 *
	 * @param name the name of this report
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this report.
	 *
	 * @param primaryKey the primary key of this report
	 */
	@Override
	public void setPrimaryKey(
		com.hiop.hisc.reports.service.persistence.ReportPK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the report ID of this report.
	 *
	 * @param reportId the report ID of this report
	 */
	@Override
	public void setReportId(long reportId) {
		model.setReportId(reportId);
	}

	/**
	 * Sets the sources of this report.
	 *
	 * @param sources the sources of this report
	 */
	@Override
	public void setSources(String sources) {
		model.setSources(sources);
	}

	/**
	 * Sets the user ID of this report.
	 *
	 * @param userId the user ID of this report
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this report.
	 *
	 * @param userUuid the user uuid of this report
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this report.
	 *
	 * @param uuid the uuid of this report
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
	protected ReportWrapper wrap(Report report) {
		return new ReportWrapper(report);
	}

}