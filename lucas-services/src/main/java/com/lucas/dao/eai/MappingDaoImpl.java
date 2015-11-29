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
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * This class is the data access object implementation in the persistence layer
 * providing a way to perform operations on the underlying mapping table
 * 
 */
/**
 * @author Naveen
 *
 */
@Repository
public class MappingDaoImpl extends ResourceDAO<Mapping> implements MappingDao {

	public MappingDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.MappingDao#getMappingById(java.lang.Long)
	 */
	@Override
	public Mapping getMappingById(Long mappingId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Mapping.class);
		criteria.add(Restrictions.eq("mappingId", mappingId));
		return (Mapping) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.MappingDao#getMappingByName(java.lang.String)
	 */
	@Override
	public Mapping getMappingByName(String mappingName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Mapping.class);
		criteria.add(Restrictions.eq("mappingName", mappingName));

		return (Mapping) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.MappingDao#getMappingByEventHandlerId(java.lang.Long)
	 */
	@Override
	public Mapping getInboundMappingByEventHandlerId(Long eventHandlerId) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Mapping.class);

		criteria.createAlias("eaiEventHandlersForInboundMappingId", "eh");

		criteria.add(Restrictions.eq("eh.eventHandlerId", eventHandlerId));

		return (Mapping) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.MappingDao#getMappingByEventHandlerId(java.lang.Long)
	 */
	@Override
	public Mapping getOutboundMappingByEventHandlerId(Long eventHandlerId) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Mapping.class);

		criteria.createAlias("eaiEventHandlersForOutboundMappingId", "eh");

		criteria.add(Restrictions.eq("eh.eventHandlerId", eventHandlerId));

		return (Mapping) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.MappingDao#getMappingListBySearchAndSortCriteria(com
	 * .lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public List<Mapping> getMappingListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Mapping.class);
		criteria = buildCriteria(session, searchAndSortCriteria);

		final List<Mapping> mappingList = criteria.list();
		return mappingList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.MappingDao#getTotalCountForMappingSearchAndSortCriteria
	 * (com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public Long getTotalCountForMappingSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityType, "entity");
		criteria = this.buildCriteria(session, searchAndSortCriteria);

		criteria.setFirstResult(0);
		criteria.setMaxResults(Integer.MAX_VALUE);
		criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
		Long totalResult = ((Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).longValue();
		return totalResult;
	}

 /*
  * (non-Javadoc)
  * @see com.lucas.dao.eai.MappingDao#getMappingsNames()
  */
    @Override
    public List<String> getMappingsNames(){
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Mapping.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("mappingName")));
        return criteria.list();
    }
}
