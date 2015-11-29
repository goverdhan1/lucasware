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
package com.lucas.alps.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.lucas.services.application.CodeLookupService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import scala.util.parsing.json.JSON;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CodeLookupControllerFunctionTests extends AbstractControllerTests{

	private static final Logger LOG = LoggerFactory.getLogger(CodeLookupControllerFunctionTests.class);

    private static final String CODE_LOOKUP_URL = "/application/codes";


	@Inject
	private CodeLookupService codeLookupService;


    /**
     * Happy path test method to retrieve code lookup seed data
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
	@Transactional(readOnly = true)
	public void testGetCodeLookupData() throws Exception{

		List<String> codeLookupList = new ArrayList<String>();
        codeLookupList.add("USER_STATUS");
        codeLookupList.add("USER_SKILL");
        codeLookupList.add("DATE_FORMAT");
        codeLookupList.add("TIME_FORMAT");

        String token = generateTokenWithAuthenticatedUser();

        ResultActions authResultActions = this.mockMvc.perform(post(CODE_LOOKUP_URL)
                    .header("Authentication-token", token)
                    .content(super.getJsonString(codeLookupList))
                    .contentType(MediaType.APPLICATION_JSON));

		LOG.debug("CodeLookupControllerFunctionTests.testGetCodeLookupDatacontroller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
		String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
		Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        Assert.isTrue(dataObj.toString().contains("USER_STATUS"), "USER_STATUS object is not in the json list");
        Assert.isTrue(dataObj.toString().contains("USER_SKILL"), "USER_SKILL object is not in the json list");
        Assert.isTrue(dataObj.toString().contains("TIME_FORMAT"), "TIME_FORMAT object is not in the json list");
        Assert.isTrue(dataObj.toString().contains("DATE_FORMAT"), "DATE_FORMAT object is not in the json list");

        // remove special characters which cause assertion errors
        String userStatus = dataObj.get("USER_STATUS").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(userStatus, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(userStatus, "value:0"));
        Assert.isTrue(StringUtils.contains(userStatus, "key:0"));
        Assert.isTrue(StringUtils.contains(userStatus, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(userStatus, "value:1"));
        Assert.isTrue(StringUtils.contains(userStatus, "key:1"));


        String userSkill = dataObj.get("USER_SKILL").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(userSkill, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(userSkill, "value:STANDARD"));
        Assert.isTrue(StringUtils.contains(userSkill, "key:STANDARD"));
        Assert.isTrue(StringUtils.contains(userSkill, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(userSkill, "value:ADVANCED"));
        Assert.isTrue(StringUtils.contains(userSkill, "key:ADVANCED"));


        String dateFormat = dataObj.get("DATE_FORMAT").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:MM-DD-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:MM-DD-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:DD-MM-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:DD-MM-YYYY"));
        Assert.isTrue(StringUtils.contains(dateFormat, "displayOrder:30"));
        Assert.isTrue(StringUtils.contains(dateFormat, "value:YYYY-MM-DD"));
        Assert.isTrue(StringUtils.contains(dateFormat, "key:YYYY-MM-DD"));



        String timeFormat = dataObj.get("TIME_FORMAT").toString().replace("\"","").replace("[","").replace("]","").replace("{","").replace("}","");
        Assert.isTrue(StringUtils.contains(timeFormat, "displayOrder:10"));
        Assert.isTrue(StringUtils.contains(timeFormat, "value:12HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "key:12HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "displayOrder:20"));
        Assert.isTrue(StringUtils.contains(timeFormat, "value:24HR"));
        Assert.isTrue(StringUtils.contains(timeFormat, "key:24HR"));
    }



    /**
     * Test method to verify when a null lookup value will produce an exception
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetCodeLookupDataWithNullLookupData() throws Exception{

        List<String> codeLookupList = new ArrayList<String>();
        codeLookupList.add("USER_STATUS");
        codeLookupList.add("USER_SKILL");
        codeLookupList.add("");

        String token = generateTokenWithAuthenticatedUser();

        ResultActions authResultActions = this.mockMvc.perform(post(CODE_LOOKUP_URL)
                .header("Authentication-token", token)
                .content(super.getJsonString(codeLookupList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("CodeLookupControllerFunctionTests.testGetCodeLookupDataWithNullLookupData controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1024"), "status is not 1024");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("No lookup codes exists"), "It's not No lookup codes exists");
    }

    /**
     * Test method to verify when a null lookup value will produce an exception
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetCodeLookupDataWithNullValueLookupData() throws Exception{

        List<String> codeLookupList = new ArrayList<String>();
        codeLookupList.add("USER_STATUS");
        codeLookupList.add("USER_SKILL");
        codeLookupList.add(null);

        String token = generateTokenWithAuthenticatedUser();

        ResultActions authResultActions = this.mockMvc.perform(post(CODE_LOOKUP_URL)
                .header("Authentication-token", token)
                .content(super.getJsonString(codeLookupList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("CodeLookupControllerFunctionTests.testGetCodeLookupDataWithNullValueLookupData controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1028"), "status is not 1028");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Cannot search from invalid lookup codes"), "Cannot search from invalid lookup codes");
    }


    /**
     * Test method to verify when an invalid lookup value will produce an exception
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetCodeLookupDataWithInvalidLookupCodes() throws Exception{

        List<String> codeLookupList = new ArrayList<String>();
        codeLookupList.add("USER_STATUS");
        codeLookupList.add("USER_SKILL");
        codeLookupList.add("INVALID_LOOKUP_DATA");

        String token = generateTokenWithAuthenticatedUser();

        ResultActions authResultActions = this.mockMvc.perform(post(CODE_LOOKUP_URL)
                .header("Authentication-token", token)
                .content(super.getJsonString(codeLookupList))
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("CodeLookupControllerFunctionTests.testGetCodeLookupDataWithInvalidLookupCodes controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1028"), "status is not 1028");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Cannot search from invalid lookup codes"), "It's not Cannot search from invalid lookup codes");
    }


    /**
     * Test method to verify when an empty lookup array will produce an exception
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
    public void testGetCodeLookupDataWithEmptyArrayLookupList() throws Exception{

        List<String> codeLookupList = new ArrayList<String>();

        String token = generateTokenWithAuthenticatedUser();

        ResultActions authResultActions = this.mockMvc.perform(post(CODE_LOOKUP_URL)
                .header("Authentication-token", token)
                .content(super.getJsonString(codeLookupList))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("CodeLookupControllerFunctionTests.testGetCodeLookupDataWithEmptyArrayLookupList controller returns : {}", authResultActions.andReturn().getResponse().getContentAsString());
        String jsonString = authResultActions.andReturn().getResponse().getContentAsString();
        Assert.hasText(jsonString);

        JSONObject obj = new JSONObject(jsonString);
        Assert.isTrue(obj.get("code").toString().equals("1027"), "status is not 1027");
        Assert.isTrue(obj.get("level").toString().equals("ERROR"), "level is not ERROR");
        Assert.isTrue(obj.get("status").toString().equals("failure"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Cannot search from empty lookup codes"), "It's not Cannot search from empty lookup codes");
    }
}
