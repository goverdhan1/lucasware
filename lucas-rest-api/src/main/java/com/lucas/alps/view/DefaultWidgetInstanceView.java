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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.CreateOrUpdateWidgetInstanceView;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.entity.ui.widget.EquipmentManagerGridWidget;
import com.lucas.entity.ui.widget.EquipmentTypeGridWidget;
import com.lucas.entity.ui.widget.GroupMaintenanceWidget;
import com.lucas.entity.ui.widget.MessageGridWidget;
import com.lucas.entity.ui.widget.MessageMappingGridWidget;
import com.lucas.entity.ui.widget.MessageSegmentGridWidget;
import com.lucas.entity.ui.widget.PicklineByWaveBarChartWidget;
import com.lucas.entity.ui.widget.SearchGroupGridWidget;
import com.lucas.entity.ui.widget.SearchProductGridWidget;
import com.lucas.entity.ui.widget.SearchUserGridWidget;
import com.lucas.entity.ui.widget.UserCreationFormWidget;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;

@SuppressWarnings("serial")
public class DefaultWidgetInstanceView<T> implements java.io.Serializable {

	DefaultWidgetInstance defaultWidgetInstance;

	

	public DefaultWidgetInstanceView() {
		defaultWidgetInstance = new DefaultWidgetInstance();
	}

	public DefaultWidgetInstanceView(DefaultWidgetInstance defaultWidgetInstance) {
		this.defaultWidgetInstance = defaultWidgetInstance;
	}
	
	@JsonView({CanvasDataView.class, FavoriteCanvasesView.class, CreateOrUpdateWidgetInstanceView.class})
	@JsonProperty("id")
	public Long getWidgetInstanceId() {
		return defaultWidgetInstance.getWidgetinstanceId();
	}

	public void setWidgetInstanceId(Long widgetInstanceId) {
		this.defaultWidgetInstance.setWidgetinstanceId(widgetInstanceId);;
	}

	@JsonIgnore
	public Long getCanvasId() {
      return defaultWidgetInstance.getCanvas().getCanvasId();
	}	

	public void setCanvasId(Long canvasId) {
	      this.defaultWidgetInstance.setCanvasId(canvasId);
		}
	
	@JsonView({CanvasDataView.class, FavoriteCanvasesView.class})
	@SuppressWarnings("unchecked")
	public T getData() {
		
		//return (T)getWidget().getData() ;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setData(T data) {
		//To-do in seperate story
	}

