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

import com.lucas.entity.equipment.Question;
import com.lucas.entity.equipment.QuestionType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * QuestionService Operations
 */
@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class QuestionServiceFunctionalTest extends AbstractSpringFunctionalTests {

    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceFunctionalTest.class);

    @Inject
    private QuestionService questionService;

    @Before
    public void initQuestionServiceFunctionalTest() {
        LOG.info("initQuestionServiceFunctionalTest invocation");
    }


    @Transactional
    @Test
    public void fetchAllQuestionsForEquipCheck() {
        final List<Question> questionList = this.questionService.getQuestionByQuestionTypeName("EquipCheck");
        Assert.assertNotNull("Question List for EquipCheck is null", questionList);
        Assert.assertTrue("Question List for EquipCheck is Empty", !questionList.isEmpty());
    }

    @Transactional
    @Test
    public void fetchAllQuestionsForTrailerCheck() {
        final List<Question> questionList = this.questionService.getQuestionByQuestionTypeName("TrailerCheck");
        Assert.assertNotNull("Question List for TrailerCheck is null", questionList);
        Assert.assertTrue("Question List for TrailerCheck is Empty", !questionList.isEmpty());
    }

    @Transactional
    @Test
    public void testFindQuestionTypeByNameForEquipCheck() {
        final QuestionType questionType = this.questionService.findByQuestionTypeName("EquipCheck");
        Assert.assertNotNull("QuestionType for EquipCheck is null", questionType);
    }

    @Transactional
    @Test
    public void testFindQuestionTypeByNameForTrailerCheck() {
        final QuestionType questionType = this.questionService.findByQuestionTypeName("TrailerCheck");
        Assert.assertNotNull("QuestionType for TrailerCheck is null", questionType);
    }

    @Transactional
    @Test
    public void testFindByAnswerType() {
        Assert.assertNotNull("AnswerType is null for Confirm ", this.questionService.findByAnswerTypeName("Confirm"));
        Assert.assertNotNull("AnswerType is null for Digit ", this.questionService.findByAnswerTypeName("Digit"));
        Assert.assertNotNull("AnswerType is null for Digit ", this.questionService.findByAnswerTypeName("Digit"));
        Assert.assertNotNull("AnswerType is null for Alphanum ", this.questionService.findByAnswerTypeName("Alphanum"));
    }
}
