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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.activiti.engine.impl.util.json.JSONArray;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.MessageSearchByColumnsView;
import com.lucas.alps.view.MessageSegmentView;
import com.lucas.alps.view.MessageView;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

/**
 * @author Naveen
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageControllerFunctionalTests extends AbstractControllerTests {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageControllerFunctionalTests.class);

	private static final String USERNAME_JOE = "joe123";

	private static final String PASSWORD_JOE = "secret";

	private static final Object FIRSTNAME_JACK = "Joe";

	private static final String PAGESIZE = "20";

	private static final String PAGENUMBER = "0";

	private static final String SEGMENT_NAME = "Control";

	private static final String SEGMENT_DESCRIPTION = "Control records for transfer order";

	private static final String SEGMENT_LENGTH = "125";
	
	private static final String MESSAGE_NAME = "Lucas user import lucas";
	
	private static final String MESSAGE_DESCRIPTION = "User's import inbound lucas message";
	
	private static final String MESSAGE_FIELD_NAME = "messageFieldName";
	
	private static final String MESSAGE_FIELD_DESCRIPTION = "messageFieldDescription";
	
	@Test
	@Transactional
	public void testGetSegmentListBySearchAndSortCriteria() throws Exception {

		final String token = super.getAuthenticatedToken(USERNAME_JOE,
				PASSWORD_JOE);

		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		final LinkedHashMap<String, Object> searchMap = new LinkedHashMap<String, Object>();
		final List<String> segmentNameList = new ArrayList<String>();
		segmentNameList.add("Control");
		searchMap.put("segmentName", segmentNameList);

		final LinkedHashMap<String, Object> segmentDescriptionMap = new LinkedHashMap<String, Object>();
		segmentDescriptionMap.put("filterType", FilterType.ALPHANUMERIC);
		segmentDescriptionMap.put("values",
				Arrays.asList("Control records for transfer order"));
		searchMap.put("segmentDescription", segmentDescriptionMap);

		final LinkedHashMap<String, Object> lengthMap = new LinkedHashMap<String, Object>();
		lengthMap.put("filterType", FilterType.NUMERIC);
		lengthMap.put("values", Arrays.asList("0", "300"));
		searchMap.put("segmentLength", lengthMap);

		searchAndSortCriteria.setSearchMap(searchMap);

		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("segmentName", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(3);

		LOG.debug("**** testGetSegmentrListBySearchAndSortCriteria: *******"
				+ super.getJsonString(searchAndSortCriteria));

		System.out
				.println("**** testGetSegmentrListBySearchAndSortCriteria: *******"
						+ super.getJsonString(searchAndSortCriteria));

		final String url = "/segments/segmentlist/search";
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
		Assert.isTrue(obj.get("code").toString().equals("200"),
				"status is not 200");
		Assert.isTrue(obj.get("status").toString().equals("success"),
				"status is not success");
		Assert.isTrue(
				obj.get("message").toString()
						.equals("Request processed successfully"),
				"message is not processed successfully");
		Assert.isTrue(obj.get("data").toString().contains(SEGMENT_NAME));
		Assert.isTrue(obj.get("data").toString().contains(SEGMENT_DESCRIPTION));
		Assert.isTrue(obj.get("data").toString().contains(SEGMENT_LENGTH));
	}

    @Test
	@Transactional
	@Rollback(true)
	public void testSaveSegment() throws Exception{

        final Segments segment = this.getSegmentObject();

        MessageSegmentView segmentView = new MessageSegmentView(segment);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(segmentView);

        final String token = super.getAuthenticatedToken(USERNAME_JOE,
				PASSWORD_JOE);
        String url = "/segments/definitions/save";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(segmentView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** MessageControllerFunctionTest.testSaveSegment() **** segmentView = " +  super.getJsonString(segmentView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

		/*String expected ="1012::INFO::success";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.getStatus()));
*/
		 final JSONObject jsonObject = new JSONObject(jsonString);
	        org.junit.Assert.assertTrue(jsonObject.has("status"));
	        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
	        org.junit.Assert.assertTrue(jsonObject.has("code"));
	        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1090"));
	        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }


	/**
	 * @return
	 */
	private Segments getSegmentObject() {
		
		final Segments segment = new Segments();
		segment.setSegmentLength(100);
		segment.setSegmentName("Test");
		segment.setSegmentDescription("Test record for segment persist");
		
		SegmentDefinition segmentDefinition = new SegmentDefinition();
		
		segmentDefinition.setSegmentFieldName("Test_definition");
		segmentDefinition.setSegmentFieldDescription("Test Record for segment definition persist");
		segmentDefinition.setSegmentFieldSeq(1);
		segmentDefinition.setSegmentFieldType("String");
		segmentDefinition.setSegmentFieldStart(1);
		segmentDefinition.setSegmentFieldEnd(50);
		
		
		
		Set<SegmentDefinition> eaiSegmentDefinition = new HashSet<SegmentDefinition>();
		eaiSegmentDefinition.add(segmentDefinition);
		
		
		segment.setEaiSegmentDefinitions(eaiSegmentDefinition);
		
		MessageDefinition messageDefinition =  new MessageDefinition();
		
		//messageDefinition.setMessageFieldLength(100);
		messageDefinition.setMessageFieldId(1L);
		
		Set<MessageDefinition> eaiMessageDefinition = new HashSet<MessageDefinition>();
		//eaiMessageDefinition.add(messageDefinition);
		segment.setEaiMessageDefinitions(eaiMessageDefinition);
		
		return segment;
	}

    @Test
    @Transactional
    public void testGetAllMessagesBySearchAnsSort() throws Exception {

        SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("messageName", new ArrayList<String>() {
                            {
                                add("%Lucas user%");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("messageName", SortType.ASC);
                    }
                });
            }
        };
      
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/messages/messagelist/search";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Message Data Json is Null");
        resultActions.andExpect(status().isOk());
        LOG.info(jsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<MessageSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<MessageSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");
        final MessageSearchByColumnsView messageSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(messageSearchByColumnsView.getTotalRecords() >= 4, "Message Records count is invalid ");
        Assert.notNull(messageSearchByColumnsView.getMessageList(), "Message list is null");
        System.out.println("messageSearchByColumnsView.getMessageList().size() " + messageSearchByColumnsView.getMessageList().size());
        Assert.isTrue(messageSearchByColumnsView.getMessageList().size() >= 4, "Message Records is not having expected Messages ");
        for (Message message : messageSearchByColumnsView.getMessageList()) {
            Assert.notNull(message.getMessageName(), "Message Name Id is null");
            Assert.notNull(message.getMessageFormat(), "Message Format is null");
        }
    }
    
    /**
     * Test method to get Message and MessageDefinition details from MessageName
     * 
     **/
    @Test
    @Transactional(readOnly=true)
    public void testGetMessageandMessageDefinition() throws Exception {

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/messages/Lucas user import lucas/definitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Message Data Json is Null");
        resultActions.andExpect(status().isOk());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<MessageView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<MessageView>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final MessageView messageView = apiResponse.getData();
        JSONObject obj = new JSONObject(jsonString);
        
         Assert.isTrue(messageView.getMessageName().equals(MESSAGE_NAME) , "Message Name is not correct");
         Assert.isTrue(messageView.getMessageDescription().equals(MESSAGE_DESCRIPTION) , "Message Description is not correct");
         Assert.isTrue(messageView.getEaiMessageDefinitions().size() >= 0, "Message Records is not having expected Definitions");
 		 Assert.isTrue(obj.get("data").toString().contains(MESSAGE_FIELD_NAME));
 		 Assert.isTrue(obj.get("data").toString().contains(MESSAGE_FIELD_DESCRIPTION));

        }

	/**
	 * @throws Exception
	 */
	@Test
	@Transactional
	public void testSegmentDefinitionsForSegment() throws Exception {
		final String token = super.getAuthenticatedToken(USERNAME_JOE,
				PASSWORD_JOE);
		final String url = "/segments/Control/definitions";
		final ResultActions resultActions = this.mockMvc.perform(get(url)
				.header("Authentication-token", token).accept(
						MediaType.APPLICATION_JSON));
		final String actualJsonString = resultActions.andReturn().getResponse()
				.getContentAsString();
		LOG.debug("controller returns : {}", actualJsonString);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		final JSONObject jsonObject = new JSONObject(actualJsonString);
		org.junit.Assert.assertTrue(jsonObject.has("status"));
		org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
		org.junit.Assert.assertTrue(jsonObject.has("code"));
		org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
		org.junit.Assert.assertTrue(jsonObject.has("message"));
		org.junit.Assert.assertTrue(jsonObject.get("message").equals(
				"Request processed successfully"));
		org.junit.Assert.assertTrue(jsonObject.has("data"));
		 JSONObject jsonObject1 = jsonObject.getJSONObject("data");
		 System.out.println("***************************" +jsonObject1.toString());
		org.junit.Assert.assertTrue(jsonObject1.has("segmentLength"));
		org.junit.Assert.assertNotNull("SegmentLength is null",
				jsonObject1.get("segmentLength"));
		org.junit.Assert.assertTrue(jsonObject1.has("segmentDescription"));
		org.junit.Assert.assertNotNull("SegmentDescription is null ",
				jsonObject1.get("segmentDescription"));
		 JSONArray jsonArray = jsonObject1
				.getJSONArray("segmentDefinitions");
		for (int i = 0; i < jsonArray.length(); i++) {
			final JSONObject object = jsonArray.getJSONObject(i);
			org.junit.Assert.assertNotNull("Segment Field Id is null ",
					object.get("segmentFieldId"));
			org.junit.Assert.assertNotNull("Segment Field Name is null ",
					object.get("segmentFieldName"));
			org.junit.Assert.assertNotNull(
					"Segment Field Description is null ",
					object.get("segmentFieldDescription"));
			org.junit.Assert.assertNotNull("Segment Length is null ",
					object.get("segmentFieldLength"));
			org.junit.Assert.assertNotNull("Segment Field Sequence is null ",
					object.get("segmentFieldSeq"));
		}
	}
	
    @Test
	@Transactional
	@Rollback(true)
	public void testDeleteSegment() throws Exception{

        final Segments segment = this.getSegmentObjectForDelete();

        MessageSegmentView segmentView = new MessageSegmentView(segment);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(segmentView);

        final String token = super.getAuthenticatedToken(USERNAME_JOE,
				PASSWORD_JOE);
        String url = "/segments/delete";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(segmentView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** MessageControllerFunctionTest.testDeleteSegment() **** segmentView = " +  super.getJsonString(segmentView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		 final JSONObject jsonObject = new JSONObject(jsonString);
	        org.junit.Assert.assertTrue(jsonObject.has("status"));
	        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
	        org.junit.Assert.assertTrue(jsonObject.has("code"));
	        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1092"));
	        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }
    
    /**
	 * @return
	 */
	private Segments getSegmentObjectForDelete() {
		
		final Segments segment = new Segments();
		segment.setSegmentId(1L);
		return segment;
	}
	
	/**
	 * getAllMessageNames() provide the functionality for testing
	 * to get all existing message names
	 * @throws Exception
	 */
	@Test
	@Transactional(readOnly = true)
	public void testGetAllMessageNames() throws Exception {
		final String token = generateTokenWithAuthenticatedUser();
		final String url = "/messages/names";
		final ResultActions resultActions = this.mockMvc.perform(get(url)
				.header("Authentication-token", token).accept(
						MediaType.APPLICATION_JSON));
		final String actualJsonString = resultActions.andReturn().getResponse()
				.getContentAsString();
		LOG.debug("controller returns : {}", actualJsonString);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		final JSONObject jsonObject = new JSONObject(actualJsonString);
		org.junit.Assert.assertTrue(jsonObject.has("status"));
		org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
		org.junit.Assert.assertTrue(jsonObject.has("code"));
		org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
		org.junit.Assert.assertTrue(jsonObject.has("message"));
		org.junit.Assert.assertTrue(jsonObject.get("message").equals(
				"Request processed successfully"));
		org.junit.Assert.assertTrue(jsonObject.has("data"));
		final JSONArray jsonArray = jsonObject.getJSONArray("data");
		org.junit.Assert.assertTrue("Message names list size is not matched ",
				jsonArray.length() == 4);
	}
}
