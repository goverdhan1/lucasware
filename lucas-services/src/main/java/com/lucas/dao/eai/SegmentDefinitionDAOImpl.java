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

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.message.SegmentDefinition;

/**
 * @author Naveen
 * 
 */
@Repository
public class SegmentDefinitionDAOImpl extends ResourceDAO<SegmentDefinition>
		implements SegmentDefinitionDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.SegmentDefinitionDAO#findSegmentDefinitionBySegmentId
	 * (java.lang.Long)
	 */
	@Override
	public Set<SegmentDefinition> findSegmentDefinitionBySegmentId(
			Long segmentId) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(SegmentDefinition.class);
		criteria.createAlias("eaiSegments", "s");
		criteria.add(Restrictions.eq("s.segmentId", segmentId));
		Set<SegmentDefinition> segmentDefinitions = (Set<SegmentDefinition>) criteria
				.list();
		return segmentDefinitions;
	}

}
