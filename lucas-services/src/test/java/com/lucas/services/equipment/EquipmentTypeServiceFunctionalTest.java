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

import com.lucas.entity.equipment.*;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.testsupport.AbstractSpringFunctionalTests;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.text.ParseException;
import java.util.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * EquipmentTypeService Operations
 */
@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class EquipmentTypeServiceFunctionalTest extends AbstractSpringFunctionalTests {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentTypeServiceFunctionalTest.class);
    @Inject
    private EquipmentTypeService equipmentTypeService;
    private static String JACK_USERNAME = "jack123";
    private static String JACK_PASSWORD = "secret";
    private static String HASHED_PASSWORD = "aHashedPassword";
    private User user;
    private Authentication authentication;
    private SecurityContext securityContext ;
    private SimpleGrantedAuthority[] grantedAuthoritiesArr = {
            new SimpleGrantedAuthority("equipment-type-widget-access")
            , new SimpleGrantedAuthority("create-equipment-type")
            , new SimpleGrantedAuthority("edit-equipment-type")
            , new SimpleGrantedAuthority("delete-equipment-type")
    };

    @org.junit.Before
    public void initEquipmentTypeServiceFunctionalTest() {
        LOG.info("initEquipmentTypeServiceFunctionalTest invocation");
        user = new User();
        user.setUserName(JACK_USERNAME);
        user.setPlainTextPassword(JACK_PASSWORD);
        user.setUserId(1L);
        user.setHashedPassword(HASHED_PASSWORD);
        authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(grantedAuthoritiesArr));
        securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Transactional
    @Test
    public void testFetchEquipmentStyleByCode() {
        Assert.assertNotNull("EquipmentStyle is null for generic Code ", this.equipmentTypeService.findByEquipmentStyleCode("generic"));
        Assert.assertNotNull("EquipmentStyle is null for pallet_jack Code ", this.equipmentTypeService.findByEquipmentStyleCode("pallet_jack"));
        Assert.assertNotNull("EquipmentStyle is null for fork_lift Code ", this.equipmentTypeService.findByEquipmentStyleCode("fork_lift"));
        Assert.assertNotNull("EquipmentStyle is null for one_sided_cart Code ", this.equipmentTypeService.findByEquipmentStyleCode("one_sided_cart"));
        Assert.assertNotNull("EquipmentStyle is null for man_up Code ", this.equipmentTypeService.findByEquipmentStyleCode("man_up"));
        Assert.assertNotNull("EquipmentStyle is null for two_sided_cart Code ", this.equipmentTypeService.findByEquipmentStyleCode("two_sided_cart"));
    }


    @Transactional
    @Test
    public void testFetchAllEquipmentStyle() {
        final List<EquipmentStyle> equipmentStyleList = this.equipmentTypeService.getAllEquipmentStyle();
        Assert.assertNotNull("EquipmentStyle list is null ", equipmentStyleList);
        Assert.assertTrue("EquipmentStyle list is empty ", !equipmentStyleList.isEmpty());
    }


    @Transactional
    @Test
    public void testFetchContainerTypeByCode() {
        Assert.assertNotNull("ContainerType is null for Carton Code ", this.equipmentTypeService.findByContainerTypeCode("Carton"));
        Assert.assertNotNull("ContainerType is null for Case Code ", this.equipmentTypeService.findByContainerTypeCode("Case"));
        Assert.assertNotNull("ContainerType is null for EquipmentSlot Code ", this.equipmentTypeService.findByContainerTypeCode("EquipmentSlot"));
        Assert.assertNotNull("ContainerType is null for Pallet Code ", this.equipmentTypeService.findByContainerTypeCode("Pallet"));
        Assert.assertNotNull("ContainerType is null for Tote Code ", this.equipmentTypeService.findByContainerTypeCode("Tote"));
    }

    @Transactional
    @Test
    public void testFetchAllContainerType() {
        final List<ContainerType> containerTypes = this.equipmentTypeService.getAllContainerType();
        Assert.assertNotNull("containerTypes list is null ", containerTypes);
        Assert.assertTrue("containerTypes list is empty", !containerTypes.isEmpty());
    }

    @Transactional
    @Test
    public void testGetAllEquipmentType() {
        final List<EquipmentType> equipmentTypeList = this.equipmentTypeService.getAllEquipmentType();
        junit.framework.Assert.assertNotNull("EquipmentType List is null", equipmentTypeList);
        junit.framework.Assert.assertTrue("EquipmentType List is Empty", !equipmentTypeList.isEmpty());
    }


    @Transactional
    @Test
    public void testEquipmentTypeByNameWithDependentEntities() {
        final EquipmentType equipmentType = this.equipmentTypeService.findByEquipmentTypeName("1 Pallet Jack", true);
        junit.framework.Assert.assertNotNull("EquipmentType  is null", equipmentType);
    }

    @Transactional
    @Test
    public void testGetAllEquipmentTypeBySearchAnsSortByEquipmentTypeName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentTypeName", new ArrayList<String>() {
                            {
                                add("%Pallet Jack%");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final List<EquipmentType> equipmentTypeList = this.equipmentTypeService.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("EquipmentType List is null", equipmentTypeList);
        junit.framework.Assert.assertTrue("EquipmentType List is Empty", !equipmentTypeList.isEmpty());
    }

    @Transactional
    @Test
    public void testGetAllEquipmentTypeBySearchAnsSortByEquipmentStyle() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("generic");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentStyle.equipmentStyleName", SortType.ASC);
                    }
                });
            }
        };
        final List<EquipmentType> equipmentTypeList = this.equipmentTypeService.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("EquipmentType List is null", equipmentTypeList);
        junit.framework.Assert.assertTrue("EquipmentType List is Empty", !equipmentTypeList.isEmpty());
    }

    @Transactional
    @Test
    public void testGetAllEquipmentTypeBySearchAnsSortTotalCount() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentTypeName", new ArrayList<String>() {
                            {
                                add("%Pallet Jack%");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentTypeService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("EquipmentType Total Count is null", totalCount);
    }

    @Transactional
    @Test
    public void testGetEquipmentTypeByEquipmentStyleSearchAnsSortTotalCount() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("generic");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentStyle.equipmentStyleName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentTypeService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("EquipmentType Total Count is null", totalCount);
    }


    @Transactional
    @Test
    public void testGetEquipmentTypeList() throws ParseException, Exception {
        final List<String> equipmentTypeList = this.equipmentTypeService.getEquipmentTypeList();
        junit.framework.Assert.assertNotNull("EquipmentType List is null", equipmentTypeList);
        junit.framework.Assert.assertTrue("EquipmentType List is Empty", !equipmentTypeList.isEmpty());
    }

    /* Test method to soft delete an EquipmentType
    *
    * @throws Exception
    *
    */
    @Rollback(value = true)
    @Transactional
    @Test
    public void testDeleteEquipmentByType() throws ParseException, Exception {
        final Map<String, String> deleteEquipment = new HashMap<String, String>();
        deleteEquipment.put("type", "8 Test Position Cart");
        final Map<String, String> resultMap = this.equipmentTypeService.deleteEquipmentByType(deleteEquipment.get("type"));
        junit.framework.Assert.assertNotNull("ResultMap is null", resultMap);
        junit.framework.Assert.assertTrue("ResultMap is Empty", !resultMap.isEmpty());
        junit.framework.Assert.assertTrue("ResultMap does not contain Key", resultMap.containsKey("EQUIPMENT_SOFT_DELETE_CODE"));
        junit.framework.Assert.assertTrue("ResultMap does not contain Value", resultMap.containsValue("EQUIPMENT_SOFT_DELETE_CODE"));
    }

    /**
     * testDeleteEquipmentTypePositionByEquipmentTypeId() provide the functionality for deletion of the
     * EquipmentTypePosition based on EquipmentTypeId
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testDeleteEquipmentTypePositionByEquipmentTypeId(){
          final boolean result = this.equipmentTypeService.deleteEquipmentTypePositionByEquipmentTypeId(4L);
        junit.framework.Assert.assertTrue("Deletion is Not Successful ",result);
    }

    /**
     * testDeleteAnswerEvaluationRuleByEquipmentTypeId() provide the functionality for deletion of the
     * AnswerEvaluationRule based on EquipmentTypeId
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testDeleteAnswerEvaluationRuleByEquipmentTypeId(){
        final boolean result = this.equipmentTypeService.deleteAnswerEvaluationRuleByEquipmentTypeId(1L);
        junit.framework.Assert.assertTrue("Deletion is Not Successful ",result);
    }

    /**
     * testDeleteEquipmentTypeQuestionByEquipmentTypeId() provide the functionality for deletion of the
     * EquipmentTypeQuestion based on EquipmentTypeId
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testDeleteEquipmentTypeQuestionByEquipmentTypeId(){
        final boolean result = this.equipmentTypeService.deleteEquipmentTypeQuestionByEquipmentTypeId(1L);
        junit.framework.Assert.assertTrue("Deletion is Not Successful ",result);
    }

    /**
     * testDeleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId() provide the functionality for deletion of the
     * LucasUserCertifiedEquipmentType based on EquipmentTypeId
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testDeleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(){
        final boolean result = this.equipmentTypeService.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(7L);
        junit.framework.Assert.assertTrue("Deletion is Not Successful ",result);
    }

    /**
     * testSaveEquipmentType() provide the functionality of testing the EquipmentType saveing
     * into the db using EquipmentTypeService.saveEquipmentType()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentType() {
        final EquipmentTypePosition equipmentTypePosition1 = new EquipmentTypePosition();
        equipmentTypePosition1.setEquipmentTypePositionNbr(1L);
        equipmentTypePosition1.setXPosition(1L);
        equipmentTypePosition1.setYPosition(1L);
        equipmentTypePosition1.setZPosition(1L);

        final EquipmentTypePosition equipmentTypePosition2 = new EquipmentTypePosition();
        equipmentTypePosition2.setEquipmentTypePositionNbr(2L);
        equipmentTypePosition2.setXPosition(2L);
        equipmentTypePosition2.setYPosition(2L);
        equipmentTypePosition2.setZPosition(2L);

        final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<EquipmentTypePosition>();
        equipmentTypePositionList.add(equipmentTypePosition1);
        equipmentTypePositionList.add(equipmentTypePosition2);

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");


        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("EquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("EquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(2L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final EquipmentType savedEquipmentType=this.equipmentTypeService.findByEquipmentTypeName("EquipmentTypeName");

        org.springframework.util.Assert.notNull(savedEquipmentType, "Equipment Type is null Persisted ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getEquipmentStyle().getEquipmentStyleCode().equals("generic"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getEquipmentTypeDescription().equals("EquipmentTypeDescription"), "Equipment Type Description is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getContainerType().getContainerCode().equals("Carton"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getLucasUserCertifiedEquipmentTypes().size() >=1, "Equipment Type Certified User Count is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getAnswerEvaluationRules().size() >=2, "Equipment Type AnswerEvaluationRules Count is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipmentType.getEquipmentTypePositionList().size()>=2, "Equipment Type EquipmentTypePosition Count is invalid  ");
    }

    /**
     * testSaveEquipmentTypeWithOutEquipmentTypePosition() provide the functionality of testing the EquipmentType saving
     * into the db using EquipmentTypeService.saveEquipmentType() where EquipmentTypePosition is missing.
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentTypeWithOutEquipmentTypePosition() {

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");


        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("EquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("EquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(null);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(2L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(!result, "Equipment Type is Persisted ");
    }

    /**
     * testSaveEquipmentTypeWithOutEquipmentStyle() provide the functionality of testing the EquipmentType saveing
     * into the db using EquipmentTypeService.saveEquipmentType() where EquipmentStyle is missing.
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentTypeWithOutEquipmentStyle() {
        final EquipmentTypePosition equipmentTypePosition1 = new EquipmentTypePosition();
        equipmentTypePosition1.setEquipmentTypePositionNbr(1L);
        equipmentTypePosition1.setXPosition(1L);
        equipmentTypePosition1.setYPosition(1L);
        equipmentTypePosition1.setZPosition(1L);

        final EquipmentTypePosition equipmentTypePosition2 = new EquipmentTypePosition();
        equipmentTypePosition2.setEquipmentTypePositionNbr(2L);
        equipmentTypePosition2.setXPosition(2L);
        equipmentTypePosition2.setYPosition(2L);
        equipmentTypePosition2.setZPosition(2L);

        final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<EquipmentTypePosition>();
        equipmentTypePositionList.add(equipmentTypePosition1);
        equipmentTypePositionList.add(equipmentTypePosition2);

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");


        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(null);
        equipmentType.setEquipmentTypeName("EquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("EquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(2L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(!result, "Equipment Type is Persisted ");
    }

    /**
     * testSaveEquipmentType() provide the functionality of testing the EquipmentType saveing
     * into the db using EquipmentTypeService.saveEquipmentType()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentTypeWithOutContainerType() {
        final EquipmentTypePosition equipmentTypePosition1 = new EquipmentTypePosition();
        equipmentTypePosition1.setEquipmentTypePositionNbr(1L);
        equipmentTypePosition1.setXPosition(1L);
        equipmentTypePosition1.setYPosition(1L);
        equipmentTypePosition1.setZPosition(1L);

        final EquipmentTypePosition equipmentTypePosition2 = new EquipmentTypePosition();
        equipmentTypePosition2.setEquipmentTypePositionNbr(2L);
        equipmentTypePosition2.setXPosition(2L);
        equipmentTypePosition2.setYPosition(2L);
        equipmentTypePosition2.setZPosition(2L);

        final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<EquipmentTypePosition>();
        equipmentTypePositionList.add(equipmentTypePosition1);
        equipmentTypePositionList.add(equipmentTypePosition2);

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");


        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("EquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("EquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(null);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(2L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(!result, "Equipment Type is Persisted ");
    }

    /**
     * testUpdateEquipmentTypeWithAllDependency() provide the functionality of testing the EquipmentType saving
     * into the db using EquipmentTypeService.saveEquipmentType()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUpdateEquipmentTypeWithAllDependency() {
        //saving the equipmentType first for testing update functionality.
        this.testSaveEquipmentType();
        final EquipmentTypePosition equipmentTypePosition1 = new EquipmentTypePosition();
        equipmentTypePosition1.setEquipmentTypePositionNbr(1L);
        equipmentTypePosition1.setXPosition(1L);
        equipmentTypePosition1.setYPosition(1L);
        equipmentTypePosition1.setZPosition(1L);

        final EquipmentTypePosition equipmentTypePosition2 = new EquipmentTypePosition();
        equipmentTypePosition2.setEquipmentTypePositionNbr(2L);
        equipmentTypePosition2.setXPosition(2L);
        equipmentTypePosition2.setYPosition(2L);
        equipmentTypePosition2.setZPosition(2L);

        final EquipmentTypePosition equipmentTypePosition3 = new EquipmentTypePosition();
        equipmentTypePosition3.setEquipmentTypePositionNbr(3L);
        equipmentTypePosition3.setXPosition(3L);
        equipmentTypePosition3.setYPosition(3L);
        equipmentTypePosition3.setZPosition(3L);

        final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<EquipmentTypePosition>();
        equipmentTypePositionList.add(equipmentTypePosition1);
        equipmentTypePositionList.add(equipmentTypePosition2);
        equipmentTypePositionList.add(equipmentTypePosition3);

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");

        final Question question3 = new Question();
        question3.setQuestionId(3L);

        final AnswerEvaluationRule answerEvaluationRule3 = new AnswerEvaluationRule();
        answerEvaluationRule3.setQuestion(question3);
        answerEvaluationRule3.setOperator("=");
        answerEvaluationRule3.setOperand("True");

        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);
        answerEvaluationRuleList.add(answerEvaluationRule3);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("UpdatedEquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("UpdatedEquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(3L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final EquipmentType updatedEquipmentType=this.equipmentTypeService.findByEquipmentTypeName("UpdatedEquipmentTypeName",true);
        org.springframework.util.Assert.notNull(updatedEquipmentType, "Updated Equipment Type is not Found ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypeName().equals("UpdatedEquipmentTypeName"), "Equipment Type Name is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypeDescription().equals("UpdatedEquipmentTypeDescription"), "Equipment Type Description is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentStyle().getEquipmentStyleCode().equals("generic"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getContainerType().getContainerCode().equals("Carton"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getLucasUserCertifiedEquipmentTypes().size() ==1, "Equipment Type Certified User Count is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getAnswerEvaluationRules().size() ==3, "Equipment Type AnswerEvaluationRules Count is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypePositionList().size()==3, "Equipment Type EquipmentTypePosition Count is invalid  ");
    }


    /**
     * testUpdateEquipmentTypeWithoutEquipmentTypePositionDependency() provide the functionality of testing the EquipmentType updating
     * into the db using EquipmentTypeService.saveEquipmentType() where EquipmentTypePosition is missing while update
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUpdateEquipmentTypeWithoutEquipmentTypePositionDependency() {
        //saving the equipmentType first for testing update functionality.
        this.testSaveEquipmentType();

        final Question question1 = new Question();
        question1.setQuestionId(1L);

        final AnswerEvaluationRule answerEvaluationRule1 = new AnswerEvaluationRule();
        answerEvaluationRule1.setQuestion(question1);
        answerEvaluationRule1.setOperator("=");
        answerEvaluationRule1.setOperand("True");

        final Question question2 = new Question();
        question2.setQuestionId(2L);

        final AnswerEvaluationRule answerEvaluationRule2 = new AnswerEvaluationRule();
        answerEvaluationRule2.setQuestion(question2);
        answerEvaluationRule2.setOperator("=");
        answerEvaluationRule2.setOperand("True");

        final Question question3 = new Question();
        question3.setQuestionId(3L);

        final AnswerEvaluationRule answerEvaluationRule3 = new AnswerEvaluationRule();
        answerEvaluationRule3.setQuestion(question3);
        answerEvaluationRule3.setOperator("=");
        answerEvaluationRule3.setOperand("True");

        final List<AnswerEvaluationRule> answerEvaluationRuleList = new ArrayList<AnswerEvaluationRule>();
        answerEvaluationRuleList.add(answerEvaluationRule1);
        answerEvaluationRuleList.add(answerEvaluationRule2);
        answerEvaluationRuleList.add(answerEvaluationRule3);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("UpdatedEquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("UpdatedEquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(null);
        equipmentType.setAnswerEvaluationRules(answerEvaluationRuleList);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(3L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(!result, "Equipment Type is Persisted ");
    }


    /**
     * testUpdateEquipmentTypeWithOutAnswerEvaluationRuleDependency() provide the functionality of testing the EquipmentType updating
     * into the db using EquipmentTypeService.saveEquipmentType() where AnswerEvaluationRule is missing
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUpdateEquipmentTypeWithOutAnswerEvaluationRuleDependency() {
        //saving the equipmentType first for testing update functionality.
        this.testSaveEquipmentType();
        final EquipmentTypePosition equipmentTypePosition1 = new EquipmentTypePosition();
        equipmentTypePosition1.setEquipmentTypePositionNbr(1L);
        equipmentTypePosition1.setXPosition(1L);
        equipmentTypePosition1.setYPosition(1L);
        equipmentTypePosition1.setZPosition(1L);

        final EquipmentTypePosition equipmentTypePosition2 = new EquipmentTypePosition();
        equipmentTypePosition2.setEquipmentTypePositionNbr(2L);
        equipmentTypePosition2.setXPosition(2L);
        equipmentTypePosition2.setYPosition(2L);
        equipmentTypePosition2.setZPosition(2L);

        final EquipmentTypePosition equipmentTypePosition3 = new EquipmentTypePosition();
        equipmentTypePosition3.setEquipmentTypePositionNbr(3L);
        equipmentTypePosition3.setXPosition(3L);
        equipmentTypePosition3.setYPosition(3L);
        equipmentTypePosition3.setZPosition(3L);

        final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<EquipmentTypePosition>();
        equipmentTypePositionList.add(equipmentTypePosition1);
        equipmentTypePositionList.add(equipmentTypePosition2);
        equipmentTypePositionList.add(equipmentTypePosition3);


        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType = new LucasUserCertifiedEquipmentType();
        lucasUserCertifiedEquipmentType.setUser(jackUser);

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = new ArrayList<LucasUserCertifiedEquipmentType>();
        lucasUserCertifiedEquipmentTypeList.add(lucasUserCertifiedEquipmentType);

        final EquipmentStyle equipmentStyle = new EquipmentStyle();
        equipmentStyle.setEquipmentStyleCode("generic");

        final ContainerType containerType = new ContainerType();
        containerType.setContainerCode("Carton");

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentStyle(equipmentStyle);
        equipmentType.setEquipmentTypeName("UpdatedEquipmentTypeName");
        equipmentType.setEquipmentTypeDescription("UpdatedEquipmentTypeDescription");
        equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        equipmentType.setAnswerEvaluationRules(null);
        equipmentType.setContainerType(containerType);
        equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
        equipmentType.setEquipmentTypePositions(3L);
        equipmentType.setEquipmentCount(0L);
        equipmentType.setEquipments(null);
        final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final EquipmentType updatedEquipmentType=this.equipmentTypeService.findByEquipmentTypeName("UpdatedEquipmentTypeName",true);
        org.springframework.util.Assert.notNull(updatedEquipmentType, "Updated Equipment Type is not Found ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypeName().equals("UpdatedEquipmentTypeName"), "Equipment Type Name is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypeDescription().equals("UpdatedEquipmentTypeDescription"), "Equipment Type Description is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentStyle().getEquipmentStyleCode().equals("generic"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getContainerType().getContainerCode().equals("Carton"), "Equipment Type Style code is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getLucasUserCertifiedEquipmentTypes().size() ==1, "Equipment Type Certified User Count is invalid ");
        org.springframework.util.Assert.isTrue(updatedEquipmentType.getEquipmentTypePositionList().size()==3, "Equipment Type EquipmentTypePosition Count is invalid  ");
    }

}
