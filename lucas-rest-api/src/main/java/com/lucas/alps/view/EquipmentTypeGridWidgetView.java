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
import com.lucas.alps.viewtype.widget.EquipmentTypeGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.EquipmentTypeGridWidget;
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
 * Created On Date: 6/19/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class EquipmentTypeGridWidgetView extends WidgetView {



    private EquipmentTypeGridWidget equipmentTypeGridWidget;

	public EquipmentTypeGridWidgetView() {
		equipmentTypeGridWidget =  new EquipmentTypeGridWidget();
	}

	public EquipmentTypeGridWidgetView(EquipmentTypeGridWidget equipmentTypeGridWidget) {
		this.equipmentTypeGridWidget = equipmentTypeGridWidget;
	}

	public EquipmentTypeGridWidget getEquipmentManagerGridWidget() {
		return equipmentTypeGridWidget;
	}

	public void setEquipmentManagerGridWidget(EquipmentTypeGridWidget equipmentTypeGridWidget) {
		this.equipmentTypeGridWidget = equipmentTypeGridWidget;
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public Long getId() {
		return equipmentTypeGridWidget.getId();
	}
	public void setId(Long Id) {
		this.equipmentTypeGridWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public String getName() {
		return equipmentTypeGridWidget.getName() ;
	}
	public void setName(String name) {
		this.equipmentTypeGridWidget.setName(name);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public String getTitle() {
		return equipmentTypeGridWidget.getTitle() ;
	}
	public void setTitle(String title) {
		this.equipmentTypeGridWidget.setTitle(title);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public String getShortName() {
		return equipmentTypeGridWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.equipmentTypeGridWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return equipmentTypeGridWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.equipmentTypeGridWidget.setWidgetType(widgetType);
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public String getDescription() {
		return equipmentTypeGridWidget.getDescription();
	}
	public void setDescription(String description) {
		this.equipmentTypeGridWidget.setDescription(description);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {

		return this.equipmentTypeGridWidget.getBroadcastList();
	}

	public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {

			this.equipmentTypeGridWidget.setBroadcastList(broadcastList);
	}



	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.equipmentTypeGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.equipmentTypeGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return equipmentTypeGridWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.equipmentTypeGridWidget.setLicense(widgetLicense);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.equipmentTypeGridWidget.getDataURL(), DataUrlView.class);
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.equipmentTypeGridWidget.setDataURL(om.writeValueAsString(dataURL));
	}


	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = equipmentTypeGridWidget
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

		equipmentTypeGridWidget.setActionConfig(mappedActionConfig);
	}


	// widget specific definition data


	//@JsonIgnore
	@JsonProperty("defaultData")
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
		MultiMap multiMap = new MultiValueMap();

		return multiMap;
	}
	public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
		//TODO Is deserialization required ??
		//if so, then we need to write custom desreializers for every new widget
	}

	/*@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
	public ReactToMapView getReactToMap() {
		return new ReactToMapView(equipmentTypeGridWidget.getReactToMap());
	}
	
	public void setReactToMap(ReactToMapView reactToMapView) {
		this.equipmentTypeGridWidget.setReactToMap(reactToMapView.getReactToMap());
	}*/

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.equipmentTypeGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.equipmentTypeGridWidget.setReactToList(reactToList);
    }

}
