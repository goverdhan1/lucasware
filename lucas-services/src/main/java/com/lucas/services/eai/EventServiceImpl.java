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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.eai.EventDAO;
import com.lucas.dao.eai.EventHandlerDAO;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventEventHandler;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.exception.EaiEventDoesNotExistsException;
import com.lucas.exception.EventAlreadyExistsException;
import com.lucas.services.search.SearchAndSortCriteria;

@Service("eaiEventService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class EventServiceImpl implements EventService {

	private static Logger LOG = LoggerFactory
			.getLogger(EventServiceImpl.class);
	private EventDAO eventDao;
	
	private EventHandlerDAO eventHandlerDao;

	@Inject
	public EventServiceImpl(EventDAO eventDao,
			EventHandlerDAO eventHandlerDao) {
		this.eventDao = eventDao;
		this.eventHandlerDao = eventHandlerDao;
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#createEvent(com.lucas.entity.eai.event.EaiEvent)
	 */
	@Transactional
	@Override
	public void saveEvent(Event event) {
		for (EventEventHandler eeh : event.getEventEventHandlers()) {
	    	 if (eeh.getEvent() == null) {
	    		 eeh.setEvent(event);
	    	 }
	    	 if (eeh.getEventHandler() != null) {
	    		 EventHandler eventHandler = eventHandlerDao.getEventHandlerByName(eeh.getEventHandler().getEventHandlerName());
	    		 eeh.setEventHandler(eventHandler);
	    	 }
	     }
		Event eventExists = eventDao.getEventByName(event.getEventName());
		if (eventExists != null && (eventExists.getEventId() != event.getEventId())) {
			throw new EventAlreadyExistsException(
					"Event Already exists. Existing Event Name"
							+ eventExists.getEventName() + "New Event Name"
							+ event.getEventName());
		}
		eventDao.save(event);
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#deleteEventById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteEventById(Long eventId) {
		if (eventId != null) {
			Event event = eventDao.load(eventId);
			if (event != null) {
				eventDao.delete(event);
			} else {
				LOG.debug(String
						.format("Did not delete any event as no event with eventId %s found.",
								eventId));
			}
		} else {
			LOG.debug("Did not delete any event as null eventId was passed in.");
		}
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#deleteEventByName(java.lang.String)
	 */
	@Transactional
	@Override
	public void deleteEventByName(String eventName) {
		if (eventName != null) {
			Event event = eventDao.load(eventName);
			if (event != null) {
				eventDao.delete(event);
			} else {
				LOG.debug(String
						.format("Did not delete any event as no event with eventName %s found.",
								eventName));
			}

		} else {
			LOG.debug("Did not delete any event as null event name was passed in.");
		}
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#getEventById(int)
	 */
	@Transactional(readOnly = true)
	@Override
	public Event getEventById(Long eaiEventId) {

		return eventDao.getEventById(eaiEventId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.EaiEventService#getEventByName(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Event getEventByName(String eaiEventName) {
		return getHydratedEvent(eventDao.getEventByName(eaiEventName));
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#updateEvent(com.lucas.entity.eai.event.EaiEvent)
	 */
	@Override
	public void updateEvent(Event event) throws JsonParseException,
			JsonMappingException, IOException {
		Event retrievedEvent = eventDao.getEventById(event
				.getEventId());
		if (retrievedEvent == null) {
			throw new EaiEventDoesNotExistsException(String.format(
					"No event exists with eventname %s. Cannot update event!",
					event.getEventId()));
		}

		// TODO Auto-generated method stub Implementation under progress

	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EaiEventService#getEventByNameList(java.util.List)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Event> getEventByNameList(List<String> eventNames)
			throws JsonParseException, JsonMappingException, IOException {

		return eventDao.getEventByNameList(eventNames);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.EventService#getInboundFilePatternAndEventName()
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<String, String> getEventNameAndInboundFilePattern() {

		Map<String, String> patternAndName = new HashMap<String, String>();
		List<Object[]> result = eventHandlerDao.getInboundFilePatternAndEventName();
		
		for(Object[] data : result) {
		    Object[] projection = (Object[]) data;
		    String eventName = (String) projection[0];
		    String inboundFilePattern = (String) projection[1];
		    patternAndName.put(eventName, inboundFilePattern);
		}
		
		return patternAndName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.EventService#startUserLogonEvent(java.lang.Long,
	 * org.springframework.integration.channel.QueueChannel)
	 */
	@Override
	public void startUserLogonEvent(Long userId,
			QueueChannel userOutboundInputChannel) {
		Map<String, String> payloadMap = new HashMap<String, String>();
		payloadMap.put(EaiFileConstants.KEY_USER_ID, Long.toString(userId));
		payloadMap.put(EaiFileConstants.USER_LOGON_KEY,
				EaiFileConstants.USER_LOGON_VALUE);
		Message<Map<String, String>> message = MessageBuilder.withPayload(
				payloadMap).build();
		userOutboundInputChannel.send(message);
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EventService#getEventHandlerById(java.lang.Long)
	 */
	@Override
	public EventHandler getEventHandlerById(Long eventHandlerById) {
		return eventHandlerDao.getEventHandlerById(eventHandlerById);
	}
	
	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EventService#deleteEventHandlerById(java.lang.Long)
	 */
	@Transactional
	@Override
	public boolean deleteEventHandlerById(Long eventHandlerId) {
		boolean flag = Boolean.FALSE;
		if (eventHandlerId != null) {
			EventHandler eventHandler = eventHandlerDao.load(eventHandlerId);
			if (eventHandler != null) {
				eventHandlerDao.delete(eventHandler);
				flag = Boolean.TRUE;
			} else {
				LOG.debug(String
						.format("Did not delete any eventHandler as no eventHandler with eventHandlerId %s found.",
								eventHandler));
			}
		} else {
			LOG.debug("Did not delete any eventHandler as null eventHandlerId was passed in.");
		}
		return flag;
	}

	private Event getHydratedEvent(Event event) {
		if (event != null && !event.getEventEventHandlers().isEmpty()) {
			Set<EventEventHandler> eventEventHandlers = event.getEventEventHandlers();
			event.setEventEventHandlers(eventEventHandlers);
		}
		return event;
	}
	
	/* (non-Javadoc)
	 * @see com.lucas.services.eai.EventService#getEventListBySearchAndSortCriteria(com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	public List<Event> getEventListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		 return this.eventDao.getEventListBySearchAndSortCriteria(searchAndSortCriteria);
	}

	 /* (non-Javadoc)
	 * @see com.lucas.services.eai.EventService#getTotalCountForSearchAndSortCriteria(com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Transactional(readOnly = true)
	    @Override
	    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception {
	        return this.eventDao.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
	    }
}