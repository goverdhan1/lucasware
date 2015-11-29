package com.lucas.entity.ui.viewconfig;

import java.util.List;
import java.util.Map;

public class WidgetOrientation {
	
	private List<Map<String, String>> option;
	private String selected;
	
	public WidgetOrientation(){
		
	}
	
	public WidgetOrientation(List<Map<String, String>> option, String selected){
			
			this.option = option;
			this.selected = selected;
		}

	public List<Map<String, String>> getOption() {
		return option;
	}

	public void setOption(List<Map<String, String>> option) {
		this.option = option;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
}
