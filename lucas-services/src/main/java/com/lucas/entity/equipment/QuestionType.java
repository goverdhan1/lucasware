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
 * question types into the db.
 */
@Entity
@Table(name = "question_type")
public class QuestionType implements Serializable {

    private Long questionTypeId;
    private String questionTypeName;
    private String questionTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Long questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    @Column(name = "question_type_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    @Column(name = "question_type_description", nullable = true, insertable = true, updatable = true, length = 255)
    public String getQuestionTypeDescription() {
        return questionTypeDescription;
    }

    public void setQuestionTypeDescription(String questionTypeDescription) {
        this.questionTypeDescription = questionTypeDescription;
    }

}
