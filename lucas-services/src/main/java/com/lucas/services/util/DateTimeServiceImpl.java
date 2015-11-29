/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.util;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.lucas.entity.process.AMPM;
import com.lucas.entity.process.Holiday;
import com.lucas.services.admin.HolidayService;

@Deprecated
@Service("dateTimeService")
public class DateTimeServiceImpl implements DateTimeService {
	
	private final HolidayService holidayService;
	
	@Inject
	public DateTimeServiceImpl(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	@Override
	public AMPM getTimeOfDay(DateTime dateTime) {
		AMPM ampm = null;
		boolean todayIsAHoliday = false;
		List<Holiday> holidayList = holidayService.getHolidays();
		DateTime today = new DateTime();
		if (holidayList != null){
			for (Holiday holiday : CollectionsUtilService.nullGuard(holidayList)) {
				if (DateUtils.isSameDay(holiday.getHolidayDate().toDate(), today.toDate())){
					ampm = AMPM.AM;
					todayIsAHoliday = true;
					break;
				}
			}
		}
		if (!todayIsAHoliday){
			DateTimeFormatter fmt = DateTimeFormat.forPattern("a");
			String amOrPm = fmt.print(dateTime);
			ampm = AMPM.valueOf(amOrPm);
		}
		
		return ampm;
	}

	@Override
	public String getDateString(DateTime dateTime, String format) {
		// TODO Auto-generated method stub
		return null;
	}

}
