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
import com.lucas.entity.equipment.AnswerEvaluationRule;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on AnswerEvaluationRule
 */
public interface AnswerEvaluationRuleDAO extends GenericDAO<AnswerEvaluationRule> {

    /**
     * findAnswerEvaluationRuleByEquipmentTypeId() provide the specification for
     *  getting the AnswerEvaluationRule based on equipmentTypeId
     *
     * @param equipmentTypeId filter param
     * @return list of the AnswerEvaluationRule
     */
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveAnswerEvaluationRule() provide the specification for persisting the AnswerEvaluationRule
     * and return the operation status in boolean true for success and false for failure.
     *
     * @param answerEvaluationRule instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);

    /**
     * updateAnswerEvaluationRule() provide the specification for updating the AnswerEvaluationRule
     * and return the Updated AnswerEvaluationRule instance.
     *
     * @param answerEvaluationRule instance for updating into db
     * @return AnswerEvaluationRule having updated value.
     */
    public AnswerEvaluationRule updateAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);

    /**
     * deleteAnswerEvaluationRuleByEquipmentTypeId() provide the specification for deletion of the
     * AnswerEvaluationRule based on EquipmentTypeId
     * @param equipmentTypeId is accepted as the formal arguments to this method.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId);
}
