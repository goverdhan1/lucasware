package com.lucas.services.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	//This is a standard json date format. Please do not change it.
	private static final String dateFormatString = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	private static final String deviceDateFormatString = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String formatDate(Date dateObject){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		return dateFormat.format(dateObject);
		
	}
	
	public static Date parseDate(String dateString) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		return dateFormat.parse(dateString);
	}

    public static LocalDate getDate(final String dateString,final String format){
        final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(format);
        final LocalDate localDate=dateFormatter.parseLocalDate(dateString);
        return localDate;
    }
    
    public static String formatDeviceDate(Date dateObject){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(deviceDateFormatString);
        return dateFormat.format(dateObject);
        
    }

    public static String getDateFormatString() {
        return dateFormatString;
    }

    public static String getDeviceDateFormatString() {
        return deviceDateFormatString;
    }
}
