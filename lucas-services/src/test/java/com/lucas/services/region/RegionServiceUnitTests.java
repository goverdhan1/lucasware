/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.region;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.dao.region.RegionDAO;
import com.lucas.entity.Region;
import com.lucas.exception.LucasRuntimeException;

/**
 * 
 * @author venkat
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RegionServiceUnitTests {

	private static final long regionId= 1005L;
	
	private Region region;
	
	@Mock
	private RegionDAO regionDAO;
		
	@InjectMocks
	private RegionServiceImpl regionService;
	
	@Mock
	private Appender mockAppender;	
	
	private ArgumentCaptor<LoggingEvent> arguments;
	
	

	@Before
	public void setup(){
		
		region = new Region();
		
		Logger.getRootLogger().addAppender(mockAppender);
		arguments = ArgumentCaptor.forClass(LoggingEvent.class);
	}
	
	@Test
	public void testFindRegionByRegionId() {
		when(regionDAO.findByRegionId(anyLong())).thenReturn(region);
		Assert.assertEquals(regionService.findByRegionId(regionId).getRegionId(), region.getRegionId());
		
		verify(regionDAO, atLeastOnce()).findByRegionId(anyLong());
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Region with Id");
	}
	
	@Test
	public void testFindRegionByInvalidRegionId() {
		regionService.findByRegionId(null);
		
		verify(regionDAO, never()).findByRegionId(anyLong());
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not create");
	}
	
	@Test
	public void testCreateRegionForValidRegion() {
		when(regionDAO.save(anyObject())).thenReturn(region);
		regionService.createRegion(region);
		
		verify(regionDAO, atLeastOnce()).save(anyObject());
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Region with Id");
	}
	
	@Test
	public void testCreateRegionForInValidRegion() {
		
		regionService.createRegion(null);
		
		verify(regionDAO, never()).save(anyObject());
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not create");
	}
	
	@Test
	public void testUpdateRegionWhenRegionIsNull() {
		regionService.updateRegion(null);
		
		verify(regionDAO, never()).save(anyObject());
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not create");
	}
	
	@Test(expected = LucasRuntimeException.class)
	public void testUpdateRegionWhenRegionDoesNotExist() {
		region.setRegionName(StringUtils.EMPTY);
		regionService.updateRegion(region);
		
		verify(regionDAO, times(1)).load(anyObject());
	}
	
	@Test
	public void testDeleteRegionWhenRegionIsNull() {
		Region deleteRegion = null;
		regionService.deleteRegion(deleteRegion);
		verify(regionDAO, never()).delete(deleteRegion);
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not delete");
	}
	
	@Test
	public void testDeleteRegionWhenRegionIdIsNull() {
		regionService.deleteRegion(region);
		verify(regionDAO, never()).delete(region);
		
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Did not delete");
	}
	
	@After
    public void removeAppender() {
        Logger.getRootLogger().removeAppender(mockAppender);
    }
	
	private void checkLogMessage(String leadingLogMessageString) {
		LoggingEvent loggingEventValue = (LoggingEvent)arguments.getValue();
		Assert.assertNotNull(loggingEventValue);
		Assert.assertNotNull(loggingEventValue.getMessage());
		String lastLogMessage = (String)loggingEventValue.getMessage();
		Assert.assertTrue(lastLogMessage.startsWith(leadingLogMessageString));
	}
}