/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.alps.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.inject.Inject;

import org.activiti.engine.impl.util.json.JSONObject;
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

import com.lucas.services.device.DeviceService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")})
public class DeviceControllerFuntionalTests extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceControllerFuntionalTests.class);

    @Inject
    private DeviceService deviceService;



    @Test
    @Transactional
    public void testGetDeviceSetOptions() throws Exception {

        final String url = "/mobileapp/setoptions/32085421875d5167";
        final ResultActions apiResultActions = this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);

        JSONObject response = new JSONObject(apiResultActions.andReturn().getResponse().getContentAsString());
        // Assert API call was successful
        org.junit.Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        org.junit.Assert.assertTrue("API response status was not 'success'", response.get("status").equals("success"));

        org.junit.Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        // org.junit.Assert.assertTrue("API response code was not '1017'",
        // response.get("code").equals("1017"));

        org.junit.Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        org.junit.Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("Request processed successfully"));

        org.junit.Assert.assertTrue("API Response does not have property 'data'", response.has("data"));
        JSONObject data = new JSONObject(response.get("data").toString());

        org.junit.Assert.assertTrue("Data element does not contain property 'appVersion'", data.has("appVersion"));
        org.junit.Assert.assertTrue("Data element does not contain property 'appVersion'", data.get("appVersion").equals("8.0.0.1234"));
        org.junit.Assert.assertTrue("Data element does not contain property 'appName'", data.has("appName"));
        org.junit.Assert.assertTrue("Data element does not contain property 'appName'", data.get("appName").equals("Framework 8.0"));
        org.junit.Assert.assertTrue("Data element does not contain property 'defaultDisplayLangCode'", data.has("defaultDisplayLangCode"));
        org.junit.Assert.assertTrue("Data element does not contain property 'defaultDisplayLangCode'", data.get("defaultDisplayLangCode").equals("en-us"));
        org.junit.Assert.assertTrue("Data element does not contain property 'spapiMessagingVersion'", data.has("spapiMessagingVersion"));
        org.junit.Assert.assertTrue("Data element does not contain property 'spapiMessagingVersion'", data.get("spapiMessagingVersion").equals("3"));

    }

}
