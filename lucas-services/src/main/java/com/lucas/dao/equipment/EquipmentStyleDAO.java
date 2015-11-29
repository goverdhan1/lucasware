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
package com.lucas.dao.equipment;


import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.equipment.EquipmentStyle;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on EquipmentStyle
 */
public interface EquipmentStyleDAO extends GenericDAO<EquipmentStyle> {

    /**
     * findByEquipmentStyleName() provide the specification for fetching the
     * EquipmentStyle from db based on the equipmentStyleName
     *
     * @param equipmentStyleName filter param.
     * @return EquipmentStyle instance having persisting state.
     */
    public EquipmentStyle findByEquipmentStyleName(String equipmentStyleName);

    /**
     * findByEquipmentStyleCode() provide the specification for fetching the
     * EquipmentStyle from db based on the equipmentStyleCode
     *
     * @param equipmentStyleCode filter param.
     * @return EquipmentStyle instance having persisting state.
     */
    public EquipmentStyle findByEquipmentStyleCode(String equipmentStyleCode);

    /**
     * getAllEquipmentStyle() provide the specification for fetching the
     * list of the EquipmentStyle without applying any filter.
     *
     * @return list of the EquipmentStyle instances having persisting state.
     */
    public List<EquipmentStyle> getAllEquipmentStyle();
}
