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
import com.lucas.entity.equipment.AnswerEvaluationRule;
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
@Repository("answerEvaluationRuleDAO")
public class AnswerEvaluationRuleDAOImpl extends ResourceDAO<AnswerEvaluationRule>
        implements AnswerEvaluationRuleDAO {

    @Override
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(AnswerEvaluationRule.class);
        criteria.createAlias("equipmentType", "et");
        criteria.add(Restrictions.eq("et.equipmentTypeId", equipmentTypeId));
        final List<AnswerEvaluationRule> answerEvaluationRules= criteria.list();
        return answerEvaluationRules;
    }

    @Override
    public boolean saveAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        if (answerEvaluationRule != null) {
            super.save(answerEvaluationRule);
            return true;
        }
        return false;
    }

    @Override
    public AnswerEvaluationRule updateAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        if (answerEvaluationRule != null) {
            super.save(answerEvaluationRule);
            return answerEvaluationRule;
        }
        return answerEvaluationRule;
    }

    @Override
    public boolean deleteAnswerEvaluationRuleByEquipmentTypeId(Long equipmentTypeId) {

        try {
            final List<AnswerEvaluationRule> answerEvaluationRuleList = findAnswerEvaluationRuleByEquipmentTypeId(equipmentTypeId);
            for (AnswerEvaluationRule answerEvaluationRule : answerEvaluationRuleList) {
                answerEvaluationRule.setEquipmentType(null);
                answerEvaluationRule.setQuestion(null);
                super.delete(answerEvaluationRule);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
