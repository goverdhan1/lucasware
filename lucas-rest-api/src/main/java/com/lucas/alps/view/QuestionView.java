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

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.QuestionDetailView;
import com.lucas.entity.equipment.Question;


import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/24/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class QuestionView implements Serializable {

    private Question question;

    public QuestionView() {
    }

    public QuestionView(Question question) {
        this.question = question;
    }

    @JsonView(QuestionDetailView.class)
    public Long getQuestionId() {
        if (question != null) {
            return question.getQuestionId();
        }
        return null;
    }

    public void setQuestionId(Long questionId) {
        this.question.setQuestionId(questionId);
    }

    @JsonView(QuestionDetailView.class)
    public String getQuestionDescription() {
        if (question != null) {
            return this.question.getQuestionDescription();
        }
        return null;
    }


    public void setQuestionDescription(String questionDescription) {
        this.question.setQuestionDescription(questionDescription);
    }

    @JsonView(QuestionDetailView.class)
    public String getQuestionPrompt() {
        if (question != null) {
            return this.question.getQuestionPrompt();
        }
        return null;
    }

    public void setQuestionPrompt(String questionPrompt) {
        this.question.setQuestionPrompt(questionPrompt);
    }

    @JsonView(QuestionDetailView.class)
    public String getQuestionFriendlyPrompt() {
        if (question != null) {
            return this.question.getQuestionFriendlyPrompt();
        }
        return null;
    }

    public void setQuestionFriendlyPrompt(String questionFriendlyPrompt) {
        this.question.setQuestionFriendlyPrompt(questionFriendlyPrompt);
    }

    @JsonView(QuestionDetailView.class)
    public String getQuestionHelp() {
        if (question != null) {
            return question.getQuestionHelp();
        }
        return null;
    }

    public void setQuestionHelp(String questionHelp) {
        this.question.setQuestionHelp(questionHelp);
    }

    @JsonView(QuestionDetailView.class)
    public AnswerTypeView getAnswerType() {
        if (question != null) {
            return new AnswerTypeView(this.question.getAnswerType());
        }
        return null;
    }

    public void setAnswerType(AnswerTypeView answerTypeView) {
        if (answerTypeView.getAnswerType() != null) {
            this.question.setAnswerType(answerTypeView.getAnswerType());
        }
    }

    @JsonView(QuestionDetailView.class)
    public QuestionTypeView getQuestionType() {
        if (question != null && question.getQuestionType() != null) {
            return new QuestionTypeView(question.getQuestionType());
        }
        return null;
    }

    public void setQuestionType(QuestionTypeView questionTypeView) {
        if (questionTypeView.getQuestionType() != null) {
            this.question.setQuestionType(questionTypeView.getQuestionType());
        }
    }

    public Question getQuestion() {
        if (question != null) {
            return question;
        }
        return null;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
