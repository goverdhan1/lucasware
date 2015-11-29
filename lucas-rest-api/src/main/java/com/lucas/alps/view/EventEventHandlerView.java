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
package com.lucas.alps.view;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EventDetailsView;
import com.lucas.entity.eai.event.EventEventHandler;

public class EventEventHandlerView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326718566326786421L;
	
	private EventEventHandler eventEventHandler;
	
	public EventEventHandlerView() {
		this.eventEventHandler = new EventEventHandler();
	}

	public EventEventHandlerView(EventEventHandler eventEventHandler) {
		this.eventEventHandler = eventEventHandler;
	}

	/**
	 * @return the eventEventHandler
	 */
	public EventEventHandler getEventEventHandler() {
		return eventEventHandler;
	}

	/**
	 * @param eventEventHandler the eventEventHandler to set
	 */
	public void setEventEventHandler(EventEventHandler eventEventHandler) {
		this.eventEventHandler = eventEventHandler;
	}

	@JsonView({EventDetailsView.class})
	public Long getEventEventHandlerId() {
		return eventEventHandler.getEventEventHandlerId();
	}
	
	@JsonView({EventDetailsView.class})
	public EventHandlerView getEventHandler() {
		return new EventHandlerView(eventEventHandler.getEventHandler());
	}
	
	@JsonView({EventDetailsView.class})
	public String getExecutionOrder() {
		return eventEventHandler.getExecutionOrder();
	}
	
	@JsonView({EventDetailsView.class})
	public int getSeq() {
		return eventEventHandler.getSeq();
	}
	
	public void setEventEventHandlerId(Long eventEventHandlerId) {
		eventEventHandler.setEventEventHandlerId(eventEventHandlerId);
	}
	
	public void setEventHandler(EventHandlerView eventHandlerView) {
		eventEventHandler.setEventHandler(eventHandlerView.getEventHandler());
	}
	
	public void setExecutionOrder(String executionOrder) {
		eventEventHandler.setExecutionOrder(executionOrder);
	}
	
	public void setSeq(int seq) {
		eventEventHandler.setSeq(seq);
	}
}
