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
import com.lucas.entity.equipment.EquipmentTypePosition;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on EquipmentTypePosition
 */
public interface EquipmentTypePositionDAO extends GenericDAO<EquipmentTypePosition> {

    /**
     * findEquipmentTypePositionById() provide the specification for fetching the EquipmentTypePosition
     * from db based on the equipmentTypePositionId.
     *
     * @param equipmentTypePositionId filter param.
     * @return EquipmentTypePosition instance having persisting state.
     */
    public EquipmentTypePosition findEquipmentTypePositionById(final Long equipmentTypePositionId);

    /**
     * findEquipmentTypePositionByEquipmentTypeId() provide the specification for fetching the
     * EquipmentTypePosition based on the equipmentTypeId
     *
     * @param equipmentTypeId filter param.
     * @return list of the EquipmentTypePosition instance having persisting state.
     */
    public List<EquipmentTypePosition> findEquipmentTypePositionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveEquipmentTypePositions() provide the specification for persisting the list of EquipmentTypePosition
     * into the db and return the operation status in the form of the boolean value.
     *
     * @param equipmentTypePositionList instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipmentTypePositions(final List<EquipmentTypePosition> equipmentTypePositionList);

    /**
     * deleteEquipmentTypePositionByEquipmentTypeId() provide the specification for deletion of the
     * EquipmentTypePosition based on the equipmentTypeId
     *
     * @param equipmentTypeId filter param.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteEquipmentTypePositionByEquipmentTypeId(final Long equipmentTypeId);

}
