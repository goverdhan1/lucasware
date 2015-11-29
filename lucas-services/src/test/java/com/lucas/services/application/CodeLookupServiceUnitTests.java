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

import com.google.common.collect.Lists;
import com.lucas.services.util.CollectionsUtilService;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class, StringUtils.class})
public class CodeLookupServiceUnitTests {

	@Mock
	private StandardPBEStringEncryptor stringEncryptor;

	@Mock
	private CodeLookupService codeLookupService;

    private static List<String> validCodeLookupList = new ArrayList<String>();
    private static List<String> invalidCodeLookupList = new ArrayList<String>();
    private static List<String> nullCodeLookupList = new ArrayList<String>();
    private static List<String> nullValueCodeLookupList = new ArrayList<String>();
    private static List<String> emptyCodeLookupList = new ArrayList<String>();
    private Map<String, Object> resultObj = new LinkedHashMap<String, Object>();
    List<Map<String, String>> userStatusArrayMap = new ArrayList<Map<String, String>>();
    List<Map<String, String>> userSkillArrayMap = new ArrayList<Map<String, String>>();
    List<Map<String, String>> dateFormatArrayMap = new ArrayList<Map<String, String>>();
    List<Map<String, String>> timeFormatArrayMap = new ArrayList<Map<String, String>>();

    private static final Logger log = LoggerFactory.getLogger(CodeLookupServiceUnitTests.class);

    @SuppressWarnings("unchecked")
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

        // setup the mapData to contain the expected data

        // Begin USER_STATUS
        Map<String, String> statusMap1 = new HashMap<String, String>();
        statusMap1.put("key", "0");
        statusMap1.put("value", "0");
        statusMap1.put("displayOrder", "10");

        Map<String, String> statusMap2 = new HashMap<String, String>();
        statusMap2.put("key", "1");
        statusMap2.put("value", "1");
        statusMap2.put("displayOrder", "20");
        // End USER_STATUS


        // Begin USER_SKILL
        Map<String, String> skillMap1 = new HashMap<String, String>();
        skillMap1.put("key", "ADVANCED");
        skillMap1.put("value", "ADVANCED");
        skillMap1.put("displayOrder", "10");

        Map<String, String> skillMap2 = new HashMap<String, String>();
        skillMap2.put("key", "STANDARD");
        skillMap2.put("value", "STANDARD");
        skillMap2.put("displayOrder", "20");
        // End USER_SKILL

        // Begin DATE_FORMAT
        Map<String, String> dateMap1 = new HashMap<String, String>();
        dateMap1.put("key", "MM-DD-YYYY");
        dateMap1.put("value", "MM-DD-YYYY");
        dateMap1.put("displayOrder", "10");

        Map<String, String> dateMap2 = new HashMap<String, String>();
        dateMap2.put("key", "DD-MM-YYYY");
        dateMap2.put("value", "DD-MM-YYYY");
        dateMap2.put("displayOrder", "20");

        Map<String, String> dateMap3 = new HashMap<String, String>();
        dateMap3.put("key", "YYYY-MM-DD");
        dateMap3.put("value", "YYYY-MM-DD");
        dateMap3.put("displayOrder", "30");
        // End DATE_FORMAT

        // Begin TIME_FORMAT
        Map<String, String> timeMap1 = new HashMap<String, String>();
        timeMap1.put("key", "12HR");
        timeMap1.put("value", "12HR");
        timeMap1.put("displayOrder", "10");

        Map<String, String> timeMap2 = new HashMap<String, String>();
        timeMap2.put("key", "24HR");
        timeMap2.put("value", "24HR");
        timeMap2.put("displayOrder", "20");
        // End TIME_FORMAT

        userStatusArrayMap = Lists.newArrayList(statusMap1, statusMap2);
        userSkillArrayMap = Lists.newArrayList(skillMap1, skillMap2);
        dateFormatArrayMap = Lists.newArrayList(dateMap1, dateMap2, dateMap3);
        timeFormatArrayMap = Lists.newArrayList(timeMap1, timeMap2);

        for (String codeName : CollectionsUtilService.nullGuard(validCodeLookupList)) {
            if (codeName == "USER_STATUS") {
                resultObj.put(codeName, userStatusArrayMap);
            } else if (codeName == "USER_SKILL") {
                resultObj.put(codeName, userSkillArrayMap);
            } else if (codeName == "DATE_FORMAT") {
                resultObj.put(codeName, dateFormatArrayMap);
            } else if (codeName == "TIME_FORMAT") {
                resultObj.put(codeName, timeFormatArrayMap);
            }
        }
    }

    /**
     * Unit test method to verify the correct lookup is produced
     *
     * @author DiepLe
     */
	@Test
	public void testGetCodeLookupDataHappyPath(){

        log.debug("CodeLookupServiceUnitTests.testGetCodeLookupDataHappyPath");

        when(codeLookupService.getCodeLookupData(anyList())).thenReturn(resultObj);
        Map<String, Object> retrieveMapData = codeLookupService.getCodeLookupData(validCodeLookupList);

        org.junit.Assert.assertEquals(retrieveMapData, resultObj);
	}

    /**
     * A negative path unit test method to verify that nothing should be returned for invalid lookup codes
     *
     * @author DiepLe
     */
    @Test
    public void testGetCodeLookupDataWithInvalidLookupCode(){
        log.debug("CodeLookupServiceUnitTests.testGetCodeLookupDataWithInvalidLookupCode");
        when(codeLookupService.getCodeLookupData(invalidCodeLookupList)).thenReturn(null);
        Map retrieveMapData = codeLookupService.getCodeLookupData(validCodeLookupList);
        Assert.isTrue(retrieveMapData.size()==0);
    }

}