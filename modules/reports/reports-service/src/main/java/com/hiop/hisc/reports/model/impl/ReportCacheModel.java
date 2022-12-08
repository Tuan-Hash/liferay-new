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

package com.hiop.hisc.reports.model.impl;

import com.hiop.hisc.reports.model.Report;
import com.hiop.hisc.reports.service.persistence.ReportPK;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Report in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ReportCacheModel implements CacheModel<Report>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReportCacheModel)) {
			return false;
		}

		ReportCacheModel reportCacheModel = (ReportCacheModel)object;

		if (reportPK.equals(reportCacheModel.reportPK)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, reportPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", reportId=");
		sb.append(reportId);
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", icon=");
		sb.append(icon);
		sb.append(", sources=");
		sb.append(sources);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Report toEntityModel() {
		ReportImpl reportImpl = new ReportImpl();

		if (uuid == null) {
			reportImpl.setUuid("");
		}
		else {
			reportImpl.setUuid(uuid);
		}

		reportImpl.setReportId(reportId);
		reportImpl.setCategoryId(categoryId);
		reportImpl.setGroupId(groupId);
		reportImpl.setCompanyId(companyId);
		reportImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			reportImpl.setCreateDate(null);
		}
		else {
			reportImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			reportImpl.setModifiedDate(null);
		}
		else {
			reportImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			reportImpl.setName("");
		}
		else {
			reportImpl.setName(name);
		}

		if (description == null) {
			reportImpl.setDescription("");
		}
		else {
			reportImpl.setDescription(description);
		}

		if (icon == null) {
			reportImpl.setIcon("");
		}
		else {
			reportImpl.setIcon(icon);
		}

		if (sources == null) {
			reportImpl.setSources("");
		}
		else {
			reportImpl.setSources(sources);
		}

		reportImpl.resetOriginalValues();

		return reportImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		reportId = objectInput.readLong();

		categoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		icon = objectInput.readUTF();
		sources = objectInput.readUTF();

		reportPK = new ReportPK(reportId, categoryId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(reportId);

		objectOutput.writeLong(categoryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (icon == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(icon);
		}

		if (sources == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sources);
		}
	}

	public String uuid;
	public long reportId;
	public long categoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String icon;
	public String sources;
	public transient ReportPK reportPK;

}