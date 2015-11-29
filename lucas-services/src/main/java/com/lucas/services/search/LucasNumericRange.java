package com.lucas.services.search;

public class LucasNumericRange {
	
	private Long startOfRange;
	private Long endOfRange;
	
	
	public LucasNumericRange() {
	}
	
	public LucasNumericRange(Long startOfRange, Long endOfRange) {
		super();
		this.startOfRange = startOfRange;
		this.endOfRange = endOfRange;
	}
	
	public Long getStartOfRange() {
		return startOfRange;
	}
	public void setStartOfRange(Long startOfRange) {
		this.startOfRange = startOfRange;
	}
	public Long getEndOfRange() {
		return endOfRange;
	}
	public void setEndOfRange(Long endOfRange) {
		this.endOfRange = endOfRange;
	}
	
	

}
