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
 * equipment position into the database.
 */
@Entity
@Table(name = "equipment_position")
public class EquipmentPosition implements Serializable{
    
    private long equipmentPositionId;
    private long equipmentPositionNbr;
    private String equipmentPositionCheckString;
    private String equipmentPositionName;
    private long xPosition;
    private long yPosition;
    private long zPosition;
    private String createdByUsername;
    private Date createdDateTime;
    private String updatedByUsername;
    private Date updatedDateTime;
    private Long containerId;
    private Long permanentContainerId;
    private Equipment equipment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_position_id", nullable = false, insertable = true, updatable = true, length = 20)
    public long getEquipmentPositionId() {
        return equipmentPositionId;
    }

    public void setEquipmentPositionId(long equipmentPositionId) {
        this.equipmentPositionId = equipmentPositionId;
    }

   
    @Column(name = "equipment_position_nbr", nullable = false, insertable = true, updatable = true, length = 20)
    public long getEquipmentPositionNbr() {
        return equipmentPositionNbr;
    }

    public void setEquipmentPositionNbr(long equipmentPositionNbr) {
        this.equipmentPositionNbr = equipmentPositionNbr;
    }

   
    @Column(name = "equipment_position_checkstring", nullable = true, insertable = true, updatable = true, length = 20)
    public String getEquipmentPositionCheckString() {
        return equipmentPositionCheckString;
    }

    public void setEquipmentPositionCheckString(String equipmentPositionCheckString) {
        this.equipmentPositionCheckString = equipmentPositionCheckString;
    }

   
    @Column(name = "equipment_position_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getEquipmentPositionName() {
        return equipmentPositionName;
    }

    public void setEquipmentPositionName(String equipmentPositionName) {
        this.equipmentPositionName = equipmentPositionName;
    }

   
    @Column(name = "x_position", nullable = false, insertable = true, updatable = true, length = 20)
    public long getXPosition() {
        return xPosition;
    }

    public void setXPosition(long xPosition) {
        this.xPosition = xPosition;
    }

   
    @Column(name = "y_position", nullable = false, insertable = true, updatable = true, length = 20)
    public long getYPosition() {
        return yPosition;
    }

    public void setYPosition(long yPosition) {
        this.yPosition = yPosition;
    }

   
    @Column(name = "z_position", nullable = false, insertable = true, updatable = true, length = 20)
    public long getZPosition() {
        return zPosition;
    }

    public void setZPosition(long zPosition) {
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

   
    @Column(name = "container_id", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

   
    @Column(name = "permanent_container_id", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getPermanentContainerId() {
        return permanentContainerId;
    }

    public void setPermanentContainerId(Long permanentContainerId) {
        this.permanentContainerId = permanentContainerId;
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "equipment_id", nullable = false)
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
