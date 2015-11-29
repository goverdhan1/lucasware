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


import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.equipment.Question;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on Question
 */
public interface QuestionDAO extends GenericDAO<Question> {

    /**
     * finalQuestionById() provide the specification for fetching the Question
     * based on the questionId from the db.
     *
     * @param questionId filter param.
     * @return Question instance having persisting state.
     */
    public Question findQuestionById(final Long questionId);

    /**
     * getQuestionByQuestionTypeId() provide the specification for fetching the list of
     * Question based on the questionTypeId from db.
     *
     * @param questionTypeId filter param.
     * @return list of the Question instances having persisting state.
     */
    public List<Question> getQuestionByQuestionTypeId(String questionTypeId);

    /**
     * getQuestionByQuestionTypeName() provide the specification for fetching the list of
     * Question based on the questionTypeName from db.
     *
     * @param questionTypeName filter param.
     * @return list of the Question instances having persisting state.
     */
    public List<Question> getQuestionByQuestionTypeName(String questionTypeName);
}
