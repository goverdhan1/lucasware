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


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EquipmentDetailView;
import com.lucas.alps.viewtype.EquipmentTypeDetailView;
import com.lucas.alps.viewtype.SelectedQuestionsAndPreferredAnswerView;
import com.lucas.entity.equipment.*;
import com.lucas.entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class EquipmentView {

    private Equipment equipment;

    public EquipmentView() {
        equipment = new Equipment();
    }

    public EquipmentView(Equipment equipment) {
        if (equipment != null) {
            this.equipment = equipment;
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getEquipmentId() {
        if (equipment != null) {
            return this.equipment.getEquipmentId();
        }
        return null;
    }

    public void setEquipmentId(Long equipmentId) {
        if (equipment != null) {
            this.equipment.setEquipmentId(equipmentId);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public String getEquipmentName() {
        if (equipment != null) {
            return this.equipment.getEquipmentName();
        }
        return null;
    }

    public void setEquipmentName(String equipmentName) {
        if (equipment != null) {
            this.equipment.setEquipmentName(equipmentName);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public String getEquipmentDescription() {
        if (equipment != null) {
            return this.equipment.getEquipmentDescription();
        }
        return null;
    }


    public void setEquipmentDescription(String equipmentDescription) {
        if (equipment != null) {
            this.equipment.setEquipmentDescription(equipmentDescription);
        }
    }

    public String getEquipmentStatus() {
        if (equipment != null) {
            return this.equipment.getEquipmentStatus();
        }
        return null;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        if (equipment != null) {
            this.equipment.setEquipmentStatus(equipmentStatus);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public Long getEquipmentPositions() {
        if (equipment != null) {
            return this.equipment.getEquipmentPositions();
        }
        return null;
    }

    public void setEquipmentPositions(Long equipmentPositions) {
        if (equipment != null) {
            this.equipment.setEquipmentPositions(equipmentPositions);
        }
    }

    public Long getCreatedByImportId() {
        if (equipment != null) {
            return this.equipment.getCreatedByImportId();
        }
        return null;
    }

    public void setCreatedByImportId(Long createdByImportId) {
        if (equipment != null) {
            this.equipment.setCreatedByImportId(createdByImportId);
        }
    }

    public Long getUpdatedByImportId() {
        if (equipment != null) {
            return this.equipment.getUpdatedByImportId();
        }
        return null;
    }

    public void setUpdatedByImportId(Long updatedByImportId) {
        if (equipment != null) {
            this.equipment.setUpdatedByImportId(updatedByImportId);
        }
    }


    public Long getCurrentAssignmentId() {
        if (equipment != null) {
            return this.equipment.getCurrentAssignmentId();
        }
        return null;
    }

    public void setCurrentAssignmentId(Long currentAssignmentId) {
        if (equipment != null) {
            this.equipment.setCurrentAssignmentId(currentAssignmentId);
        }
    }

    @JsonView({EquipmentDetailView.class})
    public String getEquipmentType() {
        if (equipment != null && equipment.getEquipmentType() != null) {
            return this.equipment.getEquipmentType().getEquipmentTypeName();
        }
        return null;
    }

    public void setEquipmentType(String equipmentTypeName) {
        if (equipment != null) {
            final EquipmentType equipmentType = new EquipmentType();
            equipmentType.setEquipmentTypeName(equipmentTypeName);
            this.equipment.setEquipmentType(equipmentType);
        }
    }

    public String getUser() {
        if (equipment != null && equipment.getUser() != null) {
            return this.equipment.getUser().getUserName();
        }
        return null;
    }

    public void setUser(String userName) {
        if (equipment != null) {
            final User user = new User();
            user.setUserName(userName);
            this.equipment.setUser(user);
        }
    }

    // fetching the list of  equipment position and converting into the EquipmentPositionView
    @JsonView({EquipmentDetailView.class})
    public List<EquipmentPositionView> getEquipmentPositionList() {
        if (equipment != null) {
            final List<EquipmentPosition> equipmentPositionList = this.equipment.getEquipmentPositionList();
            if (equipmentPositionList != null && !equipmentPositionList.isEmpty()) {
                final List<EquipmentPositionView> equipmentPositionViews = new ArrayList<EquipmentPositionView>();
                for (EquipmentPosition equipmentPosition : equipmentPositionList) {
                    equipmentPositionViews.add(new EquipmentPositionView(equipmentPosition));
                }
                return equipmentPositionViews;
            }
        }
        return null;
    }

   // receiving the list of EquipmentPositionView and converting to the EquipmentPosition
    public void setEquipmentPositionList(List<EquipmentPositionView> equipmentPositionViews) {
        if (equipment != null && equipmentPositionViews !=null && !equipmentPositionViews.isEmpty()) {
            if (equipmentPositionViews != null && !equipmentPositionViews.isEmpty()) {
                final List<EquipmentPosition> equipmentPositionList = new ArrayList<EquipmentPosition>();
                for (EquipmentPositionView equipmentPositionView : equipmentPositionViews) {
                    equipmentPositionList.add(equipmentPositionView.getEquipmentTypePosition());
                }
                this.equipment.setEquipmentPositionList(equipmentPositionList);
            }
        }
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
