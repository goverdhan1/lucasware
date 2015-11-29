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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiRequest;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.CanvasView;
import com.lucas.alps.view.OpenUserCanvasesView;
import com.lucas.alps.view.UserView;
import com.lucas.dto.user.MultiUserDTO;
import com.lucas.dto.user.UserFormFieldsDTO;
import com.lucas.dto.user.UsersAssignedGroupsDTO;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.*;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.LucasDateRange;
import com.lucas.services.search.LucasNumericRange;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.services.ui.UIService;
import com.lucas.services.user.ShiftService;
import com.lucas.services.user.UserService;
import com.lucas.services.user.WmsUserService;
import com.lucas.services.util.HttpStatusCodes;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class UserControllerFunctionTests extends AbstractControllerTests{
	

	private static final Logger LOG = LoggerFactory.getLogger(UserControllerFunctionTests.class);

	private static final String USERNAME_JACK = "jack123";
	
	private static final String USERNAME_JILL = "jill123";
	
	private static final String USERNAME_ADMIN = "admin123";

    private static final String PASSWORD_JACK="secret";

	private static final Object FIRSTNAME_JACK = "Jack";
	
	private static final String PAGESIZE = "20";
	
	private static final String PAGENUMBER = "0";	
	
	private static final String USER_SEARCH_BY_COLUMNS_FILE_PATH = "json/UserSearchByColumns.json";
	
	private static final String USER_PERMISSION_GROUPS_DATAURL_FILE_PATH = "json/UserGroupsPermissionsDataUrl.json";

    private static final String USER_PERMISSION_GROUP_LIST_FILE_PATH="json/UserGroupPermissionListData.json";
    
	@Inject
	private UIService uIService;

    @Inject
    private ShiftService shiftService;

    @Inject
    private WmsUserService wmsUserService;

    @Inject
    private UserService userService;


    @Test
    public void testGetActiveUserNameList() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/usernames";
        final ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JACK));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JILL));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_ADMIN));
    }

	 @Test
     public void testGetUserFavoriteCanvasesListWithoutLogin() throws Exception {
		 
		 resultActions = this.mockMvc.perform(get("/users/jack123/favoritecanvases").accept(MediaType.APPLICATION_JSON));
		 
		 ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);
	     ApiResponse<UserView> apiResponse =  super.jsonToObject(resultActions.andReturn().getResponse().getContentAsString());

	     LOG.debug("controller returns cdoe : {}", apiResponse.getCode());
		 
		 Assert.isTrue(apiResponse.getCode().trim().equals(HttpStatusCodes.ERROR_401));
 
	 }


	 @Test
     public void testGetUserWithLogin() throws Exception {
		 
		 String token = generateTokenWithAuthenticatedUser();
		 resultActions = this.mockMvc.perform(get("/users/"+USERNAME_JACK).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
		 ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);
	     ApiResponse<UserView> apiResponse = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserView>>() { });
	     
	     UserView user = apiResponse.getData();
	     
		 LOG.debug("controller returns username : {}", resultActions.andReturn().getResponse().getContentAsString());
		 
		 Assert.isTrue(apiResponse.getCode().trim().equals(HttpStatusCodes.SUCCESS_200));
		 Assert.isTrue(user.getFirstName().equals(FIRSTNAME_JACK));
 
	 }
	 
	 @Test
     public void testGetUsersCountWithLogin() throws Exception {
		 
		 
		String token = generateTokenWithAuthenticatedUser();
  		String url = "/users/userscount";
  		resultActions = this.mockMvc.perform(get(url)
  				.header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());

		String jsonString = resultActions.andReturn().getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		ApiResponse<String> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<String>>() { });

		String usercount =   apiResponse.getData();
		Assert.isTrue(usercount != null, "userCount returned is null. It should have a non-null value!");
		int uc = Integer.parseInt(usercount);
		
		
		org.junit.Assert.assertTrue("User count, should be > 198 because there are at least 198 users created via liquibase. Current userCount: " + uc, uc >= 198);

         	
	}

	    @Test
		@Transactional
		@Rollback(true)
		public void testSaveUser() throws Exception{

            final User user = this.getUserObject();

            UserView userView = new UserView(user);
            ObjectMapper mapper = new ObjectMapper();
            String userViewString = super.getJsonString(userView);

			String token = generateTokenWithAuthenticatedUser();
			String url = "/users/save";
			ResultActions authResultActions = this.mockMvc.perform(post(url)
					.header("Authentication-token", token)
					.content(userViewString)
					.contentType(MediaType.APPLICATION_JSON));
			LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
			LOG.debug("**** UserControllerFunctionTest.testSaveUser() **** userView = " + super.getJsonString(userView));
			String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

			String expected ="1012::INFO::false::User " + user.getUserName() + " saved successfully";
			org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s", apiResponse.getCode(), apiResponse.getLevel(), apiResponse.isExplicitDismissal(), apiResponse.getMessage()));

	    }

	    
	    @Test
		@Transactional
		@Rollback(true)	    
		public void testSaveUserThatAlreadyExistsAndExpectUpdateMessage() throws Exception{

			User user = new User();
			user.setUserName("jack123");
			user.setFirstName("firstName");
			user.setLastName("lastName");			
			user.setPlainTextPassword("PASSWORD");
            user.setStartDate(new Date());			
			user.setBirthDate(new Date());
            user.setEmployeeNumber("LX123");
            user.setMobileNumber("123456789");
            user.setEmailAddress("xyz@abc.com");
            user.setHashedPassword("HashedPassword");
            user.setMobileNumber("1234567890");
            user.setPlainTextPassword("PlainTextPassword");

            final SupportedLanguage language = new SupportedLanguage();
            language.setLanguageCode("EN_US");
            language.setAmdLanguage(true);
            language.setHhLanguage(true);
            language.setJ2uLanguage(true);
            language.setU2jLanguage(true);

            user.setAmdLanguage(language);
            user.setU2jLanguage(language);
            user.setJ2uLanguage(language);
            user.setHhLanguage(language);

            final Shift shift = shiftService.findOneByShiftName("day");
            user.setShift(shift);

            final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
            user.setWmsUser(wmsUser);

            user.setAuthenticated(true);
            user.setCreatedByUserName(USERNAME_JACK);
            user.setUpdatedByUserName(USERNAME_JACK);
            user.setSeeHomeCanvasIndicator(new Boolean(true));
            user.setRetrainVoiceModel(new Boolean(false));
            user.setEnable(new Boolean(true));
            user.setDeletedIndicator(new Boolean(false));
            user.setCreatedDateTime(new Date());
            user.setUpdatedDateTime(new Date());
            user.setSkill("Advanced");
            Canvas canvas = new Canvas();
            canvas.setCanvasId(3l);
            canvas.setName("Hazmat Canvas");
            
            user.setActiveCanvas(canvas);
            user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "24HR", 300L));

            List<PermissionGroup> permissions = new ArrayList<>();
            permissions.add(new PermissionGroup("administrator"));
            permissions.add(new PermissionGroup("basic-authenticated-user"));
            permissions.add(new PermissionGroup("picker123"));
            user.setUserPermissionGroupList(permissions);

            UserView userView = new UserView(user);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValueAsString(userView);	    	
	    	
			String token = generateTokenWithAuthenticatedUser();
			String url = "/users/save";
			ResultActions authResultActions = this.mockMvc.perform(post(url)
					.header("Authentication-token", token)
					.content(super.getJsonString(userView))
					.contentType(MediaType.APPLICATION_JSON));
			LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
			String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

			String expected ="1012::INFO::false::User jack123 saved successfully";
			org.junit.Assert.assertEquals("Level did not match ", expected, String.format("%s::%s::%s::%s",apiResponse.getCode(),apiResponse.getLevel(),apiResponse.isExplicitDismissal(),apiResponse.getMessage()));

	    }
	    
	    
	    @Test
		@Transactional
		@Rollback(true)	    
		public void testSaveUserWithInvalidEmailAddressAnExpectValidationException() throws Exception{

			User user = new User();
			user.setUserName("jack1234");
			user.setFirstName("firstName");
			user.setLastName("lastName");			
			user.setPlainTextPassword("PASSWORD");
            user.setStartDate(new Date());			
			user.setBirthDate(new Date());
            user.setEmployeeNumber("LX123");
            user.setMobileNumber("123456789");
            user.setEmailAddress("x@a.c");

            final SupportedLanguage language = new SupportedLanguage();
            language.setLanguageCode("EN_US");
            language.setAmdLanguage(true);
            language.setHhLanguage(true);
            language.setJ2uLanguage(true);
            language.setU2jLanguage(true);

            user.setAmdLanguage(language);
            user.setU2jLanguage(language);
            user.setJ2uLanguage(language);
            user.setHhLanguage(language);

            final Shift shift = shiftService.findOneByShiftName("day");
            user.setShift(shift);

            final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
            user.setWmsUser(wmsUser);

            user.setAuthenticated(true);
            user.setCreatedByUserName(USERNAME_JACK);
            user.setUpdatedByUserName(USERNAME_JACK);
            user.setSeeHomeCanvasIndicator(new Boolean(true));
            user.setRetrainVoiceModel(new Boolean(false));
            user.setEnable(new Boolean(true));
            user.setDeletedIndicator(new Boolean(false));
            user.setCreatedDateTime(new Date());
            user.setUpdatedDateTime(new Date());
            user.setSkill("Advanced");
            
            Canvas canvas = new Canvas();
            canvas.setCanvasId(3l);
            canvas.setName("Hazmat Canvas");
            
            user.setActiveCanvas(canvas);
            user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "24HR", 300L));

            UserView userView = new UserView(user);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValueAsString(userView);	    	
	    	
			String token = generateTokenWithAuthenticatedUser();
			String url = "/users/save";
			ResultActions authResultActions = this.mockMvc.perform(post(url)
					.header("Authentication-token", token)
					.content(super.getJsonString(userView))
					.contentType(MediaType.APPLICATION_JSON));
			LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
			String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

            JSONObject obj = new JSONObject(jsonString);
            Assert.isTrue(obj.get("code").toString().equals("500"), "status is not 500");
            Assert.isTrue(obj.get("message").toString().startsWith("Validation failed for argument:"), "Not a Validation failed error message");
            Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
            Assert.hasText(apiResponse.getLevel().toString(), "ERROR");
	    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUserWithInvalidUsernameAndExpectValidationException() throws Exception{

        User user = new User();
        user.setUserName("");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPlainTextPassword("PASSWORD");
        user.setStartDate(new Date());
        user.setBirthDate(new Date());
        user.setEmployeeNumber("LX123");
        user.setMobileNumber("123456789");
        user.setEmailAddress("x@a.com");

        final SupportedLanguage language = new SupportedLanguage();
        language.setLanguageCode("EN_US");
        language.setAmdLanguage(true);
        language.setHhLanguage(true);
        language.setJ2uLanguage(true);
        language.setU2jLanguage(true);

        user.setAmdLanguage(language);
        user.setU2jLanguage(language);
        user.setJ2uLanguage(language);
        user.setHhLanguage(language);

        final Shift shift = shiftService.findOneByShiftName("day");
        user.setShift(shift);

        final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
        user.setWmsUser(wmsUser);

        user.setAuthenticated(true);
        user.setCreatedByUserName(USERNAME_JACK);
        user.setUpdatedByUserName(USERNAME_JACK);
        user.setSeeHomeCanvasIndicator(new Boolean(true));
        user.setRetrainVoiceModel(new Boolean(false));
        user.setEnable(new Boolean(true));
        user.setDeletedIndicator(new Boolean(false));
        user.setCreatedDateTime(new Date());
        user.setUpdatedDateTime(new Date());
        user.setSkill("Advanced");

        Canvas canvas = new Canvas();
        canvas.setCanvasId(3l);
        canvas.setName("Hazmat Canvas");

        user.setActiveCanvas(canvas);
        user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "24HR", 300L));

        UserView userView = new UserView(user);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userView);

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/save";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userView))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("500"), "status is not 500");
        Assert.isTrue(obj.get("message").toString().startsWith("Validation failed for argument:"), "Not a Validation failed error message");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.hasText(apiResponse.getLevel().toString(), "ERROR");
    }

    @Test
		@Transactional
		@Rollback(true)	    
		public void testSaveUserWithInvalidUserNameFieldSizeAndInvalidEmailAddressAndExpectValidationException() throws Exception{

			User user = new User();
			user.setUserName("ja");
			user.setFirstName("firstName");
			user.setLastName("lastName");			
			user.setPlainTextPassword("PASSWORD");
            user.setStartDate(new Date());			
			user.setBirthDate(new Date());
            user.setEmployeeNumber("LX123");
            user.setMobileNumber("123456789");
            user.setEmailAddress("x@a.c");

            final SupportedLanguage language = new SupportedLanguage();
            language.setLanguageCode("EN_US");
            language.setAmdLanguage(true);
            language.setHhLanguage(true);
            language.setJ2uLanguage(true);
            language.setU2jLanguage(true);

            user.setAmdLanguage(language);
            user.setU2jLanguage(language);
            user.setJ2uLanguage(language);
            user.setHhLanguage(language);

            final Shift shift = shiftService.findOneByShiftName("day");
            user.setShift(shift);

            final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
            user.setWmsUser(wmsUser);

            user.setAuthenticated(true);
            user.setCreatedByUserName(USERNAME_JACK);
            user.setUpdatedByUserName(USERNAME_JACK);
            user.setSeeHomeCanvasIndicator(new Boolean(true));
            user.setRetrainVoiceModel(new Boolean(false));
            user.setEnable(new Boolean(true));
            user.setDeletedIndicator(new Boolean(false));
            user.setCreatedDateTime(new Date());
            user.setUpdatedDateTime(new Date());
            user.setSkill("Advanced");
            
            Canvas canvas = new Canvas();
            canvas.setCanvasId(3l);
            canvas.setName("Hazmat Canvas");
            
            user.setActiveCanvas(canvas);

            user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "12HR", 2000L));
            
            UserView userView = new UserView(user);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValueAsString(userView);	    	
	    	
			String token = generateTokenWithAuthenticatedUser();
			String url = "/users/save";
			ResultActions authResultActions = this.mockMvc.perform(post(url)
					.header("Authentication-token", token)
					.content(super.getJsonString(userView))
					.contentType(MediaType.APPLICATION_JSON));
			LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
			String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });

            JSONObject obj = new JSONObject(jsonString);
            Assert.isTrue(obj.get("code").toString().equals("500"), "status is not 500");
            Assert.isTrue(obj.get("message").toString().startsWith("Validation failed for argument:"), "Not a Validation failed error message");
            Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
            Assert.hasText(apiResponse.getLevel().toString(), "ERROR");
	    }



    @Test
    public void testGetUserListBySearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final LinkedHashMap<String, Object> searchMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userIdMap = new LinkedHashMap<String, Object>();

        // get jack, jill and admin using numeric values
        userIdMap.put("filterType", FilterType.NUMERIC);
        userIdMap.put("values", Arrays.asList("2", "4"));
        searchMap.put("userId", userIdMap);

      
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        LOG.debug("**** testGetUserListBySearchAndSortCriteria: *******" + super.getJsonString(searchAndSortCriteria));

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JACK));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JILL));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_ADMIN));
       }



    @Test
    public void testGetUserListBySearchAndSortCriteriaJack() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList(USERNAME_JACK));
        searchMap.put("userName", userNameMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        Assert.isTrue(actualJsonString.contains(USERNAME_JACK), USERNAME_JACK + " Not Found in Resulted Json  ");
    }


    @Test
    public void testGetUserListBySearchAndSortCriteriaForPercentageSymbol() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("%j%"));
        searchMap.put("userName", userNameMap);

        searchAndSortCriteria.setSearchMap(searchMap);


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        Assert.isTrue(actualJsonString.contains(USERNAME_JACK), USERNAME_JACK + " Not Found in Resulted Json  ");
        Assert.isTrue(actualJsonString.contains(USERNAME_JILL),USERNAME_JILL+" Not Found in Resulted Json  ");
    }


    @Test
    public void testUserExcelBasedOnSearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        final LinkedHashMap<String, Object> userIdRangeListMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("%dummy%"));
        searchMap.put("userName", userNameMap);

        // Date Range
        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", Arrays.asList("2013-01-05", "2015-08-05"));
        searchMap.put("startDate", startDateMap);

        // numeric List
        userIdRangeListMap.put("filterType", FilterType.NUMERIC);
        userIdRangeListMap.put("values", Arrays.asList("0", "300"));

        searchMap.put("userId", userIdRangeListMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(20);

        ObjectMapper jacksonObjectMapper = new LucasObjectMapper();
        String o = jacksonObjectMapper.writeValueAsString(searchAndSortCriteria);
        SearchAndSortCriteria searchAndSortCriteria1 = jacksonObjectMapper.readValue(o, new TypeReference<SearchAndSortCriteria>() {
        });

        final String url = "/users/userlist/excel";
        final ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsByteArray());
        byte[] fileData = authResultActions.andReturn().getResponse().getContentAsByteArray();
