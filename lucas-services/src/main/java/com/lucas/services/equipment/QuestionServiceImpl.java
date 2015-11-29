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
package com.lucas.services.equipment;

import com.lucas.dao.equipment.*;
import com.lucas.entity.equipment.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private QuestionDAO questionDAO;
    private AnswerTypeDAO answerTypeDAO;
    private QuestionTypeDAO questionTypeDAO;
    private EquipmentTypeDAO equipmentTypeDAO;
    private EquipmentTypeQuestionDAO equipmentTypeQuestionDAO;
    private AnswerEvaluationRuleDAO answerEvaluationRuleDAO;

    @Inject
    public QuestionServiceImpl(QuestionDAO questionDAO, AnswerTypeDAO answerTypeDAO
            , QuestionTypeDAO questionTypeDAO, EquipmentTypeDAO equipmentTypeDAO
            , EquipmentTypeQuestionDAO equipmentTypeQuestionDAO, AnswerEvaluationRuleDAO answerEvaluationRuleDAO) {
        this.questionDAO = questionDAO;
        this.answerTypeDAO = answerTypeDAO;
        this.questionTypeDAO = questionTypeDAO;
        this.equipmentTypeDAO = equipmentTypeDAO;
        this.equipmentTypeQuestionDAO = equipmentTypeQuestionDAO;
        this.answerEvaluationRuleDAO = answerEvaluationRuleDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public Question findQuestionById(Long questionId) {
        return this.questionDAO.findQuestionById(questionId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> getQuestionByQuestionTypeId(String questionTypeId) {
        return this.questionDAO.getQuestionByQuestionTypeId(questionTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> getQuestionByQuestionTypeName(String questionTypeName) {
        return this.questionDAO.getQuestionByQuestionTypeName(questionTypeName);
    }

    @Transactional(readOnly = true)
    @Override
    public AnswerType findByAnswerTypeId(Long answerTypeId) {
        return this.findByAnswerTypeId(answerTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public AnswerType findByAnswerTypeName(String answerTypeName) {
        return this.answerTypeDAO.findByAnswerTypeName(answerTypeName);
    }

    @Transactional(readOnly = true)
    @Override
    public QuestionType findByQuestionTypeId(Long questionTypeId) {
        return this.questionTypeDAO.findByQuestionTypeId(questionTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public QuestionType findByQuestionTypeName(String questionTypeName) {
        return this.questionTypeDAO.findByQuestionTypeName(questionTypeName);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypeDAO.findByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeName(String equipmentTypeName) {
        return this.equipmentTypeDAO.findByEquipmentTypeName(equipmentTypeName);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentType(EquipmentType equipmentType) {
        return this.equipmentTypeDAO.saveEquipmentType(equipmentType);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypes(List<EquipmentType> equipmentTypeList) {
        return this.equipmentTypeDAO.saveEquipmentTypes(equipmentTypeList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypeQuestionDAO.findEquipmentTypeQuestionByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypeQuestion(EquipmentTypeQuestion equipmentTypeQuestion) {
        return this.equipmentTypeQuestionDAO.saveEquipmentTypeQuestion(equipmentTypeQuestion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(Long equipmentTypeId) {
        return this.answerEvaluationRuleDAO.findAnswerEvaluationRuleByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        return this.answerEvaluationRuleDAO.saveAnswerEvaluationRule(answerEvaluationRule);
    }

    @Transactional(readOnly = false)
    @Override
    public AnswerEvaluationRule updateAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        return this.answerEvaluationRuleDAO.updateAnswerEvaluationRule(answerEvaluationRule);
    }
}
