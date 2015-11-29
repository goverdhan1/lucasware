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
package com.lucas.entity.eai.event;

// Generated 18 May, 2015 4:12:02 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Entity class for the event event handler domain, having details for the
 * message to execute the event in the system.
 */

@Entity
@Table(name = "eai_event_event_handler")
public class EventEventHandler implements java.io.Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Event Event handler Id
	 */
	private Long eventEventHandlerId;
	/**
	 * Event event handler
	 * 
	 * 
	 */
	private EventHandler eventHandler;
	/**
	 * Event
	 */
	private Event event;
	/**
	 * Execution order
	 */
	private String executionOrder;
	/**
	 * squence
	 */
	private int seq;

	public EventEventHandler() {
	}

	public EventEventHandler(EventHandler eventHandler, int seq) {
		this.eventHandler = eventHandler;
		this.seq = seq;
	}

	public EventEventHandler(EventHandler eventHandler, Event event,
			String executionOrder, int seq) {
		this.eventHandler = eventHandler;
		this.event = event;
		this.executionOrder = executionOrder;
		this.seq = seq;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_event_handler_id", unique = true, nullable = false)
	public Long getEventEventHandlerId() {
		return this.eventEventHandlerId;
	}

	public void setEventEventHandlerId(Long eventEventHandlerId) {
		this.eventEventHandlerId = eventEventHandlerId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_handler_id", nullable = false)
	public EventHandler getEventHandler() {
		return this.eventHandler;
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id")
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Column(name = "execution_order", length = 40)
	public String getExecutionOrder() {
		return this.executionOrder;
	}

	public void setExecutionOrder(String executionOrder) {
		this.executionOrder = executionOrder;
	}

	@Column(name = "seq", nullable = false)
	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((event == null) ? 0 : event.hashCode());
		result = prime * result
				+ ((eventHandler == null) ? 0 : eventHandler.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventEventHandler other = (EventEventHandler) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (eventHandler == null) {
			if (other.eventHandler != null)
				return false;
		} else if (!eventHandler.equals(other.eventHandler))
			return false;
		return true;
	}
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("eventEventHandlerId", eventEventHandlerId).toString();
	}
}
