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
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of referencing the
 * equipment style into the database.
 */
@Entity
@Table(name = "equipment_style")
public class EquipmentStyle implements Serializable{


    private long equipmentStyleId;
    private String equipmentStyleCode;
    private String equipmentStyleName;
   // private List<EquipmentType> equipmentTypes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_style_id", nullable = false, insertable = true, updatable = true, length = 20)
    public long getEquipmentStyleId() {
        return equipmentStyleId;
    }

    public void setEquipmentStyleId(long equipmentStyleId) {
        this.equipmentStyleId = equipmentStyleId;
    }

    @Column(name = "equipment_style_code", nullable = false, insertable = true, updatable = true, length = 50)
    public String getEquipmentStyleCode() {
        return equipmentStyleCode;
    }

    public void setEquipmentStyleCode(String equipmentStyleCode) {
        this.equipmentStyleCode = equipmentStyleCode;
    }

    @Column(name = "equipment_style_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getEquipmentStyleName() {
        return equipmentStyleName;
    }

    public void setEquipmentStyleName(String equipmentStyleName) {
        this.equipmentStyleName = equipmentStyleName;
    }


  /*  @OneToMany(mappedBy="equipmentStyle")
    public List<EquipmentType> getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(List<EquipmentType> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }*/
}
