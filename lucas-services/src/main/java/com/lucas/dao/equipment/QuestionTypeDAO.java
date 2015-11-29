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
import com.lucas.entity.equipment.QuestionType;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on QuestionType
 */
public interface QuestionTypeDAO extends GenericDAO<QuestionType> {

    /**
     * findByQuestionTypeId() provide the specification for fetching the
     * QuestionType based on the questionTypeId from db.
     *
     * @param questionTypeId filter param.
     * @return QuestionType instance having persisting state.
     */
    public QuestionType findByQuestionTypeId(final Long questionTypeId);

    /**
     * findByQuestionTypeName()  provide the specification for fetching the
     * QuestionType based on the questionTypeName from db.
     *
     * @param questionTypeName filter param.
     * @return QuestionType instance having persisting state.
     */
    public QuestionType findByQuestionTypeName(final String questionTypeName);
}
