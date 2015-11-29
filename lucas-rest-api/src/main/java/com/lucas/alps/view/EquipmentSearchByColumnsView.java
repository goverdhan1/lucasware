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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.EquipmentSearchByColumnsDetailsView;
import com.lucas.alps.viewtype.EquipmentTypeSearchByColumnsDetailsView;
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.entity.user.User;
import com.lucas.services.util.CollectionsUtilService;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for Equipment .
 */
public class EquipmentSearchByColumnsView implements Serializable {

    private List<Equipment> equipment;
    private Long totalRecords;

    public EquipmentSearchByColumnsView() {
    }

    public EquipmentSearchByColumnsView(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public EquipmentSearchByColumnsView(List<Equipment> equipment, Long totalRecords) {
        this.equipment = equipment;
        this.totalRecords = totalRecords;
    }

    @JsonView(EquipmentSearchByColumnsDetailsView.class)
    public Map<String, GridColumnView> getGrid() throws Exception {
        LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
        for (int i = 1; i <= 12; i++) {
            final GridColumn gridColumn = new GridColumn();
            gridView.put(i + "", new GridColumnView(gridColumn));
        }

        for (Equipment equipment : CollectionsUtilService.nullGuard(this.equipment)) {
            gridView.get("1").getValues().add((equipment != null && equipment.getEquipmentName() != null) ? equipment.getEquipmentName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("2").getValues().add((equipment != null && equipment.getEquipmentDescription() != null) ? equipment.getEquipmentDescription() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("3").getValues().add((equipment != null && equipment.getEquipmentType().getEquipmentTypeName() != null && !equipment.getEquipmentType().getEquipmentTypeName().isEmpty()) ? equipment.getEquipmentType().getEquipmentTypeName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("4").getValues().add((equipment != null && equipment.getEquipmentPositions() != null) ? equipment.getEquipmentPositions().toString() : new Long(0).toString());
            gridView.get("5").getValues().add((equipment != null && equipment.getUser() != null) ? equipment.getUser().getUserName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("6").getValues().add((equipment != null && equipment.getUser() != null) ? equipment.getUser().getFirstName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("7").getValues().add((equipment != null && equipment.getUser() != null) ? equipment.getUser().getLastName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("8").getValues().add((equipment != null && equipment.getCurrentAssignmentId() !=null ) ? equipment.getCurrentAssignmentId().toString() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("9").getValues().add((equipment != null && equipment.getEquipmentType() != null && equipment.getEquipmentType().getEquipmentStyle() != null)
                    ? equipment.getEquipmentType().getEquipmentStyle().getEquipmentStyleName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("10").getValues().add((equipment != null &&  equipment.getEquipmentStatus() !=null ) ? equipment.getEquipmentStatus() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("11").getValues().add((equipment != null) ? equipment.getCreatedDateTime().toString() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("12").getValues().add((equipment != null) ? equipment.getUpdatedDateTime().toString() : BaseView.ZERO_LENGTH_STRING);
        }
        return gridView;
    }


    public void setGrid(final Map<String, GridColumnView> gridView)
            throws Exception {
        int pageSize = gridView.get("1").getValues().size();
        this.equipment = new ArrayList<Equipment>();
        for (int index = 0; index < pageSize; index++) {
            final int i = index;
            equipment.add(new Equipment());
            equipment.get(i).setEquipmentName(gridView.get("1").getValues().get(i));
            equipment.get(i).setEquipmentDescription(gridView.get("2").getValues().get(i));
            equipment.get(i).setEquipmentType(new EquipmentType() {
                {
                    setEquipmentTypeName(gridView.get("3").getValues().get(i));
                }
            });
            equipment.get(i).setEquipmentPositions(new Long(gridView.get("4").getValues().get(i)));

            equipment.get(i).setUser(new User() {
                {
                    setUserName(gridView.get("5").getValues().get(i));
                }
            });
            equipment.get(i).setUser(new User() {
                {
                    setFirstName(gridView.get("6").getValues().get(i));
                }
            });
            equipment.get(i).setUser(new User() {
                {
                    setLastName(gridView.get("7").getValues().get(i));
                }
            });
            equipment.get(i).setCurrentAssignmentId((!gridView.get("8").getValues().get(i).isEmpty()) ? new Long(gridView.get("8").getValues().get(i)) : new Long(0));
            equipment.get(i).setEquipmentType(new EquipmentType() {
                {
                    setEquipmentStyle(new EquipmentStyle() {
                        {
                            setEquipmentStyleName(gridView.get("9").getValues().get(i));
                        }
                    });
                }
            });

            equipment.get(i).setEquipmentStatus((!gridView.get("10").getValues().get(i).isEmpty())? gridView.get("10").getValues().get(i): BaseView.ZERO_LENGTH_STRING);
            // 2015-07-20 14:43:41.0   yyyy-MM-dd
            equipment.get(i).setCreatedDateTime((gridView.get("11").getValues().get(i)!=null && !gridView.get("11").getValues().get(i).isEmpty())? this.getData(gridView.get("11").getValues().get(i)) :new Date());
            equipment.get(i).setCreatedDateTime((gridView.get("12").getValues().get(i) != null && !gridView.get("12").getValues().get(i).isEmpty()) ? this.getData(gridView.get("12").getValues().get(i)) : new Date());
        }
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @JsonView(EquipmentSearchByColumnsDetailsView.class)
    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    @JsonIgnore
    public Date getData(final String dateString)throws Exception{
        try{
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            final Date parsedDate = dateFormat.parse(dateString);
            return parsedDate;
        }catch(Exception e){
            throw e;
        }
    }
}
