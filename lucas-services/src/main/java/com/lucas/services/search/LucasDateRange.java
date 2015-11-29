package com.lucas.services.search;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;

public class LucasDateRange {
	
	private LocalDate startDateOfRange;
	private LocalDate endDate;
	
	public LucasDateRange() {
	}	
	
	public LucasDateRange(LocalDate startDate, LocalDate endDate) {
		super();
		this.startDateOfRange = startDate;
		this.endDate = endDate;
	}
	
	@JsonDeserialize(using=LocalDateDeserializer.class)
	public LocalDate getEndDate() {
		return endDate;
	}
	
	@JsonSerialize(using=LocalDateSerializer.class)
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@JsonDeserialize(using=LocalDateDeserializer.class)
	public LocalDate getStartDateOfRange() {
		return startDateOfRange;
	}
	
	@JsonSerialize(using=LocalDateSerializer.class)
	public void setStartDateOfRange(LocalDate startDateOfRange) {
		this.startDateOfRange = startDateOfRange;
	}
	

}
