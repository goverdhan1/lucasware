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
package com.lucas.services.equipment;

import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentPosition;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public interface EquipmentService {

    public static final String DELETE_STATUS="deleted";

    /**
     * findByEquipmentId()
     *
     * @param equipmentId
     * @return
     */
    public Equipment findByEquipmentId(final Long equipmentId);

    /**
     * findByEquipmentName() provide the specification for fetching the
     * Equipment based on the Equipment name and its dependency based on
     * the dependency boolean flag.
     *
     * @param equipmentName is accepted by this method
     * @param dependency is accepted as boolean value for loading dependency
     * @return instance of Equipment
     */
    public Equipment findByEquipmentName(final String equipmentName,boolean dependency);

    /**
     * getAllEquipment()
     *
     * @return
     */
    public List<Equipment> getAllEquipment();

    /**
     * saveEquipment()
     *
     * @param equipment
     * @return
     */
    public boolean saveEquipment(final Equipment equipment);

    /**
     * saveEquipments()
     *
     * @param equipmentList
     * @return
     */
    public boolean saveEquipments(final List<Equipment> equipmentList);


    /**
     * findByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public EquipmentType findByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * findByEquipmentTypeName()
     *
     * @param equipmentTypeName
     * @return
     */
    public EquipmentType findByEquipmentTypeName(final String equipmentTypeName);


    /**
     * deleteEquipmentPositionByEquipmentId() provide the specification for deletion of
     * EquipmentPosition based on the EquipmentId.
     * @param equipmentId is accepted by this method
     * @return boolean value containing the status of the operations.
     */
    public boolean deleteEquipmentPositionByEquipmentId(final Long equipmentId);

    /**
     * saveEquipmentPositions() provide the functionality for saving EquipmentPosition
     * for Equipment
     * @param equipmentPositions list of the EquipmentPosition
     * @return operation status in boolean value.
     */
    public boolean saveEquipmentPositions(List<EquipmentPosition> equipmentPositions);

    /**
     * saveEquipmentType()
     *
     * @param equipmentType
     * @return
     */
    public boolean saveEquipmentType(final EquipmentType equipmentType);

    /**
     * saveEquipmentTypes()
     *
     * @param equipmentTypeList
     * @return
     */
    public boolean saveEquipmentTypes(final List<EquipmentType> equipmentTypeList);

    /**
     * findEquipmentPositionById()
     *
     * @param equipmentPositionId
     * @return
     */
    public EquipmentPosition findEquipmentPositionById(final Long equipmentPositionId);

    /**
     * findEquipmentPositionByEquipmentId() provide the specification for getting the
     * list of EquipmentPosition based on the equipmentId
     *
     * @param equipmentId is accepted as the formal argument for fetching the list of
     *                    EquipmentPosition.
     * @return  list of EquipmentPosition
     */
    public List<EquipmentPosition> findEquipmentPositionByEquipmentId(final Long equipmentId);

    /**
     * getEquipmentListBySearchAndSortCriteria() provide the functionality of the projecting a list of
     * Equipment based on the SearchAndSortCriteria
     *
     * @param searchAndSortCriteria is the instance of SearchAndSortCriteria containing filters.
     * @return a list of Equipment
     * @throws java.text.ParseException
     * @throws Exception
     */
    public List<Equipment> getEquipmentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws  Exception;


    /**
     * getTotalCountForSearchAndSortCriteria() Service call to fetch the total records from
     * given search and sort criteria.
     *
     * @param searchAndSortCriteria is the instance of SearchAndSortCriteria containing filters.
     * @return the count of the searchAndSortCriteria based fetch records.
     * @throws ParseException
     * @throws Exception
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws Exception;


    /**
     * deleteEquipmentByName() provide the specification for deletion of the
     * Equipment and its dependency based on equipment name.
     *
     * @param equipmentName is accepted as the formal argument.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteEquipmentByName(final String equipmentName);

    /**
     * unAssignUserFromEquipment() provide the specification for un-assign user form the
     * equipment
     * @param equipmentName is accepted as the formal argument.
     * @param userName is accepted as the formal argument
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean unAssignUserFromEquipment(final String equipmentName, final String userName);
}
