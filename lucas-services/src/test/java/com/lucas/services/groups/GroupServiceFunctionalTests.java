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

package com.lucas.services.groups;

import com.lucas.dto.user.PermissionGroupSummaryDTO;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.entity.user.Permission;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.services.user.GroupService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.*;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class GroupServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static String JACK_USERNAME = "jack123";
    private static String JILL_USERNAME = "jill123";
    private static String ADMIN_USER = "admin123";
    private static String GROUP_NAME = "picker";
    private static String PERMISSION_GROUPMAINTENACEWIDGETACCESS = "group-maintenance-widget-access";
    private static String PERMISSION_CREATEGROUP = "create-group";
    private static String PERMISSION_EDITGROUP = "edit-group";
    private static String PERMISSION_RETRAINVOICE = "retrain-voice-model";

    @Inject
    private GroupService groupService;

    @Transactional(readOnly = true)
    @Test
    public void testGetGroupsBySearchAndSortCriteria() throws IllegalArgumentException, IllegalStateException, Exception {
        // build the search map criteria
        final List<String> searchList = Arrays.asList("s%");
        final List<String> descList = Arrays.asList("%group%");
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userCountMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> descriptionMap = new LinkedHashMap<String, Object>();

        // groupName
        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);

        // description
        descriptionMap.put("filterType", FilterType.ALPHANUMERIC);
        descriptionMap.put("values", descList);

        //user count
        userCountMap.put("filterType", FilterType.NUMERIC);
        userCountMap.put("values", Arrays.asList(">1", "<=2"));


        searchMap.put("userCount", userCountMap);
        searchMap.put("permissionGroupName", searchPropertyMap);
        searchMap.put("description", descriptionMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("permissionGroupName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(8);

        List<PermissionGroupSummaryDTO> result = this.groupService.getGroupSummaryByNewSearchAndSortCriteria(searchAndSortCriteria);

        Assert.notNull(result);
        Assert.isTrue(result.size() == 1, "Invalid set of results returned.");
        Assert.isTrue(result.get(0).getGroupName().equals("supervisor"), "Invalid result returned.");
    }

    @Transactional(readOnly = true)
    @Test
    public void testGetAllPermissionGroupNames() throws ParseException {

        final List<String> groupList = this.groupService.getAllPermissionGroupNames();

        Assert.notNull(groupList);
        }

    @Transactional(readOnly = true)
    @Test
    public void testGetPermissionGroupSummary() throws ParseException {

        final Map<String, Long> permissionGroupSummary = this.groupService.getPermissionGroupSummary();

        Assert.notNull(permissionGroupSummary);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetails() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testSave = false;
        Throwable e = null;
        Throwable e2 = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        userList.add(JILL_USERNAME);
        userList.add(ADMIN_USER);

        // Create permissions list
        List<String> permList = new ArrayList<String>();
        permList.add(PERMISSION_GROUPMAINTENACEWIDGETACCESS);
        permList.add(PERMISSION_CREATEGROUP);
        permList.add(PERMISSION_EDITGROUP);
        permList.add(PERMISSION_RETRAINVOICE);

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO(GROUP_NAME,"Picker group for users with picking access", permList, userList);

        // save user group details
        try {
            testSave = groupService.saveGroupDetails(permissionGroupsDTO);
        }
        catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(testSave, "User Group Details save unsuccessful");

        // retrieve user group details to check saved data
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails(GROUP_NAME);
        }
        catch (Throwable ex) {
            e2 = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase(GROUP_NAME), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getDescription().equalsIgnoreCase("Picker group for users with picking access"), "Description does not match " + resultPermissionGroupsDTO.getDescription());
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_GROUPMAINTENACEWIDGETACCESS));
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_CREATEGROUP));
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_EDITGROUP));
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_RETRAINVOICE));
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers().contains(JILL_USERNAME));
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers().contains(ADMIN_USER));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsGroupNull() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testSave = false;
        Throwable e = null;
        Throwable e2 = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        userList.add(JILL_USERNAME);
        userList.add(ADMIN_USER);

        // Create permissions list
        List<String> permList = new ArrayList<String>();
        permList.add(PERMISSION_GROUPMAINTENACEWIDGETACCESS);
        permList.add(PERMISSION_CREATEGROUP);
        permList.add(PERMISSION_EDITGROUP);
        permList.add(PERMISSION_RETRAINVOICE);

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO(null,"Picker group for users with picking access", permList, userList);

        // save user group details
        try {
            testSave = groupService.saveGroupDetails(permissionGroupsDTO);
        }
        catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof IllegalArgumentException, "User Group Name is null");

        PermissionGroupsDTO permissionGroupsDTOBlank = new PermissionGroupsDTO("","Picker group for users with picking access", permList, userList);

        // save user group details
        try {
            testSave = groupService.saveGroupDetails(permissionGroupsDTOBlank);
        }
        catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof IllegalArgumentException, "User Group Name is blank");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsDescNull() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testSave = false;
        Throwable e = null;
        Throwable e2 = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        userList.add(JILL_USERNAME);
        userList.add(ADMIN_USER);

        // Create permissions list
        List<String> permList = new ArrayList<String>();
        permList.add(PERMISSION_GROUPMAINTENACEWIDGETACCESS);
        permList.add(PERMISSION_CREATEGROUP);
        permList.add(PERMISSION_EDITGROUP);
        permList.add(PERMISSION_RETRAINVOICE);

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO(GROUP_NAME, null, permList, userList);

        // save user group details
        try {
            testSave = groupService.saveGroupDetails(permissionGroupsDTO);
        }
        catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(testSave, "User Group Details save unsuccessful");

        // retrieve user group details to check saved data
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails(GROUP_NAME);
        }
        catch (Throwable ex) {
            e2 = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase(GROUP_NAME), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isNull(resultPermissionGroupsDTO.getDescription());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsEmptyArray() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testSave = false;
        Throwable e = null;
        Throwable e2 = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        // Create permissions list
        List<String> permList = new ArrayList<String>();

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO(GROUP_NAME, "Picker group for users with picking access", permList, userList);

        // save user group details
        try {
            testSave = groupService.saveGroupDetails(permissionGroupsDTO);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(testSave, "User Group Details save unsuccessful");

        // retrieve user group details to check saved data
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails(GROUP_NAME);
        } catch (Throwable ex) {
            e2 = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase(GROUP_NAME), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getDescription().equalsIgnoreCase("Picker group for users with picking access"), "Description does not match " + resultPermissionGroupsDTO.getDescription());
        Assert.isTrue(!resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_GROUPMAINTENACEWIDGETACCESS));
        Assert.isTrue(!resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_CREATEGROUP));
        Assert.isTrue(!resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_EDITGROUP));
        Assert.isTrue(!resultPermissionGroupsDTO.getAssignedPermissions().contains(PERMISSION_RETRAINVOICE));
        Assert.isTrue(!resultPermissionGroupsDTO.getEnrolledUsers().contains(JILL_USERNAME));
        Assert.isTrue(!resultPermissionGroupsDTO.getEnrolledUsers().contains(ADMIN_USER));
    }


    @Test
    @Transactional(readOnly = true)
    public void testGetGroupDetails() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        Throwable e = null;

        // retrieve user group details
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails(GROUP_NAME);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase(GROUP_NAME), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions() instanceof ArrayList, "AssignedPermission is not an array list of string ");
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers() instanceof ArrayList, "AssignedPermission is not an array list of string ");
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetGroupDetailsNonExistGroup() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        Throwable e = null;

        // retrieve user group details
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails("pickerABCGroup");
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase("pickerABCGroup"), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions() instanceof ArrayList, "AssignedPermission is not an array list of string ");
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers() instanceof ArrayList, "AssignedPermission is not an array list of string ");
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetGroupDetailsBlankGroup() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        Throwable e = null;

        // retrieve user group details
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails("");
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName().equalsIgnoreCase(""), "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions() instanceof ArrayList, "AssignedPermission is not an array list of string ");
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers() instanceof ArrayList, "AssignedPermission is not an array list of string ");

        // retrieve user group details to check saved data
        try {
            resultPermissionGroupsDTO = groupService.getGroupDetails(null);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(resultPermissionGroupsDTO.getGroupName() == null, "Group name does not match " + resultPermissionGroupsDTO.getGroupName());
        Assert.isTrue(resultPermissionGroupsDTO.getAssignedPermissions() instanceof ArrayList, "AssignedPermission is not an array list of string ");
        Assert.isTrue(resultPermissionGroupsDTO.getEnrolledUsers() instanceof ArrayList, "AssignedPermission is not an array list of string ");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteGroupDetails() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testDelete = false;
        Throwable e = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        // Create permissions list
        List<String> permList = new ArrayList<String>();

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO(GROUP_NAME, "", permList, userList);

        // delete user group details
        try {
            testDelete = groupService.deleteGroupDetails(permissionGroupsDTO);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(testDelete, "User Group Details delete unsuccessful");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteGroupDetailsNonExistGroup() throws ParseException {
        PermissionGroupsDTO resultPermissionGroupsDTO = null;
        boolean testDelete = false;
        Throwable e = null;
        // Create user list
        List<String> userList = new ArrayList<String>();
        // Create permissions list
        List<String> permList = new ArrayList<String>();

        PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO("replen", "", permList, userList);

        // delete user group details
        try {
            testDelete = groupService.deleteGroupDetails(permissionGroupsDTO);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(testDelete==false, "User Group Details delete should be unsuccessful");
    }

}