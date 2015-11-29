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
import com.lucas.entity.equipment.AnswerType;
import com.lucas.entity.equipment.Question;
import com.lucas.entity.equipment.QuestionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * QuestionService Operations
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceUnitTest {

    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceUnitTest.class);

    @Mock
    private QuestionDAO questionDAO;
    @Mock
    private AnswerTypeDAO answerTypeDAO;
    @Mock
    private QuestionTypeDAO questionTypeDAO;
    @Mock
    private EquipmentTypeDAO equipmentTypeDAO;
    @Mock
    private EquipmentTypeQuestionDAO equipmentTypeQuestionDAO;
    @Mock
    private AnswerEvaluationRuleDAO answerEvaluationRuleDAO;

    @Mock
    private QuestionService questionService;

    private QuestionType questionType;
    private AnswerType answerType;
    private List<Question> questionList;

    @Before
    public void initQuestionServiceFunctionalTest() {
        LOG.info("inti QuestionServiceUnitTest invocation");
        this.questionDAO = mock(QuestionDAO.class);
        this.answerTypeDAO=mock(AnswerTypeDAO.class);
        this.questionTypeDAO=mock(QuestionTypeDAO.class);
        this.equipmentTypeDAO=mock(EquipmentTypeDAO.class);
        this.equipmentTypeQuestionDAO=mock(EquipmentTypeQuestionDAO.class);
        this.answerEvaluationRuleDAO=mock(AnswerEvaluationRuleDAO.class);
        this.questionService=new QuestionServiceImpl(questionDAO, answerTypeDAO
                , questionTypeDAO, equipmentTypeDAO
                , equipmentTypeQuestionDAO, answerEvaluationRuleDAO);
        this.questionType=new QuestionType();
        answerType=new AnswerType();
        this.questionList=new ArrayList<Question>();
    }

    @Test
    public void fetchAllQuestionsForEquipCheck() {
        when(this.questionDAO.getQuestionByQuestionTypeName("EquipCheck")).thenReturn(questionList);
        when(this.questionService.getQuestionByQuestionTypeName("EquipCheck")).thenReturn(questionList);
        this.questionService.getQuestionByQuestionTypeName("EquipCheck");
        verify(questionDAO, atLeastOnce()).getQuestionByQuestionTypeName(anyString());
    }

    @Test
    public void fetchAllQuestionsForTrailerCheck() {
        when(this.questionDAO.getQuestionByQuestionTypeName("TrailerCheck")).thenReturn(questionList);
        when(this.questionService.getQuestionByQuestionTypeName("TrailerCheck")).thenReturn(questionList);
        this.questionService.getQuestionByQuestionTypeName("TrailerCheck");
        verify(questionDAO, atLeastOnce()).getQuestionByQuestionTypeName(anyString());
    }

    @Test
    public void testFindQuestionTypeByNameForEquipCheck() {
        when(this.questionTypeDAO.findByQuestionTypeName("EquipCheck")).thenReturn(questionType);
        when(this.questionService.findByQuestionTypeName("EquipCheck")).thenReturn(questionType);
        this.questionService.findByQuestionTypeName("EquipCheck");
        verify(questionTypeDAO, atLeastOnce()).findByQuestionTypeName(anyString());
    }

    @Test
    public void testFindQuestionTypeByNameForTrailerCheck() {
        when(this.questionTypeDAO.findByQuestionTypeName("TrailerCheck")).thenReturn(questionType);
        when(this.questionService.findByQuestionTypeName("TrailerCheck")).thenReturn(questionType);
        this.questionService.findByQuestionTypeName("TrailerCheck");
        verify(questionTypeDAO, atLeastOnce()).findByQuestionTypeName(anyString());
    }

    @Test
    public void testFindByAnswerType() {
        when(this.answerTypeDAO.findByAnswerTypeName("Confirm")).thenReturn(answerType);
        when(this.questionService.findByAnswerTypeName("Confirm")).thenReturn(answerType);
        this.questionService.findByAnswerTypeName("Confirm");
        verify(answerTypeDAO, atLeastOnce()).findByAnswerTypeName(anyString());
    }
}
