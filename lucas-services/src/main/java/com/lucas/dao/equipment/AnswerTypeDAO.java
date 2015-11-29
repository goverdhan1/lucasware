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
import com.lucas.entity.equipment.AnswerType;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on AnswerType
 */
public interface AnswerTypeDAO extends GenericDAO<AnswerType> {

    /**
     * findByAnswerTypeId() provide the specification for fetching the AnswerType
     * from db based on the answerTypeId.
     *
     * @param answerTypeId filter param
     * @return AnswerType instance having persisting state.
     */
    public AnswerType findByAnswerTypeId(final Long answerTypeId);

    /**
     * findByAnswerTypeName() provide the specification for fetching the AnswerType
     * from db based on the answerTypeName.
     *
     * @param answerTypeName filter param
     * @return AnswerType instance having persisting state.
     */
    public AnswerType findByAnswerTypeName(final String answerTypeName);
}
