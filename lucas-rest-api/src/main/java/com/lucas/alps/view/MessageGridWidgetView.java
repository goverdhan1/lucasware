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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.MessageGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.MessageGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

/**
 * @Author Pedda
 * @Product Lucas.
 * Created By: Pedda Reddy
 * Created On Date: 07/06/15  Time: 03:46 PM
 * This Class provide the implementation for the functionality of..
 */

@SuppressWarnings("serial")
public class MessageGridWidgetView extends WidgetView{

    private MessageGridWidget messageGridWidget;

    public MessageGridWidgetView() {
        messageGridWidget =  new MessageGridWidget();
    }

    public MessageGridWidgetView(MessageGridWidget messageGridWidget) {
        this.messageGridWidget = messageGridWidget;
    }

    public MessageGridWidget getMessageGridWidget() {
        return messageGridWidget;
    }

    public void setMessageGridWidget(MessageGridWidget messageGridWidget) {
        this.messageGridWidget = messageGridWidget;
    }
    
    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public Long getId() {
        return messageGridWidget.getId();
    }
    public void setId(Long Id) {
        this.messageGridWidget.setId(Id);
    }

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public String getName() {
        return messageGridWidget.getName() ;
    }
    public void setName(String name) {
        this.messageGridWidget.setName(name);
    }

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public String getTitle() {
        return messageGridWidget.getTitle() ;
    }
    public void setTitle(String title) {
        this.messageGridWidget.setTitle(title);
    }

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public String getShortName() {
        return messageGridWidget.getShortName();
    }
    public void setShortName(String shortName) {
        this.messageGridWidget.setShortName(shortName);
    }
    
    @JsonProperty("type")
    public WidgetType getWidgetType() {
        return messageGridWidget.getWidgetType();
    }
    public void setWidgetType(WidgetType widgetType) {
        this.messageGridWidget.setWidgetType(widgetType);
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public String getDescription() {
        return messageGridWidget.getDescription();
    }
    public void setDescription(String description) {
        this.messageGridWidget.setDescription(description);
    }

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {

        return this.messageGridWidget.getBroadcastList();
    }

    public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {

            this.messageGridWidget.setBroadcastList(broadcastList);
    }



    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.messageGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
    }

    public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.messageGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public WidgetLicense getWidgetLicense() {
        return messageGridWidget.getLicense() ;
    }
    public void setWidgetLicense(WidgetLicense widgetLicense) {
        this.messageGridWidget.setLicense(widgetLicense);
    }

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.messageGridWidget.getDataURL(), DataUrlView.class);
    }

    public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.messageGridWidget.setDataURL(om.writeValueAsString(dataURL));
    }
    
    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    @JsonProperty("widgetActionConfig")
    public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
            JsonMappingException, IOException {

        Map<String, Map<Permission, Boolean>> actionConfigMap = messageGridWidget
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

        messageGridWidget.setActionConfig(mappedActionConfig);
    }


    // widget specific definition data


    //@JsonIgnore
    @JsonProperty("defaultData")
    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
        MultiMap multiMap = new MultiValueMap();

        return multiMap;
    }
    public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
        //TODO Is deserialization required ??
        //if so, then we need to write custom desreializers for every new widget
    }

    /*@JsonView({MessageGridWidgetDetailsView.class})
    public ReactToMapView getReactToMap() {
        return new ReactToMapView(equipmentManagerGridWidget.getReactToMap());
    }
    
    public void setReactToMap(ReactToMapView reactToMapView) {
        this.equipmentManagerGridWidget.setReactToMap(reactToMapView.getReactToMap());
    }*/

    @JsonView({CanvasDataView.class,MessageGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.messageGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.messageGridWidget.setReactToList(reactToList);
    }

}
