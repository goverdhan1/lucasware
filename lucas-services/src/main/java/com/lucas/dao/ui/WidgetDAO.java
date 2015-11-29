/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.AssignmentCreationWidget;
import com.lucas.entity.ui.widget.AssignmentManagementWidget;
import com.lucas.entity.ui.widget.PicklineProgressWidget;
import com.lucas.entity.ui.widget.ProductivityByZoneWidget;
import com.lucas.entity.ui.widget.UserProductivityWidget;

public interface WidgetDAO extends GenericDAO<AbstractLicensableWidget> {
	
	AbstractLicensableWidget findByWidgetId(Long widgetId);


	AbstractLicensableWidget getWidgetByName(String widgetName);
	
	List<AbstractLicensableWidget> getAllWidgets();

	List<AbstractLicensableWidget> getAllActiveWidgets();
}
