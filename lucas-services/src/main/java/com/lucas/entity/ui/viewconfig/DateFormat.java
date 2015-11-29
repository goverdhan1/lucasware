package com.lucas.entity.ui.viewconfig;

import java.util.List;

public class DateFormat {
	
	private String selectedFormat;
	
	private List<String> availableFormats;

	public String getSelectedFormat() {
		return selectedFormat;
	}

	public void setSelectedFormat(String selectedFormat) {
		this.selectedFormat = selectedFormat;
	}

	public List<String> getAvailableFormats() {
		return availableFormats;
	}

	public void setAvailableFormats(List<String> availableFormats) {
		this.availableFormats = availableFormats;
	}
	
	
}
