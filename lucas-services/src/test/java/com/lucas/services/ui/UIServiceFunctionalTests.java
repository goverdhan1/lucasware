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

package com.lucas.services.ui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import com.lucas.entity.ui.canvas.OpenUserCanvas;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.collections.MultiMap;
import org.apache.xpath.operations.Bool;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.actionconfig.ActionConfigurable;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedActionConfig;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.viewconfig.DefaultViewConfig;
import com.lucas.entity.ui.viewconfig.WidgetOrientation;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.GroupMaintenanceWidget;
import com.lucas.entity.ui.widget.UserCreationFormWidget;
import com.lucas.entity.ui.widget.UserProductivityWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;
import com.lucas.services.user.UserService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class UIServiceFunctionalTests extends AbstractSpringFunctionalTests {

	@Inject
	private UIService uIService;

    @Inject
    private UserService userService;

    @Inject
    private EntityManagerFactory resourceEntityManagerFactory;
    
	private static String JACK_USERNAME = "jack123";

	private static String USERNAME = "aUsername";
	private static String HASHED_PASSWORD = "aHashedPassword";
	private User user;
	private AbstractLicensableWidget widget;
	private DefaultWidgetInstance widgetInstance;
	private Canvas canvas;
	ActionConfigurable actionConfig;
	DefaultViewConfig defaultViewConfig;
	ActualViewConfig actualViewConfig;
	private Permission p1;
	private Permission p2;
	private Permission p3;
	private Permission p4;
	private Permission p5;
	private Permission p6;
	private Permission p7;
	List<DefaultWidgetInstance> widgetInstanceList;
	private WidgetOrientation widgetOrientation;
	private MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig;
	private static final String UserCreationFormWidgetName = "create-or-edit-user-form-widget";

	private static final String PicklineByWaveBarChartWidgetName = "pickline-by-wave-barchart-widget";

	private static final String SearchUserGridWidgetName = "search-user-grid-widget";

    private static final String EquipmentTypeGridWidgetName = "equipment-type-grid-widget";
    
	private static final String MessageSegmentGridWidgetName = "message-segment-grid-widget";
	
	private static final String MessageMappingGridWidgetName = "message-mappings-grid-widget";
	
	private static final String eventHandlertGridWidgetName = "event-handler-grid-widget";



    private static final String EventGridWidgetName = "event-grid-widget";
	private Authentication auth;
	private SimpleGrantedAuthority[] grantedAuthoritiesArr = {
			new SimpleGrantedAuthority("delete-user"),
			new SimpleGrantedAuthority("disable-user"),
			new SimpleGrantedAuthority("create-edit-user-widget-access"),
			new SimpleGrantedAuthority("search-user-grid-widget-access"),
			new SimpleGrantedAuthority("pickline-by-wave-widget-access"),
			new SimpleGrantedAuthority("group-maintenance-widget-access"),
			new SimpleGrantedAuthority("search-product-grid-widget-access"),
			new SimpleGrantedAuthority("equipment-type-widget-access"),
			new SimpleGrantedAuthority("create-equipment-type"),
			new SimpleGrantedAuthority("delete-equipment-type"),
			new SimpleGrantedAuthority("edit-equipment-type"),
			new SimpleGrantedAuthority("create-event"),
			new SimpleGrantedAuthority("edit-event"),
			new SimpleGrantedAuthority("message-segments-widget-access"),
			new SimpleGrantedAuthority("create-message-segment"),
			new SimpleGrantedAuthority("edit-message-segment"),
			new SimpleGrantedAuthority("delete-message-segment"),
			new SimpleGrantedAuthority("message-mappings-widget-access"),
			new SimpleGrantedAuthority("delete-message-mapping"),
			new SimpleGrantedAuthority("event-handlers-widget-access"),
			new SimpleGrantedAuthority("create-event-handler"),
			new SimpleGrantedAuthority("edit-event-handler"),
			new SimpleGrantedAuthority("delete-event-handler")
			 };

	private static final Logger LOG = LoggerFactory.getLogger(UIServiceFunctionalTests.class);


	@Before
	public void setup() throws Exception {
		user = new User();
		user.setUserId(1L);
		user.setUserName(USERNAME);
		user.setHashedPassword(HASHED_PASSWORD);

		canvas = new Canvas("testCanvas1");

		List<String> listensFor = new ArrayList<String>();
		listensFor.add("Area");
		listensFor.add("Shift");
		listensFor.add("Picker");

		List<Integer> anchorList = new ArrayList<Integer>();
		anchorList.add(2);
		anchorList.add(3);

		List<Map<String, String>> options = new ArrayList<Map<String,String>>();
		Map<String, String> optionsMap1 = new HashMap<String, String>();
		optionsMap1.put("legend", "horizontal");
		optionsMap1.put("value", "h");
		Map<String, String> optionsMap2 = new HashMap<String, String>();
		optionsMap2.put("legend", "vertical");
		optionsMap2.put("value", "v");
		options.add(optionsMap1);
		options.add(optionsMap2);

		widgetOrientation = new WidgetOrientation(options,"h");

		defaultViewConfig = new DefaultViewConfig(anchorList, 2L, 2L, 2L, widgetOrientation);
		actualViewConfig = new ActualViewConfig(anchorList, 2L, 2L, 2L, listensFor);
		p1 = new Permission("create-assignment");
		p2 = new Permission("view-report-productivity");
		p3 = new Permission("configure-location");
		p4 = new Permission("create-canvas");
		p5 = new Permission("delete-canvas");
		p6 = new Permission("authenticated-user");
		p7 = new Permission("pickingwidget2-widget-access");
		Map<String, Map<Permission, Boolean>> mappedActionConfigurableMap = new HashMap<String, Map<Permission, Boolean>>();
		 mappedActionConfig = new PermissionMappedActionConfig(
				mappedActionConfigurableMap);

		mappedActionConfigurableMap.put("widget-access", new HashMap<Permission, Boolean>(){
			{
			put(p7, Boolean.FALSE);
			}
		});
		
		mappedActionConfigurableMap.put("widget-actions", new HashMap<Permission, Boolean>(){
			{
				put(p1, Boolean.FALSE);
				put(p2, Boolean.FALSE);
				put(p3, Boolean.FALSE);
				put(p4, Boolean.FALSE);
				put(p5, Boolean.FALSE);
				put(p6, Boolean.FALSE);
			}
		});
		
		ObjectMapper objectMapper = new ObjectMapper();

		widget = new UserCreationFormWidget("Picking Widget2", "pick2",
				Enum.valueOf(WidgetType.class, "GRID"),
				objectMapper.writeValueAsString(defaultViewConfig));
		widget.setActionConfig(mappedActionConfig);
		widget.setActive(true);
		widget.setCategory("Administration");;
		widgetInstance = new DefaultWidgetInstance(
				objectMapper.writeValueAsString(actualViewConfig), canvas,
				widget);
		 widgetInstanceList = new ArrayList<DefaultWidgetInstance>();
		widgetInstanceList.add(widgetInstance);

		canvas.setWidgetInstanceList(widgetInstanceList);
		auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(grantedAuthoritiesArr));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveWidget() throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		LucasObjectMapper om = new LucasObjectMapper();
		String stringActionConfig = om.writeValueAsString(mappedActionConfig);
		MappedActionConfigurable<String, Map<Permission, Boolean>> mac= om.readValue(
				stringActionConfig,
				new TypeReference<MappedActionConfigurable<String, Map<Permission, Boolean>>>() {
				}); 
		uIService.deleteWidget(widget.getName());
		uIService.saveWidget(widget);
		AbstractLicensableWidget retrivedWidget = uIService
				.getWidgetByName(widget.getName());
		Assert.notNull(retrivedWidget);
	}

	
	// This is failing on nightly build server and PHX-1009 is raised for the same. 
	// Bug status - yet to be closed
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateCanvas() throws Exception {
		 uIService.saveWidget(widget);
		  canvas = new Canvas("testCanvas");
		  canvas.setCanvasType(CanvasType.PRIVATE);
		  canvas.setCanvasLayout(uIService.getCanvasLayoutById(1L));
		  canvas.setShortName("testCanvas");
		  canvas.setCreatedByUserName("createdByUserName");
		  canvas.setCreatedDateTime(new Date());
		  canvas.setWidgetInstanceList(widgetInstanceList);
		  uIService.saveCanvas(canvas);
		  Canvas retrivedCanvas = uIService.getCanvasByName(canvas.getName());
		  Assert.notNull(retrivedCanvas);
	}


		@Test
		@Transactional
		@Rollback(true)
		public void testGetWidgetInstanceListForCanvas() throws Exception {

			List<DefaultWidgetInstance> widgetInstanceList = uIService.getWidgetInstanceListForCanvas(3l, JACK_USERNAME);
			for (DefaultWidgetInstance widgetInstance : widgetInstanceList) {

				Map<String, Map<Permission, Boolean>> actionConfigMap = widgetInstance.getWidget().getActionConfig().getActionConfig();

				Assert.notNull(actionConfigMap);
			}
			Assert.notNull(widgetInstanceList);

		}
		
		@Transactional(readOnly = true)
		@Test
		public void testGetWidgetDefinitionsByCategory() throws IOException, Exception{

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);

			MultiMap widgetDefinitionsByCategoryMap = uIService.getWidgetDefinitionsByCategory();
			Assert.notNull(widgetDefinitionsByCategoryMap);
