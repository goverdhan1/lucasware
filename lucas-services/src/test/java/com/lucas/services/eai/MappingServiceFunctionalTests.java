package com.lucas.services.eai;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

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

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class MappingServiceFunctionalTests extends
		AbstractSpringFunctionalTests {

	@Inject
	private MappingService mappingService;

	private Long existingMappingId = 1L;

	private Long nonExistingMappingId = -1L;

	private String existingMappingName = "Transform inbound user details";

	private String nonExistingMappingName = "aMappingName";

	private String mappingDescription = "Transform inbound user details";

	private Long eventHandlerId = 1l;
	
	private String mappingName = "Transform inbound user details";
	
	private Mapping mapping;
	
	private static final Long mappingId = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(MappingServiceFunctionalTests.class);

	@Before
	public void setUp() throws Exception {

		mapping = new Mapping(null, mappingDescription, existingMappingName,
				null, null, null, null, null, null);
		mapping.setMappingId(existingMappingId);
	}

	@Test
	public void testGetMappingByExistingId() {
		Mapping retrievedMapping = mappingService
				.getMappingById(existingMappingId);
		Assert.notNull(retrievedMapping, "Mapping with an existing id is null");
		Assert.isTrue(
				retrievedMapping.getMappingName().equals(
						mapping.getMappingName()),
				"Mapping name for the existing mapping is not the same as expected");
	}

	@Test
	public void testGetMappingByNonExistingId() {
		Mapping retrievedMapping = mappingService
				.getMappingById(nonExistingMappingId);
		Assert.isNull(retrievedMapping,
				"Mapping with an non existing id is not null");
	}

	@Test
	public void testGetMappingByExistingName() {

		Mapping retrievedMapping = mappingService
				.getMappingByName(existingMappingName);
		Assert.notNull(retrievedMapping,
				"Mapping with an existing name is null");
		Assert.isTrue(
				retrievedMapping.getMappingId() == mapping.getMappingId(),
				"Mapping id for the existing mapping is not the same as expected");

	}

	@Test
	public void testGetMappingByNonExistingName() {
		Mapping retrievedMapping = mappingService
				.getMappingByName(nonExistingMappingName);
		Assert.isNull(retrievedMapping,
				"Mapping with an non existing name is not null");
	}

	@Test
	public void testGetMappingPathByExistingMappingName() {
		List<MappingPath> retrievedMappingPaths = mappingService
				.getMappingPathByMappingName(existingMappingName);
		Assert.notNull(retrievedMappingPaths,
				"Mapping path with an existing mapping name is null");
		Assert.isTrue(retrievedMappingPaths.size() == 2,
				"Expected retrieved mapping paths size is not equal to two");
	}

	@Test
	public void testGetMappingPathByNonExistingMappingName() {
		List<MappingPath> retrievedMappingPaths = mappingService
				.getMappingPathByMappingName(nonExistingMappingName);
		Assert.isTrue(retrievedMappingPaths.size() == 0,
				"Expected retrieved mapping paths size is not equal to zero");
	}
	
	@Test
	public void testGetMappingByEventHandlerId() {
		Mapping retrievedMapping = mappingService
				.getInboundMappingByEventHandlerId(eventHandlerId);
		Assert.notNull(retrievedMapping, "No mapping found for the event handler with id " + eventHandlerId);
		Assert.isTrue(retrievedMapping.getMappingName().equals(mappingName),
				"Expected retrieved mapping is differnet from what expected");
	}

    /**
     * testGetAllMappingNames() provide the testing functionality for
     * getting the list of the names form db using MappingService and MappingDao.
     *
     * @see com.lucas.services.eai.MappingService#getMappingsNames()
     * @see com.lucas.dao.eai.MappingDao#getMappingsNames()
     */
    @Test
    public void testGetAllMappingNames(){
        final List<String> mappingNameList=this.mappingService.getMappingsNames();
        Assert.notNull(mappingNameList, "Mapping Name List is Null");
        Assert.isTrue(!mappingNameList.isEmpty(),"Mapping Name List is Empty");
    }

	@Transactional
	@Rollback(true)
	@Test
	public void testGetMappingBySearchAndSortCriteria()
			throws ParseException, IOException, Exception {

		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		final LinkedHashMap<String, Object> searchMap = new LinkedHashMap<String, Object>();
		final List<String> mappingNameList = new ArrayList<String>();
		mappingNameList.add("Transform inbound user details");
		searchMap.put("mappingName", mappingNameList);

		final LinkedHashMap<String, Object> mappingDescriptionMap = new LinkedHashMap<String, Object>();
		mappingDescriptionMap.put("filterType", FilterType.ALPHANUMERIC);
		mappingDescriptionMap.put("values",
				Arrays.asList("Transform inbound user details"));
		searchMap.put("mappingDescription", mappingDescriptionMap);

		final LinkedHashMap<String, Object> sourceMessageIdMap = new LinkedHashMap<String, Object>();
		sourceMessageIdMap.put("filterType", FilterType.ALPHANUMERIC);
		sourceMessageIdMap.put("values",
				Arrays.asList("Lucas user import host"));
		searchMap.put("sourceMessage", sourceMessageIdMap);

		final LinkedHashMap<String, Object> destinationMessageIdMap = new LinkedHashMap<String, Object>();
		destinationMessageIdMap.put("filterType", FilterType.ALPHANUMERIC);
		destinationMessageIdMap.put("values",
				Arrays.asList("Lucas user import lucas"));
		searchMap.put("destinationMessage", destinationMessageIdMap);

		searchAndSortCriteria.setSearchMap(searchMap);

		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("mappingName", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(3);
		
		
		final List<Mapping> mappingList = this.mappingService
				.getMappingListBySearchAndSortCriteria(searchAndSortCriteria);
		Assert.notNull(mappingList);
		Assert.isTrue(mappingList.size() >= 1);
	}

	public void testSaveMappingForUpdate() {
		Mapping mappingTest = mappingService.getMappingByName("Transform inbound user details");
		Assert.isTrue(mappingTest.getMappingDescription().equals("Transform inbound user details"));
		mappingTest.setMappingDescription("Test Mapping Description");
		mappingService.saveMapping(mappingTest);
		Mapping mappingTestNew = mappingService.getMappingByName("Transform inbound user details");
		Assert.isTrue(mappingTestNew.getMappingDescription().equals("Test Mapping Description"));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testSaveMappingForCreate() {
		Mapping mappingTest = new Mapping(null, "Test Mapping Description", "Test Mapping Name", null, null, null, null, null, null); 
		mappingService.saveMapping(mappingTest);
		Mapping mappingTestNew = mappingService.getMappingByName("Test Mapping Name");
		Assert.isTrue(mappingTestNew.getMappingDescription().equals("Test Mapping Description"));
	}
	
	
	/**
	 * Method to test mapping delete by mapping id
	 */
	@Transactional
	@Test
	@Rollback(true)
	public void testDeleteMapping() {
		final boolean isDeleted = this.mappingService.deleteMapping(mappingId);
		junit.framework.Assert.assertTrue("Deletion of mapping Failed",
				isDeleted);
	}
}