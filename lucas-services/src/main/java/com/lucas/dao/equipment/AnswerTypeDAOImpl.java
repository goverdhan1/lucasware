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
package com.lucas.dao.equipment;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.equipment.AnswerType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Repository("answerTypeDAO")
public class AnswerTypeDAOImpl extends ResourceDAO<AnswerType>
        implements AnswerTypeDAO {

    @Override
    public AnswerType findByAnswerTypeId(Long answerTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(AnswerType.class);
        criteria.add(Restrictions.eq("answerTypeId", answerTypeId));
        final AnswerType answerType=(AnswerType)criteria.uniqueResult();
        return answerType;
    }

    @Override
    public AnswerType findByAnswerTypeName(String answerTypeName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(AnswerType.class);
        criteria.add(Restrictions.eq("answerTypeName", answerTypeName));
        final AnswerType answerType=(AnswerType)criteria.uniqueResult();
        return answerType;
    }
}
