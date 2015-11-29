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
import com.lucas.exception.LucasRuntimeException;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 7/8/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for provide
 * service specification for Event Handler
 */
public interface EventHandlerService {

    /**
     * getEventHandlerByName() provide the specification for getting the EventHandler information
     *  from db based on the eventHandlerName provide to the method.
     *
     *  @see com.lucas.dao.eai.EventHandlerDAO#getEventHandlerByName(String)
     *  @see com.lucas.dao.eai.MappingDao#getOutboundMappingByEventHandlerId(Long)
     *  @see com.lucas.dao.eai.MappingDao#getInboundMappingByEventHandlerId(Long)
     *
     * @param eventHandlerName is accepted as the formal arguments to this method for fetching data
     *                          from the db. its an filter condition.
     * @param dependency is accepted as second argument for this method when its true all the dependency
     *                   is also fetched and send if false then only eventHandler without dependency is send.
     * @return EventHandler instance with the data.
     */
    public EventHandler getEventHandlerByName(final String eventHandlerName, final Boolean dependency);

    /**
     * saveEventHandler() provide the specification for saving or updating the EventHandler into the
     * database based on the instance send to the method
     * Note: if eventHandlerId is presend then update operation is performed otherwise insert operation
     *
     * @param eventHandler is accepted as the formal argument to this method containing data.
     * @return boolean value containing the operation status if true then update/insert operation is
     *          sucessful otherwise false in case of failure oepration.
     * @throws LucasRuntimeException
     */
    public Boolean saveEventHandler(final EventHandler eventHandler)throws LucasRuntimeException;

}
