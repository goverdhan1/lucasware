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
package com.lucas.dao.eai;

import java.text.ParseException;
import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.event.Event;
import com.lucas.services.search.SearchAndSortCriteria;

public interface EventDAO extends GenericDAO<Event> {
	/**
	 * This method returns all the information of event requested by event id
	 * 
	 * @param eventId
	 * @return
	 */
	Event getEventById(Long eventId);

	/**
	 * This method returns all the information of event requested by event name
	 * 
	 * @param EventName
	 * @return
	 */
	Event getEventByName(String EventName);

	/**
	 * This method returns all the information of all the events requested by
	 * list of event names
	 * 
	 * @param eventNames
	 * @return
	 */
	List<Event> getEventByNameList(List<String> eventNames);
	
	/**
	 * The method returns the event data for the event based upon the search and sort criteria passed in.
	 *  
	 * @param searchAndSortCriteria
	 * @return a list of event objects
	 */
	List<Event> getEventListBySearchAndSortCriteria (
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

	/**
     * The method returns the total number of records for the 
     * results returned by the queried search and sort criteria
     *
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

}
