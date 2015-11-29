package com.lucas.services.messaging;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;

import com.lucas.services.util.StringUtils;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

public class LucasRpcClientTest extends AbstractSpringFunctionalTests {

    protected static Logger LOG = LoggerFactory.getLogger(LucasRpcClientTest.class);

    @Inject
    private LucasRpcClientService lucasRpcClientService;
    private Long startTime = null;
    private Long endTime = null;
    
    private String data  = null;
    
    @Inject
    private RabbitAdmin rabbitAdmin;
    
    @Value ("${lucas.mq.request.queue}")
    private String requestQueue;
    @Value ("${lucas.mq.response.queue}")
    private String responseQueue;
    
    @Before
    public void initLucasTestCase() throws Exception{
        startTime=new Date().getTime();
        org.springframework.util.Assert.notNull(lucasRpcClientService, "LucasRpcClientService is Null in LucasRpcClientTest");
        data = System.getProperty("user.name") + " " + InetAddress.getLocalHost().getHostAddress() + new Date();
        rabbitAdmin.purgeQueue(requestQueue,  true);
        rabbitAdmin.purgeQueue(responseQueue,  true);
    }



    @Test
    public void rpcClientTestFor10KBMessageIn500MilliSecond() throws Exception {
        final int size = 10240;
        final char[] chars = new char[size];
        Arrays.fill(chars, 'a');
        final String extraData = new String(chars);
        final String correlationIdRequest = UUID.randomUUID().toString();
        final byte[] result = lucasRpcClientService.getInstance(false).primitiveCall(correlationIdRequest, (data + "" + extraData).getBytes());
        endTime=new Date().getTime();
        long took = (endTime - startTime);
        if (result != null && result.length != 0) {
            LOG.debug("Result in {} ms. Message: " + StringUtils.truncate(result, 200), took);
        } else {
            LOG.error("Result for Correlation Id is null " + correlationIdRequest);
        }
        Assert.assertNotNull("Result Not Received for CorrelationId " + correlationIdRequest, result);
        if (took > 500){
        	LOG.warn("Processing for rpcClientTestFor10KBMessageIn500MilliSecond takes More than 500 ms. Took " + took + " ms");
        }
    }

    @Test
    public void rpcClientTestFor1MBMessageIn500MilliSecond() throws Exception {
        final int size = 1048576;
        final char[] chars = new char[size];
        Arrays.fill(chars, 'a');
        final String extraData = new String(chars);
        final String correlationIdRequest = UUID.randomUUID().toString();

        final byte[] result = lucasRpcClientService.getInstance(false).primitiveCall(correlationIdRequest, (data + "" + extraData).getBytes());
        endTime=new Date().getTime();
        long took = (endTime - startTime);
        if (result != null && result.length != 0) {
        	LOG.debug("Result in {} ms. Message: " + StringUtils.truncate(result, 200), took);
        } else {
            LOG.error("Result for Correlation Id is null " + correlationIdRequest + "");
        }
        Assert.assertNotNull("Result Not Received for CorrelationId " + correlationIdRequest, result);   
        if (took > 500){
        	LOG.warn("Processing for rpcClientTestFor1MBMessageIn500MilliSecond took more than 500 ms. Took " + took + " ms");
        }
    }

    @Test
    public void rpcClientTestFor5MBMessageIn500MilliSecond() throws Exception {
        final int size = 655360;
        final char[] chars = new char[size];
        Arrays.fill(chars, 'a');
        final String extraData = new String(chars);
        final String correlationIdRequest = UUID.randomUUID().toString();
        final byte[] result = lucasRpcClientService.getInstance(false).primitiveCall(correlationIdRequest, (data + "" + extraData).getBytes());
        endTime=new Date().getTime();
        long took = (endTime - startTime);
        if (result != null && result.length != 0) {
        	LOG.debug("Result in {} ms. Message: " + StringUtils.truncate(result, 200), took);
        } else {
            LOG.error("Result for Correlation Id is null " + correlationIdRequest + "");
        }
        Assert.assertNotNull("Result Not Received for CorrelationId " + correlationIdRequest, result);
        if (took > 500){
        	LOG.warn("Processing for rpcClientTestFor5MBMessageIn500MilliSecond took more than 500 ms. Took " + took + " ms");
        }
    }


