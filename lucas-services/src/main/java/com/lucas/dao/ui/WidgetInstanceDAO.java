/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.ui.widgetinstance.WidgetInstance;

public interface WidgetInstanceDAO extends GenericDAO<WidgetInstance> {
	
	WidgetInstance findByWidgetInstanceById(Long widgetInstanceId);

	List<DefaultWidgetInstance> getWidgetInstancesByCanvasId(Long canvasId);

}
