/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.process;

import org.joda.time.DateTime;

/**
 * This class is used only for demo purposes by the lucas-process project.
 * @author Pankaj Tandon
 *
 */
public class Holiday {

	private String id;
	private String name;
	private DateTime holidayDate;
	
	public Holiday(String id, String name, DateTime dateTime){
		this.id = id;
		this.name = name;
		this.holidayDate = dateTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DateTime getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(DateTime holidayDate) {
		this.holidayDate = holidayDate;
	}
}