    @JsonView({CanvasDataView.class})
    @JsonProperty("actualViewConfig")
	public ActualViewConfigView getActualViewConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_EMPTY);
		return  om.readValue(defaultWidgetInstance.getActualViewConfig(), ActualViewConfigView.class);
	}	

	public void setActualViewConfig(ActualViewConfigView actualViewConfig) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_EMPTY);
		this.defaultWidgetInstance.setActualViewConfig(om.writeValueAsString(actualViewConfig.getActualViewConfig()));  
	}
	
	    @JsonView({ FavoriteCanvasesView.class})
		@JsonProperty("widgetViewConfig")
		public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException {
		if (defaultWidgetInstance.getWidget() != null) {
			if (defaultWidgetInstance.getWidget().getDefaultViewConfig() != null) {
				ObjectMapper om = new ObjectMapper();
				return om.readValue(defaultWidgetInstance.getWidget()
						.getDefaultViewConfig(), DefaultViewConfigView.class);
			}
		}
		return null;
		}	

		public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfig) throws JsonProcessingException {
			if (defaultWidgetInstance.getWidget() != null) {
				ObjectMapper om = new ObjectMapper();
				this.defaultWidgetInstance.getWidget().setDefaultViewConfig(
						om.writeValueAsString(defaultViewConfig));
			}  
		}

	@JsonView({CanvasDataView.class, FavoriteCanvasesView.class})
	@JsonProperty("widgetDefinition")
    @SuppressWarnings("rawtypes")
	public WidgetView getWidget() {

		if (defaultWidgetInstance.getWidget() != null) {
			if (defaultWidgetInstance.getWidget() instanceof SearchUserGridWidget) {
				return new SearchUserGridWidgetView((SearchUserGridWidget) defaultWidgetInstance.getWidget());
			} else if (defaultWidgetInstance.getWidget() instanceof UserCreationFormWidget) {
				return new UserCreationFormWidgetView((UserCreationFormWidget) defaultWidgetInstance.getWidget());
			} else if (defaultWidgetInstance.getWidget() instanceof PicklineByWaveBarChartWidget) {
				return new PicklineByWaveBarChartWidgetView((PicklineByWaveBarChartWidget) defaultWidgetInstance.getWidget());
			} else if (defaultWidgetInstance.getWidget() instanceof GroupMaintenanceWidget) {
				return new GroupMaintenanceWidgetView((GroupMaintenanceWidget) defaultWidgetInstance.getWidget());
			} else if (defaultWidgetInstance.getWidget() instanceof SearchProductGridWidget) {
				return new SearchProductGridWidgetView((SearchProductGridWidget) defaultWidgetInstance.getWidget());
			}else if(defaultWidgetInstance.getWidget() instanceof SearchGroupGridWidget){
                return new SearchGroupGridWidgetView((SearchGroupGridWidget)defaultWidgetInstance.getWidget());
            }else if(defaultWidgetInstance.getWidget() instanceof EquipmentTypeGridWidget){
                return new EquipmentTypeGridWidgetView((EquipmentTypeGridWidget)defaultWidgetInstance.getWidget());
            }else if(defaultWidgetInstance.getWidget() instanceof MessageSegmentGridWidget){
                return new MessageSegmentGridWidgetView((MessageSegmentGridWidget)defaultWidgetInstance.getWidget());
            }else if(defaultWidgetInstance.getWidget() instanceof EquipmentManagerGridWidget){
                return new EquipmentManagerGridWidgetView((EquipmentManagerGridWidget)defaultWidgetInstance.getWidget());
            }else if(defaultWidgetInstance.getWidget() instanceof MessageGridWidget){
                return new MessageGridWidgetView((MessageGridWidget)defaultWidgetInstance.getWidget());
            }else if(defaultWidgetInstance.getWidget() instanceof MessageMappingGridWidget){
                return new MessageMappingGridWidgetView((MessageMappingGridWidget)defaultWidgetInstance.getWidget());
            }
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public void setWidget(AbstractLicensableWidgetView abstractLicensableWidgetView) {
		if (abstractLicensableWidgetView != null) {
			this.defaultWidgetInstance.setWidget(abstractLicensableWidgetView
					.getAbstractLicensableWidget());
		}
	}

	@JsonIgnore
	public DefaultWidgetInstance getDefaultWidgetInstance() {
		return defaultWidgetInstance;
	}

	public void setDefaultWidgetInstance(DefaultWidgetInstance defaultWidgetInstance) {
		this.defaultWidgetInstance = defaultWidgetInstance;
	}

	@JsonProperty("canvas")
    @SuppressWarnings("rawtypes")
	public CanvasView getCanvas() {
		return new CanvasView(defaultWidgetInstance.getCanvas());
	}

	@SuppressWarnings("rawtypes")
	public void setCanvas(CanvasView canvasView) {
		this.defaultWidgetInstance.setCanvas(canvasView.getCanvas());
	}
	
	public Boolean getDelete() {
		return defaultWidgetInstance.getDelete();
	}

	public void setDelete(Boolean delete) {
		defaultWidgetInstance.setDelete(delete);
	}

    @JsonView({CanvasDataView.class, FavoriteCanvasesView.class})
	public DataUrlView getDataURL() throws JsonParseException, JsonMappingException, IOException {
		
		if (this.defaultWidgetInstance.getDataURL() != null) {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(this.defaultWidgetInstance.getDataURL(),
					DataUrlView.class);
		}
		return null;
	}

	public void setDataURL(DataUrlView dataURL) throws JsonProcessingException {
		if (dataURL != null) {
			ObjectMapper om = new ObjectMapper();
			this.defaultWidgetInstance.setDataURL(om
					.writeValueAsString(dataURL.getDataURL()));
		}
	}

    @JsonProperty("widgetInteractionConfig")
    @JsonView({CanvasDataView.class})
    public List<Map<String, Object>> getWidgetInteractionConfig() throws JsonParseException, JsonMappingException, IOException {

        if (this.defaultWidgetInstance.getWidgetInteractionConfig() != null) {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(this.defaultWidgetInstance.getWidgetInteractionConfig(),
            		(Class<List<Map<String, Object>>>)(Class<?>)List.class);
        } else {
            // massage as empty array json for the frontend
            Map<String,Object> myMap = new HashMap<String, Object>();

            List<Map<String , Object>> arrayListMap  = new ArrayList<Map<String,Object>>();

            return arrayListMap;
        }
    }

    public void setWidgetInteractionConfig(List<Map<String, Object>> widgetInteractionConfigView) throws JsonProcessingException {
        if (widgetInteractionConfigView != null) {
            ObjectMapper om = new ObjectMapper();
            this.defaultWidgetInstance.setWidgetInteractionConfig(om
                    .writeValueAsString(widgetInteractionConfigView));
        }
    }
}
