package com.lucas.alps.api;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.UserView;
import com.lucas.services.util.HttpStatusCodes;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationControllerFunctionalTests extends AbstractControllerTests {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationControllerFunctionalTests.class);

	 @Test
     public void testWhenAuthenticateWithValidUserThenReturnAPIResponse() throws Exception {

		 	Map<String,String> map = new HashMap<String,String>();
		 	map.put("username", USERNAME);
		 	map.put("password", PASSWORD);
             ResultActions resultActions = this.mockMvc.perform(post(
                             "/authenticate").content(super.getJsonString(map))
                             .contentType(MediaType.APPLICATION_JSON));
             logger.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
             
             
             ApiResponse<UserView> apiResponse =  super.jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
             UserView userView = apiResponse.getData();
             Assert.assertNotNull(userView);
             Assert.assertNotNull(apiResponse.getToken());
             resultActions.andExpect(status().isOk()).andExpect(content().string(containsString(String.valueOf(userView.getUserName()))));
     }
	 
	 @Test
     public void testWhenAuthenticateURLWithValidUserThenReturnAPIResponse() throws Exception {

		 	Map<String,String> map = new HashMap<String,String>();
		 	map.put("username", USERNAME);
		 	map.put("password", PASSWORD);
            ResultActions resultActions = this.mockMvc.perform(
            		 post("/authenticate")
            		 	.content(getJsonString(map))
                             .contentType(MediaType.APPLICATION_JSON));
             logger.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
             
             
             ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
             UserView user = apiResponse.getData();
             Assert.assertNotNull(user);
             Assert.assertNotNull(apiResponse.getToken());
             resultActions.andExpect(status().isOk()).andExpect(content().string(containsString(String.valueOf(user.getUserName()))));
     }
	 
	 @Test
	 public void testWhenAuthenticateURLWithNullUsernameThenReturnAPIResponse() throws Exception {
		 	Map<String,String> map = new HashMap<String,String>(); 
		 	map.put("username", null);
		 	map.put("password", PASSWORD);
         ResultActions resultActions = this.mockMvc.perform(
         		 post("/authenticate")
         		 	.content(getJsonString(map))
                          .contentType(MediaType.APPLICATION_JSON));
          logger.debug("testWhenAuthenticateURLWithInvalidUserThenReturnNullAPIResponse: {}", resultActions.andReturn().getResponse().getContentAsString());


          ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
          UserView user = apiResponse.getData();
          Assert.assertNull(user);
          Assert.assertNull(apiResponse.getToken());
	 }

	 @Test
	 public void testWhenAuthenticateURLWithInvalidPasswordThenReturnAPIResponse() throws Exception {

		 String expectedCode = HttpStatusCodes.ERROR_1001;

		 String expectedStatus = "failure";
		 String expectedMessage = "Invalid Username or Password";
		 Map<String,String> map = new HashMap<String,String>(); 
		 	map.put("username", USERNAME);
		 	map.put("password", "wrongpassword");
         ResultActions resultActions = this.mockMvc.perform(
         		 post("/authenticate")
         		 	.content(getJsonString(map))
                          .contentType(MediaType.APPLICATION_JSON));
          logger.debug("testWhenAuthenticateURLWithInvalidPasswordThenReturnAPIResponse: {}", resultActions.andReturn().getResponse().getContentAsString());

          ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
          Assert.assertTrue(expectedCode.equals(apiResponse.getCode()));
          Assert.assertTrue(expectedStatus.equals(apiResponse.getStatus()));     
          Assert.assertTrue(expectedMessage.equals(apiResponse.getMessage()));
          UserView user = apiResponse.getData();
          Assert.assertNull(user);
          Assert.assertNull(apiResponse.getToken());
	 }
	 
	 @Test
	 public void testWhenNullUsernameAndPasswordThenReturnApiResponse() throws Exception {

		 String expectedCode = HttpStatusCodes.ERROR_1001;
		 String expectedStatus = "failure";
		 String expectedMessage = "Invalid Username or Password";
		 Map<String,String> map = new HashMap<String,String>(); 
		 	map.put("username", null);
		 	map.put("password", null);
         ResultActions resultActions = this.mockMvc.perform(
         		 post("/authenticate")
         		 	.content(getJsonString(map))
                          .contentType(MediaType.APPLICATION_JSON));
          logger.debug("testWhenAuthenticateURLWithInvalidPasswordThenReturnAPIResponse: {}", resultActions.andReturn().getResponse().getContentAsString());

          ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
          Assert.assertTrue(expectedCode.equals(apiResponse.getCode()));
          Assert.assertTrue(expectedStatus.equals(apiResponse.getStatus()));
          Assert.assertTrue(expectedMessage.equals(apiResponse.getMessage()));
          UserView user = apiResponse.getData();
          Assert.assertNull(user);
          Assert.assertNull(apiResponse.getToken());
	 }


}
