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
import com.lucas.entity.equipment.AnswerType;
import com.lucas.entity.equipment.QuestionType;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/24/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class QuestionTypeView implements Serializable {

    private QuestionType questionType;

    public QuestionTypeView() {
    }

    public QuestionTypeView(QuestionType questionType) {
        this.questionType = questionType;
    }

    @JsonView(QuestionDetailView.class)
    public Long getQuestionTypeId() {
        if (questionType != null) {
            return questionType.getQuestionTypeId();
        }
        return null;
    }

    public void setQuestionTypeId(long questionTypeId) {
        this.questionType.setQuestionTypeId(questionTypeId);
    }

    @JsonView(QuestionDetailView.class)
    public String getQuestionTypeName() {
        if (questionType != null) {
            return questionType.getQuestionTypeName();
        }
        return null;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionType.setQuestionTypeName(questionTypeName);
    }

    public String getQuestionTypeDescription() {
        if (questionType != null) {
            return questionType.getQuestionTypeDescription();
        }
        return null;
    }

    public void setQuestionTypeDescription(String questionTypeDescription) {
        this.questionType.setQuestionTypeDescription(questionTypeDescription);
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
