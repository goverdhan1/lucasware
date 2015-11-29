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

import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.event.EventHandler;

public interface EventHandlerDAO extends GenericDAO<EventHandler> {
	/**
	 * This method returns all the information of event name and the associated
	 * file inbound pattern if any
	 * 
	 * @return
	 */
	List<Object[]> getInboundFilePatternAndEventName();

	/**
	 * This method returns the event hander instance according to the id queried
	 * for
	 * 
	 * @param eventHandlerById
	 * @return
	 */
	EventHandler getEventHandlerById(Long eventHandlerById);
	
	/**
	 * This method returns the event hander instance according to the name queried
	 * for
	 * 
	 * @param eventHandlerName
	 * @return
	 */
	EventHandler getEventHandlerByName(String eventHandlerName);

}
