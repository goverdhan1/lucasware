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

import java.text.ParseException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.product.Product;
import com.lucas.services.search.SearchAndSortCriteria;

@Repository
public class EventDAOImpl extends ResourceDAO<Event> implements EventDAO{

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.event.EaiEventDAO#getEventById(int)
	 */
	@Override
	public Event getEventById(Long eaiEventId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Event.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("eventId",eaiEventId));
		Event eaiEvent = (Event) criteria.uniqueResult();
		return eaiEvent;
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.event.EaiEventDAO#getEventByName(java.lang.String)
	 */
	@Override
	public Event getEventByName(String eaiEventName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Event.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("eventName", eaiEventName));
		Event eaiEvent = (Event) criteria.uniqueResult();
		return eaiEvent;
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.event.EaiEventDAO#getEventByNameList(java.util.List)
	 */
	@Override
	public List<Event> getEventByNameList(List<String> eventNames) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.add(Restrictions.in("eventName", eventNames));
		List<Event> result = criteria.list();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.EventDAO#getEventListBySearchAndSortCriteria(com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public List<Event> getEventListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		
		final List<Event> eventList = this.getBySearchAndSortCriteria(searchAndSortCriteria);
		return eventList;
	}
	
    /* (non-Javadoc)
     * @see com.lucas.dao.eai.EventDAO#getTotalCountForSearchAndSortCriteria(com.lucas.services.search.SearchAndSortCriteria)
     */
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final Session session = getSession();
        final Criteria criteria=super.buildCriteria(session, searchAndSortCriteria);
        criteria.setFirstResult(0);
        criteria.setMaxResults(Integer.MAX_VALUE);
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
        Long totalResult = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
        return totalResult;
    }


}
