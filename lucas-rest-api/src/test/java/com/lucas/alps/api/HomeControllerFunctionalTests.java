package com.lucas.alps.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.lucas.services.benchmark.BenchmarkService;


/**
 * Reference Implementation of Spring MVC Testing based on
 * https://github.com/SpringSource/spring-framework/tree/master/spring-test-mvc/src/test/java/org/springframework/test/web/servlet/samples
 * 
 * @author Pankaj Tandon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/META-INF/spring/api-bootstrap-context.xml", "classpath:mock-services-context.xml"})

public class HomeControllerFunctionalTests { 
	@Inject
	private WebApplicationContext wac;	

	private BenchmarkService benchmarkService;
	
	private MockMvc mockMvc; 

	@Before
	public void setup() {
		//Build  the MVC context
		this.mockMvc = webAppContextSetup(this.wac).build();		
		//Pull the mocked bean from the loaded context
		benchmarkService = wac.getBean("benchmarkService", BenchmarkService.class);
		//and set behavior
		when(benchmarkService.authenticate("bob", "doesn't matter")).thenReturn(true);
	}
	
	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/home"))
			.andExpect(model().attributeExists("serverTime"))
			.andExpect(model().attributeExists("inOrOut"))
			.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
	}
}
