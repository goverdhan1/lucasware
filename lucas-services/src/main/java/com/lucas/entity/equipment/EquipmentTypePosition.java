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
 * This Class provide the implementation for the functionality of referencing the
 * equipment type position into the db.
 */
@Entity
@Table(name = "equipment_type_position")
public class EquipmentTypePosition implements Serializable{
    
    private Long equipmentTypePositionId;
    private Long equipmentTypePositionNbr;
    private Long xPosition;
    private Long yPosition;
    private Long zPosition;
    private String createdByUsername;
    private Date createdDateTime;
    private String updatedByUsername;
    private Date updatedDateTime;
    private EquipmentType equipmentType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_type_position_id", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getEquipmentTypePositionId() {
        return equipmentTypePositionId;
    }

    public void setEquipmentTypePositionId(Long equipmentTypePositionId) {
        this.equipmentTypePositionId = equipmentTypePositionId;
    }

   
    @Column(name = "equipment_type_position_nbr", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getEquipmentTypePositionNbr() {
        return equipmentTypePositionNbr;
    }

    public void setEquipmentTypePositionNbr(Long equipmentTypePositionNbr) {
        this.equipmentTypePositionNbr = equipmentTypePositionNbr;
    }

   
    @Column(name = "x_position", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getXPosition() {
        return xPosition;
    }

    public void setXPosition(Long xPosition) {
        this.xPosition = xPosition;
    }

   
    @Column(name = "y_position", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getYPosition() {
        return yPosition;
    }

    public void setYPosition(Long yPosition) {
        this.yPosition = yPosition;
    }

   
    @Column(name = "z_position", nullable = false, insertable = true, updatable = true, length = 20)
    public Long getZPosition() {
        return zPosition;
    }

    public void setZPosition(Long zPosition) {
        this.zPosition = zPosition;
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

    @ManyToOne
    @JoinColumn(name = "equipment_type_id", referencedColumnName = "equipment_type_id")
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }
}
