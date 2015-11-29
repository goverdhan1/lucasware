package com.lucas.services.messaging;


import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import com.lucas.services.messaging.client.LucasMessageBroadcastClientService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import com.rabbitmq.client.AMQP;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.util.Assert;

import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.messaging.client.LucasMessageBroadcastClientService;
import com.lucas.services.messaging.client.LucasMessageBroadcastClientServiceImpl;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.File;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/20/14  Time: 4:20 PM
 * This Class provide the implementation for the functionality of..
 */

public class LucasBroadCastServiceTest extends AbstractSpringFunctionalTests {

    protected static Logger LOG = LoggerFactory.getLogger(LucasBroadCastServiceTest.class);

    @Inject
    private LucasMessageBroadcastService lucasBroadCastService;


    @Inject
    private LucasMessageBroadcastClientService lucasMessageBroadcastClientService;

    @Inject
    private RabbitAdmin rabbitAdmin;

    private List<String> queueNameList;
    private MultiMap pushedQueueMap;
    private MultiMap pulledQueueMap;

    private static final String TEST_QUEUE_PREFIX = "lucas.test.queue";

    @Before
    public void initLucasTestCase() {
        Assert.notNull(rabbitAdmin, "RabbitAdmin not set. Please configure it in the Spring context");
        queueNameList = new ArrayList<String>();
        pushedQueueMap = new MultiValueMap();
        pushedQueueMap = MultiValueMap.decorate(pushedQueueMap, ArrayList.class);

        pulledQueueMap = new MultiValueMap();
        pulledQueueMap = MultiValueMap.decorate(pulledQueueMap, ArrayList.class);

        for (int i = 0; i < 100; i++) {
            try {
                rabbitAdmin.purgeQueue(TEST_QUEUE_PREFIX + i, true);
            } catch (Exception e) {
                //swallow
            }
        }
    }


    @Test
    public void testPublishAndSubscribe() throws Exception {
        final int limit = 10;
        for (int index = 0; index < limit; index++) {
            final String queueName = TEST_QUEUE_PREFIX + index;
            queueNameList.add(queueName);
            final String bindKey = TEST_QUEUE_PREFIX + ".Binding" + index;
            final String data = System.getProperty("user.name") + " " + InetAddress.getLocalHost().getHostAddress()
                    + "  " + " " + new Date();
            this.lucasBroadCastService.sendMessage(queueName, bindKey, data);
            byte[] retrievedByteArray = this.lucasMessageBroadcastClientService.receiveMessage(queueName, bindKey, 1000L);
            String retrievedString = retrievedByteArray != null ? (new String(retrievedByteArray)) : null;
            Assert.isTrue(StringUtils.equals(data, retrievedString),
                    "The returned values do not match. \nSent: " + data + "\nRecd: " + retrievedString);

        }
    }

    @Test
    public void testOrderOfReceipts() throws Exception {
        queueNameList.add("aQueueName");

        this.lucasBroadCastService.sendMessage("aQueueName", "aBindKey", "String1");
        this.lucasBroadCastService.sendMessage("aQueueName", "aBindKey", "String2");
        this.lucasBroadCastService.sendMessage("aQueueName", "aBindKey", "String3");


        String s = this.lucasMessageBroadcastClientService.receiveFirstMessage("aQueueName", "aBindKey", 1000L);
        LOG.info("Retrieved: " + s);
        Assert.notNull(s, "retrieved string should not be null");
        Assert.isTrue(StringUtils.equals("String1", s),
                "The returned values do not match. \nSent: " + "String1" + "\nRecd: " + s);

        s = this.lucasMessageBroadcastClientService.receiveFirstMessage("aQueueName", "aBindKey", 1000L);
        Assert.notNull(s, "retrieved string should not be null");
        Assert.isTrue(StringUtils.equals("String2", s),
                "The returned values do not match. \nSent: " + "String2" + "\nRecd: " + s);

        s = this.lucasMessageBroadcastClientService.receiveFirstMessage("aQueueName", "aBindKey", 1000L);
        Assert.notNull(s, "retrieved string should not be null");
        Assert.isTrue(StringUtils.equals("String3", s),
                "The returned values do not match. \nSent: " + "String3" + "\nRecd: " + s);
    }

