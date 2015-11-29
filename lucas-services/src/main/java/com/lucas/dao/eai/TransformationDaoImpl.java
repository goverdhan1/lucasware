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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;

/**
 * This class is the data access object implementation in the persistence layer
 * providing a way to perform operations on the underlying transformation table
 * 
 */

@Repository
public class TransformationDaoImpl extends ResourceDAO<Transformation>
		implements TransformationDao {

	public TransformationDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.TransformationDao#getTransformationById(java.lang.Long)
	 */
	@Override
	public Transformation getTransformationById(Long transformationId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Transformation.class);
		criteria.add(Restrictions.eq("transformationId", transformationId));
		return (Transformation) criteria.uniqueResult();
	}

}
