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
import java.lang.reflect.Method;
import java.util.*;

import javax.inject.Inject;





import com.lucas.dao.ui.*;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.ui.widget.*;
import com.lucas.services.util.OpenUserCanvasComparator;
import com.lucas.services.util.WidgetInstanceComparator;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasLayout;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.ui.dataurl.DataUrl;
import com.lucas.entity.ui.reacttomap.ReactToMap;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;
import com.lucas.exception.CanvasAlreadyExistsException;
import com.lucas.exception.WidgetAlreadyExistsException;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.sort.SortType;
import com.lucas.services.util.CollectionsUtilService;

@Service("uiService")
public class UIServiceImpl implements UIService {


    private static final String UserCreationFormWidgetName = "create-or-edit-user-form-widget";

    private static final String SearchUserGridWidgetName = "search-user-grid-widget";

    private static final String EquipmentTypeGridWidgetName = "equipment-type-grid-widget";

    private static final String SearchGroupGridWidgetName = "search-group-grid-widget";

    private static final String SearchProductGridWidgetName = "search-product-grid-widget";

    private static final String PicklineByWaveBarChartWidgetName = "pickline-by-wave-barchart-widget";

    private static final String GroupMaintenanceWidgetName = "group-maintenance-widget";
    
	private static final String eaiMessageSegmentGridWidgetName = "message-segment-grid-widget";

	private static final String messageMappingGridWidgetName = "message-mappings-grid-widget";
	
	private static final String eventHandlertGridWidgetName = "event-handler-grid-widget";

    private static final String WIDGETINSTANCE_ACTION_UPDATE = "update";

    private static final String WIDGETINSTANCE_ACTION_CREATE = "create";

    private static final String VIEW_CANVAS_ACTION = "view-canvas";

    private static final String EquipmentManagerGridWidget = "equipment-manager-grid-widget";
    
    private static final String MessageGridWidget = "message-grid-widget";

	private static final String EventGridWidgetName = "event-grid-widget";

    private static Logger LOG = LoggerFactory.getLogger(UIServiceImpl.class);

    private final WidgetDAO widgetDAO;

    private final WidgetInstanceDAO widgetInstanceDAO;

    private final CanvasDAO canvasDAO;
    private final CanvasLayoutDAO canvasLayoutDAO;

    private final UserDAO userDAO;
    private final PermissionGroupDAO permissionGroupDAO;
    private final PermissionDAO permissionDAO;
    private final OpenUserCanvasDAO openUserCanvasDAO;

    private final SecurityService securityService;

    @Inject
    public UIServiceImpl(CanvasDAO canvasDAO, WidgetDAO widgetDAO,
                         WidgetInstanceDAO widgetInstanceDAO, UserDAO userDAO,
                         PermissionGroupDAO permissionGroupDAO, PermissionDAO permissionDAO,
                         SecurityService securityService, CanvasLayoutDAO canvasLayoutDAO
            , OpenUserCanvasDAO openUserCanvasDAO) {
        this.canvasDAO = canvasDAO;
        this.widgetDAO = widgetDAO;
        this.userDAO = userDAO;
        this.widgetInstanceDAO = widgetInstanceDAO;
        this.permissionGroupDAO = permissionGroupDAO;
        this.permissionDAO = permissionDAO;
        this.securityService = securityService;
        this.canvasLayoutDAO = canvasLayoutDAO;
        this.openUserCanvasDAO = openUserCanvasDAO;
    }

    /* (non-Javadoc)
     * @see com.lucas.services.ui.UIService#saveCanvas(com.lucas.entity.ui.canvas.Canvas)
     */
    @Transactional
    @Override
    public Long saveCanvas(Canvas canvas) throws Exception {
        if (canvas != null) {

            if (canvas.getCanvasId() != null) {
                Canvas retrievedCanvas = getCanvasById(canvas.getCanvasId());
                retrievedCanvas.setCanvasType(canvas.getCanvasType());
                retrievedCanvas.setUpdatedByUserName(canvas.getUpdatedByUserName());
                retrievedCanvas.setUpdatedDateTime(canvas.getUpdatedDateTime());
                retrievedCanvas.setName(canvas.getName());
                retrievedCanvas.setShortName(canvas.getShortName());
                canvas = retrievedCanvas;
            } else if (canvas.getName() != null) {
                Canvas retrievedCanvas = canvasDAO.getCanvasByName(canvas
                        .getName());
                if (retrievedCanvas != null) {
                    throw new CanvasAlreadyExistsException("Canvas name already exists");
                }
            }

            Canvas savedCanvas = canvasDAO.save(canvas);
            return savedCanvas.getCanvasId();
        }

        return null;
    }


    @Transactional
    @Override
    public void deleteCanvas(Long canvasId) {
        if (canvasId != null) {
            Canvas retrievedCanvas = canvasDAO.load(canvasId);
            if (retrievedCanvas != null) {
                canvasDAO.delete(retrievedCanvas);
            } else {
                LOG.debug(String.format("Did not delete any canvas as no canvas with canvasId %s found.", canvasId));
            }
        } else {
            LOG.debug("Did not delete any canvas as null canvasId was passed in.");
        }
    }

    @Transactional
    @Override

