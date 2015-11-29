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
import java.util.List;
import java.util.Map;

import org.springframework.integration.channel.QueueChannel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * @author Prafull
 *
 */
public interface EventService {

	/**
	 * This Service method creates the Eai Event
	 */
	void saveEvent(Event eaiEvent);

	/**
	 * This Service method deletes the EaiEvent by using Event Id
	 */
	void deleteEventById(Long eventId);

	
	/**
	 * 
	 * This Service method deletes the Eai Event by using Event Name
	 */
	void deleteEventByName(String eventName);

	/**
	 * This Service method returns all the information related to the requested event id
	 * @param numberOne
	 * @return
	 * 
	 */
	Event getEventById(Long numberOne) ;

	/**
	 * This Service method returns all the information related to the requested
	 * event name
	 * 
	 * @param eaiEventName
	 * @return
	 */
	Event getEventByName(String eaiEventName);

	/**
	 * This Service method updates the event requested by event id
	 */
	void updateEvent(Event event) throws JsonParseException,
			JsonMappingException, IOException;

	/**
	 * This service method return all the info related to list of events
	 * identified by passing event names to the method
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	List<Event> getEventByNameList(List<String> eventNames)
			throws JsonParseException, JsonMappingException, IOException;
	
	/**
	 * The method returns the map of inbound file pattern and the event name
	 * @return
	 */
	Map<String, String> getEventNameAndInboundFilePattern();
	
	/**
	 * The method starts the user logon event in the system
	 * @param userId
	 * @param userOutboundInputChannel
	 */
	void startUserLogonEvent(Long userId, QueueChannel userOutboundInputChannel);

	/**
	 * The method returns the event handler object searched by id
	 * 
	 * @param eventHandlerById
	 * @return
	 */
	EventHandler getEventHandlerById(Long eventHandlerById);
	
	/**
	 * This Service method deletes the Event Handler by using Event Handler Id
	 * 
	 * @param eventHandlerId
	 */
	boolean deleteEventHandlerById(Long eventHandlerId);
	
	/**
	 * The method returns the event data for the event based upon the search and sort criteria passed in.
	 *  
	 * @param searchAndSortCriteria
	 * @return a list of event objects
	 * @throws ParseException 
	 */
	List<Event> getEventListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;
	
	/**
     * Service call to fetch the total records from eventDAO given search and sort criteria.
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)throws ParseException ,Exception;
}