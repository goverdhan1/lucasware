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

import com.lucas.dao.equipment.*;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.equipment.*;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.sort.SortType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * EquipmentTypeService Operations
 */
@RunWith(MockitoJUnitRunner.class)
public class EquipmentTypeServiceUnitTest {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentTypeServiceUnitTest.class);

    @Mock
    private UserDAO userDAO;
    @Mock
    private EquipmentDAO equipmentDAO;
    @Mock
    private ContainerTypeDAO containerTypeDAO;
    @Mock
    private EquipmentTypeDAO equipmentTypeDAO;
    @Mock
    private EquipmentStyleDAO equipmentStyleDAO;
    @Mock
    private AnswerEvaluationRuleDAO answerEvaluationRuleDAO;
    @Mock
    private EquipmentTypeQuestionDAO equipmentTypeQuestionDAO;
    @Mock
    private EquipmentTypePositionDAO equipmentTypePositionDAO;
    @Mock
    private LucasUserCertifiedEquipmentTypeDAO lucasUserCertifiedEquipmentTypeDAO;

    @Mock
    private QuestionDAO questionDAO;
    @Mock
    private AnswerTypeDAO answerTypeDAO;
    @Mock
    private QuestionTypeDAO questionTypeDAO;
    @Mock
    private QuestionService questionService;

    @Mock
    private SecurityService securityService;

    @Mock
    private EquipmentTypeService equipmentTypeService;

    private EquipmentStyle equipmentStyle;
    private ContainerType containerType;
    private EquipmentType equipmentType;
    private Question question;
    private EquipmentTypeQuestion equipmentTypeQuestion;
    private AnswerEvaluationRule answerEvaluationRule;
    private User user;
    private EquipmentTypePosition equipmentTypePosition;
    private List<EquipmentType> equipmentTypeList;
    private List<ContainerType> containerTypeList;
    private List<EquipmentStyle> equipmentStyleList;
    private List<String> equipmentTypeNameList;
    final List<String> equipmentStyleNameList=new ArrayList<String>();
    private List<Equipment> equipmentList=new ArrayList<Equipment>();;
    private Map<String,String> resultMap;

    @org.junit.Before
    public void initEquipmentTypeServiceFunctionalTest() {
        LOG.info("init EquipmentTypeServiceUnitTest invocation");
        this.userDAO = mock(UserDAO.class);
        this.equipmentDAO = mock(EquipmentDAO.class);
        this.containerTypeDAO = mock(ContainerTypeDAO.class);
        this.equipmentTypeDAO = mock(EquipmentTypeDAO.class);
        this.equipmentStyleDAO = mock(EquipmentStyleDAO.class);
        this.answerEvaluationRuleDAO = mock(AnswerEvaluationRuleDAO.class);
        this.equipmentTypeQuestionDAO = mock(EquipmentTypeQuestionDAO.class);
        this.equipmentTypePositionDAO = mock(EquipmentTypePositionDAO.class);
        this.lucasUserCertifiedEquipmentTypeDAO = mock(LucasUserCertifiedEquipmentTypeDAO.class);
        this.questionDAO = mock(QuestionDAO.class);
        this.answerTypeDAO=mock(AnswerTypeDAO.class);
        this.questionTypeDAO=mock(QuestionTypeDAO.class);
        this.equipmentTypeQuestionDAO=mock(EquipmentTypeQuestionDAO.class);
        this.answerEvaluationRuleDAO=mock(AnswerEvaluationRuleDAO.class);
        this.questionService=new QuestionServiceImpl(questionDAO, answerTypeDAO
                , questionTypeDAO, equipmentTypeDAO
                , equipmentTypeQuestionDAO, answerEvaluationRuleDAO);
        this.equipmentTypeService = new EquipmentTypeServiceImpl(userDAO, equipmentDAO, containerTypeDAO
                , equipmentTypeDAO, equipmentStyleDAO
                , answerEvaluationRuleDAO, equipmentTypeQuestionDAO
                , equipmentTypePositionDAO, lucasUserCertifiedEquipmentTypeDAO,questionService,securityService);

        this.equipmentType = new EquipmentType();
        this.containerType = new ContainerType();
        this.equipmentStyle = new EquipmentStyle();
        this.question=new Question();
        this.user=new User();
        this.equipmentTypePosition=new EquipmentTypePosition();
        this.equipmentTypeQuestion=new EquipmentTypeQuestion();
        this.answerEvaluationRule=new AnswerEvaluationRule();
        this.containerTypeList = new ArrayList<ContainerType>();
        this.equipmentStyleList = new ArrayList<EquipmentStyle>();
        this.equipmentTypeList = new ArrayList<EquipmentType>();
    }

    @Test
    public void testFetchEquipmentStyleByCode() {
        when(this.equipmentStyleDAO.findByEquipmentStyleCode("generic")).thenReturn(equipmentStyle);
        when(this.equipmentTypeService.findByEquipmentStyleCode("generic")).thenReturn(equipmentStyle);
        this.equipmentTypeService.findByEquipmentStyleCode("generic");
        verify(equipmentStyleDAO, times(1)).findByEquipmentStyleCode(anyString());
    }

    @Test
    public void testFetchAllEquipmentStyle() {
        when(this.equipmentStyleDAO.getAllEquipmentStyle()).thenReturn(equipmentStyleList);
        when(this.equipmentTypeService.getAllEquipmentStyle()).thenReturn(equipmentStyleList);
        this.equipmentTypeService.getAllEquipmentStyle();
        verify(equipmentStyleDAO, atLeastOnce()).getAllEquipmentStyle();
    }

    @Test
    public void testFetchContainerTypeByCode() {
        when(this.containerTypeDAO.findByContainerTypeCode("Carton")).thenReturn(containerType);
        when(this.equipmentTypeService.findByContainerTypeCode("Carton")).thenReturn(containerType);
        this.equipmentTypeService.findByContainerTypeCode("Carton");
        verify(containerTypeDAO, atLeastOnce()).findByContainerTypeCode(anyString());

    }

    @Test
    public void testFetchAllContainerType() {
        when(this.containerTypeDAO.getAllContainerType()).thenReturn(containerTypeList);
        when(this.equipmentTypeService.getAllContainerType()).thenReturn(containerTypeList);
        this.equipmentTypeService.getAllContainerType();
        verify(containerTypeDAO, atLeastOnce()).getAllContainerType();
    }

    @Test
    public void testGetAllEquipmentType() {
        when(this.equipmentTypeDAO.getAllEquipmentType()).thenReturn(equipmentTypeList);
        when(this.equipmentTypeService.getAllEquipmentType()).thenReturn(equipmentTypeList);
        this.equipmentTypeService.getAllEquipmentType();
        verify(equipmentTypeDAO, atLeastOnce()).getAllEquipmentType();

    }

    @Test
    public void testEquipmentTypeByNameWithDependentEntities() {
        when(this.equipmentTypeDAO.findByEquipmentTypeName("1 Pallet Jack")).thenReturn(equipmentType);
        when(this.equipmentTypeService.findByEquipmentTypeName("1 Pallet Jack", false)).thenReturn(equipmentType);
        this.equipmentTypeService.findByEquipmentTypeName("1 Pallet Jack", false);
        verify(equipmentTypeDAO, atLeastOnce()).findByEquipmentTypeName(anyString());
    }

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
        when(this.equipmentTypeDAO.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria)).thenReturn(equipmentTypeList);
        when(this.equipmentTypeService.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria)).thenReturn(equipmentTypeList);
        this.equipmentTypeService.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
        verify(equipmentTypeDAO, atLeastOnce()).getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
    }
      

    @Test
    public void testGetAllEquipmentStyleNames(){
        when(this.equipmentStyleDAO.getAllEquipmentStyle()).thenReturn(equipmentStyleList);
        when(this.equipmentTypeService.getAllEquipmentStyle()).thenReturn(equipmentStyleList);
        when(this.equipmentTypeService.getAllEquipmentStyleNames()).thenReturn(equipmentStyleNameList);
    }
    
    /* Test method to soft delete an EquipmentType
    *
    * @throws ParsException, Exception
    *
    */
    @Test
    public void testDeleteEquipmentByType() throws ParseException, Exception{
        when(this.equipmentTypeDAO.findByEquipmentTypeName("8 Position Cart")).thenReturn(equipmentType);
        when(this.equipmentDAO.findEquipmentListByTypeId(5L)).thenReturn(equipmentList);
        when(this.equipmentTypeService.deleteEquipmentByType("8 Position Cart")).thenReturn(resultMap);
    }

    /**
     * testDeleteEquipmentTypePositionByEquipmentTypeId() mock test deletion for EquipmentTypePosition
     * based on EquipmentTypeId
     */
    @Test
    public void testDeleteEquipmentTypePositionByEquipmentTypeId(){
        when(this.equipmentTypePositionDAO.deleteEquipmentTypePositionByEquipmentTypeId(4L)).thenReturn(true);
        when(this.equipmentTypeService.deleteEquipmentTypePositionByEquipmentTypeId(4L)).thenReturn(true);
    }

    /**
     * testDeleteAnswerEvaluationRuleByEquipmentTypeId() mock test deletion for AnswerEvaluationRule
     * based on EquipmentTypeId
     */
    @Test
    public void testDeleteAnswerEvaluationRuleByEquipmentTypeId(){
        when(this.answerEvaluationRuleDAO.deleteAnswerEvaluationRuleByEquipmentTypeId(1L)).thenReturn(true);
        when(this.equipmentTypeService.deleteAnswerEvaluationRuleByEquipmentTypeId(1L)).thenReturn(true);
    }

    /**
     * testDeleteEquipmentTypeQuestionByEquipmentTypeId() mock test deletion for EquipmentTypeQuestion
     * based on EquipmentTypeId
     */
    @Test
    public void testDeleteEquipmentTypeQuestionByEquipmentTypeId(){
        when(this.equipmentTypeQuestionDAO.deleteEquipmentTypeQuestionByEquipmentTypeId(1L)).thenReturn(true);
        when(this.equipmentTypeService.deleteEquipmentTypeQuestionByEquipmentTypeId(1L)).thenReturn(true);
    }

    /**
     * testDeleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId() mock test deletion for LucasUserCertifiedEquipmentType
     * based on EquipmentTypeId
     */
    @Test
    public void testDeleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(){
       when(this.lucasUserCertifiedEquipmentTypeDAO.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(7L)).thenReturn(true);
       when(this.equipmentTypeService.deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(7L)).thenReturn(true);
    }

    /**
     * testSaveEquipmentType() mock test for persisting (save or update) of the equipmentType into the db
     * with its dependent entices.
     */
    @Test
    public void testSaveEquipmentType(){
        when(this.equipmentTypeDAO.findByEquipmentTypeName("EquipmentTypeName")).thenReturn(equipmentType);
        when(this.containerTypeDAO.findByContainerTypeCode("Carton")).thenReturn(containerType);
        when(this.equipmentTypeDAO.save(equipmentType)).thenReturn(equipmentType);
        when(this.questionService.findQuestionById(anyLong())).thenReturn(question);
        when(this.answerEvaluationRuleDAO.save(answerEvaluationRule)).thenReturn(answerEvaluationRule);
        when(this.equipmentTypeQuestionDAO.save(equipmentTypeQuestion)).thenReturn(equipmentTypeQuestion);
        when(this.userDAO.findByUserName("jack123")).thenReturn(user);
        when(this.equipmentTypePositionDAO.save(equipmentTypePosition)).thenReturn(equipmentTypePosition);
    }
}
