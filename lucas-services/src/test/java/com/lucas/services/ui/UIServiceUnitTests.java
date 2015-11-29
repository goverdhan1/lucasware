/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.ui;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.dao.ui.CanvasDAO;
import com.lucas.dao.ui.WidgetDAO;
import com.lucas.dao.ui.WidgetInstanceDAO;
import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.ui.actionconfig.ActionConfigurable;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedActionConfig;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.ui.dataurl.DataUrl;
import com.lucas.entity.ui.reacttomap.ReactToMap;
import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.viewconfig.DefaultViewConfig;
import com.lucas.entity.ui.viewconfig.WidgetOrientation;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.AlertWidget;
import com.lucas.entity.ui.widget.AreaRouteChartWidget;
import com.lucas.entity.ui.widget.AssignmentCreationWidget;
import com.lucas.entity.ui.widget.AssignmentManagementWidget;
import com.lucas.entity.ui.widget.GroupMaintenanceWidget;
import com.lucas.entity.ui.widget.PicklineByWaveBarChartWidget;
import com.lucas.entity.ui.widget.PicklineProgressWidget;
import com.lucas.entity.ui.widget.ProductivityByZoneWidget;
import com.lucas.entity.ui.widget.SearchProductGridWidget;
import com.lucas.entity.ui.widget.SearchUserGridWidget;
import com.lucas.entity.ui.widget.UserCreationFormWidget;
import com.lucas.entity.ui.widget.UserProductivityWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.exception.WidgetAlreadyExistsException;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.sort.SortType;

@RunWith(MockitoJUnitRunner.class)
public class UIServiceUnitTests {

	@Mock
	private UserDAO userDAO;
	
	@Mock
	private CanvasDAO canvasDAO;
	
	@Mock
	private WidgetDAO widgetDAO;	

	@Mock
	private WidgetInstanceDAO widgetInstanceDAO;	
	
	
	@Mock
	private Canvas mockCanvas;
	
	@Mock
	SecurityService securityService;
	
	@InjectMocks
	private UIServiceImpl uIService;

	@Mock
	private Appender mockAppender;
	
	@Mock
	private PermissionGroupDAO permissionGroupDAO;
	
	@Mock
	private PermissionDAO permissionDAO;	

	
	private static String USERNAME = "aUsername";
	private static String HASHED_PASSWORD = "aHashedPassword";
	private static String CANVASNAME = "aCanvasName";
	private static String WIDGETNAME = "aWidgetName";
	private static String PERMISSIONGROUPNAME = "aPermissionGroupName";
	private static String PERMISSIONNAME = "aPermissionName";
	private User user;
	private AbstractLicensableWidget widget;
	private AbstractLicensableWidget assignmentCreationWidget;
	private AbstractLicensableWidget assignmentManagementWidget; 
	private AbstractLicensableWidget picklineProgressWidget;
	private AbstractLicensableWidget productivityByZoneWidget;
	private AbstractLicensableWidget userProductivityWidget;
	private AbstractLicensableWidget alertWidget;
	private AbstractLicensableWidget userCreationFormWidget;
	private AbstractLicensableWidget searchUserGridWidget;
	private AbstractLicensableWidget searchProductGridWidget;
	private AbstractLicensableWidget picklineByWaveBarChartWidget;
    private AbstractLicensableWidget groupMaintenanceWidget;
	private DefaultWidgetInstance widgetInstance;	
	private Canvas canvas;
	ActionConfigurable actionConfig;
	DefaultViewConfig defaultViewConfig;
	String defaultViewConfigAsString;
	ActualViewConfig  actualViewConfig;
	private List<Canvas> favoriteCanvasList;	
	private List<Canvas> savedCanvasList;	
	private List<Canvas> companyCanvasList;	
	private WidgetOrientation widgetOrientation;
	
	private static final String userCreationFormWidgetName = "create-or-edit-user-form-widget";	
	
	private List<DefaultWidgetInstance> widgetInstanceList;
	private Permission p1;
	private Permission p2;
	private Permission p3;
	private Permission p4;
	private Permission p5;
	private Permission p6;
	private Permission p7;
	private List<PermissionGroup> permissionGroupList;
	private List<Permission> permissionList;
	private PermissionGroup permissionGroup;
	private Permission permission;
	private ObjectMapper objectMapper;
    final List<Permission> permissions=new ArrayList<Permission>(){
        {
            add(new Permission());
            add(new Permission());
        }
    };
	
    private List<AbstractLicensableWidget> widgetsList = null;
	
	private List<Canvas> canvasList = new ArrayList<Canvas>();



