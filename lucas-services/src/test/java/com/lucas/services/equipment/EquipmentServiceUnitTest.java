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

import com.lucas.dao.equipment.EquipmentDAO;
import com.lucas.dao.equipment.EquipmentPositionDAO;
import com.lucas.dao.equipment.EquipmentTypeDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentPosition;
import com.lucas.entity.user.User;
import com.lucas.services.security.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * EquipmentService Operations using mock data.
 */
@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceUnitTest {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentServiceUnitTest.class);

    @Mock
    private UserDAO userDAO;
    @Mock
    private EquipmentDAO equipmentDAO;
    @Mock
    private EquipmentTypeDAO equipmentTypeDAO;
    @Mock
    private EquipmentPositionDAO equipmentPositionDAO;

    @Mock
    private SecurityService securityService;



    @Mock
    private EquipmentService equipmentService;

    private User user;
    private Equipment equipment;
    private List<Equipment> equipmentList;
    private EquipmentPosition equipmentPosition;
    private List<EquipmentPosition> equipmentPositionList;


    @Before
    public void initEquipmentServiceFunctionalTest() {
        LOG.info("init EquipmentServiceUnitTest invocation");
        this.userDAO = mock(UserDAO.class);
        this.equipmentDAO=mock(EquipmentDAO.class);
        this.equipmentTypeDAO=mock(EquipmentTypeDAO.class);
        this.equipmentPositionDAO=mock(EquipmentPositionDAO.class);
        this.equipmentService=new EquipmentServiceImpl(equipmentDAO
                , equipmentPositionDAO, equipmentTypeDAO,userDAO,securityService);
        this.user=new User();
        this.equipment=new Equipment();
        this.equipmentList=new ArrayList<Equipment>();
        this.equipmentPosition=new EquipmentPosition();
        this.equipmentPositionList = new ArrayList<EquipmentPosition> ();
        this.equipmentPositionList.add(equipmentPosition);

    }

    @Test
    public void testGetAllEquipment() {
        when(this.equipmentDAO.getAllEquipment()).thenReturn(equipmentList);
        when(this.equipmentService.getAllEquipment()).thenReturn(equipmentList);
        this.equipmentService.getAllEquipment();
        verify(equipmentDAO, atLeastOnce()).getAllEquipment();
    }

    @Test
    public void testFindEquipmentByName(){
        when(this.equipmentPositionDAO.findEquipmentPositionByEquipmentId(1L)).thenReturn(equipmentPositionList);
        when(this.equipmentDAO.findByEquipmentName("01")).thenReturn(equipment);
        when(this.equipmentService.findByEquipmentName("01",anyBoolean())).thenReturn(equipment);
        this.equipmentService.findByEquipmentName("01", true);
        verify(equipmentDAO, atLeastOnce()).findByEquipmentName(anyString());
    }

    /**
     * testDeleteEquipmentPositionByEquipmentId() mock test deletion for EquipmentPosition
     * based on EquipmentId
     */
    @Test
    public void testDeleteEquipmentPositionByEquipmentId(){
        when(this.equipmentPositionDAO.deleteEquipmentPositionByEquipmentId(1L)).thenReturn(true);
        when(this.equipmentService.deleteEquipmentPositionByEquipmentId(1L)).thenReturn(true);
    }

    /**
     * testSaveEquipment() mock test for persisting (save or update) of the equipment into the db
     * with its dependent entices.
     */
    @Test
    public void testSaveEquipment(){
        when(this.equipmentDAO.findByEquipmentName("EquipmentName")).thenReturn(equipment);
        when(this.equipmentDAO.save(equipment)).thenReturn(equipment);
        when(this.userDAO.findByUserName("jack123")).thenReturn(user);
        when(this.equipmentPositionDAO.save(equipmentPosition)).thenReturn(equipmentPosition);
    }


    /**
     * testDeleteEquipmentByType() provide the mock testing functionality of deletion of
     * Equipment based on the equipment Name.
     * @throws ParseException
     * @throws Exception
     */
    @Test
    public void testDeleteEquipmentByType() throws ParseException, Exception{
        when(this.equipmentDAO.findByEquipmentName("01")).thenReturn(equipment);
        when(this.equipmentPositionDAO.deleteEquipmentPositionByEquipmentId(1L)).thenReturn(true);
        when(this.equipmentDAO.deleteEquipment(equipment)).thenReturn(anyBoolean());
        when(this.equipmentService.deleteEquipmentByName("01")).thenReturn(true);
    }
}
