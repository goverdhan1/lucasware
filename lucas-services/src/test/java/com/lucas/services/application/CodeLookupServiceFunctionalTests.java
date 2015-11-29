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
package com.lucas.services.application;

import com.lucas.exception.CodeLookupDoesNotExistException;
import com.lucas.exception.CodeLookupInvalidException;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

import org.activiti.engine.impl.util.json.JSONObject;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class CodeLookupServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static final Logger log = LoggerFactory.getLogger(CodeLookupServiceFunctionalTests.class);
    
    @Inject
    private EntityManagerFactory resourceEntityManagerFactory;

    @Inject
    private CodeLookupService codeLookupService;

    private static List<String> validCodeLookupList = new ArrayList<String>();
    private static List<String> invalidCodeLookupList = new ArrayList<String>();
    private static List<String> nullCodeLookupList = new ArrayList<String>();
    private static List<String> nullValueCodeLookupList = new ArrayList<String>();
    private static List<String> emptyCodeLookupList = new ArrayList<String>();

    @Before
    public void setup() {

        validCodeLookupList.add("USER_STATUS");
        validCodeLookupList.add("USER_SKILL");
        validCodeLookupList.add("DATE_FORMAT");
        validCodeLookupList.add("TIME_FORMAT");

        invalidCodeLookupList.add("USER_STATUS");
        invalidCodeLookupList.add("USER_SKILL");
        invalidCodeLookupList.add("INVALID_LOOKUP_DATA");

        nullCodeLookupList.add("USER_STATUS");
        nullCodeLookupList.add("USER_SKILL");
        nullCodeLookupList.add("");

        nullValueCodeLookupList.add("USER_STATUS");
        nullValueCodeLookupList.add("USER_SKILL");
        nullValueCodeLookupList.add(null);
    }


    /**
     * Test method to verify that the code lookup data retrieved correctly
     *
     * @author DiepLe
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetValidCodeLookupData() {

        log.debug("***Running Test: CodeLookupServiceFunctionalTests.testGetValidCodeLookupData");
        JSONObject obj = new JSONObject(codeLookupService.getCodeLookupData(validCodeLookupList).toString());

        // remove special characters which cause assertion errors
        String userStatus = obj.get("USER_STATUS").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(userStatus, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(userStatus, "value:0"));
        Assert.isTrue(StringUtils.contains(userStatus, "key:0"));
        Assert.isTrue(StringUtils.contains(userStatus, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(userStatus, "value:1"));
        Assert.isTrue(StringUtils.contains(userStatus, "key:1"));


        String userSkill = obj.get("USER_SKILL").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(userSkill, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(userSkill, "value:STANDARD"));
        Assert.isTrue(StringUtils.contains(userSkill, "key:STANDARD"));
        Assert.isTrue(StringUtils.contains(userSkill, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(userSkill, "value:ADVANCED"));
        Assert.isTrue(StringUtils.contains(userSkill, "key:ADVANCED"));


        String dateFormat = obj.get("DATE_FORMAT").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:MM-DD-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:MM-DD-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:DD-MM-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:DD-MM-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:30"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:YYYY-MM-DD"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:YYYY-MM-DD"));



        String timeFormat = obj.get("TIME_FORMAT").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(timeFormat, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(timeFormat, "value:12HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "key:12HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(timeFormat, "value:24HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "key:24HR"));

    }

    /**
     * Test method to verify that CodeLookupInvalidException exception was thrown
     * when invalid code lookup was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetCodeLookupDataWithInvalidCodeLookupDataPassedIn() {

        log.debug("***Running Test: CodeLookupServiceFunctionalTests.testGetCodeLookupDataWithInvalidCodeLookupDataPassedIn");
        Throwable e = null;

        try {
            final Map<String, Object> mapData = codeLookupService.getCodeLookupData(invalidCodeLookupList);
        } catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof CodeLookupInvalidException);
    }

    /**
     * Test method to verify that CodeLookupInvalidException exception was thrown
     * when invalid code lookup was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGeCodeLookupDataWithNullValuePassedIn() {

        log.debug("***Running Test: CodeLookupServiceFunctionalTests.testGeCodeLookupDataWithNullValuePassedIn");
        Throwable e = null;

        try {
            final Map<String, Object> mapData = codeLookupService.getCodeLookupData(nullCodeLookupList);
        } catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof CodeLookupDoesNotExistException);
    }

    /**
     * Test method to verify that CodeLookupInvalidException exception was thrown
     * when invalid code lookup was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetCodeLookupDataWithEmptyArrayValuePassedIn() {

        log.debug("***Running Test: CodeLookupServiceFunctionalTests.testGetCodeLookupDataWithEmptyArrayValuePassedIn");
        Throwable e = null;

        try {
            final Map<String, Object> mapData = codeLookupService.getCodeLookupData(emptyCodeLookupList);
        } catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof CodeLookupDoesNotExistException);
    }

    /**
     * Test method to verify that CodeLookupInvalidException exception was thrown
     * when invalid code lookup was passed in
     *
     * @author DiepLe
     *
     *
     */
    @Transactional(readOnly = true)
    @Test
    public void testGetCodeLookupDataWithNullWordValuePassedIn() {

        log.debug("***Running Test: CodeLookupServiceFunctionalTests.testGetCodeLookupDataWithNullWordValuePassedIn");
        Throwable e = null;

        try {
            final Map<String, Object> mapData = codeLookupService.getCodeLookupData(nullValueCodeLookupList);
        } catch (Throwable ex) {
            log.debug("Yep Exception did throw as expected");
            e = ex;
        }

        Assert.isTrue(e instanceof CodeLookupInvalidException);
    }
}
