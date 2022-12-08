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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Catalog in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CatalogCacheModel implements CacheModel<Catalog>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CatalogCacheModel)) {
			return false;
		}

		CatalogCacheModel catalogCacheModel = (CatalogCacheModel)object;

		if (catalogId == catalogCacheModel.catalogId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, catalogId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", catalogId=");
		sb.append(catalogId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Catalog toEntityModel() {
		CatalogImpl catalogImpl = new CatalogImpl();

		if (uuid == null) {
			catalogImpl.setUuid("");
		}
		else {
			catalogImpl.setUuid(uuid);
		}

		catalogImpl.setCatalogId(catalogId);
		catalogImpl.setGroupId(groupId);
		catalogImpl.setCompanyId(companyId);
		catalogImpl.setUserId(userId);

		if (userName == null) {
			catalogImpl.setUserName("");
		}
		else {
			catalogImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			catalogImpl.setCreateDate(null);
		}
		else {
			catalogImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			catalogImpl.setModifiedDate(null);
		}
		else {
			catalogImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (categoryId == null) {
			catalogImpl.setCategoryId("");
		}
		else {
			catalogImpl.setCategoryId(categoryId);
		}

		if (name == null) {
			catalogImpl.setName("");
		}
		else {
			catalogImpl.setName(name);
		}

		catalogImpl.resetOriginalValues();

		return catalogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		catalogId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		categoryId = objectInput.readUTF();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(catalogId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (categoryId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(categoryId);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public String uuid;
	public long catalogId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String categoryId;
	public String name;

}