    @Before
	public void setup() throws Exception{
		mockStatic(StringUtils.class);
		user = new User();
		user.setUserId(1L);
		user.setUserName(USERNAME);
		user.setHashedPassword(HASHED_PASSWORD);
		
		permissionGroup = new PermissionGroup();
		permissionGroup.setPermissionGroupId(1L);
		permissionGroup.setPermissionGroupName(PERMISSIONGROUPNAME);
		
		Set<User> userSet = new HashSet<User>();
		userSet.add(user);
		permissionGroup.setUserSet(userSet);
		
		permission = new Permission();
		permission.setPermissionId(1L);
		permission.setPermissionName(PERMISSIONNAME);
		
		permissionGroupList = new ArrayList<PermissionGroup>();
		permissionGroupList.add(permissionGroup);
		
		permissionList = new ArrayList<Permission>();
		permissionList.add(permission);
		
		objectMapper = new ObjectMapper();
		Map<String, String> broadCastMap = new HashMap<String, String>(); 
		broadCastMap.put("Area", "Series.key");
		broadCastMap.put("Route", "point.label");
		List<String> listensFor = new ArrayList<String>();
		listensFor.add("Area");
		listensFor.add("Shift");
		listensFor.add("Picker");
		widget = new UserCreationFormWidget(userCreationFormWidgetName, "CreateUser",
				Enum.valueOf(WidgetType.class, "FORM"),
				objectMapper.writeValueAsString(defaultViewConfig));
        widget.setId(10L);
        
	
		p1 = new Permission("create-assignment");
		p2 = new Permission("view-report-productivity");
		p3 = new Permission("configure-location");
		p4 = new Permission("create-canvas");
		p5 = new Permission("delete-canvas");
		p6 = new Permission("authenticated-user");
		p7 = new Permission("pickingwidget2-widget-access");
		Map<String, Map<Permission, Boolean>> mappedActionConfigurableMap = new HashMap<String, Map<Permission, Boolean>>();
		MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig = new PermissionMappedActionConfig(
				mappedActionConfigurableMap);

		mappedActionConfigurableMap.put("widget-access", new HashMap<Permission, Boolean>(){
			{
			put(p7, Boolean.FALSE);
			}
		});
		
		mappedActionConfigurableMap.put("widget-action", new HashMap<Permission, Boolean>(){
			{
				put(p1, Boolean.FALSE);
				put(p2, Boolean.FALSE);
				put(p3, Boolean.FALSE);
				put(p4, Boolean.FALSE);
				put(p5, Boolean.FALSE);
				put(p6, Boolean.FALSE);
			}
		});


        widget.setActionConfig(mappedActionConfig);
        
        assignmentCreationWidget = new AssignmentCreationWidget();
        assignmentCreationWidget.setActive(false);
        
    	assignmentManagementWidget = new AssignmentManagementWidget();
    	assignmentManagementWidget.setActive(false);
    	
    	picklineProgressWidget = new PicklineProgressWidget();
    	picklineProgressWidget.setActive(false);
    	
    	productivityByZoneWidget = new ProductivityByZoneWidget();
    	productivityByZoneWidget.setActive(false);
    	
    	userProductivityWidget = new UserProductivityWidget();
    	userProductivityWidget.setActive(false);
    	
    	alertWidget = new AlertWidget();
    	alertWidget.setActive(false);
    	
    	userCreationFormWidget = new UserCreationFormWidget();
    	userCreationFormWidget.setActive(true);
    	userCreationFormWidget.setCategory("Administration");
    	
    	searchUserGridWidget = new SearchUserGridWidget();
    	searchUserGridWidget.setActionConfig(mappedActionConfig);
    	searchUserGridWidget.setActive(true);
    	searchUserGridWidget.setCategory("Administration");
    	
    	searchProductGridWidget = new SearchProductGridWidget();
    	searchProductGridWidget.setActionConfig(mappedActionConfig);
    	searchProductGridWidget.setActive(true);
    	searchProductGridWidget.setCategory("Work execution");
    	
    	picklineByWaveBarChartWidget = new PicklineByWaveBarChartWidget();
    	picklineByWaveBarChartWidget.setActive(true);
    	picklineByWaveBarChartWidget.setCategory("Reporting");
    	
		canvas = new Canvas("testCanvas");
		canvas.setCanvasId(10L);
		canvas.setCanvasType(CanvasType.PRIVATE);
		canvas.setShortName("testCanvas");
		canvas.setCreatedByUserName("createdByUserName");
		
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
		defaultViewConfigAsString = objectMapper.writeValueAsString(defaultViewConfig);
		widgetInstance = new DefaultWidgetInstance( objectMapper.writeValueAsString(actualViewConfig) ,canvas, widget);
		
		widgetInstanceList = new ArrayList<DefaultWidgetInstance>();
		widgetInstanceList.add(widgetInstance);
		canvas.setWidgetInstanceList(widgetInstanceList);
		canvas.setCanvasType(CanvasType.PRIVATE);
		canvas.setShortName("testCanvas");
		canvas.setCreatedByUserName("createdByUserName");
		canvas.setWidgetInstanceList(widgetInstanceList);
		favoriteCanvasList = new ArrayList<Canvas>();
		savedCanvasList = new ArrayList<Canvas>();
		companyCanvasList = new ArrayList<Canvas>();
		
		favoriteCanvasList.add(canvas);
		savedCanvasList.add(canvas);
		companyCanvasList.add(canvas);

        final String [] broadcastListArr = {"groupName"};
        final int pageNumber = 0;
        final int pageSize = Integer.MAX_VALUE;
        final String url="/groups/permissions";

        final Map<String, Object > groupMaintenanceWidgetSearchMap = new HashMap<String,Object >();
        final List<String>  permissionGroupNameList=new ArrayList<String>();
        permissionGroupNameList.add("$groupName");
        groupMaintenanceWidgetSearchMap.put("permissionGroupName", permissionGroupNameList);
        final Map<String, SortType> groupMaintenanceWidgetSortMap = new HashMap<String, SortType>();
        groupMaintenanceWidgetSortMap.put("permissionGroupName", SortType.ASC);

        final SearchAndSortCriteria groupMaintenanceWidgetSearchAndSortCriteria = new SearchAndSortCriteria();
        groupMaintenanceWidgetSearchAndSortCriteria.setPageNumber(pageNumber);
        groupMaintenanceWidgetSearchAndSortCriteria.setPageSize(pageSize);
        groupMaintenanceWidgetSearchAndSortCriteria.setSearchMap(groupMaintenanceWidgetSearchMap);
        groupMaintenanceWidgetSearchAndSortCriteria.setSortMap(groupMaintenanceWidgetSortMap);
        final DataUrl groupMaintenanceWidgetDataUrl = new DataUrl(url, groupMaintenanceWidgetSearchAndSortCriteria);

        final ReactToMap reactToMap = new ReactToMap();
        reactToMap.setGroupName(groupMaintenanceWidgetDataUrl);
        this.groupMaintenanceWidget=new GroupMaintenanceWidget();
        groupMaintenanceWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        //MappedActionConfigurable<Permission, Boolean> mappedActionConfigurable = groupMaintenanceWidget.getActionConfig();
        groupMaintenanceWidget.setActive(Boolean.TRUE);
        mappedActionConfig.buildActionConfigurable(permissionList);
        groupMaintenanceWidget.setActionConfig(mappedActionConfig);
        groupMaintenanceWidget.setCategory("Administration");
        widgetsList = new ArrayList<AbstractLicensableWidget>(){
    		{
    			add(picklineByWaveBarChartWidget);
    			add(userCreationFormWidget);
    			add(searchProductGridWidget);
    			add(searchUserGridWidget);
    			add(groupMaintenanceWidget);
    		}
    	};
    	
    	canvasList.add(canvas);
    	
	}

	

