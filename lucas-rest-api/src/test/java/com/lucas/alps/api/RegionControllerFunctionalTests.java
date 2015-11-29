/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.alps.api;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.Region;
import com.lucas.services.region.RegionService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml") })
public class RegionControllerFunctionalTests {

	private static final Logger logger = LoggerFactory
			.getLogger(RegionControllerFunctionalTests.class);

	@Inject
	private WebApplicationContext wac;

	@Inject
	private RegionService regionService;

	private MockMvc mockMvc;

	private Region region;

	private Region regionobj;

	public static byte[] convertObjectToJsonBytes(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	public static ApiResponse<Region> jsonToObject(
			String jsonString) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.readValue(jsonString,
				new TypeReference<ApiResponse<Region>>() {
		});
	}

	@Before
	public void setup(){
		this.mockMvc = webAppContextSetup(this.wac).build();
		region = new Region();
		region.setRegionName("Hyd");
	}

	@Test
	public void testWhenNewRegionThenCreate() throws Exception {

		ResultActions resultActions = this.mockMvc.perform(post(
				"/region/").content(convertObjectToJsonBytes(region))
				.contentType(MediaType.APPLICATION_JSON));
		logger.debug("controller returns : "
				+ resultActions.andReturn().getResponse().getContentAsString());
		ApiResponse<Region> apiResponse = (ApiResponse<Region>) jsonToObject(resultActions
				.andReturn().getResponse().getContentAsString());
		region = apiResponse.getData();
		resultActions.andExpect(status().isOk()).andExpect(content().string(containsString(String.valueOf(region.getRegionName()))));

	}
	@After
	public void destroy(){
		
		regionService.deleteRegion(region);
	}

	@Test
	public void testToDeleteRegion() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
		region = new Region();
		region.setRegionName("Hydd");
		regionobj = regionService.createRegion(region);
		ResultActions resultActions = this.mockMvc.perform(delete(
				"/region/" + regionobj.getRegionId()).accept(
						MediaType.APPLICATION_JSON));
		resultActions.andExpect(status().isOk());
	}	


	@Test
	public void testToUpdateTheRegion() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
		region = new Region();
		region.setRegionName("Hyd");
		regionobj = regionService.createRegion(region);
		regionobj.setRegionName("warangal");
		ResultActions resultActions = this.mockMvc.perform(put(
				"/region/").content(convertObjectToJsonBytes(region))
				.contentType(MediaType.APPLICATION_JSON));
		resultActions.andExpect(status().isOk());
		regionService.deleteRegion(region);
	}
	@Test
	public void testWhenUrlIswrongThenThrow404Error() throws Exception {
		
		ResultActions resultActions = this.mockMvc.perform(get("/region")
				.accept(MediaType.APPLICATION_JSON));
		resultActions.andExpect(status().is(404));
	}	
	

}