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

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lucas.entity.BaseEntity;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "canvas")
public class Canvas extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2593105067466326108L;

	private Long canvasId;
	private String name;
	private String shortName;
	private CanvasType canvasType;
	private CanvasLayout canvasLayout;
	private Set<User> userSet;
	public List<DefaultWidgetInstance> widgetInstanceList;
	//private Map<String, Boolean> actionConfig;
	private MappedActionConfigurable<Permission, Boolean> actionConfig;
	
	public Canvas() {
	}

	public Canvas(String name) {
		super();
		this.name = name;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "canvas_id")
	public Long getCanvasId() {
		return canvasId;
	}
	public void setCanvasId(Long canvasId) {
		this.canvasId = canvasId;
	}	

	@Column(name="name", unique=true,nullable=false, length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	

	@Column(name="short_name", unique=true,nullable=false, length=30)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name = "layout_id")
	public CanvasLayout getCanvasLayout() {
		return this.canvasLayout;
	}
	public void setCanvasLayout(CanvasLayout canvasLayout) {
		this.canvasLayout = canvasLayout;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name="canvas_type")
	public CanvasType getCanvasType() {
		return canvasType;
	}

	public void setCanvasType(CanvasType canvasType) {
		this.canvasType = canvasType;
	}
	

	@Transient
	public Set<User> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@Embedded
	@ElementCollection
	public List<DefaultWidgetInstance> getWidgetInstanceList() {
		return widgetInstanceList;
	}
	public void setWidgetInstanceList(List<DefaultWidgetInstance> widgetInstanceList) {
		this.widgetInstanceList = widgetInstanceList;
	}

	/**
	 * The method returns the canvas action config
	 * 
	 * @return
	 */
	@Transient
	public MappedActionConfigurable<Permission, Boolean> getActionConfig() {
		return actionConfig;
	}

	/**
	 * This method sets the canvas action config
	 * 
	 * @param actionConfig
	 */
	public void setActionConfig(
			MappedActionConfigurable<Permission, Boolean> actionConfig) {
		this.actionConfig = actionConfig;
	}

	public boolean equals(Object other) {
		if (this == other){
			return true;
		}
		if (!(other instanceof Canvas)){
			return false;
		}
		Canvas castOther = (Canvas) other;
		return new EqualsBuilder().append(name, castOther.name).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("name", name).toString();
	}
	
}