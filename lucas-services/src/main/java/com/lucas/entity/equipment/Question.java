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
package com.lucas.entity.equipment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of referencing the
 * questions into the db.
 */
@Entity
@Table(name = "question")
public class Question implements Serializable{
    
    private Long questionId;
    private String questionDescription;
    private String questionPrompt;
    private String questionFriendlyPrompt;
    private String questionHelp;
    private AnswerType answerType;
    private QuestionType questionType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

   
    @Column(name = "question_description", nullable = false, insertable = true, updatable = true, length = 50)
    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

   
    @Column(name = "question_prompt", nullable = false, insertable = true, updatable = true)
    public String getQuestionPrompt() {
        return questionPrompt;
    }

    public void setQuestionPrompt(String questionPrompt) {
        this.questionPrompt = questionPrompt;
    }

   
    @Column(name = "question_friendly_prompt", nullable = false, insertable = true, updatable = true)
    public String getQuestionFriendlyPrompt() {
        return questionFriendlyPrompt;
    }

    public void setQuestionFriendlyPrompt(String questionFriendlyPrompt) {
        this.questionFriendlyPrompt = questionFriendlyPrompt;
    }

   
    @Column(name = "question_help", nullable = false, insertable = true, updatable = true)
    public String getQuestionHelp() {
        return questionHelp;
    }

    public void setQuestionHelp(String questionHelp) {
        this.questionHelp = questionHelp;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_type_id", referencedColumnName = "answer_type_id", nullable = false)
    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_type_id", referencedColumnName = "question_type_id", nullable = false)
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
