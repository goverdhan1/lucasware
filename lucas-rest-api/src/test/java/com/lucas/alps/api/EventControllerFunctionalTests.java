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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.EventHandlerView;
import com.lucas.alps.view.EventSearchByColumnsView;
import com.lucas.alps.view.EventView;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventEventHandler;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.services.eai.EventService;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

/**Test case class for the event controller for executing the e2e test case
 * @author Prafull
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class EventControllerFunctionalTests extends AbstractControllerTests{

	private static final Logger LOG = LoggerFactory.getLogger(EventControllerFunctionalTests.class);
	
	private static final Long USERID_JILL = 3L;

	private static final String USERNAME_JACK = "jack123";
	
	private static final String PASSWORD_JACK = "secret";
	
	@Inject
	EventService eventService; 
	
	@Inject
	QueueChannel userOutboundInputChannel;
	
	public EventControllerFunctionalTests() {
	}
	
	 @Test
	 public void testUserLogon() throws Exception {
		 //This will be completed when all the services are implemented
		// eventService.startUserLogonEvent(USERID_JILL, userOutboundInputChannel);
	 }
	 
	 @Test
	    @Transactional
	    public void testGetEventListBySearchAndSortCriteria() throws Exception {

	        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
	        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
	        final Map<String, Object> searchMap = new HashMap<String, Object>();

	        searchMap.put("eventName", new ArrayList<String>() {
	            {
	                add("Users data import");
	            }
	        });


	        searchAndSortCriteria.setSearchMap(searchMap);
	        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
	            {
	                put("eventName", SortType.ASC);
	            }
	        });

	        searchAndSortCriteria.setPageNumber(0);
	        searchAndSortCriteria.setPageSize(3);


	        final String url = "/events/eventlist/search";
	        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
	                .header("Authentication-token", token)
	                .content(super.getJsonString(searchAndSortCriteria))
	                .contentType(MediaType.APPLICATION_JSON));

	        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
	        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(Include.NON_NULL);
	        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	        org.junit.Assert.assertNotNull(actualJsonString);
	        final ApiResponse<EventSearchByColumnsView> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<EventSearchByColumnsView>>() {
	        });
	        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
	        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
	        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");
	        final EventSearchByColumnsView eventSearchByColumnsView = apiResponse.getData();
	        //Assert.isTrue(eventSearchByColumnsView.getTotalRecords() >= 4, "Event Records count is invalid ");
	        Assert.notNull(eventSearchByColumnsView.getEvents(), "Event list is null");
	        Assert.notEmpty(eventSearchByColumnsView.getEvents(), "Event list is empty ");
	        Assert.isTrue(eventSearchByColumnsView.getEvents().size() >= 1, "Event Records is not having number");

	    }
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveEventForCreate() throws Exception {
		final Event event = this.getEventObject();
		
		EventView eventView = new EventView(event);
		ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(eventView);
        
        String token = generateTokenWithAuthenticatedUser();
		String url = "/events/save";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(eventView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** EventControllerFunctionalTests.testSaveEvent() **** eventView = " +  super.getJsonString(eventView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		String expected ="1081::INFO::false::Event " + event.getEventName() + " saved successfully";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveEventForUpdate() throws Exception {
		final Event event = this.getEventObject();
		event.setEventId(Long.valueOf(2));
		
		EventView eventView = new EventView(event);
		ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(eventView);
        
        String token = generateTokenWithAuthenticatedUser();
		String url = "/events/save";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(eventView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** EventControllerFunctionalTests.testSaveEvent() **** eventView = " +  super.getJsonString(eventView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		String expected ="1084::INFO::false::Event " + event.getEventName() + " updated successfully";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteEvent() throws Exception {
		final Event event = new Event();
		event.setEventId(Long.valueOf(5));
		
		EventView eventView = new EventView(event);
		ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(eventView);
        
        String token = generateTokenWithAuthenticatedUser();
		String url = "/events/delete";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(eventView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** EventControllerFunctionalTests.testDeleteEventFor() **** eventView = " +  super.getJsonString(eventView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		String expected ="1083::INFO::false::Event " + event.getEventId() + " is Deleted Successfully";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
	}
	
	/**
     * testGetEvent() provide the functionality for testing
     * to get existing event
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetEvent() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/events/Users data import";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
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
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        org.junit.Assert.assertTrue(jsonObject.has("data"));
        final JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        org.junit.Assert.assertTrue(jsonObject1.has("eventId"));
        org.junit.Assert.assertNotNull("eventId is null",jsonObject1.get("eventId"));
        org.junit.Assert.assertTrue("Invalid eventId ",jsonObject1.get("eventId").equals(1));
        org.junit.Assert.assertTrue(jsonObject1.has("eventCategory"));
        org.junit.Assert.assertNotNull("eventCategory is null",jsonObject1.get("eventCategory"));
        org.junit.Assert.assertTrue("Invalid eventCategory ",jsonObject1.get("eventCategory").equals("Master data"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventDescription"));
        org.junit.Assert.assertNotNull("eventDescription is null ",jsonObject1.get("eventDescription"));
        org.junit.Assert.assertTrue("Invalid eventDescription ",jsonObject1.get("eventDescription").equals("Import user details to the lucas system"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventName"));
        org.junit.Assert.assertNotNull("eventName is null ",jsonObject1.get("eventName"));
        org.junit.Assert.assertTrue("Invalid eventName ",jsonObject1.get("eventName").equals("Users data import"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventSource"));
        org.junit.Assert.assertNotNull("eventSource is null ",jsonObject1.get("eventSource"));
        org.junit.Assert.assertTrue("Invalid eventSource ",jsonObject1.get("eventSource").equals("Host"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventSubCategory"));
        org.junit.Assert.assertNotNull("eventSubCategory is null ",jsonObject1.get("eventSubCategory"));
        org.junit.Assert.assertTrue("Invalid eventSubCategory ",jsonObject1.get("eventSubCategory").equals("Users Import"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventType"));
        org.junit.Assert.assertNotNull("eventType is null ",jsonObject1.get("eventType"));
        org.junit.Assert.assertTrue("Invalid eventType ",jsonObject1.get("eventType").equals("Inbound"));
        org.junit.Assert.assertTrue(jsonObject1.has("eventEventHandlers"));
        final JSONArray jsonArray=jsonObject1.getJSONArray("eventEventHandlers");
        for(int i=0;i<jsonArray.length();i++){
            final JSONObject object= jsonArray.getJSONObject(i);
            org.junit.Assert.assertNotNull("eventEventHandlerId is null ",object.get("eventEventHandlerId"));
            org.junit.Assert.assertNotNull("eventHandler is null ", object.get("eventHandler"));
            org.junit.Assert.assertNotNull("executionOrder is null ", object.get("executionOrder"));
            org.junit.Assert.assertTrue("seq is null ", object.has("seq"));
        }
    }
    
    @Test
	@Transactional
	@Rollback(true)
	public void testDeleteExistingEventHandler() throws Exception {
		final EventHandler eventHandler = new EventHandler();
		eventHandler.setEventHandlerId(Long.valueOf(1));
		
		EventHandlerView eventHandlerView = new EventHandlerView(eventHandler);
		ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValueAsString(eventHandlerView);
	    
	    String token = generateTokenWithAuthenticatedUser();
		String url = "/eventhandlers/delete";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(eventHandlerView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** EventControllerFunctionalTests.testDeleteEventHandler() **** eventHandlerView = " +  super.getJsonString(eventHandlerView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		String expected ="1089::INFO::false::Event Handler " + eventHandlerView.getEventHandlerId() + " is Deleted Successfully";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
	}
    
    @Test
	@Transactional
	@Rollback(true)
	public void testDeleteNonExistingEventHandler() throws Exception {
		final EventHandler eventHandler = new EventHandler();
		eventHandler.setEventHandlerId(Long.valueOf(5));
		
		EventHandlerView eventHandlerView = new EventHandlerView(eventHandler);
		ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValueAsString(eventHandlerView);
	    
	    String token = generateTokenWithAuthenticatedUser();
		String url = "/eventhandlers/delete";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(eventHandlerView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		LOG.debug("**** EventControllerFunctionalTests.testDeleteEventHandler() **** eventHandlerView = " +  super.getJsonString(eventHandlerView));
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		String expected ="2009::INFO::false::Event Handler " + eventHandlerView.getEventHandlerId() + " is not found";
		org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));
	}

	private final Event getEventObject() {
		final Event event = new Event();
		event.setEventName("Test Name");
		event.setEventDescription("Test Description");
		event.setEventCategory("Test category");
		event.setEventSource("Test Event");
		event.setEventSubCategory("Event sub category");
		event.setEventType("Test type");
		
		Set<EventEventHandler> eeHandlerSet = new HashSet<EventEventHandler>();
		
		final EventHandler eHandler = new EventHandler();
		eHandler.setEventHandlerId(Long.valueOf(1));
		
		EventEventHandler eeHandler = new EventEventHandler();
		eeHandler.setEventEventHandlerId(null);
		eeHandler.setSeq(2);
		eeHandler.setEventHandler(eHandler);
		eeHandlerSet.add(eeHandler);
		
		eeHandler = new EventEventHandler();
		eeHandler.setEventEventHandlerId(null);
		eeHandler.setSeq(2);
		eHandler.setEventHandlerId(Long.valueOf(2));
		eeHandler.setEventHandler(eHandler);
		eeHandlerSet.add(eeHandler);
		
		return event;
	}
}
