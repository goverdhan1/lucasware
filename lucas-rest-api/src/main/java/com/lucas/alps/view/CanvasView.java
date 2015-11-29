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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.TimestampDeserializer;
import com.lucas.alps.viewtype.*;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedCanvasActionConfig;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;
import com.lucas.services.util.CollectionsUtilService;

@SuppressWarnings("rawtypes")
public class CanvasView implements java.io.Serializable {
	
	private static final long serialVersionUID = -591924984218836613L;
	
	private Canvas canvas;


	public CanvasView() { 
		this.canvas = new Canvas();
	}
	
	public CanvasView(Canvas canvas) {
		this.canvas = canvas;
	}

	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public List<DefaultWidgetInstanceView> getWidgetInstanceList() {
				List<DefaultWidgetInstanceView> defaultWidgetInstanceViewList = new ArrayList<DefaultWidgetInstanceView>();
		if (canvas != null) {
			for (DefaultWidgetInstance defaultWidgetInstance : CollectionsUtilService
					.nullGuard(canvas.getWidgetInstanceList())) {
				DefaultWidgetInstanceView defaultWidgetInstanceView = new DefaultWidgetInstanceView(
						defaultWidgetInstance);
				defaultWidgetInstanceViewList.add(defaultWidgetInstanceView);
			}
		}
		return defaultWidgetInstanceViewList;
	}
	
	public void setWidgetInstanceList(List<DefaultWidgetInstanceView> widgetInstanceList) {
		List<DefaultWidgetInstance> defaultWidgetInstanceList = new ArrayList<DefaultWidgetInstance>();
		for( DefaultWidgetInstanceView defaultWidgetInstanceView : CollectionsUtilService.nullGuard(widgetInstanceList)) {
			defaultWidgetInstanceList.add(defaultWidgetInstanceView.getDefaultWidgetInstance());
		}
		if (canvas != null) {
			this.canvas.setWidgetInstanceList(defaultWidgetInstanceList);
		}
	}
	
	
	/*@JsonView(FavoriteCanvasesView.class)
	@JsonProperty("widget")
	public List<AbstractLicensableWidgetView<?>> getWidgetDefinitionList() {
		
		List<AbstractLicensableWidgetView<?>> abstractLicensableWidgetList = new ArrayList<AbstractLicensableWidgetView<?>>();
		for( DefaultWidgetInstance defaultWidgetInstance : canvas.getWidgetInstanceList()) {
			AbstractLicensableWidgetView<?> abstractLicensableWidgetView = new AbstractLicensableWidgetView(defaultWidgetInstance.getWidget());
			abstractLicensableWidgetList.add(abstractLicensableWidgetView);
		}
		return abstractLicensableWidgetList;
	}
	
	//Need to implement
	public void setWidgetDefinitionList(List<DefaultWidgetInstanceView> widgetInstanceList) {
		List<DefaultWidgetInstanzce> defaultWidgetInstanceList = new ArrayList<DefaultWidgetInstance>();
		for( DefaultWidgetInstanceView defaultWidgetInstanceView : widgetInstanceList) {
			defaultWidgetInstanceList.add(defaultWidgetInstanceView.getDefaultWidgetInstance());
		}
		this.canvas.setWidgetInstanceList(defaultWidgetInstanceList);
	}
	*/
	
	@JsonView({ActiveCanvasView.class,OpenCanvasView.class,CanvasDataView.class,FavoriteCanvasesView.class,UserDetailsView.class, CanvasPalleteView.class})
	public Long getCanvasId() {
		if (canvas != null) {
			return canvas.getCanvasId();
		}
		return null;
	}
	public void setCanvasId(Long canvasId) {
		if (canvas != null) {
			this.canvas.setCanvasId(canvasId);
		}
	}
	
	@JsonIgnore
	public Set<User> getUserSet() {
		return canvas.getUserSet();
	}
	public void setUserSet(Set<User> userSet) {
		this.canvas.setUserSet(userSet);
	}	
	@JsonView({ActiveCanvasView.class,OpenCanvasView.class,CanvasDataView.class,FavoriteCanvasesView.class, CanvasPalleteView.class})
	@JsonProperty("canvasName")
	public String getName() {
		if (canvas != null) {
			return canvas.getName();
		}
		return null;
	}
	public void setName(String canvasName) {
		if (canvas != null) {
			this.canvas.setName(canvasName);
		}
	}

