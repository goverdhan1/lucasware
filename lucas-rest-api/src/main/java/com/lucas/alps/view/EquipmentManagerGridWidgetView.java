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
import com.lucas.alps.viewtype.widget.EquipmentManagerGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.EquipmentManagerGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

/**
 * @Author Pedda
 * @Product Lucas.
 * Created By: Pedda Reddy
 * Created On Date: 07/06/15  Time: 03:46 PM
 * This Class provide the implementation for the functionality of..
 */
public class EquipmentManagerGridWidgetView extends WidgetView {



    private EquipmentManagerGridWidget equipmentManagerGridWidget;

    public EquipmentManagerGridWidgetView() {
        equipmentManagerGridWidget =  new EquipmentManagerGridWidget();
    }

    public EquipmentManagerGridWidgetView(EquipmentManagerGridWidget equipmentManagerGridWidget) {
        this.equipmentManagerGridWidget = equipmentManagerGridWidget;
    }

    public EquipmentManagerGridWidget getEquipmentManagerGridWidget() {
        return equipmentManagerGridWidget;
    }

    public void setEquipmentManagerGridWidget(EquipmentManagerGridWidget equipmentManagerGridWidget) {
        this.equipmentManagerGridWidget = equipmentManagerGridWidget;
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public Long getId() {
        return equipmentManagerGridWidget.getId();
    }
    public void setId(Long Id) {
        this.equipmentManagerGridWidget.setId(Id);
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public String getName() {
        return equipmentManagerGridWidget.getName() ;
    }
    public void setName(String name) {
        this.equipmentManagerGridWidget.setName(name);
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public String getTitle() {
        return equipmentManagerGridWidget.getTitle() ;
    }
    public void setTitle(String title) {
        this.equipmentManagerGridWidget.setTitle(title);
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public String getShortName() {
        return equipmentManagerGridWidget.getShortName();
    }
    public void setShortName(String shortName) {
        this.equipmentManagerGridWidget.setShortName(shortName);
    }
    
    @JsonProperty("type")
    public WidgetType getWidgetType() {
        return equipmentManagerGridWidget.getWidgetType();
    }
    public void setWidgetType(WidgetType widgetType) {
        this.equipmentManagerGridWidget.setWidgetType(widgetType);
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public String getDescription() {
        return equipmentManagerGridWidget.getDescription();
    }
    public void setDescription(String description) {
        this.equipmentManagerGridWidget.setDescription(description);
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {

        return this.equipmentManagerGridWidget.getBroadcastList();
    }

    public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {

            this.equipmentManagerGridWidget.setBroadcastList(broadcastList);
    }



    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.equipmentManagerGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
    }

    public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.equipmentManagerGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
    }

    @JsonIgnore
    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public WidgetLicense getWidgetLicense() {
        return equipmentManagerGridWidget.getLicense() ;
    }
    public void setWidgetLicense(WidgetLicense widgetLicense) {
        this.equipmentManagerGridWidget.setLicense(widgetLicense);
    }

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(this.equipmentManagerGridWidget.getDataURL(), DataUrlView.class);
    }

    public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        this.equipmentManagerGridWidget.setDataURL(om.writeValueAsString(dataURL));
    }
    
    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    @JsonProperty("widgetActionConfig")
    public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
            JsonMappingException, IOException {

        Map<String, Map<Permission, Boolean>> actionConfigMap = equipmentManagerGridWidget
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

        equipmentManagerGridWidget.setActionConfig(mappedActionConfig);
    }


    // widget specific definition data


    //@JsonIgnore
    @JsonProperty("defaultData")
    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
        MultiMap multiMap = new MultiValueMap();

        return multiMap;
    }
    public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
        //TODO Is deserialization required ??
        //if so, then we need to write custom desreializers for every new widget
    }

    /*@JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public ReactToMapView getReactToMap() {
        return new ReactToMapView(equipmentManagerGridWidget.getReactToMap());
    }
    
    public void setReactToMap(ReactToMapView reactToMapView) {
        this.equipmentManagerGridWidget.setReactToMap(reactToMapView.getReactToMap());
    }*/

    @JsonView({CanvasDataView.class,EquipmentManagerGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.equipmentManagerGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.equipmentManagerGridWidget.setReactToList(reactToList);
    }



}
