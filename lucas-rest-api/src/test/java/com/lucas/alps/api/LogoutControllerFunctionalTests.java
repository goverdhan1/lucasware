package com.lucas.alps.api;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.UserView;

@RunWith(SpringJUnit4ClassRunner.class)
public class LogoutControllerFunctionalTests extends AbstractControllerTests {
	
    private static final Logger LOG = LoggerFactory.getLogger(LogoutControllerFunctionalTests.class);
	 
    @Test
    public void testLogoutApiResponseWithToken() throws Exception {
        LOG.debug("***Running Test: testLogoutApiResponseWithToken()");
 
        String testToken = "amFjazEyMyEhITE0MDYxMTI0NjQ4OTUhISE3b8+hW+83o9eMnj9e3XTM";
        
        String expectedCode = "200";
        String expectedStatus = "success";
        String expectedMessage = "Logout Successfull";
        String expectedToken = "";
        
        String actualCode;
        String actualStatus;
        String actualMessage;
        String actualToken;
        
        ResultActions resultActions = this.mockMvc.perform(post("/logout").header("Authentication-token", testToken));
        LOG.debug("***Request Token [" + resultActions.andReturn().getRequest().getHeader("Authentication-token") + "]");
        LOG.debug("***Response Token [" + resultActions.andReturn().getResponse().getHeader("Authentication-token") + "]");
        LOG.debug("***Json [" + resultActions.andReturn().getResponse().getContentAsString() + "]");
        
        ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
        actualCode = apiResponse.getCode();
        actualStatus = apiResponse.getStatus();
        actualMessage = apiResponse.getMessage();
        actualToken = resultActions.andReturn().getResponse().getHeader("Authentication-token");
        
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedCode, actualCode));
        assertTrue(expectedCode.equals(actualCode));
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedStatus, actualStatus));
        assertTrue(expectedStatus.equals(actualStatus));     
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedMessage, actualMessage));
        assertTrue(expectedMessage.equals(actualMessage));
        
        LOG.debug("***Asserting for empty response header token");
        assertTrue(expectedToken.equals(actualToken));
             
        UserView user = apiResponse.getData();
        assertNull(user);
    }
 
    @Test
    public void testLogoutApiResponseEmptyToken() throws Exception {
        LOG.debug("***Running Test: testLogoutApiResponseEmptyToken");
        
        String testToken = "";
        
        String expectedCode = "200";
        String expectedStatus = "success";
        String expectedMessage = "Logout Successfull";
        String expectedToken = "";
        
        String actualCode;
        String actualStatus;
        String actualMessage;
        String actualToken;
        
        ResultActions resultActions = this.mockMvc.perform(post("/logout").header("Authentication-token", testToken));
        LOG.debug("***Request Token [" + resultActions.andReturn().getRequest().getHeader("Authentication-token") + "]");
        LOG.debug("***Response Token [" + resultActions.andReturn().getResponse().getHeader("Authentication-token") + "]");
        LOG.debug("***Json [" + resultActions.andReturn().getResponse().getContentAsString() + "]");
        
        ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
        actualCode = apiResponse.getCode();
        actualStatus = apiResponse.getStatus();
        actualMessage = apiResponse.getMessage();
        actualToken = resultActions.andReturn().getResponse().getHeader("Authentication-token");
        
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedCode, actualCode));
        assertTrue(expectedCode.equals(actualCode));
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedStatus, actualStatus));
        assertTrue(expectedStatus.equals(actualStatus));     
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedMessage, actualMessage));
        assertTrue(expectedMessage.equals(actualMessage));
        
        LOG.debug("***Asserting for empty response header token");
        assertTrue(expectedToken.equals(actualToken));
             
        UserView user = apiResponse.getData();
        assertNull(user);
    }
    
    @Test
    public void testLogoutApiResponseNoToken() throws Exception {
        LOG.debug("***Running Test: testLogoutApiResponseNoToken");
        
        String expectedCode = "200";
        String expectedStatus = "success";
        String expectedMessage = "Logout Successfull";
        String expectedToken = "";
        
        String actualCode;
        String actualStatus;
        String actualMessage;
        String actualToken;
        
        ResultActions resultActions = this.mockMvc.perform(post("/logout"));
        LOG.debug("***Request Token [" + resultActions.andReturn().getRequest().getHeader("Authentication-token") + "]");
        LOG.debug("***Response Token [" + resultActions.andReturn().getResponse().getHeader("Authentication-token") + "]");
        LOG.debug("***Json [" + resultActions.andReturn().getResponse().getContentAsString() + "]");
        
        ApiResponse<UserView> apiResponse = (ApiResponse<UserView>) jsonToObject(resultActions.andReturn().getResponse().getContentAsString());
        actualCode = apiResponse.getCode();
        actualStatus = apiResponse.getStatus();
        actualMessage = apiResponse.getMessage();
        actualToken = resultActions.andReturn().getResponse().getHeader("Authentication-token");
        
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedCode, actualCode));
        assertTrue(expectedCode.equals(actualCode));
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedStatus, actualStatus));
        assertTrue(expectedStatus.equals(actualStatus));     
         
        LOG.debug(String.format("***Asserting [%s] against [%s]", expectedMessage, actualMessage));
        assertTrue(expectedMessage.equals(actualMessage));
        
        LOG.debug("***Asserting for empty response header token");
        assertTrue(expectedToken.equals(actualToken));
             
        UserView user = apiResponse.getData();
        assertNull(user);
    }
}