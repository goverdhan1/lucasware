/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.AssignmentCreationWidget;
import com.lucas.entity.ui.widget.AssignmentManagementWidget;
import com.lucas.entity.ui.widget.PicklineProgressWidget;
import com.lucas.entity.ui.widget.ProductivityByZoneWidget;
import com.lucas.entity.ui.widget.UserProductivityWidget;
import com.lucas.services.search.SearchAndSortCriteria;

@Repository
public class WidgetDAOImpl extends ResourceDAO<AbstractLicensableWidget> implements WidgetDAO {
	
	@Override
	public AbstractLicensableWidget findByWidgetId(Long widgetId) {
		return load(widgetId);
	}

	@Override
	public AbstractLicensableWidget getWidgetByName(String widgetName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(AbstractLicensableWidget.class);

		criteria.add(Restrictions.eq("name", widgetName).ignoreCase());

		AbstractLicensableWidget widget =  (AbstractLicensableWidget) criteria.uniqueResult();

		return widget;
	}

	@Override
	public List<AbstractLicensableWidget> getAllWidgets() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(AbstractLicensableWidget.class);
		return criteria.list();
	}

	@Override
	public List<AbstractLicensableWidget> getAllActiveWidgets() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(AbstractLicensableWidget.class);
		
		criteria.add(Restrictions.eq("active", Boolean.TRUE));
		return criteria.list();
	}
	
	 
}
