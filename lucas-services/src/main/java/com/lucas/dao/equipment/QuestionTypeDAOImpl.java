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
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.QuestionType;
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
@Repository("questionTypeDAO")
public class QuestionTypeDAOImpl extends ResourceDAO<QuestionType>
        implements QuestionTypeDAO {

    @Override
    public QuestionType findByQuestionTypeId(Long questionTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(QuestionType.class);
        criteria.add(Restrictions.eq("questionTypeId", questionTypeId));
        final QuestionType questionType=(QuestionType)criteria.uniqueResult();
        return questionType;
    }

    @Override
    public QuestionType findByQuestionTypeName(String questionTypeName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(QuestionType.class);
        criteria.add(Restrictions.eq("questionTypeName", questionTypeName));
        final QuestionType questionType=(QuestionType)criteria.uniqueResult();
        return questionType;
    }
}
