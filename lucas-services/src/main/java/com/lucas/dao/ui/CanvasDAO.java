/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.util.List;
import java.util.Set;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasType;
import com.lucas.entity.user.User;

public interface CanvasDAO extends GenericDAO<Canvas> {
	
	Canvas findByCanvasId(Long canvasId);

	Canvas getCanvasByName(String canvasName);
	


	Set<User> getUserSetByCanvasId(Long canvasId);

    /**
     * getCanvasByType() provide the specification for fetching
     * the Canvas of persistence state on the basic of the canvas type
     *
     * @param canvasType accept the instance of the
     *                   com.lucas.entity.ui.canvas.CanvasType
     *                   Containing the canvas type.
     * @return instance of the com.lucas.entity.ui.canvas.Canvas
     *          from database.
     */
    public Canvas getCanvasByType(CanvasType canvasType);

    /**
        * deleteCanvas() provide the specification for
        *               deletion the canvas of persistence state
        *               from the database.
        * @param canvasId it accept the java.lang.Long
        *               instance containing the canvasId
        *               for the basics for deletion form database.
        * @return  the instance of java.lang.Boolean containing the
        *          true is the operation is successful or false if the
        *          operation is unsuccessful.
        */
      public Boolean deleteCanvas(Long canvasId);
      
    /**
     * The method returns user private canvases with the given username
     * @param username
     * @return
     */
	public List<Canvas> getUserPrivateCanvases(String username);

	/**
	 * The method returns the canvas by a given type
	 * @param company
	 * @return
	 */
	public List<Canvas> getCanvasesByType(CanvasType company);

}