	@JsonIgnore
	public Canvas getCanvas() {
		return canvas;
	}
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@JsonView({ActiveCanvasView.class,CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	public String getShortName() {
		if (canvas != null) {
			return this.canvas.getShortName();
		}
		return null;
	}

	public void setShortName(String shortName) {
		if (canvas != null) {
			this.canvas.setShortName(shortName);
		}
	}
	
	@JsonView({CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	public String getCreatedByUserName() {
		if (canvas != null) {
			return this.canvas.getCreatedByUserName();
		}
		return null;
	}
	public void setCreatedByUserName(String createdByUserName) {
		if (canvas != null) {
			this.canvas.setCreatedByUserName(createdByUserName);
		}
	}

	@JsonView({CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	
	public Date getCreatedDateTime() {
		if (canvas != null) {
			return this.canvas.getCreatedDateTime();
		}
		return null;
	}
	
	@JsonDeserialize(using=TimestampDeserializer.class)
	public void setCreatedDateTime(Date createdDateTime) {
		if (canvas != null) {
			this.canvas.setCreatedDateTime(createdDateTime);
		}
	}

	@JsonView({CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	public String getUpdatedByUserName() {
		if (canvas != null) {
			return this.canvas.getUpdatedByUserName();
		}
		return null;
	}

	public void setUpdatedByUserName(String updatedByUserName) {
		if (canvas != null) {
			this.canvas.setUpdatedByUserName(updatedByUserName);
		}

	}

	@JsonView({CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	public Date getUpdatedDateTime() {
		if (canvas != null) {
			return this.canvas.getUpdatedDateTime();
		}
		return null;
	}
	public void setUpdatedDateTime(Date updatedDateTime) {
		if (canvas != null) {
			this.canvas.setUpdatedDateTime(updatedDateTime);
		}
	}


	@JsonView({ActiveCanvasView.class,OpenCanvasView.class,CanvasDataView.class,CreateOrUpdateWidgetInstanceView.class})
	public CanvasType getCanvasType() {
		if (canvas != null) {
			return this.canvas.getCanvasType();
		}
		return null;
	}

	public void setCanvasType(CanvasType canvasType) {
		if (canvas != null) {
			this.canvas.setCanvasType(canvasType);
		}
	}		

	
	@JsonView({CanvasDataView.class, CreateOrUpdateWidgetInstanceView.class})
	@JsonProperty("canvasLayout")
	public CanvasLayoutView getCanvasLayout() {
		if (canvas!= null && this.canvas.getCanvasLayout() != null) {
			return new CanvasLayoutView(this.canvas.getCanvasLayout());
		}
		return null;
	}

	public void setCanvasLayout(CanvasLayoutView canvasLayout) {
		if (canvas != null && canvasLayout != null) {
			this.canvas.setCanvasLayout(canvasLayout.getCanvasLayout());
		}
	}
	
	/**
	 * This method return the canvas action config as a map of permission name
	 * and boolean value
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@JsonView({CanvasDataView.class, CanvasPalleteView.class })
	@JsonProperty("canvasActionConfig")
	public Map<String, Boolean> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {
		if (canvas !=null && canvas.getActionConfig() != null) {
			Map<Permission, Boolean> actionConfigMap = canvas.getActionConfig()
					.getActionConfig();
			Map<String, Boolean> permissionsMap = new HashMap<String, Boolean>();
			for (Map.Entry<Permission, Boolean> entry : actionConfigMap
					.entrySet()) {

				permissionsMap.put(entry.getKey().getPermissionName(),
						entry.getValue());
			}
			return permissionsMap;
		}

		return null;
	}
	
	public void setActionConfig(PermissionMappedCanvasActionConfig actionConfig){
		if (actionConfig != null) {
			this.setActionConfig(actionConfig);
		}
	}

}