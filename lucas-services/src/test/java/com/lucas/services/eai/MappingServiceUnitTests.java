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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

import com.lucas.dao.eai.MappingDao;
import com.lucas.dao.eai.MappingPathDao;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;

@RunWith(MockitoJUnitRunner.class)
public class MappingServiceUnitTests {

	@Mock
	private MappingDao mappingDao;
	
	@Mock
	private MappingPathDao mappingPathDao;

	@InjectMocks
	private MappingServiceImpl mappingService;

	private Mapping mapping;

	private Long mappingId = 1L;

	private String mappingName = "aMappingName";

	private String mappingDescription = "aMappingDescription";
	
	private MappingPath mappingPath;
	
	private List<MappingPath> mappingPathList = new ArrayList<MappingPath>();

	public MappingServiceUnitTests() {
	}

	@Before
	public void setup() throws Exception {
		mapping = new Mapping(null, mappingDescription, mappingName, null,
				null, null, null, null, null);
		mapping.setMappingId(mappingId);
		
		mappingPath = new MappingPath(mapping);
		
		mappingPathList.add(mappingPath);
	}

	@Test
	public void testGetMappingById() {
		when(mappingDao.getMappingById(anyLong())).thenReturn(mapping);
		mappingService.getMappingById(mappingId);
		verify(mappingDao, times(1)).getMappingById(anyLong());
	}

	@Test
	public void testGetMappingByNullId() {
		when(mappingDao.getMappingById(null)).thenReturn(null);
		mappingService.getMappingById(null);
		verify(mappingDao, times(1)).getMappingById(anyLong());
	}

	@Test
	public void testGetMappingByName() {
		when(mappingDao.getMappingByName(anyString())).thenReturn(mapping);
		mappingService.getMappingByName(mappingName);
		verify(mappingDao, times(1)).getMappingByName(anyString());
	}

	@Test
	public void testGetMappingByNullName() {
		when(mappingDao.getMappingByName(null)).thenReturn(null);
		mappingService.getMappingByName(null);
		verify(mappingDao, times(1)).getMappingByName(null);
	}
	
	@Test
	public void testGetMappingPathByMappingName() {
		when(mappingPathDao.getMappingPathByMappingName(anyString())).thenReturn(mappingPathList);
		mappingService.getMappingPathByMappingName(mappingName);
		verify(mappingPathDao, times(1)).getMappingPathByMappingName(anyString());
	}

	@Test
	public void testGetMappingPathByNullMappingName() {
		when(mappingPathDao.getMappingPathByMappingName(null)).thenReturn(null);
		mappingService.getMappingPathByMappingName(null);
		verify(mappingPathDao, times(1)).getMappingPathByMappingName(null);
	}

}
