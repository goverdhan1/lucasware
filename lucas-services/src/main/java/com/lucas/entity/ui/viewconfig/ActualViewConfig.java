/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.ui.viewconfig;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Embeddable
public class ActualViewConfig implements ViewConfig, java.io.Serializable {

	private List<Integer> anchor;
	private Long minimumWidth;
	private Long minimumHeight;
	private Long zindex;
	private List<String> listensForList;
	private WidgetOrientation orientation;
	private Map<String, GridColumn> gridColumns;
	private DateFormat dateFormat;
    private Boolean isMaximized;
    private Integer position;
    private Map<Integer, String> deviceWidths;
    private AutoRefreshConfig autoRefreshConfig;
	
	public ActualViewConfig() {
	}
	
	public ActualViewConfig(List<Integer> anchorList, Long width, Long height,
			Long zindex, List<String> listensForList) {
		this.anchor = anchorList;
		this.minimumWidth = width;
		this.minimumHeight = height;
		this.zindex = zindex;
		this.listensForList = listensForList; 	
	}
	
	@Override
	@Column(name="actual_width")
	public Long getMinimumWidth() {
		return minimumWidth;
	}
	public void setMinimumWidth(Long minimumWidth) {
		this.minimumWidth = minimumWidth;
	}
	
	@Override
	@Column(name="actual_height")
	public Long getMinimumHeight() {
		return minimumHeight;
	}
	public void setMinimumHeight(Long minimumHeight) {
		this.minimumHeight = minimumHeight;
	}
	

	@Override
	@Column(name="actual_anchor_point")
	public List<Integer> getAnchor() {
		return anchor;
	}

	public void setAnchor(List<Integer> anchor) {
		this.anchor = anchor;
	}

	@Override
	@Column(name="actual_zindex")
	public Long getZindex() {
		return zindex;
	}
	public void setZindex(Long zindex) {
		this.zindex = zindex;
	}

    @Column(name="orientation")
	public WidgetOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(WidgetOrientation orientation) {
		this.orientation = orientation;
	}

    @Column(name="gridColumns")
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

    @Column(name="isMaximized")
    public Boolean getIsMaximized() {
        return isMaximized;
    }

    public void setIsMaximized(Boolean isMaximized) {
        this.isMaximized = isMaximized;
    }


    @Column(name="listensForList")
	@ElementCollection
	public List<String> getListensForList() {
		return listensForList;
	}

	public void setListensForList(List<String> listensForList) {
		this.listensForList = listensForList;
	}

    @Column(name="position")
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

    @Column(name="deviceWidths")
    public Map<Integer, String> getDeviceWidths() {
        return deviceWidths;
    }

    @Column(name="deviceWidths")
    public void setDeviceWidths(Map<Integer, String> deviceWidths) {
        this.deviceWidths = deviceWidths;
    }

    @Column(name="autoRefreshConfig")
    public AutoRefreshConfig getAutoRefreshConfig() {
        return autoRefreshConfig;
    }

    public void setAutoRefreshConfig(AutoRefreshConfig autoRefreshConfig) {
        this.autoRefreshConfig = autoRefreshConfig;
    }
}
