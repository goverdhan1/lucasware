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
package com.lucas.alps.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.MappingView;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.services.eai.MessageService;
import com.lucas.services.eai.TransformationService;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality test for exposed
 * the rest end points for EAI  Mapping.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MappingControllerFunctionalTests extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory
            .getLogger(MappingControllerFunctionalTests.class);
    
    private static final String USERNAME_JACK = "jack123";

	private static final String PASSWORD_JACK = "secret";

    @Inject
    MessageService messageService;
    @Inject
    TransformationService transformationService;

    /**
     * testGetAllMappingNames() provide the functionality for getting the list of
     * the mappings names form MappingController which intern uses MappingService and
     * MappingDao.
     *
     * @see com.lucas.alps.api.MappingController#getMappingNames()
     * @see com.lucas.services.eai.MappingService#getMappingsNames()
     * @see com.lucas.dao.eai.MappingDao#getMappingsNames()
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetAllMappingNames()throws Exception{
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/mappings/names";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Return "+actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1098"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("mappings names found from db"));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<List<String>> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<List<String>>>() {
        });
        final List<String> mappingNameList=apiResponse.getData();
        Assert.notNull(mappingNameList, "Mapping Name List is Null");
        Assert.isTrue(!mappingNameList.isEmpty(),"Mapping Name List is Empty");
    }


    /**
     * testGetMappingByNamesWhereMappingExists() provide the functionality for getting the
     * the mappings based on mapping name form MappingController which intern uses
     * MappingService and MappingDao.
     *
     * @see com.lucas.alps.api.MappingController#getMappingByName(String)
     * @see com.lucas.services.eai.MappingService#getMappingByName(String)
     * @see com.lucas.dao.eai.MappingDao#getMappingByName(String)
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetMappingByNamesWhereMappingExists()throws Exception{
        final String token = super.generateTokenWithAuthenticatedUser();
        final String mappingName="Transform inbound user details";
        final String url = "/mappings/"+mappingName+"/definitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns "+actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1101"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("mapping found in db for name"));
    }

    /**
     * testGetMappingByNamesWhereMappingNonExists() provide the functionality for getting the
     * the mappings based on mapping name form MappingController which intern uses
     * MappingService and MappingDao.
     *
     * note:provided mapping name is not exist in the db and mapping will not be found for that.
     *
     * @see com.lucas.alps.api.MappingController#getMappingByName(String)
     * @see com.lucas.services.eai.MappingService#getMappingByName(String)
     * @see com.lucas.dao.eai.MappingDao#getMappingByName(String)
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetMappingByNamesWhereMappingNonExists()throws Exception{
        final String token = super.generateTokenWithAuthenticatedUser();
        final String mappingName="Transform inbound user";
        final String url = "/mappings/"+mappingName+"/definitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns "+actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1102"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("mapping not found in db for name"));
    }


    /**
     * testGetMappingByNamesWhereMappingIsNull() provide the functionality for getting the
     * the mappings based on mapping name form MappingController which intern uses
     * MappingService and MappingDao.
     *
     * note:provided mapping name is null for which mapping will not be found for that.
     *
     * @see com.lucas.alps.api.MappingController#getMappingByName(String)
     * @see com.lucas.services.eai.MappingService#getMappingByName(String)
     * @see com.lucas.dao.eai.MappingDao#getMappingByName(String)
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetMappingByNamesWhereMappingIsNull()throws Exception{
        final String token = super.generateTokenWithAuthenticatedUser();
        final String mappingName=null;
        final String url = "/mappings/"+mappingName+"/definitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns "+actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1102"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("mapping not found in db for name"));
    }

    /**
     * testGetMappingByNamesWhereMappingIsEmpty() provide the functionality for getting the
     * the mappings based on mapping name form MappingController which intern uses
     * MappingService and MappingDao.
     *
     * note:provided mapping name is empty for which mapping will not be found for that.
     *
     * @see com.lucas.alps.api.MappingController#getMappingByName(String)
     * @see com.lucas.services.eai.MappingService#getMappingByName(String)
     * @see com.lucas.dao.eai.MappingDao#getMappingByName(String)
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetMappingByNamesWhereMappingIsEmpty()throws Exception{
        final String token = super.generateTokenWithAuthenticatedUser();
        final String mappingName="%20%";
        final String url = "/mappings/"+mappingName+"/definitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns "+actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1102"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("mapping not found in db for name"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveMappingForCreate() throws Exception {
        final Mapping mapping = this.getMappingObject();

        MappingView mappingView = new MappingView(mapping);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(mappingView);

        String token = generateTokenWithAuthenticatedUser();
        String url = "/mappings/definitions/save";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(mappingView))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        LOG.debug("**** MappingControllerFunctionalTests.testSaveMappingForCreate() **** mappingView = " +  super.getJsonString(mappingView));
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
        String expected ="2001::INFO::false::Mapping " + mapping.getMappingName() + " saved successfully";
        org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveMappingForUpdate() throws Exception {
        final Mapping mapping = this.getMappingObject();
        mapping.setMappingId(Long.valueOf(2));

        MappingView mappingView = new MappingView(mapping);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(mappingView);

        String token = generateTokenWithAuthenticatedUser();
        String url = "/mappings/definitions/save";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(mappingView))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        LOG.debug("**** MappingControllerFunctionalTests.testSaveMappingForUpdate() **** mappingView = " +  super.getJsonString(mappingView));
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
        String expected ="2003::INFO::false::Mapping " + mapping.getMappingName() + " updated successfully";
        org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
    }

    private Mapping getMappingObject() throws JsonParseException, JsonMappingException, IOException {
        Message destinationMessageId = messageService.getMessageByName("Lucas user import lucas");
        destinationMessageId.setEaiMessageDefinitions(new HashSet<MessageDefinition>());
        Message sourceMessageId = messageService.getMessageByName("Lucas user export lucas");
        sourceMessageId.setEaiMessageDefinitions(new HashSet<MessageDefinition>());
        Transformation fromTransformationId = transformationService.getTransformationById(1L);
        fromTransformationId.setEaiTransformationDefinitions(new HashSet<TransformationDefinition>());
        Transformation toTransformationId = transformationService.getTransformationById(2L);
        toTransformationId.setEaiTransformationDefinitions(new HashSet<TransformationDefinition>());

        Set<MappingPath> mappingPaths = new HashSet<MappingPath>();
        MappingPath MappingPath = new MappingPath(null, fromTransformationId, 1, toTransformationId);
        mappingPaths.add(MappingPath);

        Mapping mappingTest = new Mapping(destinationMessageId, "Test Mapping Description", "Test Mapping Name", sourceMessageId, 1, mappingPaths, null, null, null);

        return mappingTest;
    }
    
    
	@Test
	@Transactional
	@Rollback(true)
	public void testGetMappingListBySearchAndSortCriteria() throws Exception {

		final String token = super.getAuthenticatedToken(USERNAME_JACK,
				PASSWORD_JACK);

		testSaveMappingForCreate();
		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		final LinkedHashMap<String, Object> searchMap = new LinkedHashMap<String, Object>();
		final List<String> mappingNameList = new ArrayList<String>();
		mappingNameList.add("Test Mapping Name");
		searchMap.put("mappingName", mappingNameList);

		final LinkedHashMap<String, Object> mappingDescriptionMap = new LinkedHashMap<String, Object>();
		mappingDescriptionMap.put("filterType", FilterType.ALPHANUMERIC);
		mappingDescriptionMap.put("values",
				Arrays.asList("Test Mapping Description"));
		searchMap.put("mappingDescription", mappingDescriptionMap);

		searchAndSortCriteria.setSearchMap(searchMap);

		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("mappingName", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(3);

		LOG.debug("**** testGetMappingListBySearchAndSortCriteria: *******"
				+ super.getJsonString(searchAndSortCriteria));

		final String url = "/mappings/mappinglist/search";
		final ResultActions apiResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(searchAndSortCriteria))
				.contentType(MediaType.APPLICATION_JSON));

		LOG.debug("controller returns : {}", apiResultActions.andReturn()
				.getResponse().getContentAsString());
		String actualJsonString = apiResultActions.andReturn().getResponse()
				.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		org.junit.Assert.assertNotNull(actualJsonString);

		JSONObject obj = new JSONObject(actualJsonString);
		org.junit.Assert.assertEquals("status is not 200", "200",
				obj.get("code").toString());
		org.junit.Assert.assertEquals("status is not success", "success", obj
				.get("status").toString());

		org.junit.Assert
				.assertEquals("mapping is not processed successfully",
						"Request processed successfully", obj.get("message")
								.toString());
	}


	/**
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteMapping() throws Exception {

		final Mapping mapping = this.getMappingObjectForDelete();

		MappingView mappingView = new MappingView(mapping);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(mappingView);

		final String token = super.getAuthenticatedToken(USERNAME_JACK,
				PASSWORD_JACK);
		String url = "/mappings/delete";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(mappingView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn()
				.getResponse().getContentAsString());
		LOG.debug("**** MappingControllerFunctionTest.testDeleteMapping() **** mappingView = "
				+ super.getJsonString(mappingView));
		String jsonString = authResultActions.andReturn().getResponse()
				.getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString,
				new TypeReference<ApiResponse<Object>>() {
				});
		final JSONObject jsonObject = new JSONObject(jsonString);
		org.junit.Assert.assertTrue(jsonObject.has("status"));
		org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status")
				.equals("success"));
		org.junit.Assert.assertTrue(jsonObject.has("code"));
		org.junit.Assert.assertTrue("Invalid code", jsonObject.get("code")
				.equals("2005"));
		org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
	}
	    
	
	/**
	 * @return
	 */
	private Mapping getMappingObjectForDelete() {

		final Mapping mapping = new Mapping();
		mapping.setMappingId(1L);
		return mapping;
	}
	
}
