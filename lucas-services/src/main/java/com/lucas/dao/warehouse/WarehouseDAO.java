/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.warehouse;

import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.Warehouse;

public interface WarehouseDAO extends GenericDAO<Warehouse> {
	 List<Warehouse> getWarehouseList(Long regionId);
	 List<Warehouse> getWarehouseList(Long regionId, Long warehouseId);

}
