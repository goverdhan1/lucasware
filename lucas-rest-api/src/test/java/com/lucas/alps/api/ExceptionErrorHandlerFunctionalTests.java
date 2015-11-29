package com.lucas.alps.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionErrorHandlerFunctionalTests extends AbstractControllerTests {

	@Inject
	private WebApplicationContext wac;	

	private MockMvc mockMvc; 
	private Map<String,String> map;
	
	@Before
	public void setup() {
		//Build  the MVC context
		this.mockMvc = webAppContextSetup(this.wac).build();
		map = new HashMap<String,String>();
		map.put("username", "jack123");
		map.put("password", "password");
	}
	
	@Test
	public void testAuthenticateException() throws Exception {
		this.mockMvc.perform(post("/authenticate").content("{\"username\":\"jack123\",\"password\":\"secret\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testWhenNoUsernameIsSetThenThrowException() throws Exception {

		MvcResult m = this.mockMvc.perform(post("/authenticate").content("{\"password\":\"secret\"}")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		Assert.assertTrue(m.getResponse().getContentAsString().contains("Invalid Username or Password"));
	}
	
	@Test

	public void testWhenNoPasswordIsSetThenThrowException() throws Exception {
	   MvcResult m = this.mockMvc.perform(post("/authenticate").content("{\"username\":\"jack123\"}")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

	   Assert.assertTrue(m.getResponse().getContentAsString().contains("Invalid Username or Password"));
	}
	
	@Test
	public void testWhenGETReqestThenThrowException() throws Exception {
	   MvcResult m = this.mockMvc.perform(get("/authenticate")).andReturn();
	   Assert.assertEquals("Request method 'GET' not supported", m.getResolvedException().getMessage());
	}
	
	@Test
	public void testWhenNoUsernameAndPasswordThenThrowException() throws Exception {

	   MvcResult m = this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)).andReturn();
	   Assert.assertTrue(m.getResponse().getContentAsString().contains("Could not read JSON:"));

	}
	
	@Test
	public void testWhenInvalidURLThenThrowNullPointerException() throws Exception {
		this.mockMvc.perform(post("/csk").param("username", "jack123").param("password", "secret")).andExpect(status().is(404));
	}
	
	// Accessing '/home' URL then access is granted to all the users
	// pattern defined in security-context.xml file
	@Test
	public void testWhenSecuredUrlIsHomeThenPermitAllGETRequest() throws Exception {
		this.mockMvc.perform(get("/home").param("username", "jack123").param("password", "secret")).andExpect(status().isOk());
	}
	
	@Test
	public void testWhenSecuredUrlIsHomeThenPermitAllPOSTRequest() throws Exception {
		this.mockMvc.perform(post("/home").param("username", "jack123").param("password", "secret")).andExpect(status().isOk());
	}
	
	// Accessing '/something' URL then access is granted to authenticated users
	// and the Request is GET.
	// pattern defined in security-context.xml file	
	@Test
	public void testWhenSecuredURLIsSomethingIsAuthenticatedWithGETRequest() throws Exception {
		this.mockMvc.perform(get("/something").param("username", "jack123").param("password", "secret")).andExpect(status().is(404));
	}
	
	@Test
	public void testWhenSecureURLPermitAllWithPOSTRequest() throws Exception {
		this.mockMvc.perform(post("/something").param("username", "jack123").param("password", "secret")).andExpect(status().is(404));
	}
	
	@Test
	public void testWhenSecureURLIsAuthenticatedWithPUTRequest() throws Exception {
		this.mockMvc.perform(put("/something/fop").param("username", "jack123").param("password", "secret")).andExpect(status().is(404));
	}
}
