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
import java.util.Date;


/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of mapping the
 * equipment type and question entities and store the response of the user.
 */
@Entity
@Table(name = "answer_evaluation_rule")
public class AnswerEvaluationRule implements Serializable{
    
    private Long answerEvaluationRuleId;
    private String operator;
    private String operand;
    private String createdByUsername;
    private Date createdDateTime;
    private String updatedByUsername;
    private Date updatedDateTime;
    private EquipmentType equipmentType;
    private Question question;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_evaluation_rule_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getAnswerEvaluationRuleId() {
        return answerEvaluationRuleId;
    }

    public void setAnswerEvaluationRuleId(Long answerEvaluationRuleId) {
        this.answerEvaluationRuleId = answerEvaluationRuleId;
    }

   
    @Column(name = "operator", nullable = true, insertable = true, updatable = true, length = 20)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

   
    @Column(name = "operand", nullable = true, insertable = true, updatable = true, length = 50)
    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_type_id", referencedColumnName = "equipment_type_id", nullable = false)
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    @Column(name = "created_by_username", nullable = false, insertable = true, updatable = true, length = 100)
    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time", nullable = false, insertable = true, updatable = false)
    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }


    @Column(name = "updated_by_username", nullable = false, insertable = true, updatable = true, length = 100)
    public String getUpdatedByUsername() {
        return updatedByUsername;
    }

    public void setUpdatedByUsername(String updatedByUsername) {
        this.updatedByUsername = updatedByUsername;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date_time", nullable = false, insertable = true, updatable = true)
    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

}
