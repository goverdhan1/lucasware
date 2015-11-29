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

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class TransportControllerFunctionalTests extends AbstractControllerTests {
	
	private static final Logger LOG = LoggerFactory.getLogger(TransportControllerFunctionalTests.class);
	
	/**
     * testGetAllTransports() provide the functionality for testing
     * to get all existing transport names
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
	public void testGetAllTransports() throws Exception {
    	final String token = generateTokenWithAuthenticatedUser();
        final String url = "/transports/transport";
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
        final JSONArray jsonArray=jsonObject.getJSONArray("data");
        org.junit.Assert.assertTrue("Transport names list size is not matched ", jsonArray.length() == 2);
	}

}
