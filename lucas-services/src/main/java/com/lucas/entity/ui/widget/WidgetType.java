package com.lucas.entity.ui.widget;

public enum WidgetType {

	GRID ("GRID"), 
	GRAPH_2D ("GRAPH-2D"),
	FORM ("FORM"),
	CONFIGURE_LOCATION ("configure-location");

	private String name;  

	WidgetType(String name) { this.name = name; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
