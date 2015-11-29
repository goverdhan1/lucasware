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
package com.lucas.dao.eai;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.event.EventHandler;

/**
 * @author Prafull
 *
 */
@Repository
public class EventHandlerDAOImpl extends ResourceDAO<EventHandler> implements EventHandlerDAO {


	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.EventHandlerDAO#getInboundFilePatternAndEventName()
	 */
	@Override
	public List<Object[]> getInboundFilePatternAndEventName() {
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(EventHandler.class);
		ProjectionList proList = Projections.projectionList();
		criteria.createAlias("eventEventHandlers", "eeh");
		criteria.createAlias("eeh.event", "event");
		proList.add(Projections.property("event.eventName"), "eventName");
		proList.add(Projections.property("inboundFilePattern"), "inboundFilePattern");
		criteria.setProjection(proList);
		return criteria.list();
	}

	
	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerById(java.lang.Long)
	 */
	@Override
	public EventHandler getEventHandlerById(Long eventHandlerId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EventHandler.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("eventHandlerId", eventHandlerId));
		EventHandler eaiEventHandler = (EventHandler) criteria.uniqueResult();
		return eaiEventHandler;
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(java.lang.String)
	 */
	@Override
	public EventHandler getEventHandlerByName(String eventHandlerName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EventHandler.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("eventHandlerName", eventHandlerName));
		EventHandler eventHandler = (EventHandler) criteria.uniqueResult();
		return eventHandler;
	}


}
