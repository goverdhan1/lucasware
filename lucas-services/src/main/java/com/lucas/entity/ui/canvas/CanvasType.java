package com.lucas.entity.ui.canvas;

public enum CanvasType {
	
	COMPANY ("company"), 
	PRIVATE ("private"),
    LUCAS ("lucas");

	private String name;  

	CanvasType(String name) { this.name = name; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
