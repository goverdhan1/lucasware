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
import com.lucas.alps.viewtype.EquipmentDetailView;
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentPosition;

import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/29/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality ....
 */
public class EquipmentPositionView implements Serializable {

    private EquipmentPosition equipmentPosition;

    public EquipmentPositionView() {
        equipmentPosition = new EquipmentPosition();
    }

    public EquipmentPositionView(EquipmentPosition equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
    }

    @JsonView({EquipmentDetailView.class})
    public String getEquipmentPositionName() {
        if (this.equipmentPosition != null) {
            return equipmentPosition.getEquipmentPositionName();
        }
        return null;
    }

    public void setEquipmentPositionName(String equipmentPositionName) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setEquipmentPositionName(equipmentPositionName);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public String getEquipmentPositionCheckString() {
        if (this.equipmentPosition != null) {
            return equipmentPosition.getEquipmentPositionCheckString();
        }
        return null;
    }

    public void setEquipmentPositionCheckString(String equipmentPositionCheckString) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setEquipmentPositionCheckString(equipmentPositionCheckString);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getEquipmentPositionNbr() {
        if (this.equipmentPosition != null) {
            return this.equipmentPosition.getEquipmentPositionNbr();
        }
        return null;
    }

    public void setEquipmentPositionNbr(Long equipmentPositionNbr) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setEquipmentPositionNbr(equipmentPositionNbr);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getXPosition() {
        if (this.equipmentPosition != null) {
            return this.equipmentPosition.getXPosition();
        }
        return null;
    }

    public void setXPosition(Long xPosition) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setXPosition(xPosition);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getYPosition() {
        if (this.equipmentPosition != null) {
            return this.equipmentPosition.getYPosition();
        }
        return null;
    }

    public void setYPosition(Long yPosition) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setYPosition(yPosition);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getZPosition() {
        if (this.equipmentPosition != null) {
            return this.equipmentPosition.getZPosition();
        }
        return null;
    }

    public void setZPosition(Long zPosition) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setZPosition(zPosition);
        }
    }

    public Long getContainerId() {
        if (this.equipmentPosition != null) {
            return equipmentPosition.getContainerId();
        }
        return null;
    }

    public void setContainerId(Long containerId) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setContainerId(containerId);
        }
    }

    public Long getPermanentContainerId() {
        if (this.equipmentPosition != null) {
            return equipmentPosition.getPermanentContainerId();
        }
        return null;
    }

    public void setPermanentContainerId(Long permanentContainerId) {
        if (this.equipmentPosition != null) {
            this.equipmentPosition.setPermanentContainerId(permanentContainerId);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getEquipment() {
        if (this.equipmentPosition != null && equipmentPosition.getEquipment() != null) {
            return equipmentPosition.getEquipment().getEquipmentId();
        }
        return null;
    }

    public void setEquipment(Long equipmentId) {
        if (this.equipmentPosition != null) {
            final Equipment equipment = new Equipment();
            equipment.setEquipmentId(equipmentId);
            this.equipmentPosition.setEquipment(equipment);
        }
    }

    public EquipmentPosition getEquipmentTypePosition() {
        return equipmentPosition;
    }

    public void setEquipmentTypePosition(EquipmentPosition equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
    }
}