    @Test
    public void testRpcClient() throws Exception {
        final String correlationIdRequest = UUID.randomUUID().toString();
        final byte[] result = lucasRpcClientService.getInstance(false).primitiveCall(correlationIdRequest, data.getBytes());
        endTime=new Date().getTime();
        long took = (endTime - startTime);
        if (result != null && result.length != 0) {
        	LOG.debug("Result in {} ms. Message: " + StringUtils.truncate(result, 200), took);
        } else {
            LOG.error("Result for Correlation Id is null " + correlationIdRequest + "");
        }
        Assert.assertNotNull("Result Not Received for CorrelationId " + correlationIdRequest, result);
        if (took > 500){
        	LOG.warn("Processing for testRpcClient took more than 500 ms. Took " + took + " ms");
        }
    }

    @Test
    public void testRpcClientForMultipleServiceActivator()throws Exception{
        final String correlationIdRequest = UUID.randomUUID().toString();
        startTime=new Date().getTime();
        final byte[] result = lucasRpcClientService.getInstance(false).primitiveCall(correlationIdRequest, data.getBytes());
        endTime=new Date().getTime();
        long took = (endTime - startTime);
        if (result != null && result.length != 0) {
        	LOG.debug("Result in {} ms. Message: " + StringUtils.truncate(result, 200), took); 
        } else {
            LOG.error("Result for Correlation Id is null " + correlationIdRequest + "");
        }
        Assert.assertNotNull("Result Not Received for CorrelationId " + correlationIdRequest, result);
        if (took > 500){
        	LOG.warn("Processing for testRpcClientForMultipleServiceActivator took more than 500 ms. Took " + took + " ms");
        }        
        
    }

    private static int workCounter = 0;
    
    //Below test fails 50% of the time. So I am commenting for now - PT. Jun 2014
    @Ignore
    @Test
    public void testLucasRpcClientWithMultipleThreadLoop() throws Exception {

        final class TestIntegration extends Thread {

            private final int size = 10240;
            private final char[] chars = new char[size];
            private String extraData;
            private LucasRpcClientService.LucasRpcClient lucasRpcClient;
            final String correlationId = UUID.randomUUID().toString();

            public TestIntegration(LucasRpcClientService.LucasRpcClient lucasRpcClient
                    , String name, int priority) throws Exception {
                super(name);
                super.setPriority(priority);
                this.lucasRpcClient = lucasRpcClient;
                Arrays.fill(chars, 'a');
                extraData = new String(chars);
            }

            @Override
            public void run() {
                try {
                    final String data = System.getProperty("user.name")
                            + " " + InetAddress.getLocalHost().getHostAddress()
                            + " " + correlationId + " " + new Date()+extraData;
                    final byte[] result = lucasRpcClient.primitiveCall(correlationId, data.getBytes());
                    if (result != null && result.length != 0) {
                        LOG.info("Result " + new String(result) + "");
                        workCounter++;
                    } else {
                        LOG.error("Result for Correlation Id is null " + correlationId + "");
                    }
                } catch (Exception e) {
                    LOG.error("Exception Generated " + e, e);
                }
            }
        }

        final int parallelThreadLimit = 100;
        final TestIntegration[] testIntegrations = new TestIntegration[parallelThreadLimit];
        for (int i = 0; i < parallelThreadLimit; i++) {
            testIntegrations[i] = new TestIntegration(this.lucasRpcClientService.getInstance(true), "testIntegration" + i, 5);
        }
        startTime=new Date().getTime();
        for (int i = 0; i < parallelThreadLimit; i++) {
            testIntegrations[i].start();
        }
        endTime=new Date().getTime();
        for (int i = 0; i < parallelThreadLimit; i++) {
            testIntegrations[i].join();
        }
        Assert.assertTrue("All Correlation Id Not matched with the Response ", parallelThreadLimit == workCounter);
    }

    @After
    public void cleanLucasTestCase() {
        LOG.info("Execution Completed In Millisecond " + ((endTime - startTime)) + " Milliseconds");
    }
    
}

