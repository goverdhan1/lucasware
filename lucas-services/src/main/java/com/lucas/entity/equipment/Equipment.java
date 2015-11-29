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

import com.lucas.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of referencing
 * the equipment into the database.
 */
@Entity
@Table(name = "equipment")
public class Equipment implements Serializable{
    
    private Long equipmentId;
    private String equipmentName;
    private String equipmentDescription;
    private String equipmentStatus;
    private Long equipmentPositions;
    private String createdByUsername;
    private Date createdDateTime;
    private String updatedByUsername;
    private Date updatedDateTime;
    private Long createdByImportId;
    private Long updatedByImportId;
    private Long currentAssignmentId;
    private EquipmentType equipmentType;
    private User user;
    private List<EquipmentPosition> equipmentPositionList;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

   
    @Column(name = "equipment_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

   
    @Column(name = "equipment_description", nullable = true, insertable = true, updatable = true, length = 100)
    public String getEquipmentDescription() {
        return equipmentDescription;
    }

    public void setEquipmentDescription(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

   
    @Column(name = "equipment_status", nullable = true, insertable = true, updatable = true, length = 25)
    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

   
    @Column(name = "equipment_positions", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getEquipmentPositions() {
        return equipmentPositions;
    }

    public void setEquipmentPositions(Long equipmentPositions) {
        this.equipmentPositions = equipmentPositions;
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

   
    @Column(name = "current_assignment_id", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getCurrentAssignmentId() {
        return currentAssignmentId;
    }

    public void setCurrentAssignmentId(Long currentAssignmentId) {
        this.currentAssignmentId = currentAssignmentId;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_type_id", referencedColumnName = "equipment_type_id", nullable = false)
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_user_id", referencedColumnName = "user_id", nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy="equipment")
    public List<EquipmentPosition> getEquipmentPositionList() {
        return equipmentPositionList;
    }

    public void setEquipmentPositionList(List<EquipmentPosition> equipmentPositionList) {
        this.equipmentPositionList = equipmentPositionList;
    }


}
