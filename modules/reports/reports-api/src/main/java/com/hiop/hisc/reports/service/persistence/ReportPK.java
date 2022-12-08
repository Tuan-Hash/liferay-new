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

package com.hiop.hisc.reports.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ReportPK implements Comparable<ReportPK>, Serializable {

	public long reportId;
	public long categoryId;

	public ReportPK() {
	}

	public ReportPK(long reportId, long categoryId) {
		this.reportId = reportId;
		this.categoryId = categoryId;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public int compareTo(ReportPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (reportId < pk.reportId) {
			value = -1;
		}
		else if (reportId > pk.reportId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (categoryId < pk.categoryId) {
			value = -1;
		}
		else if (categoryId > pk.categoryId) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		if (!(object instanceof ReportPK)) {
			return false;
		}

		ReportPK pk = (ReportPK)object;

		if ((reportId == pk.reportId) && (categoryId == pk.categoryId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, reportId);
		hashCode = HashUtil.hash(hashCode, categoryId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append("{");

		sb.append("reportId=");

		sb.append(reportId);
		sb.append(", categoryId=");

		sb.append(categoryId);

		sb.append("}");

		return sb.toString();
	}

}