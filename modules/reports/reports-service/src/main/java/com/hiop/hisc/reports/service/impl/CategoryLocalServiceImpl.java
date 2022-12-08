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

package com.hiop.hisc.reports.service.impl;

import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.service.base.CategoryLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author huyth
 */
@Component(property = "model.class.name=com.hiop.hisc.reports.model.Category", service = AopService.class)
public class CategoryLocalServiceImpl extends CategoryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public Category addEntry(Category category, ServiceContext serviceContext)
			throws PortalException {

		long categoryId = counterLocalService.increment();
		category.setCategoryId(categoryId);
		category.setNew(true);
		category = categoryPersistence.update(category);

		resourceLocalService.addModelResources(
				category.getCompanyId(), category.getGroupId(),
				category.getUserId(), getModelClassName(),
				categoryId, serviceContext.getModelPermissions());

		return category;
	}

	@Indexable(type = IndexableType.REINDEX)
	public Category updateEntry(Category category, ServiceContext serviceContext)
			throws PortalException {

		category = categoryPersistence.update(category);

		resourceLocalService.deleteResource(
				serviceContext.getCompanyId(), getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		resourceLocalService.updateResources(
				category.getCompanyId(), category.getGroupId(),
				getModelClassName(), category.getCategoryId(),
				serviceContext.getModelPermissions());

		return category;
	}

	@Indexable(type = IndexableType.DELETE)
	public Category deleteEntry(long categoryId, ServiceContext serviceContext)
			throws PortalException {

		Category category = categoryPersistence.remove(categoryId);

		resourceLocalService.deleteResource(
				serviceContext.getCompanyId(), getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		return category;
	}
}