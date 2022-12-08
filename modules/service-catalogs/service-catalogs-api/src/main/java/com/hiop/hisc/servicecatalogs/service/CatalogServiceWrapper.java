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

package com.hiop.hisc.servicecatalogs.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CatalogService}.
 *
 * @author Brian Wing Shun Chan
 * @see CatalogService
 * @generated
 */
public class CatalogServiceWrapper
	implements CatalogService, ServiceWrapper<CatalogService> {

	public CatalogServiceWrapper() {
		this(null);
	}

	public CatalogServiceWrapper(CatalogService catalogService) {
		_catalogService = catalogService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _catalogService.getOSGiServiceIdentifier();
	}

	@Override
	public CatalogService getWrappedService() {
		return _catalogService;
	}

	@Override
	public void setWrappedService(CatalogService catalogService) {
		_catalogService = catalogService;
	}

	private CatalogService _catalogService;

}