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
 * This Class provide the implementation for the functionality of storing the answer type
 * into the database.
 */
@Entity
@Table(name = "answer_type")
public class AnswerType implements Serializable{

    private Long answerTypeId;
    private String answerTypeName;
    private String answerTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_type_id", nullable = false, insertable = true, updatable = true)
    public Long getAnswerTypeId() {
        return answerTypeId;
    }

    public void setAnswerTypeId(Long answerTypeId) {
        this.answerTypeId = answerTypeId;
    }

    @Column(name = "answer_type_name", nullable = false, insertable = true, updatable = true, length = 500)
    public String getAnswerTypeName() {
        return answerTypeName;
    }

    public void setAnswerTypeName(String answerTypeName) {
        this.answerTypeName = answerTypeName;
    }

    @Column(name = "answer_type_description", nullable = true, insertable = true, updatable = true, length = 255)
    public String getAnswerTypeDescription() {
        return answerTypeDescription;
    }

    public void setAnswerTypeDescription(String answerTypeDescription) {
        this.answerTypeDescription = answerTypeDescription;
    }

}
