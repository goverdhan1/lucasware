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

import com.lucas.dao.equipment.EquipmentTypeDAO;
import com.lucas.entity.equipment.*;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public interface EquipmentTypeService {

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
     * findByEquipmentTypeName() provide the specification for getting the EquipmentType
     * based on the equipmentTypeName
     *
     * @param equipmentTypeName is accepted for selection of the EquipmentType
     * @param dependency is boolean value for fetching the dependent object of the
     *              EquipmentType based on true  dependency will also fetch.
     * @return EquipmentType object is return based on the equipmentTypeName.
     */
    public EquipmentType findByEquipmentTypeName(final String equipmentTypeName,boolean dependency);

    /**
     * getAllEquipmentType()
     *
     * @return
     */
    public List<EquipmentType> getAllEquipmentType();

    /**
     * saveEquipmentType() provide the specification for saving the
     * EquipmentType or updating the existing EquipmentType
     * while saving or updating the EquipmentType every time
     * EquipmentTypePosition is clean up and reinserted and associated
     * with the EquipmentType
     *
     *
     * @param equipmentType instance is accepted for insertion or updating
     * @return boolean value representing operation status true for success and false for failure
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
     * findByEquipmentId()
     *
     * @param equipmentId
     * @return
     */
    public Equipment findByEquipmentId(final Long equipmentId);

    /**
     * findByEquipmentName()
     *
     * @param equipmentName
     * @return
     */
    public Equipment findByEquipmentName(final String equipmentName);

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
     * findByContainerTypeCode()
     *
     * @param containerTypeCode
     * @return
     */
    public ContainerType findByContainerTypeCode(String containerTypeCode);

    /**
     * getAllContainerType()
     *
     * @return
     */
    public List<ContainerType> getAllContainerType();


    /**
     * findByEquipmentStyleName()
     *
     * @param equipmentStyleName
     * @return
     */
    public EquipmentStyle findByEquipmentStyleName(String equipmentStyleName);

    /**
     * findByEquipmentStyleCode()
     *
     * @param equipmentStyleCode
     * @return
     */
    public EquipmentStyle findByEquipmentStyleCode(String equipmentStyleCode);

    /**
     * getAllEquipmentStyle() provide the specification for fetching all the
     * equipment style from db without any filter conditions.
     *
     * @return list of the EquipmentStyle from db.
     */
    public List<EquipmentStyle> getAllEquipmentStyle();

    /**
     * getAllEquipmentStyleNames() provide the specification for fetching all the
     * equipment style names from db without any filter conditions.
     *
     * @return list of the EquipmentStyle from db.
     */
    public List<String> getAllEquipmentStyleNames();

    /**
     * findAnswerEvaluationRuleByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveAnswerEvaluationRule()
     *
     * @param answerEvaluationRule
     * @return
     */
    public boolean saveAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);

    /**
     * updateAnswerEvaluationRule()
     *
     * @param answerEvaluationRule
     * @return
     */
    public AnswerEvaluationRule updateAnswerEvaluationRule(final AnswerEvaluationRule answerEvaluationRule);

    /**
     * findEquipmentTypeQuestionByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveEquipmentTypeQuestion()
     *
     * @param equipmentTypeQuestion
     * @return
     */
    public boolean saveEquipmentTypeQuestion(final EquipmentTypeQuestion equipmentTypeQuestion);


    /**
     * findEquipmentTypePositionById()
     *
     * @param equipmentTypePositionId
     * @return
     */
    public EquipmentTypePosition findEquipmentTypePositionById(final Long equipmentTypePositionId);

    /**
     * findEquipmentTypePositionByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<EquipmentTypePosition> findEquipmentTypePositionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * saveEquipmentTypePositions()
     *
     * @param equipmentTypePositionList
     * @return
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

    /**
     * saveLucasUserCertifiedEquipmentType()
     *
     * @param equipmentTypeId
     * @param userId
     * @return
     */
    public boolean saveLucasUserCertifiedEquipmentType(final Long equipmentTypeId,final Long userId);

    /**
     * findLucasUserCertifiedEquipmentTypeByEquipmentTypeId()
     *
     * @param equipmentTypeId
     * @return
     */
    public List<LucasUserCertifiedEquipmentType> findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * getEquipmentTypeListBySearchAndSortCriteria()
     *
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public List<EquipmentType>  getEquipmentTypeListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws ParseException,Exception;


    /**
     * Service call to fetch the total records from UserDAO given search and sort criteria.
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)throws ParseException ,Exception;
    
    /**
     * getEquipmentTypeList()
     *
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public List<String>  getEquipmentTypeList() throws ParseException,Exception;
    
    /**
     * deleteEquipmentByType() service method to soft delete an Equipment Type.
     *
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public Map<String,String> deleteEquipmentByType(String equipmentTypeName) throws ParseException, Exception;


    /**
     * deleteAnswerEvaluationRuleByEquipmentTypeId() provide the specification for deletion of the
     * AnswerEvaluationRule based on the equipment type id its uses
     * AnswerEvaluationRuleDAO.deleteAnswerEvaluationRuleByEquipmentTypeId();
     *
     * @param equipmentTypeId is accepted as the formal arguments to this method.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * deleteEquipmentTypeQuestionByEquipmentTypeId provide the specification for deletion of the
     * EquipmentTypeQuestion based on the equipment type id its uses
     * EquipmentTypeQuestionDAO.deleteEquipmentTypeQuestionByEquipmentTypeId();
     *
     * @param equipmentTypeId is accepted as the formal arguments to this method.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId);

    /**
     * deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId() provide the specification for deletion of the
     * LucasUserCertifiedEquipmentType based on the equipment type id its uses
     * LucasUserCertifiedEquipmentTypeDAO.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId();
     *
     * @param equipmentTypeId is accepted as the formal arguments to this method.
     * @return boolean value representing operation status true for success and false for failure
     */
    public boolean deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(final Long equipmentTypeId);
}
