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


package com.lucas.entity.ui.canvas;

import javax.persistence.*;

import com.lucas.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="canvas_layout")
public class CanvasLayout extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long layoutID;
	private Boolean defaultIndicator;
	private Integer displayOrder;	
	private List<Canvas> canvas;

	public CanvasLayout() {
	}

	public CanvasLayout(Long layoutID, Boolean defaultIndicator, int displayOrder) {
		super();
		this.layoutID = layoutID;
		this.defaultIndicator = defaultIndicator;
		this.displayOrder = displayOrder;
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "layout_id")
	public Long getLayoutID() {
		return layoutID;
	}

	public void setLayoutID(Long layoutID) {
		this.layoutID = layoutID;
	}

	@Column(name="default_indicator", nullable=false)
	public Boolean getDefaultIndicator() {
		return defaultIndicator;
	}

	public void setDefaultIndicator(Boolean defaultIndicator) {
		this.defaultIndicator = defaultIndicator;
	}

	@Column(name="display_order", length=2, unique=true ,nullable=true)
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@OneToMany(mappedBy="canvasLayout")
	public List<Canvas> getCanvas() {
		return canvas;
	}

	public void setCanvas(List<Canvas> canvas) {
		this.canvas = canvas;
	}

}