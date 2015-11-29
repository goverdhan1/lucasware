/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.canvas.CanvasLayout;



@Repository
public class CanvasLayoutDAOImpl extends ResourceDAO<CanvasLayout> implements CanvasLayoutDAO {

	@Override
	public CanvasLayout getCanvasLayoutById(Long layoutID) {
		return load(layoutID);
	}

	
}
