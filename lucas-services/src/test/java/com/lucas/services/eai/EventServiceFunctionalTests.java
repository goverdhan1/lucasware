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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventEventHandler;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.transport.Transport;
import com.lucas.entity.product.Product;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class EventServiceFunctionalTests extends AbstractSpringFunctionalTests {
	private static final Long NUMBER_ONE = 1L;

	private static final String eventName = "Users data import";

	private String csvDelimitedUserFilePattern = "users_details.*?.csv";

	private static final String eventCategory = "Users Import";

	private static final String eventDescription = "Import user details to the lucas system";

	private static final String eventSource = "WMS";

	private static final String eventSubCategory = "Users Import";

	private static final String eventType = "INBOUND_FILE_ASYNCHRONUS";

	// private static final String inboundFileName = "users_details*.csv";

	private static String EVENT_NAME_DOES_NOT_EXISTS = "eventDoesNotExists";

//	private List<String> eventNames = new ArrayList<String>();

	private Event eaiEvent;

	@Inject
	private EventService eventService;

//	private static final Logger LOG = LoggerFactory
//			.getLogger(EventServiceFunctionalTests.class);

	protected static final String EVENT_NAME = "Users data import";

	@Before
	public void setUp() throws Exception {
		eaiEvent = new Event();
		eaiEvent.setEventId(NUMBER_ONE);
		eaiEvent.setEventName(eventName);
		eaiEvent.setEventCategory(eventCategory);
		eaiEvent.setEventDescription(eventDescription);
		eaiEvent.setEventSource(eventSource);
		eaiEvent.setEventSubCategory(eventSubCategory);
		eaiEvent.setEventType(eventType);

		EventEventHandler handler = new EventEventHandler();
		handler.setEvent(eaiEvent);
		handler.setEventEventHandlerId(1L);
		handler.setExecutionOrder("1");
		Transport eaiTransport = new Transport();

		EventHandler eaiEventHandler = new EventHandler();
		eaiEventHandler.setEaiTransport(eaiTransport);
		handler.setEventHandler(eaiEventHandler);
	}

	@Test
	public void testIfEventDoesNotExists() throws JsonParseException,
			JsonMappingException, IOException {
		Event eaiEventTest = eventService
				.getEventByName(EVENT_NAME_DOES_NOT_EXISTS);

		org.junit.Assert.assertNull(eaiEventTest);
	}

	@Test
	public void testIfEventNameExists() throws JsonParseException,
			JsonMappingException, IOException {
		Event eaiEventTest = eventService.getEventByName(eventName);
		String ExpectedEventName = "Users data import";
		Assert.isTrue(
				eaiEventTest.getEventName().equals(ExpectedEventName),
				"WMS user import service was expected but got "
						+ eaiEventTest.getEventName());
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIfEventNameExistsByEventNames() throws JsonParseException,
			JsonMappingException, IOException {

		final List<String> eventNames = new ArrayList<String>();

		eventNames.add("Mobile device login");
		eventNames.add("Users data import");

		final List<Event> eaiEventListTest = this.eventService
				.getEventByNameList(eventNames);
		Assert.notNull(eaiEventListTest, "Event list is empty");
		Assert.isTrue(eaiEventListTest.size() >= 1);

	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIfEventNameExistsByEventId() throws JsonParseException,
			JsonMappingException, IOException {

		final List<String> eventNames = new ArrayList<String>();

		eventNames.add("Mobile device login");
		eventNames.add("Users data import");

		final Event eaiEventTest = this.eventService.getEventById(NUMBER_ONE);
		Assert.notNull(eaiEventTest, "Event list is empty");
		Assert.isTrue(eaiEventTest.getEventName().equals(eventName));

	}

	@Test
	public void testGetEventNameAndInboundFilePattern()
			throws JsonParseException, JsonMappingException, IOException {

		final Map<String, String> result = this.eventService
				.getEventNameAndInboundFilePattern();

		Assert.notNull(result,
				"Map of event name and the inbound file pattern is null");
		Assert.isTrue(
				result.get(eventName).equals(csvDelimitedUserFilePattern),
				"The file pattern for the event name " + eventName
						+ "is not what expected");

	}
	
	@Test
	  public void testGetEventListBySearchAndSortCriteria() throws ParseException, JsonProcessingException {
	    final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
	    final Map<String, Object> searchMap = new HashMap<String, Object>();

	    searchMap.put("eventName", new ArrayList<String>() {
	      {
	        add(EVENT_NAME);
	      }
	    });


	    searchAndSortCriteria.setSearchMap(searchMap);
	    searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
	      {
	        put("eventName", SortType.ASC);
	      }
	    });
	    searchAndSortCriteria.setPageNumber(0);
	    searchAndSortCriteria.setPageSize(3);
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    final List<Event> eventList = eventService.getEventListBySearchAndSortCriteria(searchAndSortCriteria);
	    Assert.notNull(eventList, "Event List is Null");
	    Assert.isTrue(eventList.size()>=1, "Event list is not having the expected size");
	  }
	@Transactional
	@Rollback(true)
	@Test
	public void testSaveEventToUpdate() {
		Event eventTest = eventService.getEventById(NUMBER_ONE);
		Assert.isTrue(eventTest.getEventDescription().equals("Import user details to the lucas system"));
		eventTest.setEventDescription("Test Description");
		eventService.saveEvent(eventTest);
		Event eventTestNew = eventService.getEventById(NUMBER_ONE);
		Assert.isTrue(eventTestNew.getEventDescription().equals("Test Description"));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testSaveEventToCreate() {
		
		final Event event = new Event();
		event.setEventName("Test Name123");
		event.setEventDescription("Test Description");
		event.setEventCategory("Test category");
		event.setEventSource("Test Event");
		event.setEventSubCategory("Event sub category");
		event.setEventType("Test type");
		
		Set<EventEventHandler> eeHandlerSet = new HashSet<EventEventHandler>();
		
		final EventHandler eHandler = new EventHandler();
		eHandler.setEventHandlerId(Long.valueOf(1));
		
		EventEventHandler eeHandler = new EventEventHandler();
		eeHandler.setEventEventHandlerId(null);
		eeHandler.setSeq(2);
		eeHandler.setEventHandler(eHandler);
		eeHandlerSet.add(eeHandler);
		
		eeHandler = new EventEventHandler();
		eeHandler.setEventEventHandlerId(null);
		eeHandler.setSeq(2);
		eHandler.setEventHandlerId(Long.valueOf(2));
		eeHandler.setEventHandler(eHandler);
		eeHandlerSet.add(eeHandler);
		
		eventService.saveEvent(event);
		Event eventTestNew = eventService.getEventByName("Test Name123");
		Assert.isTrue(eventTestNew.getEventDescription().equals("Test Description"));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testDeleteEvent() {
		Event eventTest = eventService.getEventById(NUMBER_ONE);
		Assert.isTrue(eventTest.getEventDescription().equals("Import user details to the lucas system"));
		eventService.deleteEventById(eventTest.getEventId());
		Event eventTestNew = eventService.getEventById(NUMBER_ONE);
		Assert.isNull(eventTestNew);
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testDeleteEventHandler() {
		EventHandler eventHandlerTest = eventService.getEventHandlerById(NUMBER_ONE);
		Assert.isTrue(eventHandlerTest.getEventHandlerName().equals("Users data import"));
		eventService.deleteEventHandlerById(NUMBER_ONE);
		EventHandler eventHandlerTestNew = eventService.getEventHandlerById(NUMBER_ONE);
		Assert.isNull(eventHandlerTestNew);
	}
}
