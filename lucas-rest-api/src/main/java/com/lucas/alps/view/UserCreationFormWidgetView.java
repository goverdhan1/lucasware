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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.SearchGroupGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.UserCreationFormWidgetDetailsView;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.UserCreationFormWidget;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;
import com.lucas.services.ui.Languages;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@JsonPropertyOrder({  "id", "name", "shortName", "widgetType", "description", "widgetActionConfig",  "widgetLicense", "broadCastMap", "definitionData", "widgetViewConfig" })
public class UserCreationFormWidgetView extends WidgetView {

	private UserCreationFormWidget userCreationFormWidget;

	public UserCreationFormWidgetView() {
		userCreationFormWidget =  new UserCreationFormWidget();
	}

	public UserCreationFormWidgetView(UserCreationFormWidget userCreationFormWidget) {
		this.userCreationFormWidget = userCreationFormWidget;
	}

	public UserCreationFormWidget getUserCreationFormWidget() {
		return userCreationFormWidget;
	}

	public void setUserCreationFormWidget(
			UserCreationFormWidget userCreationFormWidget) {
		this.userCreationFormWidget = userCreationFormWidget;
	}

    // common properties for all widgets
	@JsonView({CanvasDataView.class,SearchUserGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class})
	public Long getId() {
		return userCreationFormWidget.getId();
	}
	public void setId(Long Id) {
		this.userCreationFormWidget.setId(Id);
	}

	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public String getName() {
		return userCreationFormWidget.getName() ;
	}
	public void setName(String name) {
		this.userCreationFormWidget.setName(name); 
	}



	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public String getShortName() {
		return userCreationFormWidget.getShortName();
	}
	public void setShortName(String shortName) {
		this.userCreationFormWidget.setShortName(shortName);
	}

    @JsonProperty("type")
	public WidgetType getWidgetType() {
		return userCreationFormWidget.getWidgetType();
	}
	public void setWidgetType(WidgetType widgetType) {
		this.userCreationFormWidget.setWidgetType(widgetType);
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public String getDescription() {
		return userCreationFormWidget.getDescription();
	}
	public void setDescription(String description) {
		this.userCreationFormWidget.setDescription(description);
	}

	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public List<String> getBroadcastList() throws JsonParseException, JsonMappingException, IOException {
		
		return this.userCreationFormWidget.getBroadcastList();
	}	

	public void setBroadcastList(List<String> broadcastList) throws JsonProcessingException {
		
			this.userCreationFormWidget.setBroadcastList(broadcastList);
	}


	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readValue(this.userCreationFormWidget.getDefaultViewConfig(), DefaultViewConfigView.class);
	}	

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfigView) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.userCreationFormWidget.setDefaultViewConfig(om.writeValueAsString(defaultViewConfigView));
	}

    @JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public WidgetLicense getWidgetLicense() {
		return userCreationFormWidget.getLicense() ;
	}
	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.userCreationFormWidget.setLicense(widgetLicense);
	}

	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<String, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Map<Permission, Boolean>> actionConfigMap = userCreationFormWidget
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

		userCreationFormWidget.setActionConfig(mappedActionConfig);
	}
	
	
	// widget specific definition data


	//@JsonIgnore
	@JsonProperty("definitionData")
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public MultiMap getDefinitionData() throws JsonParseException, JsonMappingException, IOException {
		MultiMap multiMap = new MultiHashMap();
		if (this.getAmdScreenLanguageList() != null) {
			for (Languages language : this.getAmdScreenLanguageList()) {
				multiMap.put("amdScreenLanguageList", language);
			}
		}

		if (this.getHandheldScreenLanguageList() != null) {
			for (Languages language : this.getHandheldScreenLanguageList()) {
				multiMap.put("handheldScreenLanguageList", language);
			}
		}

		if (this.getJenniferToUserLanguageList() != null) {
			for (Languages language : this.getJenniferToUserLanguageList()) {
				multiMap.put("jenniferToUserLanguageList", language);
			}
		}

		if (this.getUsertoJenniferLanguageList() != null) {
			for (Languages language : this.getUsertoJenniferLanguageList()) {
				multiMap.put("userToJenniferLanguageList", language);
			}
		}
		multiMap.put("User", this.getData());

		return multiMap;
	}
	public void setDefinitionData(MultiMap data) throws JsonProcessingException {
		//TODO Is deserialization required ??
		//if so, then we need to write custom desreializers for every new widget
	}


	@JsonIgnore
	@JsonProperty("user")
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public UserView getData() throws JsonParseException, JsonMappingException, IOException {
		LucasObjectMapper jacksonObjectMapper = new LucasObjectMapper();
		if(userCreationFormWidget.getDefinitionData() != null) {
			User user = jacksonObjectMapper.readValue(userCreationFormWidget.getDefinitionData(), new TypeReference<User>() {
			});
			UserView userview = new UserView(user);
			return userview;
		}
		return null;
	}


	public void setData(UserView data) throws JsonProcessingException {
		this.userCreationFormWidget.setDefinitionData(new LucasObjectMapper().writeValueAsString(data.getUser()));
	}



	@JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public List<Languages> getJenniferToUserLanguageList() {
		return userCreationFormWidget.getJenniferToUserLanguageList();
	}
	public void setJenniferToUserLanguageList(
			List<Languages> jenniferToUserLanguageList) {
		userCreationFormWidget.setJenniferToUserLanguageList(jenniferToUserLanguageList);
	}

	@JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public List<Languages> getUsertoJenniferLanguageList() {
		return userCreationFormWidget.getUsertoJenniferLanguageList();
	}

	public void setUsertoJenniferLanguageList(
			List<Languages> usertoJenniferLanguageList) {
		userCreationFormWidget.setUsertoJenniferLanguageList(usertoJenniferLanguageList);
	}

	@JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public List<Languages> getHandheldScreenLanguageList() {
		return userCreationFormWidget.getHandheldScreenLanguageList();
	}

	public void setHandheldScreenLanguageList(
			List<Languages> handheldScreenLanguageList) {
		userCreationFormWidget.setHandheldScreenLanguageList(handheldScreenLanguageList);
	}

	@JsonIgnore
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
	public List<Languages> getAmdScreenLanguageList() {
		return userCreationFormWidget.getamdScreenLanguageList();
	}

	public void setAmdScreenLanguageList(List<Languages> amdScreenLanguageList) {
		userCreationFormWidget.setamdScreenLanguageList(amdScreenLanguageList);
	}

    @JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class})
    public List<String> getReactToList() throws JsonParseException, JsonMappingException, IOException {

        return this.userCreationFormWidget.getReactToList();
    }

    public void setReactToList(List<String> reactToList) throws JsonProcessingException {

        this.userCreationFormWidget.setReactToList(reactToList);
    }

}
