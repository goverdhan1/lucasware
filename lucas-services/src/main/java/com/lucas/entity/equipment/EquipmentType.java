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
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of referencing the
 * equipment type into the database.
 */
@Entity
@Table(name = "equipment_type")
public class EquipmentType implements Serializable{

    private Long equipmentTypeId;
    private String equipmentTypeName;
    private String equipmentTypeDescription;
    private boolean requiresCheckList;
    private boolean requiresCertification;
    private boolean shippingContainer;
    private Long reinspectTimeInterval;
    private String equipmentTypeStatus;
    private Long equipmentCount;
    private Long equipmentTypePositions;
    private String createdByUsername;
    private Date createdDateTime;
    private String updatedByUsername;
    private Date updatedDateTime;
    private Long createdByImportId;
    private Long updatedByImportId;
    private EquipmentStyle equipmentStyle;
    private ContainerType containerType;
    private List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypes;
    private List<EquipmentTypePosition> equipmentTypePositionList;
    private List<EquipmentTypeQuestion> equipmentTypeQuestions;
    private List<AnswerEvaluationRule> answerEvaluationRules;
    private List<Equipment> equipments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_type_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    @Column(name = "equipment_type_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    @Column(name = "equipment_type_description", nullable = true, insertable = true, updatable = true, length = 100)
    public String getEquipmentTypeDescription() {
        return equipmentTypeDescription;
    }

    public void setEquipmentTypeDescription(String equipmentTypeDescription) {
        this.equipmentTypeDescription = equipmentTypeDescription;
    }

    @Column(name = "requires_check_list", nullable = false, insertable = true, updatable = true, length = 1)
    public boolean isRequiresCheckList() {
        return requiresCheckList;
    }

    public void setRequiresCheckList(boolean requiresCheckList) {
        this.requiresCheckList = requiresCheckList;
    }

    @Basic
    @Column(name = "requires_certification", nullable = false, insertable = true, updatable = true, length = 1)
    public boolean isRequiresCertification() {
        return requiresCertification;
    }

    public void setRequiresCertification(boolean requiresCertification) {
        this.requiresCertification = requiresCertification;
    }

    @Column(name = "shipping_container", nullable = false, insertable = true, updatable = true, length = 1)
    public boolean isShippingContainer() {
        return shippingContainer;
    }

    public void setShippingContainer(boolean shippingContainer) {
        this.shippingContainer = shippingContainer;
    }

    @Column(name = "reinspect_time_interval", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getReinspectTimeInterval() {
        return reinspectTimeInterval;
    }

    public void setReinspectTimeInterval(Long reinspectTimeInterval) {
        this.reinspectTimeInterval = reinspectTimeInterval;
    }

    @Column(name = "equipment_type_status", nullable = true, insertable = true, updatable = true, length = 25)
    public String getEquipmentTypeStatus() {
        return equipmentTypeStatus;
    }

    public void setEquipmentTypeStatus(String equipmentTypeStatus) {
        this.equipmentTypeStatus = equipmentTypeStatus;
    }

    @Column(name = "equipment_count", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Long equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    @Column(name = "equipment_type_positions", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getEquipmentTypePositions() {
        return equipmentTypePositions;
    }

    public void setEquipmentTypePositions(Long equipmentTypePositions) {
        this.equipmentTypePositions = equipmentTypePositions;
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

    @Column(name = "created_by_import_id", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getCreatedByImportId() {
        return createdByImportId;
    }

    public void setCreatedByImportId(Long createdByImportId) {
        this.createdByImportId = createdByImportId;
    }

    @Column(name = "updated_by_import_id", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getUpdatedByImportId() {
        return updatedByImportId;
    }

    public void setUpdatedByImportId(Long updatedByImportId) {
        this.updatedByImportId = updatedByImportId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_style_id", referencedColumnName = "equipment_style_id", nullable = false)
    public EquipmentStyle getEquipmentStyle() {
        return equipmentStyle;
    }

    public void setEquipmentStyle(EquipmentStyle equipmentStyle) {
        this.equipmentStyle = equipmentStyle;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "container_type_default", referencedColumnName = "container_code", nullable = false)
    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    @OneToMany(mappedBy="equipmentType")
    public List<EquipmentTypePosition> getEquipmentTypePositionList() {
        return equipmentTypePositionList;
    }

    public void setEquipmentTypePosition(EquipmentTypePosition equipmentTypePosition) {
        this.equipmentTypePositionList.add(equipmentTypePosition);
    }

    public void setEquipmentTypePositionList(List<EquipmentTypePosition> equipmentTypePositionList) {
        this.equipmentTypePositionList = equipmentTypePositionList;
    }


    @OneToMany(mappedBy="equipmentType")
    public List<LucasUserCertifiedEquipmentType> getLucasUserCertifiedEquipmentTypes() {
        return lucasUserCertifiedEquipmentTypes;
    }

    public void setLucasUserCertifiedEquipmentTypes(List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypes) {
        this.lucasUserCertifiedEquipmentTypes = lucasUserCertifiedEquipmentTypes;
    }

    @OneToMany(mappedBy="equipmentType")
    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Transient
    public List<EquipmentTypeQuestion> getEquipmentTypeQuestions() {
        return equipmentTypeQuestions;
    }

    public void setEquipmentTypeQuestions(List<EquipmentTypeQuestion> equipmentTypeQuestions) {
        this.equipmentTypeQuestions = equipmentTypeQuestions;
    }

    @Transient
    public List<AnswerEvaluationRule> getAnswerEvaluationRules() {
        return answerEvaluationRules;
    }

    public void setAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        this.answerEvaluationRules.add(answerEvaluationRule);
    }
    public void setAnswerEvaluationRules(List<AnswerEvaluationRule> answerEvaluationRules) {
        this.answerEvaluationRules = answerEvaluationRules;
    }
}
