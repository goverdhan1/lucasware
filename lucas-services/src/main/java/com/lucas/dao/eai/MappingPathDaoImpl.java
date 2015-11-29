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
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.mapping.MappingPath;

/**
 * This class is the data access object implementation in the persistence layer
 * providing a way to perform operations on the underlying mapping path table
 * 
 * @author Prafull
 * 
 */
@Repository
public class MappingPathDaoImpl extends ResourceDAO<MappingPath> implements MappingPathDao {

	/**
	 * 
	 */
	public MappingPathDaoImpl() {
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.MappingPathDao#getMappingPathByMappingName(java.lang.String)
	 */
	@Override
	public List<MappingPath> getMappingPathByMappingName(String mappingName) {
		
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(MappingPath.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		criteria.createAlias("mapping", "m");
		criteria.add(Restrictions.eq("m.mappingName", mappingName));
		
		return criteria.list();
		
	}
	
	
	

}
