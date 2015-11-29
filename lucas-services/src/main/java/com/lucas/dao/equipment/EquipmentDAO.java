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
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 *  This Class provide the specification for Operations can be done on Equipment
 */
public interface EquipmentDAO extends GenericDAO<Equipment> {

    public static final String equipmentTypeName="equipmentType.equipmentTypeName";
    public static final String userName="user.userName";
    public static final String firstName="user.firstName";
    public static final String lastName="user.lastName";
    public static final String equipmentStyleName="equipmentStyle.equipmentStyleName";
    public static final String equipmentStatus="equipmentStatus";



    /**
     * findByEquipmentId() provide the specification for fetching the Equipment
     * based on the equipmentId
     *
     * @param equipmentId filter param
     * @return Equipment instance having persisting state.
     */
    public Equipment findByEquipmentId(final Long equipmentId);

    /**
     * findByEquipmentName() provide the specification for fetching the Equipment
     * based on the equipmentName.
     *
     * @param equipmentName filter param
     * @return Equipment instance having persisting state.
     */
    public Equipment findByEquipmentName(final String equipmentName);

    /**
     * getAllEquipment() provide the specification for fetching the list of
     * Equipment from db.
     *
     * @return
     */
    public List<Equipment> getAllEquipment();

    /**
     * saveEquipment() provide the specification for persisting the
     * Equipment into db and return the operation status in boolean
     *
     * @param equipment instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipment(final Equipment equipment);

    /**
     * saveEquipments()  provide the specification for persisting the
     * list of the Equipment into the db and retrun the operation status
     * in the boolean value.
     *
     * @param equipmentList instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipments(final List<Equipment> equipmentList);
    
    /**
     * findEquipmentListByTypeId()  provide the specification for soft deleting 
     * the EquipmentType from DB
     * @param equipmentTypeId
     * @return
     */
    public List<Equipment> findEquipmentListByTypeId(Long equipmentTypeId);

    /**
     * deleteEquipment() provide the specification for deletion of the
     * Equipment and its dependency
     * @param equipment is accepted as the formal arguments
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteEquipment(final Equipment equipment);


    /**
     * getEquipmentListBySearchAndSortCriteria() provide the specification for fetching the
     * list of the Equipment form db based on the SearchAndSortCriteria provided from Grid
     *
     * @param searchAndSortCriteria
     * @return
     */
    public List<Equipment> getEquipmentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;




}
