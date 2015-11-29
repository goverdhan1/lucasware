/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.warehouse;

import java.util.List;

import com.lucas.entity.Warehouse;

public interface WarehouseService {
	
    List<Warehouse> getWarehouseList(Long regionId);
	
	List<Warehouse> getWarehouseList(Long regionId, Long warehouseId);
	
	Warehouse createWarehouse(Warehouse warehouse);
	
	void updateWarehouse(Warehouse warehouse);
	
	void deleteWarehouse(Warehouse warehouse);
	
	void deleteWarehouse(Long warehouseId);
	
}