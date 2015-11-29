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
import com.lucas.entity.equipment.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Repository("questionDAO")
public class QuestionDAOImpl extends ResourceDAO<Question>
        implements QuestionDAO {

    @Override
    public Question findQuestionById(Long questionId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Question.class);
        criteria.add(Restrictions.eq("questionId", questionId));
        final Question question = (Question) criteria.uniqueResult();
        return question;
    }

    @Override
    public List<Question> getQuestionByQuestionTypeId(String questionTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Question.class);
        criteria.createAlias("questionType", "qt");
        criteria.add(Restrictions.eq("qt.questionTypeId", questionTypeId));
        final List<Question> questionList = criteria.list();
        return questionList;
    }

    @Override
    public List<Question> getQuestionByQuestionTypeName(String questionTypeName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Question.class);
        criteria.createAlias("questionType", "qt");
        criteria.add(Restrictions.eq("qt.questionTypeName", questionTypeName));
        final List<Question> questionList = criteria.list();
        return questionList;
    }
}
