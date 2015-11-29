package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.alps.viewtype.widget.PicklineByWaveBarChartWidgetDetailsView;
import com.lucas.entity.ui.viewconfig.WidgetOrientation;

import java.util.List;
import java.util.Map;

public class WidgetOrientationView {
	
	private WidgetOrientation widgetOrientation;

	public WidgetOrientationView() {
		this.widgetOrientation = new WidgetOrientation();
	}

	public WidgetOrientationView(WidgetOrientation widgetOrientation) {
		this.widgetOrientation = widgetOrientation;
	}
	
	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class, PicklineByWaveBarChartWidgetDetailsView.class})
	public List<Map<String, String>> getOption() {
		
		if (widgetOrientation != null) {
			return widgetOrientation.getOption();
		}
		return null;
	}

	public void setOption(List<Map<String, String>> option) {
		
		if (widgetOrientation != null) {
			widgetOrientation.setOption(option);
		}
	}
	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class, PicklineByWaveBarChartWidgetDetailsView.class})
	public String getSelected() {
		if (widgetOrientation != null) {
			
			return widgetOrientation.getSelected();
		}
		return null;
	}

	public void setSelected(String selected) {
		
		if (widgetOrientation != null) {
			
			widgetOrientation.setSelected(selected);
		}
	}

	public WidgetOrientation getWidgetOrientation() {
		return widgetOrientation;
	}

	public void setWidgetOrientation(WidgetOrientation widgetOrientation) {
		this.widgetOrientation = widgetOrientation;
	} 
	
	
}
