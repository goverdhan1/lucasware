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
import com.lucas.alps.viewtype.widget.EventGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.EventGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

@SuppressWarnings("serial")
public class EventGridWidgetView extends WidgetView {

	
	private EventGridWidget eventGridWidget;

	public EventGridWidgetView() {
		eventGridWidget =  new EventGridWidget();
	}

	public EventGridWidgetView(EventGridWidget eventGridWidget) {
		this.eventGridWidget = eventGridWidget;
	}

	public EventGridWidget getEventGridWidget() {
		return eventGridWidget;
	}

	public void setEventGridWidget(EventGridWidget eventGridWidget) {
		this.eventGridWidget = eventGridWidget;
	}

	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public Long getId() {
		return eventGridWidget.getId();
	}
	public void setId(Long Id) {
		this.eventGridWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public String getName() {
		return eventGridWidget.getName() ;
	}
	public void setName(String name) {
		this.eventGridWidget.setName(name); 
	}
	
	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public String getTitle() {
		return eventGridWidget.getTitle() ;
	}
	public void setTitle(String title) {
		this.eventGridWidget.setTitle(title); 
	}

	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public String getShortName() {
		return eventGridWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.eventGridWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return eventGridWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.eventGridWidget.setWidgetType(widgetType);
	}
	
    @JsonIgnore
	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public String getDescription() {
		return eventGridWidget.getDescription();
	}
	public void setDescription(String description) {
		this.eventGridWidget.setDescription(description);
	}

		
	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.eventGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}	

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.eventGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return eventGridWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.eventGridWidget.setLicense(widgetLicense);
	}
	
	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.eventGridWidget.getDataURL(), DataUrlView.class);
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.eventGridWidget.setDataURL(om.writeValueAsString(dataURL));
	}


	@JsonView({CanvasDataView.class,EventGridWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = eventGridWidget
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

		eventGridWidget.setActionConfig(mappedActionConfig);
	}
	
	
	
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.eventGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.eventGridWidget.setReactToList(reactToList);
    }

}