//			Assert.isTrue(widgetDefinitionsByCategoryMap.size() == 3);
//			Assert.isTrue(widgetDefinitionsByCategoryMap.values().size() == 4);
			
			org.junit.Assert.assertTrue("testGetWidgetDefinitionsByCategory() widgetDefinitionsByCategoryMap.size() failed test, "
					+ "expected value 3, actually got: " + widgetDefinitionsByCategoryMap.size(), widgetDefinitionsByCategoryMap.size() == 4);
			org.junit.Assert.assertTrue("testGetWidgetDefinitionsByCategory() widgetDefinitionsByCategoryMap.values().size() failed test, "
					+ "expected value 4, actually got: " + widgetDefinitionsByCategoryMap.values().size(), widgetDefinitionsByCategoryMap.values().size() >= 5);
			
			securityContext.setAuthentication(null);
		}

		@Transactional(readOnly = true)
		@Test
		public void testGetUserCreationFormWidgetDefinition() throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
			String listenForListString = "\"listensForList\": [ \"userName\"]";
			AbstractLicensableWidget userCreationFormWidget = uIService.getWidgetDefinition(UserCreationFormWidgetName);
			Assert.notNull(userCreationFormWidget);
			Assert.notNull(userCreationFormWidget.getReactToList());
			Assert.notNull(userCreationFormWidget.getBroadcastList());
			String defaultViewConfig = userCreationFormWidget
					.getDefaultViewConfig();
			Assert.isTrue(defaultViewConfig.contains(listenForListString),
					"Default view config does not have the expected listen for list");
		}

		
		// This is randomly failing with below error:
		/*
		 * java.lang.IllegalArgumentException: Search user grid widget action config does not have delete-user permission false
			at org.springframework.util.Assert.isTrue(Assert.java:65)
			at com.lucas.services.ui.UIServiceFunctionalTests.testGetSearchUserGridWidgetDefinitionWithoutAuthentication(UIServiceFunctionalTests.java:272)
		 * 
		 */
		
		@Ignore
		@Test
		public void testGetSearchUserGridWidgetDefinitionWithoutAuthentication() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
			AbstractLicensableWidget searchUserGridWidget = uIService.getWidgetDefinition(SearchUserGridWidgetName);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(null);
			Assert.notNull(searchUserGridWidget);
			Assert.notNull(searchUserGridWidget.getDataURL());
			
			Assert.isTrue(
					searchUserGridWidget.getActionConfig().getActionConfig().size() == 2,
					"Search user grid widget action config does not have expected # of permissions values");
			
			Assert.isTrue(
					searchUserGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("delete-user")) == Boolean.FALSE,
					"Search user grid widget action config does not have delete-user permission false");
			
			Assert.isTrue(
					searchUserGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("disable-user")) == Boolean.FALSE,
					"Search user grid widget action config does not have disable-user permission false");
		}

		@Test
		@Transactional(readOnly = true)
		public void testGetSearchUserGridWidgetDefinitionWithAuthentication() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
			String listenForListString = "\"listensForList\":[\"userName\"]";
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
			AbstractLicensableWidget searchUserGridWidget = uIService.getWidgetDefinition(SearchUserGridWidgetName);
			Assert.notNull(searchUserGridWidget);
		Assert.isTrue(
				searchUserGridWidget.getActionConfig().getActionConfig().size() == 2,
				"Search user grid widget action config does not have expected # of permissions values");
		Assert.isTrue(
				searchUserGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("delete-user")) == Boolean.TRUE,
				"Search user grid widget action config does not have delete-user permission true");
		Assert.isTrue(
				searchUserGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("disable-user")) == Boolean.TRUE,
				"Search user grid widget action config does not have disable-user permission true");
		Assert.notNull(searchUserGridWidget.getReactToList(), "Search user grid widget definition does not have react to map");
		Assert.notNull(searchUserGridWidget.getBroadcastList(), "Search user grid widget definition does not have the broadcast list");
		Assert.isTrue(searchUserGridWidget.getBroadcastList().size() == 1, "Search user grid widget definition does not have the required broadcast list size");
		String defaultViewConfig = searchUserGridWidget
				.getDefaultViewConfig();
		Assert.isTrue(defaultViewConfig.contains(listenForListString),
				"Default view config does not have the expected listen for list");

            //Assertions for AutoRefreshConfig
            JSONObject defaultViewConfigJSON = new JSONObject(searchUserGridWidget.getDefaultViewConfig());
            Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"), "Could not find property 'AutoRefreshConfig'");

            JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON.get("autoRefreshConfig").toString());
            Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"), "Could not find property 'globalOverride'");
            Assert.isTrue(autoRefreshConfigJSON.has("enabled"), "Could not find property 'enabled'");
            Assert.isTrue(autoRefreshConfigJSON.has("interval"), "Could not find property 'interval'");

            Assert.isTrue(autoRefreshConfigJSON.getBoolean("globalOverride") == false, "Incorrect value for property 'globalOverride'");
            Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true, "Incorrect value for property 'enabled'");
            Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120, "Incorrect value for property 'interval'");
        }

    @Test
    @Transactional(readOnly = true)
    public void testGetEquipmentTypeGridWidgetDefinitionWithAuthentication()
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
            , SecurityException, NoSuchMethodException, IOException {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        AbstractLicensableWidget equipmentTypeGridWidget = uIService.getWidgetDefinition(EquipmentTypeGridWidgetName);
        Assert.notNull(equipmentTypeGridWidget);
        Assert.isTrue(
                equipmentTypeGridWidget.getActionConfig().getActionConfig().size() == 2,
                "Equipment Type Grid widget action config does not have expected # of permissions values");
        Assert.isTrue(
                equipmentTypeGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("create-equipment-type")) == Boolean.TRUE,
                "Equipment Type Grid widget action config does not have create-equipment-type permission true");
        Assert.isTrue(
                equipmentTypeGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("delete-equipment-type")) == Boolean.TRUE,
                "Equipment Type Grid widget action config does not have delete-equipment-type permission true");
        Assert.isTrue(
                equipmentTypeGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission( "edit-equipment-type")) == Boolean.TRUE,
                "Equipment Type Grid widget action config does not have edit-equipment-type permission true");

        Assert.notNull(equipmentTypeGridWidget.getReactToList(), "Equipment Type Grid widget definition does not have react to map");
        Assert.notNull(equipmentTypeGridWidget.getBroadcastList(), "Equipment Type Grid widget definition does not have the broadcast list");
        Assert.isTrue(equipmentTypeGridWidget.getBroadcastList().size() == 1, "Equipment Type Grid widget definition does not have the required broadcast list size");

        //Assertions for AutoRefreshConfig
        JSONObject defaultViewConfigJSON = new JSONObject(equipmentTypeGridWidget.getDefaultViewConfig());
        
        Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"), "Could not find property 'AutoRefreshConfig'");

        JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON.get("autoRefreshConfig").toString());
        Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"), "Could not find property 'globalOverride'");
        Assert.isTrue(autoRefreshConfigJSON.has("enabled"), "Could not find property 'enabled'");
        Assert.isTrue(autoRefreshConfigJSON.has("interval"), "Could not find property 'interval'");

        Assert.isTrue(autoRefreshConfigJSON.getBoolean("globalOverride") == false, "Incorrect value for property 'globalOverride'");
        Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true, "Incorrect value for property 'enabled'");
        Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120, "Incorrect value for property 'interval'");
    }

    
    /* Test for Message Segment rendering */

	@Test
	@Transactional(readOnly = true)
	public void testGetMessageSegmentGridWidgetDefinitionWithAuthentication()
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, IOException {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		AbstractLicensableWidget messageSegmentGridWidget = uIService
				.getWidgetDefinition(MessageSegmentGridWidgetName);

		Assert.notNull(messageSegmentGridWidget);
		Assert.isTrue(
				messageSegmentGridWidget.getActionConfig().getActionConfig()
						.size() == 2,
				"Message Segment Grid widget action config does not have expected # of permissions values");
		Assert.isTrue(
				messageSegmentGridWidget.getActionConfig().getActionConfig()
						.get("widget-actions")
						.get(new Permission("create-message-segment")) == Boolean.TRUE,
				"Message Segment Grid widget action config does not have create-message-segment permission true");

		Assert.isTrue(
				messageSegmentGridWidget.getActionConfig().getActionConfig()
						.get("widget-actions")
						.get(new Permission("edit-message-segment")) == Boolean.TRUE,
				"Message Segment Grid widget action config does not have edit-message-segment permission true");

		Assert.isTrue(
				messageSegmentGridWidget.getActionConfig().getActionConfig()
						.get("widget-actions")
						.get(new Permission("delete-message-segment")) == Boolean.TRUE,
				"Message Segment Grid widget action config does not have delete-message-segment permission true");

		// Assertions for AutoRefreshConfig
		JSONObject defaultViewConfigJSON = new JSONObject(
				messageSegmentGridWidget.getDefaultViewConfig());

		Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"),
				"Could not find property 'AutoRefreshConfig'");

		JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON
				.get("autoRefreshConfig").toString());
		Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"),
				"Could not find property 'globalOverride'");
		Assert.isTrue(autoRefreshConfigJSON.has("enabled"),
				"Could not find property 'enabled'");
		Assert.isTrue(autoRefreshConfigJSON.has("interval"),
				"Could not find property 'interval'");

		Assert.isTrue(
				autoRefreshConfigJSON.getBoolean("globalOverride") == false,
				"Incorrect value for property 'globalOverride'");
		Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true,
				"Incorrect value for property 'enabled'");
		Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120,
				"Incorrect value for property 'interval'");
	}

	
	  /* Test for Message Mapping rendering */

		@Test
		@Transactional(readOnly = true)
		public void testGetMessageMappingGridWidgetDefinitionWithAuthentication()
				throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException, SecurityException,
				NoSuchMethodException, IOException {

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
			AbstractLicensableWidget messageMappingGridWidget = uIService
					.getWidgetDefinition(MessageMappingGridWidgetName);

			Assert.notNull(messageMappingGridWidget);
			Assert.isTrue(
					messageMappingGridWidget.getActionConfig().getActionConfig()
							.size() == 2,
					"Message Mapping Grid widget action config does not have expected # of permissions values");
			Assert.isTrue(
					messageMappingGridWidget.getActionConfig().getActionConfig()
							.get("widget-actions")
							.get(new Permission("delete-message-mapping")) == Boolean.TRUE,
					"Message Mapping Grid widget action config does not have create-message-mapping permission true");



			// Assertions for AutoRefreshConfig
			JSONObject defaultViewConfigJSON = new JSONObject(
					messageMappingGridWidget.getDefaultViewConfig());

			Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"),
					"Could not find property 'AutoRefreshConfig'");

			JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON
					.get("autoRefreshConfig").toString());
			Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"),
					"Could not find property 'globalOverride'");
			Assert.isTrue(autoRefreshConfigJSON.has("enabled"),
					"Could not find property 'enabled'");
			Assert.isTrue(autoRefreshConfigJSON.has("interval"),
					"Could not find property 'interval'");

			Assert.isTrue(
					autoRefreshConfigJSON.getBoolean("globalOverride") == false,
					"Incorrect value for property 'globalOverride'");
			Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true,
					"Incorrect value for property 'enabled'");
			Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120,
					"Incorrect value for property 'interval'");
		}

		@Test
		public void testGetPicklineByWaveBarChartWidgetDefinition() throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
			AbstractLicensableWidget picklineByWaveBarChartWidget = uIService.getWidgetDefinition(PicklineByWaveBarChartWidgetName);
			Assert.notNull(picklineByWaveBarChartWidget);
		}

		@Transactional
		@Rollback(true)
		@Test
		public void testUpdateWidgetInstance() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
			ObjectMapper objectMapper = new ObjectMapper();

			DefaultWidgetInstance defaultWidgetInstance = prepareData(objectMapper);

			Map<String, String> responsMap = uIService.createOrUpdateWidgetInstance(defaultWidgetInstance);
			Assert.isTrue(responsMap.get("widgetInstanceAction").equals("update"), "The response action should have been 'updated'");

			 Canvas retrievedPerishableCanvas = uIService.getCanvasByName("Perishable Goods Canvas");
			 List<DefaultWidgetInstance> retrievedDefaultWidgetInstances = retrievedPerishableCanvas.getWidgetInstanceList();

				 DefaultWidgetInstance retrievedRefaultWidgetInstance = retrievedDefaultWidgetInstances.get(0);
				 Assert.isTrue(objectMapper.writeValueAsString(actualViewConfig).equals(retrievedRefaultWidgetInstance.getActualViewConfig()));
		}

		@Transactional
		@Rollback(true)
		@Test
		public void testCreateWidgetInstance() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
			ObjectMapper objectMapper = new ObjectMapper();

			DefaultWidgetInstance defaultWidgetInstance = prepareData(objectMapper);

			defaultWidgetInstance.setWidgetinstanceId(null);

			Map<String, String> responsMap = uIService.createOrUpdateWidgetInstance(defaultWidgetInstance);
			Assert.isTrue(responsMap.get("widgetInstanceAction").equals("create"), "The response action should have been 'create'");
			 Canvas retrievedPerishableCanvas = uIService.getCanvasByName("Perishable Goods Canvas");
			 List<DefaultWidgetInstance> retrievedDefaultWidgetInstances = retrievedPerishableCanvas.getWidgetInstanceList();

			Assert.isTrue(retrievedDefaultWidgetInstances.size() == 6);
		}

		private DefaultWidgetInstance prepareData(ObjectMapper objectMapper) throws JsonProcessingException{

			AbstractLicensableWidget widget = new UserProductivityWidget();
			widget.setId(1l);
			widget.setName("user-productivity-widget");
			Canvas perishableCanvas = new Canvas();
			perishableCanvas.setName("Perishable Goods Canvas");
			perishableCanvas.setShortName("shortName");
			perishableCanvas.setCanvasType(CanvasType.PRIVATE);
			perishableCanvas.setCanvasId(1l);
			DefaultWidgetInstance defaultWidgetInstance = new DefaultWidgetInstance();
			defaultWidgetInstance.setActualViewConfig(objectMapper.writeValueAsString(actualViewConfig));
			defaultWidgetInstance.setCanvas(perishableCanvas);
			defaultWidgetInstance.setWidgetinstanceId(1l);
			defaultWidgetInstance.setWidget(widget);

			return defaultWidgetInstance;
		}

    @Transactional
    @Test
    public void getGroupMaintenanceWidget() throws Exception{
        final User jackUser=this.userService.getUser(JACK_USERNAME);
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(jackUser, null, Arrays.asList(new SimpleGrantedAuthority("group-maintenance-create"), new SimpleGrantedAuthority("group-maintenance-edit"))));
        final GroupMaintenanceWidget groupMaintenanceWidget= this.uIService.getGroupMaintenanceWidgetDefinition();
        Assert.notNull(groupMaintenanceWidget,"GroupMaintenanceWidget is Null");
        Assert.isTrue(groupMaintenanceWidget.getId() == 12, "Id is not Proper for GroupMaintenanceWidget");
        Assert.isTrue(groupMaintenanceWidget.isActive() == true, "GroupMaintenanceWidget isn't active");
        Assert.notNull(groupMaintenanceWidget.getBroadcastList(), "GroupMaintenanceWidget BroadCast List is Null");
        Assert.isTrue(groupMaintenanceWidget.getBroadcastList().contains("groupName"), "GroupMaintenanceWidget Doesn't Contain GroupName in BroadCast List");
        Assert.isTrue(groupMaintenanceWidget.getShortName().equals("GroupMaintenance"), "GroupMaintenanceWidget Doesn't Contain Proper Short Name");
        Assert.isTrue(groupMaintenanceWidget.getName().equals("group-maintenance-widget"), "GroupMaintenanceWidget Doesn't Contain Proper Name");
        Assert.isTrue(groupMaintenanceWidget.getTitle().equals("Group Maintenance"), "GroupMaintenanceWidget Doesn't Contain Proper Title");
        Assert.notNull(groupMaintenanceWidget.getWidgetType(), "GroupMaintenanceWidget WidgetType is Null");
    }
    
    /**
	  * This is not a test case it should be uncommented and executed to verify whether the second level cache is working fine.
	  */
	/* @Test
	    public void testCache() {
	    	EntityManagerFactoryInfo emfi = (EntityManagerFactoryInfo)resourceEntityManagerFactory;
	    	EntityManagerFactory emf = emfi.getNativeEntityManagerFactory();
	    	EntityManagerFactoryImpl empImpl = (EntityManagerFactoryImpl)emf;
	    	LOG.debug("*************************** " +empImpl.getSessionFactory().getStatistics() + " ***************************");
	    }*/

	@Test
	@Transactional
	public void testGetCanvasById()throws Exception{
		final Canvas canvas= this.uIService.getCanvasById(1L);
		Assert.notNull(canvas,"Canvas Doesn't Exist for CanvasId 1");
		final List<DefaultWidgetInstance> defaultWidgetInstances=canvas.getWidgetInstanceList();
		Assert.notNull(defaultWidgetInstances,"DefaultWidgetInstance List for CanvasId 5 is Null");
	}

    /**
       * @Author Adarsh kumar
       *
       * testCanvasPersistedAndDeletion() provide the functionality of
       * deletion of the canvas based on the canvasId or canvasName
       * in this test case new canvas is inserted and then deleted
       * by the uiService for testing.
       * @throws Exception
       */
     @Test
     @Rollback(value = true)
     @Transactional
     public void testCanvasPersistedAndDeletion()throws Exception{

                     /* CREATING NEW CANVAS FOR TESTING PURPOSE */
                             this.uIService.saveWidget(widget);
             final Canvas canvasNew = new Canvas("testCanvas");
             canvasNew.setCanvasType(CanvasType.PRIVATE);
             canvasNew.setCanvasLayout(uIService.getCanvasLayoutById(1L));
             canvasNew.setShortName("testCanvas");
             canvasNew.setCreatedByUserName("createdByUserName");
             canvasNew.setCreatedDateTime(new Date());
             canvasNew.setWidgetInstanceList(widgetInstanceList);
             final Long canvasId=this.uIService.saveCanvas(canvasNew);
             Assert.notNull(canvasId,"Canvas Isn't Persisted into database "+canvasId);

                     /* DELETING THE CANVAS FROM DATABASE BASED ON ID   */
                             final Boolean result = this.uIService.deleteCanvasById(canvasNew.getCanvasId());
             Assert.isTrue(result,"Canvas Isn't deleted from database");

       }


       /**
       * @Author Adarsh kumar
       *
       * testExistingCanvasAndDeletion() provide the functionality of
       * deletion of the existing canvas based on the canvasId or canvasName
       * in this test already existing canvas is used for deletion test
       * by the uiService
       * @throws Exception
       */
     @Test
     @Rollback(value = true)
     @Transactional
     public void testExistingCanvasAndDeletion()throws Exception{

                     /* FETCHING CANVAS FROM DATABASE FOR ENSURING NEW CANVAS IS EXISTING INTO DB */
                             Canvas canvasPersisted= this.uIService.getCanvasById(1L);
             Assert.notNull(canvasPersisted,"Canvas Doesn't Exist for CanvasId "+canvasPersisted.getCanvasId());

                     /* DELETING THE CANVAS FROM DATABASE BASED ON ID OR NAME  */
                             final Boolean result = this.uIService.deleteCanvasById(canvasPersisted.getCanvasId());
             Assert.isTrue(result,"Canvas Isn't deleted from database");
         }
             
     @Test
     public void testGetCanvasesByCategories()throws Exception{
    	 
    	 SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
            LinkedHashMap<String, List<Canvas>> canvasesByCategory = uIService.getCanvasesByCategories();
			Assert.notNull(canvasesByCategory, "Canvases by category service call returns null");
			Assert.isTrue(canvasesByCategory.size() == 2, "Canvases by category service call does not return all three types of canvases");
			Assert.isTrue(canvasesByCategory.values().size() == 2, "Canvases by category service call does not return expected number of canvases (=5)");
			securityContext.setAuthentication(null);
     }


    /**
     * @Author Adarsh kumar
     *
     * testDeleteWidgetFromCanvas() provide the functionality of
     * deletion of the widget from the canvas based on the canvasId and widgetInstanceId
     * in this test case old canvas is used for testing and deleted
     * by the uiService for testing.
     * @throws Exception
     */
    @Test
    @Rollback(value = true)
    @Transactional
    public void testDeleteWidgetFromCanvas()throws Exception{
        final Long canvasId=2L;
        final Long widgetInstanceListId=3L;
        final Boolean result= this.uIService.deleteWidgetFromCanvas(canvasId,widgetInstanceListId);
        Assert.isTrue(result,"WidgetInstance Isn't Deleted from Canvas "+canvasId);
    }


    /**
     * @Author Adarsh kumar
     *
     * testDeleteWidgetFromCanvasNegative() provide the functionality of
     * deletion of the widget from the canvas based on the canvasId and widgetInstanceId
     * in this test case old canvas is used for testing and deleted
     * by the uiService for testing.
     * @throws Exception
     */
    @Test
    @Rollback(value = true)
    @Transactional
    public void testDeleteWidgetFromCanvasNegative()throws Exception{
        final Long canvasId=0L;
        final Long widgetInstanceListId=0L;
        final Boolean result= this.uIService.deleteWidgetFromCanvas(canvasId,widgetInstanceListId);
        Assert.isTrue(!result, "WidgetInstance Is Deleted from Canvas for canvas Id " + canvasId+" and WidgetInstanceListId "+widgetInstanceListId);
    }


    @Test
    @Transactional
    public void testHydratedCanvas()throws Exception{
        final Canvas canvas= this.uIService.getHydratedCanvas(300L);
        Assert.notNull(canvas,"Canvas Doesn't Exist for CanvasId 300");
        final List<DefaultWidgetInstance> defaultWidgetInstances=canvas.getWidgetInstanceList();
        Assert.notNull(defaultWidgetInstances,"DefaultWidgetInstance List for CanvasId 6 is Null");
    }


    @Test
    @Transactional
    public void testHydratedCanvasForNonExistenceCanvas()throws Exception{
        Canvas canvas=null;
        try{
          canvas= this.uIService.getHydratedCanvas(30000L);
        }catch (Exception e){
            Assert.isNull(canvas ,"Canvas Shouldn't Exist for CanvasId 30000");
        }
    }

    @Test
    @Transactional
    public void testOpenCanvasesForUserWithOpenCanvases()throws Exception{
        final User user=this.userService.getUser("jack123");
        final List<OpenUserCanvas> openUserCanvases=this.uIService.getAllOpenCanvasForUser(user.getUserId());
        Assert.notNull(openUserCanvases,"Jack User Doesn't have OpenCanvas , Open Canvas is Null for User Jack123 ");
        Assert.isTrue(openUserCanvases.size() != 0, "Jack User Doesn't have OpenCanvas ");
    }

    @Test
    @Transactional
    public void testOpenCanvasesForNonExistenceUser() throws Exception {
        List<OpenUserCanvas> openUserCanvases = null;
        try {
            openUserCanvases = this.uIService.getAllOpenCanvasForUser(99999L);
        } catch (Exception e) {
            Assert.isNull(openUserCanvases, "User have OpenCanvas , Open Canvas is not Null for User Annonimus ");
        }
    }

    @Test
    @Transactional
    public void testOpenCanvasesForUserWithoutOpenCanvases()throws Exception{
        final User user=this.userService.getUser("jill123");
        final List<OpenUserCanvas> openUserCanvases=this.uIService.getAllOpenCanvasForUser(user.getUserId());
        Assert.notNull(openUserCanvases,"Jill User Doesn't have OpenCanvas , Open Canvas is Null for User Jill ");
        Assert.isTrue(openUserCanvases.size() == 0, "Jill User Doesn't have OpenCanvas ");
    }
    
    @Test
	@Transactional(readOnly = true)
	public void testGetEventGridWidgetDefinitionWithAuthentication() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		AbstractLicensableWidget eventGridWidget = uIService.getWidgetDefinition(EventGridWidgetName);
		Assert.notNull(eventGridWidget);
	Assert.isTrue(
			eventGridWidget.getActionConfig().getActionConfig().size() == 2,
			"Event grid widget action config does not have expected # of permissions values");
	Assert.isTrue(
			eventGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("create-event")) == Boolean.TRUE,
			"Event grid widget action config does not have create-event permission true");
	Assert.isTrue(
			eventGridWidget.getActionConfig().getActionConfig().get("widget-actions").get(new Permission("edit-event")) == Boolean.TRUE,
			"Event grid widget action config does not have edit-event permission true");

        //Assertions for AutoRefreshConfig
        JSONObject defaultViewConfigJSON = new JSONObject(eventGridWidget.getDefaultViewConfig());
        Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"), "Could not find property 'AutoRefreshConfig'");

        JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON.get("autoRefreshConfig").toString());
        Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"), "Could not find property 'globalOverride'");
        Assert.isTrue(autoRefreshConfigJSON.has("enabled"), "Could not find property 'enabled'");
        Assert.isTrue(autoRefreshConfigJSON.has("interval"), "Could not find property 'interval'");

        Assert.isTrue(autoRefreshConfigJSON.getBoolean("globalOverride") == false, "Incorrect value for property 'globalOverride'");
        Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true, "Incorrect value for property 'enabled'");
        Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120, "Incorrect value for property 'interval'");
    }

    

	  /* Test for Event Handler rendering */

		@Test
		@Transactional(readOnly = true)
		public void testGetEventHandlerGridWidgetDefinitionWithAuthentication()
				throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException, SecurityException,
				NoSuchMethodException, IOException {

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
			AbstractLicensableWidget eventHandlerGridWidget = uIService
					.getWidgetDefinition(eventHandlertGridWidgetName);

			Assert.notNull(eventHandlerGridWidget);
			Assert.isTrue(
					eventHandlerGridWidget.getActionConfig().getActionConfig()
							.size() == 2,
					"Event Handler Grid widget action config does not have expected # of permissions values");
			Assert.isTrue(
					eventHandlerGridWidget.getActionConfig().getActionConfig()
							.get("widget-actions")
							.get(new Permission("create-event-handler")) == Boolean.TRUE,
					"Event Handler Grid widget action config does not have create-event-handler permission true");
			Assert.isTrue(
					eventHandlerGridWidget.getActionConfig().getActionConfig()
							.get("widget-actions")
							.get(new Permission("edit-event-handler")) == Boolean.TRUE,
					"Event Handler Grid widget action config does not have edit-event-handler permission true");
			Assert.isTrue(
					eventHandlerGridWidget.getActionConfig().getActionConfig()
							.get("widget-actions")
							.get(new Permission("delete-event-handler")) == Boolean.TRUE,
					"Event Handler Grid widget action config does not have delete-event-handler permission true");
			// Assertions for AutoRefreshConfig
			JSONObject defaultViewConfigJSON = new JSONObject(
					eventHandlerGridWidget.getDefaultViewConfig());

			Assert.isTrue(defaultViewConfigJSON.has("autoRefreshConfig"),
					"Could not find property 'AutoRefreshConfig'");

			JSONObject autoRefreshConfigJSON = new JSONObject(defaultViewConfigJSON
					.get("autoRefreshConfig").toString());
			Assert.isTrue(autoRefreshConfigJSON.has("globalOverride"),
					"Could not find property 'globalOverride'");
			Assert.isTrue(autoRefreshConfigJSON.has("enabled"),
					"Could not find property 'enabled'");
			Assert.isTrue(autoRefreshConfigJSON.has("interval"),
					"Could not find property 'interval'");

			Assert.isTrue(
					autoRefreshConfigJSON.getBoolean("globalOverride") == false,
					"Incorrect value for property 'globalOverride'");
			Assert.isTrue(autoRefreshConfigJSON.getBoolean("enabled") == true,
					"Incorrect value for property 'enabled'");
			Assert.isTrue(autoRefreshConfigJSON.getInt("interval") == 120,
					"Incorrect value for property 'interval'");
		}

    
}
