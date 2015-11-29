package com.lucas.alps.api;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.UserView;
import com.lucas.services.user.UserService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebAppConfiguration
@ContextConfiguration(locations={"/META-INF/spring/api-bootstrap-context.xml"})
public class AbstractControllerTests {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractControllerTests.class);
	protected ResultActions resultActions = null;
	
	@Inject
	protected WebApplicationContext wac;	
	
	@Inject
	private FilterChainProxy springSecurityFilterChain;
	
	protected MockMvc mockMvc; 


	protected UserService userService;
	protected String USERNAME = "jack123";
	protected String PASSWORD = "secret";

	@Before
	public void setup() throws JsonProcessingException {
		//Build  the MVC context
		this.mockMvc = webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();

		userService = wac.getBean("userService", UserService.class);

	}
	
	public static String getJsonString(Object object) throws IOException {
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.setSerializationInclusion(Include.NON_NULL);
		 return mapper.writeValueAsString(object);
	}
	
	public static ApiResponse<UserView> jsonToObject(String jsonString) throws IOException {
	     ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);
	     return mapper.readValue(jsonString, new TypeReference<ApiResponse<UserView>>() { });
	}

	public String generateTokenWithAuthenticatedUser() throws Exception,
	IOException, UnsupportedEncodingException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", USERNAME);
		map.put("password", PASSWORD);
		ResultActions authResultActions = this.mockMvc.perform(post(
				"/authenticate").content(getJsonString(map))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		ApiResponse authResponse =  jsonToObject(authResultActions.andReturn().getResponse().getContentAsString());
		String token = authResponse.getToken().trim();	    	
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		return token;
	}
	
	public boolean compareJson(String actualJsonString, String filePath) throws JsonProcessingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualTree = mapper.readTree(actualJsonString);
		
		Resource expectedJsonFileResource = new ClassPathResource(filePath);
		JsonNode expectedTree = mapper.readTree(expectedJsonFileResource.getFile());
		return actualTree.equals(expectedTree);
	}

    public final String getAuthenticatedToken(final String userName, final String userPwd) throws Exception {

        final ResultActions authResultActions = this.mockMvc.perform(post(
                "/authenticate").content(getJsonString(new HashMap<String, String>() {
            {
                put("username", userName);
                put("password", userPwd);
            }
        }))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final ApiResponse authResponse = jsonToObject(authResultActions.andReturn().getResponse().getContentAsString());
        final String token = authResponse.getToken().trim();
        return token;
    }


    public class  PostThreads implements Callable<Object> {

        private String serviceUrl;
        private String token;
        private MediaType mediaType;
        private Object data;

        public PostThreads(String serviceUrl, String token, MediaType mediaType, Object data) {
            this.serviceUrl = serviceUrl;
            this.token = token;
            this.mediaType = mediaType;
            this.data = data;
        }

        @Override
        public MockHttpServletResponse call() throws Exception {
            final ResultActions apiResultActions = mockMvc.perform(post(serviceUrl)
                    .header("Authentication-token", token)
                    .content(getJsonString(data))
                    .contentType(mediaType));
            LOG.debug("Result From controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
            return apiResultActions.andReturn().getResponse();
        }
    }

}
