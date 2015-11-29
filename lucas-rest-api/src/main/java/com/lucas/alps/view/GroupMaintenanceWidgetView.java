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

package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.GroupMaintenanceWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.GroupMaintenanceWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 11/14/14  Time: 1:56 PM
 * This Class provide the implementation for the functionality of..
 */

public class GroupMaintenanceWidgetView extends WidgetView {

    private GroupMaintenanceWidget groupMaintenanceWidget;


    public GroupMaintenanceWidgetView() {
        this.groupMaintenanceWidget = new GroupMaintenanceWidget();
    }

    public GroupMaintenanceWidgetView(GroupMaintenanceWidget groupMaintenanceWidget) {
        this.groupMaintenanceWidget = groupMaintenanceWidget;
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public Long getId() {
        return groupMaintenanceWidget.getId();
    }
    public void setId(Long Id) {
        this.groupMaintenanceWidget.setId(Id);
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public String getName() {
        return groupMaintenanceWidget.getName() ;
    }
    public void setName(String name) {
        this.groupMaintenanceWidget.setName(name);
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public String getTitle() {
        return groupMaintenanceWidget.getTitle() ;
    }
    public void setTitle(String title) {
        this.groupMaintenanceWidget.setTitle(title);
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public String getShortName() {
        return groupMaintenanceWidget.getShortName();
    }
    public void setShortName(String shortName) {
        this.groupMaintenanceWidget.setShortName(shortName);
    }

    @JsonProperty("type")
    public WidgetType getWidgetType() {
        return groupMaintenanceWidget.getWidgetType();
    }
    public void setWidgetType(WidgetType widgetType) {
        this.groupMaintenanceWidget.setWidgetType(widgetType);
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public String getDescription() {
        return groupMaintenanceWidget.getDescription();
    }
    public void setDescription(String description) {
        this.groupMaintenanceWidget.setDescription(description);
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {

        return this.groupMaintenanceWidget.getBroadcastList();
    }

    public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {

        this.groupMaintenanceWidget.setBroadcastList(broadcastList);
    }



    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.groupMaintenanceWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
    }

    public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.groupMaintenanceWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public WidgetLicense getWidgetLicense() {
        return groupMaintenanceWidget.getLicense() ;
    }
    public void setWidgetLicense(WidgetLicense widgetLicense) {
        this.groupMaintenanceWidget.setLicense(widgetLicense);
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.groupMaintenanceWidget.getDataURL(), DataUrlView.class);

    }

    public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.groupMaintenanceWidget.setDataURL(om.writeValueAsString(dataURL));
    }


    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    @JsonProperty("widgetActionConfig")
    public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
            JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = groupMaintenanceWidget
				.getActionConfig().getActionConfig();
		Map<String, Map<String, Boolean>> permissionsMap = new HashMap<String, Map<String, Boolean>>();

		for (Map.Entry<String, Map<Permission, Boolean>> entry : actionConfigMap.entrySet()) {
			String parentKey = entry.getKey();
			Map<String, Boolean> parentValue = new HashMap<String, Boolean>();
			Map<Permission, Boolean> childMap = entry
					.getValue();
			for(Map.Entry<Permission, Boolean> childEntry:childMap.entrySet()){
				parentValue.put(childEntry.getKey().getPermissionName(), childEntry.getValue().booleanValue());
			}
			permissionsMap.put(parentKey, parentValue);
		}

		return permissionsMap;
	}

    public void setActionConfig(
            MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig)
            throws JsonProcessingException {

        this.groupMaintenanceWidget.setActionConfig(mappedActionConfig);
    }


    // widget specific definition data


    //@JsonIgnore
    @JsonProperty("defaultData")
    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
        MultiMap multiMap = new MultiValueMap();

        return multiMap;
    }
    public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
        //if so, then we need to write custom desreializers for every new widget
    }

    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.groupMaintenanceWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.groupMaintenanceWidget.setReactToList(reactToList);
    }

   /* @JsonView(SearchUserGridWidgetDetailsView.class)
    public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.groupMaintenanceWidget.getDataURL(), DataUrlView.class);

    }

    public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.groupMaintenanceWidget.setDataURL(om.writeValueAsString(dataURL));
    }*/
}
