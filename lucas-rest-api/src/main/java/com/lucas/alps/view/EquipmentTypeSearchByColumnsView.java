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
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.EquipmentTypeSearchByColumnsDetailsView;
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.entity.user.User;
import com.lucas.services.util.CollectionsUtilService;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for Equipment Types.
 */
public class EquipmentTypeSearchByColumnsView implements Serializable{

    private List<EquipmentType> equipmentTypes;
    private Long totalRecords;

    public EquipmentTypeSearchByColumnsView() {
    }

    public EquipmentTypeSearchByColumnsView(List<EquipmentType> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }

    public EquipmentTypeSearchByColumnsView(List<EquipmentType> equipmentTypes, Long totalRecords) {
        this.equipmentTypes = equipmentTypes;
        this.totalRecords = totalRecords;
    }

    @JsonView(EquipmentTypeSearchByColumnsDetailsView.class)
    public Map<String, GridColumnView> getGrid() throws IOException {
        LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
        for (int i = 1; i <= 10; i++) {
            GridColumn gridColumn = new GridColumn();
            gridView.put(i + "", new GridColumnView(gridColumn));
        }

        for (EquipmentType equipmentType : CollectionsUtilService.nullGuard(equipmentTypes)) {
            gridView.get("1").getValues().add((equipmentType != null && equipmentType.getEquipmentStyle() != null) ? equipmentType.getEquipmentStyle().getEquipmentStyleName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("2").getValues().add((equipmentType.getEquipmentTypeName() != null && !equipmentType.getEquipmentTypeName().isEmpty()) ? equipmentType.getEquipmentTypeName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("3").getValues().add((equipmentType.getEquipmentTypeDescription() != null && !equipmentType.getEquipmentTypeDescription().isEmpty()) ? equipmentType.getEquipmentTypeDescription() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("4").getValues().add((equipmentType.isRequiresCheckList()) ? "Yes" : "No");
            gridView.get("5").getValues().add((equipmentType.isShippingContainer()) ? "Yes" : "No");
            gridView.get("6").getValues().add((equipmentType.isRequiresCertification()) ? "Yes" : "No");
            gridView.get("7").getValues().add((equipmentType.getEquipmentTypePositions() != null) ? equipmentType.getEquipmentTypePositions().toString() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("8").getValues().add((equipmentType.getEquipmentCount() != null) ? equipmentType.getEquipmentCount().toString() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("9").getValues().add(equipmentType.getCreatedDateTime().toString());
            gridView.get("10").getValues().add(equipmentType.getUpdatedDateTime().toString());
        }
        return gridView;
    }


    public void setGrid(final Map<String, GridColumnView> gridView)
            throws ParseException {
        int pageSize = gridView.get("1").getValues().size();
        equipmentTypes = new ArrayList<EquipmentType>();
        for (int index = 0; index < pageSize; index++) {
            final int i = index;
            equipmentTypes.add(new EquipmentType());
            equipmentTypes.get(i).setEquipmentStyle(new EquipmentStyle() {
                {
                    setEquipmentStyleName(gridView.get("1").getValues().get(i));
                }
            });
            equipmentTypes.get(i).setEquipmentTypeName(gridView.get("2").getValues().get(i));
            equipmentTypes.get(i).setEquipmentTypeDescription(gridView.get("3").getValues().get(i));
            equipmentTypes.get(i).setRequiresCheckList(gridView.get("4").getValues().get(i).equalsIgnoreCase("YES"));
            equipmentTypes.get(i).setShippingContainer(gridView.get("5").getValues().get(i).equalsIgnoreCase("YES"));
            equipmentTypes.get(i).setRequiresCertification(gridView.get("6").getValues().get(i).equalsIgnoreCase("YES"));
            equipmentTypes.get(i).setEquipmentTypePositions((gridView.get("7").getValues().get(i) != null) ? Long.parseLong(gridView.get("7").getValues().get(i)) : 0l);
            equipmentTypes.get(i).setEquipmentCount((gridView.get("8").getValues().get(i) != null && !gridView.get("8").getValues().get(i).isEmpty()) ? Long.parseLong(gridView.get("8").getValues().get(i)) : 0l);
        }
    }

    public List<EquipmentType> getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(List<EquipmentType> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }

    @JsonView(EquipmentTypeSearchByColumnsDetailsView.class)
    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