	@Test
    public void testSaveWidget(){
		when(widgetDAO.load(anyLong())).thenReturn(null);
		uIService.saveWidget(widget);
		verify(widgetDAO, times(1)).save(any());
	}
	
	
	/*@Test
    public void testCreateWidgetInstance() {
		when(widgetDAO.load(anyLong())).thenReturn(null);
		uIService.createWidgetInstance(widget,defaultViewConfigAsString);
		verify(widgetInstanceDAO, times(1)).save(any());
	}*/	

	@Test(expected=WidgetAlreadyExistsException.class)
    public void testSaveExistingWidget(){
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(widget);
		uIService.saveWidget(widget);
		verify(widgetDAO, times(0)).save(any());
	}
	
	@Test
    public void testDeleteCanvas(){
		when(canvasDAO.load(anyLong())).thenReturn(canvas);
		uIService.deleteCanvas(canvas.getCanvasId());
		verify(canvasDAO, times(1)).delete(any(Canvas.class));	
	}
	
	
	@Test
    public void testDeleteCanvasThatDoesNotExist(){
		when(canvasDAO.load(anyLong())).thenReturn(null);
		uIService.deleteCanvas(canvas.getCanvasId());
		verify(canvasDAO, never()).delete(any(Canvas.class));	
	}
	
	@Test
    public void testDeleteCanvasWithNullId(){
		uIService.deleteCanvas(null);
		verify(canvasDAO, never()).load(anyLong());
		verify(canvasDAO, never()).delete(any(Canvas.class));	
	}
    
	@Test
    public void testDeleteWidget(){
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(widget);
		uIService.deleteWidget(widget.getName());
		verify(widgetDAO, times(1)).delete(any(AreaRouteChartWidget.class));	
	}
	@Test
    public void testDeleteWidgetThatDoesNotExist(){
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(null);
		uIService.deleteWidget(widget.getName());
		verify(widgetDAO, never()).delete(any(AreaRouteChartWidget.class));	
	}
	
