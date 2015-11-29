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
import com.lucas.alps.viewtype.widget.SearchProductGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.SearchUserGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SearchUserGridWidgetView extends WidgetView {

	
	private SearchUserGridWidget searchUserGridWidget;

	public SearchUserGridWidgetView() {
		searchUserGridWidget =  new SearchUserGridWidget();
	}

	public SearchUserGridWidgetView(SearchUserGridWidget searchUserGridWidget) {
		this.searchUserGridWidget = searchUserGridWidget;
	}

	public SearchUserGridWidget getSearchUserGridWidget() {
		return searchUserGridWidget;
	}

	public void setSearchUserGridWidget(SearchUserGridWidget searchUserGridWidget) {
		this.searchUserGridWidget = searchUserGridWidget;
	}

	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class,SearchProductGridWidgetDetailsView.class})
	public Long getId() {
		return searchUserGridWidget.getId();
	}
	public void setId(Long Id) {
		this.searchUserGridWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public String getName() {
		return searchUserGridWidget.getName() ;
	}
	public void setName(String name) {
		this.searchUserGridWidget.setName(name); 
	}
	
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public String getTitle() {
		return searchUserGridWidget.getTitle() ;
	}
	public void setTitle(String title) {
		this.searchUserGridWidget.setTitle(title); 
	}

	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public String getShortName() {
		return searchUserGridWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.searchUserGridWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return searchUserGridWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.searchUserGridWidget.setWidgetType(widgetType);
	}
	
    @JsonIgnore
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public String getDescription() {
		return searchUserGridWidget.getDescription();
	}
	public void setDescription(String description) {
		this.searchUserGridWidget.setDescription(description);
	}

	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {
		
		return this.searchUserGridWidget.getBroadcastList();
	}	

	public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {
		
			this.searchUserGridWidget.setBroadcastList(broadcastList);
	}

	
	
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.searchUserGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}	

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.searchUserGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return searchUserGridWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.searchUserGridWidget.setLicense(widgetLicense);
	}
	
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.searchUserGridWidget.getDataURL(), DataUrlView.class);
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.searchUserGridWidget.setDataURL(om.writeValueAsString(dataURL));
	}


	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = searchUserGridWidget
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

		searchUserGridWidget.setActionConfig(mappedActionConfig);
	}
	
	
	// widget specific definition data


	//@JsonIgnore
	@JsonProperty("defaultData")
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
		MultiMap multiMap = new org.apache.commons.collections.map.MultiValueMap();

		return multiMap;
	}
	public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
		//TODO Is deserialization required ??
		//if so, then we need to write custom desreializers for every new widget
	}

	/*@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
	public ReactToMapView getReactToMap() {
		return new ReactToMapView(searchUserGridWidget.getReactToMap());
	}
	
	public void setReactToMap(ReactToMapView reactToMapView) {
		this.searchUserGridWidget.setReactToMap(reactToMapView.getReactToMap());
	}*/

    @JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.searchUserGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.searchUserGridWidget.setReactToList(reactToList);
    }

}
