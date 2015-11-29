/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.util;

import org.joda.time.DateTime;

import com.lucas.entity.process.AMPM;

public interface DateTimeService {
	
	AMPM getTimeOfDay(DateTime dateTime);
	
	String getDateString(DateTime dateTime, String format);

}
