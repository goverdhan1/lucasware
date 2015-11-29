/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.warehouse;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.dao.warehouse.WarehouseDAO;
import com.lucas.entity.Warehouse;
import com.lucas.exception.LucasRuntimeException;



@RunWith(MockitoJUnitRunner.class)
public class WarehouseServiceUnitTests {

	List<Warehouse> warehouseList;
	private static final long regionid= 1001;
	
	@Mock
	private WarehouseDAO warehouseDAO;
		
	@InjectMocks
	private WarehouseServiceImpl WarehouseService;
	
	@Mock
	private Appender mockAppender;	
	
	private ArgumentCaptor<LoggingEvent> arguments;
	private Warehouse warehouse;
		
	@Before
	public void setup(){
		warehouse = new Warehouse();
		warehouse.setWarehouseId(0l);
		warehouse.setWarehouseName("BestBuy");
		Logger.getRootLogger().addAppender(mockAppender);
		arguments = ArgumentCaptor.forClass(LoggingEvent.class);
		
		warehouseList = new ArrayList<Warehouse>() ;	
	}
	
	@Test
	public void testgetWarehouseList() {
		warehouseList.add(new Warehouse());	
		when(warehouseDAO.getWarehouseList(anyLong(),anyLong())).thenReturn(warehouseList);
		Assert.assertEquals(WarehouseService.getWarehouseList(regionid,warehouse.getWarehouseId()), warehouseList);
	}
	
	@Test
	public void testgetWarehouseListWhenIdIsNull() {
		Assert.assertEquals(WarehouseService.getWarehouseList(null,null), warehouseList);
	}
	@Test
	public void testgetWarehouseListWhenRegionIdAndWarehouseIdIsZero() {
		warehouseList.add(new Warehouse());	
		when(warehouseDAO.getWarehouseList(anyLong(),anyLong())).thenReturn(warehouseList);
		Assert.assertEquals(WarehouseService.getWarehouseList(0L,0L), warehouseList);
	}
	@Test(expected = LucasRuntimeException.class)
	public void updateNullWarehouse(){
		when(warehouseDAO.load(warehouse.getWarehouseId())).thenReturn(null);
		WarehouseService.updateWarehouse(warehouse);
		verify(warehouseDAO, never()).save(any());
	}
	@Test
	public void updateNonNullWarehouse(){
		when(warehouseDAO.load(warehouse.getWarehouseId())).thenReturn(warehouse);
		WarehouseService.updateWarehouse(warehouse);
		verify(warehouseDAO, times(1)).save(any());
	}
	
	@Test
	public void CreateWarehouseWhenWarehouseIsNotNull(){
		WarehouseService.createWarehouse(warehouse);
		verify(warehouseDAO, times(1)).save(any());
	}
	@Test
	public void CreateWarehouseWhenWarehouseIsNull(){
		Warehouse warehouse = null;
		WarehouseService.createWarehouse(warehouse);
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());	
		checkLogMessage("warehouse object is");
	}
	@Test
	public void deleteWarehouseWhenwarehouseIdDoesNotExist(){
		when(warehouseDAO.load(warehouse.getWarehouseId())).thenReturn(null);
		WarehouseService.deleteWarehouse(warehouse);
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not delete any warehouse");
	}
	@Test
	public void deleteWarehouseWhenwarehouseIdExist(){
		when(warehouseDAO.load(warehouse.getWarehouseId())).thenReturn(warehouse);
		WarehouseService.deleteWarehouse(warehouse);
		verify(warehouseDAO, times(1)).delete((Warehouse) any());
	}
	
	private void checkLogMessage(String leadingLogMessageString) {
		LoggingEvent loggingEventValue = (LoggingEvent)arguments.getValue();
		Assert.assertNotNull(loggingEventValue);
		Assert.assertNotNull(loggingEventValue.getMessage());
		String lastLogMessage = (String)loggingEventValue.getMessage();
		Assert.assertTrue(lastLogMessage.startsWith(leadingLogMessageString));
	}
}