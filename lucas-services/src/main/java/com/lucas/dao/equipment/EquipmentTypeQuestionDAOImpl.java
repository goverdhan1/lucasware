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
import com.lucas.entity.equipment.EquipmentTypeQuestion;
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
@Repository("equipmentTypeQuestionDAO")
public class EquipmentTypeQuestionDAOImpl extends ResourceDAO<EquipmentTypeQuestion>
        implements EquipmentTypeQuestionDAO {

    @Override
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentTypeQuestion.class);
        criteria.createAlias("equipmentType", "et");
        criteria.add(Restrictions.eq("et.equipmentTypeId", equipmentTypeId));
        final List<EquipmentTypeQuestion> equipmentTypeQuestionList = criteria.list();
        return equipmentTypeQuestionList;
    }

    @Override
    public boolean saveEquipmentTypeQuestion(EquipmentTypeQuestion equipmentTypeQuestion) {
        if (equipmentTypeQuestion != null) {
            super.save(equipmentTypeQuestion);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEquipmentTypeQuestionByEquipmentTypeId(Long equipmentTypeId) {
        try {
            final List<EquipmentTypeQuestion> answerEvaluationRuleList = findEquipmentTypeQuestionByEquipmentTypeId(equipmentTypeId);
            for (EquipmentTypeQuestion equipmentTypeQuestion : answerEvaluationRuleList) {
                equipmentTypeQuestion.setEquipmentType(null);
                equipmentTypeQuestion.setQuestion(null);
                super.delete(equipmentTypeQuestion);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
