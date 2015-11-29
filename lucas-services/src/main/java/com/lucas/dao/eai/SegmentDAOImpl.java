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

import javax.swing.text.Segment;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * @author Naveen
 * 
 */
@Repository
public class SegmentDAOImpl extends ResourceDAO<Segments> implements SegmentDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.SegmentDAO#getSegmentListBySearchAndSortCriteria(com
	 * .lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public List<Segments> getSegmentListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {

		Session session = getSession();

		final List<Segments> segmentList = this
				.getBySearchAndSortCriteria(searchAndSortCriteria);
		return segmentList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.SegmentDAO#getTotalCountForSearchAndSortCriteria(com
	 * .lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public Long getTotalCountForSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityType, "entity");
		criteria = this.buildCriteria(session, searchAndSortCriteria);

		// reset the pagination to get the total records which was previously
		// built in criteria
		criteria.setFirstResult(0);
		criteria.setMaxResults(Integer.MAX_VALUE);
		criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
		Long totalResult = ((Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).longValue();
		return totalResult;
	}


	/**
	 * @param segmentId
	 * @return
	 */
	public Segments findBySegmentId(Long segmentId){
		return load(segmentId);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.SegmentDAO#getSegment(java.lang.String)
	 */
	@Override
	public Segments getSegment(String segmentName) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(Segments.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);;
		criteria.add(Restrictions.eq("segmentName", segmentName));
		Segments segment = (Segments) criteria.uniqueResult();
		return segment;
	}

}
