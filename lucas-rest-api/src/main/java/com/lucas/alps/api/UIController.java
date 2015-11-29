/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

import javax.inject.Inject;

import com.lucas.alps.view.*;
import com.lucas.alps.viewtype.*;
import com.lucas.alps.viewtype.widget.*;
import com.lucas.entity.ui.widget.*;
import com.lucas.exception.Level;
import com.lucas.services.user.UserService;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.viewtype.widget.GroupMaintenanceWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageSegmentGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.PicklineByWaveBarChartWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchGroupGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchProductGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.UserCreationFormWidgetDetailsView;
import com.lucas.dto.ui.SaveCanvasDTO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.ui.widgetinstance.SaveWidgetInstanceResponseComparator;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.ui.UIService;
import com.lucas.services.util.CollectionsUtilService;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class UIController {

    private final String EQUIPMENT_TYPE_GRID_WIDGET_NAME = "equipment-type-grid-widget";
    private final String SEARCH_USER_GRID_WIDGET_NAME = "search-user-grid-widget";
    private final String SEARCH_PRODUCT_GRID_WIDGET_NAME = "search-product-grid-widget";
    private final String GROUP_MAINTENANCE_WIDGET = "group-maintenance-widget";
    private final String PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME = "pickline-by-wave-barchart-widget";
    private final String USER_CREATE_OR_EDIT_WIDGET_NAME = "create-or-edit-user-form-widget";
    private final String SEARCH_GROUP_GRID_WIDGET_NAME = "search-group-grid-widget";
    private final String MESSAGE_SEGMENT_GRID_WIDGET = "message-segment-grid-widget";
    private final String EQUIPMENT_MANAGER_GRID_WIDGET = "equipment-manager-grid-widget";
    private final String MESSAGE_GRID_WIDGET = "message-grid-widget";
    private static final Logger LOG = LoggerFactory.getLogger(UIController.class);
    private static final String CANVAS_ACTION_UPDATE = "update";
    private static final String CANVAS_ACTION_CREATE = "create";
    private static final String EVENT_GRID_WIDGET_NAME = "event-grid-widget";
    private final String MESSAGE_MAPPINGS_GRID_WIDGET = "message-mappings-grid-widget";
    
    private final String EVENT_HANDLER_GRID_WIDGET = "event-handler-grid-widget";
    
    private final UIService uiService;
    private final UserService userService;

    @Inject
    public UIController(UIService uiService, UserService userService) {
        this.uiService = uiService;
        this.userService = userService;
    }

    /**
     * JSON:
     * 
     * { "status": "success", "code": "200", "message": "Request processed successfully", "level":
     * null, "uniqueKey": null, "token": null, "explicitDismissal": null, "data": {
     * "Work Execution": [ { "id": 14, "name": "search-product-grid-widget", "shortName":
     * "SearchProduct", "type": "GRID", "description": "GRID-SearchProductGridWidget", "title":
     * "Search Product" } ], "Administration": [ { "id": 8, "name":
     * "create-or-edit-user-form-widget", "shortName": "CreateUser", "type": "FORM", "description":
     * "GRID-UserCreationFormWidget", "title": "Create Or Edit User" }, { "id": 9, "name":
     * "search-user-grid-widget", "shortName": "SearchUser", "type": "GRID", "description":
     * "GRID-SearchUserGridWidget", "title": "Search User" }, { "id": 12, "name":
     * "group-maintenance-widget", "shortName": "GroupMaintenance", "type": "FORM", "description":
     * "FORM-GroupMaintenanceWidget", "title": "Group Maintenance" } ], "Reporting": [ { "id": 10,
     * "name": "pickline-by-wave-barchart-widget", "shortName": "PicklineByWave", "type":
     * "GRAPH_2D", "description": "PicklineByWaveBarChartWidget", "title":
     * "Pickline By Wave Bar chart" } ] } }
     * 
     * // refer PHX-853
     * 
     * @throws Exception
     */

    @RequestMapping(value = "/widgetdefinitions", method = RequestMethod.GET)
    @ResponseView(WidgetPalleteView.class)
    public @ResponseBody ApiResponse<MultiMap> getWidgetDefinitionList() throws Exception {
        ApiResponse<MultiMap> apiResponse = new ApiResponse<MultiMap>();

        MultiMap widgetDefinitionListByCategories = new MultiValueMap();

        MultiMap widgetDefinitionsByCategoryMap = uiService.getWidgetDefinitionsByCategory();

        Set<Map.Entry<String, List<AbstractLicensableWidget>>> widgetsByCategories = widgetDefinitionsByCategoryMap.entrySet();

        for (Entry<String, List<AbstractLicensableWidget>> entry : CollectionsUtilService.nullGuard(widgetsByCategories)) {
            String widgetsCategory = entry.getKey();
            List<AbstractLicensableWidget> widgetsList = entry.getValue();
            for (AbstractLicensableWidget widget : CollectionsUtilService.nullGuard(widgetsList)) {

                widgetDefinitionListByCategories.put(widgetsCategory, new AbstractLicensableWidgetView(widget));
            }
        }

        apiResponse.setData(widgetDefinitionListByCategories);

        return apiResponse;
    }



    /**
     * method returns the following json { "status": "success", "code": "200", "message":
     * "Request processed successfully", "level": null, "uniqueKey": null, "token": null,
     * "explicitDismissal": null, "data": { "id": 8, "name": "create-or-edit-user-form-widget",
     * "shortName": "CreateUser", "widgetActionConfig": { "create-assignment": false,
     * "view-report-productivity": false, "configure-location": false, "delete-canvas": false,
     * "create-canvas": false }, "definitionData": { "User": [ { "startDate":
     * "2014-10-28T19:46:37.531Z" } ], "handheldScreenLanguageList": [ "ENGLISH", "FRENCH", "GERMAN"
     * ], "amdScreenLanguageList": [ "ENGLISH", "FRENCH", "GERMAN" ], "jenniferToUserLanguageList":
     * [ "ENGLISH", "FRENCH", "GERMAN" ], "userToJenniferLanguageList": [ "ENGLISH", "FRENCH",
     * "GERMAN" ] }, "broadcastList": [ "userName" ], "reactToMap": { "userName": { "url": "/users",
     * "searchCriteria": { "pageNumber": "0", "pageSize": "10", "searchMap": { "userName": [
     * "$userName" ] } } } }, "defaultViewConfig": { "height": 240, "width": 567, "dateFormat": {
     * "selectedFormat": "mm-dd-yyyy", "availableFormats": [ "mm-dd-yyyy", "MMM dd, yyyy",
     * "dd-mm-yyyy" ] }, "anchor": [ 275, 295 ], "zindex": 1, "isMaximized": false,
     * "listensForList": [ "userName" ] } } }
     */
    @ResponseView(UserCreationFormWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + USER_CREATE_OR_EDIT_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<UserCreationFormWidgetView> getUserCreationFormWidgetDefinition() {

        ApiResponse<UserCreationFormWidgetView> apiResponse = new ApiResponse<UserCreationFormWidgetView>();
        UserCreationFormWidget userCreationWidget = null;

        try {
            userCreationWidget = (UserCreationFormWidget) uiService.getWidgetDefinition(USER_CREATE_OR_EDIT_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        UserCreationFormWidgetView userCreationWidgetView = new UserCreationFormWidgetView(userCreationWidget);
        apiResponse.setData(userCreationWidgetView);
        return apiResponse;
    }

    /**
     *
     * @return { "status": "success", "code": "200", "message": "Request processed successfully",
     *         "level": null, "uniqueKey": null, "token": null, "explicitDismissal": null, "data": {
     *         "name": "search-user-grid-widget", "id": 9, "shortName": "SearchUser",
     *         "broadcastList": [ "userName", "hostLogin" ], "defaultData": {}, "title":
     *         "Search User", "reactToMap": { "userName": { "url": "/users/userlist/search",
     *         "searchCriteria": { "pageNumber": "0", "pageSize": "10", "searchMap": { "hostLogin":
     *         [ "$hostLogin" ], "userName": [ "$userName" ] } } }, "hostLogin": { "url":
     *         "/users/userlist/search", "searchCriteria": { "pageNumber": "0", "pageSize": "10",
     *         "searchMap": { "hostLogin": [ "$hostLogin" ], "userName": [ "$userName" ] } } } },
     *         "dataURL": { "url": "/users/userlist/search", "searchCriteria": { "pageNumber": "0",
     *         "pageSize": "10", "searchMap": {}, "sortMap": {} } }, "widgetActionConfig": {
     *         "view-users-searchusergrid-widget": true, "delete-user": true, "disable-user": true
     *         }, "defaultViewConfig": { "dateFormat": { "selectedFormat": "mm-dd-yyyy",
     *         "availableFormats": [ "mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy" ] }, "width": 485,
     *         "height": 375, "anchor": [ 1, 363 ], "zindex": 1, "gridColumns": { "1": { "name":
     *         "Birth Date", "fieldName": "birthDate", "visible": false, "order": "1", "sortOrder":
     *         "1" }, "2": { "name": "Email Address", "fieldName": "emailAddress", "visible": true,
     *         "order": "2", "sortOrder": "1" }, "3": { "name": "Employee Id", "fieldName":
     *         "employeeId", "visible": false, "order": "3", "sortOrder": "1" }, "4": { "name":
     *         "First Name", "fieldName": "firstName", "visible": true, "order": "4", "sortOrder":
     *         "1" }, "5": { "name": "Last Name", "fieldName": "lastName", "visible": true, "order":
     *         "6", "sortOrder": "1" }, "6": { "name": "Mobile Number", "fieldName": "mobileNumber",
     *         "visible": false, "order": "7", "sortOrder": "1" }, "7": { "name":
     *         "Needs Authentication", "fieldName": "needsAuthentication", "visible": false,
     *         "order": "8", "sortOrder": "1" }, "8": { "name": "Preferred Language", "fieldName":
     *         "preferredLanguage", "visible": false, "order": "9", "sortOrder": "1" }, "9": {
     *         "name": "Start Date", "fieldName": "startDate", "visible": true, "order": "10",
     *         "sortOrder": "1" }, "10": { "name": "Title", "fieldName": "title", "visible": false,
     *         "order": "10", "sortOrder": "1" }, "11": { "name": "User Id", "fieldName": "userId",
     *         "visible": false, "order": "11", "sortOrder": "1" }, "12": { "name": "User Name",
     *         "fieldName": "userName", "visible": true, "order": "13", "sortOrder": "1" }, "13": {
     *         "name": "J2uLanguage", "fieldName": "j2uLanguage", "visible": true, "order": "13",
     *         "sortOrder": "1" }, "14": { "name": "U2jLanguage", "fieldName": "u2jLanguage",
     *         "visible": true, "order": "14", "sortOrder": "1" }, "15": { "name": "HhLanguage",
     *         "fieldName": "hhLanguage", "visible": true, "order": "15", "sortOrder": "1" }, "16":
     *         { "name": "AmdLanguage", "fieldName": "amdLanguage", "visible": true, "order": "16",
     *         "sortOrder": "1" } }, "listensForList": [ "userName" ] } } }
     */

    @ResponseView(SearchUserGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + SEARCH_USER_GRID_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<SearchUserGridWidgetView> getSearchUserGridWidgetDefinition() {

        ApiResponse<SearchUserGridWidgetView> apiResponse = new ApiResponse<SearchUserGridWidgetView>();
        SearchUserGridWidget searchUserGridWidget;

        try {
            searchUserGridWidget = (SearchUserGridWidget) uiService.getWidgetDefinition(SEARCH_USER_GRID_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        SearchUserGridWidgetView searchUserGridWidgetView = new SearchUserGridWidgetView(searchUserGridWidget);
        apiResponse.setData(searchUserGridWidgetView);
        return apiResponse;
    }

    /*
     * endpoint : http://localhost:8080/lucas-api/widgets/search-product-grid-widget/definition json
     * returned:
     * 
     * { "status": "success", "code": "200", "message": "Request processed successfully", "level":
     * null, "uniqueKey": null, "token": null, "explicitDismissal": null, "data": { "name":
     * "search-product-grid-widget", "id": 13, "dataURL": { "url": "/products/productlist/search",
     * "searchCriteria": { "pageNumber": "0", "pageSize": "10", "searchMap": {}, "sortMap": {} } },
     * "broadcastList": [ "productName", "hostLogin" ], "shortName": "SearchProduct", "title":
     * "Search Product", "reactToMap": { "userName": { "url": "/products/productlist/search" } },
     * "defaultViewConfig": { "anchor": [ 1, 363 ], "height": 375, "width": 485, "zindex": 1,
     * "gridColumns": { "1": { "name": "Product Number", "visible": false, "order": "1",
     * "fieldName": "productNumber", "sortOrder": "1" }, "2": { "name": "Description", "visible":
     * true, "order": "2", "fieldName": "description", "sortOrder": "1" }, "3": { "name": "Status",
     * "visible": true, "order": "3", "fieldName": "status", "sortOrder": "1" }, "4": { "name":
     * "Name", "visible": true, "order": "4", "fieldName": "name", "sortOrder": "1" }, "5": {
     * "name": "Marked Out", "visible": true, "order": "5", "fieldName": "markedOut", "sortOrder":
     * "1" }, "6": { "name": "Base Item", "visible": true, "order": "6", "fieldName": "baseItem",
     * "sortOrder": "1" }, "7": { "name": "Base Item Threshold", "visible": true, "order": "7",
     * "fieldName": "baseItemThreshold", "sortOrder": "1" }, "8": { "name": "UPC Check", "visible":
     * true, "order": "8", "fieldName": "upcCheck", "sortOrder": "1" }, "9": { "name":
     * "UPC Check Every X qty", "visible": true, "order": "9", "fieldName": "upcCheckEveryXqty",
     * "sortOrder": "1" }, "10": { "name": "Capture Lot Number", "visible": true, "order": "10",
     * "fieldName": "captureLotNumber", "sortOrder": "1" }, "11": { "name": "Capture Serial Number",
     * "visible": true, "order": "11", "fieldName": "captureSerialNumber", "sortOrder": "1" }, "12":
     * { "name": "Heads Up Messages", "visible": true, "order": "12", "fieldName":
     * "headsUpMessages", "sortOrder": "1" } }, "listensForList": [ "productName" ] },
     * "widgetActionConfig": { "edit-product": true, "create-product": true, "delete-product": true,
     * "view-product": true }, "defaultData": {} } } refer PHX-852
     */
    @ResponseView(SearchProductGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + SEARCH_PRODUCT_GRID_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<SearchProductGridWidgetView> getSearchProductGridWidgetDefinition() {

        ApiResponse<SearchProductGridWidgetView> apiResponse = new ApiResponse<SearchProductGridWidgetView>();
        SearchProductGridWidget searchProductGridWidget;

        try {
            searchProductGridWidget = (SearchProductGridWidget) uiService.getWidgetDefinition(SEARCH_PRODUCT_GRID_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        SearchProductGridWidgetView searchUserGridWidgetView = new SearchProductGridWidgetView(searchProductGridWidget);
        apiResponse.setData(searchUserGridWidgetView);
        return apiResponse;
    }

    /**
     * getEquipmentTypeGridWidgetDefinition() provide the functionality for getting the
     * EquipmentTypeGridWidget from database and wrap it into the EquipmentTypeGridWidgetView which
     * intern use Jaxson api to convert the marked properties by
     * 
     * @JsonView(EquipmentTypeGridWidgetDetailsView.clss) into the actual jason this method intern
     *                                                    use UIService.getWidgetDefinition() method
     *                                                    to fetch the
     *                                                    EquipmentTypeGridWidgetDefinition from db
     *                                                    using the widget name as the argument.
     *
     * @return the ApiResponse object contains EquipmentTypeGridWidgetView instance holding
     *         EquipmentTypeGridWidgetDefinition data internally.
     */
    @ResponseView(EquipmentTypeGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + EQUIPMENT_TYPE_GRID_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<EquipmentTypeGridWidgetView> getEquipmentTypeGridWidgetDefinition() {
        final ApiResponse<EquipmentTypeGridWidgetView> apiResponse = new ApiResponse<EquipmentTypeGridWidgetView>();
        EquipmentTypeGridWidget equipmentTypeGridWidget;
        try {
            equipmentTypeGridWidget = (EquipmentTypeGridWidget) uiService.getWidgetDefinition(EQUIPMENT_TYPE_GRID_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
        final EquipmentTypeGridWidgetView equipmentTypeGridWidgetView = new EquipmentTypeGridWidgetView(equipmentTypeGridWidget);
        apiResponse.setData(equipmentTypeGridWidgetView);
        return apiResponse;
    }


    /**
     *
     * @return json { "status": "success", "code": "200", "message":
     *         "Request processed successfully", "uniqueKey": null, "token": null, "reason": null,
     *         "data": { "name": "pickline-by-wave-barchart-widget", "id": 10, "shortName":
     *         "PicklineByWave", "widgetActionConfig": {}, "defaultData": {}, "title":
     *         "Pickline By Wave Bar chart", "widgetViewConfig": { "height": 100, "width": 120,
     *         "dateFormat": { "selectedFormat": "mm-dd-yyyy", "availableFormats": [ "mm-dd-yyyy",
     *         "MMM dd, yyyy", "dd-mm-yyyy" ] }, "anchor": [ 1, 2 ], "zindex": 1, "orientation": {
     *         "option": [ { "legend": "horizontal", "value": "h" }, { "legend": "vertical",
     *         "value": "v" } ], "selected": "h" } }, "listensForList": [], "broadCastMap": {
     *         "waveId": "waveId" }, "dataURL": {} } }
     */
    @ResponseView(PicklineByWaveBarChartWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<PicklineByWaveBarChartWidgetView> getPicklineByWaveBarChartWidgetDefinition() {
        ApiResponse<PicklineByWaveBarChartWidgetView> apiResponse = new ApiResponse<PicklineByWaveBarChartWidgetView>();
        PicklineByWaveBarChartWidget picklineByWaveBarChartWidget;

        try {
            picklineByWaveBarChartWidget = (PicklineByWaveBarChartWidget) uiService.getWidgetDefinition(PICKLINE_BY_WAVE_BAR_CHART_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        PicklineByWaveBarChartWidgetView picklineByWaveBarChartWidgetView = new PicklineByWaveBarChartWidgetView(picklineByWaveBarChartWidget);
        apiResponse.setData(picklineByWaveBarChartWidgetView);
        return apiResponse;
    }

    /**
     *
     * @param widgetInstanceView { "actualViewConfig": { "anchor": [ 2, 3 ], "height": 2, "width":
     *        2, "listensFor": [ "Area", "Shift", "Picker" ], "zindex": 2 }, "widgetDefinition": {
     *        "name": "Picking Widget", "id": 1 }, "id": 1 or null (update or create), "canvas": {
     *        "canvasName": "Perishable Goods Canvas", "canvasId": 1 } }
     * @throws CloneNotSupportedException
     *
     * @Response resp { "status": "success", "code": "200", "message":
     *           "Request processed successfully", "uniqueKey": null, "token": null, "reason": null,
     *           "data": { "id": 9 } }
     */
    @ResponseView(CreateOrUpdateWidgetInstanceView.class)
    @RequestMapping(value = "/widgetinstances", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Map<String, String>> createOrUpdateWidgetInstance(@RequestBody DefaultWidgetInstanceView widgetInstanceView) throws CloneNotSupportedException {
        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<Map<String, String>>();

        DefaultWidgetInstance defaultWidgetInstance = widgetInstanceView.getDefaultWidgetInstance();
        Long widgetInstanceId = defaultWidgetInstance.getWidgetinstanceId();
        try {

            Map<String, String> responseMap = uiService.createOrUpdateWidgetInstance(defaultWidgetInstance);
            if (widgetInstanceId == null) {
                widgetInstanceId = uiService.getRecentlyInsertedWidgetInstanceId(defaultWidgetInstance.getCanvas().getName());
            }

            if (widgetInstanceId != null) {
                responseMap.put("widgetInstanceId", widgetInstanceId.toString());
            }
            widgetInstanceView.setWidgetInstanceId(widgetInstanceId);
            apiResponse.setData(responseMap);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;
    }

    /*
     * url =
     * http://[HostName]:[PortNumber]/[Context/lucas-api]/widgets/group-maintenance-widget/definition
     * sample JSON expected as input to this method { "status":"success", "code":"200",
     * "message":"Request processed successfully", "level":null, "uniqueKey":null, "token":null,
     * "explicitDismissal":null, "data":{ "name":"group-maintenance-widget", "id":12, "dataURL":{
     * "url":"/groups/permissions", "searchCriteria":{ "pageNumber":"0", "pageSize":"2147483647",
     * "searchMap":{ }, "sortMap":{ "permissionGroupName":"ASC" } } }, "broadcastList":[ "groupName"
     * ], "shortName":"GroupAssignment", "title":"Group Assignment", "reactToMap":{ "groupName":{
     * "url":"/groups/permissions", "searchCriteria":{ "pageNumber":"0", "pageSize":"2147483647",
     * "searchMap":{ "permissionGroupName":[ "$groupName" ] }, "sortMap":{
     * "permissionGroupName":"ASC" } } } }, "defaultViewConfig":{ "anchor":[ 2, 2 ], "height":500,
     * "width":1100, "zindex":1, "listensForList":[ "groupName" ] }, "widgetActionConfig":{
     * "group-assignment-edit":true, "group-assignment-create":true }, "defaultData":{ } } }
     */

    @ResponseView(GroupMaintenanceWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + GROUP_MAINTENANCE_WIDGET + "/definition", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<GroupMaintenanceWidgetView> getWidgetDefinitions() {

        final ApiResponse<GroupMaintenanceWidgetView> apiResponse = new ApiResponse<GroupMaintenanceWidgetView>();
        GroupMaintenanceWidget groupMaintenanceWidget;

        try {
            groupMaintenanceWidget = this.uiService.getGroupMaintenanceWidgetDefinition();
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
        final GroupMaintenanceWidgetView groupMaintenanceWidgetView = new GroupMaintenanceWidgetView(groupMaintenanceWidget);
        apiResponse.setData(groupMaintenanceWidgetView);
        return apiResponse;
    }



    /**
     * method returns the following json { "status": "success", "code": "200", "message":
     * "Request processed successfully", "level": null, "uniqueKey": null, "token": null,
     * "explicitDismissal": null, "data": { "name": "message-segment-grid-widget", "id": 17,
     * "defaultViewConfig": { "minimumWidth": 320, "minimumHeight": 300, "gridColumns": { "1": {
     * "name": "Segment Name", "fieldName": "segmentName", "allowFilter": true, "visible": true,
     * "order": "1", "width": 150, "sortOrder": "1" }, "2": { "name": "Segment Description",
     * "fieldName": "segmentDiscription", "allowFilter": true, "visible": true, "order": "2",
     * "width": 150, "sortOrder": "1" }, "3": { "name": "Length", "fieldName": "length",
     * "allowFilter": true, "visible": true, "order": "3", "width": 150, "sortOrder": "1" } },
     * "listensForList": [ "userName" ], "deviceWidths": { "320": "mobile", "600": "tablet", "800":
     * "desktop", "1200": "wideScreen" }, "autoRefreshConfig": { "interval": 120, "enabled": true,
     * "globalOverride": false }, "dateFormat": { "selectedFormat": "mm-dd-yyyy",
     * "availableFormats": [ "mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy" ] }, "anchor": [ 1, 363 ],
     * "zindex": 1 },
     * 
     * "shortName": "MessageSegment", "widgetActionConfig": { "widget-access": {
     * "message-segments-widget-access": true }, "widget-actions": { "create-message-segment": true,
     * "edit-message-segment": true, "delete-message-segment": true
     * 
     * } }, "title": "Message Segments", "dataURL": { "searchCriteria": { "pageNumber": "0",
     * "pageSize": "100", "searchMap": {}, "sortMap": {} }, "url": "/segments/segmentlist/search" }
     * } }
     */
    @ResponseView(MessageSegmentGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + MESSAGE_SEGMENT_GRID_WIDGET + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<MessageSegmentGridWidgetView> getMessageSegmentGridWidgetDefinition() {

        ApiResponse<MessageSegmentGridWidgetView> apiResponse = new ApiResponse<MessageSegmentGridWidgetView>();
        MessageSegmentGridWidget messageSegmentWidget = null;

        try {
            messageSegmentWidget = (MessageSegmentGridWidget) uiService.getWidgetDefinition(MESSAGE_SEGMENT_GRID_WIDGET);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        MessageSegmentGridWidgetView eaiMessageSegmentGridWidgetView = new MessageSegmentGridWidgetView(messageSegmentWidget);
        apiResponse.setData(eaiMessageSegmentGridWidgetView);
        return apiResponse;
    }
    
    
    
    @ResponseView(MessageMappingGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + MESSAGE_MAPPINGS_GRID_WIDGET + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<MessageMappingGridWidgetView> getMessageMappingGridWidgetDefinition() {

        ApiResponse<MessageMappingGridWidgetView> apiResponse = new ApiResponse<MessageMappingGridWidgetView>();
        MessageMappingGridWidget messageMappingWidget = null;

        try {
        	messageMappingWidget = (MessageMappingGridWidget) uiService.getWidgetDefinition(MESSAGE_MAPPINGS_GRID_WIDGET);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        MessageMappingGridWidgetView messageMappingGridWidgetView = new MessageMappingGridWidgetView(messageMappingWidget);
        apiResponse.setData(messageMappingGridWidgetView);
        return apiResponse;
    }

    
    @ResponseView(EventHandlerGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" +EVENT_HANDLER_GRID_WIDGET+ "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<EventHandlerGridWidgetView> getEventHandlerGridWidgetDefinition() {

        ApiResponse<EventHandlerGridWidgetView> apiResponse = new ApiResponse<EventHandlerGridWidgetView>();
        EventHandlerGridWidget eventHandlerWidget = null;

        try {
        	eventHandlerWidget = (EventHandlerGridWidget) uiService.getWidgetDefinition(EVENT_HANDLER_GRID_WIDGET);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        EventHandlerGridWidgetView eventHandlerGridWidgetView = new EventHandlerGridWidgetView(eventHandlerWidget);
        apiResponse.setData(eventHandlerGridWidgetView);
        return apiResponse;
    }

    
    /**
     * The method is used to create or update a canvas.
     * 
     * @param canvasView
     * @return
     * @throws Exception
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IOException
     */
    @ResponseView(SaveCanvasDetailsView.class)
    @RequestMapping(value = "/canvases/save", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<SaveCanvasView> saveCanvas(@RequestBody CanvasView canvasView) throws Exception {

        String canvasAction = CANVAS_ACTION_UPDATE;
        ApiResponse<SaveCanvasView> apiResponse = new ApiResponse<SaveCanvasView>();
        Long canvasId = canvasView.getCanvasId();
        List<DefaultWidgetInstanceView> defWIList = canvasView.getWidgetInstanceList();
        canvasView.getCanvas().setWidgetInstanceList(null);
        if (canvasId == null) {

            canvasAction = CANVAS_ACTION_CREATE;
        }
        canvasId = uiService.saveCanvas(canvasView.getCanvas());
        DefaultWidgetInstanceView[] defWIArr = new DefaultWidgetInstanceView[defWIList.size()];
        for (int i = 0; i < defWIArr.length; i++) {
            defWIArr[i] = defWIList.get(i);
            defWIArr[i].setCanvas(canvasView);
            defWIArr[i].getCanvas().setCanvasId(canvasId);
            defWIArr[i].getCanvas().setName(canvasView.getName());
        }
        SaveCanvasDTO saveCanvasDTO = new SaveCanvasDTO();
        ApiResponse<List<Map<String, String>>> apiResponseWiArr = createOrUpdateWidgetInstances(defWIArr);
        saveCanvasDTO.setCanvasId(canvasId);
        saveCanvasDTO.setCanvasName(canvasView.getName());
        saveCanvasDTO.setAction(canvasAction);
        saveCanvasDTO.setWidgets(apiResponseWiArr.getData());
        SaveCanvasView saveCanvasView = new SaveCanvasView(saveCanvasDTO);
        apiResponse.setData(saveCanvasView);
        return apiResponse;
    }

    /**
     * This method will update/create widget instances in bulk for canvases.
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     *
     */

    @RequestMapping(value = "/canvases/widgetinstances", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<List<Map<String, String>>> createOrUpdateWidgetInstances(@RequestBody DefaultWidgetInstanceView[] widgetInstanceViews) throws JsonParseException, JsonMappingException,
            IOException {

        final ApiResponse<List<Map<String, String>>> apiResponse = new ApiResponse<List<Map<String, String>>>();


        if (widgetInstanceViews.length > 0) {

            List<Map<String, String>> responseMaps = new ArrayList<Map<String, String>>();

            for (DefaultWidgetInstanceView widgetInstanceView : widgetInstanceViews) {

                DefaultWidgetInstance defaultWidgetInstance = widgetInstanceView.getDefaultWidgetInstance();
                Long widgetInstanceId = defaultWidgetInstance.getWidgetinstanceId();

                Map<String, String> responsMap = uiService.createOrUpdateWidgetInstance(defaultWidgetInstance);

                if (widgetInstanceId == null) {
                    widgetInstanceId = uiService.getRecentlyInsertedWidgetInstanceId(defaultWidgetInstance.getCanvas().getName());
                }
                if (widgetInstanceId != null) {
                    responsMap.put("widgetInstanceId", widgetInstanceId.toString());
                }

                Integer position = widgetInstanceView.getActualViewConfig().getPosition();

                if (position != null) {
                    responsMap.put("position", position.toString());
                }

                responseMaps.add(responsMap);
            }

            Collections.sort(responseMaps, new SaveWidgetInstanceResponseComparator());
            apiResponse.setData(responseMaps);
        }
        return apiResponse;
    }


    /**
     * @Author Adarsh kumar
     *
     *         deleteCanvasById() provide the functionality for deletion of the canvas based on the
     *         persistence state canvasId send form client. url for service is =
     *         /canvas/delete/{canvasId} it accept the http delete request.
     * @param canvasId accept the instance of the java.lang.Long containing the persistence state
     *        canvasId for deletion.
     * @return the deletion operation status in the form of java.lang.Boolean instance i.e true for
     *         successful deletion operation and false for unsuccessful deletion operation or
     *         provided canvasId Add a comment to this line null or empty.
     */
    @RequestMapping(value = "/canvas/delete/{canvasId}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> deleteCanvasById(@PathVariable Long canvasId) {
        final ApiResponse<Boolean> apiResponse = new ApiResponse<Boolean>();
        if (canvasId != null) {
            final Boolean result = this.uiService.deleteCanvasById(canvasId);
            apiResponse.setData(result);
        } else {
            apiResponse.setData(Boolean.FALSE);
        }
        return apiResponse;
    }

    /**
     * This method returns the canvases by categories(PRIVATE, COMPANY, LUCAS). If the user does not
     * have access permission on any of the widget on the canvas the canvas is not going to appear
     * in the response. If the canvas doesn't have any widget on it, it is going to appear in the
     * response.
     * 
     * @return sample json { "status": "success", "code": "200", "message":
     *         "Request processed successfully", "level": null, "uniqueKey": null, "token": null,
     *         "explicitDismissal": null, "data": { "lucas": [ { "canvasName":
     *         "Pick Monitoring Canvas", "canvasId": 5 } ], "company": [ { "canvasName":
     *         "Hazmat Canvas", "canvasId": 3 }, { "canvasName": "User Management Canvas",
     *         "canvasId": 4 } ], "private": [ { "canvasName": "Morning Shift Canvas", "canvasId": 2
     *         } ] } }
     * @throws Exception
     */
    @ResponseView(CanvasPalleteView.class)
    @RequestMapping(value = "/canvasesbycategories", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<LinkedHashMap<String, List<CanvasView>>> getCanvasesByCategories() throws Exception {

        LOG.info("Requesting canvases by categories");
        ApiResponse<LinkedHashMap<String, List<CanvasView>>> apiResponse = new ApiResponse<LinkedHashMap<String, List<CanvasView>>>();

        LinkedHashMap<String, List<CanvasView>> canvasesByCategories = new LinkedHashMap<String, List<CanvasView>>();

        LinkedHashMap<String, List<Canvas>> canvasesByCategoriesMap = uiService.getCanvasesByCategories();

        Set<Map.Entry<String, List<Canvas>>> canvasesByCategoriesSet = canvasesByCategoriesMap.entrySet();

        for (Entry<String, List<Canvas>> entry : CollectionsUtilService.nullGuard(canvasesByCategoriesSet)) {
            String canvasCategory = entry.getKey();
            List<Canvas> canvasList = entry.getValue();
            for (Canvas canvas : CollectionsUtilService.nullGuard(canvasList)) {
                List<CanvasView> canvasViewList = canvasesByCategories.get(canvasCategory);
                if (canvasViewList == null) {
                    canvasViewList = new ArrayList<CanvasView>();
                }
                canvasViewList.add(new CanvasView(canvas));
                canvasesByCategories.put(canvasCategory, canvasViewList);
            }
        }
        apiResponse.setData(canvasesByCategories);
        return apiResponse;
    }


    /**
     * @Author Adarsh kumar
     *
     *         deleteWidgetFromCanvas() provide the functionality for deletion of the widget from
     *         the canvas based on the persistence state canvasId and widgetInstanceListId send form
     *         client. url for service is = /canvas/widgetinstance/delete it accept the http post
     *         request.
     * @param requestParamMap accept the instance java.util.Map containing the of the java.lang.Long
     *        containing persistence state canvasId and widgetInstanceListId for deletion.
     * @return the deletion operation status in the form of java.lang.Boolean instance i.e true for
     *         successful deletion operation and false for unsuccessful deletion operation or
     *         provided canvasId Add a comment to this line null or empty.
     */
    @RequestMapping(value = "/canvas/widgetinstance/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ApiResponse<Boolean> deleteWidgetFromCanvas(@RequestBody Map<String, Long> requestParamMap) {
        final ApiResponse<Boolean> apiResponse = new ApiResponse<Boolean>();
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);

        if (requestParamMap != null && requestParamMap.containsKey("canvasId") && requestParamMap.containsKey("widgetInstanceListId")) {

            final Long canvasId = requestParamMap.get("canvasId");
            final Long widgetInstanceListId = requestParamMap.get("widgetInstanceListId");

            if (canvasId != null && widgetInstanceListId != null) {
                final Boolean result = this.uiService.deleteWidgetFromCanvas(canvasId, widgetInstanceListId);
                apiResponse.setData(result);
            } else {
                apiResponse.setData(Boolean.FALSE);
            }
        } else {
            apiResponse.setData(Boolean.FALSE);
        }
        return apiResponse;
    }


    /**
     * @param canvasId accept the instance of the java.lang.Long containing the persistence state
     *        canvasId for fetching operation.
     * @return the Canvas data and its widgets wrapped into the Canvas view.
     * @Author Adarsh kumar
     *         <p/>
     *         getCanvasData() provide the functionality for getting the canvas data and its widgets
     *         from the database based on the persistence state canvasId send form client. url for
     *         service is = /canvases/{canvasId} it accept the http get request.
     */
    @ResponseView(CanvasDataView.class)
    @RequestMapping(value = "/canvases/{canvasId}", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<CanvasView> getCanvasData(@PathVariable Long canvasId) throws Exception {
        final ApiResponse<CanvasView> apiResponse = new ApiResponse<CanvasView>();
        try {
            final Canvas canvas = this.uiService.getHydratedCanvas(canvasId);
            final CanvasView canvasView = new CanvasView(canvas);
            apiResponse.setData(canvasView);
        } catch (Exception e) {
            apiResponse.setMessage("Canvas Not Found With Id " + canvasId);
            apiResponse.setStatus("failure");
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        return apiResponse;
    }

    @ResponseView(SearchGroupGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + SEARCH_GROUP_GRID_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<SearchGroupGridWidgetView> getSearchGroupGridWidgetDefinition() {

        ApiResponse<SearchGroupGridWidgetView> apiResponse = new ApiResponse<SearchGroupGridWidgetView>();
        SearchGroupGridWidget searchGroupGridWidget = null;

        try {
            searchGroupGridWidget = (SearchGroupGridWidget) uiService.getWidgetDefinition(SEARCH_GROUP_GRID_WIDGET_NAME);
        } catch (Exception e) {
            apiResponse.setMessage("Widget Definition Not Found !");
            apiResponse.setStatus("failure");
        }

        SearchGroupGridWidgetView searchGroupGridWidgetView = new SearchGroupGridWidgetView(searchGroupGridWidget);
        apiResponse.setData(searchGroupGridWidgetView);
        return apiResponse;
    }

    /**
     * getEquipmentManagerGridWidgetDefinition() provide the functionality for getting the
     * EquipmentManagerGridWidget from database and wrap it into the EquipmentManagerGridWidgetView
     * which intern use Jaxson api to convert the marked properties by
     * 
     * @JsonView(EquipmentManagerGridWidgetDetailsView.class) into the actual jason this method
     *                                                        intern use
     *                                                        UIService.getWidgetDefinition() method
     *                                                        to fetch the
     *                                                        EquipmentManagerGridWidgetDefinition
     *                                                        from db using the widget name as the
     *                                                        argument.
     *
     * @return the ApiResponse object contains EquipmentManagerGridWidgetView instance holding
     *         EquipmentManagerGridWidgetDefinition data internally.
     */
    @ResponseView(EquipmentManagerGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + EQUIPMENT_MANAGER_GRID_WIDGET + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<EquipmentManagerGridWidgetView> getEquipmentManagerGridWidgetDefinition() {
        final ApiResponse<EquipmentManagerGridWidgetView> apiResponse = new ApiResponse<EquipmentManagerGridWidgetView>();
        EquipmentManagerGridWidget equipmentManagerGridWidget;
        try {
            equipmentManagerGridWidget = (EquipmentManagerGridWidget) uiService.getWidgetDefinition(EQUIPMENT_MANAGER_GRID_WIDGET);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
        final EquipmentManagerGridWidgetView equipmentManagerGridWidgetView = new EquipmentManagerGridWidgetView(equipmentManagerGridWidget);
        apiResponse.setData(equipmentManagerGridWidgetView);
        return apiResponse;
    }

    /**
     * getMessageGridWidgetDefinition() provide the functionality for getting the MessageGridWidget
     * from database and wrap it into the MessageGridWidgetView which intern use Jaxson api to
     * convert the marked properties by @JsonView(MessageGridWidgetDetailsView.class) into the
     * actual jason this method intern use UIService.getWidgetDefinition() method to fetch the
     * MessageGridWidgetDefinition from db using the widget name as the argument.
     *
     * @return the ApiResponse object contains MessageGridWidgetView instance holding
     *         MessageGridWidgetDefinition data internally.
     */
    @ResponseView(MessageGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + MESSAGE_GRID_WIDGET + "/definition", method = RequestMethod.GET)
    public
    @ResponseBody
    ApiResponse<MessageGridWidgetView> getMessageGridWidgetDefinition() {
        final ApiResponse<MessageGridWidgetView> apiResponse = new ApiResponse<MessageGridWidgetView>();
        MessageGridWidget messageGridWidget;
        try {
            messageGridWidget = (MessageGridWidget) uiService
                    .getWidgetDefinition(MESSAGE_GRID_WIDGET);
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        final MessageGridWidgetView messageGridWidgetView = new MessageGridWidgetView(messageGridWidget);
        apiResponse.setData(messageGridWidgetView);
        return apiResponse;
    }
    /**
     * The endpoint for returning the event grid widget definition details
     * 
     * @return
     */
    @ResponseView(EventGridWidgetDetailsView.class)
    @RequestMapping(value = "/widgets/" + EVENT_GRID_WIDGET_NAME + "/definition", method = RequestMethod.GET)
    public @ResponseBody ApiResponse<EventGridWidgetView> getEventGridWidgetDefinition() {

        ApiResponse<EventGridWidgetView> apiResponse = new ApiResponse<EventGridWidgetView>();
        EventGridWidget eventGridWidget;

        try {
            eventGridWidget = (EventGridWidget) uiService.getWidgetDefinition(EVENT_GRID_WIDGET_NAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        EventGridWidgetView eventGridWidgetView = new EventGridWidgetView(eventGridWidget);
        apiResponse.setData(eventGridWidgetView);
        return apiResponse;
    }

}