    public void deleteWidget(String widgetName) {
        if (widgetName != null) {
            AbstractLicensableWidget retrievedWidget = widgetDAO.getWidgetByName(widgetName);
            if (retrievedWidget != null) {
                widgetDAO.delete(retrievedWidget);
            } else {
                LOG.debug(String.format("Did not delete any widget as no widget with widgetName %s found.", widgetName));
            }
        } else {
            LOG.debug("Did not delete any widget as null widgetName was passed in.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Canvas getCanvasByName(String canvasName) {
        return canvasDAO.getCanvasByName(canvasName);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.ui.UIService#findByCanvasId(java.lang.Long)
     */
    @Transactional(readOnly = true)
    public Canvas findByCanvasId(Long canvasId) {
        return canvasDAO.findByCanvasId(canvasId);
    }

    @Transactional(readOnly = true)
    @Override
    public AbstractLicensableWidget getWidgetByName(String widgetName) {
        return widgetDAO.getWidgetByName(widgetName);
    }


    List<DefaultWidgetInstance> getWidgetInstancesByCanvasId(Long canvasId) {
        return widgetInstanceDAO.getWidgetInstancesByCanvasId(canvasId);
    }

    @Override
    @Transactional
    public CanvasLayout getCanvasLayoutById(Long layoutID) {
        return canvasLayoutDAO.getCanvasLayoutById(layoutID);
    }

    @Override
    @Transactional
    public Set<User> getUserSetByCanvasId(Long canvasId) {
        return canvasDAO.getUserSetByCanvasId(canvasId);
    }

    /**
     * This method stores the Widget definition
     * along with its attributes (name, shortName, type, desc, defaultActionConfig and defaultViewConfig)
     *
     * @param widget
     */
    @Override
    @Transactional
    public void saveWidget(AbstractLicensableWidget widget) {
        AbstractLicensableWidget retrievedWidget = getWidgetByName(widget.getName());
        if (retrievedWidget != null) {
            LOG.debug("Widget already exists");
            throw new WidgetAlreadyExistsException(String.format("Widget already exists with name %s. Cannot create widget!", widget.getName()));
        }

        widgetDAO.save(widget);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public List<DefaultWidgetInstance> getWidgetInstanceListForCanvas(
            Long canvasId, String username) throws Exception {

        List<DefaultWidgetInstance> widgetInstanceList = getWidgetInstancesByCanvasId(canvasId);
        for (DefaultWidgetInstance widgetInstance : CollectionsUtilService.nullGuard(widgetInstanceList)) {
            securityService.buildWidgetActionConfig(widgetInstance.getWidget());
        }
        return widgetInstanceList;
    }

    /**
     * This method returns the widget definitions according to the widget categories
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public MultiMap getWidgetDefinitionsByCategory() throws JsonParseException,
            JsonMappingException, IOException, Exception {

        MultiMap widgetDefinitionsByCategoryMap = new MultiValueMap();
        // Retrieve all the active widgets
        List<AbstractLicensableWidget> activeWidgets = widgetDAO
                .getAllActiveWidgets();
        for (AbstractLicensableWidget widget : activeWidgets) {
            // Build the action config for the widgets
            securityService.buildWidgetActionConfig(widget);
            Map<String, Map<Permission, Boolean>> actionConfigMap = null;
            if (widget.getActionConfig() != null) {
                actionConfigMap = widget.getActionConfig().getActionConfig();
                if (actionConfigMap != null && actionConfigMap.size() > 0) {
                    Map<Permission, Boolean> widgetAccessMap = actionConfigMap
                            .get("widget-access");
                    if (widgetAccessMap != null && widgetAccessMap.size() > 0) {
                        // Based on the user logged in permissions return the
                        // widget definitions by category
                        Map.Entry<Permission, Boolean> widgetAccessEntry = widgetAccessMap
                                .entrySet().iterator().next();
                        if (widgetAccessEntry.getValue()) {
                            widgetDefinitionsByCategoryMap.put(
                                    widget.getCategory(), widget);
                        }
                    }
                }
            }

        }
        return widgetDefinitionsByCategoryMap;
    }



	/*	
    /**
	 * This method can be used to retrieve an instance of a widget.
	 * The instance is tied to a canvas. A canvas is either at ClientHierachy level, saved or favorite canvases.
	 * Given an instance of such a canvas, this method returns all widget instances on this canvas.
	 * Assumption is that before this method is invoked, the following relationship is already persisted:
	 * canvasId, widgetInstanceId, widgetId, widgetViewConfig(JSON) and widgetActionConfig(JSON)
	 *  
	 * @param username
	 * @return
	 *//*
    public List<WidgetInstance<Map<Permission, Boolean>>> getUserProductivityWidgetInstanceListForCanvas(Long canvasId, String username) {

		//Look for all widgetInstances that are tied to the current canvas -> get a List of widgetInstanceIds
		//For each widgetInsanceId, get the widgetId
			//widgetInstance.getWidgetId();
			//Create a Widget object		
			//Get the AbstractLicensableWidget object from that widget
			//Based on the user logged in populate the widgetActionConfig
			//The widgetViewConfig is unchanged
		//Return the modified widgetInsance list.

		//TODO --> Can be implemented similar to getWidgetInstanceListForUser(userName);
		return null;
	}
	  */

    @Transactional(readOnly = true)
    public AbstractLicensableWidget getWidgetDefinition(String widgetName) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        Map<String, Method> widgetMap = initializeWidgetMap();
        //TODO : Incase of widget imported then instead of this object we need to pass the dynamically loaded widget service
        AbstractLicensableWidget abstractLicensableWidget = (AbstractLicensableWidget) widgetMap.get(widgetName).invoke(this);
        return abstractLicensableWidget;
    }

    private Map<String, Method> initializeWidgetMap() throws JsonProcessingException, SecurityException, NoSuchMethodException {
        Map<String, Method> widgetMap = new HashMap<String, Method>();
        List<AbstractLicensableWidget> abstractLicensableWidgetList = widgetDAO.getAllWidgets();
        for (AbstractLicensableWidget abstractLicensableWidget : CollectionsUtilService.nullGuard(abstractLicensableWidgetList)) {
            if (abstractLicensableWidget.getDefinitionMethod() != null) {
                //TODO : Incase of widget imported then instead of this object we need to pass the dynamically loaded widget service
                Method method = this.getClass().getMethod(abstractLicensableWidget.getDefinitionMethod());
                widgetMap.put(abstractLicensableWidget.getName(), method);
            }
        }

        return widgetMap;
    }

    @Transactional(readOnly = true)
    public UserCreationFormWidget getUserCreationFormWidgetDefinition() throws Exception {
        String userNameUrl = "/users/userlist/search";
        int pageNumber = 0;
        int pageSize = 10;
        List<String> searchMapValueList = new ArrayList<String>();
        searchMapValueList.add("$userName");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("userName", searchMapValueList);
        SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        searchAndSortCriteria.setPageNumber(pageNumber);
        searchAndSortCriteria.setPageSize(pageSize);
        searchAndSortCriteria.setSearchMap(searchMap);
        DataUrl userNameDataUrl = new DataUrl(userNameUrl, searchAndSortCriteria);
        ReactToMap reactToMap = new ReactToMap(userNameDataUrl, null, null, null);
        String[] broadcastListArr = {"userName"};
        String[] reactToListArr = {"userName"};
        UserCreationFormWidget userCreationFormWidget = (UserCreationFormWidget) widgetDAO.getWidgetByName(UserCreationFormWidgetName);
        userCreationFormWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        userCreationFormWidget.setReactToList(Arrays.asList(broadcastListArr));
        LucasObjectMapper jacksonObjectMapper = new LucasObjectMapper();
        User user = new User();
        user.setStartDate(new Date());

		/* BE will no longer send the list of languages , A seperate end point will be invoked by AMD which will internally use 
		 * the supported_language table.
		
		userCreationFormWidget.setJenniferToUserLanguageList(new ArrayList<Languages>(Arrays.asList(Languages.values())));
		userCreationFormWidget.setamdScreenLanguageList(new ArrayList<Languages>(Arrays.asList(Languages.values())));
		userCreationFormWidget.setHandheldScreenLanguageList(new ArrayList<Languages>(Arrays.asList(Languages.values())));
		userCreationFormWidget.setUsertoJenniferLanguageList(new ArrayList<Languages>(Arrays.asList(Languages.values())));
		*/
		
		/* TODO - wil we be using definition data any more ? , current use is to use just provide a default date to AMD.
		 * 
		 */
        //userCreationFormWidget.setDefinitionData(jacksonObjectMapper.writeValueAsString(user));


        //We need to remove 548 to 553 and call the buildActionConfig when we shall actually build action config for this widget.
        //MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfigurable = userCreationFormWidget.getActionConfig();

        //userCreationFormWidget.setActionConfig(mappedActionConfigurable);

        //PHX - 1192 added the call to buildActionConfig to build action config for this widget
        securityService.buildWidgetActionConfig(userCreationFormWidget);

        return userCreationFormWidget;
    }

    @Transactional(readOnly = true)
    public SearchUserGridWidget getSearchUserGridWidgetDefinition() throws Exception {
        String[] broadcastListArr = {"userName"};
        String[] reactToListArr = {"userName"};
        String url = "/users/userlist/search";
        int pageNumber = 0;
        int pageSize = 10;
        List<String> userNameSearchMapValueList = new ArrayList<String>();
        List<String> hostLoginSearchMapValueList = new ArrayList<String>();
        userNameSearchMapValueList.add("$userName");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("userName", userNameSearchMapValueList);
        SearchAndSortCriteria userNameSearchAndSortCriteria = new SearchAndSortCriteria();
        userNameSearchAndSortCriteria.setPageNumber(pageNumber);
        userNameSearchAndSortCriteria.setPageSize(pageSize);
        userNameSearchAndSortCriteria.setSearchMap(searchMap);
        DataUrl userNameDataUrl = new DataUrl(url, userNameSearchAndSortCriteria);

        SearchAndSortCriteria hostLoginSearchAndSortCriteria = new SearchAndSortCriteria();
        hostLoginSearchAndSortCriteria.setPageNumber(pageNumber);
        hostLoginSearchAndSortCriteria.setPageSize(pageSize);
        hostLoginSearchAndSortCriteria.setSearchMap(searchMap);
        DataUrl hostLoginDataUrl = new DataUrl(url, hostLoginSearchAndSortCriteria);

        ReactToMap reactToMap = new ReactToMap(userNameDataUrl, null, hostLoginDataUrl, null);
        SearchUserGridWidget searchUserGridWidget = (SearchUserGridWidget) widgetDAO.getWidgetByName(SearchUserGridWidgetName);
        searchUserGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        searchUserGridWidget.setReactToList(Arrays.asList(reactToListArr));

        //Build the widget action config
        securityService.buildWidgetActionConfig(searchUserGridWidget);


        return searchUserGridWidget;
    }


    @Transactional(readOnly = true)
    public EquipmentTypeGridWidget getEquipmentTypeGridWidgetDefinition() throws Exception {
        final String[] broadcastListArr = {"equipmentTypeName"};
        final String[] reactToListArr = {"equipmentTypeName"};
        final String url = "equipments/equipment-type-list/search";
        final int pageNumber = 0;
        final int pageSize = 10;
        final List<String> equipmentTypeNameSearchMapValueList = new ArrayList<String>();
        equipmentTypeNameSearchMapValueList.add("$equipmentTypeName");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("equipmentTypeName", equipmentTypeNameSearchMapValueList);
        SearchAndSortCriteria equipmentTypeNameSearchAndSortCriteria = new SearchAndSortCriteria();
        equipmentTypeNameSearchAndSortCriteria.setPageNumber(pageNumber);
        equipmentTypeNameSearchAndSortCriteria.setPageSize(pageSize);
        equipmentTypeNameSearchAndSortCriteria.setSearchMap(searchMap);
        final DataUrl equipmentTypeDataUrl = new DataUrl(url, equipmentTypeNameSearchAndSortCriteria);

        final ReactToMap reactToMap = new ReactToMap(null, null, null, null, equipmentTypeDataUrl);
        final EquipmentTypeGridWidget equipmentTypeGridWidget = (EquipmentTypeGridWidget) this.widgetDAO.getWidgetByName(EquipmentTypeGridWidgetName);
        equipmentTypeGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        equipmentTypeGridWidget.setReactToList(Arrays.asList(reactToListArr));

        //Build the widget action config
        securityService.buildWidgetActionConfig(equipmentTypeGridWidget);
        return equipmentTypeGridWidget;
    }


    @Transactional(readOnly = true)
    public SearchGroupGridWidget getSearchGroupGridWidgetDefinition() throws Exception {
        final String[] broadcastListArr = {"groupName"};
        final String[] reactToListArr = {"userName"};
        final String url = "/groups/grouplist/search";
        final int pageNumber = 0;
        final int pageSize = 10;
        final List<String> groupSearchMapValueList = new ArrayList<String>();
        groupSearchMapValueList.add("$groupName");

        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("groupName", groupSearchMapValueList);

        final SearchAndSortCriteria groupNameSearchAndSortCriteria = new SearchAndSortCriteria();
        groupNameSearchAndSortCriteria.setPageNumber(pageNumber);
        groupNameSearchAndSortCriteria.setPageSize(pageSize);
        groupNameSearchAndSortCriteria.setSearchMap(searchMap);
        final DataUrl groupNameDataUrl = new DataUrl(url, groupNameSearchAndSortCriteria);

       /* final SearchAndSortCriteria hostLoginSearchAndSortCriteria = new SearchAndSortCriteria();
        hostLoginSearchAndSortCriteria.setPageNumber(pageNumber);
        hostLoginSearchAndSortCriteria.setPageSize(pageSize);
        hostLoginSearchAndSortCriteria.setSearchMap(searchMap);
        final DataUrl hostLoginDataUrl = new DataUrl(url, hostLoginSearchAndSortCriteria);*/

        final ReactToMap reactToMap = new ReactToMap(null, groupNameDataUrl, null, null);
        final SearchGroupGridWidget searchGroupGridWidget = (SearchGroupGridWidget) widgetDAO.getWidgetByName(SearchGroupGridWidgetName);
        searchGroupGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        searchGroupGridWidget.setReactToList(Arrays.asList(reactToListArr));
        //Build the widget action config
        this.securityService.buildWidgetActionConfig(searchGroupGridWidget);
        return searchGroupGridWidget;
    }

    @Transactional(readOnly = true)
    public PicklineByWaveBarChartWidget getPicklineByWaveBarChartWidgetDefinition() throws Exception {
        PicklineByWaveBarChartWidget picklineByWaveBarChartWidget = (PicklineByWaveBarChartWidget) widgetDAO.getWidgetByName(PicklineByWaveBarChartWidgetName);

        //Build the action config for the widget
        securityService.buildWidgetActionConfig(picklineByWaveBarChartWidget);

        return picklineByWaveBarChartWidget;
    }


    /* (non-Javadoc)
     * @see com.lucas.services.ui.UIService#createOrUpdateWidgetInstance(com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance)
     */
    @Transactional(readOnly = false)
    @Override
    public Map<String, String> createOrUpdateWidgetInstance(DefaultWidgetInstance defaultWidgetInstance) {
        String widgetInstanceAction = null;

        Map<String, String> responsMap = new HashMap<String, String>();
        Canvas canvas = new Canvas();
        List<DefaultWidgetInstance> widgetInstanceList = new ArrayList<DefaultWidgetInstance>();

        Canvas retrivedcanvas = null;

        if (defaultWidgetInstance.getCanvas().getCanvasId() != null)
            retrivedcanvas = findByCanvasId(defaultWidgetInstance.getCanvas().getCanvasId());

        if (retrivedcanvas == null) {
            String responseMsg = "We cannot create or update Widget Instances for the canvas : " + defaultWidgetInstance.getCanvas().getCanvasId().toString() + " as it does not exist.";
            LOG.debug(responseMsg);
            responsMap.put("widgetInstanceAction", responseMsg);
            return responsMap;
        }
        canvas = retrivedcanvas;
        widgetInstanceList = canvas.getWidgetInstanceList();

        Long widgetInstanceId = defaultWidgetInstance.getWidgetinstanceId();
        if (widgetInstanceId != null) {
            widgetInstanceAction = WIDGETINSTANCE_ACTION_UPDATE;
            for (DefaultWidgetInstance widgetInstance : CollectionsUtilService.nullGuard(widgetInstanceList)) {
                if (widgetInstance.getWidgetinstanceId() == defaultWidgetInstance.getWidgetinstanceId()) {
                    widgetInstance.setActualViewConfig(defaultWidgetInstance.getActualViewConfig());
                    widgetInstance.setDataURL(defaultWidgetInstance.getDataURL());
                    widgetInstance.setWidgetInteractionConfig(defaultWidgetInstance.getWidgetInteractionConfig());
                }
            }
        } else {
            widgetInstanceAction = WIDGETINSTANCE_ACTION_CREATE;
            if (widgetInstanceList == null) {
                widgetInstanceList = new ArrayList<DefaultWidgetInstance>();
            }
            widgetInstanceList.add(defaultWidgetInstance);

        }


        responsMap.put("widgetInstanceAction", widgetInstanceAction);
        canvas.setWidgetInstanceList(widgetInstanceList);
        canvasDAO.save(canvas);

        return responsMap;
    }

    @Transactional(readOnly = true)
    @Override
    public Long getRecentlyInsertedWidgetInstanceId(String canvasName) {
        Long widgetInstanceId = null;
        Canvas retrievedCanvas = getCanvasByName(canvasName);
        List<DefaultWidgetInstance> widgetInstanceList = retrievedCanvas.getWidgetInstanceList();

        Collections.sort(widgetInstanceList);

        if (widgetInstanceList != null && widgetInstanceList.size() > 0) {
            widgetInstanceId = widgetInstanceList.get(
                    widgetInstanceList.size() - 1).getWidgetinstanceId();
        }
        return widgetInstanceId;
    }


    @Transactional(readOnly = true)
    @Override
    public GroupMaintenanceWidget getGroupMaintenanceWidgetDefinition() throws Exception {
        final String[] broadcastListArr = {"groupName"};
        final int pageNumber = 0;
        final int pageSize = Integer.MAX_VALUE;
        final String url = "/users/groups/permissions";

        final Map<String, Object> groupMaintenanceWidgetSearchMap = new HashMap<String, Object>();
        final List<String> permissionGroupNameList = new ArrayList<String>();
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

        final GroupMaintenanceWidget groupMaintenanceWidget = (GroupMaintenanceWidget) widgetDAO.getWidgetByName(GroupMaintenanceWidgetName);
        groupMaintenanceWidget.setBroadcastList(Arrays.asList(broadcastListArr));

        //Build the action config for this widget
        securityService.buildWidgetActionConfig(groupMaintenanceWidget);

        return groupMaintenanceWidget;
    }


    @Transactional(readOnly = true)
    public SearchProductGridWidget getSearchProductGridWidgetDefinition() throws Exception {
        String[] broadcastListArr = {"productName"};
        String[] reactToListArr = {"userName"};
        String url = "/products/productlist/search";
        int pageNumber = 0;
        int pageSize = 10;
        List<String> productNameSearchMapValueList = new ArrayList<String>();
        productNameSearchMapValueList.add("$productName");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("productName", productNameSearchMapValueList);
        SearchAndSortCriteria productNameSearchAndSortCriteria = new SearchAndSortCriteria();
        productNameSearchAndSortCriteria.setPageNumber(pageNumber);
        productNameSearchAndSortCriteria.setPageSize(pageSize);
        productNameSearchAndSortCriteria.setSearchMap(searchMap);
        DataUrl productNameDataUrl = new DataUrl(url, productNameSearchAndSortCriteria);


        SearchProductGridWidget searchProductGridWidget = (SearchProductGridWidget) widgetDAO.getWidgetByName(SearchProductGridWidgetName);
        searchProductGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        searchProductGridWidget.setReactToList(Arrays.asList(reactToListArr));
        //Build the action config for this widget.
        securityService.buildWidgetActionConfig(searchProductGridWidget);
        return searchProductGridWidget;
    }
    
    
    @Transactional(readOnly = true)
	public MessageSegmentGridWidget getEaiMessageSegmentGridWidgetDefinition()
			throws Exception {
		String[] broadcastListArr = { "segmentName" };
		// String[] reactToListArr = {"userName"};
		String url = "/segments/segmentlist/search";
		int pageNumber = 0;
		int pageSize = 10;
		List<String> segmentNameSearchMapValueList = new ArrayList<String>();
		// List<String> hostLoginSearchMapValueList = new ArrayList<String>();
		segmentNameSearchMapValueList.add("$segmentName");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("segmentName", segmentNameSearchMapValueList);
		SearchAndSortCriteria segmentNameSearchAndSortCriteria = new SearchAndSortCriteria();
		segmentNameSearchAndSortCriteria.setPageNumber(pageNumber);
		segmentNameSearchAndSortCriteria.setPageSize(pageSize);
		segmentNameSearchAndSortCriteria.setSearchMap(searchMap);
		// DataUrl userNameDataUrl = new DataUrl(url,
		// segmentNameSearchAndSortCriteria);

		SearchAndSortCriteria hostLoginSearchAndSortCriteria = new SearchAndSortCriteria();
		hostLoginSearchAndSortCriteria.setPageNumber(pageNumber);
		hostLoginSearchAndSortCriteria.setPageSize(pageSize);
		hostLoginSearchAndSortCriteria.setSearchMap(searchMap);
		// DataUrl hostLoginDataUrl = new DataUrl(url,
		// hostLoginSearchAndSortCriteria);

		// ReactToMap reactToMap = new ReactToMap(userNameDataUrl, null,
		// hostLoginDataUrl, null);
		MessageSegmentGridWidget searchSegmentGridWidget = (MessageSegmentGridWidget) widgetDAO
				.getWidgetByName(eaiMessageSegmentGridWidgetName);
		/*searchSegmentGridWidget.setBroadcastList(Arrays
				.asList(broadcastListArr));*/
		// searchUserGridWidget.setReactToList(Arrays.asList(reactToListArr));

		// Build the widget action config
		securityService.buildWidgetActionConfig(searchSegmentGridWidget);

		return searchSegmentGridWidget;
	}
    
    
    @Transactional(readOnly = true)
  	public MessageMappingGridWidget getMessageMappingGridWidgetDefinition()
  			throws Exception {
  		String[] broadcastListArr = { "mappingName" };
  		// String[] reactToListArr = {"userName"};
  		String url = "/mappings/mappinglist/search";
  		int pageNumber = 0;
  		int pageSize = 10;
  		List<String> mappingNameSearchMapValueList = new ArrayList<String>();
  		// List<String> hostLoginSearchMapValueList = new ArrayList<String>();
  		mappingNameSearchMapValueList.add("$mappingName");
  		Map<String, Object> searchMap = new HashMap<String, Object>();
  		searchMap.put("mappingName", mappingNameSearchMapValueList);
  		SearchAndSortCriteria mappingNameSearchAndSortCriteria = new SearchAndSortCriteria();
  		mappingNameSearchAndSortCriteria.setPageNumber(pageNumber);
  		mappingNameSearchAndSortCriteria.setPageSize(pageSize);
  		mappingNameSearchAndSortCriteria.setSearchMap(searchMap);
  		// DataUrl userNameDataUrl = new DataUrl(url,
  		// segmentNameSearchAndSortCriteria);

  		SearchAndSortCriteria hostLoginSearchAndSortCriteria = new SearchAndSortCriteria();
  		hostLoginSearchAndSortCriteria.setPageNumber(pageNumber);
  		hostLoginSearchAndSortCriteria.setPageSize(pageSize);
  		hostLoginSearchAndSortCriteria.setSearchMap(searchMap);
  		// DataUrl hostLoginDataUrl = new DataUrl(url,
  		// hostLoginSearchAndSortCriteria);

  		// ReactToMap reactToMap = new ReactToMap(userNameDataUrl, null,
  		// hostLoginDataUrl, null);
  		MessageMappingGridWidget searchMappingGridWidget = (MessageMappingGridWidget) widgetDAO
  				.getWidgetByName(messageMappingGridWidgetName);
  		/*searchSegmentGridWidget.setBroadcastList(Arrays
  				.asList(broadcastListArr));*/
  		// searchUserGridWidget.setReactToList(Arrays.asList(reactToListArr));

  		// Build the widget action config
  		securityService.buildWidgetActionConfig(searchMappingGridWidget);

  		return searchMappingGridWidget;
  	}
    

    @Transactional(readOnly = true)
	public EventHandlerGridWidget getEventHandlerGridWidgetDefinition()
			throws Exception {
		String[] broadcastListArr = { "eventHandlerName" };
		String url = "/eventhandlers/eventhandlerlist/search";
		int pageNumber = 0;
		int pageSize = 10;
		List<String> eventHandlerNameSearchMapValueList = new ArrayList<String>();
		// List<String> hostLoginSearchMapValueList = new ArrayList<String>();
		eventHandlerNameSearchMapValueList.add("$eventHandlerName");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("eventHandlerName", eventHandlerNameSearchMapValueList);
		SearchAndSortCriteria eventHandlerNameSearchAndSortCriteria = new SearchAndSortCriteria();
		eventHandlerNameSearchAndSortCriteria.setPageNumber(pageNumber);
		eventHandlerNameSearchAndSortCriteria.setPageSize(pageSize);
		eventHandlerNameSearchAndSortCriteria.setSearchMap(searchMap);

		SearchAndSortCriteria hostLoginSearchAndSortCriteria = new SearchAndSortCriteria();
		hostLoginSearchAndSortCriteria.setPageNumber(pageNumber);
		hostLoginSearchAndSortCriteria.setPageSize(pageSize);
		hostLoginSearchAndSortCriteria.setSearchMap(searchMap);
		EventHandlerGridWidget searchEventHandlerGridWidget = (EventHandlerGridWidget) widgetDAO
				.getWidgetByName(eventHandlertGridWidgetName);
		/*searchSegmentGridWidget.setBroadcastList(Arrays
				.asList(broadcastListArr));*/
		// searchUserGridWidget.setReactToList(Arrays.asList(reactToListArr));

		// Build the widget action config
		securityService.buildWidgetActionConfig(searchEventHandlerGridWidget);

		return searchEventHandlerGridWidget;
	}
    
    
    
    @Override
    @Transactional(readOnly = true)
    public Canvas getCanvasById(Long canvasId) throws Exception {
        final List<DefaultWidgetInstance> widgetInstanceList = getWidgetInstancesByCanvasId(canvasId);
        for (DefaultWidgetInstance defaultWidgetInstance : widgetInstanceList) {
            this.securityService.buildWidgetActionConfig(defaultWidgetInstance.getWidget());
        }
        final Canvas canvas = this.canvasDAO.findByCanvasId(canvasId);
        canvas.setWidgetInstanceList(widgetInstanceList);
        return canvas;
    }


    /**
     * getHydratedCanvas() provide the functionality for fetching the
     * canvas persistence instance from the database this method
     * accept persistence state canvas id
     *
     * @param canvasId accept the instance of java.lang.Long containing
     *                 the persistence state canvas id.
     * @return the instance of com.lucas.entity.ui.canvas.Canvas
     * of persistence state .
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Canvas getHydratedCanvas(Long canvasId) throws Exception {
        final Canvas canvas = this.getCanvasById(canvasId);
        
        final List<DefaultWidgetInstance> defaultWidgetInstances = canvas.getWidgetInstanceList();
        if (defaultWidgetInstances != null && !defaultWidgetInstances.isEmpty()) {
            final List<DefaultWidgetInstance> widgetInstances = new ArrayList<DefaultWidgetInstance>(defaultWidgetInstances.size());
            for (DefaultWidgetInstance defaultWidgetInstance : defaultWidgetInstances) {
                final AbstractLicensableWidget abstractLicensableWidget = this.getWidgetDefinition(defaultWidgetInstance.getWidget().getName());
                defaultWidgetInstance.setWidget(abstractLicensableWidget);
                widgetInstances.add(defaultWidgetInstance);
            }
            //sorting based on the position for widgets instance list
            canvas.setWidgetInstanceList(this.sortWidgetInstanceByPosition(widgetInstances));
        }
        return canvas;
    }

    /**
     * sortWidgetInstanceByPosition() provide the functionality for sorting the widget instance list based on the
     * position of the widget instance on the canvas.
     *
     * @param defaultWidgetInstances accept the java.util.List instance containing the DefaultWidgetInstance
     * @return return the sorted java.util.List instance containing the DefaultWidgetInstance based on the position
     */
    private List<DefaultWidgetInstance> sortWidgetInstanceByPosition(final List<DefaultWidgetInstance> defaultWidgetInstances) {
        Collections.sort(defaultWidgetInstances, new WidgetInstanceComparator());
        return defaultWidgetInstances;
    }


    /**
     * deleteCanvasById() provide the implementation for
     * deletion the canvas of persistence state
     * from the database using the com.lucas.dao.ui.CanvasDAO.
     *
     * @param canvasId it accept the java.lang.Long
     *                 instance containing the canvasId or canvasName
     *                 for the basics for deletion form database and forwards
     *                 the formal argument to the om.lucas.dao.ui.CanvasDAO.deleteCanvas()
     * @return the instance of java.lang.Boolean containing the
     * true is the operation is successful or false if the
     * operation is unsuccessful.
     */
    @Override
    @Transactional(readOnly = false)
    public Boolean deleteCanvasById(Long canvasId) {
        return this.canvasDAO.deleteCanvas(canvasId);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.lucas.services.ui.UIService#getCanvasesByCategories()
     */
    @Transactional(readOnly = true)
    @Override
    public LinkedHashMap<String, List<Canvas>> getCanvasesByCategories()
            throws JsonParseException, JsonMappingException, IOException, Exception {

        LinkedHashMap<String, List<Canvas>> canvasesByCategoriesMap = new LinkedHashMap<String, List<Canvas>>();
        List<Canvas> requiredCanvases = new ArrayList<Canvas>();

        // Retrieve all the private canvases by username
        List<Canvas> savedCanvases = canvasDAO
                .getUserPrivateCanvases(securityService.getLoggedInUsername());

        requiredCanvases.addAll(savedCanvases);
        // Retrieve all the company canvases
        List<Canvas> companyCanvases = canvasDAO.getCanvasesByType(CanvasType.COMPANY);
        requiredCanvases.addAll(companyCanvases);
        // Retrieve all the Jennifer canvases
        List<Canvas> lucasCanvases = canvasDAO.getCanvasesByType(CanvasType.LUCAS);
        requiredCanvases.addAll(lucasCanvases);

        LOG.debug("::getCanvasesByCategories::", requiredCanvases);
        if (requiredCanvases != null && !requiredCanvases.isEmpty()) {
            for (Canvas canvas : requiredCanvases) {
                String requiredCanvasCategoryName = canvas.getCanvasType().getName();
                securityService.buildCanvasActionConfig(canvas);
                if (canvas.getActionConfig() != null
                        && (canvas.getActionConfig().getActionConfig().get(new Permission(VIEW_CANVAS_ACTION)) == Boolean.TRUE || canvas
                        .getCanvasType().equals(CanvasType.PRIVATE))) {
                    List<Canvas> canvasList = canvasesByCategoriesMap.get(requiredCanvasCategoryName);
                    if (canvasList == null) {
                        canvasList = new ArrayList<Canvas>();
                    }
                    canvasList.add(canvas);
                    canvasesByCategoriesMap.put(requiredCanvasCategoryName, canvasList);
                }

            }
        }

        return canvasesByCategoriesMap;
    }

    /**
     * deleteWidgetFromCanvas() provide the specification for
     * deletion the widget from the canvas of persistence state
     *
     * @param canvasId             it accept the com.lucas.entity.ui.canvas.Canvas
     *                             instance containing the canvasId
     *                             for the basics for deletion form database.
     * @param widgetInstanceListId it accept the widgetInstanceListId
     *                             for the basics for deletion form database.
     * @return the instance of java.lang.Boolean containing the
     * true is the operation is successful or false if the
     * operation is unsuccessful.
     */
    @Transactional(readOnly = false)
    @Override
    public Boolean deleteWidgetFromCanvas(Long canvasId, Long widgetInstanceListId) {
        final List<DefaultWidgetInstance> widgetInstances = new ArrayList<>();
        boolean result = false;
        //if canvasId is Null
        if (canvasId != null) {
            try {
                final Canvas canvas = this.canvasDAO.findByCanvasId(canvasId);
                final List<DefaultWidgetInstance> widgetInstancesList = canvas.getWidgetInstanceList();
                //iterating on the list of widgetInstance of the canvas
                for (DefaultWidgetInstance defaultWidgetInstance : widgetInstancesList) {
                    //checking the id to delete the widgetInstance from canvas
                    if (defaultWidgetInstance.getWidgetinstanceId() != widgetInstanceListId) {
                        widgetInstances.add(defaultWidgetInstance);
                    }
                }
                //if the size of the widgets fetch from db and if doesn't match it mean
                //widget didn't find for deletion and size will same so needn't to update in db also
                if (widgetInstancesList.size() != widgetInstances.size()) {
                    canvas.setWidgetInstanceList(widgetInstances);
                    //persisting into the db
                    this.canvasDAO.save(canvas);
                    result = Boolean.TRUE;
                } else {
                    // setting response for not finding the widget for deletion
                    result = Boolean.FALSE;
                }
            } catch (Exception e) {
                result = Boolean.FALSE;
            }
        } else {
            //if canvasId is Null
            result = Boolean.FALSE;
        }
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public List<OpenUserCanvas> getAllOpenCanvasForUser(Long userId) {
        final List<OpenUserCanvas> openUserCanvases = this.userDAO.getOpenCanvasesForUserId(userId);
        Collections.sort(openUserCanvases, new OpenUserCanvasComparator());
        return openUserCanvases;
    }
    
    /**
     * getEquipmentManagerGridWidgetDefinition() provides specification
     * to get EquipmentManagerGridWidget from the database
     * 
     * @return the instance of EquipmentManagerGridWidget
     * 
     */
    @Transactional(readOnly = true)
    public EquipmentManagerGridWidget getEquipmentManagerGridWidgetDefinition() throws Exception {
        final String[] broadcastListArr = {"equipmentManagerName"};
        final String[] reactToListArr = {"equipmentManagerName"};
        final String url = "equipments/equipment-manager-list/search";
        final int pageNumber = 0;
        final int pageSize = 10;
        final List<String> equipmentManagerSearchMapValueList = new ArrayList<String>();
        equipmentManagerSearchMapValueList.add("$equipmentManagerName");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("equipmentManagerName", equipmentManagerSearchMapValueList);
        SearchAndSortCriteria equipmentManagerSearchAndSortCriteria = new SearchAndSortCriteria();
        equipmentManagerSearchAndSortCriteria.setPageNumber(pageNumber);
        equipmentManagerSearchAndSortCriteria.setPageSize(pageSize);
        equipmentManagerSearchAndSortCriteria.setSearchMap(searchMap);
        final DataUrl equipmentManagerDataUrl = new DataUrl(url, equipmentManagerSearchAndSortCriteria);

        final ReactToMap reactToMap = new ReactToMap(null, null, null, null, null,equipmentManagerDataUrl);
        final EquipmentManagerGridWidget equipmentManagerGridWidget = (EquipmentManagerGridWidget) this.widgetDAO.getWidgetByName(EquipmentManagerGridWidget);
        equipmentManagerGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        equipmentManagerGridWidget.setReactToList(Arrays.asList(reactToListArr));

        //Build the widget action config
        securityService.buildWidgetActionConfig(equipmentManagerGridWidget);
        return equipmentManagerGridWidget;
    }
    
    /**
     * getMessageGridWidgetDefinition() provides specification
     * to get MessageGridWidget from the database
     * 
     * @return the instance of MessageGridWidget
     * 
     */
    @Transactional(readOnly = true)
    public MessageGridWidget getMessageGridWidgetDefinition() throws Exception {
        final String[] broadcastListArr = {"messageName"};
        final String[] reactToListArr = {"messageName"};
        final String url = "/messages/messagelist/search";
        final int pageNumber = 0;
        final int pageSize = 10;
        final List<String> messageSearchMapValueList = new ArrayList<String>();
        messageSearchMapValueList.add("$messageName");
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("messageName", messageSearchMapValueList);
        SearchAndSortCriteria messageSearchAndSortCriteria = new SearchAndSortCriteria();
        messageSearchAndSortCriteria.setPageNumber(pageNumber);
        messageSearchAndSortCriteria.setPageSize(pageSize);
        messageSearchAndSortCriteria.setSearchMap(searchMap);
        final DataUrl messageDataUrl = new DataUrl(url, messageSearchAndSortCriteria);

        final ReactToMap reactToMap = new ReactToMap(null, null, null, null, messageDataUrl);
        final MessageGridWidget messageGridWidget = (MessageGridWidget) this.widgetDAO.getWidgetByName(MessageGridWidget);
        messageGridWidget.setBroadcastList(Arrays.asList(broadcastListArr));
        messageGridWidget.setReactToList(Arrays.asList(reactToListArr));

        //Build the widget action config
        securityService.buildWidgetActionConfig(messageGridWidget);
        return messageGridWidget;
    }

    /** 
     * The method creates a event grid widget and returns it instance
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public EventGridWidget getEventGridWidgetDefinition() throws Exception {
        
        String url = "/events/eventlist/search";
        int pageNumber = 0;
        int pageSize = 10;
        List<String> eventNameSearchMapValueList = new ArrayList<String>();
        eventNameSearchMapValueList.add("$eventName");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("eventName", eventNameSearchMapValueList);
        SearchAndSortCriteria eventNameSearchAndSortCriteria = new SearchAndSortCriteria();
        eventNameSearchAndSortCriteria.setPageNumber(pageNumber);
        eventNameSearchAndSortCriteria.setPageSize(pageSize);
        eventNameSearchAndSortCriteria.setSearchMap(searchMap);
        DataUrl userNameDataUrl = new DataUrl(url, eventNameSearchAndSortCriteria);

        EventGridWidget eventGridWidget = (EventGridWidget) widgetDAO.getWidgetByName(EventGridWidgetName);

        //Build the widget action config
        securityService.buildWidgetActionConfig(eventGridWidget);

        return eventGridWidget;
    }
}
