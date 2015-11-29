/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "warehouse")
public class Warehouse implements java.io.Serializable {

	private static final long serialVersionUID = -347803272663643595L;
	
	private Long warehouseId;
	
	private Long regionId;
	
	private String warehouseName;

	public Warehouse() {
	}

	public Warehouse(Long warehouseId, Long regionId, String warehouseName) {
		this.warehouseId = warehouseId;
		this.regionId = regionId;
		this.warehouseName = warehouseName;
	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "warehouse_id")
	public Long getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "warehouse_name")
	public String getWarehouseName() {
		return this.warehouseName;
	}
	
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	@Column(name = "region_id")
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

}