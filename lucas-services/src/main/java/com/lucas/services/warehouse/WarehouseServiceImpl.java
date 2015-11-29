/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.warehouse;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.warehouse.WarehouseDAO;
import com.lucas.entity.Warehouse;
import com.lucas.exception.LucasRuntimeException;


/**
 * 
 * @author sparupati
 *
 */
@Transactional(readOnly = false)
@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

	private static Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
	private final WarehouseDAO warehouseDAO;
	
	@Inject
	public WarehouseServiceImpl(WarehouseDAO warehouseDAO){
		this.warehouseDAO = warehouseDAO;
	}

	@Transactional(readOnly = true)
	@Override
	@PreAuthorize("hasRole('view-report-productivity')")
	public List<Warehouse> getWarehouseList(Long regionId) {
		return warehouseDAO.getWarehouseList(regionId);	
	}
	
	@Transactional(readOnly = true)
	@Override
	@PreAuthorize("hasRole('view-report-productivity')")
	public List<Warehouse> getWarehouseList(Long regionId, Long warehouseId) {
		return warehouseDAO.getWarehouseList(regionId,warehouseId);	
	}

	@Override
	@PreAuthorize("hasRole('create-assignment')")
	public Warehouse createWarehouse(Warehouse warehouse) {
		if( null != warehouse ){
			warehouse = warehouseDAO.save(warehouse);
		}
		else{
			logger.debug("warehouse object is null");
		}
		return warehouse;
	}
	
	@Override
	@PreAuthorize("hasRole('configure-location')")
	public void updateWarehouse(Warehouse warehouse) throws LucasRuntimeException {
		
		Warehouse retrievedWarehouse = warehouseDAO.load( warehouse.getWarehouseId());
		
		if ( null != retrievedWarehouse ) {
			warehouseDAO.save(warehouse);	
		} else{
			throw new LucasRuntimeException(String.format("No Warehouse exists with warehousename %s. Cannot update warehouse!", warehouse.getWarehouseName()));
		}	
	}

	@Override
	public void deleteWarehouse(Warehouse warehouse) {
		if ( null != warehouse ) {
			deleteWarehouse(warehouse.getWarehouseId());
		} else {
			logger.debug("Did not delete any warehouse as null warehouseId was passed in.");
		}
	}
	
	@Override
	public void deleteWarehouse(Long warehouseId) {
		if ( null != warehouseId ){
			Warehouse retrievedWarehouse = warehouseDAO.load(warehouseId);
			if ( null != retrievedWarehouse ) {
				warehouseDAO.delete(retrievedWarehouse);
			} else {
				logger.debug(String.format("Did not delete any warehouse as no warehouse with warehouseId %s found.", warehouseId));
			}
		} else {
			logger.debug("Did not delete any warehouse as null warehouseId was passed in.");
		}
	}
}