    @Test
    public void testPublishAndSubscribeBroadCastServiceWithSeparateThread() throws Exception {
        final Random random = new Random(System.currentTimeMillis());

        final class LucasBroadCastServiceThread extends Thread {

            private LucasMessageBroadcastService lucasMessageBroadcastService;
            final String randomString = UUID.randomUUID().toString();
            String queueName;
            String bindKey;
            String data;


            public LucasBroadCastServiceThread(final LucasMessageBroadcastService lucasMessageBroadcastService,
                                               final String threadName,
                                               final int priority) throws Exception {

                super(threadName);
                super.setPriority(priority);
                this.lucasMessageBroadcastService = lucasMessageBroadcastService;
                final int randomNum = random.nextInt(10) + 1;
                this.queueName = TEST_QUEUE_PREFIX + randomNum;
                this.bindKey = TEST_QUEUE_PREFIX + ".Binding" + randomNum;
                this.data = randomString;
            }


            @Override
            public void run() {
                try {
                    LOG.debug("Posting queueName: " + queueName + " and data: " + data);
                    pushedQueueMap.put(queueName, data);
                    this.lucasMessageBroadcastService.sendMessage(queueName, bindKey, data);
                } catch (Exception e) {
                    LOG.error("Exception Generated ", e);
                    throw new LucasRuntimeException(e);
                }
            }
        }

        final class LucasBroadCastClientThread extends Thread {

            private LucasMessageBroadcastClientService lucasMessageBroadcastClientService;
            private String lucasBroadCastQueue;
            private String lucasBroadCastBinding;

            public LucasBroadCastClientThread(LucasMessageBroadcastClientService lucasMessageBroadcastClientService,
                                              String threadName,
                                              int threadPriority,
                                              String lucasBroadCastQueue,
                                              String lucasBroadCastBinding) {
                super(threadName);
                super.setPriority(threadPriority);
                this.lucasMessageBroadcastClientService = lucasMessageBroadcastClientService;
                this.lucasBroadCastBinding = lucasBroadCastBinding;
                this.lucasBroadCastQueue = lucasBroadCastQueue;
            }

            @Override
            public void run() {
                try {
                    String[] stringArray = this.lucasMessageBroadcastClientService.receiveAllMessagesWithinTimePeriod(this.lucasBroadCastQueue, this.lucasBroadCastBinding, 1L, 5000L);
                    if (stringArray != null){
                        for (String string : stringArray) {
                            pulledQueueMap.put(this.lucasBroadCastQueue, string);
                        }
                    }

                } catch (Exception e) {
                    LOG.error("Exception Generated ", e);
                    throw new LucasRuntimeException(e);
                }
            }
        }

        //
        //Publisher Threads
        final int maxThreads = 1000;
        ExecutorService pushExecutor = Executors.newFixedThreadPool(maxThreads);
        CompletionService<?> pushCompletion = new ExecutorCompletionService(pushExecutor);
        for (int i = 0; i < maxThreads; i++) {
            LucasBroadCastServiceThread thread = new LucasBroadCastServiceThread(this.lucasBroadCastService
                    , "LucasBroadCastServiceThread" + i, 5);
            pushCompletion.submit(thread, null);
        }
        for (int i = 0; i < maxThreads; ++i) {
            pushCompletion.take();
        }
        pushExecutor.shutdown();


        //Subscriber threads
        int j = 0;
        //Get the queueNames stored while publishing...
        Set<String> keySet = pushedQueueMap.keySet();
        if (keySet != null){
            ExecutorService pullExecutor = Executors.newFixedThreadPool(keySet.size());
            CompletionService<?> pullCompletion = new ExecutorCompletionService(pullExecutor);
            for (String queueName : keySet) {
                //...and receive on each queueName
                LucasBroadCastClientThread thread = new LucasBroadCastClientThread(lucasMessageBroadcastClientService,
                        "LucasBroadCastClientThread" + j,
                        5,
                        queueName,
                        TEST_QUEUE_PREFIX + ".Binding" + j);
                pullCompletion.submit(thread, null);
                j++;
            }
            for (int i = 0; i < keySet.size(); ++i) {
                pullCompletion.take();
            }
            pullExecutor.shutdown();
        }


        LOG.debug("Pushed values:");
        CollectionsUtilService.dumpMultiMapNumbers(pushedQueueMap);
        LOG.debug("Pulled values:");
        CollectionsUtilService.dumpMultiMapNumbers(pulledQueueMap);
        //Assert
        junit.framework.Assert.assertTrue( "The pushed and pulled values do not match!", CollectionsUtilService.compareMultiMaps(pushedQueueMap, pulledQueueMap));
    }




    @Test
    public void testBinaryFileRoundTrip() throws Exception {
        final String queueName = "lucas.broadCastQueue";
        final String bindKey = "lucas.broadCastQueue.Binding";
        final String fileNameSend = "src/test/resources/file/send/GAYATRI_MANTRA_SEND.mp3";
        this.lucasBroadCastService.sendBinaryFile(queueName, bindKey, fileNameSend);
        final String fileNameReceive = "src/test/resources/file/receive/GAYATRI_MANTRA_RECEIVE.mp3";
        final File receiveFile = this.lucasMessageBroadcastClientService.receiveBinaryFile(fileNameReceive, queueName, bindKey);
        final File sendFile = new File(fileNameSend);

        Assert.isTrue((sendFile.length() == receiveFile.length()), "Send File and Receive File Doesn't have Same Size");
    }


    @Test
    public void testTextFileRoundTrip() throws Exception {
        final String queueName = "lucas.broadCastQueue";
        final String bindKey = "lucas.broadCastQueue.Binding";
        final String fileNameSend = "src/test/resources/file/send/sample_send.txt";
        this.lucasBroadCastService.sendBinaryFile(queueName, bindKey, fileNameSend);
        final String fileNameReceive = "src/test/resources/file/receive/sample_receive.txt";
        final File receiveFile = this.lucasMessageBroadcastClientService.receiveBinaryFile(fileNameReceive, queueName, bindKey);
        final File sendFile = new File(fileNameSend);

        Assert.isTrue((sendFile.length() == receiveFile.length()), "Send File and Receive File Doesn't have Same Size");
    }



    @After
    public void clearAllQueue() throws Exception {
        for (String name : queueNameList) {
            final boolean result = this.rabbitAdmin.deleteQueue(name);
            LOG.debug((result) ? "\t\tQueue Deletion Successful for: " + name : "\t\tQueue Deletion UnSuccessful for: " + name);
        }
        for (Object queueName : pushedQueueMap.keySet()) {
            final boolean result = this.rabbitAdmin.deleteQueue((String) queueName);
            LOG.debug((result) ? "\t\tQueue Deletion Successful for: " + queueName : "\t\tQueue Deletion UnSuccessful for: " + queueName);
        }
        queueNameList.clear();
        pushedQueueMap.clear();
    }

}
