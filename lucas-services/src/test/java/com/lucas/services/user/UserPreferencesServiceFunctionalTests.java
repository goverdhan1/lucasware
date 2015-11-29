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

import com.lucas.exception.UserPreferencesDoesNotExistException;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.LinkedHashMap;
import java.util.Map;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class UserPreferencesServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static final Logger log = LoggerFactory.getLogger(UserPreferencesServiceFunctionalTests.class);
    
    @Inject
    private UserPreferencesService userPreferencesService;

    private String userName = "jack123";

    @Before
    public void setup() {
    }


    /**
     * Test method to verify that the user preferences data retrieved correctly
     * @author DiepLe
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetUserPreferencesDataHappyPath() {

        log.debug("***Running Test: UserPreferencesServiceFunctionalTests.testGetUserPreferencesDataHappyPath");
        Throwable e = null;

        Map <String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = userPreferencesService.findOneUserPreferenceByUserName(userName);
        }
        catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        for (Map.Entry<String, String> entry : resultMap.entrySet())
        {
            if(StringUtils.equals(entry.getKey(), "dateFormat")) {
                Assert.isTrue(StringUtils.equals(entry.getValue(), "DD-MM-YYYY"));
            } else if (StringUtils.equals(entry.getKey(), "timeFormat")) {
                Assert.isTrue(StringUtils.equals(entry.getValue(), "24HR"));
            } else if (StringUtils.equals(entry.getKey(), "dataRefreshFrequency")) {
                Assert.isTrue(StringUtils.equals(entry.getValue(), "120"));
            }
        }
    }

    /**
     * Test method to verify that UserPreferencesDoesNotExistException exception was thrown
     * when invalid username was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetUserPreferenceDataWithInvalidUserNameDataPassedIn() {

        log.debug("***Running Test: UserPreferencesServiceFunctionalTests.testGetUserPreferenceDataWithInvalidUserNameDataPassedIn");
        Throwable e = null;

        Map <String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = userPreferencesService.findOneUserPreferenceByUserName("INVALID-USER_NAME");
        }
        catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof UserPreferencesDoesNotExistException);
    }


    /**
     * Test method to verify that UserPreferencesDoesNotExistException exception was thrown
     * when null username was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetUserPreferenceDataWithNullUserNameDataPassedIn() {

        log.debug("***Running Test: UserPreferencesServiceFunctionalTests.testGetUserPreferenceDataWithNullUserNameDataPassedIn");
        Throwable e = null;

        Map <String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = userPreferencesService.findOneUserPreferenceByUserName(null);
        }
        catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof UserPreferencesDoesNotExistException);
    }

    /**
     * Test method to verify that UserPreferencesDoesNotExistException exception was thrown
     * when empty username was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetUserPreferenceDataWithEmptyUserNameDataPassedIn() {

        log.debug("***Running Test: UserPreferencesServiceFunctionalTests.testGetUserPreferenceDataWithEmptyUserNameDataPassedIn");
        Throwable e = null;

        Map <String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = userPreferencesService.findOneUserPreferenceByUserName(null);
        }
        catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof UserPreferencesDoesNotExistException);
    }
}
