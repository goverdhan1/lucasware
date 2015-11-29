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

import com.lucas.services.user.UserService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class ParallelRestCallTest extends AbstractControllerTests {

    @Inject
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(ParallelRestCallTest.class);
    private static final String USERNAME_JACK = "jack123";
    private static final String USERNAME_JILL = "jill123";
    private static final String USERNAME_ADMIN = "admin123";
    private static final String PASSWORD_JACK="secret";

    @Test
    public void postThreadTest()throws Exception{
        final int threadLimit=1000;
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url="/users/groups/permissionslist";
        final List<String> userNameList=new ArrayList<>();
        userNameList.add(USERNAME_JACK);
        userNameList.add(USERNAME_JILL);
        userNameList.add(USERNAME_ADMIN);
        final ExecutorService executor = Executors.newFixedThreadPool(threadLimit);
        final List<PostThreads> postThreadsList=new ArrayList<>();
        for(int index=0;index<threadLimit;index++){
            PostThreads  postThreads=new PostThreads(url,token, MediaType.APPLICATION_JSON,userNameList);
            postThreadsList.add(postThreads);
        }

        final List<Integer> responseStatusList=new ArrayList<>();
        final List<Future<Object>> futureList = executor.invokeAll(postThreadsList);
        for(Future<Object> responseFuture:futureList){
            LOG.info("Response From Server for Parallel Call =============> "+responseFuture.toString());
            final MockHttpServletResponse response=(MockHttpServletResponse)responseFuture.get();
            final int responseStatus=response.getStatus();
            LOG.info("Response Status for Post Calls "+responseStatus);
            LOG.info("Response Data from the Post Calls "+response.getContentAsString());
            responseStatusList.add(responseStatus);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {   }

        Assert.assertTrue("Response Received for Less then Send Request ",(responseStatusList.size()==threadLimit));
        for(Integer status:responseStatusList){
            Assert.assertTrue("Response Failed",(status==200));
        }
    }

}
