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

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.alps.viewtype.widget.*;
import com.lucas.entity.ui.viewconfig.DefaultViewConfig;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.util.CollectionsUtilService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultViewConfigView {

	private DefaultViewConfig defaultViewConfig;

	public DefaultViewConfigView() {
		this.defaultViewConfig = new DefaultViewConfig();
	}

	public DefaultViewConfigView(DefaultViewConfig defaultViewConfig) {
		this.defaultViewConfig = defaultViewConfig;
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,FavoriteCanvasesView.class, UserCreationFormWidgetDetailsView.class
			, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,
			PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public List<Integer> getAnchor() {
		return defaultViewConfig.getAnchor();
	}

	public void setAnchor(List<Integer> anchorPoint) {

		defaultViewConfig.setAnchor(anchorPoint);
	}
	
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,FavoriteCanvasesView.class
			, UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class
			,  PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Long getMinimumWidth() {
		return defaultViewConfig.getMinimumWidth();
	}

	public void setMinimumWidth(Long width) {
		defaultViewConfig.setMinimumWidth(width);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,FavoriteCanvasesView.class
			, UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,
			PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Long getMinimumHeight() {
		return defaultViewConfig.getMinimumHeight();
	}

	public void setMinimumHeight(Long height) {
		defaultViewConfig.setMinimumHeight(height);
	}

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,FavoriteCanvasesView.class,
			UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,
			PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Long getZindex() {
		return defaultViewConfig.getZindex();
	}

	public void setZindex(Long zindex) {
		defaultViewConfig.setZindex(zindex);
	}

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,
    	EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public Boolean getIsMaximized() {
        return defaultViewConfig.getIsMaximized();
    }

    public void setIsMaximized(Boolean isMaximized) {
        this.defaultViewConfig.setIsMaximized(isMaximized);
    }
    
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,
	    SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    , EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Map<String, GridColumnView>  getGridColumns(){
		Map<String, GridColumnView> gridColumns = new HashMap<String, GridColumnView>();
		Map<String, GridColumn> gridColumnsMap = defaultViewConfig.getGridColumns();
		
		if (gridColumnsMap != null) {
			for (Entry<String, GridColumn> entry : CollectionsUtilService
					.nullGuard(gridColumnsMap.entrySet())) {
				gridColumns.put(entry.getKey(),
						new GridColumnView(entry.getValue()));
			}
		}
		return  gridColumns;
	}
	
	public void setGridColumns(Map<String, GridColumnView> gridColums){
		
		if (gridColums !=null && gridColums.size() > 0) {
			Map<String, GridColumn> gridColumnsMap = new HashMap<String, GridColumn>();
			for (Entry<String, GridColumnView> entry : CollectionsUtilService
					.nullGuard(gridColums.entrySet())) {
				gridColumnsMap.put(entry.getKey(), entry.getValue()
						.getGridColumn());
			}
			defaultViewConfig.setGridColumns(gridColumnsMap);
		}
	}
	
	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class, PicklineByWaveBarChartWidgetDetailsView.class})
	public WidgetOrientationView getOrientation(){
		return  new WidgetOrientationView(defaultViewConfig.getOrientation());
	}
	
	public void setOrientation(WidgetOrientationView orientation){
		defaultViewConfig.setOrientation(orientation.getWidgetOrientation());
	}
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,
			PicklineByWaveBarChartWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public DateFormatView getDateFormat() {
		return new DateFormatView(defaultViewConfig.getDateFormat());
	}

	public void setDateFormat(DateFormatView dateFormatView) {
		this.defaultViewConfig.setDateFormat(dateFormatView.getDateFormat()); 
	}
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class
			,SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class
			,MessageSegmentGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public List<String> getListensForList() {
		return defaultViewConfig.getListensForList();
	}

	public void setListensForList(List<String> listensForList) {
		this.defaultViewConfig.setListensForList(listensForList);
	}

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,FavoriteCanvasesView.class,
		UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,
		PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
		, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Map<Integer, String> getDeviceWidths() {
		return defaultViewConfig.getDeviceWidths();
	}

	public void setDeviceWidths(Map<Integer, String> deviceWidths) {
		this.defaultViewConfig.setDeviceWidths(deviceWidths);
	}

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
    	, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public AutoRefreshConfigView getAutoRefreshConfig() {
        return new AutoRefreshConfigView(defaultViewConfig.getAutoRefreshConfig());
    }

    public void setAutoRefreshConfig(AutoRefreshConfigView autoRefreshConfigView) {
        this.defaultViewConfig.setAutoRefreshConfig(autoRefreshConfigView.getAutoRefreshConfig());
    }


}
