/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "region")
public class Region implements java.io.Serializable {

	private static final long serialVersionUID = -7149275107489985126L;
	
	private Long regionId;
	
	private String regionName;
	
	private List<Warehouse> warehouseList = new ArrayList<Warehouse>();

	public Region() {
	}

	public Region(Long regionId) {
		this.regionId = regionId;
	}

	public Region(Long regionId, String regionName, List<Warehouse> warehouseList) {
		this.regionId = regionId;
		this.regionName = regionName;
		this.warehouseList = warehouseList;
	}
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "region_id")
	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	@Column(name = "region_name")
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@OneToMany
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	@JoinColumn(name="region_id")
	public List<Warehouse> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<Warehouse> warehouseList) {
		this.warehouseList = warehouseList;
	}
}