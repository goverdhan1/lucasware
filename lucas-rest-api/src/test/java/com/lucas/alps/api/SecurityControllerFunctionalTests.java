package com.lucas.alps.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class SecurityControllerFunctionalTests extends AbstractControllerTests{
	
	private static final Logger LOG = LoggerFactory.getLogger(SecurityControllerFunctionalTests.class);
	private static final String USERNAME_JACK = "jack123";
    private static final String PASSWORD_JACK="secret";
	private static final String GET_PERMISSIONS_BY_CATEGORIES_FILE_PATH = "json/GetPermissionsByCategories.json";
	
	@Test
	public void testGetAllPermissionsByCategories() throws Exception {

		final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
		final String url = "/permissions";
		final ResultActions apiResultActions = this.mockMvc.perform(get(url)
				.header("Authentication-token", token)
		        .contentType(MediaType.APPLICATION_JSON));
		
		LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
		String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
		LOG.debug(actualJsonString);
		Assert.notNull(actualJsonString, "/permissions Resulted Json is Null");
		Assert.isTrue(compareJson(actualJsonString, GET_PERMISSIONS_BY_CATEGORIES_FILE_PATH), "/permissions Resulted Json is not up to the expectations");
		
//		org.junit.Assert.assertTrue("/permissions Resulted Json is not up to the expectations, expected value " + + ", actually got: " + 
//				widgetDefinitionsByCategoryMap.size(), 
//				
//				compareJson(actualJsonString, GET_PERMISSIONS_BY_CATEGORIES_FILE_PATH));
	}
}
