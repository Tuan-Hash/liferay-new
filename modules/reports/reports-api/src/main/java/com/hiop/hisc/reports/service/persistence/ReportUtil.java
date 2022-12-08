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

import com.hiop.hisc.reports.model.Report;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the report service. This utility wraps <code>com.hiop.hisc.reports.service.persistence.impl.ReportPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReportPersistence
 * @generated
 */
public class ReportUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Report report) {
		getPersistence().clearCache(report);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Report> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Report> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Report> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Report> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Report update(Report report) {
		return getPersistence().update(report);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Report update(Report report, ServiceContext serviceContext) {
		return getPersistence().update(report, serviceContext);
	}

	/**
	 * Returns all the reports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching reports
	 */
	public static List<Report> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the reports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @return the range of matching reports
	 */
	public static List<Report> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the reports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the reports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Report> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first report in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByUuid_First(
			String uuid, OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first report in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUuid_First(
		String uuid, OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByUuid_Last(
			String uuid, OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUuid_Last(
		String uuid, OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the reports before and after the current report in the ordered set where uuid = &#63;.
	 *
	 * @param reportPK the primary key of the current report
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next report
	 * @throws NoSuchReportException if a report with the primary key could not be found
	 */
	public static Report[] findByUuid_PrevAndNext(
			ReportPK reportPK, String uuid,
			OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_PrevAndNext(
			reportPK, uuid, orderByComparator);
	}

	/**
	 * Removes all the reports where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of reports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching reports
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the report where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchReportException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByUUID_G(String uuid, long groupId)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the report where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the report where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the report where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the report that was removed
	 */
	public static Report removeByUUID_G(String uuid, long groupId)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of reports where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching reports
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the reports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching reports
	 */
	public static List<Report> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the reports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @return the range of matching reports
	 */
	public static List<Report> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the reports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the reports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Report> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first report in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first report in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the reports before and after the current report in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param reportPK the primary key of the current report
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next report
	 * @throws NoSuchReportException if a report with the primary key could not be found
	 */
	public static Report[] findByUuid_C_PrevAndNext(
			ReportPK reportPK, String uuid, long companyId,
			OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByUuid_C_PrevAndNext(
			reportPK, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the reports where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of reports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching reports
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the reports where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching reports
	 */
	public static List<Report> findByName(String name) {
		return getPersistence().findByName(name);
	}

	/**
	 * Returns a range of all the reports where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @return the range of matching reports
	 */
	public static List<Report> findByName(String name, int start, int end) {
		return getPersistence().findByName(name, start, end);
	}

	/**
	 * Returns an ordered range of all the reports where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByName(
		String name, int start, int end,
		OrderByComparator<Report> orderByComparator) {

		return getPersistence().findByName(name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the reports where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reports
	 */
	public static List<Report> findByName(
		String name, int start, int end,
		OrderByComparator<Report> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByName(
			name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first report in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByName_First(
			String name, OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByName_First(name, orderByComparator);
	}

	/**
	 * Returns the first report in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByName_First(
		String name, OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByName_First(name, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report
	 * @throws NoSuchReportException if a matching report could not be found
	 */
	public static Report findByName_Last(
			String name, OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByName_Last(name, orderByComparator);
	}

	/**
	 * Returns the last report in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching report, or <code>null</code> if a matching report could not be found
	 */
	public static Report fetchByName_Last(
		String name, OrderByComparator<Report> orderByComparator) {

		return getPersistence().fetchByName_Last(name, orderByComparator);
	}

	/**
	 * Returns the reports before and after the current report in the ordered set where name = &#63;.
	 *
	 * @param reportPK the primary key of the current report
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next report
	 * @throws NoSuchReportException if a report with the primary key could not be found
	 */
	public static Report[] findByName_PrevAndNext(
			ReportPK reportPK, String name,
			OrderByComparator<Report> orderByComparator)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByName_PrevAndNext(
			reportPK, name, orderByComparator);
	}

	/**
	 * Removes all the reports where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public static void removeByName(String name) {
		getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of reports where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching reports
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Caches the report in the entity cache if it is enabled.
	 *
	 * @param report the report
	 */
	public static void cacheResult(Report report) {
		getPersistence().cacheResult(report);
	}

	/**
	 * Caches the reports in the entity cache if it is enabled.
	 *
	 * @param reports the reports
	 */
	public static void cacheResult(List<Report> reports) {
		getPersistence().cacheResult(reports);
	}

	/**
	 * Creates a new report with the primary key. Does not add the report to the database.
	 *
	 * @param reportPK the primary key for the new report
	 * @return the new report
	 */
	public static Report create(ReportPK reportPK) {
		return getPersistence().create(reportPK);
	}

	/**
	 * Removes the report with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param reportPK the primary key of the report
	 * @return the report that was removed
	 * @throws NoSuchReportException if a report with the primary key could not be found
	 */
	public static Report remove(ReportPK reportPK)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().remove(reportPK);
	}

	public static Report updateImpl(Report report) {
		return getPersistence().updateImpl(report);
	}

	/**
	 * Returns the report with the primary key or throws a <code>NoSuchReportException</code> if it could not be found.
	 *
	 * @param reportPK the primary key of the report
	 * @return the report
	 * @throws NoSuchReportException if a report with the primary key could not be found
	 */
	public static Report findByPrimaryKey(ReportPK reportPK)
		throws com.hiop.hisc.reports.exception.NoSuchReportException {

		return getPersistence().findByPrimaryKey(reportPK);
	}

	/**
	 * Returns the report with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param reportPK the primary key of the report
	 * @return the report, or <code>null</code> if a report with the primary key could not be found
	 */
	public static Report fetchByPrimaryKey(ReportPK reportPK) {
		return getPersistence().fetchByPrimaryKey(reportPK);
	}

	/**
	 * Returns all the reports.
	 *
	 * @return the reports
	 */
	public static List<Report> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @return the range of reports
	 */
	public static List<Report> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of reports
	 */
	public static List<Report> findAll(
		int start, int end, OrderByComparator<Report> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of reports
	 */
	public static List<Report> findAll(
		int start, int end, OrderByComparator<Report> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the reports from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of reports.
	 *
	 * @return the number of reports
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static ReportPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ReportPersistence _persistence;

}