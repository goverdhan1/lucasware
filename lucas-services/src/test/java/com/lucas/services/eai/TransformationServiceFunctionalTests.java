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

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class TransformationServiceFunctionalTests extends
		AbstractSpringFunctionalTests {

	@Inject
	private TransformationService transformationService;

	private Transformation transformation;

	private Long existingTransformationId;

	private Long nonExistingTransformationId;

	private String transformationChannel = "userExpressionTransformationChannel";

	private static final Logger LOG = LoggerFactory
			.getLogger(TransformationServiceFunctionalTests.class);

	public TransformationServiceFunctionalTests() {
	}

	@Before
	public void setUp() throws Exception {

		transformation = new Transformation(transformationChannel);
		transformation.setTransformationId(existingTransformationId);
		existingTransformationId = 1l;
		nonExistingTransformationId = -1l;
	}

	@Test
	public void testGetTransformationByExistingId() {
		Transformation retrievedTransformation = transformationService
				.getTransformationById(existingTransformationId);
		Assert.notNull(retrievedTransformation,
				"Transformation with an existing id is null");
		Assert.isTrue(
				retrievedTransformation.getTransformationChannel().equals(
						transformation.getTransformationChannel()),
				"Transformation channel for the existing transformation is not the same as expected");
		LOG.debug("The #of transformation definitions for this transformation found "
				+ retrievedTransformation.getEaiTransformationDefinitions()
						.size());
	}

	@Test
	public void testGetTransformationByNonExistingId() {
		Transformation retrievedTransformation = transformationService
				.getTransformationById(nonExistingTransformationId);
		Assert.isNull(retrievedTransformation,
				"Transformation with a non existing id is not null");
	}

}
