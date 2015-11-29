/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

 */
package com.lucas.services.eai;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.eai.TransformationDao;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;

/**
 * This class is the service layer implementation class to provide all the
 * business logic needs to be performed on the transformation domain
 * 
 * 
 */
@Service("transformationService")
public class TransformationServiceImpl implements TransformationService {

	private static Logger LOG = LoggerFactory
			.getLogger(TransformationServiceImpl.class);

	private TransformationDao transformationDao;

	/**
	 * 
	 */
	public TransformationServiceImpl() {
	}

	@Inject
	public TransformationServiceImpl(TransformationDao transformationDao) {
		this.transformationDao = transformationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.TransformationService#getTransformationById(java
	 * .lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public Transformation getTransformationById(Long transformationId) {
		return transformationDao.getTransformationById(transformationId);
	}


}
