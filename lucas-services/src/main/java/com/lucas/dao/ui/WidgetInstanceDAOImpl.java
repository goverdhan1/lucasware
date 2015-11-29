/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.ui.widgetinstance.WidgetInstance;

@Repository
public class WidgetInstanceDAOImpl  extends ResourceDAO<WidgetInstance> implements WidgetInstanceDAO {
	
	@Override
	public WidgetInstance findByWidgetInstanceById(Long widgetInstanceId) {
		return load(widgetInstanceId);
	}

	@Override
	public List<DefaultWidgetInstance> getWidgetInstancesByCanvasId(Long canvasId) {
		Session session = getSession();
		
				Criteria criteria = session.createCriteria(Canvas.class);
		 		
				criteria.add(Restrictions.eq("canvasId", canvasId));
				
				Canvas c = (Canvas)criteria.uniqueResult();
				
				List<DefaultWidgetInstance>  widgetInstanceList = c.getWidgetInstanceList();
				return widgetInstanceList;
	}
}
