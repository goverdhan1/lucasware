package com.lucas.entity.product;

public enum ResponseType {

	ACKNOWLEDGE ("ACKNOWLEDGE"), 
	ANNOUNCE ("ANNOUNCE");

	private String description;

	private ResponseType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}  


}
