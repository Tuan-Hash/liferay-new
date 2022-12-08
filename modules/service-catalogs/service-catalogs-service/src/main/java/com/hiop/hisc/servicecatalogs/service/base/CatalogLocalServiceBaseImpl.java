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

package com.hiop.hisc.servicecatalogs.service.base;

import com.hiop.hisc.servicecatalogs.model.Catalog;
import com.hiop.hisc.servicecatalogs.service.CatalogLocalService;
import com.hiop.hisc.servicecatalogs.service.CatalogLocalServiceUtil;
import com.hiop.hisc.servicecatalogs.service.persistence.CatalogPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the catalog local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.hiop.hisc.servicecatalogs.service.impl.CatalogLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.hiop.hisc.servicecatalogs.service.impl.CatalogLocalServiceImpl
 * @generated
 */
public abstract class CatalogLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CatalogLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CatalogLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CatalogLocalServiceUtil</code>.
	 */

	/**
	 * Adds the catalog to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CatalogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param catalog the catalog
	 * @return the catalog that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Catalog addCatalog(Catalog catalog) {
		catalog.setNew(true);

		return catalogPersistence.update(catalog);
	}

	/**
	 * Creates a new catalog with the primary key. Does not add the catalog to the database.
	 *
	 * @param catalogId the primary key for the new catalog
	 * @return the new catalog
	 */
	@Override
	@Transactional(enabled = false)
	public Catalog createCatalog(long catalogId) {
		return catalogPersistence.create(catalogId);
	}

	/**
	 * Deletes the catalog with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CatalogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param catalogId the primary key of the catalog
	 * @return the catalog that was removed
	 * @throws PortalException if a catalog with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Catalog deleteCatalog(long catalogId) throws PortalException {
		return catalogPersistence.remove(catalogId);
	}

	/**
	 * Deletes the catalog from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CatalogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param catalog the catalog
	 * @return the catalog that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Catalog deleteCatalog(Catalog catalog) {
		return catalogPersistence.remove(catalog);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return catalogPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			Catalog.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return catalogPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.servicecatalogs.model.impl.CatalogModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return catalogPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.servicecatalogs.model.impl.CatalogModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return catalogPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return catalogPersistence.countWithDynamicQuery(dynamicQuery);
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
		DynamicQuery dynamicQuery, Projection projection) {

		return catalogPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public Catalog fetchCatalog(long catalogId) {
		return catalogPersistence.fetchByPrimaryKey(catalogId);
	}

	/**
	 * Returns the catalog matching the UUID and group.
	 *
	 * @param uuid the catalog's UUID
	 * @param groupId the primary key of the group
	 * @return the matching catalog, or <code>null</code> if a matching catalog could not be found
	 */
	@Override
	public Catalog fetchCatalogByUuidAndGroupId(String uuid, long groupId) {
		return catalogPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the catalog with the primary key.
	 *
	 * @param catalogId the primary key of the catalog
	 * @return the catalog
	 * @throws PortalException if a catalog with the primary key could not be found
	 */
	@Override
	public Catalog getCatalog(long catalogId) throws PortalException {
		return catalogPersistence.findByPrimaryKey(catalogId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(catalogLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Catalog.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("catalogId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			catalogLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Catalog.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("catalogId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(catalogLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Catalog.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("catalogId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Catalog>() {

				@Override
				public void performAction(Catalog catalog)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, catalog);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(Catalog.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return catalogPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return catalogLocalService.deleteCatalog((Catalog)persistedModel);
	}

	@Override
	public BasePersistence<Catalog> getBasePersistence() {
		return catalogPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return catalogPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the catalogs matching the UUID and company.
	 *
	 * @param uuid the UUID of the catalogs
	 * @param companyId the primary key of the company
	 * @return the matching catalogs, or an empty list if no matches were found
	 */
	@Override
	public List<Catalog> getCatalogsByUuidAndCompanyId(
		String uuid, long companyId) {

		return catalogPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of catalogs matching the UUID and company.
	 *
	 * @param uuid the UUID of the catalogs
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of catalogs
	 * @param end the upper bound of the range of catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching catalogs, or an empty list if no matches were found
	 */
	@Override
	public List<Catalog> getCatalogsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Catalog> orderByComparator) {

		return catalogPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the catalog matching the UUID and group.
	 *
	 * @param uuid the catalog's UUID
	 * @param groupId the primary key of the group
	 * @return the matching catalog
	 * @throws PortalException if a matching catalog could not be found
	 */
	@Override
	public Catalog getCatalogByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return catalogPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.hiop.hisc.servicecatalogs.model.impl.CatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of catalogs
	 * @param end the upper bound of the range of catalogs (not inclusive)
	 * @return the range of catalogs
	 */
	@Override
	public List<Catalog> getCatalogs(int start, int end) {
		return catalogPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of catalogs.
	 *
	 * @return the number of catalogs
	 */
	@Override
	public int getCatalogsCount() {
		return catalogPersistence.countAll();
	}

	/**
	 * Updates the catalog in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CatalogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param catalog the catalog
	 * @return the catalog that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Catalog updateCatalog(Catalog catalog) {
		return catalogPersistence.update(catalog);
	}

	@Deactivate
	protected void deactivate() {
		_setLocalServiceUtilService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CatalogLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		catalogLocalService = (CatalogLocalService)aopProxy;

		_setLocalServiceUtilService(catalogLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CatalogLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Catalog.class;
	}

	protected String getModelClassName() {
		return Catalog.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = catalogPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		CatalogLocalService catalogLocalService) {

		try {
			Field field = CatalogLocalServiceUtil.class.getDeclaredField(
				"_service");

			field.setAccessible(true);

			field.set(null, catalogLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	protected CatalogLocalService catalogLocalService;

	@Reference
	protected CatalogPersistence catalogPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@Reference
	protected com.liferay.asset.kernel.service.AssetEntryLocalService
		assetEntryLocalService;

	@Reference
	protected com.liferay.asset.kernel.service.AssetTagLocalService
		assetTagLocalService;

}