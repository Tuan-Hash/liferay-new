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

package com.hiop.hisc.reports.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ReportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ReportLocalService
 * @generated
 */
public class ReportLocalServiceWrapper
	implements ReportLocalService, ServiceWrapper<ReportLocalService> {

	public ReportLocalServiceWrapper() {
		this(null);
	}

	public ReportLocalServiceWrapper(ReportLocalService reportLocalService) {
		_reportLocalService = reportLocalService;
	}

	/**
	 * Adds the report to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ReportLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param report the report
	 * @return the report that was added
	 */
	@Override
	public com.hiop.hisc.reports.model.Report addReport(
		com.hiop.hisc.reports.model.Report report) {

		return _reportLocalService.addReport(report);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new report with the primary key. Does not add the report to the database.
	 *
	 * @param reportPK the primary key for the new report
	 * @return the new report
	 */
	@Override
	public com.hiop.hisc.reports.model.Report createReport(
		com.hiop.hisc.reports.service.persistence.ReportPK reportPK) {

		return _reportLocalService.createReport(reportPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the report from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ReportLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param report the report
	 * @return the report that was removed
	 */
	@Override
	public com.hiop.hisc.reports.model.Report deleteReport(
		com.hiop.hisc.reports.model.Report report) {

		return _reportLocalService.deleteReport(report);
	}

	/**
	 * Deletes the report with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ReportLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param reportPK the primary key of the report
	 * @return the report that was removed
	 * @throws PortalException if a report with the primary key could not be found
	 */
	@Override
	public com.hiop.hisc.reports.model.Report deleteReport(
			com.hiop.hisc.reports.service.persistence.ReportPK
				reportPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.deleteReport(reportPK);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _reportLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _reportLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _reportLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _reportLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.reports.model.impl.ReportModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _reportLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.reports.model.impl.ReportModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _reportLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _reportLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _reportLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.hiop.hisc.reports.model.Report fetchReport(
		com.hiop.hisc.reports.service.persistence.ReportPK reportPK) {

		return _reportLocalService.fetchReport(reportPK);
	}

	/**
	 * Returns the report matching the UUID and group.
	 *
	 * @param uuid the report's UUID
	 * @param groupId the primary key of the group
	 * @return the matching report, or <code>null</code> if a matching report could not be found
	 */
	@Override
	public com.hiop.hisc.reports.model.Report
		fetchReportByUuidAndGroupId(String uuid, long groupId) {

		return _reportLocalService.fetchReportByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _reportLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _reportLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _reportLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _reportLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the report with the primary key.
	 *
	 * @param reportPK the primary key of the report
	 * @return the report
	 * @throws PortalException if a report with the primary key could not be found
	 */
	@Override
	public com.hiop.hisc.reports.model.Report getReport(
			com.hiop.hisc.reports.service.persistence.ReportPK
				reportPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.getReport(reportPK);
	}

	/**
	 * Returns the report matching the UUID and group.
	 *
	 * @param uuid the report's UUID
	 * @param groupId the primary key of the group
	 * @return the matching report
	 * @throws PortalException if a matching report could not be found
	 */
	@Override
	public com.hiop.hisc.reports.model.Report
			getReportByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _reportLocalService.getReportByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.reports.model.impl.ReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @return the range of reports
	 */
	@Override
	public java.util.List<com.hiop.hisc.reports.model.Report>
		getReports(int start, int end) {

		return _reportLocalService.getReports(start, end);
	}

	/**
	 * Returns all the reports matching the UUID and company.
	 *
	 * @param uuid the UUID of the reports
	 * @param companyId the primary key of the company
	 * @return the matching reports, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.hiop.hisc.reports.model.Report>
		getReportsByUuidAndCompanyId(String uuid, long companyId) {

		return _reportLocalService.getReportsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of reports matching the UUID and company.
	 *
	 * @param uuid the UUID of the reports
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of reports
	 * @param end the upper bound of the range of reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching reports, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.hiop.hisc.reports.model.Report>
		getReportsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.hiop.hisc.reports.model.Report>
					orderByComparator) {

		return _reportLocalService.getReportsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of reports.
	 *
	 * @return the number of reports
	 */
	@Override
	public int getReportsCount() {
		return _reportLocalService.getReportsCount();
	}

	/**
	 * Updates the report in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ReportLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param report the report
	 * @return the report that was updated
	 */
	@Override
	public com.hiop.hisc.reports.model.Report updateReport(
		com.hiop.hisc.reports.model.Report report) {

		return _reportLocalService.updateReport(report);
	}

	@Override
	public ReportLocalService getWrappedService() {
		return _reportLocalService;
	}

	@Override
	public void setWrappedService(ReportLocalService reportLocalService) {
		_reportLocalService = reportLocalService;
	}

	private ReportLocalService _reportLocalService;

}