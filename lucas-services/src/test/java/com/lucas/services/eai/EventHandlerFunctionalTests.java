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
package com.lucas.services.eai;


import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.transport.Transport;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class EventHandlerFunctionalTests extends AbstractSpringFunctionalTests {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerFunctionalTests.class);

    @Inject
    private EventHandlerService eventHandlerService;

    private static final String eventHandlerName = "Users data import";

    @Before
    public void init() {
        LOG.info("Init Method of EventHandlerFunctionalTests");
    }

    /**
     * testGetEventHandlerByName() provide the testing functionality for
     * getting the EventHandler based on the eventHandlerName
     *
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     */
    @Transactional
    @Test
    public void testGetEventHandlerByName() {
        final EventHandler eventHandler = this.eventHandlerService.getEventHandlerByName(eventHandlerName, Boolean.TRUE);
        Assert.notNull(eventHandler, "EventHandler is null");
    }

    /**
     * testSaveEventHandler() provide the functionality for saving the EventHandler based on the
     * EventHandler instance supplied to the method.
     *
     * @see com.lucas.services.eai.EventHandlerService#saveEventHandler(com.lucas.entity.eai.event.EventHandler)
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testSaveEventHandler() {
        final EventHandler eventHandler = getSampleEventHandler();
        final Boolean result = this.eventHandlerService.saveEventHandler(eventHandler);
        LOG.info("Result of the Saving EventHandler " + result);
        Assert.isTrue(result, "Event Handler Not Saved ");
    }

    /**
     * testUpdateEventHandler() provide the functionality for saving the EventHandler based on the
     * EventHandler instance supplied to the method.
     *
     * @see com.lucas.services.eai.EventHandlerService#saveEventHandler(com.lucas.entity.eai.event.EventHandler)
     */
    @Rollback(value = true)
    @Transactional(readOnly = false)
    @Test
    public void testUpdateEventHandler() {
        final EventHandler eventHandler = getSampleEventHandler();
        final Boolean insertResult = this.eventHandlerService.saveEventHandler(eventHandler);
        LOG.info("Result of the Saving EventHandler " + insertResult);
        Assert.isTrue(insertResult, "Event Handler Not Saved ");
        final Boolean updateResult = this.eventHandlerService.saveEventHandler(eventHandler);
        LOG.info("Result of the Saving EventHandler " + updateResult);
        Assert.isTrue(updateResult, "Event Handler Not Updated ");
    }

    /**
     * getSampleEventHandler() provide the functionality for getting
     * the dummy Event Handler to check the save for update functionality
     * for the event handler.
     *
     * @return instance of EventHandler containing data
     */
    private EventHandler getSampleEventHandler() {
        final EventHandler eventHandler = new EventHandler();
        eventHandler.setEaiTransport(new Transport() {
            {
                setTransportName("File inbound async");
            }
        });

        eventHandler.setEaiMappingByInboundMappingId(new Mapping() {
            {
                setMappingId(1L);
            }
        });
        eventHandler.setEventHandlerName("Dummy-Event-Handler");
        eventHandler.setEventHandlerDescription("Dummy-Event-Handler-Description");
        eventHandler.setEventHandlerType("Dummy-Event-Handler-Type");
        eventHandler.setInboundFilePattern("Dummy-Event-Handler-Inbound-File-Pattern");
        eventHandler.setOutboundFilePattern("Dummy-Event-Handler-Outbound-File-Pattern");
        eventHandler.setUsageInEvent(1);
        return eventHandler;
    }
}
