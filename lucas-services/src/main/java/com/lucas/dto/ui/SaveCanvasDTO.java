package com.lucas.dto.ui;

import java.util.List;
import java.util.Map;

/**This class represents a data transfer object for transferring the save canvas response to the view.
 * @author Prafull
 *
 */
public class SaveCanvasDTO {

	private List<Map<String,String>> widgets;
	
	private Long canvasId;
	
	private String canvasName;
	
	private String action;
	
	public SaveCanvasDTO() {
	}

	public List<Map<String, String>> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<Map<String, String>> widgets) {
		this.widgets = widgets;
	}

	public Long getCanvasId() {
		return canvasId;
	}

	public void setCanvasId(Long canvasId) {
		this.canvasId = canvasId;
	}

	public String getCanvasName() {
		return canvasName;
	}

	public void setCanvasName(String canvasName) {
		this.canvasName = canvasName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}
