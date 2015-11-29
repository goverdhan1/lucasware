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
import com.lucas.entity.equipment.EquipmentPosition;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on EquipmentPosition
 */
public interface EquipmentPositionDAO extends GenericDAO<EquipmentPosition> {

    /**
     * findEquipmentPositionById() provide the specification for fetching the EquipmentPosition
     * based on the equipmentPositionId from db.
     *
     * @param equipmentPositionId filter param
     * @return EquipmentPosition instance having persisting state.
     */
    public EquipmentPosition findEquipmentPositionById(final Long equipmentPositionId);

    /**
     * findEquipmentPositionByEquipmentId() provide the specification for fetching the
     * EquipmentPosition based on the equipmentId from db.
     *
     * @param equipmentId filter param.
     * @return list of the EquipmentPosition instances having persisting state.
     */
    public List<EquipmentPosition> findEquipmentPositionByEquipmentId(final Long equipmentId);

    /**
     * deleteEquipmentPositionByEquipmentId() provide the specification for deletion of the
     * equipment position based on the equipment id
     * @param equipmentId accepted for deletion operation
     * @return boolean value containing the status of the deletion operation.
     */
    public boolean deleteEquipmentPositionByEquipmentId(final Long  equipmentId);
}
