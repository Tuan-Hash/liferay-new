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

package com.hiop.hisc.servicecatalogs.service.impl;

import com.hiop.hisc.servicecatalogs.service.base.CatalogLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;

import com.hiop.hisc.servicecatalogs.model.Catalog;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.hiop.hisc.servicecatalogs.model.Catalog",
	service = AopService.class
)
public class CatalogLocalServiceImpl extends CatalogLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public Catalog addEntry(Catalog catalog) throws PortalException {

		long catalogId = counterLocalService.increment();
		catalog.setCatalogId(catalogId);
		catalog.setNew(true);
		catalog = catalogPersistence.update(catalog);

		resourceLocalService.addResources(
				catalog.getCompanyId(), catalog.getGroupId(), catalog.getUserId(),
				getModelClassName(), catalogId, false, true, true);

		return catalog;
	}

	@Indexable(type = IndexableType.DELETE)
	public Catalog deleteEntry(long catalogId) throws PortalException {

		Catalog catalog = catalogPersistence.remove(catalogId);

		resourceLocalService.deleteResource(
				catalog.getCompanyId(), getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL, catalog.getCatalogId());

		return catalog;
	}
}