//        final String separator = File.separator;
//        final File file = new File("src" + separator + "test" + separator + "resources" + separator + "employees.xls");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        final FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.write(fileData);
//        fileOutputStream.close();
        
        Assert.notNull(fileData);
        Assert.isTrue(fileData.length > 0, "File data is null when it should not be!");
    }

    @Test
    public void testUserPdfBasedOnSearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("userName", new ArrayList<String>() {
            {
                add("%dummy%");
                add(USERNAME_JACK);
            }
        });

        // Date Range
        final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        final LocalDate startDate = dateFormatter.parseLocalDate("2014-01-05");
        final LocalDate endDate = dateFormatter.parseLocalDate("2015-08-05");
        final LucasDateRange lucasDateRange = new LucasDateRange(startDate, endDate);
        searchMap.put("startDate", lucasDateRange);

        // numeric List
        searchMap.put("userId", new ArrayList<Long>() {
            {
                add(0L);
                add(300L);
            }
        });

        // numeric Range
        final LucasNumericRange lucasNumericRange = new LucasNumericRange(0L, 300L);
        searchMap.put("userId", lucasNumericRange);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(1);
        searchAndSortCriteria.setPageSize(20);

        ObjectMapper jacksonObjectMapper = new LucasObjectMapper();
        String o = jacksonObjectMapper.writeValueAsString(searchAndSortCriteria);
        SearchAndSortCriteria searchAndSortCriteria1 = jacksonObjectMapper.readValue(o, new TypeReference<SearchAndSortCriteria>() {
        });

        final String url = "/users/userlist/pdf";
        final ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsByteArray());
        byte[] fileData = authResultActions.andReturn().getResponse().getContentAsByteArray();
