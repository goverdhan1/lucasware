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


package com.lucas.dao.application;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.application.CodeLookup;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CodeLookupDAOImpl extends ResourceDAO<CodeLookup> implements CodeLookupDAO {
	
	private static Logger logger = LoggerFactory.getLogger(CodeLookupDAOImpl.class);

    /**
     * Method to get the code lookup data base on the passed parameter codeName
     *
     * @author DiepLe
     *
     * @param codeName
     * @return List<Object[]>
     */
    @Override
    public List<Object[]>  getCodeLookupData(String codeName) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(CodeLookup.class);
        criteria.add(Restrictions.eq("id.codeName", codeName));
        criteria.addOrder(Order.asc("displayOrder"));

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id.codeValue"), "key");

        // There is no translation codes at the moment so just default to
        // code value at the moment
        proList.add(Projections.property("id.codeValue"), "value"); //if no translation = key
        proList.add(Projections.property("displayOrder"));

        criteria.setProjection(proList);

        List<Object[]> results = criteria.list();

        return results;
    }


    @Override
    public List<String> getCodeLookupValuesBySingleKey(String codeLookupKey) {

        Session session = getSession();

        Criteria criteria = session.createCriteria(CodeLookup.class);
        criteria.add(Restrictions.eq("id.codeName", codeLookupKey));
        criteria.addOrder(Order.asc("displayOrder"));

        ProjectionList proList = Projections.projectionList();

        // There is no translation codes at the moment so just default to
        // code value at the moment
        proList.add(Projections.property("id.codeValue"), "value"); //if no translation = key

        criteria.setProjection(proList);

        List<String> results = criteria.list();

        return results;
    }
}