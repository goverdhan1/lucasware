package com.lucas.alps.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.SaveCanvasDetailsView;
import com.lucas.dto.ui.SaveCanvasDTO;

public class SaveCanvasView implements Serializable{
	
	/**This class represents a view for the save canvas operation.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SaveCanvasDTO saveCanvasDTO;
	
	public SaveCanvasView() {
		saveCanvasDTO = new SaveCanvasDTO();
	}
	
	public SaveCanvasView(SaveCanvasDTO saveCanvasDTO) {
		
		this.saveCanvasDTO = saveCanvasDTO;
	}
	
	@JsonView(SaveCanvasDetailsView.class)
	public List<Map<String, String>> getWidgets() {
		return saveCanvasDTO.getWidgets();
	}

	public void setWidgets(List<Map<String, String>> widgets) {
		this.saveCanvasDTO.setWidgets(widgets);;
	}
	@JsonView(SaveCanvasDetailsView.class)
	public Long getCanvasId() {
		return saveCanvasDTO.getCanvasId();
	}

	public void setCanvasId(Long canvasId) {
		this.saveCanvasDTO.setCanvasId(canvasId);;
	}
	@JsonView(SaveCanvasDetailsView.class)
	public String getCanvasName() {
		return saveCanvasDTO.getCanvasName();
	}

	public void setCanvasName(String canvasName) {
		this.saveCanvasDTO.setCanvasName(canvasName);
	}

	@JsonView(SaveCanvasDetailsView.class)
	public String getAction(){
		return saveCanvasDTO.getAction();
	}
	
	public void setAction(String action) {
		this.saveCanvasDTO.setAction(action);
	}

	public SaveCanvasDTO getSaveCanvasDTO() {
		return saveCanvasDTO;
	}

	public void setSaveCanvasDTO(SaveCanvasDTO saveCanvasDTO) {
		this.saveCanvasDTO = saveCanvasDTO;
	}
	
	
}