//        final String separator = File.separator;
//        final File file = new File("src" + separator + "test" + separator + "resources" + separator + "employees.pdf");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        final FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.write(fileData);
//        fileOutputStream.close();
        Assert.notNull(fileData);
        Assert.isTrue(fileData.length > 0, "File data is zero when it should not be!");
    }
    
    @Test
	@Transactional
	@Rollback(true)
	public void testi18nSupportForFrenchUsingSaveUser() throws Exception{

        final double userNumber = Math.random();
        String testUser = "User_" + userNumber;

        final User user = this.getUserObject();
        user.setUserName(testUser);

        Canvas canvas = new Canvas();
        canvas.setCanvasId(3l);
        canvas.setName("Hazmat Canvas");
        
        user.setActiveCanvas(canvas);
        
        final UserView userView = new UserView(user);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userView);

        String token = generateTokenWithAuthenticatedUser();
	String url = "/users/save";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.locale(Locale.FRENCH)
				.content(super.getJsonString(userView))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ApiResponse<Object> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
		
		String expected = "L&apos;utilisateur " + testUser + " enregistr&eacute; avec succ&egrave;sk";
		org.junit.Assert.assertEquals("Strings did not match: ", expected, apiResponse.getMessage());
    }

    /*
 output for call is mentioned below
{
   "status":"success",
   "code":"200",
   "message":"Request processed successfully",
   "level":null,
   "uniqueKey":null,
   "token":null,
   "explicitDismissal":null,
   "data":{
      "firstName":"Jack",
      "username":"jack123",
      "userPermissions":[
         "edit-canvas",
         "authenticated-user",
         "user-management-canvas-view",
         "create-assignment",
         "user-management-canvas-edit",
         "user-list-download-excel",
         "user-list-download-pdf",
         "pick-monitoring-canvas-edit",
         "pick-monitoring-canvas-view"
      ],
      "userId":3
   }
}
 */
    @Test
    public void testJackUserPermission() throws Exception {

        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/" + USERNAME_JACK;
        resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
        Assert.notNull(jsonString, "Response Json is Null");
        Assert.isTrue(jsonString.trim().length() != 0, "Response Json is Empty ");
        Assert.isTrue(jsonString.contains("publish-canvas"), "Jack Don't have Edit-Canvas Permission ");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        final ApiResponse<UserView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<UserView>>() {
        });
        final User jackUser = ((UserView) apiResponse.getData()).getUser();
        final Set<Permission> jackPermissionSet = jackUser.getPermissionsSet();
        Assert.notEmpty(jackPermissionSet, "Jack User Don't Have any Permission");
    }
    

    @Test
	@Transactional
	@Rollback(true)	    
	public void testMultiUserDetails() throws Exception{

		List<String> userNameList = new ArrayList<String>(); 
    	userNameList.add("admin123");
    	userNameList.add("jill123");
    	userNameList.add("jack123");
    	
		String token = generateTokenWithAuthenticatedUser();
		String url = "/users/multiedit/details";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(userNameList))
				.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		Assert.hasText(jsonString);
		
    }     

    
    @Test
	@Transactional
	@Rollback(true)	    
	public void testGetUserDetails() throws Exception{

		List<String> userNameList = new ArrayList<String>(); 
    	        userNameList.add("admin123");
    	        userNameList.add("jill123");
    	        userNameList.add("jack123");
    	
		String token = generateTokenWithAuthenticatedUser();
		String url = "/users/details";
		ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		Assert.hasText(jsonString);
		
                JSONObject obj = new JSONObject(jsonString);
                Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
                Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
                Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
                Assert.isTrue(obj.get("data").toString().contains("admin123"), "admin123 is not in the json list");
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserDetailsWithNullUser() throws Exception{

        List<String> userNameList = new ArrayList<String>();
        userNameList.add("admin123");
        userNameList.add("jill123");
        userNameList.add("");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/details";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1026"), "status is not 1026");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("User does not exist"), "It's not User does not exist");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserDetailsWithInvalidUser() throws Exception{

        List<String> userNameList = new ArrayList<String>();
        userNameList.add("admin123");
        userNameList.add("jill123");
        userNameList.add("INVALID_USER");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/details";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1026"), "status is not 1026");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("User does not exist"), "It's not User does not exist");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserDetailsWithEmptyArrayUserList() throws Exception{

        List<String> userNameList = new ArrayList<String>();
        /*userNameList.add("admin123");
        userNameList.add("jill123");
                userNameList.add("INVALID_USER");*/

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/details";
        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1026"), "status is not 1026");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("User does not exist"), "It's not User does not exist");
    }

    /*
{
   "status":"success",
   "code":"200",
   "message":"Request processed successfully",
   "level":null,
   "uniqueKey":null,
   "token":null,
   "explicitDismissal":null,
   "data":{
      "firstName":"Jill",
      "username":"jill123",
      "userPermissions":[
         "authenticated-user",
         "user-management-canvas-view",
         "create-assignment",
         "pick-monitoring-canvas-view"
      ],
      "userId":2
   }
}
 */
    @Test
    public void testJillUserPermission() throws Exception {

        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/" + USERNAME_JILL;
        resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
        Assert.notNull(jsonString, "Response Json is Null");
        Assert.isTrue(jsonString.trim().length() != 0, "Response Json is Empty ");
        Assert.isTrue(!jsonString.contains("edit-canvas"), "Jill have Edit-Canvas Permission ");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        final ApiResponse<UserView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<UserView>>() {
        });
        final User jackUser = ((UserView) apiResponse.getData()).getUser();
        final Set<com.lucas.entity.user.Permission> jackPermissionSet = jackUser.getPermissionsSet();
        Assert.notEmpty(jackPermissionSet, "Jill User Don't Have any Permission");
    }


    /**
     * Test method to disable a bunch of dummy users
     * @throws Exception
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testDisableUsers() throws Exception {
        final List<String> userList = new ArrayList<String>();
        userList.add("dummy-username10");
        userList.add("dummy-username11");
        userList.add("dummy-username12");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/disable";
        final ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("testDisableUsers() controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {
        });
        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");
        LOG.debug("Server Response ",responseMap.toString());
        org.springframework.util.Assert.isTrue(responseMap.size() == userList.size(), "Response is Not for All User Send ");
        org.springframework.util.Assert.isTrue(responseMap.containsKey("dummy-username10"), "No Response for " + "dummy-username10");
        org.springframework.util.Assert.isTrue(responseMap.containsKey("dummy-username11"), "No Response for " + "dummy-username11");
        org.springframework.util.Assert.isTrue(responseMap.containsKey("dummy-username12"), "No Response for " + "dummy-username12");
    }


    /*
  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/enable
     [
       "jack123",
       "jill123",
       "admin123",
     ]
 */
    @Test
    @Transactional
    public void testEnableUsers() throws Exception {
        final List<String> userList = new ArrayList<String>();
        userList.add(USERNAME_JACK);
        userList.add(USERNAME_JILL);
        userList.add(USERNAME_ADMIN);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/enable";
        final ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {
        });
        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");
        LOG.debug("Server Response ",responseMap.toString());
        org.springframework.util.Assert.isTrue(responseMap.size() == userList.size(), "Response is Not for All User Send ");
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_JACK), "No Response for " + USERNAME_JACK);
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_JILL), "No Response for " + USERNAME_JILL);
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_ADMIN), "No Response for " + USERNAME_ADMIN);
    }

    /*
     url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/delete
      [
        "jack123",
        "jill123",
        "admin123",
      ]
*/
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUser() throws Exception {
        final List<String> userList = new ArrayList<String>();
        for(int index=0;index<5;index++){
            final User user=this.getUserObject();
            final UserView userView = new UserView(user);
            final ObjectMapper mapper1 = new ObjectMapper();
            mapper1.writeValueAsString(userView);

            final String token1 = generateTokenWithAuthenticatedUser();
            final String url1 = "/users/delete";
            final ResultActions authResultActions1 = this.mockMvc.perform(post(url1)
                    .header("Authentication-token", token1)
                    .content(super.getJsonString(userView))
                    .contentType(MediaType.APPLICATION_JSON));
            LOG.debug("controller returns : {}", authResultActions1.andReturn().getResponse().getContentAsString());
            final String jsonString1 = authResultActions1.andReturn().getResponse().getContentAsString();
            mapper1.setSerializationInclusion(Include.NON_NULL);
            mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final ApiResponse<Object> apiResponse1 = mapper1.readValue(jsonString1, new TypeReference<ApiResponse<Object>>() { });
            if(apiResponse1.getStatus().equals("success")){
                userList.add(user.getUserName());
            }
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUserWithNullValue() throws Exception {
        final List<String> userList = new ArrayList<String>();
        userList.add("dummy-username30");
        userList.add("dummy-username31");
        userList.add("");

        final String token1 = generateTokenWithAuthenticatedUser();
        final String url1 = "/users/delete";
        final ResultActions authResultActions1 = this.mockMvc.perform(post(url1)
                .header("Authentication-token", token1)
                .content(super.getJsonString(userList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions1.andReturn().getResponse().getContentAsString());
        final String jsonString1 = authResultActions1.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper1 = new ObjectMapper();

        mapper1.setSerializationInclusion(Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(jsonString1, new TypeReference<ApiResponse<Object>>() { });
        Assert.isTrue(apiResponse1.getCode().equals("1019"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUserWithInvalidUsername() throws Exception {
        final List<String> userList = new ArrayList<String>();
        userList.add("dummy-username30");
        userList.add("dummy-username31");
        userList.add("Invalid user");

        final String token1 = generateTokenWithAuthenticatedUser();
        final String url1 = "/users/delete";
        final ResultActions authResultActions1 = this.mockMvc.perform(post(url1)
                .header("Authentication-token", token1)
                .content(super.getJsonString(userList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions1.andReturn().getResponse().getContentAsString());
        final String jsonString1 = authResultActions1.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper1 = new ObjectMapper();

        mapper1.setSerializationInclusion(Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(jsonString1, new TypeReference<ApiResponse<Object>>() { });
        Assert.isTrue(apiResponse1.getCode().equals("1020"));
    }

    private final User getUserObject() {
        java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date fixedDate = null;
        try {
            // we need a fixed date to compare with JSON file testing
            fixedDate = sdf.parse("2015-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final double userNumber = Math.random();
        String testUser = "User_" + userNumber;
        final User user = new User();
        user.setUserName(testUser);
        user.setFirstName("User_FirstName_" + userNumber);
        user.setLastName("User_LastName_" + userNumber);
        user.setTitle("UserTitle_" + userNumber);
        user.setEmailAddress("User@EmailAddress_" + userNumber + ".com");
        user.setBirthDate(sqlDate);
        user.setEmployeeNumber("EmployeeNumber_" + userNumber);
        user.setHashedPassword("HashedPassword");
        user.setMobileNumber("1234567890");
        user.setPlainTextPassword("PlainTextPassword");

        user.setAuthenticated(true);
        user.setCreatedByUserName(USERNAME_JACK);
        user.setUpdatedByUserName(USERNAME_JACK);
        user.setSeeHomeCanvasIndicator(new Boolean(true));
        user.setDeletedIndicator(new Boolean(false));
        user.setRetrainVoiceModel(new Boolean(false));
        user.setEnable(new Boolean(true));
        user.setDeletedIndicator(new Boolean(false));
        user.setCreatedDateTime(fixedDate);
        user.setUpdatedDateTime(fixedDate);
        user.setSkill("Advanced");

        final SupportedLanguage language = new SupportedLanguage();
        language.setLanguageCode("EN_US");
        language.setAmdLanguage(true);
        language.setHhLanguage(true);
        language.setJ2uLanguage(true);
        language.setU2jLanguage(true);
        user.setAmdLanguage(language);
        user.setU2jLanguage(language);
        user.setJ2uLanguage(language);
        user.setHhLanguage(language);

        final Shift shift = shiftService.findOneByShiftName("day");
        user.setShift(shift);
        user.getShift().setShiftId(shift.getShiftId());

        //final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
        final WmsUser wmsUser = new WmsUser();
        wmsUser.setUserId(user.getUserId());
        wmsUser.setHostPlainTextPassword("PlainTextPassword");
        wmsUser.setUpdatedByUserName("system");
        wmsUser.setCreatedByUserName("system");
        wmsUser.setHostLogin(user.getUserName());
        wmsUser.setCreatedDateTime(fixedDate);
        wmsUser.setUpdatedDateTime(fixedDate);
        user.setWmsUser(wmsUser);
        user.setHostLogin(wmsUser.getHostLogin());
        user.setHostPassword(wmsUser.getHostPlainTextPassword());


        PermissionGroup p1 = new PermissionGroup("supervisor123");
        PermissionGroup p2 = new PermissionGroup("warehouse-manager123");
        PermissionGroup p3 = new PermissionGroup("picker");
        PermissionGroup p4 = new PermissionGroup("administrator123");
        PermissionGroup[] permissionArray = {p1, p2, p3, p4};
        List<PermissionGroup> permissionList = new ArrayList<PermissionGroup>();
        permissionList.addAll(Arrays.asList(permissionArray));
        user.setUserPermissionGroupList(permissionList);

        Canvas canvas = new Canvas();
        canvas.setCanvasId(3l);
        canvas.setName("Hazmat Canvas");

        user.setActiveCanvas(canvas);
        user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "24HR", 300L));
        return user;
    }




        @Test
    	@Transactional
    	@Rollback(true)
    	public void testSaveMultiUser() throws Exception {
    		String token = generateTokenWithAuthenticatedUser();
    		String url = "/users/multiedit/save";
    		ApiRequest<MultiUserDTO> apiRequest = new ApiRequest<MultiUserDTO>();
    		UserFormFieldsDTO multiEditFields = gerUserFormFieldsDTOData();
    		List<String> userNameList = Arrays.asList("jack123","jill123");
    		MultiUserDTO multiUserDTO = new MultiUserDTO(userNameList,
    				multiEditFields);
    		//apiRequest.setData(multiUserDTO);
    		ResultActions resultActions = this.mockMvc.perform(post(url)
    				.header("Authentication-token", token)
    				.content(super.getJsonString(multiUserDTO))
    				.contentType(MediaType.APPLICATION_JSON));
    		LOG.debug("controller returns : {}", resultActions.andReturn()
    				.getResponse().getContentAsString());
    		String jsonString = resultActions.andReturn().getResponse()
    				.getContentAsString();
    		ObjectMapper mapper = new ObjectMapper();
    		mapper.setSerializationInclusion(Include.NON_NULL);
    		ApiResponse<Object> apiResponse =  mapper.readValue(jsonString, new TypeReference<ApiResponse<Object>>() { });
    		Assert.isTrue(apiResponse.getCode().equals("1018"), "ApiResponse code is not equal to 1018");
    		Assert.isTrue(apiResponse.isExplicitDismissal(), "Explicit dismissal flag is not true");
    		Assert.isTrue(apiResponse.getLevel().name().equals("INFO"), "Level is not equal to INFO");
    		Assert.isTrue(apiResponse.getMessage().equals("Users [jack123, jill123] have been updated"));
    		Assert.isTrue(apiResponse.getData() == null, "data element returned in the response is not null");
    		resultActions.andExpect(status().isOk()).andExpect(content().string(containsString(String.valueOf(apiResponse.getCode()))));
    	}
        

    	private UserFormFieldsDTO gerUserFormFieldsDTOData() {
    		UserFormFieldsDTO multiEditFields = new UserFormFieldsDTO();
    		multiEditFields.setSkill(new HashMap<String, String>() {
    			{
    				put("value", "TestSkill");
    				put("forceUpdate", "false");
    			}
    		});
    		multiEditFields.setJ2uLanguage(new HashMap<String, String>() {
    			{
    				put("value", "FR_FR");
    				put("forceUpdate", "true");
    			}
    		});
    		multiEditFields.setU2jLanguage(new HashMap<String, String>() {
    			{
    				put("value", "FR_FR");
    				put("forceUpdate", "true");
    			}
    		});
    		multiEditFields.setHhLanguage(new HashMap<String, String>() {
    			{
    				put("value", "FR_FR");
    				put("forceUpdate", "true");
    			}
    		});
    		multiEditFields.setAmdLanguage(new HashMap<String, String>() {
    			{
    				put("value", "FR_FR");
    				put("forceUpdate", "true");
    			}
    		});
    		return multiEditFields;
    	}
    	
    	 @Test
    	 public void testGetUserGroupsPermissionsBySearchAndSortCriteria() throws Exception {

    	 	final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

    	    final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
    	    final Map<String, Object> searchMap = new HashMap<String, Object>();

   	        searchAndSortCriteria.setSearchMap(searchMap);

   	        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
   	        	{
    	        	put("permissionGroupName", SortType.ASC);
    	        }
   	        });

    	    searchAndSortCriteria.setPageNumber(0);
    	    searchAndSortCriteria.setPageSize(Integer.MAX_VALUE);

    	    final String url = "/users/groups/permissions";
    	    final ResultActions apiResultActions = this.mockMvc.perform(post(url)
    	    	.header("Authentication-token", token)
    	        .content(super.getJsonString(searchAndSortCriteria))
    	        .contentType(MediaType.APPLICATION_JSON));

    	    LOG.debug("testGetUserGroupsPermissionsBySearchAndSortCriteria() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
    	    String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
    	    ObjectMapper mapper = new ObjectMapper();
    	    mapper.setSerializationInclusion(Include.NON_NULL);
    	    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    	        
    	    org.junit.Assert.assertNotNull("Data url response should not be null from the server", actualJsonString);
    	        
    	    Assert.isTrue(compareJson(actualJsonString, USER_PERMISSION_GROUPS_DATAURL_FILE_PATH),
				"Server response for the data url endpoint is not what expected");
    	 }

    /**
     *  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/groups/permissionslist
     following is the input json for the service
     [
     "jack123",
     "jill123",
     "joe123"
     ]
     *  sample JSON expected as input to this method

    {
        "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicitDismissal": null,
            "data": [
        {
            "username": "jack123",
                "groups": [
            {
                "groupName": "picker",
                    "permission": [
                "create-assignment",
                        "authenticated-user",
                        "user-management-canvas-view",
                        "pick-monitoring-canvas-view",
                        "disable-user"
                ]
            },
            {
                "groupName": "supervisor",
                    "permission": [
                "user-management-canvas-view",
                        "user-management-canvas-edit",
                        "pick-monitoring-canvas-view",
                        "pick-monitoring-canvas-edit",
                        "user-list-download-excel",
                        "user-list-download-pdf",
                        "edit-canvas",
                        "delete-user",
                        "disable-user",
                        "disable-user",
                        "enable-user",
                        "edit-multi-user",
                        "group-maintenance-create",
                        "group-maintenance-edit",
                        "create-product",
                        "view-product",
                        "edit-product",
                        "delete-product",
                        "view-users-createeditform-widget",
                        "view-users-searchusergrid-widget",
                        "view-users-picklinebywave-widget",
                        "view-users-groupmaintenance-widget",
                        "view-users-searchproductgrid-widget"
                ]
            }
            ]
        },
        {
            "username": "jill123",
                "groups": [
            {
                "groupName": "picker",
                    "permission": [
                "create-assignment",
                        "authenticated-user",
                        "user-management-canvas-view",
                        "pick-monitoring-canvas-view",
                        "disable-user"
                ]
            }
            ]
        },
        {
            "username": "admin123",
                "groups": [
            {
                "groupName": "warehouse-manager",
                    "permission": [
                "create-assignment",
                        "view-report-productivity",
                        "configure-location",
                        "create-canvas",
                        "delete-canvas"
                ]
            },
            {
                "groupName": "basic-authenticated-user",
                    "permission": [
                "authenticated-user"
                ]
            }
            ]
        }
        ]
    }

    */

    @Test
    @Transactional(readOnly = true)
    public void testUserPermissionGroup()throws Exception{
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(USERNAME_JACK);
        userNameList.add(USERNAME_JILL);
        userNameList.add(USERNAME_ADMIN);
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups/permissionslist";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testUserPermissionGroup() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        Assert.notNull(actualJsonString,"User Permission Group List is null");


        Assert.isTrue(
                compareJson(actualJsonString,
                        USER_PERMISSION_GROUP_LIST_FILE_PATH),
                "Server response for the data url endpoint is not what expected");
    }

    /**
     * Straight path test method which takes the permission group and assigned it to a user
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUsersAssignedGroups() throws Exception {

        List<String> permissionsList = Arrays.asList("picker");

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(USERNAME_JACK);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups/assign";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(usersAssignedGroupsDTO))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        apiResultActions.andExpect(status().isOk());

        JSONObject obj = new JSONObject(actualJsonString);

        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
    }

    /**
     * Test method to make sure that saving the non existing functionality is working correctly as expected
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUsersAssignedInvalidGroups() throws Exception {

        List<String> permissionsList = Arrays.asList("non-existing-group");

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(USERNAME_JACK);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups/assign";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(usersAssignedGroupsDTO))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(actualJsonString);

        Assert.isTrue(obj.get("code").toString().equals("500"), "status is not 500");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Permission group does not exist with group non-existing-group. Cannot add Groups to user!"), "status is not failure");
    }

    /**
     * Test method to make sure the user permission groups can be roked correctly by passing in null group
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUsersAssignedGroupsWithNullGroups() throws Exception {

        List<String> permissionsList = new ArrayList<String>();

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(USERNAME_JACK);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups/assign";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(usersAssignedGroupsDTO))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(actualJsonString);

        Assert.isTrue(obj.get("code").toString().equals("1012"), "status is not 1012");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("User jack123 saved successfully"), "message is not - User jack123 saved successfully");
    }

    /**
     * Test method to make sure the permission groups cannot be added for non existing user
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUsersAssignedGroupsWithNonExistingUser() throws Exception {

        List<String> permissionsList = new ArrayList<String>();

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName("AN_INVALID-USER");
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups/assign";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(usersAssignedGroupsDTO))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(actualJsonString);

        Assert.isTrue(obj.get("code").toString().equals("500"), "status is not 500");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("No user exists with username AN_INVALID-USER. Cannot add Groups to user!"), "message is not - No user exists with username AN_INVALID-USER. Cannot add Groups to user!");
    }
    
  /**
   * Test to check the total records in default search and sort criteria for Users.
   * 
   * @throws Exception
   */
  @SuppressWarnings("serial")
  @Test
  public void testTotalRecordsForUsersBySearchAndSortCriteria() throws Exception {

    final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

    final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();

    searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
      {
        put("userName", SortType.ASC);
      }
    });

    searchAndSortCriteria.setPageNumber(0);
    searchAndSortCriteria.setPageSize(3);

    LOG.debug("**** testTotalRecordsForUsersBySearchAndSortCriteria: *******" + super.getJsonString(searchAndSortCriteria));

    final String url = "/users/userlist/search";
    final ResultActions apiResultActions =
        this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

    LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
    String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
    org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
    org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
    
    final Map responseMap = apiResponse.getData();
    org.springframework.util.Assert.notNull(responseMap, "Response is Null");
    org.springframework.util.Assert.isTrue(Long.parseLong(responseMap.get("totalRecords").toString()) == 202L, "Total Records is not correct.");
  }
  
    /**
     * Test method to set the retrain voice model for the selected user for success.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsers() throws Exception {
        final List<String> userList = new ArrayList<String>();
        userList.add(USERNAME_JACK);
        userList.add(USERNAME_JILL);
        userList.add(USERNAME_ADMIN);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/retrainvoice";
        final ResultActions authResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {});

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");
        LOG.debug("Server Response ", responseMap.toString());
        org.springframework.util.Assert.isTrue(responseMap.size() == userList.size(), "Response is Not for All User Send ");
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_JACK), "No Response for " + USERNAME_JACK);
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_JILL), "No Response for " + USERNAME_JILL);
        org.springframework.util.Assert.isTrue(responseMap.containsKey(USERNAME_ADMIN), "No Response for " + USERNAME_ADMIN);
    }

    /**
     * Test method to set the retrain voice model for null user.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForEmptyUserList() throws Exception {
        final List<String> userList = new ArrayList<String>();
        // userList.add(null);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/retrainvoice";
        final ResultActions authResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("500"), "status is not 500");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");

        Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.isNull(responseMap, "Response is not Null");
    }

    /**
     * Test method to set the retrain voice model for empty user.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForEmptyUser() throws Exception {
        final List<String> userList = Arrays.asList("");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/retrainvoice";
        final ResultActions authResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("500"), "status is not 500");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");

        Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.isNull(responseMap, "Response is not Null");
    }

    /**
     * Test method to set the retrain voice model for invalid user.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForInvalidUser() throws Exception {
        final List<String> userList = Arrays.asList("sdfghfgh");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/retrainvoice";
        final ResultActions authResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("500"), "status is not 500");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");

        Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.isNull(responseMap, "Response is not Null");
    }

    /**
     * Test method to set the retrain voice model for invalid user.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForInvalidUserWithValidUsers() throws Exception {
        final List<String> userList = Arrays.asList("jack123", "jill123", "hdfjksdhyfsd");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userList);
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/users/retrainvoice";
        final ResultActions authResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("500"), "status is not 500");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");

        Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.isNull(responseMap, "Response is not Null");
    }
    
    /**
     * Happy path test method to pull back expected results and verify it the response results
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetUserAssignedGroups()throws Exception{
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(USERNAME_JACK);
        userNameList.add(USERNAME_JILL);
        userNameList.add(USERNAME_ADMIN);
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testUserPermissionGroup() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Assert.notNull(actualJsonString,"User Permission Group List is null");
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1022"), "status is not 1022");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("User groups retrieved successfully"), "message is not User groups retrieved successfully");
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JACK));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_JILL));
        Assert.isTrue(obj.get("data").toString().contains(USERNAME_ADMIN));

        JSONObject dataObj = obj.getJSONObject("data");

        Assert.isTrue(dataObj.get(USERNAME_JILL).toString().contains("picker"));
        Assert.isTrue(dataObj.get(USERNAME_JACK).toString().contains("warehouse-manager"));
        Assert.isTrue(dataObj.get(USERNAME_ADMIN).toString().contains("system"));
    }

    /**
     * Test to verify that with an invalid user the result produced an exception
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetUserAssignedGroupsWithInvalidUser()throws Exception{
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(USERNAME_JACK);
        userNameList.add(USERNAME_JILL);
        userNameList.add("AN-INVALID-USER");
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testUserPermissionGroup() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Assert.notNull(actualJsonString,"User Permission Group List is null");
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1023"), "status is not 1023");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Cannot retrieve user groups for invalid user"), "Cannot retrieve user groups for invalid user");
    }

    /**
     * Test to verify that with an null user the result produced an exception
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetUserAssignedGroupsWithNullUser()throws Exception{
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(USERNAME_JACK);
        userNameList.add(USERNAME_JILL);
        userNameList.add("");
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testUserPermissionGroup() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Assert.notNull(actualJsonString,"User Permission Group List is null");
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1023"), "status is not 1023");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Cannot retrieve user groups for invalid user"), "Cannot retrieve user groups for invalid user");
    }

    /**
     * Test to verify that with an empty user list the result produced an exception
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetUserAssignedGroupsWithEmptyUser()throws Exception{
        final List<String> userNameList=new ArrayList<String>();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/groups";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testUserPermissionGroup() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Assert.notNull(actualJsonString,"User Permission Group List is null");
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1023"), "status is not 1023");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Cannot retrieve user groups for invalid user"), "Cannot retrieve user groups for invalid user");
    }
    
    /**
     * Test method to filter j2uLanguage in SearchAndSortCriteria
     * 
     * @throws Exception
     */
    @SuppressWarnings({"serial", "rawtypes"})
    @Test
    public void testGetUserListBySearchAndSortCriteriaJ2uLanguage() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("j2uLanguage", langMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");

        final Map gridMap = (Map) responseMap.get("grid");
        
        // value for j2uLangauge is mapped to 7 in grid data
        final Map j2uLanguageMap = (Map) gridMap.get("7");
        final List<?> j2uLanguageValuesMap = (List<?>) j2uLanguageMap.get("values");
        org.springframework.util.Assert.isTrue(j2uLanguageValuesMap.contains("EN_GB"), "No Response for j2uLangauage for EN_GB");
    }

    /**
     * Test method to filter u2jLanguage in SearchAndSortCriteria
     * 
     * @throws Exception
     */
    @SuppressWarnings({"serial", "rawtypes"})
    @Test
    public void testGetUserListBySearchAndSortCriteriaU2jLanguage() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("u2jLanguage", langMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");

        final Map gridMap = (Map) responseMap.get("grid");

        // value for u2jLangauge is mapped to 8 in grid data
        final Map u2jLanguageMap = (Map) gridMap.get("8");
        final List<?> u2jLanguageValuesMap = (List<?>) u2jLanguageMap.get("values");
        org.springframework.util.Assert.isTrue(u2jLanguageValuesMap.contains("EN_GB"), "No Response for u2jLanguage for EN_GB");
    }

    /**
     * Test method to filter amdLanguage in SearchAndSortCriteria
     * 
     * @throws Exception
     */
    @SuppressWarnings({"serial", "rawtypes"})
    @Test
    public void testGetUserListBySearchAndSortCriteriaAmdLanguage() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("amdLanguage", langMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");

        final Map gridMap = (Map) responseMap.get("grid");

        // value for amdLanguage is mapped to 10 in grid data
        final Map amdLanguageMap = (Map) gridMap.get("10");
        final List<?> amdLanguageValuesMap = (List<?>) amdLanguageMap.get("values");
        org.springframework.util.Assert.isTrue(amdLanguageValuesMap.contains("EN_GB"), "No Response for amdLanguage for EN_GB");
    }

    /**
     * Test method to filter hhLanguage in SearchAndSortCriteria
     * 
     * @throws Exception
     */
    @SuppressWarnings({"serial", "rawtypes"})
    @Test
    public void testGetUserListBySearchAndSortCriteriaHhLanguage() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("hhLanguage", langMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");

        final Map gridMap = (Map) responseMap.get("grid");

        // value for hhLangauge is mapped to 9 in grid data
        final Map hhLanguageMap = (Map) gridMap.get("9");
        final List<?> hhLanguageValuesMap = (List<?>) hhLanguageMap.get("values");
        org.springframework.util.Assert.isTrue(hhLanguageValuesMap.contains("EN_GB"), "No Response for hhLangauge for EN_GB");
    }


    /**
     * Test method to filter j2uLanguage in SearchAndSortCriteria
     * 
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithInvalidSearchCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        searchMap.put("j2uLanguage", langMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions =
                this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1069"), "status is not 1069");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");

        Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.isNull(responseMap, "Response is not Null");
    }

    /**
     * @throws Exception
     * @Author Adarsh kumar
     * <p/>
     * testOpenCanvasesForUser() provide the functionality testing open canvases
     * its accept the  persisting state user id  using the UIController.getOpenCanvasesForUser();
     * which intern use UIService.getAllOpenCanvasForUser();
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/{username}/canvases/open
     */
    @Test
    @Transactional
    public void testOpenCanvasesForUser() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/users/jack123/canvases/open";
        final ResultActions resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(
                        MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Canvas Data Json is Null");
        resultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        org.junit.Assert.assertTrue(jsonObject.has("data"));
        final JSONArray jsonArray= jsonObject.getJSONArray("data");
        org.junit.Assert.assertNotNull("Json Object is Null", jsonArray);
        final List<String> canvasList=new ArrayList<String>(){
            {
                add("JackCanvas300");
                add("JackCanvas401");
                add("JackCanvas402");
                add("JackCanvas403");
                add("JackCanvas404");
                add("JackCanvas405");
                add("ProductManagement");
                add("Work Execution");
                add("AssignmentManagement");
                add("GroupManagement");
                add("Pick Monitoring Canvas");
                add("Perishable Goods Canvas");
            }
        };
        for(int index=0;index<canvasList.size();index++){
            LOG.info(canvasList.get(index));
            org.junit.Assert.assertTrue("Invalid Canvas for User ", canvasList.contains(((JSONObject) ((JSONObject) jsonArray.get(index)).get("canvas")).get("canvasName").toString()));
        }
    }


    /**
     * @throws Exception
     * @Author Adarsh kumar
     * <p/>
     * testOpenCanvasesForNonOpenCanvasForUser() provide the functionality testing open canvases
     * its accept the  persisting state user id  using the UIController.getOpenCanvasesForUser();
     * which intern use UIService.getAllOpenCanvasForUser();
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/{username}/canvases/open
     */
    @Test
    @Transactional
    public void testOpenCanvasesForUserNotHavingOpenCanvas() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/users/jill123/canvases/open";
        final ResultActions resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(
                        MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}", jsonString);
        org.junit.Assert.assertNotNull(jsonString, "Canvas Data Json is Null");

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().equals("Open Canvas Not Found for User jill123"));

    }


    /**
     * @throws Exception
     * @Author Adarsh kumar
     * <p/>
     * testOpenCanvasesForNullUser() provide the functionality testing open canvases
     * its accept the  persisting state user id  using the UIController.getOpenCanvasesForUser();
     * which intern use UIService.getAllOpenCanvasForUser();
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/{username}/canvases/open
     */
    @Test
    @Transactional
    public void testOpenCanvasesForNullUser() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/users/null/canvases/open";
        final ResultActions resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}", jsonString);
        org.junit.Assert.assertNotNull(jsonString, "Canvas Data Json is Null");
        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").toString().equals("Open Canvas Not Found for User  null"));

    }

    
    /*
     * Summary: The method tests saving of open canvases for a user
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveOpenCanvasesForUser() throws Exception {

        final OpenUserCanvas openCanvas1 = new OpenUserCanvas();
        openCanvas1.setDisplayOrder(4);
        openCanvas1.setCanvas(new Canvas(){
            {
                setCanvasId(1L);
            }
        });

        final OpenUserCanvas openCanvas2 = new OpenUserCanvas();
        openCanvas2.setDisplayOrder(3);
        openCanvas2.setCanvas( new Canvas(){
            {
                setCanvasId(2L);
            }
        });

        final OpenUserCanvas openCanvas3 = new OpenUserCanvas();
        openCanvas3.setDisplayOrder(2);
        openCanvas3.setCanvas(new Canvas(){
            {
                setCanvasId(350L);
            }
        });

        final OpenUserCanvas openCanvas4 = new OpenUserCanvas();
        openCanvas4.setDisplayOrder(1);
        openCanvas4.setCanvas( new Canvas(){
            {
                setCanvasId(400L);
            }
        });
        final List<OpenUserCanvasesView> openUserCanvasesViewList = new ArrayList<OpenUserCanvasesView>();
        openUserCanvasesViewList.add(new OpenUserCanvasesView(openCanvas1));
        openUserCanvasesViewList.add(new OpenUserCanvasesView(openCanvas2));
        openUserCanvasesViewList.add(new OpenUserCanvasesView(openCanvas3));
        openUserCanvasesViewList.add(new OpenUserCanvasesView(openCanvas4));

        final UserView userView = new UserView(new User(){
            {
                setUserId(4L);
            }
        });

        userView.setOpenUserCanvases(openUserCanvasesViewList);
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/canvases/open";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userView))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        apiResultActions.andExpect(status().isOk());
    }


    /*
     * Summary: The below method is to test Saving Active Canvas for a User
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveActiveCanvasesForUser() throws Exception {

        User u = new User();
        u.setUserId(4L);
        Canvas canvas = new Canvas();
        canvas.setCanvasId(1L);
        CanvasView cv = new CanvasView(canvas);

        UserView userView = new UserView(u);
        userView.setActiveCanvas(cv);
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/canvases/active";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(userView)).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug(actualJsonString);
        Assert.notNull(actualJsonString, "Resulted Json is Null");
        apiResultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetActiveCanvasForUser() throws Exception {

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/users/jack123/canvases/active";
        final ResultActions apiResultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);

        JSONObject response = new JSONObject(apiResultActions.andReturn().getResponse().getContentAsString());
        // Assert API call was successful
        org.junit.Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        org.junit.Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        org.junit.Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        org.junit.Assert.assertTrue("API response code was not '1017'", response.get("code").equals("1017"));

        org.junit.Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        org.junit.Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        org.junit.Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        JSONObject data = new JSONObject(response.get("data").toString());

        org.junit.Assert.assertTrue("Data element does not contain property 'canvasName'", data.has("canvasName"));
        org.junit.Assert.assertTrue("Data element does not contain property 'canvasName'", data.get("canvasName").equals("JackCanvas405"));
        org.junit.Assert.assertTrue("Data element does not contain property 'canvasId'", data.has("canvasId"));
        org.junit.Assert.assertTrue("Data element does not contain property 'canvasId'", data.get("canvasId").equals(405));
        org.junit.Assert.assertTrue("Data element does not contain property 'canvasType'", data.has("canvasType"));
        org.junit.Assert.assertTrue("Data element does not contain property 'canvasType'", data.get("canvasType").equals("PRIVATE"));
        org.junit.Assert.assertTrue("Data element does not contain property 'shortName'", data.has("shortName"));
        org.junit.Assert.assertTrue("Data element does not contain property 'shortName'", data.get("shortName").equals("JackCanvas405"));



    }


    /**
     * Method to test the saving of user preferences happy path
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUserPreferences() throws Exception {

        Map<String,String> map = new HashMap<String,String>();
        map.put("userName", USERNAME_JACK);
        map.put("dateFormat", "YYYY-MM-DD");
        map.put("timeFormat", "12HR");
        map.put("dataRefreshFrequency", "200");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/save";

        ResultActions authResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(map))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testSaveUserPreferences: controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = authResultActions.andReturn().getResponse().getContentAsString();

        authResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1033"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("User preferences saved successfully"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }


    /**
     * Method to test the saving of user preferences with an invalid user name
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUserPreferencesWithInvalidUserName() throws Exception {

        Map<String,String> map = new HashMap<String,String>();
        map.put("userName", "AN-INVALID-USER-NAME");
        map.put("dateFormat", "YYYY-MM-DD");
        map.put("timeFormat", "12HR");
        map.put("dataRefreshFrequency", "200");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/save";

        ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(map))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testSaveUserPreferencesWithInvalidUserName: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1032"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("No user preferences exist"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }

    /**
     * Method to test the saving of user preferences with a null user name
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUserPreferencesWithNullUserName() throws Exception {

        Map<String,String> map = new HashMap<String,String>();
        map.put("userName", null);
        map.put("dateFormat", "YYYY-MM-DD");
        map.put("timeFormat", "12HR");
        map.put("dataRefreshFrequency", "200");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/save";

        ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(map))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testSaveUserPreferencesWithNullUserName: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1032"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("No user preferences exist"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }

    /**
     * Method to test the saving of user preferences with a null user name
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUserPreferencesWithEmptyUserName() throws Exception {

        Map<String,String> map = new HashMap<String,String>();
        map.put("userName", "");
        map.put("dateFormat", "YYYY-MM-DD");
        map.put("timeFormat", "12HR");
        map.put("dataRefreshFrequency", "200");

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/save";

        ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(map))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testSaveUserPreferencesWithNullUserName: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1032"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("No user preferences exist"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }


    /**
     * Method to test the retrieving of user preferences happy path
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserPreferences() throws Exception {

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/" + USERNAME_JACK;

        ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testGetUserPreferences: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1031"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("User preferences retrieved successfully"));

        JSONObject data = new JSONObject(jsonObject.get("data").toString());

        org.junit.Assert.assertTrue("Data element does not contain property 'dateFormat'", data.has("dateFormat"));
        org.junit.Assert.assertTrue("Data element does not contain property 'dateFormat'", data.get("dateFormat").equals("DD-MM-YYYY"));
        org.junit.Assert.assertTrue("Data element does not contain property 'timeFormat'", data.has("timeFormat"));
        org.junit.Assert.assertTrue("Data element does not contain property 'timeFormat'", data.get("timeFormat").equals("24HR"));
        org.junit.Assert.assertTrue("Data element does not contain property 'dataRefreshFrequency'", data.has("dataRefreshFrequency"));
        org.junit.Assert.assertTrue("Data element does not contain property 'dataRefreshFrequency'", data.get("dataRefreshFrequency").equals("120"));
    }

    /**
     * Method to test the retrieving of user preferences with null username
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserPreferencesWithNullUsername() throws Exception {

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/null";

        ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testGetUserPreferencesWithNullUsername: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1032"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("No user preferences exist"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }

    /**
     * Method to test the retrieving of user preferences with empty username
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetUserPreferencesWithEmptyUsername() throws Exception {

        String token = generateTokenWithAuthenticatedUser();
        String url = "/users/preferences/{}";

        ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("UserControllerFunctionTest.testGetUserPreferencesWithEmptyUsername: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1032"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("No user preferences exist"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));

    }

    /* Test method to pull back expected results and verify the response results
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAllUsersProperties()throws Exception{
        final List<String> userPropList=new ArrayList<String>();
        userPropList.add("firstName");
        userPropList.add("lastName");
        userPropList.add("createdByUsername");
        userPropList.add("createdDateTime");
        userPropList.add("updatedByUsername");
        userPropList.add("updatedDateTime");
        userPropList.add("skill");
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/users/userproperties";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userPropList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("testGetAllUsersProperties() controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        final String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<List<Map<String, String>>> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<List<Map<String, String>>>>() {});

        Assert.notNull(actualJsonString, "Resulted Json is Null");

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "User properties retrieve error");
        Assert.isTrue(obj.get("data").toString().contains("firstName"));
        Assert.isTrue(obj.get("data").toString().contains("lastName"));
        Assert.isTrue(obj.get("data").toString().contains("skill"));

        JSONArray dataArray = obj.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains(USERNAME_JILL))
            {
            Assert.isTrue(dataArray.getString(i).toString().contains("Jill"));
            Assert.isTrue(dataArray.getString(i).toString().contains("User1"));
            }
        }
    }

    /**
     * This method test all user filter types being applied together.
     * The expected result is jack123
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAllFilterTypesSearchAndSortCriteriaCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> preferencesMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> dateMap = new LinkedHashMap<String, Object>();


        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("FR_FR", "EN_US"));

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("jac%"));

        preferencesMap.put("filterType", FilterType.NUMERIC);
        preferencesMap.put("values", Arrays.asList("=120")); // dataRefreshFrequency

        dateMap.put("filterType", FilterType.DATE);
        dateMap.put("values", Arrays.asList("2014-01-01"));

        searchMap.put("j2uLanguage", langMap);
        searchMap.put("userName", userNameMap);
        searchMap.put("dataRefreshFrequency", preferencesMap);
        searchMap.put("startDate", dateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }


    /**
     * Method to verify that the Filter user with shift in ("moning") works as expected
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByShiftEnumerationSearchAndSortCriteriaCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> shiftList = Arrays.asList("morning");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> shiftMap = new LinkedHashMap<String, Object>();

        shiftMap.put("filterType", FilterType.ENUMERATION);
        shiftMap.put("values", shiftList);

        searchMap.put("shift", shiftMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject shiftNameObj = (JSONObject) dataObj.getJSONObject("grid").get("6"); // grid column 6 is shiftName
        JSONArray shiftNameJsonArray = shiftNameObj.getJSONArray("values");
        Assert.isTrue(shiftNameJsonArray.toString().contains("morning"));
    }


    /**
     * Method to verify that the Filter user with j2uLanguage in (FR_FR and EN_US) and also username like jac%
     * The result should not matched any records with the filter search criteria
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByEnumerationSearchAndSortCriteriaCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> languageList = Arrays.asList("FR_FR", "EN_US");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", languageList);

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("jac%"));

        searchMap.put("j2uLanguage", langMap);
        searchMap.put("userName", userNameMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("j2uLanguage", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }


    /**
     * Method to verify that the Filter user with j2uLanguage in (FR_FR and EN_US) and also username like j%
     * The result should matched any records with the combo filter search criteria
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByEnumerationAndAlphaNumericFiltersSearchAndSortCriteriaWithUsersMatchingCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> languageList = Arrays.asList("FR_FR", "EN_US");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", languageList);

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("j%"));

        searchMap.put("j2uLanguage", langMap);
        searchMap.put("userName", userNameMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("j2uLanguage", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName

        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
        Assert.isTrue(userNameJsonArray.toString().contains("jill123"));
        Assert.isTrue(userNameJsonArray.toString().contains("joe123"));

    }



    /**
     * Method to verify that the Filter user with ALPHANUMERIC with start with character
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAlphaNumericFilterStartWithBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("jac%");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("userName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }


    /**
     * Method to verify that the Filter user with ALPHANUMERIC with contains charaters
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAlphaNumericFilterContainsBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("%jac%");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("userName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }

    /**
     * Method to verify that the Filter user with ALPHANUMERIC with end with characters (-ve result)
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAlphaNumericFilterEndWithBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("%jac");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("userName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.doesNotContain(userNameJsonArray.toString(), "jack123");
    }


    /**
     * Method to verify that the Filter user with ALPHANUMERIC with end with characters (+ve result)
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAlphaNumericFilterEndWithPositiveResultBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("%ack123");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("userName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }



    /**
     * Method to verify that the Filter user with ALPHANUMERIC with end with charaters
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByAlphaNumericFilterExactValueBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("jack123");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("userName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");
        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject userNameObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column 1 is userName
        JSONArray userNameJsonArray = userNameObj.getJSONArray("values");
        Assert.isTrue(userNameJsonArray.toString().contains("jack123"));
    }


    /**
     * Method to verify that the Filter user with NUMERIC with Equal value
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByNumericFilterWithEqualBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList("=1");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.NUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("shift.shiftId", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject shiftIdObj = (JSONObject) dataObj.getJSONObject("grid").get("6"); // grid column 6 is shiftId
        JSONArray shiftJsonArray = shiftIdObj.getJSONArray("values");
        Assert.isTrue(shiftJsonArray.toString().contains("morning"));

    }


    /**
     * Method to verify that the Filter user with NUMERIC with greater than number value
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByNumericFilterWithEqualByGreaterThanValueBySearchAndSortCriteria() throws Exception {

        final List<String> searchList = Arrays.asList(">150");

        ObjectMapper objectMapper = new ObjectMapper();

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.NUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("dataRefreshFrequency", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject preferenceIdObj = (JSONObject) dataObj.getJSONObject("grid").get("19"); // grid column 19 is dataRefreshFrequency
        JSONArray preferenceJsonArray = preferenceIdObj.getJSONArray("values");

        for (int i = 0; i < preferenceJsonArray.length(); i++)
        {
            Assert.isTrue(Long.parseLong(preferenceJsonArray.getString(i)) > 150, "The retrieved preferenceId is outside expected range");
        }

    }


    /**
     * Method to verify that the Filter by Date ranges is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByDateRangeSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        //2015-01-27T14:27:03
        final List<String> dateList = Arrays.asList("2013-01-27T00:00:00", "2015-03-27T23:59:59");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("14"); // grid column number 14 is startDate

        // get the StartDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            // Assert that the dates is between "2013-01-27", "2015-03-27" as expected
            DateTime date1 = new DateTime("2013-01-27");
            DateTime date2 = new DateTime(dateArray.getString(i));
            LocalDate firstDate = date1.toLocalDate();
            LocalDate secondDate = date2.toLocalDate();

            Assert.isTrue(firstDate.compareTo(secondDate) == -1, "The retrieved date is outside expected range");

            DateTime date3 = new DateTime("2015-03-27");
            DateTime date4 = new DateTime(dateArray.getString(i));
            LocalDate thirdDate = date3.toLocalDate();
            LocalDate fourthDate = date4.toLocalDate();

            Assert.isTrue(thirdDate.compareTo(fourthDate) == 1, "The retrieved date is outside expected range");
        }
    }


    /**
     * Method to verify that the Filter by today date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByTodayEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("TODAY");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> createdDateTimeMap = new LinkedHashMap<String, Object>();

        createdDateTimeMap.put("filterType", FilterType.DATE);
        createdDateTimeMap.put("values", dateList);

        searchMap.put("createdDateTime", createdDateTimeMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("1"); // grid column number 1 is userName

        // get the StartDate array to test for validity of results
        JSONArray dataArray = dateObj.getJSONArray("values");

        Assert.isTrue(dataArray.get(0).equals("admin123"));

    }


    /**
     * Method to verify that the Filter by yesterday date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByYesterdayEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("YESTERDAY");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");


        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("15"); // grid column number 15 is birthDate

        // get the birthDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            LocalDate retrievedDate = ISODateTimeFormat.dateTime().parseLocalDate(dateArray.getString((i)));
            LocalDate yesterday = LocalDate.now().plusDays(-1);
            Assert.isTrue(retrievedDate.compareTo(yesterday) == 0, "The retrieved date is outside expected range");
        }

    }


    /**
     * Method to verify that the Filter by this week date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByThisWeekDateEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("THIS_WEEK");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");


        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("15"); // grid column number 15 is birthDate

        // get the birthDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            LocalDate retrievedDate = ISODateTimeFormat.dateTime().parseLocalDate(dateArray.getString((i)));
            LocalDate yesterday = LocalDate.now().plusDays(-1);

            LocalDate localToday = new LocalDate();
            LocalDate weekStart = localToday.dayOfWeek().withMinimumValue();
            LocalDate weekEnd = localToday.dayOfWeek().withMaximumValue();

            Assert.isTrue(retrievedDate.compareTo(weekStart) >= 0, "The retrieved date is outside expected range");
            Assert.isTrue(retrievedDate.compareTo(weekEnd) <= 0, "The retrieved date is outside expected range");
        }

    }



    /**
     * Method to verify that the Filter by last week date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByLastWeekDateEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("LAST_WEEK");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");


        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("15"); // grid column number 15 is birthDate

        // get the birthDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            LocalDate today = new LocalDate();
            LocalDate thisWeekStart = today.dayOfWeek().withMinimumValue();
            LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
            LocalDate startOfPreviousWeek = lastWeekStart.dayOfWeek().withMinimumValue();
            LocalDate endOfPreviousWeek = lastWeekStart.dayOfWeek().withMaximumValue().plusDays(1);

            LocalDate retrievedDate = ISODateTimeFormat.dateTime().parseLocalDate(dateArray.getString((i)));
            Assert.isTrue(retrievedDate.compareTo(startOfPreviousWeek) >= 0, "The retrieved date is outside expected range");
            Assert.isTrue(retrievedDate.compareTo(endOfPreviousWeek) <= 0, "The retrieved date is outside expected range");
        }

    }


    /**
     * Method to verify that the Filter by this month date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByThisMonthDateEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("THIS_MONTH");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");


        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("15"); // grid column number 15 is birthDate

        // get the birthDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            LocalDate today = new LocalDate();
            LocalDate startOfMonth = today.dayOfMonth().withMinimumValue();
            LocalDate endOfMonth = today.dayOfMonth().withMaximumValue();

            LocalDate retrievedDate = ISODateTimeFormat.dateTime().parseLocalDate(dateArray.getString((i)));
            Assert.isTrue(retrievedDate.compareTo(startOfMonth) >= 0, "The retrieved date is outside expected range");
            Assert.isTrue(retrievedDate.compareTo(endOfMonth) <= 0, "The retrieved date is outside expected range");
        }

    }


    /**
     * Method to verify that the Filter by this month date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByLastMonthDateEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("LAST_MONTH");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("15"); // grid column number 15 is birthDate

        // get the birthDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            LocalDate firstDayOfThisMonth = new LocalDate().withDayOfMonth(1);
            LocalDate lastMonth = firstDayOfThisMonth.minusMonths(1);
            LocalDate startOfLastMonth = lastMonth.dayOfMonth().withMinimumValue();
            LocalDate endOfLastMonth = lastMonth.dayOfMonth().withMaximumValue();

            LocalDate retrievedDate = ISODateTimeFormat.dateTime().parseLocalDate(dateArray.getString((i)));
            Assert.isTrue(retrievedDate.compareTo(startOfLastMonth) >= 0, "The retrieved date is outside expected range");
            Assert.isTrue(retrievedDate.compareTo(endOfLastMonth) <= 0, "The retrieved date is outside expected range");
        }

    }


    /**
     * Method to verify that the Filter by gibberish date is working correctly
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByGibberishDateEnumSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("GIBBERISH");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", FilterType.DATE);
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1070"), "status is not 1070");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Invalid search map arguments"), "Invalid search map arguments");
    }

    /**
     * Method to test the Boolean enum for True value
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByBooleanEnumForTrueValueSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> booleanFieldValueList = Arrays.asList("1");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> booleanMap = new LinkedHashMap<String, Object>();

        booleanMap.put("filterType", FilterType.BOOLEAN);
        booleanMap.put("values", booleanFieldValueList);

        searchMap.put("enable", booleanMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject enableObj = (JSONObject) dataObj.getJSONObject("grid").get("11"); // grid column number 11 is enable

        // get the retrain array to test for validity of results
        JSONArray booleanArray = enableObj.getJSONArray("values");

        for (int i = 0; i < booleanArray.length(); i++)
        {
            Assert.isTrue(booleanArray.getString(i).equals("1"), "The retrieved data is not as expected");
        }

    }

    /**
     * Method to test the Boolean enum for False value
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByBooleanEnumForFalseValueSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> booleanFieldValueList = Arrays.asList("0");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> booleanMap = new LinkedHashMap<String, Object>();

        booleanMap.put("filterType", FilterType.BOOLEAN);
        booleanMap.put("values", booleanFieldValueList);

        searchMap.put("enable", booleanMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONObject enableObj = (JSONObject) dataObj.getJSONObject("grid").get("11"); // grid column number 11 is enable

        // get the retrain array to test for validity of results
        JSONArray booleanArray = enableObj.getJSONArray("values");

        for (int i = 0; i < booleanArray.length(); i++)
        {
            Assert.isTrue(booleanArray.getString(i).equals("0"), "The retrieved data is not as expected");
        }

    }


    /**
     * Method to test for the invalid filter type functionality
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByInvalidFilterTypeSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("TODAY");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> birthDateMap = new LinkedHashMap<String, Object>();

        birthDateMap.put("filterType", "INVALID_FILTER_TYPE");
        birthDateMap.put("values", dateList);

        searchMap.put("birthDate", birthDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("1069"), "status is not 1069");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not failure");
        Assert.isTrue(obj.get("message").toString().equals("Invalid filter type arguments"), "Invalid filter type arguments");
    }


    /**
     * Method to test dates range in wrong order can be swapped around correctly
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    public void testGetPropertyByDateRangeInWrongOrderSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        //2015-01-27T14:27:03
        final List<String> dateList = Arrays.asList("2015-03-27T23:59:59", "2013-01-27T00:00:00");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("14"); // grid column number 14 is startDate

        // get the StartDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        for (int i = 0; i < dateArray.length(); i++)
        {
            // Assert that the dates is between "2013-01-27", "2015-03-27" as expected
            DateTime date1 = new DateTime("2013-01-27");
            DateTime date2 = new DateTime(dateArray.getString(i));
            LocalDate firstDate = date1.toLocalDate();
            LocalDate secondDate = date2.toLocalDate();

            Assert.isTrue(firstDate.compareTo(secondDate) == -1, "The retrieved date is outside expected range");

            DateTime date3 = new DateTime("2015-03-27");
            DateTime date4 = new DateTime(dateArray.getString(i));
            LocalDate thirdDate = date3.toLocalDate();
            LocalDate fourthDate = date4.toLocalDate();

            Assert.isTrue(thirdDate.compareTo(fourthDate) == 1, "The retrieved date is outside expected range");
        }
    }


    /**
     * Method to test an empty filter type for a date data field with null value
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByEmptyFilterWithDateFieldIsEmptyArrayNullSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.EMPTY);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("14"); // grid column number 14 is startDate

        // get the StartDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        //verify that startDate are nulls for the result set
        for (int i = 0; i < dateArray.length(); i++)
        {
            Assert.isTrue((dateArray.getString(i) == null) || (dateArray.getString(i).isEmpty()), "The retrieved date is not null");
        }
    }

    /**
     * Method to test an empty filter type for a date data field with null array value
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByEmptyFilterWithDateFieldEmptyArrayNullSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();

        // The empty "values" array to be tested
        final List<String> dateList = new ArrayList<String>();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.EMPTY);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("14"); // grid column number 14 is startDate

        // get the StartDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        //verify that startDate are nulls for the result set
        for (int i = 0; i < dateArray.length(); i++)
        {
            Assert.isTrue((dateArray.getString(i) == null) || (dateArray.getString(i).isEmpty()), "The retrieved date is not null");
        }
    }


    /**
     * Method to test an non empty filter type for a date data field
     *
     * @author DiepLe
     *
     * @throws Exception
     */
    @Test
    public void testGetPropertyByNonEmptyFilterWithEmptyArrayDateFieldSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.NON_EMPTY);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        JSONObject dateObj = (JSONObject) dataObj.getJSONObject("grid").get("14"); // grid column number 14 is startDate

        // get the StartDate array to test for validity of results
        JSONArray dateArray = dateObj.getJSONArray("values");

        //verify that startDate are nulls for the result set
        for (int i = 0; i < dateArray.length(); i++)
        {
            Assert.isTrue((dateArray.getString(i) != null) || (!dateArray.getString(i).isEmpty()), "The retrieved date is empty/null");
        }
    }


    /**
     * Method to test a single date filter value (no rows return)
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    public void testGetPropertyBySingleDateValueNotReturningAnyRowsSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("2014-01-02");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        Long records = Long.parseLong(dataObj.get("totalRecords").toString());
        Assert.isTrue(records == 0, "Unexpected record returned - it should be zero");

    }


    /**
     * Method to test a single date filter value (one or more rows return)
     *
     * @author DiepLe
     *
     * @throws Exception
     */

    @Test
    public void testGetPropertyBySingleDateValueFoundSomeRecordsSearchAndSortCriteria() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final List<String> dateList = Arrays.asList("2014-01-01");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", dateList);

        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/users/userlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(objectMapper.writeValueAsString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());

        Long records = Long.parseLong(dataObj.get("totalRecords").toString());

        Assert.isTrue(records > 0, "Unexpected result there should be some records returned");
    }


    /* Test method to update user entity and verify the response results createdByUserName and updateByUsername are set
     *
     * @throws Exception
     *
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSetUpdatedByUsername() throws Exception{
        // Authenticate with user jack123
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);

        // save a new user
        final User user = this.getUserObject();

        UserView userView = new UserView(user);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userView);

        String urlUserSave = "/users/save";
        ResultActions authResultActions = this.mockMvc.perform(post(urlUserSave)
                .header("Authentication-token", token)
                .content(super.getJsonString(userView))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        LOG.debug("**** UserControllerFunctionTest.testSaveUser() **** userView = " + super.getJsonString(userView));

        // Get user detail
        List<String> userNameList = new ArrayList<String>();
        userNameList.add(user.getUserName());

        String urlUserDetail = "/users/details";
        authResultActions = this.mockMvc.perform(post(urlUserDetail)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);

        // Check createdByUsername is set to jack123 and createdDateTime is current date
        JSONArray dataArray = obj.getJSONArray("data");
        LOG.debug("Get user detail for " + user.getUserName());
        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains(user.getUserName()))
            {
                String strCreatedByUserName = ((JSONObject)dataArray.get(0)).get("createdByUserName").toString();
                Assert.isTrue(strCreatedByUserName.equalsIgnoreCase(USERNAME_JACK),"User entity is not created by " + USERNAME_JACK);
                Long longDateTime = Long.parseLong(((JSONObject) dataArray.get(0)).get("createdDateTime").toString());
                DateTime updatedDate = new DateTime(longDateTime);
                DateTime now = new DateTime();
                Assert.isTrue(DateTimeComparator.getDateOnlyInstance().compare(now, updatedDate) == 0, "Date not matching");
            }
        }

        // Update retrain voice
        mapper = new ObjectMapper();
        mapper.writeValueAsString(userNameList);
        String urlUserRetrainVoice = "/users/retrainvoice";
        authResultActions =
                this.mockMvc.perform(post(urlUserRetrainVoice).header("Authentication-token", token).content(super.getJsonString(userNameList)).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());

        // Retrieve user
        authResultActions = this.mockMvc.perform(post(urlUserDetail)
                .header("Authentication-token", token)
                .content(super.getJsonString(userNameList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString2 = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        obj = new JSONObject(jsonString2);

        // Validate updatedByUsername is set to jack123 and updatedDateTime is current date
        dataArray = obj.getJSONArray("data");
        LOG.debug("Validate updatedByUsername is set to jack123");
        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains(user.getUserName()))
            {
                String strUpdatedByUserName2 = ((JSONObject)dataArray.get(0)).get("updatedByUserName").toString();
                Assert.isTrue(strUpdatedByUserName2.equalsIgnoreCase(USERNAME_JACK),"User entity is not updated by " + USERNAME_JACK);
                Long longDateTime2 = Long.parseLong(((JSONObject) dataArray.get(0)).get("updatedDateTime").toString());
                DateTime updatedDate2 = new DateTime(longDateTime2);
                DateTime now2 = new DateTime();
                Assert.isTrue(DateTimeComparator.getDateOnlyInstance().compare(now2, updatedDate2) == 0, "Date not matching");
            }
        }
    }



}
