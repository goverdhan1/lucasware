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
package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.SelectedQuestionsAndPreferredAnswerView;
import com.lucas.entity.equipment.AnswerEvaluationRule;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.equipment.Question;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality
 */
public class AnswerEvaluationRuleView {

    private AnswerEvaluationRule answerEvaluationRule;

    public AnswerEvaluationRuleView() {
        this.answerEvaluationRule = new AnswerEvaluationRule();
    }

    public AnswerEvaluationRuleView(AnswerEvaluationRule answerEvaluationRule) {
        this.answerEvaluationRule = answerEvaluationRule;
    }

    @JsonView(SelectedQuestionsAndPreferredAnswerView.class)
    public Long getAnswerEvaluationRuleId() {
        if (answerEvaluationRule != null) {
            return answerEvaluationRule.getAnswerEvaluationRuleId();
        }
        return null;
    }

    public void setAnswerEvaluationRuleId(Long answerEvaluationRuleId) {
        if (answerEvaluationRule != null) {
            this.answerEvaluationRule.setAnswerEvaluationRuleId(answerEvaluationRuleId);
        }
    }

    @JsonView(SelectedQuestionsAndPreferredAnswerView.class)
    public String getOperator() {
        if (answerEvaluationRule != null) {
            return this.answerEvaluationRule.getOperator();
        }
        return null;
    }

    public void setOperator(String operator) {
        if (answerEvaluationRule != null) {
            this.answerEvaluationRule.setOperator(operator);
        }
    }

    @JsonView(SelectedQuestionsAndPreferredAnswerView.class)
    public String getOperand() {
        if (answerEvaluationRule != null) {
            return this.answerEvaluationRule.getOperand();
        }
        return null;
    }

    public void setOperand(String operand) {
        if (answerEvaluationRule != null) {
            this.answerEvaluationRule.setOperand(operand);
        }
    }
    @JsonProperty("questionId")
    @JsonView(SelectedQuestionsAndPreferredAnswerView.class)
    public Long getQuestion() {
        if (answerEvaluationRule != null && answerEvaluationRule.getQuestion() != null) {
            return this.answerEvaluationRule.getQuestion().getQuestionId();
        }
        return null;
    }

    public void setQuestion(Long questionId) {
        if (answerEvaluationRule != null) {
            final Question question=new Question();
            question.setQuestionId(questionId);
            this.answerEvaluationRule.setQuestion(question);
        }
    }

    public AnswerEvaluationRule getAnswerEvaluationRule() {
        return answerEvaluationRule;
    }

    public void setAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        this.answerEvaluationRule = answerEvaluationRule;
    }
}
