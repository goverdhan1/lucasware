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
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the specification for Operations can be done on EquipmentType
 */
public interface EquipmentTypeDAO extends GenericDAO<EquipmentType> {

    public static final String equipmentStyle="equipmentStyle.equipmentStyleName";


    /**
     * findByEquipmentTypeId() provide the specification for fetching the EquipmentType
     * from db based on the equipmentTypeId
     *
     * @param equipmentTypeId filter param.
     * @return EquipmentType instance having persisting state.
     */
    public EquipmentType findByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * findByEquipmentTypeName() provide the specification for fetching the EquipmentType
     * from db based on the equipmentTypeName
     *
     * @param equipmentTypeName filter param
     * @return EquipmentType instance having persisting state.
     */
    public EquipmentType findByEquipmentTypeName(final String equipmentTypeName);

    /**
     * getAllEquipmentType() provide the specification for fetching the list of
     *  EquipmentType from db where equipmentTypeStatus is null
     *
     * @return
     */
    public List<EquipmentType> getAllEquipmentType();


    /**
     * saveEquipmentType() provide the specification for persisting the EquipmentType
     * into the db and return the operation status in the form of the boolean value.
     *
     * @param equipmentType instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipmentType(final EquipmentType equipmentType);

    /**
     * saveEquipmentTypes() provide the specification for persisting the list of
     * EquipmentType  into the db and return the operation status in the
     * form of the boolean value.
     *
     * @param equipmentTypeList instance for persisting into db
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean saveEquipmentTypes(final List<EquipmentType> equipmentTypeList);


    /**
     * getEquipmentTypeListBySearchAndSortCriteria() provide the specification for fetching the
     * list of the EquipmentType form db based on the SearchAndSortCriteria provided from Grid
     *
     * @param searchAndSortCriteria
     * @return
     */
    public List<EquipmentType> getEquipmentTypeListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws Exception ;

    /**
     * getTotalCountForSearchAndSortCriteria()
     *
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

    
    /**
     * getEquipmentTypeList() provide the specification for fetching the
     * list of the EquipmentTypeNames form database
     *
     * @return List of EquipmentType
     */
    public List<EquipmentType> getEquipmentTypeList() throws ParseException;

    /**
     * getEquipmentTypeNameList() provide the specification for fetching the
     * list of the EquipmentTypeNames form database based on the filterType
     * @param filterType is a entity property for filter
     * @param equal is a boolean if null not apply for filtering
     *              if true then equal to the supplied filter type
     *              if false then not equal to the supplied filter type.
     * @return list of the Equipment type Name.
     * @throws Exception
     */
    public List<String> getEquipmentTypeNameList(final String filterType,final Boolean equal) throws Exception;


}
