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
import com.lucas.entity.equipment.EquipmentTypeQuestion;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on EquipmentTypeQuestion
 */
public interface EquipmentTypeQuestionDAO extends GenericDAO<EquipmentTypeQuestion> {

    /**
     * findEquipmentTypeQuestionByEquipmentTypeId() provide the specification for fetching the
     * EquipmentTypeQuestion based on the equipmentTypeId.
     *
     * @param equipmentTypeId filter param.
     * @return list of the EquipmentTypeQuestion instances having persisting state.
     */
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveEquipmentTypeQuestion() provide the specification for persisting the EquipmentTypeQuestion
     * and return the operation status in the form of the boolean value.
     *
     * @param equipmentTypeQuestion instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipmentTypeQuestion(final EquipmentTypeQuestion equipmentTypeQuestion);


    /**
     * deleteEquipmentTypeQuestionByEquipmentTypeId() provide the specification for deletion of the
     * EquipmentTypeQuestion based on the equipment type id its uses
     * @param equipmentTypeId is accepted as the formal arguments to this method.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId);
}
