package com.lucas.services.ui;

public enum Languages { // languae.values order //tdm

	ENGLISH (10,"English"), 
	FRENCH (20, "French"),
	GERMAN (30, "German");

	private String name;  

	Languages(Integer order, String name) { this.name = name; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
