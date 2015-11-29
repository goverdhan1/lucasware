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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.dao.eai.TransformationDao;
import com.lucas.entity.eai.transformation.Transformation;

@RunWith(MockitoJUnitRunner.class)
public class TransformationServiceUnitTests {


		@Mock
		private TransformationDao transformationDao;

		@InjectMocks
		private TransformationServiceImpl transformationService;

		private Transformation transformation;

		private Long transformationId = 1L;


		private String transformationChannel = "aTransformationChannel";

		public TransformationServiceUnitTests() {

		}

		@Before
		public void setup() throws Exception {
			transformation = new Transformation(transformationChannel);
			transformation.setTransformationId(transformationId);
		}

		@Test
		public void testGetTransformationById() {
			when(transformationDao.getTransformationById(anyLong())).thenReturn(transformation);
			transformationService.getTransformationById(transformationId);
			verify(transformationDao, times(1)).getTransformationById(anyLong());
		}

		@Test
		public void testGetTransformationByNullId() {
			when(transformationDao.getTransformationById(anyLong())).thenReturn(null);
			transformationService.getTransformationById(null);
			verify(transformationDao, times(1)).getTransformationById(anyLong());
		}


}
