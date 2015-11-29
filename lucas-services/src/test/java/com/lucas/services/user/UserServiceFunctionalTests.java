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
package com.lucas.services.user;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import com.lucas.entity.user.*;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.dto.user.UserFormFieldsDTO;
import com.lucas.dto.user.UsersAssignedGroupsDTO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.PermissionGroupDoesNotExistException;
import com.lucas.exception.UserDoesNotExistException;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.LucasDateRange;
import com.lucas.services.search.LucasNumericRange;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.services.util.DateUtils;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class UserServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static final int NORMAL_USER_PERMISSIONS_SIZE = 5;

    private static final String PERMISSION_GROUP = "aPermissionGroup";
    private static final String EXISTING_PERMISSION_GROUP = "picker";

    private static final String ADMIN_USERNAME = "admin123";

    private static final long ADMIN_ID = 1l;

    private static final Logger log = LoggerFactory.getLogger(UserServiceFunctionalTests.class);
    
    @Inject
    private EntityManagerFactory resourceEntityManagerFactory;

    // Since tests are not real application classes, it is OK to inject
    // individual dependencies as against
    // constructor injection as is done in all our Spring managed beans that
    // manage application classes.
    private Set<Permission> permSet = new HashSet<Permission>();

    @Inject
    private UserService userService;

    @Inject
    private ShiftService shiftService;


    @Inject
    private SupportedLanguageService languageService;

    @Inject
    private WmsUserService wmsUserService;

    private User user;

    private static String JACK_USERNAME = "jack123";
    private static String JACK_PASSWORD = "secret";

    private static String JILL_USERNAME = "jill123";
    private static String ADVANCED = "Advanced";

    private static String JACK_GROUP_NAME = "picker";

    private static String USERNAME = "aUserName";
    private static String PASSWORD = "goblygook";

    private static String ADMIN_USER = "admin123";
    private static String USER_DOES_NOT_EXISTS = "userDoesNotExists";
    
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceFunctionalTests.class);

    @Before
    public void setup() {

    	permSet.add(new Permission("group-maintenance-widget-access", 10L));
    	permSet.add(new Permission("create-group", 20L));
    	permSet.add(new Permission("edit-group", 30L));
    	
    	permSet.add(new Permission("search-user-widget-access", 10L));
    	permSet.add(new Permission("create-edit-user-widget-access", 20L));
    	permSet.add(new Permission("create-user", 30L));
    	permSet.add(new Permission("edit-user", 40L));
    	permSet.add(new Permission("disable-user", 50L));
    	permSet.add(new Permission("delete-user", 60L));
    	permSet.add(new Permission("enable-user", 70L));
    	permSet.add(new Permission("retrain-voice-model", 80L));
    	
    	permSet.add(new Permission("create-canvas", 10L));
    	permSet.add(new Permission("publish-canvas", 20L));
        permSet.add(new Permission("edit-company-canvas", 30L));
    	
    	permSet.add(new Permission("user-list-download-excel", 80L));
    	permSet.add(new Permission("user-list-download-pdf", 90L));
        
    	permSet.add(new Permission("create-product", 100L));
    	permSet.add(new Permission("view-product", 110L));
    	permSet.add(new Permission("edit-product", 120L));
    	permSet.add(new Permission("delete-product", 130L));
        
    	permSet.add(new Permission("create-assignment", 140L));
    	permSet.add(new Permission("view-report-productivity", 150L));
    	permSet.add(new Permission("configure-location", 160L));
    	permSet.add(new Permission("authenticated-user", 170L));
    	permSet.add(new Permission("edit-multi-user", 210L));
        
    	permSet.add(new Permission("pickline-by-wave-widget-access", 260L));
    	permSet.add(new Permission("search-product-grid-widget-access", 280L));

        java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

        user = new User();
        user.setUserName(USERNAME);
        user.setPlainTextPassword(PASSWORD);
        user.setCreatedByUserName(JACK_USERNAME);
        user.setUpdatedByUserName(JACK_USERNAME);
        user.setSeeHomeCanvasIndicator(new Boolean(true));
        user.setRetrainVoiceModel(new Boolean(false));
        user.setEnable(new Boolean(true));
        user.setDeletedIndicator(new Boolean(false));
        user.setCreatedDateTime(sqlDate);
        user.setUpdatedDateTime(sqlDate);
        user.setSkill(ADVANCED);
        user.setUserPreferences(new UserPreferences("DD-MM-YYY","24HR",120L));

        final SupportedLanguage language = languageService.findOneLanguageByCode("EN_US");
        user.setAmdLanguage(language);
        user.setU2jLanguage(language);
        user.setJ2uLanguage(language);
        user.setHhLanguage(language);

        final Shift shift = shiftService.findOneByShiftName("day");
        shift.setCreatedDateTime(sqlDate);
        shift.setUpdatedDateTime(sqlDate);
        user.setShift(shift);

        final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
        user.setWmsUser(wmsUser);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testUserInteractions() {
        try {
            userService.createUser(user);
        }
        catch (Throwable ex) {
        }
        User retrievedUser = userService.getUser(user.getUserName());
        Assert.isTrue(userService.isAuthenticateByHashedCredentials(USERNAME,
                retrievedUser.getHashedPassword()), "Failed isAuthenticateByHashedCredentials");
        Assert.isTrue(userService.isAuthenticateByPlainTextCredentials(
                USERNAME, PASSWORD),
                "Failed isAuthenticateByPlainTextCredentials");
    }

    @Test
    public void testUserWithoutPermission() {
        User user = userService.getUser(JACK_USERNAME, false);
        Assert.isTrue(user.getPermissionsSet().size() == 0, "Permissions size not equal to 0");
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testUserWithPermission() {
        List<PermissionGroup> userPermissionGroupList = new ArrayList<PermissionGroup>();
        Permission p30 = new Permission("dummy-permission1", 300L);
        Permission p31 = new Permission("dummy-permission2", 310L);
        Permission p32 = new Permission("dummy-permission3", 320L);
        Permission p33 = new Permission("dummy-permission4", 330L);
        Permission p34 = new Permission("dummy-permission5", 340L);
        Permission[] permissionArray1 = {p30, p31, p32, p33, p34};

        try {
            // Add permissions to permission group
            PermissionGroup pg = new PermissionGroup(PERMISSION_GROUP);
            pg.setPermissionGroupPermissionList(Arrays.asList(permissionArray1));
            PermissionGroup pg2 = new PermissionGroup(EXISTING_PERMISSION_GROUP);

            // Enroll user to permission group
            Set<User> userSet = new HashSet<User>();
            userSet.add(user);
            pg.setUserSet(userSet);

            // Add permission group to a list
            userPermissionGroupList.add(pg);
            userPermissionGroupList.add(pg2);

            // set the list into the user
            user.setUserPermissionGroupList(userPermissionGroupList);
            // update user
            userService.createUser(user);

        }
        catch (Throwable ex) {
        }
        User retrievedUser = userService.getUser(user.getUserName());
        Assert.notEmpty(retrievedUser.getPermissionsSet());
        Assert.isTrue(retrievedUser.getPermissionsSet().size() == 10);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testUserWithPermissionGroup() {
        List<PermissionGroup> userPermissionGroupList = new ArrayList<PermissionGroup>();
        Permission p30 = new Permission("dummy-permission1", 300L);
        Permission p31 = new Permission("dummy-permission2", 310L);
        Permission p32 = new Permission("dummy-permission3", 320L);
        Permission p33 = new Permission("dummy-permission4", 330L);
        Permission p34 = new Permission("dummy-permission5", 340L);
        Permission[] permissionArray1 = {p30, p31, p32, p33, p34};

        try {
            // Add permissions to permission group
            PermissionGroup pg = new PermissionGroup(PERMISSION_GROUP);
            pg.setPermissionGroupPermissionList(Arrays.asList(permissionArray1));

            // Enroll user to permission group
            Set<User> userSet = new HashSet<User>();
            userSet.add(user);
            pg.setUserSet(userSet);

            // Add permission group to a list
            userPermissionGroupList.add(pg);

            // set the list into the user
            user.setUserPermissionGroupList(userPermissionGroupList);
            // create new user with permission group
            userService.createUser(user);

        }
        catch (Throwable ex) {
        }
        User retrievedUser = userService.getUser(user.getUserName());
        Assert.notEmpty(retrievedUser.getPermissionsSet());
        Assert.isTrue(retrievedUser.getPermissionsSet().size() == NORMAL_USER_PERMISSIONS_SIZE);
    }

    @Test
    public void testToCheckAllPermissionForAdmin() {
        User user = userService.getUser(ADMIN_USER);
        Assert.notEmpty(user.getPermissionsSet());
        Assert.isTrue(user.getPermissionsSet().size() > 0);
        org.junit.Assert.assertEquals(permSet.size(), user.getPermissionsSet()
                .size());
        Assert.isTrue(permSet.containsAll(user.getPermissionsSet()));
    }

    @Test
    public void testIfUserDoesNotExists() {
        User user = userService.getUser(USER_DOES_NOT_EXISTS);
        org.junit.Assert.assertNull(user);
    }

    @Test
    public void testIfAdminUserHasAllPermissions() {
        User user = userService.getUser(ADMIN_USER);
        Set<Permission> pset = user.getPermissionsSet();
        Assert.notEmpty(pset);
        Assert.isTrue(pset.size() > 0);
        org.junit.Assert.assertEquals(permSet.size(), user.getPermissionsSet()
                .size());
        Assert.isTrue(permSet.containsAll(user.getPermissionsSet()));
    }

    @Transactional
    @Test
    public void testGetFullyHydratedUser() throws JsonParseException, JsonMappingException, IOException {
        User retrievedUser = userService.getFullyHydratedUser(JACK_USERNAME, JACK_PASSWORD);

        Assert.notNull(retrievedUser.getUserPermissionGroupList());

        Assert.isTrue(retrievedUser.getUserPermissionGroupList().size() == 4);
    }

    @Test
    public void testGetUserList() {
        String pageSize = "20";
        String pageNumber = "4";
        List<User> userList = userService.getUserList(pageSize, pageNumber);
        String ExcpectedUserName = "dummy-username80";
        Assert.isTrue(userList.get(0).getUserName().equals(ExcpectedUserName), "dummy-username80 was expected but got " + userList.get(0).getUserName() );
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteria() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();
        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("%dummy%"));
        searchMap.put("userName", userNameMap);

        // Start Date Range
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();
        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", Arrays.asList("2014-01-01", "2015-08-05"));
        searchMap.put("startDate", startDateMap);

        // userId numeric Range
        final LinkedHashMap<String, Object> userIdMap = new LinkedHashMap<String, Object>();
        userIdMap.put("filterType", FilterType.NUMERIC);
        userIdMap.put("values", Arrays.asList("0", "300"));
        searchMap.put("userId", userIdMap);


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(1000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() >= 1);

    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaForDefaultUser() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        // userId numeric Range
        final LinkedHashMap<String, Object> userIdMap = new LinkedHashMap<String, Object>();
        userIdMap.put("filterType", FilterType.NUMERIC);
        userIdMap.put("values", Arrays.asList("0", "300"));
        searchMap.put("userId", userIdMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() == 3);
    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaForJackUser() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("jack123"));
        searchMap.put("userName", userNameMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() != 0);
    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSearchCriteria() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> userNameMap = new LinkedHashMap<String, Object>();

        userNameMap.put("filterType", FilterType.ALPHANUMERIC);
        userNameMap.put("values", Arrays.asList("j%"));
        searchMap.put("userName", userNameMap);


        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        Assert.notNull(userList);
        Assert.isTrue(userList.size() == 3);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetActiveUsernamesFromSystem()
            throws ParseException, IOException, Exception {

        final List<String> userList = this.userService.getActiveUsernameList();

        Assert.notNull(userList);
        Assert.isTrue(userList.size() >= 1);

    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSortCriteria() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        searchAndSortCriteria.setSearchMap(null);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userName", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() >= 3);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithoutSearchAndSortEmptyMap() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>());
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(1000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() > 0);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithoutSearchAndSortNull() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        searchAndSortCriteria.setSearchMap(null);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(1000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() > 0);
    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaPageNumberAndPageSizeZero() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        searchAndSortCriteria.setSearchMap(null);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(0);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() > 0);
    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithIdRange() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        // userId numeric Range
        final LinkedHashMap<String, Object> userIdMap = new LinkedHashMap<String, Object>();
        userIdMap.put("filterType", FilterType.NUMERIC);
        userIdMap.put("values", Arrays.asList("0", "300"));
        searchMap.put("userId", userIdMap);



        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("userId", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(1000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() >= 1);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithDateRange() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> startDateMap = new LinkedHashMap<String, Object>();

        // Date Range
        startDateMap.put("filterType", FilterType.DATE);
        startDateMap.put("values", Arrays.asList("2013-01-27T00:00:00", "2015-03-27T23:59:59"));
        searchMap.put("startDate", startDateMap);

        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(null);

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(1000);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        Assert.isTrue(userList.size() >= 1);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithEmptyAsFilterCriteria() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> titleMap = new LinkedHashMap<String, Object>();

        titleMap.put("filterType", FilterType.EMPTY);
        titleMap.put("values", Arrays.asList(""));
        searchMap.put("title", titleMap);

        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);
        searchAndSortCriteria.setSearchMap(searchMap);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        for (User user : userList) {
        	Assert.isTrue(StringUtils.isBlank(user.getTitle()));
		}
    }
    

    @Transactional
    @Rollback(true)
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithNonEmptyAsFilterCriteria() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> titleMap = new LinkedHashMap<String, Object>();

        titleMap.put("filterType", FilterType.NON_EMPTY);
        titleMap.put("values", Arrays.asList(""));
        searchMap.put("title", titleMap);


        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(10);
        searchAndSortCriteria.setSearchMap(searchMap);
        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(userList);
        for (User user : userList) {
        	Assert.isTrue(StringUtils.isNotBlank(user.getTitle()));
		}
    }


    @Transactional
    @Rollback(true)
    @Test
    public void testGetFullyHydratedUserList() throws IOException {
        List<User> userList = new ArrayList();
        userList.add(this.userService.getUser(JACK_USERNAME));
        userList = this.userService.getFullyHydratedUserList(userList);
        for (final User user : userList) {

            Assert.notNull(user.getUserPermissionGroupList());

            List<PermissionGroup> pgl= user.getUserPermissionGroupList();

            Assert.isTrue(user.getUserPermissionGroupList().size() == 4);
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testDeleteUserWithUsername() throws Exception {
        userService.deleteUser(ADMIN_USERNAME);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testDeleteUserWithUserObject() throws Exception {
        User user = userService.getUser(ADMIN_USERNAME);
        userService.deleteUser(user);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testDeleteUserWithUserId() throws Exception {
        userService.deleteUser(ADMIN_ID);
    }

    @Transactional
    @Test
    public void testGetJackUserWithPermission() {
        final User jackUser = userService.getUser(JACK_USERNAME);
        Assert.notEmpty(jackUser.getPermissionsSet());
        Assert.isTrue(jackUser.getPermissionsSet().size() != 0);
    }


    @Transactional
    @Test
    public void testGetJillUserWithPermission() {
        User jillUser = userService.getUser(JILL_USERNAME);
        Assert.notEmpty(jillUser.getPermissionsSet());
        Assert.isTrue(jillUser.getPermissionsSet().size() != 0);
    }

    @Transactional(readOnly = true)
    @Test
    public void testJackHasNewPermissions() {
        String deleteUser = "delete-user";
        String enableUser = "enable-user";
        String disableUser = "disable-user";
        Permission deleteUserPermission = new Permission(deleteUser);
        Permission enableUserPermission = new Permission(enableUser);
        User userJack = userService.getUser(JACK_USERNAME);
        Set<Permission> jackPermissionSet = userJack.getPermissionsSet();
        Assert.notEmpty(jackPermissionSet);
//        Assert.isTrue(jackPermissionSet.contains(deleteUserPermission), "User jack does not have delete-user permission");
//        Assert.isTrue(jackPermissionSet.contains(enableUserPermission), "User jack does not have enable-user permission");
        
        org.junit.Assert.assertTrue("testJackHasNewPermissions() User jack does not have delete-user permission.",
        		jackPermissionSet.contains(deleteUserPermission));
        org.junit.Assert.assertTrue("testJackHasNewPermissions() User jack does not have enable-user permission.",
        		jackPermissionSet.contains(enableUserPermission));
    }

    @Transactional(readOnly = true)
    @Test
    public void testJillHasNewPermissions() {
        String authenticatedUser = "authenticated-user";
        Permission authenticatedUserPermission = new Permission(authenticatedUser);
        User userJill = userService.getUser(JILL_USERNAME);
        Set<Permission> jillPermissionSet = userJill.getPermissionsSet();
        Assert.notEmpty(jillPermissionSet);
//        Assert.isTrue(jillPermissionSet.contains(authenticatedUserPermission), "User jill does not have authenticated-user permission");
        
        org.junit.Assert.assertTrue("testJillHasNewPermissions() User jill does not have authenticated-user permission.",
        		jillPermissionSet.contains(authenticatedUserPermission));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUser() {
        final User user = this.getUserObject();
        user.setUserPreferences(null);

        user.setActiveCanvas(null);
        Throwable e = null;
        try {
            this.userService.createUser(user);
        }
        catch (Throwable ex) {
            e = ex;
        }

        final User persistedUser = this.userService.getUser(user.getUserName());
        
        Assert.notNull(persistedUser, "User is Not Persisted ");
        Assert.isTrue(persistedUser.getAmdLanguage().equals(user.getAmdLanguage()), "Amd Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getHhLanguage().equals(user.getHhLanguage()), "Handheld Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getU2jLanguage().equals(user.getU2jLanguage()), "User to Jenefer Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getJ2uLanguage().equals(user.getJ2uLanguage()), "Jenefer to User Lang isn't Persisted ");
    }

    /**
     * Method to test saving an existing user
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveAnExistingUser() {
        final User user = this.getUserObject();
        user.setUserName("dummy-username20");
        Throwable e = null;
        try {
            this.userService.createUser(user);
        }
        catch (Throwable ex) {
            e = ex;
        }

        final User persistedUser = this.userService.getUser(user.getUserName());

        Assert.notNull(persistedUser, "User is Not Persisted ");
        Assert.isTrue(persistedUser.getAmdLanguage().equals(user.getAmdLanguage()), "Amd Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getHhLanguage().equals(user.getHhLanguage()), "Handheld Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getU2jLanguage().equals(user.getU2jLanguage()), "User to Jenefer Lang isn't Persisted ");
        Assert.isTrue(persistedUser.getJ2uLanguage().equals(user.getJ2uLanguage()), "Jenefer to User Lang isn't Persisted ");
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testSaveUsers() {
        final List<User> userList = new ArrayList<User>();
        for (int index = 0; index < 50; index++) {
            userList.add(this.getUserObject());
        }
        Throwable e = null;
        try {
            this.userService.saveUsers(userList);
        }
        catch (Throwable ex) {
            e = ex;
        }
        
        for (User user : userList) {
            try {
                final User u = this.userService.getUser(user.getUserName());
                Assert.notNull(u, "User is Not Saved");
                Assert.isTrue(u.getAmdLanguage().equals(user.getAmdLanguage()), "Amd Lang isn't Persisted ");
                Assert.isTrue(u.getHhLanguage().equals(user.getHhLanguage()), "Handheld Lang isn't Persisted ");
                Assert.isTrue(u.getU2jLanguage().equals(user.getU2jLanguage()), "User to Jenefer Lang isn't Persisted ");
                Assert.isTrue(u.getJ2uLanguage().equals(user.getJ2uLanguage()), "Jenefer to User Lang isn't Persisted ");
            } catch (Exception ex) {

            }
        }
    }

    private final User getUserObject() {
        final double userNumber = Math.random();
        java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

        final User user = new User();
        user.setUserName("User_" + userNumber);
        user.setFirstName("User_FirstName_" + userNumber);
        user.setLastName("User_LastName_" + userNumber);
        user.setTitle("UserTitle_" + userNumber);
        user.setEmailAddress("User@EmailAddress_" + userNumber + ".com");
        user.setBirthDate(sqlDate);
        user.setEmployeeNumber("EmployeeNumber_" + userNumber);
        user.setHashedPassword("HashedPassword");
        user.setMobileNumber("1234567890");
        user.setPlainTextPassword("PlainTextPassword");

        final SupportedLanguage language = languageService.findOneLanguageByCode("EN_US");
        user.setAmdLanguage(language);
        user.setU2jLanguage(language);
        user.setJ2uLanguage(language);
        user.setHhLanguage(language);

        final Shift shift = shiftService.findOneByShiftName("day");
        user.setShift(shift);

        user.getShift().setShiftId(shift.getShiftId());

        final WmsUser wmsUser = wmsUserService.findOneUserById(1L);
        user.setWmsUser(wmsUser);

        user.setAuthenticated(true);
        user.setCreatedByUserName(JACK_USERNAME);
        user.setUpdatedByUserName(JACK_USERNAME);
        user.setSeeHomeCanvasIndicator(new Boolean(true));
        user.setRetrainVoiceModel(new Boolean(false));
        user.setEnable(new Boolean(true));
        user.setDeletedIndicator(new Boolean(false));
        user.setCreatedDateTime(sqlDate);
        user.setUpdatedDateTime(sqlDate);
        user.setSkill(ADVANCED);
        Canvas canvas = new Canvas();
        canvas.setName("Perishable Goods Canvas");
        user.setActiveCanvas(canvas);

        user.setUserPreferences(new UserPreferences("YYYY-MM-DD", "24HR", 300L));

        return user;
    }

    @Test
    public void testEnableUser(){
        final List<String> userList=new ArrayList<String>();
        userList.add(JACK_USERNAME);
        userList.add(JILL_USERNAME);
        final Map<String,String> resultMap=this.userService.enableUser(userList);
        Assert.notNull(resultMap,"Result for Enable User List is Null");
        Assert.isTrue(resultMap.get(JACK_USERNAME).equals("Enabled Successfully"), JACK_USERNAME + " is not Enabled Successfully");
        Assert.isTrue(resultMap.get(JILL_USERNAME).equals("Enabled Successfully"),JILL_USERNAME+" is not Enabled Successfully" );
    }

    @Test
    @Rollback(true)
    public void testDisableUser(){
        final List<String> userList=new ArrayList<String>();
        userList.add("dummy-username15");
        userList.add("dummy-username16");
        final Map<String,String> resultMap=this.userService.disableUser(userList);
        Assert.notNull(resultMap,"Result for Disable User List is Null");
        Assert.isTrue(resultMap.get("dummy-username15").equals("Disabled Successfully"), "dummy-username15 is not Disabled Successfully");
        Assert.isTrue(resultMap.get("dummy-username16").equals("Disabled Successfully"),"dummy-username16 is not Disabled Successfully");

    }

    /**
     * Happy path test to delete a bunch of users from the database
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUsers(){
        final List<String> userList = new ArrayList<String>();
        userList.add("dummy-username20");
        userList.add("dummy-username21");
        userList.add("dummy-username22");
        userList.add("dummy-username23");
        userList.add("dummy-username24");

        final Map<String,String> userDeletionResultMap=this.userService.deleteUser(userList);

        Assert.notNull(userDeletionResultMap,"User Deletion Operation is Unsuccessful");
        Assert.isTrue(userDeletionResultMap.get(userList.get(0)).equals("Deleted Successfully"),userList.get(0)+" Deletion Operation isn't Successful");
        Assert.isTrue(userDeletionResultMap.get(userList.get(1)).equals("Deleted Successfully"),userList.get(1)+" Deletion Operation isn't Successful");
        Assert.isTrue(userDeletionResultMap.get(userList.get(2)).equals("Deleted Successfully"),userList.get(2)+" Deletion Operation isn't Successful");
        Assert.isTrue(userDeletionResultMap.get(userList.get(3)).equals("Deleted Successfully"),userList.get(3)+" Deletion Operation isn't Successful");
        Assert.isTrue(userDeletionResultMap.get(userList.get(4)).equals("Deleted Successfully"),userList.get(4)+" Deletion Operation isn't Successful");

    }
    
    @Ignore
    @Transactional
	@Rollback(true)
	@Test
	public void testSaveMultiUser() throws IllegalArgumentException,
			IntrospectionException, IllegalAccessException,
			InvocationTargetException, ParseException {
		final List<String> userList = new ArrayList<String>();
		userList.add(JACK_USERNAME);
		userList.add(JILL_USERNAME);
		UserFormFieldsDTO multiEditFields = gerUserFormFieldsDTOData();
		userService.saveMultiUser(userList, multiEditFields);
		User jackUserAfter = userService.getUser(JACK_USERNAME);
		User jillUserAfter = userService.getUser(JILL_USERNAME);

		Assert.isTrue(jackUserAfter.getUserName().equals(JACK_USERNAME),
				"Jack username should have been " + JACK_USERNAME);
		Assert.isTrue(jillUserAfter.getUserName().equals(JILL_USERNAME),
				"Jill username should have been " + JILL_USERNAME);
		Assert.isTrue(jackUserAfter.getFirstName().equals("Jack"),
				"Jack first name should have been Jack");
		Assert.isTrue(jillUserAfter.getFirstName().equals("Jill"),
				"Jill first name should have been Jill");
		Assert.isTrue(jackUserAfter.getLastName().equals("User2"),
				"Jack last name should have been User2");
		Assert.isTrue(jillUserAfter.getLastName().equals("User1"),
				"Jill last name should have been User1");
		Assert.isTrue(jackUserAfter.getEmailAddress().equals("abc@xyz.com"),
				"Jack email address should have been abc@xyz.com");
		Assert.isTrue(jillUserAfter.getEmailAddress().equals("abc@xyz.com"),
				"Jill email address should have been abc@xyz.com");
		Assert.isTrue(jackUserAfter.getTitle() == null,
				"Jack title should have been null");
		Assert.isTrue(jillUserAfter.getTitle() == null,
				"Jill title should have been null");
		Assert.isTrue(
				jackUserAfter.getStartDate().equals(
						DateUtils.parseDate("2014-02-01T00:00:00.0Z")),
				"Jack start date is not up to the expectations");
		Assert.isTrue(
				jillUserAfter.getStartDate().equals(
						DateUtils.parseDate("2014-02-01T00:00:00.0Z")),
				"Jill start date is not up to the expectations");
		Assert.isTrue(jackUserAfter.isEnable().equals(Boolean.TRUE),
				"Jack should have been enable");
		Assert.isTrue(jillUserAfter.isEnable().equals(Boolean.TRUE),
				"Jill should have been enable");
		Assert.isTrue(jackUserAfter.getHashedPassword()
				.equals("hashedPassword"),
				"Jack hashedPassword should have been hashedPassword");
		Assert.isTrue(jillUserAfter.getHashedPassword()
				.equals("hashedPassword"),
				"Jill hashedPassword should have been hashedPassword");
		Assert.isTrue(jackUserAfter.getMobileNumber().equals("12345678"),
				"Jack mobile number should have been 12345678");
		Assert.isTrue(jillUserAfter.getMobileNumber().equals("12345678"),
				"Jill mobile number should have been 12345678");
		Assert.isTrue(jackUserAfter.getEmployeeNumber().equals("87654321"),
				"Jack employee number should have been 87654321");
		Assert.isTrue(jillUserAfter.getEmployeeNumber().equals("87654321"),
				"Jill employee number should have been 87654321");
		Assert.isTrue(jackUserAfter.getJ2uLanguage().equals("TestJ2uLanguage"),
				"Jack j2uLanguage should have been TestJ2uLanguage");
		Assert.isTrue(jillUserAfter.getJ2uLanguage().equals("TestJ2uLanguage"),
				"Jill j2uLanguage should have been TestJ2uLanguage");
		Assert.isTrue(jackUserAfter.getU2jLanguage().equals("TestU2jLanguage"),
				"Jack u2jLanguage should have been TestU2jLanguage");
		Assert.isTrue(jillUserAfter.getU2jLanguage().equals("TestU2jLanguage"),
				"Jill u2jLanguage should have been TestU2jLanguage");
		Assert.isTrue(jackUserAfter.getHhLanguage().equals("TestHhLanguage"),
				"Jack hhLanguage should have been TestHhLanguage");
		Assert.isTrue(jillUserAfter.getHhLanguage().equals("TestHhLanguage"),
				"Jill hhLanguage should have been TestHhLanguage");
		Assert.isTrue(jackUserAfter.getAmdLanguage().equals("TestAmdLanguage"),
				"Jack amdLanguage should have been TestAmdLanguage");
		Assert.isTrue(jillUserAfter.getAmdLanguage().equals("TestAmdLanguage"),
				"Jill amdLanguage should have been TestAmdLanguage");
	}

	private UserFormFieldsDTO gerUserFormFieldsDTOData() {
		UserFormFieldsDTO multiEditFields = new UserFormFieldsDTO();
		multiEditFields.setUserName(new HashMap<String, String>() {
			{
				put("value", null);
				put("forceUpdate", "false");
			}
		});
		multiEditFields.setFirstName(new HashMap<String, String>() {
			{
				put("value", null);
				put("forceUpdate", "false");
			}
		});
		multiEditFields.setLastName(new HashMap<String, String>() {
			{
				put("value", null);
				put("forceUpdate", "false");
			}
		});
		multiEditFields.setEmailAddress(new HashMap<String, String>() {
			{
				put("value", "abc@xyz.com");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setTitle(new HashMap<String, String>() {
			{
				put("value", null);
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setStartDate(new HashMap<String, String>() {
			{
				put("value", "2014-02-01T00:00:00.0Z");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setEnable(new HashMap<String, String>() {
			{
				put("value", Boolean.TRUE.toString());
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setNeedsAuthentication(new HashMap<String, String>() {
			{
				put("value", Boolean.TRUE.toString());
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setPlainTextPassword(new HashMap<String, String>() {
			{
				put("value", "dummyPlainTextPassword");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setHashedPassword(new HashMap<String, String>() {
			{
				put("value", "hashedPassword");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setMobileNumber(new HashMap<String, String>() {
			{
				put("value", "12345678");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setEmployeeNumber(new HashMap<String, String>() {
			{
				put("value", "87654321");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setBirthDate(new HashMap<String, String>() {
			{
				put("value", "2014-05-01T01:01:01.0Z");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setJ2uLanguage(new HashMap<String, String>() {
			{
				put("value", "TestJ2uLanguage");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setU2jLanguage(new HashMap<String, String>() {
			{
				put("value", "TestU2jLanguage");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setHhLanguage(new HashMap<String, String>() {
			{
				put("value", "TestHhLanguage");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setAmdLanguage(new HashMap<String, String>() {
			{
				put("value", "TestAmdLanguage");
				put("forceUpdate", "true");
			}
		});
		multiEditFields.setAuthenticated(new HashMap<String, String>() {
			{
				put("value", Boolean.TRUE.toString());
				put("forceUpdate", "true");
			}
		});
		return multiEditFields;
	}
	
	 @Transactional
	    @Rollback(true)
	    @Test
	    public void testGetPermissionGroupsBySearchAndSortCriteria() throws ParseException, IOException, Exception {

	        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
	        final Map<String, Object> searchMap = new HashMap<String, Object>();

	       
	        // numeric Range
	        searchAndSortCriteria.setSearchMap(searchMap);
	        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
	            {
	                put("permissionGroupName", SortType.ASC);
	            }
	        });
	        
	        ObjectMapper mapper = new ObjectMapper();
	        
	        searchAndSortCriteria.setPageNumber(0);
	        searchAndSortCriteria.setPageSize(Integer.MAX_VALUE);
	        
	      /*  {
	        	  "pageSize": 2147483647,
	        	  "pageNumber": 0,
	        	  "searchMap": {},
	        	  "sortMap": {
	        	    "permissionGroupName": "ASC"
	        	  }
	        	}*/
	        
	        final List<PermissionGroup> permissionGroupList = this.userService.getPermissionGroupsBySearchAndSortCriteria(searchAndSortCriteria);
	        Assert.notNull(permissionGroupList, "Permission group list is null");
	        Assert.isTrue(permissionGroupList.size() >= 1, "Permission group list is empty");

	    }
	 /**
	  * This is not a test case it should be uncommented and executed to verify whether the second level cache is working fine.
	  */
	/* @Test
	    public void testCache() {
	    	EntityManagerFactoryInfo emfi = (EntityManagerFactoryInfo)resourceEntityManagerFactory;
	    	EntityManagerFactory emf = emfi.getNativeEntityManagerFactory();
	    	EntityManagerFactoryImpl empImpl = (EntityManagerFactoryImpl)emf;
	    	LOG.debug("*************************** " +empImpl.getSessionFactory().getStatistics() + " ***************************");
	    }*/


    /**
     *  testGetUserListByUserName() test is for testing the functionality of
     *  com.lucas.services.user.UserService.getUserListFromUserNameList() which
     *  internally used com.lucas.dao.user.UserDAOImpl.getUser()method
     *  on the bases of list of User name supplied inside instance of java.util.List
     *
     */
    @Test
    @Transactional
    public void testGetUserListByUserName(){
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(ADMIN_USERNAME);
        userNameList.add(JACK_USERNAME);
        userNameList.add(JILL_USERNAME);
        final List<User> userObjectList=this.userService.getUserListFromUserNameList(userNameList);
        Assert.notNull(userObjectList,"User Object List is null");
        Assert.notEmpty(userObjectList,"User Object List is Empty");
        for(User user1:userObjectList){
            LOG.info(user1.toString());
            Assert.notNull(user1.getPermissionsSet(),"User Permission Set is null");
            Assert.notEmpty(user1.getPermissionsSet(),"User Permission Set is Empty");
            LOG.info(user1.getPermissionsSet().toString());
            Assert.notNull(user1.getUserPermissionGroupList(),"User PermissionGroup List is null");
            Assert.notEmpty(user1.getUserPermissionGroupList(),"User PermissionGroup List is Empty");
            LOG.info(user1.getUserPermissionGroupList().toString());
        }
    }

    /**
     *  Test to verify that null username will produce an exception
     *
     */
    @Test
    @Transactional
    public void testGetUserListWithNullUserName(){
        Throwable e = null;
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(ADMIN_USERNAME);
        userNameList.add(JACK_USERNAME);
        userNameList.add("");
        try {
            final List<User> userObjectList = this.userService.getUserList(userNameList);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(e instanceof UserDoesNotExistException);

    }

    /**
     *  Test to verify that invalid username will produce an exception
     *
     */
    @Test
    @Transactional
    public void testGetUserListWithInvalidUserName(){
        Throwable e = null;
        final List<String> userNameList=new ArrayList<String>();
        userNameList.add(ADMIN_USERNAME);
        userNameList.add(JACK_USERNAME);
        userNameList.add("INVALID-USER-TEST");
        try {
            final List<User> userObjectList = this.userService.getUserList(userNameList);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(e instanceof UserDoesNotExistException);

    }

    /**
     * Happy route test method to assign groups to user correctly as expected
     */
    @Test
    @Transactional
    public void testSaveUsersAssignedGroups(){

        List<String> permissionsList = Arrays.asList("administrator", "warehouse-manager", "supervisor");

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(JACK_USERNAME);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        boolean status = userService.saveUsersAssignedGroups(usersAssignedGroupsDTO);
        Assert.isTrue(status == true);

    }

    /**
     * Test method to make a non existing group cannot be assigned to the user
     */
    @Test
    @Transactional
    public void testSaveUsersAssignedGroupsInvalidGroup(){

        Throwable e = null;

        List<String> permissionsList = Arrays.asList("administrator", "warehouse-manager", "NON-EXISTING-GROUP");

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(JACK_USERNAME);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        try {
             boolean status = userService.saveUsersAssignedGroups(usersAssignedGroupsDTO);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(e instanceof PermissionGroupDoesNotExistException);
    }

    /**
     * Test method to make the permission groups are revoked from the user correctly as expected
     */
    @Test
    @Transactional
    public void testSaveUsersAssignedGroupsWithNullGroup(){

        List<String> permissionsList = new ArrayList<String>();

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName(JACK_USERNAME);
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        boolean status = userService.saveUsersAssignedGroups(usersAssignedGroupsDTO);
        Assert.isTrue(status == true);
    }

    /**
     * Test method to make sure permission groups cannot be assigned to an invalid user as expected
     */
    @Test
    @Transactional
    public void testSaveUsersAssignedGroupsWithInvalidUsername(){

        Throwable e = null;

        List<String> permissionsList = Arrays.asList("administrator", "warehouse-manager");

        UsersAssignedGroupsDTO usersAssignedGroupsDTO = new UsersAssignedGroupsDTO();
        usersAssignedGroupsDTO.setUserName("Invalid-User-Name");
        usersAssignedGroupsDTO.setAssignedGroups(permissionsList);

        try {
            boolean status = userService.saveUsersAssignedGroups(usersAssignedGroupsDTO);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isTrue(e instanceof UserDoesNotExistException);
    }
    
  /**
   * Test is get the total count of records with the given search and sort criteria.
   * 
   * @throws ParseException
   * @throws IOException
   * @throws Exception
   */
  @SuppressWarnings("serial")
  @Test
  @Transactional
  public void testTotalCountForSearchAndSortCriteria() throws ParseException, IOException, Exception {

    final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
    searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
      {
        put("userName", SortType.ASC);
      }
    });

    searchAndSortCriteria.setPageNumber(1);
    searchAndSortCriteria.setPageSize(3);
    final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
    final Long totalRecords = this.userService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
    Assert.notNull(userList);
    Assert.isTrue(userList.size() == 3);
    Assert.isTrue(totalRecords.intValue() == 202, "Expected 201 but record count is " + totalRecords.intValue());
  }
  
    /**
     * Test method to test for setting retrain voice model for selected users through Service call.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsers() {
        final List<String> userList = new ArrayList<String>();
        userList.add(JACK_USERNAME);
        userList.add(JILL_USERNAME);
        final Map<String, String> resultMap = this.userService.retrainVoiceModelForSelectedUsers(userList);
        Assert.notNull(resultMap, "Result for Enable User List is Null");
        Assert.isTrue(resultMap.get(JACK_USERNAME).equals("Retrain Voice Reset Successfully"), JACK_USERNAME + " retrain voice model is not Enabled Successfully");
        Assert.isTrue(resultMap.get(JILL_USERNAME).equals("Retrain Voice Reset Successfully"), JILL_USERNAME + " retrain voice model is not Enabled Successfully");
    }
    
    /**
     * Test method to test for setting retrain voice model for selected users with empty user list.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForEmptyUserList() {
        Throwable e = null;
        final List<String> userList = new ArrayList<String>();
        Map<String, String> resultMap;
        try {
            resultMap = this.userService.retrainVoiceModelForSelectedUsers(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }

    /**
     * Test method to test for setting retrain voice model for selected users with empty string in
     * user list.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForEmptyStringInUserList() {
        Throwable e = null;
        final List<String> userList = Arrays.asList("");
        Map<String, String> resultMap;
        try {
            resultMap = this.userService.retrainVoiceModelForSelectedUsers(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }

    /**
     * Test method to test for setting retrain voice model for selected users with invalid user in
     * user list.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForInvalidUserInUserList() {
        Throwable e = null;
        final List<String> userList = Arrays.asList("sdfghfgh");
        Map<String, String> resultMap;
        try {
            resultMap = this.userService.retrainVoiceModelForSelectedUsers(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }

    /**
     * Test method to test for setting retrain voice model for selected users with invalid user with
     * valid users in the user list.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testRetrainVoiceModelForSelectedUsersForInvalidUserWithValidUsers() {
        Throwable e = null;
        final List<String> userList = Arrays.asList("jack123", "jill123", "hdfjksdhyfsd");
        Map<String, String> resultMap = null;
        try {
            resultMap = this.userService.retrainVoiceModelForSelectedUsers(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }
    
    /**
     * Happy path test method to get user assigned groups
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAssignedUserGroups() {

        Throwable e = null;
        final List<String> userList = Arrays.asList(JACK_USERNAME, JILL_USERNAME, ADMIN_USERNAME);

        Map<String, Object> resultMap = null;

        try {
            resultMap = this.userService.getAssignedUserGroups(userList);
        } catch (Throwable ex) {
        }
        Assert.notNull(resultMap, "Result for User assigned group list is null");

        JSONObject obj = new JSONObject(resultMap);
        Assert.isTrue(obj.get("jack123").toString().contains("picker"));
        Assert.isTrue(obj.get("jack123").toString().contains("warehouse-manager"));
        Assert.isTrue(obj.get("jack123").toString().contains("supervisor"));

        Assert.isTrue(obj.get("jill123").toString().contains("picker"));

        Assert.isTrue(obj.get("admin123").toString().contains("warehouse-manager"));
        Assert.isTrue(obj.get("admin123").toString().contains("system"));
        Assert.isTrue(obj.get("admin123").toString().contains("basic-authenticated-user"));

    }

    /**
     * Test method to verify passing in an invalid user produces an exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAssignedUserGroupsWithInvalidUser() {

        Throwable e = null;
        final List<String> userList = Arrays.asList(JACK_USERNAME, JILL_USERNAME, "INVALID-TEST-USER");

        Map<String, Object> resultMap = null;

        try {
            resultMap = this.userService.getAssignedUserGroups(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }

    /**
     * Test method to verify passing in an invalid user produces an exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAssignedUserGroupsWithNullUser() {

        Throwable e = null;
        final List<String> userList = Arrays.asList(JACK_USERNAME, JILL_USERNAME, "");

        Map<String, Object> resultMap = null;

        try {
            resultMap = this.userService.getAssignedUserGroups(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }

    /**
     * Test method to verify passing in an invalid user produces an exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAssignedUserGroupsWithEmptyUserArray() {

        Throwable e = null;
        final List<String> userList = new ArrayList<>();

        Map<String, Object> resultMap = null;

        try {
            resultMap = this.userService.getAssignedUserGroups(userList);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.isTrue(e instanceof UserDoesNotExistException);
    }
    
    /**
     * Test method to amdLanguage in SearchAndSortCriteria for filtering
     * 
     * @throws ParseException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Transactional
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSearchCriteriaJ2uLanguage() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();

        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("j2uLanguage", langMap);

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);

        Assert.notNull(userList, "User Object List is null");
        Assert.isTrue(userList.size() >= 1, "User Object List is null");
    }

    /**
     * Test method to amdLanguage in SearchAndSortCriteria for filtering
     * 
     * @throws ParseException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Transactional
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSearchCriteriaAmdLanguage() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("amdLanguage", langMap);

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);

        Assert.notNull(userList, "User Object List is null");
        Assert.isTrue(userList.size() >= 1, "User Object List is null");
    }

    /**
     * Test method to u2jLanguage in SearchAndSortCriteria for filtering
     * 
     * @throws ParseException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Transactional
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSearchCriteriaU2jLanguage() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("u2jLanguage", langMap);

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);

        Assert.notNull(userList, "User Object List is null");
        Assert.isTrue(userList.size() >= 1, "User Object List is null");
    }

    /**
     * Test method to hhLanguage in SearchAndSortCriteria for filtering
     * 
     * @throws ParseException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Transactional
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithSearchCriteriaHhLanguage() throws ParseException, IOException, Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", Arrays.asList("EN_GB"));
        searchMap.put("hhLanguage", langMap);


        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        final List<User> userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);

        Assert.notNull(userList, "User Object List is null");
        Assert.isTrue(userList.size() >= 1, "User Object List is null");
    }


    /**
     * Test method to test for search with invalid SearchAndSortCriteria for filtering
     * 
     * @throws ParseException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("serial")
    @Transactional
    @Test
    public void testGetUserListBySearchAndSortCriteriaWithInvalidSearchCriteria() throws ParseException, IOException, Exception {
        Throwable e = null;
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> langMap = new LinkedHashMap<String, Object>();

        langMap.put("filterType", FilterType.ENUMERATION);
        langMap.put("values", new ArrayList<String>() {});
        searchMap.put("j2uLanguage", langMap);


        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        searchAndSortCriteria.setSortMap(null);
        searchAndSortCriteria.setSearchMap(searchMap);

        List<User> userList;
        try {
            userList = this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        } catch (Throwable ex) {
            e = ex;
        }
        
        Assert.isTrue(e instanceof IllegalArgumentException);
    }
    
    /**
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    @Transactional
    @Rollback(true)
    @Test
    public void testUpdateUserActiveCanvases() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        final User persistedUser = this.userService.getUser(JACK_USERNAME);
        Canvas canvas = new Canvas();
        canvas.setCanvasId(1L);
        persistedUser.setActiveCanvas(canvas);
        this.userService.updateUserActiveCanvas(persistedUser);
        final User updatedUser = this.userService.getUser(JACK_USERNAME);
        Assert.notNull(updatedUser, "Updated user is Null");
        Assert.notNull(updatedUser.getActiveCanvas(), "Active Canvas is Not Persisted");
        Assert.isTrue(updatedUser.getActiveCanvas().getCanvasId() == 1, "Active Canvas ID isn't 1");

    }

    /**
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    @Transactional
    @Rollback(true)
    @Test
    public void testUpdateUserOpenCanvases() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        final User persistedUser = this.userService.getUser(JACK_USERNAME);
        List<OpenUserCanvas> openUserCanvases = persistedUser.getOpenUserCanvases();
        OpenUserCanvas openCanvas1 = new OpenUserCanvas();
        Canvas canvas1 = new Canvas();
        canvas1.setCanvasId(4L);
        openCanvas1.setDisplayOrder(1);
        openCanvas1.setCanvas(canvas1);
        openUserCanvases.add(openCanvas1);
        persistedUser.setOpenUserCanvases(openUserCanvases);
        persistedUser.setUpdatedByUserName(JACK_USERNAME);
        persistedUser.setUpdatedDateTime(new Date());
        this.userService.updateUserOpenCanvas(persistedUser);
        final User updatedUser = this.userService.getUser(JACK_USERNAME);
        Assert.notNull(updatedUser, "Updated user is Null");
        Assert.notNull(updatedUser.getOpenUserCanvases());
        Assert.isTrue(updatedUser.getOpenUserCanvases().size() >= 1, "User Open Canvas is Empty");
    }
    
    @Transactional
    @Test
    public void testGetUserActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        Canvas canvas;
        try {
            canvas = this.userService.getUserActiveCanvas(JACK_USERNAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        Assert.notNull(canvas, "Canvas is Null");
        Assert.notNull(canvas.getCanvasId(), "Active Canvas is Not Persisted");
        Assert.isTrue(canvas.getCanvasId() != null, "Active Canvas ID is null");

    }

    @Transactional
    @Test
    public void testGetUserWithNoActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        Canvas canvas;
        try {
            canvas = this.userService.getUserActiveCanvas(JILL_USERNAME);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        Assert.isTrue(canvas == null, "Canvas is Null");
    }

    /**
     *  Test testGetAllUsersProperties to get the FirstNames of all users
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAllUsersPropertiesWithOneProp(){

        Throwable e = null;
        final List<String> propList = Arrays.asList("firstName");
        List<Map<String, String>> userObjectMap = null;

        try {
            userObjectMap = this.userService.getAllUsersProperties(propList);
        } catch (Throwable ex) {
        }

        Assert.notNull(userObjectMap,"User Object List is null");
        Assert.notEmpty(userObjectMap,"User Object List is Empty");

        for (Map objMap : userObjectMap )
        {
            if (objMap.containsValue("jill123"))
            {
                Assert.isTrue(objMap.get("firstName").equals("Jill"));
            }
        }

    }

    /**
     *  Test testGetAllUsersProperties to get the FirstNames and LastNames of all users
     *
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetAllUsersPropertiesWithTwoProps(){

        Throwable e = null;
        final List<String> propList = Arrays.asList("firstName","lastName");
        List<Map<String, String>> userObjectMap = null;

        try {
            userObjectMap = this.userService.getAllUsersProperties(propList);
        } catch (Throwable ex) {
        }

        Assert.notNull(userObjectMap,"User Object List is null");
        Assert.notEmpty(userObjectMap,"User Object List is Empty");

        for (Map objMap : userObjectMap )
        {
            if (objMap.containsValue("jill123"))
            {
                Assert.isTrue(objMap.get("lastName").equals("User1"));
                Assert.isTrue(objMap.get("firstName").equals("Jill"));
            }
        }
    }


    /**
     *  Test testGetAllUsersProperties to get the FirstNames, LastNames and Skill of all users
     *
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetAllUsersPropertiesWithMultProps(){

        Throwable e = null;
        final List<String> propList = Arrays.asList("firstName","lastName","skill","emailAddress","j2uLanguage","title","retrainVoiceModel","userId","birthDate","startDate","mobileNumber","createdByUserName","createdDateTime","shift","wmsUser","userPreferences");
        List<Map<String, String>> userObjectMap = null;

        // Update user with skill
        User user1 = userService.getUser(JILL_USERNAME);
        user1.setSkill(ADVANCED);
        user1.setMobileNumber("07123456789");

        try {
            userService.createUser(user1);
        }
        catch (Throwable ex) {
            e = ex;
        }

        try {
            userObjectMap = this.userService.getAllUsersProperties(propList);
        } catch (Throwable ex) {
        }

        Assert.notNull(userObjectMap,"User Object List is null");
        Assert.notEmpty(userObjectMap,"User Object List is Empty");

        for (Map objMap : userObjectMap )
        {
            if (objMap.containsValue("jill123"))
            {
                Assert.isTrue(objMap.get("lastName").equals("User1"));
                Assert.isTrue(objMap.get("firstName").equals("Jill"));
                Assert.isTrue(objMap.get("skill").equals("Advanced"));
                Assert.isTrue(objMap.get("emailAddress").equals("user1@normal.com"));
                Assert.isTrue(objMap.get("j2uLanguage").equals("EN_US"));
                Assert.isTrue(objMap.get("mobileNumber").equals("07123456789"));
                Assert.isTrue(objMap.get("dateFormat").equals("DD-MM-YYYY"));
                Assert.isTrue(objMap.get("timeFormat").equals("24HR"));
                Assert.isTrue(objMap.get("dataRefreshFrequency").equals("240"));
            }
            else if (objMap.containsValue("Lucas System"))
            {
                Assert.isTrue(objMap.get("wmsUser").equals("system"));
            }
        }
    }

    /**
     *  Test testGetAllUsersProperties to get the FirstNames, LastNames and invalid property of all users
     *
     */
    @Test
    @Transactional
    public void testGetAllUsersPropertiesWithInvalidProp(){

        Throwable e = null;
        final List<String> propList = Arrays.asList("firstName","lastName","invalidProp");
        List<Map<String, String>> userObjectMap = null;

        try {
            userObjectMap = this.userService.getAllUsersProperties(propList);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isNull(userObjectMap, "Result for Enable User List is not Null");
        Assert.isTrue(e instanceof LucasRuntimeException);
    }

    /**
     *  Test testGetAllUsersProperties when passing in null as list of user properties
     *
     */
    @Test
    @Transactional
    public void testGetAllUsersPropertiesWithNullProp(){

        Throwable e = null;
        List<Map<String, String>> userObjectMap = null;

        try {
            userObjectMap = this.userService.getAllUsersProperties(null);
        } catch (Throwable ex) {
            e = ex;
        }

        Assert.isNull(userObjectMap, "Result for Enable User List is not Null");
        Assert.isTrue(e instanceof LucasRuntimeException);
    }

}
