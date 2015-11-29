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

import com.lucas.entity.equipment.*;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public interface QuestionService {

    /**
     * finalQuestionById()
     *
     * @param questionId
     * @return
     */
    public Question findQuestionById(final Long questionId);

    /**
     * getQuestionByQuestionTypeId()
     *
     * @param questionTypeId
     * @return
     */
    public List<Question> getQuestionByQuestionTypeId(String questionTypeId);

    /**
     * getQuestionByQuestionTypeName()
     *
     * @param questionTypeName
     * @return
     */
    public List<Question> getQuestionByQuestionTypeName(String questionTypeName);

    /**
     * findByAnswerTypeId()
     *
     * @param answerTypeId
     * @return
     */
    public AnswerType findByAnswerTypeId(final Long answerTypeId);

    /**
     * findByAnswerTypeName()
     *
     * @param answerTypeName
     * @return
     */
    public AnswerType findByAnswerTypeName(final String answerTypeName);

    /**
     * findByQuestionTypeId()
     *
     * @param questionTypeId
     * @return
     */
    public QuestionType findByQuestionTypeId(final Long questionTypeId);

    /**
     * findByQuestionTypeName()
     *
     * @param questionTypeName
     * @return
     */
    public QuestionType findByQuestionTypeName(final String questionTypeName);


    /**
     * findByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public EquipmentType findByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * findByEquipmentTypeName()
     *
     * @param equipmentTypeName
     * @return
     */
    public EquipmentType findByEquipmentTypeName(final String equipmentTypeName);


    /**
     * saveEquipmentType()
     *
     * @param equipmentType
     * @return
     */
    public boolean saveEquipmentType(final EquipmentType equipmentType);

    /**
     * saveEquipmentTypes()
     *
     * @param equipmentTypeList
     * @return
     */
    public boolean saveEquipmentTypes(final List<EquipmentType> equipmentTypeList);

    /**
     * findEquipmentTypeQuestionByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveEquipmentTypeQuestion()
     *
     * @param equipmentTypeQuestion
     * @return
     */
    public boolean saveEquipmentTypeQuestion(final EquipmentTypeQuestion equipmentTypeQuestion);

    /**
     * findAnswerEvaluationRuleByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveAnswerEvaluationRule()
     *
     * @param answerEvaluationRule
     * @return
     */
    public boolean saveAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);

    /**
     * updateAnswerEvaluationRule()
     *
     * @param answerEvaluationRule
     * @return
     */
    public AnswerEvaluationRule updateAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);
}
