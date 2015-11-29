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
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.SearchProductGridWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SearchProductGridWidgetView extends WidgetView {

	
	private SearchProductGridWidget searchProductGridWidget;

	public SearchProductGridWidgetView() {
		searchProductGridWidget =  new SearchProductGridWidget();
	}

	public SearchProductGridWidgetView(SearchProductGridWidget SearchProductGridWidget) {
		this.searchProductGridWidget = SearchProductGridWidget;
	}

	public SearchProductGridWidget getSearchProductGridWidget() {
		return searchProductGridWidget;
	}

	public void setSearchProductGridWidget(SearchProductGridWidget SearchProductGridWidget) {
		this.searchProductGridWidget = SearchProductGridWidget;
	}


	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public Long getId() {
		return searchProductGridWidget.getId();
	}
	public void setId(Long Id) {
		this.searchProductGridWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public String getName() {
		return searchProductGridWidget.getName() ;
	}
	public void setName(String name) {
		this.searchProductGridWidget.setName(name); 
	}
	
	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public String getTitle() {
		return searchProductGridWidget.getTitle() ;
	}
	public void setTitle(String title) {
		this.searchProductGridWidget.setTitle(title); 
	}

	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public String getShortName() {
		return searchProductGridWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.searchProductGridWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return searchProductGridWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.searchProductGridWidget.setWidgetType(widgetType);
	}
	
    @JsonIgnore
	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public String getDescription() {
		return searchProductGridWidget.getDescription();
	}
	public void setDescription(String description) {
		this.searchProductGridWidget.setDescription(description);
	}

	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {
		
		return this.searchProductGridWidget.getBroadcastList();
	}	

	public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {
		
			this.searchProductGridWidget.setBroadcastList(broadcastList);
	}



	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.searchProductGridWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}	

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.searchProductGridWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return searchProductGridWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.searchProductGridWidget.setLicense(widgetLicense);
	}

	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.searchProductGridWidget.getDataURL(), DataUrlView.class);

	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.searchProductGridWidget.setDataURL(om.writeValueAsString(dataURL));
	}


	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = searchProductGridWidget
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
			throws Exception {

		searchProductGridWidget.setActionConfig(mappedActionConfig);
	}
	
	
	// widget specific definition data


	@JsonProperty("defaultData")
	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
		MultiMap multiMap = new org.apache.commons.collections.map.MultiValueMap();

		return multiMap;
	}
	public void setDefinitionData(MultiValueMap data) throws JsonProcessingException {
		//TODO Is deserialization required ??
		//if so, then we need to write custom desreializers for every new widget
	}

	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.searchProductGridWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.searchProductGridWidget.setReactToList(reactToList);
    }
}
