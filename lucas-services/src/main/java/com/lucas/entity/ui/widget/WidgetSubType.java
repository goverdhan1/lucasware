package com.lucas.entity.ui.widget;

public enum WidgetSubType {

	CHART_PIE ("CHART_PIE"), 
	CHART_BAR ("CHART_BAR"),
	CHART_LINE ("CHART_LINE"),
	FORM ("FORM"),
	GRID("GRID");

	private String name;  

	WidgetSubType(String name) { this.name = name; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
