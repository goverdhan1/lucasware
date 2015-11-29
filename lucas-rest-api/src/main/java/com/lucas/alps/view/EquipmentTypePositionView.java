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
import com.lucas.alps.viewtype.EquipmentTypeDetailView;
import com.lucas.entity.equipment.EquipmentTypePosition;

import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/29/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality ....
 */
public class EquipmentTypePositionView implements Serializable {

    private EquipmentTypePosition equipmentTypePosition;

    public EquipmentTypePositionView() {
        equipmentTypePosition = new EquipmentTypePosition();
    }

    public EquipmentTypePositionView(EquipmentTypePosition equipmentTypePosition) {
        this.equipmentTypePosition = equipmentTypePosition;
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getEquipmentTypePositionId() {
        if (this.equipmentTypePosition != null) {
            return this.equipmentTypePosition.getEquipmentTypePositionId();
        }
        return null;
    }


    public void setEquipmentTypePositionId(Long equipmentTypePositionId) {
        if (this.equipmentTypePosition != null) {
            this.equipmentTypePosition.setEquipmentTypePositionId(equipmentTypePositionId);
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getEquipmentTypePositionNbr() {
        if (this.equipmentTypePosition != null) {
            return this.equipmentTypePosition.getEquipmentTypePositionNbr();
        }
        return null;
    }

    public void setEquipmentTypePositionNbr(Long equipmentTypePositionNbr) {
        if (this.equipmentTypePosition != null) {
            this.equipmentTypePosition.setEquipmentTypePositionNbr(equipmentTypePositionNbr);
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getXPosition() {
        if (this.equipmentTypePosition != null) {
            return this.equipmentTypePosition.getXPosition();
        }
        return null;
    }

    public void setXPosition(Long xPosition) {
        if (this.equipmentTypePosition != null) {
            this.equipmentTypePosition.setXPosition(xPosition);
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getYPosition() {
        if (this.equipmentTypePosition != null) {
            return this.equipmentTypePosition.getYPosition();
        }
        return null;
    }

    public void setYPosition(Long yPosition) {
        if (this.equipmentTypePosition != null) {
            this.equipmentTypePosition.setYPosition(yPosition);
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getZPosition() {
        if (this.equipmentTypePosition != null) {
            return this.equipmentTypePosition.getZPosition();
        }
        return null;
    }

    public void setZPosition(Long zPosition) {
        if (this.equipmentTypePosition != null) {
            this.equipmentTypePosition.setZPosition(zPosition);
        }
    }

    public EquipmentTypePosition getEquipmentTypePosition() {
        return equipmentTypePosition;
    }

    public void setEquipmentTypePosition(EquipmentTypePosition equipmentTypePosition) {
        this.equipmentTypePosition = equipmentTypePosition;
    }
}
