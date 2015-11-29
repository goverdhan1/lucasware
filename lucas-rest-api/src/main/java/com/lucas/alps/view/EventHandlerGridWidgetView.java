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
import com.lucas.alps.viewtype.widget.EventHandlerGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageMappingGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.EventHandlerGridWidget;
import com.lucas.entity.ui.widget.MessageMappingGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

/**
 * @author Naveen
 * 
 */
public class EventHandlerGridWidgetView extends WidgetView {

	private EventHandlerGridWidget eventHandlerGridWidget;

	public EventHandlerGridWidgetView() {
		eventHandlerGridWidget = new EventHandlerGridWidget();
	}

	public EventHandlerGridWidgetView(
			EventHandlerGridWidget eventHandlerGridWidget) {
		this.eventHandlerGridWidget = eventHandlerGridWidget;
	}

	public EventHandlerGridWidget getEventHandlerGridWidget() {
		return eventHandlerGridWidget;
	}

	public void setEventHandlerGridWidget(
			EventHandlerGridWidget eventHandlerGridWidget) {
		this.eventHandlerGridWidget = eventHandlerGridWidget;
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public Long getId() {
		return eventHandlerGridWidget.getId();
	}

	public void setId(Long Id) {
		this.eventHandlerGridWidget.setId(Id);
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public String getName() {
		return eventHandlerGridWidget.getName();
	}

	public void setName(String name) {
		this.eventHandlerGridWidget.setName(name);
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public String getTitle() {
		return eventHandlerGridWidget.getTitle();
	}

	public void setTitle(String title) {
		this.eventHandlerGridWidget.setTitle(title);
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public String getShortName() {
		return eventHandlerGridWidget.getShortName();
	}

	public void setShortName(String shortName) {
		this.eventHandlerGridWidget.setShortName(shortName);
	}

	@JsonProperty("type")
	public WidgetType getWidgetType() {
		return eventHandlerGridWidget.getWidgetType();
	}

	public void setWidgetType(WidgetType widgetType) {
		this.eventHandlerGridWidget.setWidgetType(widgetType);
	}

	@JsonIgnore
	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public String getDescription() {
		return eventHandlerGridWidget.getDescription();
	}

	public void setDescription(String description) {
		this.eventHandlerGridWidget.setDescription(description);
	}

	@JsonView({ CanvasDataView.class })
	public List<String> getBroadcastList() throws JsonParseException,
			JsonMappingException, IOException {

		return this.eventHandlerGridWidget.getBroadcastList();
	}

	public void setBroadcastList(List<String> broadcastList)
			throws JsonProcessingException {

		this.eventHandlerGridWidget.setBroadcastList(broadcastList);
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public DefaultViewConfigView getDefaultViewConfig()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.eventHandlerGridWidget.getDefaultViewConfig(),
				DefaultViewConfigView.class);
	}

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView)
			throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.eventHandlerGridWidget.setDefaultViewConfig(om
				.writeValueAsString(defaultViewConfigView));
	}

	@JsonIgnore
	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public WidgetLicense getWidgetLicense() {
		return eventHandlerGridWidget.getLicense();
	}

	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.eventHandlerGridWidget.setLicense(widgetLicense);
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	public DataUrlView getDataURL() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.eventHandlerGridWidget.getDataURL(),
				DataUrlView.class);
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.eventHandlerGridWidget.setDataURL(om.writeValueAsString(dataURL));
	}

	@JsonView({ CanvasDataView.class, EventHandlerGridWidgetDetailsView.class })
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig()
			throws JsonParseException, JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = eventHandlerGridWidget
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

		eventHandlerGridWidget.setActionConfig(mappedActionConfig);
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

}
