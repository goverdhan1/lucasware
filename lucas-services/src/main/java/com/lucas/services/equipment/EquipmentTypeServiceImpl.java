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

import com.lucas.dao.user.UserDAO;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.dao.equipment.*;
import com.lucas.entity.equipment.*;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Service("equipmentTypeService")
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentTypeServiceImpl.class);

    private UserDAO userDAO;
    private SecurityService securityService;
    private EquipmentDAO equipmentDAO;
    private ContainerTypeDAO containerTypeDAO;
    private EquipmentTypeDAO equipmentTypeDAO;
    private EquipmentStyleDAO equipmentStyleDAO;
    private AnswerEvaluationRuleDAO answerEvaluationRuleDAO;
    private EquipmentTypeQuestionDAO equipmentTypeQuestionDAO;
    private EquipmentTypePositionDAO equipmentTypePositionDAO;
    private LucasUserCertifiedEquipmentTypeDAO lucasUserCertifiedEquipmentTypeDAO;
    private QuestionService questionService;
    public static final String DELETE_STATUS="deleted";

    @Inject
    public EquipmentTypeServiceImpl(UserDAO userDAO, EquipmentDAO equipmentDAO, ContainerTypeDAO containerTypeDAO
            , EquipmentTypeDAO equipmentTypeDAO, EquipmentStyleDAO equipmentStyleDAO
            , AnswerEvaluationRuleDAO answerEvaluationRuleDAO, EquipmentTypeQuestionDAO equipmentTypeQuestionDAO
            , EquipmentTypePositionDAO equipmentTypePositionDAO, LucasUserCertifiedEquipmentTypeDAO lucasUserCertifiedEquipmentTypeDAO
            , QuestionService questionService,SecurityService securityService) {
        this.userDAO = userDAO;
        this.equipmentDAO = equipmentDAO;
        this.containerTypeDAO = containerTypeDAO;
        this.equipmentTypeDAO = equipmentTypeDAO;
        this.equipmentStyleDAO = equipmentStyleDAO;
        this.answerEvaluationRuleDAO = answerEvaluationRuleDAO;
        this.equipmentTypeQuestionDAO = equipmentTypeQuestionDAO;
        this.equipmentTypePositionDAO = equipmentTypePositionDAO;
        this.lucasUserCertifiedEquipmentTypeDAO = lucasUserCertifiedEquipmentTypeDAO;
        this.questionService = questionService;
        this.securityService=securityService;
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypeDAO.findByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeName(String equipmentTypeName) {
        return this.equipmentTypeDAO.findByEquipmentTypeName(equipmentTypeName);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeName(String equipmentTypeName, boolean dependency) {
        final EquipmentType equipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipmentTypeName);
        if (equipmentType != null && dependency) {
            final List<EquipmentTypePosition> equipmentTypePosition = this.equipmentTypePositionDAO.findEquipmentTypePositionByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setEquipmentTypePositionList(equipmentTypePosition);

            final List<EquipmentTypeQuestion> equipmentTypeQuestionList = this.equipmentTypeQuestionDAO.findEquipmentTypeQuestionByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setEquipmentTypeQuestions(equipmentTypeQuestionList);

            final List<AnswerEvaluationRule> answerEvaluationRules = this.answerEvaluationRuleDAO.findAnswerEvaluationRuleByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setAnswerEvaluationRules(answerEvaluationRules);

            final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = this.lucasUserCertifiedEquipmentTypeDAO.findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        }
        return equipmentType;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentType> getAllEquipmentType() {
        final List<EquipmentType> equipmentTypeList = this.equipmentTypeDAO.getAllEquipmentType();
        for (EquipmentType equipmentType : equipmentTypeList) {
            final List<EquipmentTypePosition> equipmentTypePosition = this.equipmentTypePositionDAO.findEquipmentTypePositionByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setEquipmentTypePositionList(equipmentTypePosition);
        }
        return equipmentTypeList;
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentType(EquipmentType equipmentType) {
        try {
            EquipmentType retrievalEquipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipmentType.getEquipmentTypeName());
            final String currentLoggedUsername=this.securityService.getLoggedInUsername();
            if (retrievalEquipmentType == null) {
                LOG.info("EquipmentType Not found for " + equipmentType.getEquipmentTypeName());
                // when EquipmentType doesn't exists

                // Associating the EquipmentStyle with EquipmentType
                final EquipmentStyle equipmentStyle = equipmentType.getEquipmentStyle();
                if (equipmentStyle != null && equipmentStyle.getEquipmentStyleCode() != null) {
                    LOG.info("Getting EquipmentStyle for EquipmentType " + equipmentStyle.getEquipmentStyleCode());
                    equipmentType.setEquipmentStyle(this.findByEquipmentStyleCode(equipmentStyle.getEquipmentStyleCode()));
                } else {
                    throw new LucasRuntimeException("EquipmentStyle is missing for EquipmentType");
                }

                // Associating the ContainerType with EquipmentType
                final ContainerType containerType = equipmentType.getContainerType();
                if (containerType != null && containerType.getContainerCode() != null) {
                    LOG.info("Getting ContainerType for EquipmentType " + containerType.getContainerCode());
                    equipmentType.setContainerType(this.containerTypeDAO.findByContainerTypeCode(containerType.getContainerCode()));
                }else{
                    throw new LucasRuntimeException("ContainerType is missing for EquipmentType");
                }

                //setting the audit columns values
                equipmentType.setCreatedDateTime(new Date());
                equipmentType.setCreatedByUsername(currentLoggedUsername);
                equipmentType.setUpdatedDateTime(new Date());
                equipmentType.setUpdatedByUsername(currentLoggedUsername);
                retrievalEquipmentType = this.equipmentTypeDAO.save(equipmentType);
                LOG.info("Persisted the EquipmentType " + retrievalEquipmentType);

                // Associating the EquipmentTypeQuestion with EquipmentType
                final List<AnswerEvaluationRule> answerEvaluationRules = equipmentType.getAnswerEvaluationRules();
                if (answerEvaluationRules != null && !answerEvaluationRules.isEmpty()) {
                    for (AnswerEvaluationRule answerEvaluationRule : answerEvaluationRules) {
                        if (answerEvaluationRule.getQuestion() != null) {

                            final Question question = this.questionService.findQuestionById(answerEvaluationRule.getQuestion().getQuestionId());
                            answerEvaluationRule.setQuestion(question);
                            answerEvaluationRule.setEquipmentType(retrievalEquipmentType);
                            answerEvaluationRule.setCreatedDateTime(new Date());
                            answerEvaluationRule.setCreatedByUsername(currentLoggedUsername);
                            answerEvaluationRule.setUpdatedDateTime(new Date());
                            answerEvaluationRule.setUpdatedByUsername(currentLoggedUsername);
                            LOG.info((this.answerEvaluationRuleDAO.save(answerEvaluationRule) != null) ?
                                    "AnswerEvaluationRule Persisted for the EquipmentType " + answerEvaluationRule
                                    : "AnswerEvaluationRule Not Persisted for the EquipmentType " + answerEvaluationRule);

                            final EquipmentTypeQuestion equipmentTypeQuestion = new EquipmentTypeQuestion();
                            equipmentTypeQuestion.setCreatedDateTime(new Date());
                            equipmentTypeQuestion.setCreatedByUsername(currentLoggedUsername);
                            equipmentTypeQuestion.setUpdatedDateTime(new Date());
                            equipmentTypeQuestion.setUpdatedByUsername(currentLoggedUsername);
                            equipmentTypeQuestion.setQuestion(question);
                            equipmentTypeQuestion.setEquipmentType(retrievalEquipmentType);
                            LOG.info((this.equipmentTypeQuestionDAO.save(equipmentTypeQuestion) != null) ?
                                    "EquipmentTypeQuestion Persisted for the EquipmentType " + equipmentTypeQuestion
                                    : "EquipmentTypeQuestion Not Persisted for the EquipmentType " + equipmentTypeQuestion);
                        }
                    }
                }

                // Associating the LucasUserCertifiedEquipmentType with EquipmentType
                final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = equipmentType.getLucasUserCertifiedEquipmentTypes();
                final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeArrayList = new ArrayList<LucasUserCertifiedEquipmentType>();
                if (lucasUserCertifiedEquipmentTypeList != null && !lucasUserCertifiedEquipmentTypeList.isEmpty()) {
                    for (LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType : lucasUserCertifiedEquipmentTypeList) {
                        if (lucasUserCertifiedEquipmentType.getUser() != null) {
                            final User user = this.userDAO.findByUserName(lucasUserCertifiedEquipmentType.getUser().getUserName());
                            LOG.info("Getting User for EquipmentType " + user);
                            lucasUserCertifiedEquipmentType.setUser(user);
                            lucasUserCertifiedEquipmentType.setCreatedDateTime(new Date());
                            lucasUserCertifiedEquipmentType.setCreatedByUsername(currentLoggedUsername);
                            lucasUserCertifiedEquipmentType.setUpdatedDateTime(new Date());
                            lucasUserCertifiedEquipmentType.setUpdatedByUsername(currentLoggedUsername);
                            lucasUserCertifiedEquipmentType.setEquipmentType(equipmentType);
                            lucasUserCertifiedEquipmentType = this.lucasUserCertifiedEquipmentTypeDAO.saveLucasUserCertifiedEquipmentType(lucasUserCertifiedEquipmentType);
                            LOG.info((lucasUserCertifiedEquipmentType != null) ?
                                    "LucasUserCertifiedEquipmentType Persisted for the  EquipmentType " + lucasUserCertifiedEquipmentType :
                                    "LucasUserCertifiedEquipmentType Not Persisted for the  EquipmentType " + lucasUserCertifiedEquipmentType);
                            lucasUserCertifiedEquipmentTypeArrayList.add(lucasUserCertifiedEquipmentType);
                        }
                    }
                    retrievalEquipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeArrayList);
                }

                // Associating the EquipmentTypePosition with EquipmentType
                final List<EquipmentTypePosition> equipmentTypePositionList = equipmentType.getEquipmentTypePositionList();
                final List<EquipmentTypePosition> equipmentTypePositionArrayList = new ArrayList<EquipmentTypePosition>();
                if (equipmentTypePositionList != null && !equipmentTypePositionList.isEmpty()) {
                    for (EquipmentTypePosition equipmentTypePosition : equipmentTypePositionList) {
                        equipmentTypePosition.setCreatedDateTime(new Date());
                        equipmentTypePosition.setCreatedByUsername(currentLoggedUsername);
                        equipmentTypePosition.setUpdatedDateTime(new Date());
                        equipmentTypePosition.setUpdatedByUsername(currentLoggedUsername);
                        equipmentTypePosition.setEquipmentType(equipmentType);
                        final EquipmentTypePosition equipmentTypePositionPersisted = this.equipmentTypePositionDAO.save(equipmentTypePosition);
                        LOG.info((equipmentTypePositionPersisted != null)
                                ? " EquipmentTypePosition Persisted for the EquipmentType " + equipmentTypePositionPersisted
                                : " EquipmentTypePosition Not Persisted for the EquipmentType " + equipmentTypePositionPersisted);
                        equipmentTypePositionArrayList.add(equipmentTypePositionPersisted);
                    }
                    retrievalEquipmentType.setEquipmentTypePositionList(equipmentTypePositionArrayList);
                    retrievalEquipmentType.setEquipmentTypePositions(new Long(equipmentTypePositionArrayList.size()));
                } else {
                    throw new LucasRuntimeException("EquipmentTypePosition is missing for EquipmentType");
                }
                //setting the audit columns values
                equipmentType.setUpdatedDateTime(new Date());
                equipmentType.setUpdatedByUsername(currentLoggedUsername);
                final boolean result = this.equipmentTypeDAO.saveEquipmentType(retrievalEquipmentType);
                LOG.info((result) ? "EquipmentType is  Persisting" + retrievalEquipmentType : "EquipmentType isn't  Persisting" + retrievalEquipmentType);
                return result;
            } else {

                LOG.info("EquipmentType found for " + equipmentType.getEquipmentTypeName());
                // updating existing EquipmentType
                final Long equipmentTypeId = retrievalEquipmentType.getEquipmentTypeId();

                // Associating the EquipmentStyle with EquipmentType
                final EquipmentStyle equipmentStyle = equipmentType.getEquipmentStyle();
                if (equipmentStyle != null && equipmentStyle.getEquipmentStyleCode() != null) {
                    LOG.info("Getting EquipmentStyle for EquipmentType " + equipmentStyle.getEquipmentStyleCode());
                    retrievalEquipmentType.setEquipmentStyle(this.findByEquipmentStyleCode(equipmentStyle.getEquipmentStyleCode()));
                } else {
                    throw new LucasRuntimeException("EquipmentStyle is missing for EquipmentType");
                }

                // Associating the ContainerType with EquipmentType
                final ContainerType containerType = equipmentType.getContainerType();
                if (containerType != null && containerType.getContainerCode() != null) {
                    LOG.info("Getting ContainerType for EquipmentType " + containerType.getContainerCode());
                    retrievalEquipmentType.setContainerType(this.containerTypeDAO.findByContainerTypeCode(containerType.getContainerCode()));
                }

                //cleaning the equipment type position for equipment type
                LOG.info(this.deleteEquipmentTypePositionByEquipmentTypeId(equipmentTypeId)
                        ? "EquipmentTypePosition are deleted for EquipmentType " + equipmentTypeId
                        : "EquipmentTypePosition are not deleted for EquipmentType " + equipmentTypeId);

                // Associating the EquipmentTypePosition with EquipmentType
                final List<EquipmentTypePosition> equipmentTypePositionList = equipmentType.getEquipmentTypePositionList();
                final List<EquipmentTypePosition> equipmentTypePositionArrayList = new ArrayList<EquipmentTypePosition>();
                if (equipmentTypePositionList != null && !equipmentTypePositionList.isEmpty()) {
                    for (EquipmentTypePosition equipmentTypePosition : equipmentTypePositionList) {
                        equipmentTypePosition.setCreatedDateTime(new Date());
                        equipmentTypePosition.setCreatedByUsername(currentLoggedUsername);
                        equipmentTypePosition.setUpdatedDateTime(new Date());
                        equipmentTypePosition.setUpdatedByUsername(currentLoggedUsername);
                        equipmentTypePosition.setEquipmentType(retrievalEquipmentType);
                        final EquipmentTypePosition equipmentTypePositionPersisted = this.equipmentTypePositionDAO.save(equipmentTypePosition);
                        LOG.info((equipmentTypePositionPersisted != null)
                                ? " EquipmentTypePosition Persisted for the EquipmentType " + equipmentTypePositionPersisted
                                : " EquipmentTypePosition Not Persisted for the EquipmentType " + equipmentTypePositionPersisted);
                        equipmentTypePositionArrayList.add(equipmentTypePositionPersisted);
                    }
                    retrievalEquipmentType.setEquipmentTypePositionList(equipmentTypePositionArrayList);
                    retrievalEquipmentType.setEquipmentTypePositions(new Long(equipmentTypePositionArrayList.size()));
                } else {
                    throw new LucasRuntimeException("EquipmentTypePosition is missing for EquipmentType");
                }

                //  Deletion of the AnswerEvaluationRule Based on EquipmentTypeId
                LOG.info(this.deleteAnswerEvaluationRuleByEquipmentTypeId(equipmentTypeId)
                        ? "AnswerEvaluationRule are deleted for EquipmentType " + equipmentTypeId
                        : "AnswerEvaluationRule are not deleted for EquipmentType " + equipmentTypeId);
                //  Deletion of the EquipmentTypeQuestion Based on EquipmentTypeId
                LOG.info(this.deleteEquipmentTypeQuestionByEquipmentTypeId(equipmentTypeId)
                        ? "EquipmentTypeQuestion are deleted for EquipmentType " + equipmentTypeId
                        : "EquipmentTypeQuestion are not deleted for EquipmentType " + equipmentTypeId);

                final List<AnswerEvaluationRule> answerEvaluationRules = equipmentType.getAnswerEvaluationRules();
                if (answerEvaluationRules != null && !answerEvaluationRules.isEmpty()) {
                    for (AnswerEvaluationRule answerEvaluationRule : answerEvaluationRules) {
                        if (answerEvaluationRule.getQuestion() != null) {
                            // Associating the AnswerEvaluationRule with EquipmentType
                            final Question question = this.questionService.findQuestionById(answerEvaluationRule.getQuestion().getQuestionId());
                            answerEvaluationRule.setQuestion(question);
                            answerEvaluationRule.setEquipmentType(retrievalEquipmentType);
                            answerEvaluationRule.setCreatedDateTime(new Date());
                            answerEvaluationRule.setCreatedByUsername(currentLoggedUsername);
                            answerEvaluationRule.setUpdatedDateTime(new Date());
                            answerEvaluationRule.setUpdatedByUsername(currentLoggedUsername);
                            LOG.info((this.answerEvaluationRuleDAO.save(answerEvaluationRule) != null)
                                    ? "Persisted the AnswerEvaluationRule for EquipmentType " + answerEvaluationRule
                                    : "AnswerEvaluationRule for EquipmentType is Not Persisted" + answerEvaluationRule);

                            // Associating the EquipmentTypeQuestion with EquipmentType
                            final EquipmentTypeQuestion equipmentTypeQuestion = new EquipmentTypeQuestion();
                            equipmentTypeQuestion.setCreatedDateTime(new Date());
                            equipmentTypeQuestion.setCreatedByUsername(currentLoggedUsername);
                            equipmentTypeQuestion.setUpdatedDateTime(new Date());
                            equipmentTypeQuestion.setUpdatedByUsername(currentLoggedUsername);
                            equipmentTypeQuestion.setQuestion(question);
                            equipmentTypeQuestion.setEquipmentType(retrievalEquipmentType);
                            LOG.info((this.equipmentTypeQuestionDAO.save(equipmentTypeQuestion) != null)
                                    ? "Persisted the EquipmentTypeQuestion for EquipmentType " + equipmentTypeQuestion
                                    : "EquipmentTypeQuestion for EquipmentType is Not Persisted" + equipmentTypeQuestion);
                        }
                    }
                }

                //  Deletion of the LucasUserCertifiedEquipmentType Based on EquipmentTypeId
                LOG.info(this.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentTypeId)
                        ? "LucasUserCertifiedEquipmentType are deleted for EquipmentType " + equipmentTypeId
                        : "LucasUserCertifiedEquipmentType are not deleted for EquipmentType " + equipmentTypeId);
                // Associating the LucasUserCertifiedEquipmentType with EquipmentType
                final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = equipmentType.getLucasUserCertifiedEquipmentTypes();
                final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeArrayList = new ArrayList<LucasUserCertifiedEquipmentType>();
                if (lucasUserCertifiedEquipmentTypeList != null && !lucasUserCertifiedEquipmentTypeList.isEmpty()) {
                    for (LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType : lucasUserCertifiedEquipmentTypeList) {
                        if (lucasUserCertifiedEquipmentType.getUser() != null) {
                            final User user = this.userDAO.findByUserName(lucasUserCertifiedEquipmentType.getUser().getUserName());
                            lucasUserCertifiedEquipmentType.setUser(user);
                            lucasUserCertifiedEquipmentType.setCreatedDateTime(new Date());
                            lucasUserCertifiedEquipmentType.setCreatedByUsername(currentLoggedUsername);
                            lucasUserCertifiedEquipmentType.setUpdatedDateTime(new Date());
                            lucasUserCertifiedEquipmentType.setUpdatedByUsername(currentLoggedUsername);
                            lucasUserCertifiedEquipmentType.setEquipmentType(retrievalEquipmentType);
                            lucasUserCertifiedEquipmentType = this.lucasUserCertifiedEquipmentTypeDAO.saveLucasUserCertifiedEquipmentType(lucasUserCertifiedEquipmentType);
                            LOG.info((lucasUserCertifiedEquipmentType != null)
                                    ? "LucasUserCertifiedEquipmentType Persisted for the EquipmentType " + lucasUserCertifiedEquipmentType
                                    : "LucasUserCertifiedEquipmentType NotPersisted for the EquipmentType " + lucasUserCertifiedEquipmentType);
                            lucasUserCertifiedEquipmentTypeArrayList.add(lucasUserCertifiedEquipmentType);
                        }
                    }
                    retrievalEquipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeArrayList);
                }
                //setting the audit columns values
                retrievalEquipmentType.setUpdatedDateTime(new Date());
                retrievalEquipmentType.setUpdatedByUsername(currentLoggedUsername);
                retrievalEquipmentType.setEquipmentTypeName(equipmentType.getEquipmentTypeName());
                retrievalEquipmentType.setEquipmentTypeDescription(equipmentType.getEquipmentTypeDescription());
                final boolean result = this.equipmentTypeDAO.saveEquipmentType(retrievalEquipmentType);
                LOG.info((result) ? "EquipmentType is  Persisting" + retrievalEquipmentType : "EquipmentType isn't  Persisting" + retrievalEquipmentType);
                return result;
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            return false;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypes(List<EquipmentType> equipmentTypeList) {
        return this.equipmentTypeDAO.saveEquipmentTypes(equipmentTypeList);
    }

    @Override
    public Equipment findByEquipmentId(Long equipmentId) {
        return this.equipmentDAO.findByEquipmentId(equipmentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Equipment findByEquipmentName(String equipmentName) {
        return this.equipmentDAO.findByEquipmentName(equipmentName);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipment(Equipment equipment) {
        return this.equipmentDAO.saveEquipment(equipment);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipments(List<Equipment> equipmentList) {
        return this.equipmentDAO.saveEquipments(equipmentList);
    }

    @Transactional(readOnly = true)
    @Override
    public ContainerType findByContainerTypeCode(String containerTypeCode) {
        return this.containerTypeDAO.findByContainerTypeCode(containerTypeCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContainerType> getAllContainerType() {
        return this.containerTypeDAO.getAllContainerType();
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentStyle findByEquipmentStyleName(String equipmentStyleName) {
        return this.equipmentStyleDAO.findByEquipmentStyleName(equipmentStyleName);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentStyle findByEquipmentStyleCode(String equipmentStyleCode) {
        return this.equipmentStyleDAO.findByEquipmentStyleCode(equipmentStyleCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentStyle> getAllEquipmentStyle() {
        return this.equipmentStyleDAO.getAllEquipmentStyle();
    }


    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#getAllEquipmentStyleNames()
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> getAllEquipmentStyleNames() {
        final List<EquipmentStyle> equipmentStyleList = this.getAllEquipmentStyle();
        if (equipmentStyleList != null && !equipmentStyleList.isEmpty()) {
            final List<String> equipmentStyleNameList = new ArrayList<>(equipmentStyleList.size());
            for (EquipmentStyle equipmentStyle : equipmentStyleList) {
                equipmentStyleNameList.add(equipmentStyle.getEquipmentStyleName());
            }
            return equipmentStyleNameList;
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnswerEvaluationRule> findAnswerEvaluationRuleByEquipmentTypeId(Long equipmentTypeId) {
        return this.answerEvaluationRuleDAO.findAnswerEvaluationRuleByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        return this.answerEvaluationRuleDAO.saveAnswerEvaluationRule(answerEvaluationRule);
    }

    @Transactional(readOnly = false)
    @Override
    public AnswerEvaluationRule updateAnswerEvaluationRule(AnswerEvaluationRule answerEvaluationRule) {
        return this.answerEvaluationRuleDAO.updateAnswerEvaluationRule(answerEvaluationRule);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentTypeQuestion> findEquipmentTypeQuestionByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypeQuestionDAO.findEquipmentTypeQuestionByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypeQuestion(EquipmentTypeQuestion equipmentTypeQuestion) {
        return this.equipmentTypeQuestionDAO.saveEquipmentTypeQuestion(equipmentTypeQuestion);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentTypePosition findEquipmentTypePositionById(Long equipmentTypePositionId) {
        return this.equipmentTypePositionDAO.findEquipmentTypePositionById(equipmentTypePositionId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentTypePosition> findEquipmentTypePositionByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypePositionDAO.findEquipmentTypePositionByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypePositions(List<EquipmentTypePosition> equipmentTypePositionList) {
        return this.equipmentTypePositionDAO.saveEquipmentTypePositions(equipmentTypePositionList);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteEquipmentTypePositionByEquipmentTypeId(final Long equipmentTypeId) {
        return this.equipmentTypePositionDAO.deleteEquipmentTypePositionByEquipmentTypeId(equipmentTypeId);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteAnswerEvaluationRuleByEquipmentTypeId(final Long equipmentTypeId) {
        return this.answerEvaluationRuleDAO.deleteAnswerEvaluationRuleByEquipmentTypeId(equipmentTypeId);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteEquipmentTypeQuestionByEquipmentTypeId(final Long equipmentTypeId) {
        return this.equipmentTypeQuestionDAO.deleteEquipmentTypeQuestionByEquipmentTypeId(equipmentTypeId);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(final Long equipmentTypeId) {
        return this.lucasUserCertifiedEquipmentTypeDAO.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentTypeId);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentTypeService#saveEquipmentType()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean saveLucasUserCertifiedEquipmentType(Long equipmentTypeId, Long userId) {
        final EquipmentType equipmentType = this.equipmentTypeDAO.findByEquipmentTypeId(equipmentTypeId);
        final User user = this.userDAO.findByUserId(userId);
        if (equipmentType != null && user != null) {
            final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
            lucasUserCertifiedEquipmentType.setEquipmentType(equipmentType);
            lucasUserCertifiedEquipmentType.setUser(user);
            lucasUserCertifiedEquipmentType.setCreatedByUsername(user.getUserName());
            lucasUserCertifiedEquipmentType.setCreatedDateTime(new Date());
            lucasUserCertifiedEquipmentType.setUpdatedByUsername(user.getUserName());
            lucasUserCertifiedEquipmentType.setUpdatedDateTime(new Date());
            this.lucasUserCertifiedEquipmentTypeDAO.save(lucasUserCertifiedEquipmentType);
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LucasUserCertifiedEquipmentType> findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(Long equipmentTypeId) {
        return this.lucasUserCertifiedEquipmentTypeDAO.findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentType> getEquipmentTypeListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception {
        final List<EquipmentType> equipmentTypeList = this.equipmentTypeDAO.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
        for (EquipmentType equipmentType : equipmentTypeList) {
            final List<EquipmentTypePosition> equipmentTypePosition = this.equipmentTypePositionDAO.findEquipmentTypePositionByEquipmentTypeId(equipmentType.getEquipmentTypeId());
            equipmentType.setEquipmentTypePositionList(equipmentTypePosition);
        }
        return equipmentTypeList;
    }

    @Transactional(readOnly = true)
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception {
        return this.equipmentTypeDAO.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
    }


   	 /* (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentTypeService#getEquipmentTypeList()
    */
    @Transactional(readOnly = true)
    @Override
    public List<String> getEquipmentTypeList() throws Exception {
        return this.equipmentTypeDAO.getEquipmentTypeNameList(DELETE_STATUS, false);
    }

    /**
     * deleteEquipmentByType() service method to soft delete an Equipment Type.
     *
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @Transactional(readOnly = false)
    @Override
    public Map<String, String> deleteEquipmentByType(String equipmentTypeName) throws ParseException, Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        if (equipmentTypeName != null && !equipmentTypeName.isEmpty()) {

            EquipmentType equipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipmentTypeName);

            if (equipmentType != null && equipmentType.getEquipmentTypeId() != null) {

                final List<Equipment> equipmentList = this.equipmentDAO.findEquipmentListByTypeId(equipmentType.getEquipmentTypeId());
                final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList =
                        this.lucasUserCertifiedEquipmentTypeDAO.findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentType.getEquipmentTypeId());

                if (equipmentList.size() == 0 && lucasUserCertifiedEquipmentTypeList.size() == 0) {

                    equipmentType.setEquipmentTypeStatus(DELETE_STATUS);

                    this.equipmentTypeDAO.saveEquipmentType(equipmentType);

                    resultMap.put("EQUIPMENT_SOFT_DELETE_CODE", "EQUIPMENT_SOFT_DELETE_CODE");

                } else if (equipmentList.size() != 0 && (lucasUserCertifiedEquipmentTypeList.size() == 0 || lucasUserCertifiedEquipmentTypeList.size() != 0)) {

                    resultMap.put("EQUIPMENT_EXISTS_CODE", "EQUIPMENT_EXISTS_CODE");

                } else if (equipmentList.size() == 0 && lucasUserCertifiedEquipmentTypeList.size() != 0) {

                    resultMap.put("CERTIFIED_USER_EXISTS_CODE", "CERTIFIED_USER_EXISTS_CODE");
                }

            } else {

                resultMap.put("EQUIPMENT_TYPE_NOT_FOUND", "EQUIPMENT_TYPE_NOT_FOUND");

            }
        } else {

            resultMap.put("EQUIPMENT_TYPE_NAME_EMPTY", "EQUIPMENT_TYPE_NAME_EMPTY");

        }

        return resultMap;
    }
}
