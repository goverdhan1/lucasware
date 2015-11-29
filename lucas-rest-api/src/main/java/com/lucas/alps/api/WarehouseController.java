/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.Warehouse;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/regions")
public class WarehouseController {
	
	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

	public WarehouseService warehouseService;
	
	@Inject
	public WarehouseController(WarehouseService warehouseService){
		this.warehouseService = warehouseService;
	}

	@RequestMapping(method = RequestMethod.GET, value="/{regionId}")
	public @ResponseBody ApiResponse<List<Warehouse>> getWarehouseList(@PathVariable("regionId") String id){
		List<Warehouse> warehouseList = new ArrayList<Warehouse>();
		ApiResponse<List<Warehouse>> apiResponse = new ApiResponse<List<Warehouse>>();
		
			logger.debug(" WarehouseController [ getWarehouseList ] - Returning List of Warehouses for the regionId {} ",  id);
			warehouseList = warehouseService.getWarehouseList(new Long(id));
			
			if ( ! CollectionUtils.isEmpty(warehouseList) ) { 
				apiResponse.setData(warehouseList);
				
				for(Warehouse w : CollectionsUtilService.nullGuard(warehouseList)) { 
					logger.debug(" WarehouseName - {} " ,w.getWarehouseName());
					logger.debug(" WarehouseId   - {} " ,w.getWarehouseId());
				}
			}
		
		
		return  apiResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{regionId}/warehouses/{warehouseId}")
	public @ResponseBody ApiResponse<List<Warehouse>> getWarehouseList(@PathVariable("regionId") String id,
			@PathVariable("warehouseId") String warehouseId){
		List<Warehouse> warehouseList = new ArrayList<Warehouse>();
		ApiResponse<List<Warehouse>> apiResponse = new ApiResponse<List<Warehouse>>();
		
			logger.debug(" WarehouseController [ getWarehouseList ] - Returning List of Warehouses for the regionId {} ",  id);
			warehouseList = warehouseService.getWarehouseList(new Long(id), new Long(warehouseId));
			
			if ( ! CollectionUtils.isEmpty(warehouseList) ) { 
				apiResponse.setData(warehouseList);
				
				for(Warehouse w : CollectionsUtilService.nullGuard(warehouseList)) { 
					logger.debug(" WarehouseName - {} " ,w.getWarehouseName());
					logger.debug(" WarehouseId   - {} " ,w.getWarehouseId());
				}
			}
		
		
		return  apiResponse;
	}
}