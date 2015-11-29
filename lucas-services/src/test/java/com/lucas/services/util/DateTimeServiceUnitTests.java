package com.lucas.services.util;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.Assert;

import com.lucas.entity.process.AMPM;
import com.lucas.entity.process.Holiday;
import com.lucas.services.admin.HolidayService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={DateUtils.class})
public class DateTimeServiceUnitTests {

	@Mock
	private HolidayService holidayService;
	
	@InjectMocks
	private DateTimeServiceImpl dateTimeService;

	private List<Holiday> holidayList;
	
	@Before
	public void setup(){
		holidayList = new ArrayList<Holiday>();
		Holiday h1 = new Holiday("1", "Indep Day", new DateTime()); 
		Holiday h2 = new Holiday("2", "Divali", new DateTime().minus(1)); 
		Holiday h3 = new Holiday("3", "Indep Day", new DateTime().plus(1));
		holidayList.add(h1);
		holidayList.add(h2);
		holidayList.add(h3);		
	}
		
	@Test
	public void testGetTimeOfDayForNonHoliday(){
		when(holidayService.getHolidays()).thenReturn(holidayList);
		AMPM ampm = dateTimeService.getTimeOfDay(new DateTime());
		Assert.notNull(ampm);
		Assert.isTrue(ampm.equals(AMPM.AM));
	}

	@Test
	public void testGetTimeOfDayWhenNoHolidaysDefined(){
		mockStatic(DateUtils.class);
		when(holidayService.getHolidays()).thenReturn(null);
		dateTimeService.getTimeOfDay(new DateTime());
		verifyStatic(never());
	}

	
	@Test
	public void testGetTimeOfDayWhenNoHolidaysDefinedAndAPMValueIsPassed(){
		mockStatic(DateUtils.class);
		when(holidayService.getHolidays()).thenReturn(null);
		DateTime pmValue = new DateTime(2013, 1, 1, 13, 1, DateTimeZone.getDefault());
		AMPM ampm = dateTimeService.getTimeOfDay(pmValue);
		verifyStatic(never());
		
		Assert.notNull(ampm);
		Assert.isTrue(ampm.equals(AMPM.PM));
	}
}
