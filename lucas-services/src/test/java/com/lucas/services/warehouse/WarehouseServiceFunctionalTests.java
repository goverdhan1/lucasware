/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lucas.entity.Region;
import com.lucas.entity.Warehouse;
import com.lucas.services.region.RegionService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager="resourceTransactionManager")
public class WarehouseServiceFunctionalTests extends AbstractSpringFunctionalTests {
	
	@Inject
	private WarehouseService warehouseService;
	@Inject
	private RegionService regionService;
	
	private Region region;
	private Warehouse warehouse;
	
	private List<Warehouse> expectedWarehouseList;
	
	@Before
	public void setup() {
		
		Region insertRegion = new Region();
		insertRegion.setRegionName("");
		region = regionService.createRegion(insertRegion);
		
		warehouse = new Warehouse();
		warehouse.setWarehouseName("");
		warehouse.setRegionId(region.getRegionId());
		//warehouse.setWarehouseId(new Long(5));
		warehouseService.createWarehouse(warehouse);
		
		expectedWarehouseList = new ArrayList<Warehouse>();
		expectedWarehouseList.add(warehouse);
	}
	
	@Test
	public void testWhenValidRegionIdThenReturnWarehouses() {
		List<Warehouse> warehouseList = warehouseService.getWarehouseList(new Long(1),new Long(20));
		Assert.assertNotNull(warehouseList);
	}
	
	@After
	public void destroy() {
		warehouseService.deleteWarehouse(warehouse);
		regionService.deleteRegion(region);
	}
}