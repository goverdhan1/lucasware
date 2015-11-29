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

import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/24/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class AnswerTypeView implements Serializable {

    private AnswerType answerType;

    public AnswerTypeView() {
    }

    public AnswerTypeView(AnswerType answerType) {
        this.answerType = answerType;
    }

    @JsonView(QuestionDetailView.class)
    public Long getAnswerTypeId() {
        if (answerType != null) {
            return answerType.getAnswerTypeId();
        }
        return null;
    }

    public void setAnswerTypeId(Long answerTypeId) {
        this.answerType.setAnswerTypeId(answerTypeId);
    }

    @JsonView(QuestionDetailView.class)
    public String getAnswerTypeName() {
        if (answerType != null) {
            return this.answerType.getAnswerTypeName();
        }
        return null;
    }

    public void setAnswerTypeName(String answerTypeName) {
        this.answerType.setAnswerTypeName(answerTypeName);
    }

    public String getAnswerTypeDescription() {
        if (answerType != null) {
            return answerType.getAnswerTypeDescription();
        }
        return null;
    }

    public void setAnswerTypeDescription(String answerTypeDescription) {
        this.answerType.setAnswerTypeDescription(answerTypeDescription);
    }

    public AnswerType getAnswerType() {
        if (answerType != null) {
            return answerType;
        }
        return null;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }
}
