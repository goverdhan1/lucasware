package com.lucas.services.sort;

public enum SortType {

	ASC ("ASC"),
	DESC ("DESC");

	private String type;  

	SortType(String type) { this.type = type; }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	
}
