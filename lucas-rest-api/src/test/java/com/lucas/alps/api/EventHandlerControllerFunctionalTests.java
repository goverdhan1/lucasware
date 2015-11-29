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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.EventHandlerView;
import com.lucas.alps.view.MappingView;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.entity.eai.transport.Transport;
import com.lucas.services.eai.MessageService;
import com.lucas.services.eai.TransformationService;
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

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality test for exposed
 * the rest end points for EAI EventHandler.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EventHandlerControllerFunctionalTests extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerControllerFunctionalTests.class);

    @Inject
    private MessageService messageService;


    /**
     * testGetEventHandlerByNamesWhereEventHandlerExists() provide the functionality for getting the
     * the event handler based on mapping name form EventHandlerController which intern uses
     * EventHandlerService and EventHandlerDao.
     *
     * @throws Exception
     * @see EventHandlerController#getEventHandlerByName(String)
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(String)
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetEventHandlerByNamesWhereEventHandlerExists() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String eventHandlerName = "Users data import";
        final String url = "/eventhandlers/" + eventHandlerName;
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns " + actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1105"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("event handler found in db for name"));
    }

    /**
     * testGetEventHandlerByNamesWhereEventHandlerNonExists() provide the functionality for getting the
     * the event handler based on mapping name form EventHandlerController which intern uses
     * EventHandlerService and EventHandlerDao.
     * <p/>
     * note:provided event handler name is not exist in the db and mapping will not be found for that.
     *
     * @throws Exception
     * @see EventHandlerController#getEventHandlerByName(String)
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(String)
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetEventHandlerByNamesWhereEventHandlerNonExists() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String eventHandlerName = "Transform inbound user";
        final String url = "/eventhandlers/" + eventHandlerName;
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns " + actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1106"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("event handler not found in db for name"));
    }


    /**
     * testGetEventHandlerByNamesWhereEventHandlerIsNull() provide the functionality for getting the
     * the event handler based on mapping name form EventHandlerController which intern uses
     * EventHandlerService and EventHandlerDao.
     * <p/>
     * note:provided event handler name is null for which mapping will not be found for that.
     *
     * @throws Exception
     * @see EventHandlerController#getEventHandlerByName(String)
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(String)
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetEventHandlerByNamesWhereEventHandlerIsNull() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String eventHandlerName = null;
        final String url = "/eventhandlers/" + eventHandlerName;
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns " + actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1106"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("event handler not found in db for name"));
    }

    /**
     * testGetEventHandlerByNamesWhereEventHandlerIsEmpty() provide the functionality for getting the
     * the event handler based on mapping name form EventHandlerController which intern uses
     * EventHandlerService and EventHandlerDao.
     * <p/>
     * note:provided event handler name is empty for which mapping will not be found for that.
     *
     * @throws Exception
     * @see EventHandlerController#getEventHandlerByName(String)
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(String)
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetEventHandlerByNamesWhereEventHandlerIsEmpty() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String eventHandlerName = "%20%";
        final String url = "/eventhandlers/" + eventHandlerName;
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        LOG.info("Mapping Controller Returns " + actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1106"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().contains("event handler not found in db for name"));
    }


    /**
     * testSaveOrUpdateEventHandler() provide the testing functionality for
     * the persisting event Handler type and its dependent objects.
     *
     * NOTE:Event Handler is Having all the dependency for saving
     *
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = false)
    @Rollback(value = true)
    public void testSaveOrUpdateEventHandler() throws Exception {
        final EventHandler eventHandler = this.getSampleEventHandler("insert");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/eventhandlers/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(new EventHandlerView(eventHandler)))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code", jsonObject.get("code").equals("1109"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message", jsonObject.get("message").equals("save or update operation for for event handler is successfully"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

    }


    /**
     * testUpdateEventHandler() provide the testing functionality for
     * the updating event Handler type and its dependent objects.
     *
     * NOTE:Event Handler is Having all the dependency for update
     *
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = false)
    @Rollback(value = true)
    public void testUpdateEventHandler() throws Exception {
        final EventHandler eventHandler = this.getSampleEventHandler("insert");
        final String token = super.generateTokenWithAuthenticatedUser();

        // Code for Testing the Insert
        final String url = "/eventhandlers/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(new EventHandlerView(eventHandler)))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code", jsonObject.get("code").equals("1109"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message", jsonObject.get("message").equals("save or update operation for for event handler is successfully"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

        // Code for Testing the Update
        final EventHandler updateEventHandler = this.getSampleEventHandler("update");
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(new EventHandlerView(updateEventHandler)))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject1.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code", jsonObject1.get("code").equals("1109"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message", jsonObject1.get("message").equals("save or update operation for for event handler is successfully"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(true));

    }


    /**
     * testSaveOrUpdateEventHandler() provide the testing functionality for
     * the persisting event Handler type and its dependent objects.
     *
     * NOTE:Event Handler is null
     *
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = false)
    @Rollback(value = true)
    public void testSaveOrUpdateEventHandlerWithNullEventHandler() throws Exception {
        final EventHandler eventHandler = null;
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/eventhandlers/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(new EventHandlerView(eventHandler)))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code", jsonObject.get("code").equals("1111"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message", jsonObject.get("message").equals("exception generated while save or update operation for for event handler"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * getSampleEventHandler() provide the functionality for getting
     * the dummy Event Handler to check the save for update functionality
     * for the event handler.
     *
     * @return instance of EventHandler containing data
     */
    private EventHandler getSampleEventHandler(final String initText) {
        final EventHandler eventHandler = new EventHandler();
        eventHandler.setEaiTransport(new Transport() {
            {
                setTransportName("File inbound async");
            }
        });

        eventHandler.setEaiMappingByInboundMappingId(new Mapping() {
            {
                setMappingId(1L);
            }
        });
        eventHandler.setEventHandlerName("Dummy-Event-Handler");
        eventHandler.setEventHandlerDescription(initText+" Dummy-Event-Handler-Description");
        eventHandler.setEventHandlerType(initText+" Dummy-Event-Handler-Type");
        eventHandler.setInboundFilePattern(initText+" Dummy-Event-Handler-Inbound-File-Pattern");
        eventHandler.setOutboundFilePattern(initText+" Dummy-Event-Handler-Outbound-File-Pattern");
        eventHandler.setUsageInEvent(1);
        return eventHandler;
    }

}
