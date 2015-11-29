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

package com.lucas.services.ui;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.CanvasLayout;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.GroupMaintenanceWidget;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.User;

import org.apache.commons.collections.MultiMap;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UIService {
	
	/**
	 * This method is used for updating a canvas
	 * @param canvas
	 * @return canvasId
	 * @throws Exception 
	 */
    public Long saveCanvas(Canvas canvas) throws Exception;
    
    public void saveWidget(AbstractLicensableWidget widget);
    
    public void deleteCanvas(Long canvasId);
    
    public void deleteWidget(String widgetName);
    
    public Canvas getCanvasByName(String canvasName);
    
    public AbstractLicensableWidget getWidgetByName(String widgetName);
    
    public List<DefaultWidgetInstance> getWidgetInstanceListForCanvas(Long canvasId, String username) throws JsonParseException, JsonMappingException, IOException, Exception;
    
    public Set<User> getUserSetByCanvasId(Long canvasId);
    
    public MultiMap getWidgetDefinitionsByCategory()throws IOException, Exception;
    	
	public AbstractLicensableWidget getWidgetDefinition(String widgetName) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException;
	/**
	 * This method creates or updates a single widget instance on a canvas.
	 * @param defaultWidgetInstance instance of the widget on a canvas
	 * @return map of String key and values (canvas id, name, widget instance id and the action )
	 */
    public Map<String, String>	createOrUpdateWidgetInstance(DefaultWidgetInstance defaultWidgetInstance);

    public GroupMaintenanceWidget getGroupMaintenanceWidgetDefinition() throws IOException, Exception;
    
    public Long getRecentlyInsertedWidgetInstanceId(String canvasName);

	/**
	 * @param layoutID
	 * @return canvasLayout object
	 */
	CanvasLayout getCanvasLayoutById(Long layoutID);

    /**
     * getCanvasById() provide the functionality for fetching the
     * canvas persistence instance from the database this method
     * accept persistence state canvas id
     *
     * @param canvasId accept the instance of java.lang.Long containing
     *                 the persistence state canvas id.
     * @return the instance of com.lucas.entity.ui.canvas.Canvas
     *          of persistence state .
     * @throws Exception 
     */
    public Canvas getCanvasById(Long canvasId)
            throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;


    /**
     * getHydratedCanvas() provide the functionality for fetching the
     * canvas persistence instance from the database this method
     * accept persistence state canvas id
     *
     * @param canvasId accept the instance of java.lang.Long containing
     *                 the persistence state canvas id.
     * @return the instance of com.lucas.entity.ui.canvas.Canvas
     *          of persistence state .
     * @throws Exception
     */
    public Canvas getHydratedCanvas(Long canvasId) throws Exception;

    /**
     * deleteCanvasById() provide the specification for
     *               deletion the canvas of persistence state
     *               from the database using the com.lucas.dao.ui.CanvasDAO.
     * @param canvasId it accept the com.lucas.entity.ui.canvas.Canvas
     *               instance containing the canvasId
     *               for the basics for deletion form database and forwards
     *               the formal argument to the om.lucas.dao.ui.CanvasDAO.deleteCanvas()
     * @return  the instance of java.lang.Boolean containing the
     *          true is the operation is successful or false if the
     *          operation is unsuccessful.
     */
    public Boolean deleteCanvasById(Long canvasId);
       
     /**
	 * This method returns the canvases details by categories PRIVATE, COMPANY,
	 * LUCAS
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception
	 */
     LinkedHashMap<String, List<Canvas>> getCanvasesByCategories()
             throws JsonParseException,	JsonMappingException, IOException, Exception;

	/**
	 * The method returns the nonhydrated instance of the canvas for the given canvas id.
	 * @param canvasId
	 * @return
	 */
	 Canvas findByCanvasId(Long canvasId);


    /**
     * deleteWidgetFromCanvas() provide the specification for
     *               deletion the widget from the canvas of persistence state
     * @param canvasId it accept the com.lucas.entity.ui.canvas.Canvas
     *               instance containing the canvasId
     *               for the basics for deletion form database and forwards
     * @param widgetInstanceListId it accept the widgetInstanceListId
     *                for the basics for deletion form database and forwards
     *               the formal argument to the om.lucas.dao.ui.CanvasDAO.deleteCanvas()
     * @return  the instance of java.lang.Boolean containing the
     *          true is the operation is successful or false if the
     *          operation is unsuccessful.
     */
    public Boolean deleteWidgetFromCanvas(Long canvasId,Long widgetInstanceListId);


    /**
     * getAllOpenCanvasForUser() provide the specifications for the
     * fetching the all open canvas for the user based on the userId
     * send as the formal argument in the method from the caller
     * this method intern user UserDAOImpl.getOpenCanvasesForUserId()
     * to fetch the data from the database.
     *
     * @param userId accept the java.lang.Long instance containing the
     *               persistence state userId
     * @return the instance of the java.util.List containing the persistence
     *        state OpenUserCanvas instances containing the Canvas information
     */
    public List<OpenUserCanvas> getAllOpenCanvasForUser(final Long userId);

}
