/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.alps.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.about.About;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
public class AboutControllerFunctionalTests {

	private static final Logger log = LoggerFactory.getLogger(AboutControllerFunctionalTests.class);
	private MockMvc mockMvc; 
	
	@Value("${buildNumberProperty}")
	private String expectedBuildNumber;
	
	@Value("${buildTimestamp}")
	private String expectedBuildTimestamp;
	
	@Inject
	private WebApplicationContext wac;
	
	@Before
	public void setUp() throws Exception {
		// Setup mock MVC framework
		log.debug("***AboutControllerFunctionalTest - Test Setup");
	    this.mockMvc = webAppContextSetup(this.wac).build();
	    
	    log.debug("***Expected Build Number: " + expectedBuildNumber);
	    log.debug("***Expected Build Timestamp: " + expectedBuildTimestamp);
	}

	@Test
	public void testBuildInformationApiResponse() throws Exception {
	    log.debug("***AboutControllerFunctionalTest - Executing Test 1.0");
		
	    ResultActions resultActions = mockMvc.perform(get("/about/buildinfo"));
		String jsonString = resultActions.andReturn().getResponse().getContentAsString();
		log.debug("***About Controller - JSON Returned: [" + jsonString + "]");
				
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<ApiResponse<About>> ref = new TypeReference<ApiResponse<About>>() {};
		ApiResponse<About> api = mapper.readValue(jsonString, ref);
		
		String actualBuildNumber = api.getData().getBuildNumber();
		String actualBuildTimestamp = api.getData().getBuildTimestamp();
		String status = api.getCode();
		
		log.debug("***About Controller - ApiResponse Status: " + status);
		log.debug("***About Controller - ApiResponse Build Number: " + actualBuildNumber);
		log.debug("***About Controller - ApiResponse Build Timestamp: " + actualBuildNumber);
				
		assertTrue(status.equals("200"));
		assertEquals(actualBuildNumber, expectedBuildNumber);
		assertEquals(actualBuildTimestamp, expectedBuildTimestamp);
	}
}
