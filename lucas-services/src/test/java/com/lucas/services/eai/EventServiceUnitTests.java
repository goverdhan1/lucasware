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

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.eai.EventDAO;
import com.lucas.dao.eai.EventHandlerDAO;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventEventHandler;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.transport.Transport;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { StandardPBEStringEncryptor.class, StringUtils.class })
public class EventServiceUnitTests {

	private static final Long NUMBER_ONE = 1L;

	private static final String eventName = "WMS user import service";

	private static final String eventCategory = "Users Import";

	private static final String eventDescription = "Import user details to the lucas system";

	private static final String eventSource = "WMS";

	private static final String eventSubCategory = "Users Import";

	private static final String eventType = "INBOUND_FILE_ASYNCHRONUS";

	private static final String inboundFileName = "users_details*.csv";

	private List<String> eventNames = new ArrayList<String>();

	@Mock
	EventDAO eventDao;
	
	@Mock
	EventHandlerDAO eventHandlerDao;

	@Inject
	private EventService eventService;

	private Event event;
	private List<Event> eventList;

	@Before
	public void setUp() throws Exception {

		eventService = Mockito.spy(new EventServiceImpl(eventDao, eventHandlerDao));
		event = new Event();
		event.setEventId(NUMBER_ONE);
		event.setEventName(eventName);
		event.setEventCategory(eventCategory);
		event.setEventDescription(eventDescription);
		event.setEventSource(eventSource);
		event.setEventSubCategory(eventSubCategory);
		event.setEventType(eventType);

		// Set<EaiEventEventHandler> eaiEventEventHandlerSet = new
		// HashSet(EaiEventEventHandler);
		EventEventHandler handler = new EventEventHandler();
		handler.setEvent(event);
		handler.setEventEventHandlerId(1L);
		handler.setExecutionOrder("1");
		Transport eaiTransport = new Transport();

		EventHandler eaiEventHandler = new EventHandler();
		eaiEventHandler.setEaiTransport(eaiTransport);
		handler.setEventHandler(eaiEventHandler);
		// eaiEventEventHandlerSet.add(handler);
		// eaiEvent.setEaiEventEventHandlers(eaiEventEventHandlerSet);
	}

	@Test
	public void testGetEaiEventById() throws JsonParseException,
			JsonMappingException, IOException {
		when(eventDao.getEventById(anyLong())).thenReturn(event);
		Event eaiEventTest = eventService.getEventById(NUMBER_ONE);
		Assert.notNull(eaiEventTest);
		Assert.isTrue(eaiEventTest.getEventId() == event.getEventId());
		verify(eventDao, times(1)).getEventById(NUMBER_ONE);
	}

	@Test
	public void testGetEaiEventByName() throws JsonParseException,
			JsonMappingException, IOException {

		when(eventDao.getEventByName(anyString())).thenReturn(event);
		Event eaiEventTest = eventService.getEventByName(eventName);
		Assert.notNull(eaiEventTest);
		Assert.isTrue(eaiEventTest.getEventId() == event.getEventId());
		verify(eventDao, times(1)).getEventByName(eventName);
	}

	@Test
	public void testGetEaiEventByNameList() throws JsonParseException,
			JsonMappingException, IOException {
		eventNames.add("User Import");
		// eventNames.add("User Acknowledgement");
		when(eventDao.getEventByNameList(anyList())).thenReturn(eventList);
		eventService.getEventByNameList(eventNames);
		verify(eventDao, times(1)).getEventByNameList(eventNames);
	}

}
