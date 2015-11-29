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

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Entity class for the event domain, having details for the message to execute
 * the event in the system.
 */
@Entity
@Table(name = "eai_event", uniqueConstraints = @UniqueConstraint(columnNames = "event_name"))
public class Event implements java.io.Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Event Id
	 */
	private Long eventId;
	/**
	 * Event Category
	 */
	private String eventCategory;
	/**
	 * Event description
	 */
	private String eventDescription;
	/**
	 * Event name
	 */
	private String eventName;
	/**
	 * Event Source
	 */
	private String eventSource;
	/**
	 * Event Sub category
	 */
	private String eventSubCategory;
	/**
	 * Event type
	 */
	private String eventType;
	/**
	 * Set of Event event handlers
	 */
	private Set<EventEventHandler> eventEventHandlers = new HashSet<EventEventHandler>();

	public Event() {
	}

	public Event(String eventCategory, String eventDescription,
			String eventName, String eventSource, String eventSubCategory,
			String eventType) {
		this.eventCategory = eventCategory;
		this.eventDescription = eventDescription;
		this.eventName = eventName;
		this.eventSource = eventSource;
		this.eventSubCategory = eventSubCategory;
		this.eventType = eventType;
	}

	public Event(String eventCategory, String eventDescription,
			String eventName, String eventSource, String eventSubCategory,
			String eventType, Set<EventEventHandler> eventEventHandlers) {
		this.eventCategory = eventCategory;
		this.eventDescription = eventDescription;
		this.eventName = eventName;
		this.eventSource = eventSource;
		this.eventSubCategory = eventSubCategory;
		this.eventType = eventType;
		this.eventEventHandlers = eventEventHandlers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_id", unique = true, nullable = false)
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "event_category", nullable = false, length = 256)
	public String getEventCategory() {
		return this.eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	@Column(name = "event_description", nullable = false, length = 256)
	public String getEventDescription() {
		return this.eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	@Column(name = "event_name", unique = true, nullable = false, length = 120)
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "event_source", nullable = false, length = 120)
	public String getEventSource() {
		return this.eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	@Column(name = "event_sub_category", nullable = false, length = 256)
	public String getEventSubCategory() {
		return this.eventSubCategory;
	}

	public void setEventSubCategory(String eventSubCategory) {
		this.eventSubCategory = eventSubCategory;
	}

	@Column(name = "event_type", nullable = false, length = 80)
	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
	public Set<EventEventHandler> getEventEventHandlers() {
		return this.eventEventHandlers;
	}

	public void setEventEventHandlers(Set<EventEventHandler> eventEventHandlers) {
		this.eventEventHandlers = eventEventHandlers;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Event)) {
			return false;
		}
		Event castOther = (Event) other;
		return new EqualsBuilder().append(eventId, castOther.eventId)
				.append(eventName, castOther.eventName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(eventId).append(eventName)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("eventId", eventId).append("eventName", eventName)
				.toString();
	}

}
