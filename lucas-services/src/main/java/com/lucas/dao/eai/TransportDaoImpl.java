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
import com.lucas.entity.eai.transport.Transport;

/**
 * This class is the data access object implementation in the persistence layer
 * providing a way to perform operations on the underlying transport table
 * 
 */
@Repository
public class TransportDaoImpl extends ResourceDAO<Transport> implements
		TransportDao {

	public TransportDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.TransportDao#getTransportById(java.lang.String)
	 */
	@Override
	public Transport getTransportById(Long transportId) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(Transport.class);
		criteria.add(Restrictions.eq("transportId", transportId));
		return (Transport) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.TransportDao#getTransportByName(java.lang.String)
	 */
	@Override
	public Transport getTransportByName(String transportName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Transport.class);
		criteria.add(Restrictions.eq("transportName", transportName));

		return (Transport) criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.TransportDao#getAllTransportNames()
	 */
	@Override
	public List<String> getAllTransportNames() throws ParseException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Transport.class);
		criteria.setProjection(Projections.projectionList().add(Projections.property("transportName")));
        return criteria.list();
	}

}
