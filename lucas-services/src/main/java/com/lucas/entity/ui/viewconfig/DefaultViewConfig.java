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
package com.lucas.entity.ui.viewconfig;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;

//@JsonDeserialize(using = MyDeserializer.class)
public class DefaultViewConfig implements ViewConfig {
	private List<Integer> anchor;
	private Long minimumWidth;
	private Long minimumHeight;
	private Long zIndex;
	private List<String> listensForList;
	private WidgetOrientation orientation;
	private Map<String, GridColumn> gridColumns;
	private DateFormat dateFormat;
    private Boolean isMaximized;
    private Map<Integer, String> deviceWidths;
    private AutoRefreshConfig autoRefreshConfig;
	
	public DefaultViewConfig() {
	}

	public DefaultViewConfig(List<Integer> anchorList, Long minimumWidth, Long minimumHeight,
			Long zIndex, WidgetOrientation widgetOrientation) {
		this.anchor = anchorList;
		this.minimumWidth = minimumWidth;
		this.minimumHeight = minimumHeight;
		this.zIndex = zIndex;
		this.orientation = widgetOrientation;
	}

	public DefaultViewConfig(List<Integer> anchorList, Long minimumWidth, Long minimumHeight,
			Long zIndex, WidgetOrientation widgetOrientation, Map<String, GridColumn> gridColumn, DateFormat dateFormat, AutoRefreshConfig autoRefreshConfig) {
		this.anchor = anchorList;
		this.minimumWidth = minimumWidth;
		this.minimumHeight = minimumHeight;
		this.zIndex = zIndex;
		this.orientation = widgetOrientation;
		this.gridColumns = gridColumn;
		this.dateFormat = dateFormat;
        this.autoRefreshConfig = autoRefreshConfig;
	}

	
	@Override
	public Long getMinimumWidth() {
		return minimumWidth;
	}

	@Override
	public void setMinimumWidth(Long minimumWidth) {
		this.minimumWidth = minimumWidth;
	}
	
	@Column(name="default_height")
	public Long getMinimumHeight() {
		return minimumHeight;
	}

	public void setMinimumHeight(Long minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	@Override
	public List<Integer> getAnchor() {
		return anchor;
	}

	@Override
	public void setAnchor(List<Integer> anchor) {
		this.anchor = anchor;
	}

	@Override
	public Long getZindex() {
		return zIndex;
	}
	@Override	
	public void setZindex(Long zIndex) {
		this.zIndex = zIndex;
	}

	public WidgetOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(WidgetOrientation orientation) {
		this.orientation = orientation;
	}

	public Map<String, GridColumn> getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(Map<String, GridColumn> gridColumns) {
		this.gridColumns = gridColumns;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

    public Boolean getIsMaximized() {
        return isMaximized;
    }

    public void setIsMaximized(Boolean isMaximized) {
        this.isMaximized = isMaximized;
    }

	public List<String> getListensForList() {
		return listensForList;
	}

	public void setListensForList(List<String> listensForList) {
		this.listensForList = listensForList;
	}

	public Map<Integer, String> getDeviceWidths() {
		return deviceWidths;
	}

	public void setDeviceWidths(Map<Integer, String> deviceWidths) {
		this.deviceWidths = deviceWidths;
	}

    public AutoRefreshConfig getAutoRefreshConfig() {
        return autoRefreshConfig;
    }
    public void setAutoRefreshConfig(AutoRefreshConfig autoRefreshConfig) {
        this.autoRefreshConfig = autoRefreshConfig;
    }
}
