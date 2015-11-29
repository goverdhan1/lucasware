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

package com.lucas.entity.ui.widgetinstance;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.lucas.services.ui.Languages;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;

import java.util.List;

@Embeddable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DefaultWidgetInstance implements WidgetInstance<Object>, java.io.Serializable, Comparable<DefaultWidgetInstance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2990580418404958570L;
	private Long widgetinstanceId;
	private String actualViewConfig;
	private Canvas canvas;
	private AbstractLicensableWidget widget;
	private Boolean delete;
	private String dataURL;
	private String widgetInteractionConfig;

	public DefaultWidgetInstance() {
	}
	
	public DefaultWidgetInstance(String viewConfig, Canvas canvas,
			AbstractLicensableWidget widget) {
		this.actualViewConfig = viewConfig;
		this.canvas = canvas;
		this.widget = widget;
	}
	
	@Transient
	public void setCanvasId(Long canvasId) {
		Canvas canvas = new Canvas();
		canvas.setCanvasId(canvasId);
	}	
	
	@Column(name = "widgetinstance_id")
	public Long getWidgetinstanceId() {
		return widgetinstanceId;
	}

	public void setWidgetinstanceId(Long widgetinstanceId) {
		this.widgetinstanceId = widgetinstanceId;
	}

	@Transient
	@JsonIgnore
	@ManyToOne  
	public Canvas getCanvas() {
		return canvas;
	}	
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}


	@Override
	@Lob
	@Column(name="actual_widget_view_config")	
	public String getActualViewConfig() {
		return actualViewConfig;
	}

	@Override
	public void setActualViewConfig(String actualViewConfig) {
		this.actualViewConfig = actualViewConfig;
	}


	@Override
    @ManyToOne
    @JoinColumn(name = "widget_id") 
	@Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	public AbstractLicensableWidget getWidget() {
		return widget;
	}
	
	@Override
	public void setWidget(AbstractLicensableWidget widget) {
		this.widget = widget;
		
	}

	@Override
	public int compareTo(DefaultWidgetInstance o) {

		return (new CompareToBuilder()).append(this.widgetinstanceId,
				o.widgetinstanceId).toComparison();

	}
	
	@Transient
	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
	
	@Override
	@Lob
	@Column(name = "data_url")	
	public String getDataURL() {
		return dataURL;
	}

	@Override
	public void setDataURL(String dataURL) {
		this.dataURL = dataURL;
	}

    @Override
    @Lob
    @Column(name = "widget_interaction_config")
    public String getWidgetInteractionConfig() {
        return widgetInteractionConfig;
    }

    @Override
    public void setWidgetInteractionConfig(String widgetInteractionConfig) {
        this.widgetInteractionConfig = widgetInteractionConfig;
    }


}
