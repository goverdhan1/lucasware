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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.dto.logging.LogMessageDTO;
import com.lucas.dto.logging.LogSeverity;
import com.lucas.dto.logging.LogSource;
import com.lucas.entity.about.About;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
public class LoggingControllerFunctionalTests {

	private static final Logger log = LoggerFactory.getLogger(LoggingControllerFunctionalTests.class);
	private MockMvc mockMvc;


	@Inject
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		// Setup mock MVC framework
		log.debug("***LoggingControllerFunctionalTests - Test Setup");
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLogSomethingAsTestUserAndSaveToFile() throws Exception {
		log.debug("***LoggingControllerFunctionalTests - Executing Test 1.0");
		ObjectMapper objectMapper = new ObjectMapper();

		LogMessageDTO message = new LogMessageDTO();
		message.setDeviceId("testDeviceId");
		message.setMessage("message from testLogSomething");
		message.setSeverity(LogSeverity.INFORMATION);
		message.setSource(LogSource.MOBILE_APP);
		message.setTimeStamp(LocalDateTime.now());
		message.setUsername("testUser");
		message.setWriteToFile(true);

		final String url = "/log/message";
		final ResultActions apiResultActions = this.mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(message))
				.contentType(MediaType.APPLICATION_JSON));

		String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();
		log.debug("***Logging Controller - JSON Returned: [" + jsonString + "]");

		JSONObject obj = new JSONObject(jsonString);
		Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
		Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
		Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testLogSomethingAsTestUserAndSaveToDatabase() throws Exception {
		log.debug("***LoggingControllerFunctionalTests - Executing Test 1.0");
		ObjectMapper objectMapper = new ObjectMapper();

		LogMessageDTO message = new LogMessageDTO();
		message.setDeviceId("testDeviceId");
		message.setMessage("message from testLogSomething");
		message.setSeverity(LogSeverity.INFORMATION);
		message.setSource(LogSource.MOBILE_APP);
		message.setTimeStamp(LocalDateTime.now());
		message.setUsername("testUser");
		message.setWriteToFile(false);

		final String url = "/log/message";
		final ResultActions apiResultActions = this.mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(message))
				.contentType(MediaType.APPLICATION_JSON));

		String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();
		log.debug("***Logging Controller - JSON Returned: [" + jsonString + "]");

		JSONObject obj = new JSONObject(jsonString);
		Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
		Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
		Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
	}

}
