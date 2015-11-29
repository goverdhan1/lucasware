package com.lucas.exception;

public enum Level {
	
	INFO ("INFO"), 
	WARN ("WARN"),
	ERROR ("ERROR");

	private String type;  

	Level(String type) { this.type = type; }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
