/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.canvas.CanvasLayout;

public interface CanvasLayoutDAO extends GenericDAO<CanvasLayout> {
	
	CanvasLayout getCanvasLayoutById(Long layoutID);
}
