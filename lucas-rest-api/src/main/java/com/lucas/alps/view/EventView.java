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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EventDetailsView;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventEventHandler;

public class EventView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1117777845656258280L;
	
	private Event event;
	
	public EventView() {
		this.event = new Event();
	}

	public EventView(Event event) {
		this.event = event;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	@JsonView({EventDetailsView.class})
	public Long getEventId() {
		return event.getEventId();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventCategory() {
		return event.getEventCategory();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventDescription() {
		return event.getEventDescription();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventName() {
		return event.getEventName();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventSource() {
		return event.getEventSource();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventSubCategory() {
		return event.getEventSubCategory();
	}
	
	@JsonView({EventDetailsView.class})
	public String getEventType() {
		return event.getEventType();
	}
	
	@JsonView({EventDetailsView.class})
	public List<EventEventHandlerView> getEventEventHandlers() {
		List<EventEventHandlerView> eventEventHandlerList = new ArrayList<EventEventHandlerView>();
        for (EventEventHandler eventEventHandler : event.getEventEventHandlers()) {
        	eventEventHandlerList.add(new EventEventHandlerView(eventEventHandler));
        }
        return eventEventHandlerList;
	}
	
	public void setEventId(Long eventId) {
		event.setEventId(eventId);
	}
	
	public void setEventCategory(String eventCategory) {
		event.setEventCategory(eventCategory);
	}
	
	public void setEventDescription(String eventDescription) {
		event.setEventDescription(eventDescription);
	}
	
	public void setEventName(String eventName) {
		event.setEventName(eventName);
	}
	
	public void setEventSource(String eventSource) {
		event.setEventSource(eventSource);
	}
	
	public void setEventSubCategory(String eventSubCategory) {
		event.setEventSubCategory(eventSubCategory);
	}
	
	public void setEventType(String eventType) {
		event.setEventType(eventType);
	}
	
	public void setEventEventHandlers(List<EventEventHandlerView> eventEventHandlerViews) {
		Set<EventEventHandler> eventEventHandlers = new HashSet<EventEventHandler>();
		for (EventEventHandlerView eventEventHandlerView : eventEventHandlerViews) {
			eventEventHandlers.add(eventEventHandlerView.getEventEventHandler());
		}
		event.setEventEventHandlers(eventEventHandlers);
	}
}
