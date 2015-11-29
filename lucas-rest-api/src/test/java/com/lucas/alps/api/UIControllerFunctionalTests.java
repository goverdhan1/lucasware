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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.view.*;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.canvas.CanvasLayout;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.viewconfig.DefaultViewConfig;
import com.lucas.entity.ui.viewconfig.WidgetOrientation;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringJUnit4ClassRunner.class)
public class UIControllerFunctionalTests extends AbstractControllerTests {

	private static final Logger LOG = LoggerFactory
			.getLogger(UIControllerFunctionalTests.class);
	private static final String USERNAME_JACK = "jack123";
	private static final String USERNAME_JILL = "jill123";
	private static final String SAVE_CANVAS_REQUEST_FILE_PATH = "json/CreateCanvasRequest.json";
	private static final String SAVE_JACK_CANVAS_REQUEST_FILE_PATH = "json/CreateJackCanvasRequest.json";

	DefaultViewConfig defaultViewConfig;
	ActualViewConfig actualViewConfig;
	private WidgetOrientation widgetOrientation;
    private final String EQUIPMENT_TYPE_GRID_WIDGET_NAME = "equipment-type-grid-widget";
	private final String SEARCH_USER_GRID_WIDGET_NAME =  "search-user-grid-widget";
	private final String SEARCH_PRODUCT_GRID_WIDGET_NAME =  "search-product-grid-widget";
    private final String GROUP_MAINTENANCE_WIDGET_NAME =  "group-maintenance-widget";
	private final String PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME = "pickline-by-wave-barchart-widget";
	private final String USER_CREATE_OR_EDIT_WIDGET_NAME = "create-or-edit-user-form-widget";
	private final String SEARCH_GROUP_GRID_WIDGET_NAME = "search-group-grid-widget";
	private final String SEARCH_PRODUCT_GRID_WIDGET_DEFINITION_FILE_PATH = "json/SearchProductGridWidgetDefinition.json";
	private final String CANVASES_BY_CATEGORIES_FOR_JILL_FILE_PATH = "json/CanvasesByCategoriesForJill.json";
	private final String EQUIPMENT_MANAGER_GRID_WIDGET = "equipment-manager-grid-widget";
	private final String MESSAGE_GRID_WIDGET = "message-grid-widget";
	private final String MESSAGE_MAPPING_GRID_WIDGET = "message-mappings-grid-widget";

	private static final String EVENT_GRID_WIDGET_NAME = "event-grid-widget";
	
    private final String EVENT_HANDLER_GRID_WIDGET = "event-handler-grid-widget";


	@SuppressWarnings("rawtypes")
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateWidgetInstance() throws Exception {

		String url = "/widgetinstances";

		DefaultWidgetInstanceView wiv = prepareWidgetInstanceViewData();

		String token = generateTokenWithAuthenticatedUser();


		ResultActions resultActions = this.mockMvc
				.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
						.post(url).content(super.getJsonString(wiv))
						.header("Authentication-token", token)
						.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

		 ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);

		ApiResponse<Map<String,String>> apiResponse =  mapper.readValue(resultActions.andReturn()
				.getResponse().getContentAsString(), new TypeReference<ApiResponse<Map<String,String>>>() { });

		Map<String,String> responseMap = apiResponse.getData();

