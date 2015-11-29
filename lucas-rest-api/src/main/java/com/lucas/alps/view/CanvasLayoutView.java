package com.lucas.alps.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.TimestampDeserializer;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.entity.ui.canvas.CanvasLayout;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 1/26/2015  Time: 7:43 PM
 * This Class provide the implementation for the functionality of..
 */

public class CanvasLayoutView {

    private CanvasLayout canvasLayout;

    public CanvasLayoutView() {
        this.canvasLayout = null;
    }

    public CanvasLayoutView(CanvasLayout canvasLayout) {
        this.canvasLayout = canvasLayout;
    }

    @JsonView({CanvasDataView.class})
    @JsonProperty("canvasLayoutID")
    public Long getLayoutId(){
        return this.canvasLayout.getLayoutID();
    }

    public void setLayoutId(Long canvasLayoutID) {
        this.canvasLayout.setLayoutID(canvasLayoutID);
    }
    
    public Boolean getDefaultIndicator() {
		return canvasLayout.getDefaultIndicator();
	}

	public void setDefaultIndicator(Boolean defaultIndicator) {
		canvasLayout.setDefaultIndicator(defaultIndicator);
	}
	
	public Date getCreatedDateTime() {
		return this.canvasLayout.getCreatedDateTime();
	}
	
	@JsonDeserialize(using=TimestampDeserializer.class)
	public void setCreatedDateTime(Date createdDateTime) {
		this.canvasLayout.setCreatedDateTime(createdDateTime);
	}
	
	public String getCreatedByUserName() {
		return this.canvasLayout.getCreatedByUserName();
	}
	public void setCreatedByUserName(String createdByUserName) {
		this.canvasLayout.setCreatedByUserName(createdByUserName);
	}
	
    public CanvasLayout getCanvasLayout() {
        return canvasLayout;
    }

    public void setCanvasLayout(CanvasLayout canvasLayout) {
        this.canvasLayout = canvasLayout;
    }
}