	@Test
    public void testDeleteWidgetWithNullId(){
		uIService.deleteWidget(null);
		verify(widgetDAO, never()).load(anyLong());
		verify(widgetDAO, never()).delete(any(AreaRouteChartWidget.class));	
	}
	@Test
	public void testGetCanvasByName(){
		when(canvasDAO.getCanvasByName(anyString())).thenReturn(canvas);
		uIService.getCanvasByName(CANVASNAME);
		verify(canvasDAO, times(1)).getCanvasByName(anyString());
	}

	@Test
	public void testGetWidgetByName(){
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(widget);
		uIService.getWidgetByName(WIDGETNAME);
		verify(widgetDAO, times(1)).getWidgetByName(anyString());
	}
	
	@Test
	public void testCreateOrUpdateWidgetInstanceWithNullWidgetInstanceId() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
		when(canvasDAO.save(any())).thenReturn(canvas);
		when(uIService.findByCanvasId((anyLong()))).thenReturn(canvas);
		uIService.createOrUpdateWidgetInstance(widgetInstance);
		verify(canvasDAO, times(1)).save(any());	
	}
	
	@Test
	public void testCreateOrUpdateWidgetInstanceWithNonNullWidgetInstanceId() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
		when(canvasDAO.save(any())).thenReturn(canvas);
		DefaultWidgetInstance dwi = new DefaultWidgetInstance( objectMapper.writeValueAsString(actualViewConfig) ,canvas, widget);
		dwi.setWidgetinstanceId(1l);
		when(uIService.findByCanvasId((anyLong()))).thenReturn(canvas);
		uIService.createOrUpdateWidgetInstance(dwi);
		verify(canvasDAO, times(1)).save(any());	
	}
	
	@Test
	public void testGetSearchUserGridWidget() throws Exception {
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(searchUserGridWidget);
		when(securityService.getPermissionsForCurrentUser()).thenReturn(permissionList);
		uIService.getSearchUserGridWidgetDefinition();
		verify(widgetDAO, times(1)).getWidgetByName(anyString());
	}
	
	@Test
	public void testGetSearchProductGridWidget() throws Exception {
		when(widgetDAO.getWidgetByName(anyString())).thenReturn(searchProductGridWidget);
		when(securityService.getPermissionsForCurrentUser()).thenReturn(permissionList);
		uIService.getSearchProductGridWidgetDefinition();
		verify(widgetDAO, times(1)).getWidgetByName(anyString());
	}
	
	@Test
	public void testGetWidgetDefinitionsByCategory()throws Exception{
		when(widgetDAO.getAllActiveWidgets()).thenReturn(widgetsList);
		uIService.getWidgetDefinitionsByCategory();
		verify(widgetDAO, times(1)).getAllActiveWidgets();
	}
	
	@Test
	public void testGetUserCreationFormWidgetDefinition() throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		String widgetName = "create-or-edit-user-form-widget";
		List<AbstractLicensableWidget> widgetList = new ArrayList<AbstractLicensableWidget>();
		widget.setDefinitionMethod("getUserCreationFormWidgetDefinition");
		widgetList.add(widget);
		when(widgetDAO.getAllWidgets()).thenReturn(widgetList);
		when(widgetDAO.getWidgetByName(widgetName)).thenReturn(widget);
		AbstractLicensableWidget userCreationFormWidget = uIService.getWidgetDefinition(widgetName);
		Assert.notNull(userCreationFormWidget);
	}	
	
	
	@Test
	public void testGetWidgetInstanceListForCanvas() throws Exception {
		when(securityService.getPermissionsForCurrentUser()).thenReturn(permissions);
		when(uIService.getWidgetInstancesByCanvasId(1l)).thenReturn(widgetInstanceList);
		uIService.getWidgetInstanceListForCanvas(1l, "jack123");
		verify(widgetInstanceDAO, times(1)).getWidgetInstancesByCanvasId(anyLong());
	}
	
	@Test
	public void testGetCanvasesByCategories() throws Exception {
		when(canvasDAO.getUserPrivateCanvases(USERNAME)).thenReturn(canvasList);
		when(canvasDAO.getCanvasesByType(CanvasType.COMPANY)).thenReturn(canvasList);
		when(canvasDAO.getCanvasesByType(CanvasType.LUCAS)).thenReturn(canvasList);
		uIService.getCanvasesByCategories();
		verify(canvasDAO, times(1)).getUserPrivateCanvases(anyString());
		verify(canvasDAO, times(2)).getCanvasesByType(any(CanvasType.class));
	}
}
