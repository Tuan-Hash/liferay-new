package com.hiop.hisc.servicerequest.service.base;

import java.util.List;
import java.util.stream.Collectors;

import com.hiop.hisc.servicerequest.dto.ExternalRecord;

/**
 * @author nphong
 */

public abstract class RequestServiceBaseImpl {

	public static List<ExternalRecord> findByExternalType(List<ExternalRecord> externalRecords, String externalType) {
		return externalRecords
				.stream()
				.filter(externalRecord -> externalType.equals(externalRecord.getExternalType()))
				.collect(Collectors.toList());

	}

}