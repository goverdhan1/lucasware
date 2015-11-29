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
import com.lucas.alps.viewtype.widget.PicklineByWaveBarChartWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.PicklineByWaveBarChartWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicklineByWaveBarChartWidgetView extends WidgetView{
	
	private PicklineByWaveBarChartWidget picklineByWaveBarChartWidget;

	public PicklineByWaveBarChartWidgetView() {
		picklineByWaveBarChartWidget =  new PicklineByWaveBarChartWidget();
	}

	public PicklineByWaveBarChartWidgetView(PicklineByWaveBarChartWidget picklineByWaveBarChartWidget) {
		this.picklineByWaveBarChartWidget = picklineByWaveBarChartWidget;
	}

	public PicklineByWaveBarChartWidget getPicklineByWaveBarChartWidget() {
		return picklineByWaveBarChartWidget;
	}

	public void setPicklineByWaveBarChartWidget(
			PicklineByWaveBarChartWidget picklineByWaveBarChartWidget) {
		this.picklineByWaveBarChartWidget = picklineByWaveBarChartWidget;
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public Long getId() {
		return picklineByWaveBarChartWidget.getId();
	}
	public void setId(Long Id) {
		this.picklineByWaveBarChartWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public String getName() {
		return picklineByWaveBarChartWidget.getName() ;
	}
	public void setName(String name) {
		this.picklineByWaveBarChartWidget.setName(name); 
	}
	
	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public String getTitle() {
		return picklineByWaveBarChartWidget.getTitle() ;
	}
	public void setTitle(String title) {
		this.picklineByWaveBarChartWidget.setTitle(title); 
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public String getShortName() {
		return picklineByWaveBarChartWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.picklineByWaveBarChartWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return picklineByWaveBarChartWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.picklineByWaveBarChartWidget.setWidgetType(widgetType);
	}
	
    @JsonIgnore
	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public String getDescription() {
		return picklineByWaveBarChartWidget.getDescription();
	}
	public void setDescription(String description) {
		this.picklineByWaveBarChartWidget.setDescription(description);
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {
		
		return this.picklineByWaveBarChartWidget.getBroadcastList();
	}	

	public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {
		
			this.picklineByWaveBarChartWidget.setBroadcastList(broadcastList);
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.picklineByWaveBarChartWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}	

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.picklineByWaveBarChartWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return picklineByWaveBarChartWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.picklineByWaveBarChartWidget.setLicense(widgetLicense);
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		if (this.picklineByWaveBarChartWidget.getDataURL() != null) {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(this.picklineByWaveBarChartWidget.getDataURL(),
					DataUrlView.class);
		}
		return null;
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		if (dataURL != null) {
			ObjectMapper om = new ObjectMapper();
			this.picklineByWaveBarChartWidget.setDataURL(om
					.writeValueAsString(dataURL));
		}
	}

	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = picklineByWaveBarChartWidget
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

		picklineByWaveBarChartWidget.setActionConfig(mappedActionConfig);
	}
	
	
	// widget specific definition data


	//@JsonIgnore
	@JsonProperty("defaultData")
	@JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
	public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
		MultiMap multiMap = new org.apache.commons.collections.map.MultiValueMap();

		return multiMap;
	}
	public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
		//TODO Is deserialization required ??
		//if so, then we need to write custom desreializers for every new widget
	}


    @JsonView({CanvasDataView.class,PicklineByWaveBarChartWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.picklineByWaveBarChartWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.picklineByWaveBarChartWidget.setReactToList(reactToList);
    }

}