		resultActions.andExpect(status().isOk());
	}

	@SuppressWarnings("rawtypes")
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateWidgetInstance() throws Exception {

		String url = "/widgetinstances";

		DefaultWidgetInstanceView wiv = prepareWidgetInstanceViewData();
		wiv.setWidgetInstanceId(null);

		String token = generateTokenWithAuthenticatedUser();


		ResultActions resultActions = this.mockMvc
				.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
						.post(url).content(super.getJsonString(wiv))
						.header("Authentication-token", token)
						.contentType(MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());
		ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);

		ApiResponse<Map<String,String>> apiResponse =  mapper.readValue(resultActions.andReturn()
				.getResponse().getContentAsString(), new TypeReference<ApiResponse<Map<String,String>>>() { });

		Map<String,String> responseMap = apiResponse.getData();

		resultActions.andExpect(status().isOk());
	}

	@Test
	public void testGetWidgetDefinitionsAfterLogin() throws Exception {

		String token = generateTokenWithAuthenticatedUser();

		String url = "/widgetdefinitions";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
				"Authentication-token", token).accept(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

		String jsonString = resultActions.andReturn().getResponse()
				.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		ApiResponse<MultiMap> apiResponse = mapper.readValue(jsonString,
				new TypeReference<ApiResponse<MultiValueMap>>() {
				});

		MultiMap widgetDefinitionsByCategoryMap = apiResponse.getData();

		Assert.assertNotNull(widgetDefinitionsByCategoryMap);
		Assert.assertEquals(widgetDefinitionsByCategoryMap.size(), 4);

		resultActions.andExpect(status().isOk())
				.andExpect(
						content().string(
								containsString(String
										.valueOf(widgetDefinitionsByCategoryMap
												.get(0)))));

	}

	@Test
	public void testGetUserCreationFormWidgetDefinition() throws Exception {

		String token = generateTokenWithAuthenticatedUser();

		String url = "/widgets/"+USER_CREATE_OR_EDIT_WIDGET_NAME+"/definition";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
				"Authentication-token", token).accept(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

		String actualJsonString = resultActions.andReturn().getResponse()
				.getContentAsString();


		/*
		 * //Incase we are returning definitionData as MultiMap we need to write
		 * explicit deserializer
		 * 
		 * ObjectMapper mapper = new ObjectMapper();
		 * mapper.setSerializationInclusion(Include.NON_NULL);
		 * ApiResponse<UserCreationFormWidgetView> apiResponse =
		 * mapper.readValue(jsonString, new
		 * TypeReference<ApiResponse<UserCreationFormWidgetView>>() { });
		 * UserCreationFormWidgetView userCreationFormWidgetView =
		 * apiResponse.getData();
		 * org.junit.Assert.assertNotNull(userCreationFormWidgetView);
		 */
		Assert.assertNotNull("User creation form widget definition response from the server is null",actualJsonString);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualTree = mapper.readTree(actualJsonString);
		JsonNode actionConfigNodeActualVale = actualTree.findValue("widgetActionConfig");
		Assert.assertNotNull("User creation form widget definition response for Jack does not have widgetActionConfig node values", actionConfigNodeActualVale
				);
		String actionConfigNodeExpectedJson = "{\"widget-access\":{\"create-edit-user-widget-access\":true},\"widget-actions\":{\"create-user\":true,\"edit-user\":true}}";
		JsonNode actionConfigNodeExpectedValue = mapper.readTree(actionConfigNodeExpectedJson);


		Assert.assertTrue("Action config values returned are not as per the expectaion",
				actionConfigNodeActualVale.equals(actionConfigNodeExpectedValue));

		// Test if the listens for list exists inside the defaultViewConfig
		JsonNode listensForListNodeValues = actualTree
				.findValue("defaultViewConfig").findPath("listensForList");
		Assert.assertNotNull(
				"listenForList does not exist inside the defautViewConfig",
				listensForListNodeValues);
		// Test if the listens for list has the userName

		JsonNode listenForListNodeActualValues = actualTree
				.findValue("listensForList");
		Assert.assertNotNull(
				"User creation form widget definition response for Jack does not have listensForList node values",
				listenForListNodeActualValues);
		String listenForListNodeExpectedJson = "[\"userName\"]";
		JsonNode listenForListNodeExpectedValues = mapper
				.readTree(listenForListNodeExpectedJson);

		Assert.assertTrue(
				"Listen for list returned values are not as per the expectaion",
				listenForListNodeActualValues
						.equals(listenForListNodeExpectedValues));

		// Test if the broadcastList has the userName

		JsonNode broadcastListNodeActualValues = actualTree
				.findValue("broadcastList");
		Assert.assertNotNull(
				"User creation form widget definition response for Jack does not have broadcastList node values",
				broadcastListNodeActualValues);
		String broadcastListNodeExpectedJson = "[\"userName\"]";
		JsonNode broadcastListNodeExpectedValue = mapper
				.readTree(broadcastListNodeExpectedJson);

		Assert.assertTrue(
				"Broadcast list returned values are not as per the expectaion",
				broadcastListNodeActualValues
						.equals(broadcastListNodeExpectedValue));

		// Test if the reactToMap has the expected values

        JsonNode reactToListNodeActualValues = actualTree
                .findValue("reactToList");
        Assert.assertNotNull(
                "User creation form widget definition response for Jack does not have broadcastList node values",
                broadcastListNodeActualValues);
        String reactToListNodeExpectedJson = "[\"userName\"]";
        JsonNode reactToListNodeExpectedValue = mapper
                .readTree(reactToListNodeExpectedJson);

        Assert.assertTrue(
                "React To list returned values are not as per the expectation",
                reactToListNodeActualValues
                        .equals(reactToListNodeExpectedValue));

	}


	@Test
	public void testGetSearchUserGridWidgetDefinition() throws Exception {

		String token = generateTokenWithAuthenticatedUser();

		String url = "/widgets/"+SEARCH_USER_GRID_WIDGET_NAME+"/definition";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
				"Authentication-token", token).accept(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();

		JSONObject response = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());

        //Assert API call was successful
        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Search User"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("search-user-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        String reactToList = data.getString("reactToList").replace("[","").replace("]","").replace("\"","");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("userName"));
    }


    @Test
    public void testGetEquipmentTypeGridWidgetDefinition() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/widgets/"+EQUIPMENT_TYPE_GRID_WIDGET_NAME+"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}",jsonString);

        final JSONObject response = new JSONObject(jsonString);
        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        final JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Equipment Type"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("equipment-type-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        final JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        final JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        final String reactToList = data.getString("reactToList").replace("[","").replace("]","").replace("\"","");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("equipmentTypeName"));
    }

	@Test
	public void testGetSearchProductGridWidgetDefinition() throws Exception {

		String token = generateTokenWithAuthenticatedUser();

		String url = "/widgets/"+SEARCH_PRODUCT_GRID_WIDGET_NAME+"/definition";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
				"Authentication-token", token).accept(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

		String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();

		Assert.assertNotNull("Response for search user grid widget definition from the server is null", actualJsonString);

		 ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);
	     ApiResponse<SearchProductGridWidgetView> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<SearchProductGridWidgetView>>() { });

		ObjectMapper actionConfigMapper = new ObjectMapper();
		JsonNode actualTree = actionConfigMapper.readTree(actualJsonString);
		JsonNode actionConfigNodeActual = actualTree.findValue("widgetActionConfig");
		Assert.assertNotNull("Search product grid widget definition response for Jack does not have widgetActionConfig node values", actionConfigNodeActual
				);
		String actionConfigJsonExpected = "{\"widget-access\":{\"search-product-grid-widget-access\":true},\"widget-actions\":{\"create-product\":true,\"view-product\":true,\"edit-product\":true,\"delete-product\":true}}";
		JsonNode actionConfigNodeExpectedValue = actionConfigMapper.readTree(actionConfigJsonExpected);


		Assert.assertTrue("Action config returned values are not as per the expectaion",
				actionConfigNodeActual.equals(actionConfigNodeExpectedValue));


		resultActions.andExpect(status().isOk()).andExpect(
                content().string(
                        containsString(String
                                .valueOf(apiResponse.getData().getDataURL().getUrl()))));
	}


	@Test
	public void testGetPicklineByWaveBarChartWidgetDefinition() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", USERNAME_JACK);
		map.put("password", "secret");
		ResultActions authResultActions = this.mockMvc.perform(post(
				"/authenticate").content(super.getJsonString(map)).contentType(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", authResultActions.andReturn()
				.getResponse().getContentAsString());

		ApiResponse authResponse = super.jsonToObject(authResultActions
				.andReturn().getResponse().getContentAsString());

		String token = authResponse.getToken().trim();

		String url = "/widgets/"+PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME+"/definition";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());

		String jsonString = resultActions.andReturn().getResponse()
				.getContentAsString();

		org.junit.Assert.assertNotNull(jsonString);

		 ObjectMapper mapper = new ObjectMapper();
	     mapper.setSerializationInclusion(Include.NON_NULL);
	     ApiResponse<PicklineByWaveBarChartWidgetView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<PicklineByWaveBarChartWidgetView>>() { });

		resultActions.andExpect(status().isOk()).andExpect(
				content().string(
						containsString(String
								.valueOf(apiResponse.getData().getName()))));;
	}


    @Test
    public void testOrientation() throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", USERNAME_JACK);
        map.put("password", "secret");
        ResultActions authResultActions = this.mockMvc.perform(post(
                "/authenticate").content(super.getJsonString(map)).contentType(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn()
                .getResponse().getContentAsString());

        ApiResponse authResponse = super.jsonToObject(authResultActions
                .andReturn().getResponse().getContentAsString());

        String token = authResponse.getToken().trim();

        String url = "/widgets/"+PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME+"/definition";
        ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());

        String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();

        org.junit.Assert.assertNotNull(jsonString);
        org.springframework.util.Assert.isTrue((jsonString.contains("orientation")),"Json Doesn't Contain the orientation");
        org.springframework.util.Assert.isTrue((jsonString.contains("option")), "Json Doesn't Contain the option");
        org.springframework.util.Assert.isTrue((jsonString.contains("selected")),"Json Doesn't Contain the selected");
    }

	private DefaultWidgetInstanceView prepareWidgetInstanceViewData()
			throws JsonProcessingException {

		DefaultWidgetInstanceView wiv = new DefaultWidgetInstanceView();
		wiv.setWidgetInstanceId(1L);

        List<String> listensFor = new ArrayList<String>();
        listensFor.add("Area");
        listensFor.add("Shift");
        listensFor.add("Picker");

        CanvasView cv = new CanvasView();
		cv.setCanvasId(1l);
		cv.setName("Perishable Goods Canvas");
		cv.setShortName("shortName");
		cv.setCreatedByUserName("createdByUserName");
		cv.setCanvasType(CanvasType.PRIVATE);
		cv.setCreatedDateTime(new Date());
		CanvasLayout canvasLayout = new CanvasLayout(1L, new Boolean(false), 1);
		cv.setCanvasLayout(new CanvasLayoutView(canvasLayout));

		wiv.setCanvas(cv);

		List<Integer> anchorList = new ArrayList<Integer>();
		anchorList.add(2);
		anchorList.add(3);

		List<Map<String, String>> options = new ArrayList<Map<String, String>>();
		Map<String, String> optionsMap1 = new HashMap<String, String>();
		optionsMap1.put("legend", "horizontal");
		optionsMap1.put("value", "h");
		Map<String, String> optionsMap2 = new HashMap<String, String>();
		optionsMap2.put("legend", "vertical");
		optionsMap2.put("value", "v");
		options.add(optionsMap1);
		options.add(optionsMap2);

		widgetOrientation = new WidgetOrientation(options, "h");

		defaultViewConfig = new DefaultViewConfig(anchorList, 2L, 2L, 2L,
				widgetOrientation);
		actualViewConfig = new ActualViewConfig(anchorList, 2L, 2L, 2L,
				listensFor);

		ActualViewConfigView avcv = new ActualViewConfigView();
		avcv.setAnchor(anchorList);
		avcv.setMinimumHeight(2L);
		avcv.setMinimumWidth(2L);
		avcv.setListensForList(listensFor);
		avcv.setZindex(1L);
		wiv.setActualViewConfig(avcv);

		AbstractLicensableWidgetView abstractLicensableWidgetView = new AbstractLicensableWidgetView();
		abstractLicensableWidgetView.setId(1l);
		abstractLicensableWidgetView.setName("user-productivity-widget");
		wiv.setWidget(abstractLicensableWidgetView);
		wiv.setDelete(Boolean.TRUE);
        wiv.getDefaultWidgetInstance().getWidget().setReactToList(listensFor);
        wiv.getDefaultWidgetInstance().getWidget().setBroadcastList(listensFor);

        List<Map<String, String>> widgetInteractionConfig = new ArrayList<Map<String, String>>();
        Map<String, String> wic1 = new HashMap<String, String>();
        wic1.put("widgetInstanceId", "1");
        wic1.put("dataElement", "userName");
        Map<String, String> wic2 = new HashMap<String, String>();
        wic2.put("widgetInstanceId", "3");
        wic2.put("dataElement", "groupName");
        widgetInteractionConfig.add(wic1);
        widgetInteractionConfig.add(wic2);

        wiv.setWidgetInteractionConfig(widgetInteractionConfig);
		return wiv;
	}

    @Test
    public void testGroupMaintenanceWidgetDefinition() throws Exception {

        final String token = super.generateTokenWithAuthenticatedUser();

        final String url = "/widgets/"+ GROUP_MAINTENANCE_WIDGET_NAME +"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();

        org.junit.Assert.assertNotNull(jsonString);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        final ApiResponse<GroupMaintenanceWidgetView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<GroupMaintenanceWidgetView>>() { });

        resultActions.andExpect(status().isOk()).andExpect(
                content().string(
                        containsString(String
                                .valueOf(apiResponse.getData().getName()))));;
        final GroupMaintenanceWidgetView groupMaintenanceWidget=apiResponse.getData();
        org.springframework.util.Assert.notNull(groupMaintenanceWidget, "GroupMaintenanceWidget is Null");
        org.springframework.util.Assert.isTrue(groupMaintenanceWidget.getId() == 12, "Id is not Proper for GroupMaintenanceWidget");
        org.springframework.util.Assert.notNull(groupMaintenanceWidget.getBroadcastList(), "GroupMaintenanceWidget BroadCast List is Null");
        org.springframework.util.Assert.isTrue(groupMaintenanceWidget.getBroadcastList().contains("groupName"), "GroupMaintenanceWidget Doesn't Contain GroupName in BroadCast List");
        org.springframework.util.Assert.isTrue(groupMaintenanceWidget.getShortName().equals("GroupMaintenance"), "GroupMaintenanceWidget Doesn't Contain Proper Short Name");
        org.springframework.util.Assert.isTrue(groupMaintenanceWidget.getName().equals("group-maintenance-widget"), "GroupMaintenanceWidget Doesn't Contain Proper Name");
        org.springframework.util.Assert.isTrue(groupMaintenanceWidget.getTitle().equals("Group Maintenance"), "GroupMaintenanceWidget Doesn't Contain Proper Title");
    }

    @SuppressWarnings("rawtypes")
   	@Test
   	@Transactional
   	@Rollback(true)
   	public void testCreateOrUpdateWidgetInstances() throws Exception {
       	String url = "/canvases/widgetinstances";

   		DefaultWidgetInstanceView wiv = prepareWidgetInstanceViewData();
   		wiv.setWidgetInstanceId(null);

   		DefaultWidgetInstanceView wiv1 = prepareWidgetInstanceViewData();

   		DefaultWidgetInstanceView [] wivarr = {wiv, wiv1};

   		String token = generateTokenWithAuthenticatedUser();

   		ResultActions resultActions = this.mockMvc
   				.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
   						.post(url).content(super.getJsonString(wivarr))
   						.header("Authentication-token", token)
   						.contentType(MediaType.APPLICATION_JSON));
   		LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());
   		ObjectMapper mapper = new ObjectMapper();
   		mapper.setSerializationInclusion(Include.NON_NULL);

   		ApiResponse<List<Map<String, String>>> apiResponse = mapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<ApiResponse<List<Map<String, String>>>>() {
                });

   		List<Map<String, String>> responseMaps = apiResponse.getData();

   		Assert.assertTrue(
                "Create or update widget instances response maps size is not as expected",
                responseMaps.size() == 2);
        final String actualJsonString=resultActions.andReturn()
                .getResponse().getContentAsString();
        final JSONObject obj = new JSONObject(actualJsonString);
        Assert.assertTrue("status is not 200", obj.get("code").toString().equals("200"));
        Assert.assertTrue("status is not failure", obj.get("status").toString().equals("success"));
        Assert.assertTrue("widgetInstanceId 8 not Found ", obj.get("data").toString().contains("{\"widgetInstanceId\":\"8\""));
        Assert.assertTrue("widgetInstanceAction create is not Found", obj.get("data").toString().contains("\"widgetInstanceAction\":\"create\""));
        Assert.assertTrue("widgetInstanceId 1 not Found ", obj.get("data").toString().contains("{\"widgetInstanceId\":\"1\""));
        Assert.assertTrue("widgetInstanceAction update is not Found", obj.get("data").toString().contains("\"widgetInstanceAction\":\"update\""));
   	}


    /**
          * @Author Adarsh kumar
          *
          * deleteCanvasById() provide the functionality testing for deletion of the
          * canvas of persisting state from database using the UIController.deleteCanvasById();
          * which intern use UIService.deleteCanvasByIdOrName();
          *
          *  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/canvas/delete/1
          *
          * @throws Exception
          */
        @Test
        @Rollback(value = true)
        @Transactional
        public void deleteCanvasById()throws Exception{
          /*
          getting the authentication token its the simulation for login process
          after successful login its generate the authentication token it will
          send back with the request of rest api service.
         */

                final String token = super.generateTokenWithAuthenticatedUser();
                final String deleteServiceUrl="/canvas/delete/"+new Long(1);
                final ResultActions resultActions = this.mockMvc.perform(post(deleteServiceUrl)
                        .header("Authentication-token", token).accept(
                                MediaType.APPLICATION_JSON));
                LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
                final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
                org.junit.Assert.assertNotNull(jsonString, "Deletion Acknowledgement Json is Null");
                resultActions.andExpect(status().isOk());
         }

        @Test
    	public void testGetCanvasesByCategories() throws Exception {

    		Map<String, String> map = new HashMap<String, String>();
    		map.put("username", USERNAME_JACK);
    		map.put("password", "secret");
    		ResultActions authResultActions = this.mockMvc.perform(post(
    				"/authenticate").content(super.getJsonString(map)).contentType(
                    MediaType.APPLICATION_JSON));
    		LOG.debug("controller returns : {}", authResultActions.andReturn()
    				.getResponse().getContentAsString());

    		ApiResponse authResponse = super.jsonToObject(authResultActions
                    .andReturn().getResponse().getContentAsString());

    		final String token = authResponse.getToken().trim();

    		String url = "/canvasesbycategories";
    		ResultActions resultActions = this.mockMvc.perform(get(url).header(
    				"Authentication-token", token).accept(
    				MediaType.APPLICATION_JSON));
            final String jsonString=resultActions.andReturn()
                    .getResponse().getContentAsString();
            LOG.debug("controller returns : {}",jsonString );

    		resultActions.andExpect(status().isOk());
            org.junit.Assert.assertNotNull(jsonString, "Canvas Data Json is Null");
            resultActions.andExpect(status().isOk());
            final JSONObject jsonObject = new JSONObject(jsonString);
            Assert.assertTrue(jsonObject.has("status"));
            Assert.assertTrue(jsonObject.get("status").equals("success"));
            Assert.assertTrue(jsonObject.has("code"));
            Assert.assertTrue(jsonObject.get("code").equals("200"));
            Assert.assertTrue(jsonObject.has("message"));
            Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
            Assert.assertTrue(jsonObject.has("data"));
            final JSONObject object = jsonObject.getJSONObject("data");
            Assert.assertTrue(((JSONObject)jsonObject.get("data")).has("lucas"));
    		final JSONArray lucasCanvasArray=object.getJSONArray("lucas");
            Assert.assertTrue("Valid Lucas Canvas",lucasCanvasArray.length()==1);
            Assert.assertTrue(((JSONObject)jsonObject.get("data")).has("company"));
    		final JSONArray companyCanvasArray=object.getJSONArray("company");
            Assert.assertTrue("Valid Company Canvas",companyCanvasArray.length()==6);
            Assert.assertTrue(((JSONObject)jsonObject.get("data")).has("private"));
    		final JSONArray privateCanvasArray=object.getJSONArray("private");
            Assert.assertTrue("Valid Private Canvas",privateCanvasArray.length()==8);
    	}

    @Test
    public void testGetCanvasesByCategoriesForJill() throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", USERNAME_JILL);
        map.put("password", "secret");
        ResultActions authResultActions = this.mockMvc.perform(post(
                "/authenticate").content(super.getJsonString(map)).contentType(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", authResultActions.andReturn()
                .getResponse().getContentAsString());

        ApiResponse authResponse = super.jsonToObject(authResultActions
                .andReturn().getResponse().getContentAsString());

        String token = authResponse.getToken().trim();

        String url = "/canvasesbycategories";
        ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());


        resultActions.andExpect(status().isOk());

        Assert.assertTrue("/canvasesbycategories endpoint does not return the expected json", compareJson(resultActions.andReturn()
                .getResponse().getContentAsString(),
                CANVASES_BY_CATEGORIES_FOR_JILL_FILE_PATH)
        );

    }


    /**
     * Setup test data for saving JackCanvas300
     *
     * @return
     * @throws JsonProcessingException
     */
    private DefaultWidgetInstanceView prepareJackWidgetInstanceViewData()
            throws JsonProcessingException {

        DefaultWidgetInstanceView wiv = new DefaultWidgetInstanceView();
        wiv.setWidgetInstanceId(1L);

        List<String> listensFor = new ArrayList<String>();
        listensFor.add("Area");
        listensFor.add("Shift");
        listensFor.add("Picker");

        CanvasView cv = new CanvasView();
        cv.setCanvasId(300L);
        cv.setName("JackCanvas300");
        cv.setShortName("JackCanvas300");
        cv.setCreatedByUserName(USERNAME_JACK);
        cv.setCanvasType(CanvasType.PRIVATE);
        cv.setCreatedDateTime(new Date());
        CanvasLayout canvasLayout = new CanvasLayout(1L, new Boolean(false), 1);
        cv.setCanvasLayout(new CanvasLayoutView(canvasLayout));

        wiv.setCanvas(cv);

        List<Integer> anchorList = new ArrayList<Integer>();
        anchorList.add(2);
        anchorList.add(3);

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        Map<String, String> optionsMap1 = new HashMap<String, String>();
        optionsMap1.put("legend", "horizontal");
        optionsMap1.put("value", "h");
        Map<String, String> optionsMap2 = new HashMap<String, String>();
        optionsMap2.put("legend", "vertical");
        optionsMap2.put("value", "v");
        options.add(optionsMap1);
        options.add(optionsMap2);

        widgetOrientation = new WidgetOrientation(options, "h");

        defaultViewConfig = new DefaultViewConfig(anchorList, 2L, 2L, 2L,
                widgetOrientation);
        actualViewConfig = new ActualViewConfig(anchorList, 2L, 2L, 2L,
                listensFor);

        ActualViewConfigView avcv = new ActualViewConfigView();
        avcv.setAnchor(anchorList);
        avcv.setMinimumHeight(2L);
        avcv.setMinimumWidth(2L);
        avcv.setListensForList(listensFor);
        avcv.setZindex(1L);
        wiv.setActualViewConfig(avcv);

        AbstractLicensableWidgetView abstractLicensableWidgetView = new AbstractLicensableWidgetView();
        abstractLicensableWidgetView.setId(1l);
        abstractLicensableWidgetView.setName("user-productivity-widget");
        wiv.setWidget(abstractLicensableWidgetView);
        wiv.setDelete(Boolean.TRUE);
        wiv.getDefaultWidgetInstance().getWidget().setReactToList(listensFor);
        wiv.getDefaultWidgetInstance().getWidget().setBroadcastList(listensFor);

        List<Map<String, String>> widgetInteractionConfig = new ArrayList<Map<String, String>>();
        Map<String, String> wic1 = new HashMap<String, String>();
        wic1.put("widgetInstanceId", "1");
        wic1.put("dataElement", "userName");
        Map<String, String> wic2 = new HashMap<String, String>();
        wic2.put("widgetInstanceId", "3");
        wic2.put("dataElement", "groupName");
        widgetInteractionConfig.add(wic1);
        widgetInteractionConfig.add(wic2);

        wiv.setWidgetInteractionConfig(widgetInteractionConfig);
        return wiv;
    }

    /**
     * Test saving JackCanvas300 data for a single widget instance id = 12
     *
     * @throws Exception
     */
    @Test
    @Rollback(true)
    @Transactional
    public void testSaveCanvasSingleInstanceWidgetData() throws Exception {

        String token = generateTokenWithAuthenticatedUser();
        CanvasView canvasToBeSaved = getSaveCanvasData(SAVE_JACK_CANVAS_REQUEST_FILE_PATH);
        String url = "/canvases/save";

        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(canvasToBeSaved))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper om = new ObjectMapper();
        ApiResponse<SaveCanvasView> saveCanvasView = om.readValue(actualJsonString, new TypeReference<ApiResponse<SaveCanvasView>>() {
        });
        Assert.assertTrue("The /canvases/save created widget instances size is not what expected" , saveCanvasView.getData().getWidgets().size() == 1);
        saveCanvasView.getData().getWidgets().get(0);
        Map<String, String> myMap = saveCanvasView.getData().getWidgets().get(0);
        myMap.get("widgetInstanceAction").equals("update");
        myMap.get("widgetInstanceId").equals("12");
        myMap.get("position").equals("1");
        resultActions.andExpect(status().isOk());
    }

        @Test
        @Rollback(true)
        @Transactional
    	public void testSaveNewCanvas() throws Exception {

            String token = generateTokenWithAuthenticatedUser();
    		CanvasView canvasToBeSaved = getSaveCanvasData(SAVE_CANVAS_REQUEST_FILE_PATH);
    		String url = "/canvases/save";

            final ResultActions resultActions = this.mockMvc.perform(post(url)
                    .header("Authentication-token", token).content(super.getJsonString(canvasToBeSaved))
                    .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));


            LOG.debug("controller returns : {}", resultActions.andReturn()
                    .getResponse().getContentAsString());

            String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
       		ObjectMapper om = new ObjectMapper();
       		ApiResponse<SaveCanvasView> saveCanvasView = om.readValue(actualJsonString, new TypeReference<ApiResponse<SaveCanvasView>>() {
			});
       		Assert.assertTrue("The /canvases/save created widget instances size is not what expected" , saveCanvasView.getData().getWidgets().size() == 5);

    		resultActions.andExpect(status().isOk());

    	}

        private CanvasView getSaveCanvasData(String jsonFileName) throws ParseException, JsonParseException, JsonMappingException, IOException{
        	ObjectMapper mapper = new LucasObjectMapper();
        	Resource jsonFileResource = new ClassPathResource(jsonFileName);
        	CanvasView canvasView = mapper.readValue(jsonFileResource.getFile(), new TypeReference<CanvasView>(){});

        	return canvasView;
        }


    /**
     * @Author Adarsh kumar
     *
     * testDeleteWidgetFromCanvas() provide the functionality testing for deletion of the
     * widget from the canvas of persisting state from database using the UIController.testDeleteWidgetFromCanvas();
     * which intern use UIService.testDeleteWidgetFromCanvas();
     *
     *  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/canvases/widgets/delete
     *
     * @throws Exception
     */
    @Test
    @Rollback(value = true)
    @Transactional
    public void testDeleteWidgetFromCanvasPositive()throws Exception{
          /*
          getting the authentication token its the simulation for login process
          after successful login its generate the authentication token it will
          send back with the request of rest api service.
          url=/canvas/widgetinstance/delete
         */
        final Map<String, Long> map = new HashMap<String, Long>();
        map.put("canvasId", 2L);
        map.put("widgetInstanceListId", 3L);
        final String token = super.generateTokenWithAuthenticatedUser();
        final String deleteServiceUrl="/canvas/widgetinstance/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(deleteServiceUrl)
               .header("Authentication-token", token).content(super.getJsonString(map))
               .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
        org.junit.Assert.assertNotNull(jsonString,"Deletion Acknowledgement Json is Null");
        resultActions.andExpect(status().isOk());
        final JSONObject obj = new JSONObject(jsonString);
        final Boolean result=Boolean.parseBoolean(obj.get("data").toString().trim());
        Assert.assertTrue("Widget Instance is Not Deleted from Canvas",result);
    }


    /**
     * @throws Exception
     * @Author Adarsh kumar
     * <p/>
     * testCanvasData() provide the functionality testing for canvas and its widget instance list data
     * its accept the  persisting state canvas id  using the UIController.getCanvasData();
     * which intern use UIService.getCanvasData();
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/canvases/{canvasId}
     */
    @Test
    @Transactional
    public void testCanvasData() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/canvases/300";
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

        Assert.assertTrue(jsonObject.has("status"));
        Assert.assertTrue(jsonObject.get("status").equals("success"));
        Assert.assertTrue(jsonObject.has("code"));
        Assert.assertTrue(jsonObject.get("code").equals("200"));
        Assert.assertTrue(jsonObject.has("message"));
        Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        Assert.assertTrue(jsonObject.has("data"));

        final JSONObject object = jsonObject.getJSONObject("data");

        Assert.assertNotNull("Canvas Id is Null ", object.get("canvasId"));
        Assert.assertNotNull("Canvas Name is Null ", object.get("canvasName"));
        Assert.assertNotNull("Canvas Short Name is Null", object.get("shortName"));
        Assert.assertNotNull("Canvas Type is Null ", object.get("canvasType"));
        Assert.assertTrue(object.has("canvasType"));
        Assert.assertTrue(object.get("canvasType").equals("PRIVATE"));

        Assert.assertTrue("Canvas Id is not 300", object.getInt("canvasId") == 300);
        Assert.assertTrue("Canvas Id is not JackCanvas300", object.getString("canvasName").equals("JackCanvas300"));
        Assert.assertTrue("Canvas Id is not JackCanvas300", object.getString("shortName").equals("JackCanvas300"));

        final JSONArray jsonArray = object.getJSONArray("widgetInstanceList");
        Assert.assertNotNull("Widget Instance List on Canvas is Null ", jsonArray);
        final List<String> widgetNameList=new ArrayList<String>(){
            {
              add("create-or-edit-user-form-widget");
              add("search-user-grid-widget");
              add("pickline-by-wave-barchart-widget");
              add("group-maintenance-widget");
              add("search-product-grid-widget");
              add("search-group-grid-widget");
              add("equipment-type-grid-widget");
              add("equipment-manager-grid-widget");
            }
        };

        
        Assert.assertTrue("WidgetInstanceList on Canvas Id 300 is not having fetching all the widgets ",jsonArray.length() == widgetNameList.size());
        
        for (int i = 0; i < widgetNameList.size(); i++) {
            String widgetName = widgetNameList.get(i);
            Assert.assertTrue("Widget " + widgetName + " does not exists",jsonArray.toString().contains(widgetName));
        }

        for (int j = 0; j < jsonArray.length(); j++ ) {
            JSONObject widgetObject = jsonArray.getJSONObject(j).getJSONObject("widgetDefinition");
            Assert.assertNotNull(widgetObject);
            Assert.assertTrue("Widget " + widgetObject.get("name") + " does not exists", widgetNameList.contains(widgetObject.get("name")));
            Assert.assertNotNull("Id is Null ", widgetObject.getInt("id"));
            Assert.assertNotNull("shortName is Null ", widgetObject.get("shortName"));
         }
    }

    @Test
    @Transactional
    public void testCanvasDataForNonExistingCanvas() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/canvases/301";
        final ResultActions resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(
                        MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Data Json is Null");
        resultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);

        Assert.assertTrue(jsonObject.has("status"));
        Assert.assertTrue(jsonObject.get("status").equals("failure"));
        Assert.assertTrue(jsonObject.has("message"));
        Assert.assertTrue(jsonObject.get("message").equals("Canvas Not Found With Id 301"));

    }


    /**
         * @Author Adarsh kumar
         *
         * testDeleteWidgetFromCanvas() provide the functionality testing for deletion of the
         * widget from the canvas of persisting state from database using the UIController.testDeleteWidgetFromCanvas();
         * which intern use UIService.testDeleteWidgetFromCanvas();
         *
         *  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/canvases/widgets/delete
         *
         * @throws Exception
         */
    @Test
    @Rollback(value = true)
    @Transactional
    public void testDeleteWidgetFromCanvasForNonExistingWidget()throws Exception{
          /*
          getting the authentication token its the simulation for login process
          after successful login its generate the authentication token it will
          send back with the request of rest api service.
          url=/canvas/widgetinstance/delete
         */
        final Map<String, Long> map = new HashMap<String, Long>();
        map.put("canvasId", 2L);
        map.put("widgetInstanceListId", 0L);
        final String token = super.generateTokenWithAuthenticatedUser();
        final String deleteServiceUrl="/canvas/widgetinstance/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(deleteServiceUrl)
                .header("Authentication-token", token).content(super.getJsonString(map))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse().getContentAsString();
        org.junit.Assert.assertNotNull(jsonString,"Deletion Acknowledgement Json is Null");
        resultActions.andExpect(status().isOk());
        final JSONObject obj = new JSONObject(jsonString);
        final Boolean result=Boolean.parseBoolean(obj.get("data").toString().trim());
        Assert.assertTrue("Widget Instance is Deleted from Canvas",!result);
    }

    /**
     * Test method to check UserId does not appear in grid Columns of default view config in widget
     * definitions for search user grid widget.
     *
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetSearchUserGridWidgetDefinitionWithoutUserIdInDefaultConfig() throws Exception {

        String token = generateTokenWithAuthenticatedUser();

        String url = "/widgets/" + SEARCH_USER_GRID_WIDGET_NAME + "/definition";
        ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsString());

        String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map>>() {});

        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");

        final Map responseMap = apiResponse.getData();
        org.springframework.util.Assert.notNull(responseMap, "Response is Null");

        final Map defaultViewConfigMap = (Map) responseMap.get("defaultViewConfig");
        final Map gridColumnMap = (Map) defaultViewConfigMap.get("gridColumns");

        // check User Id does not appear in grid columns for search user grid widget
        String mapAsJson = mapper.writeValueAsString(gridColumnMap);
        Assert.assertFalse("GridColumns element does not contain UserId", StringUtils.containsIgnoreCase(mapAsJson, "User Id"));
        Assert.assertFalse("GridColumns element does not contain userId", StringUtils.containsIgnoreCase(mapAsJson, "userId"));
    }
    
    @Test
    public void testGetSearchGroupGridWidgetDefinition() throws Exception {

        String token = generateTokenWithAuthenticatedUser();

        String url = "/widgets/"+SEARCH_GROUP_GRID_WIDGET_NAME+"/definition";
        ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();

        JSONObject response = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());

        //Assert API call was successful
        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Search group"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("search-group-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        String reactToList = data.getString("reactToList").replace("[","").replace("]","").replace("\"","");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("userName"));

    }
    
    /**
     * @throws Exception
     * @Author Pedda Reddy
     * <p/>
     * testGetEquipmentManagerGridWidgetDefinition() provide the functionality for testing
     * EquipmentManagerGridWidget instance data
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/widgets/equipment-manager-grid-widget/definition
     */
    @Test
    public void testGetEquipmentManagerGridWidgetDefinition() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/widgets/"+EQUIPMENT_MANAGER_GRID_WIDGET+"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}",jsonString);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualTree = mapper.readTree(jsonString);

        final JSONObject response = new JSONObject(jsonString);
        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        final JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Equipment Manager"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("equipment-manager-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        final JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        final JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        
        JsonNode reactToListNodeActualValues = actualTree.findValue("reactToList");
        String reactToListNodeExpectedJson = "[\"equipmentManagerName\"]";
        JsonNode reactToListNodeExpectedValue = mapper.readTree(reactToListNodeExpectedJson);
        System.out.println("reactToListNodeExpectedValue " + reactToListNodeExpectedValue + " reactToListNodeActualValues " + reactToListNodeActualValues);
        Assert.assertTrue("React To list returned values are not as per the expectation",reactToListNodeActualValues.equals(reactToListNodeExpectedValue));

    }
    
    /**
     * @throws Exception
     * @Author Pedda Reddy
     * <p/>
     * testGetWidgetDefinitionList() provide the functionality testing for widget instance list data
     * <p/>
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/widgetdefinitions
     */
    @Test
    @Transactional
    public void testGetWidgetDefinitionList() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/widgetdefinitions";
        final ResultActions resultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token).accept(
                        MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", resultActions.andReturn()
                .getResponse().getContentAsString());
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Widget Instance Json is Null");
        resultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);

        Assert.assertTrue(jsonObject.has("status"));
        Assert.assertTrue(jsonObject.get("status").equals("success"));
        Assert.assertTrue(jsonObject.has("code"));
        Assert.assertTrue(jsonObject.get("code").equals("200"));
        Assert.assertTrue(jsonObject.has("message"));
        Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        Assert.assertTrue(jsonObject.has("data"));

        final JSONObject object = jsonObject.getJSONObject("data");
        
        final JSONArray jsonWorkExecution = object.getJSONArray("Work Execution");
        final JSONArray jsonAdmin = object.getJSONArray("Administration");
        final JSONArray jsonReporting = object.getJSONArray("Reporting");
        Assert.assertNotNull("Work Execution Widget Instance List is Null ", jsonWorkExecution);
        Assert.assertNotNull("Administration Widget Instance List is Null ", jsonAdmin);
        Assert.assertNotNull("Reporting Widget Instance List is Null ", jsonReporting);
        
        final List<String> workExecList = new ArrayList<String>(){
            {
              add("search-product-grid-widget");
              add("equipment-type-grid-widget");
              add("equipment-manager-grid-widget");
            }
        };

        final List<String> adiminList = new ArrayList<String>(){
            {
              add("create-or-edit-user-form-widget");
              add("search-user-grid-widget");
              add("group-maintenance-widget");
              add("search-group-grid-widget");
            }
        };
        final List<String> reportList = new ArrayList<String>(){
            {
              add("pickline-by-wave-barchart-widget");
            }
        };
        
        Assert.assertTrue("WidgetInstanceList is not having all the widgets ",jsonWorkExecution.length() == workExecList.size());
        Assert.assertTrue("WidgetInstanceList is not having all the widgets ",jsonAdmin.length() == adiminList.size());
        Assert.assertTrue("WidgetInstanceList is not having all the widgets ",jsonReporting.length() == reportList.size());
        
        for (int i = 0; i < workExecList.size(); i++) {
            String widgetName = workExecList.get(i);
            Assert.assertTrue("Widget " + widgetName + " does not exists",jsonWorkExecution.toString().contains(widgetName));
        }
        
        for (int i = 0; i < adiminList.size(); i++) {
            String widgetName = adiminList.get(i);
            Assert.assertTrue("Widget " + widgetName + " does not exists",jsonAdmin.toString().contains(widgetName));
        }

        for (int i = 0; i < reportList.size(); i++) {
            String widgetName = reportList.get(i);
            Assert.assertTrue("Widget " + widgetName + " does not exists",jsonReporting.toString().contains(widgetName));
        }


        for (int j = 0; j < jsonWorkExecution.length(); j++ ) {
            JSONObject widgetObject = jsonWorkExecution.getJSONObject(j);
            Assert.assertNotNull(widgetObject);
            Assert.assertTrue("Widget " + widgetObject.get("name") + " does not exists", workExecList.contains(widgetObject.get("name")));
            Assert.assertNotNull("Id is Null ", widgetObject.getInt("id"));
            Assert.assertNotNull("shortName is Null ", widgetObject.get("shortName"));
         }
        for (int j = 0; j < jsonAdmin.length(); j++ ) {
            JSONObject widgetObject = jsonAdmin.getJSONObject(j);
            Assert.assertNotNull(widgetObject);
            Assert.assertTrue("Widget " + widgetObject.get("name") + " does not exists", adiminList.contains(widgetObject.get("name")));
            Assert.assertNotNull("Id is Null ", widgetObject.getInt("id"));
            Assert.assertNotNull("shortName is Null ", widgetObject.get("shortName"));
         }

        for (int j = 0; j < jsonReporting.length(); j++ ) {
            JSONObject widgetObject = jsonReporting.getJSONObject(j);
            Assert.assertNotNull(widgetObject);
            Assert.assertTrue("Widget " + widgetObject.get("name") + " does not exists", reportList.contains(widgetObject.get("name")));
            Assert.assertNotNull("Id is Null ", widgetObject.getInt("id"));
            Assert.assertNotNull("shortName is Null ", widgetObject.get("shortName"));
         }

    }
    
    @Test
	public void testGetEventGridWidgetDefinition() throws Exception {

		String token = generateTokenWithAuthenticatedUser();

		String url = "/widgets/"+EVENT_GRID_WIDGET_NAME+"/definition";
		ResultActions resultActions = this.mockMvc.perform(get(url).header(
				"Authentication-token", token).accept(
				MediaType.APPLICATION_JSON));
		LOG.debug("controller returns : {}", resultActions.andReturn()
				.getResponse().getContentAsString());

        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();

		JSONObject response = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());

        //Assert API call was successful
        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Events"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("event-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        }
    
    @Test
    public void testGetMessageGridWidgetDefinition() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/widgets/"+MESSAGE_GRID_WIDGET+"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}",jsonString);

        final JSONObject response = new JSONObject(jsonString);

        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));


        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        final JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Messages"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("message-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        final JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        final JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        final String reactToList = data.getString("reactToList").replace("[", "").replace("]", "").replace("\"", "");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("messageName"));
    }
    
    
    @Test
    public void testGetMessageMappingGridWidgetDefinition() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/widgets/"+MESSAGE_MAPPING_GRID_WIDGET+"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}",jsonString);

        final JSONObject response = new JSONObject(jsonString);

        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));


        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        final JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("Message Mappings"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("message-mappings-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        final JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        final JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        /*Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        final String reactToList = data.getString("reactToList").replace("[", "").replace("]", "").replace("\"", "");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("mappingName"));*/
    }
    
    @Test
    public void testGetEventHandlerGridWidgetDefinition() throws Exception {
        final String token = generateTokenWithAuthenticatedUser();
        final String url = "/widgets/"+EVENT_HANDLER_GRID_WIDGET+"/definition";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header(
                "Authentication-token", token).accept(
                MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        LOG.debug("controller returns : {}",jsonString);

        final JSONObject response = new JSONObject(jsonString);

        Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        Assert.assertTrue("API response code was not '200'", response.get("code").equals("200"));

        Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));


        Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        final JSONObject data = new JSONObject(response.get("data").toString());

        Assert.assertTrue("Data element does not contain property 'title'", data.has("title"));
        Assert.assertTrue("Widget title is not correct", data.get("title").equals("EventHandlers"));

        Assert.assertTrue("Data element does not contain property 'name'", data.has("name"));
        Assert.assertTrue("Widget name is not correct", data.get("name").equals("event-handler-grid-widget"));

        Assert.assertTrue("Data element does not contain property 'defaultViewConfig'", data.has("defaultViewConfig"));
        final JSONObject defaultViewConfig = new JSONObject(data.get("defaultViewConfig").toString());

        Assert.assertTrue("DefaultViewConfig element does not contain property 'autoRefreshConfig'", defaultViewConfig.has("autoRefreshConfig"));
        final JSONObject autoRefreshConfig = new JSONObject(defaultViewConfig.get("autoRefreshConfig").toString());

        Assert.assertTrue("Could not find property 'globalOverride'", autoRefreshConfig.has("globalOverride"));
        Assert.assertTrue("Incorrect value for property 'globalOverride'", autoRefreshConfig.getBoolean("globalOverride") == false);

        Assert.assertTrue("Could not find property 'enabled'", autoRefreshConfig.has("enabled"));
        Assert.assertTrue("Incorrect value for property 'enabled'", autoRefreshConfig.getBoolean("enabled") == true);

        Assert.assertTrue("Could not find property 'interval'", autoRefreshConfig.has("interval"));
        Assert.assertTrue("Incorrect value for property 'interval'", autoRefreshConfig.getInt("interval") == 120);

        /*Assert.assertTrue("Data element does not contain property 'reactToList'", data.has("reactToList"));
        final String reactToList = data.getString("reactToList").replace("[", "").replace("]", "").replace("\"", "");
        Assert.assertTrue("Widget title is not correct", reactToList.contains("mappingName"));*/
    }
    
 }

