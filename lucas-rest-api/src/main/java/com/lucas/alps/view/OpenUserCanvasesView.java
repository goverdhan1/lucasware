/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

 */

package com.lucas.alps.view;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.OpenCanvasView;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;

public class OpenUserCanvasesView implements Serializable{

	private static final long serialVersionUID = 1809860243295519797L;

	private OpenUserCanvas openUserCanvas;

	public OpenUserCanvasesView(OpenUserCanvas openUserCanvas) {
		this.openUserCanvas = openUserCanvas;
	}

	public OpenUserCanvasesView() {
		this.openUserCanvas = new OpenUserCanvas();
	}

    @JsonView({OpenCanvasView.class})
	public CanvasView getCanvas() {
		return new CanvasView(this.openUserCanvas.getCanvas());
	}

	public void setCanvas(Canvas canvas) {
		openUserCanvas.setCanvas(canvas);
	}

    @JsonView({OpenCanvasView.class})
	public Integer getDisplayOrder() {
		return openUserCanvas.getDisplayOrder();
	}

	public void setDisplayOrder(Integer displayOrder) {
		openUserCanvas.setDisplayOrder(displayOrder);
	}

	@JsonIgnore
	public OpenUserCanvas getOpenUserCanvas() {
		return openUserCanvas;
	}

	public void setOpenUserCanvas(OpenUserCanvas openUserCanvas) {
		this.openUserCanvas = openUserCanvas;
	}

	public String getCreatedByUserName() {
		return openUserCanvas.getCreatedByUserName();
	}

	public void setCreatedByUserName(String createdByUserName) {
		openUserCanvas.setCreatedByUserName(createdByUserName);
	}
	

	public String getUpdatedByUserName() {
		return openUserCanvas.getUpdatedByUserName();
	}

	public void setUpdatedByUserName(String updatedByUserName) {
		openUserCanvas.setUpdatedByUserName(updatedByUserName);
	}
	
	public Date getCreatedDateTime() {
        return openUserCanvas.getCreatedDateTime();
    }

    public void setCreatedDateTime(Date createdDateAndTime) {
    	openUserCanvas.setCreatedDateTime(createdDateAndTime);
    }

    public Date getUpdatedDateTime() {
    	return openUserCanvas.getUpdatedDateTime();
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
    	openUserCanvas.setUpdatedDateTime(updatedDateTime);;
    }
	
}
