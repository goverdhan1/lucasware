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
import com.lucas.alps.viewtype.widget.MessageMappingGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.MessageMappingGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;


/**
 * @author Naveen
 *
 */
public class MessageMappingGridWidgetView extends WidgetView {
	private MessageMappingGridWidget messageMappingGridWidget;
	
	public MessageMappingGridWidgetView(){
		messageMappingGridWidget = new MessageMappingGridWidget();
	}
	
	public MessageMappingGridWidgetView( MessageMappingGridWidget messageMappingGridWidget){
		this.messageMappingGridWidget = messageMappingGridWidget;
	}
	
	public MessageMappingGridWidget getMessageMappingGridWidget() {
		return messageMappingGridWidget;
	}

	public void setMessageMappingGridWidget(
			MessageMappingGridWidget messageMappingGridWidget) {
		this.messageMappingGridWidget = messageMappingGridWidget;
	}
	
	@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public Long getId() {
	return messageMappingGridWidget.getId();
}

public void setId(Long Id) {
	this.messageMappingGridWidget.setId(Id);
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public String getName() {
	return messageMappingGridWidget.getName();
}

public void setName(String name) {
	this.messageMappingGridWidget.setName(name);
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public String getTitle() {
	return messageMappingGridWidget.getTitle();
}

public void setTitle(String title) {
	this.messageMappingGridWidget.setTitle(title);
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public String getShortName() {
	return messageMappingGridWidget.getShortName();
}

public void setShortName(String shortName) {
	this.messageMappingGridWidget.setShortName(shortName);
}

@JsonProperty("type")
public WidgetType getWidgetType() {
	return messageMappingGridWidget.getWidgetType();
}

public void setWidgetType(WidgetType widgetType) {
	this.messageMappingGridWidget.setWidgetType(widgetType);
}

@JsonIgnore
@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public String getDescription() {
	return messageMappingGridWidget.getDescription();
}

public void setDescription(String description) {
	this.messageMappingGridWidget.setDescription(description);
}

@JsonView({ CanvasDataView.class})
public List<String> getBroadcastList() throws JsonParseException,
		JsonMappingException, IOException {

	return this.messageMappingGridWidget.getBroadcastList();
}

public void setBroadcastList(List<String> broadcastList)
		throws JsonProcessingException {

	this.messageMappingGridWidget.setBroadcastList(broadcastList);
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public DefaultViewConfigView getDefaultViewConfig()
		throws JsonParseException, JsonMappingException, IOException {
	ObjectMapper om = new ObjectMapper();
	return om.readValue(this.messageMappingGridWidget.getDefaultViewConfig(),
			DefaultViewConfigView.class);
}

public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView)
		throws JsonProcessingException {
	ObjectMapper om = new ObjectMapper();
	this.messageMappingGridWidget.setDefaultViewConfig(om
			.writeValueAsString(defaultViewConfigView));
}

@JsonIgnore
@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public WidgetLicense getWidgetLicense() {
	return messageMappingGridWidget.getLicense();
}

public void setWidgetLicense(WidgetLicense widgetLicense) {
	this.messageMappingGridWidget.setLicense(widgetLicense);
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
public DataUrlView getDataURL() throws JsonParseException,
		JsonMappingException, IOException {
	ObjectMapper om = new ObjectMapper();
	return om.readValue(this.messageMappingGridWidget.getDataURL(),
			DataUrlView.class);
}

public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
	ObjectMapper om = new ObjectMapper();
	this.messageMappingGridWidget.setDataURL(om.writeValueAsString(dataURL));
}

@JsonView({ CanvasDataView.class,
		MessageMappingGridWidgetDetailsView.class })
@JsonProperty("widgetActionConfig")
public Map<String, Map<String, Boolean>> getActionConfig()
		throws JsonParseException, JsonMappingException, IOException {

	Map<String, Map<Permission, Boolean>> actionConfigMap = messageMappingGridWidget
			.getActionConfig().getActionConfig();
	Map<String, Map<String, Boolean>> permissionsMap = new HashMap<String, Map<String, Boolean>>();

	for (Map.Entry<String, Map<Permission, Boolean>> entry : actionConfigMap
			.entrySet()) {
		String parentKey = entry.getKey();
		Map<String, Boolean> parentValue = new HashMap<String, Boolean>();
		Map<Permission, Boolean> childMap = entry.getValue();
		for (Map.Entry<Permission, Boolean> childEntry : childMap
				.entrySet()) {
			parentValue.put(childEntry.getKey().getPermissionName(),
					childEntry.getValue().booleanValue());
		}
		permissionsMap.put(parentKey, parentValue);
	}

	return permissionsMap;
}

public void setActionConfig(
		MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig)
		throws JsonProcessingException {

	messageMappingGridWidget.setActionConfig(mappedActionConfig);
}

// widget specific definition data

// @JsonIgnore
@JsonProperty("defaultData")
@JsonView({ CanvasDataView.class })
public MultiMap getDefinitionData() throws JsonParseException,
		JsonMappingException, IOException {
	MultiMap multiMap = new org.apache.commons.collections.map.MultiValueMap();

	return multiMap;
}

public void setDefinitionData(MultiValueMap data)
		throws JsonProcessingException {
	// TODO Is deserialization required ??
	// if so, then we need to write custom desreializers for every new
	// widget
}

}
