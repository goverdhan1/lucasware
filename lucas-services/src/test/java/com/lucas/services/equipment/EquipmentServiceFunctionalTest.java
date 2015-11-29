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
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/16/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of testing the
 * EquipmentService Operations
 */
@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class EquipmentServiceFunctionalTest extends AbstractSpringFunctionalTests {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentServiceFunctionalTest.class);

    @Inject
    private EquipmentService equipmentService;
    private static String JACK_USERNAME = "jack123";
    private static String JACK_PASSWORD = "secret";
    private static String HASHED_PASSWORD = "aHashedPassword";
    private User user;
    private Authentication authentication;
    private SecurityContext securityContext ;
    private SimpleGrantedAuthority[] grantedAuthoritiesArr = {
            new SimpleGrantedAuthority("equipment-management-widget-access")
            , new SimpleGrantedAuthority("create-equipment")
            , new SimpleGrantedAuthority("edit-equipment")
            , new SimpleGrantedAuthority("delete-equipment")
    };


    @Before
    public void initEquipmentServiceFunctionalTest() {
        LOG.info("initEquipmentServiceFunctionalTest invocation");
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
    public void testGetAllEquipment() {
        final List<Equipment> equipmentList = this.equipmentService.getAllEquipment();
        Assert.assertNotNull("Equipment List is Null", equipmentList);
        Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }

    /**
     * testFindEquipmentByName() provide the functionality for testing the
     * Equipment selection from db based on Equipment Name.
     */
    @Transactional
    @Test
    public void testFindEquipmentByName() {
        final Equipment equipment1 = this.equipmentService.findByEquipmentName("01", true);
        Assert.assertNotNull("Equipment is Null for 01", equipment1);
        Assert.assertNotNull("EquipmentPosition is Null for 01", equipment1.getEquipmentPositionList());
        final Equipment equipment2 = this.equipmentService.findByEquipmentName("30", true);
        Assert.assertNotNull("Equipment is Null for 30", equipment2);
        Assert.assertNotNull("EquipmentPosition is Null for 30", equipment2.getEquipmentPositionList());
    }

    /**
     * testDeleteEquipmentPositionByEquipmentId() provide the functionality for deletion of the
     * EquipmentPosition based on EquipmentId
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testDeleteEquipmentPositionByEquipmentId() {
        final boolean result = this.equipmentService.deleteEquipmentPositionByEquipmentId(1L);
        junit.framework.Assert.assertTrue("Deletion is Not Successful ", result);
    }


    /**
     * testSaveEquipment() provide the functionality of testing the Equipment saving
     * into the db using EquipmentService.saveEquipment()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipment() {
        final EquipmentPosition equipmentPosition1 = new EquipmentPosition();
        equipmentPosition1.setEquipmentPositionNbr(1L);
        equipmentPosition1.setXPosition(1L);
        equipmentPosition1.setYPosition(1L);
        equipmentPosition1.setZPosition(1L);

        final EquipmentPosition equipmentPosition2 = new EquipmentPosition();
        equipmentPosition2.setEquipmentPositionNbr(2L);
        equipmentPosition2.setXPosition(2L);
        equipmentPosition2.setYPosition(2L);
        equipmentPosition2.setZPosition(2L);

        final List<EquipmentPosition> equipmentPositionList = new ArrayList<EquipmentPosition>();
        equipmentPositionList.add(equipmentPosition1);
        equipmentPositionList.add(equipmentPosition2);

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentTypeName("1 Pallet Jack");

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final Equipment equipment = new Equipment();
        equipment.setEquipmentName("EquipmentName");
        equipment.setEquipmentDescription("EquipmentDescription");
        equipment.setEquipmentPositionList(equipmentPositionList);
        equipment.setUser(jackUser);
        equipment.setEquipmentType(equipmentType);

        final boolean result = this.equipmentService.saveEquipment(equipment);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final Equipment savedEquipment = this.equipmentService.findByEquipmentName("EquipmentName", true);

        org.springframework.util.Assert.notNull(savedEquipment, "Equipment is Not Persisted ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentDescription().equals("EquipmentDescription"), "Equipment Description is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentType().getEquipmentTypeName().equals("1 Pallet Jack"), "Equipment Type is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getUser().getUserName().equals("jack123"), "Equipment User is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentPositionList().size() >= 2, "Equipment EquipmentPosition Count is invalid  ");

    }

    /**
     * testSaveEquipment() provide the functionality of testing the Equipment saving
     * into the db using EquipmentService.saveEquipment()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentWithOutUser() {
        final EquipmentPosition equipmentPosition1 = new EquipmentPosition();
        equipmentPosition1.setEquipmentPositionNbr(1L);
        equipmentPosition1.setXPosition(1L);
        equipmentPosition1.setYPosition(1L);
        equipmentPosition1.setZPosition(1L);

        final EquipmentPosition equipmentPosition2 = new EquipmentPosition();
        equipmentPosition2.setEquipmentPositionNbr(2L);
        equipmentPosition2.setXPosition(2L);
        equipmentPosition2.setYPosition(2L);
        equipmentPosition2.setZPosition(2L);

        final List<EquipmentPosition> equipmentPositionList = new ArrayList<EquipmentPosition>();
        equipmentPositionList.add(equipmentPosition1);
        equipmentPositionList.add(equipmentPosition2);

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentTypeName("1 Pallet Jack");

        final Equipment equipment = new Equipment();
        equipment.setEquipmentName("EquipmentName");
        equipment.setEquipmentDescription("EquipmentDescription");
        equipment.setEquipmentPositionList(equipmentPositionList);
        equipment.setUser(null);
        equipment.setEquipmentType(equipmentType);

        final boolean result = this.equipmentService.saveEquipment(equipment);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final Equipment savedEquipment = this.equipmentService.findByEquipmentName("EquipmentName", true);

        org.springframework.util.Assert.notNull(savedEquipment, "Equipment is Not Persisted ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentDescription().equals("EquipmentDescription"), "Equipment Description is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentType().getEquipmentTypeName().equals("1 Pallet Jack"), "Equipment Type is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getUser() == null, "Equipment User is Not Null");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentPositionList().size() >= 2, "Equipment EquipmentPosition Count is invalid  ");

    }


    /**
     * testSaveEquipment() provide the functionality of testing the Equipment saving
     * into the db using EquipmentService.saveEquipment()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentWithOutPositions() {

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentTypeName("1 Pallet Jack");

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final Equipment equipment = new Equipment();
        equipment.setEquipmentName("EquipmentName");
        equipment.setEquipmentDescription("EquipmentDescription");
        equipment.setEquipmentPositionList(null);
        equipment.setUser(jackUser);
        equipment.setEquipmentType(equipmentType);

        final boolean result = this.equipmentService.saveEquipment(equipment);
        org.springframework.util.Assert.isTrue(!result, "Equipment  is Persisted ");
    }


    /**
     * testSaveEquipmentWithOutEquipmentType() provide the functionality of testing the Equipment saving
     * into the db using EquipmentService.saveEquipment()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEquipmentWithOutEquipmentType() {
        final EquipmentPosition equipmentPosition1 = new EquipmentPosition();
        equipmentPosition1.setEquipmentPositionNbr(1L);
        equipmentPosition1.setXPosition(1L);
        equipmentPosition1.setYPosition(1L);
        equipmentPosition1.setZPosition(1L);

        final EquipmentPosition equipmentPosition2 = new EquipmentPosition();
        equipmentPosition2.setEquipmentPositionNbr(2L);
        equipmentPosition2.setXPosition(2L);
        equipmentPosition2.setYPosition(2L);
        equipmentPosition2.setZPosition(2L);

        final List<EquipmentPosition> equipmentPositionList = new ArrayList<EquipmentPosition>();
        equipmentPositionList.add(equipmentPosition1);
        equipmentPositionList.add(equipmentPosition2);

        final User jackUser = new User();
        jackUser.setUserName("jack123");

        final Equipment equipment = new Equipment();
        equipment.setEquipmentName("EquipmentName");
        equipment.setEquipmentDescription("EquipmentDescription");
        equipment.setEquipmentPositionList(equipmentPositionList);
        equipment.setUser(jackUser);
        equipment.setEquipmentType(null);

        final boolean result = this.equipmentService.saveEquipment(equipment);
        org.springframework.util.Assert.isTrue(!result, "Equipment Type is Persisted ");
    }


    /**
     * testUpdateEquipment() provide the functionality of testing the Equipment update
     * into the db using EquipmentService.saveEquipment()
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUpdateEquipment() {
        this.testSaveEquipment();

        final EquipmentPosition equipmentPosition1 = new EquipmentPosition();
        equipmentPosition1.setEquipmentPositionNbr(1L);
        equipmentPosition1.setXPosition(1L);
        equipmentPosition1.setYPosition(1L);
        equipmentPosition1.setZPosition(1L);

        final EquipmentPosition equipmentPosition2 = new EquipmentPosition();
        equipmentPosition2.setEquipmentPositionNbr(2L);
        equipmentPosition2.setXPosition(2L);
        equipmentPosition2.setYPosition(2L);
        equipmentPosition2.setZPosition(2L);

        final EquipmentPosition equipmentPosition3 = new EquipmentPosition();
        equipmentPosition3.setEquipmentPositionNbr(3L);
        equipmentPosition3.setXPosition(3L);
        equipmentPosition3.setYPosition(3L);
        equipmentPosition3.setZPosition(3L);

        final List<EquipmentPosition> equipmentPositionList = new ArrayList<EquipmentPosition>();
        equipmentPositionList.add(equipmentPosition1);
        equipmentPositionList.add(equipmentPosition2);
        equipmentPositionList.add(equipmentPosition3);

        final EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipmentTypeName("1 Pallet Jack");

        final User jackUser = new User();
        jackUser.setUserName("jill123");

        final Equipment equipment = new Equipment();
        equipment.setEquipmentName("EquipmentUpdateName");
        equipment.setEquipmentDescription("EquipmentUpdateDescription");
        equipment.setEquipmentPositionList(equipmentPositionList);
        equipment.setUser(jackUser);
        equipment.setEquipmentType(equipmentType);

        final boolean result = this.equipmentService.saveEquipment(equipment);
        org.springframework.util.Assert.isTrue(result, "Equipment Type isn't Persisted ");
        final Equipment savedEquipment = this.equipmentService.findByEquipmentName("EquipmentUpdateName", true);

        org.springframework.util.Assert.notNull(savedEquipment, "Equipment is Not Persisted ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentDescription().equals("EquipmentUpdateDescription"), "Equipment Description is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentType().getEquipmentTypeName().equals("1 Pallet Jack"), "Equipment Type is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getUser().getUserName().equals("jill123"), "Equipment User is invalid ");
        org.springframework.util.Assert.isTrue(savedEquipment.getEquipmentPositionList().size() >= 3, "Equipment EquipmentPosition Count is invalid  ");
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testEquipmentDeletionForExisting() {
        final boolean deletionStatus = this.equipmentService.deleteEquipmentByName("01");
        Assert.assertTrue("Deletion is Successful", deletionStatus);
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testEquipmentDeletionForWhichUserExisting() {
        try {
            this.equipmentService.deleteEquipmentByName("30");
        } catch (Exception e) {
            Assert.assertTrue("Deletion Shouldn't Deleted ", (e.getLocalizedMessage().contains("Equipment In Use")));
        }
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testEquipmentDeletionForNonExisting() {
        final boolean deletionStatus = this.equipmentService.deleteEquipmentByName("00");
        Assert.assertTrue("Deletion shouldn't be Successful", !deletionStatus);
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testEquipmentDeletionForNullInput() {
        final boolean deletionStatus = this.equipmentService.deleteEquipmentByName(null);
        Assert.assertTrue("Deletion shouldn't be Successful", !deletionStatus);
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testEquipmentDeletionForEmptyInput() {
        final boolean deletionStatus = this.equipmentService.deleteEquipmentByName("");
        Assert.assertTrue("Deletion shouldn't be Successful", !deletionStatus);
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUnAssignUserFromEquipment(){
        final boolean unAssignmentStatus = this.equipmentService.unAssignUserFromEquipment("30", "dummy-username25");
        Assert.assertTrue("UnAssign User From Equipment is Successful", unAssignmentStatus);
    }

    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUnAssignUserFromEquipmentWhenUserDoesNotAssign(){
        try{
             this.equipmentService.unAssignUserFromEquipment("01", "dummy-username25");
        }catch (Exception e){
            Assert.assertTrue("UnAssign User From Equipment isn't Successful",  (e.getLocalizedMessage().contains("Equipment Doesn't Assign to User")));
        }
    }


    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentName() getting the equipment based on EquipmentName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortByEquipmentName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentName", new ArrayList<String>() {
                            {
                                add("0");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentName", SortType.ASC);
                    }
                });
            }
        };
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentTypeName() getting the equipment based on EquipmentTypeName
     * @throws Exception
     */
    @Transactional
       @Test
       public void testGetAllEquipmentBySearchAnsSortByEquipmentTypeName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentType.equipmentTypeName", new ArrayList<String>() {
                            {
                                add("P");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentType.equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }


    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentStyleName() getting the equipment based on EquipmentStyleName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortByEquipmentStyleName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("g");
                                add("f");
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
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByUserName() getting the equipment based on UserName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortByUserName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.userName", new ArrayList<String>() {
                            {
                                add("dummy-username24");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.userName", SortType.ASC);
                    }
                });
            }
        };
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByUserFirstName() getting the equipment based on UserFirstName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortByUserFirstName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.firstName", new ArrayList<String>() {
                            {
                                add("dummy-firstName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.firstName", SortType.ASC);
                    }
                });
            }
        };
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByUserLastName() getting the equipment based on UserLastName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortByUserLastName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.lastName", new ArrayList<String>() {
                            {
                                add("dummy-LastName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.lastName", SortType.ASC);
                    }
                });
            }
        };
        final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment List is null", equipmentList);
        junit.framework.Assert.assertTrue("Equipment List is Empty", !equipmentList.isEmpty());
    }


    /**
     * testGetAllEquipmentBySearchAnsSortTotalCount() getting the equipment count based on equipmentName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCount() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentName", new ArrayList<String>() {
                            {
                                add("0");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }

    /**
     * testGetAllEquipmentBySearchAnsSortTotalCountByEquipmentTypeName() getting the equipment count based on EquipmentTypeName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCountByEquipmentTypeName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentType.equipmentTypeName", new ArrayList<String>() {
                            {
                                add("P");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentType.equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }

    /**
     *  testGetAllEquipmentBySearchAnsSortTotalCountByEquipmentStyleName() getting the equipment count based on EquipmentStyleName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCountByEquipmentStyleName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("g");
                                add("f");
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
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }

    /**
     * testGetAllEquipmentBySearchAnsSortTotalCountByUserName() getting the equipment count based on UserName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCountByUserName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.userName", new ArrayList<String>() {
                            {
                                add("dummy-username24");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.userName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }

    /**
     * testGetAllEquipmentBySearchAnsSortTotalCountByUserFirstName() getting the equipment count based on UserFirstName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCountByUserFirstName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.firstName", new ArrayList<String>() {
                            {
                                add("dummy-firstName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.firstName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }

    /**
     *  testGetAllEquipmentBySearchAnsSortTotalCountByUserLastName() getting the equipment count based on UserLastName
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetAllEquipmentBySearchAnsSortTotalCountByUserLastName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.lastName", new ArrayList<String>() {
                            {
                                add("dummy-LastName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.lastName", SortType.ASC);
                    }
                });
            }
        };
        final Long totalCount = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
        junit.framework.Assert.assertNotNull("Equipment Total Count is null", totalCount);
    }
}
