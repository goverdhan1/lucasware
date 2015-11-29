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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.transport.Transport;

/**
 * Entity class for the event handler domain, having details for the message to
 * execute the event in the system.
 */
@Entity
@Table(name = "eai_event_handler", uniqueConstraints = @UniqueConstraint(columnNames = "event_handler_name"))
public class EventHandler implements java.io.Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Event Handler Id
	 */
	private Long eventHandlerId;
	/**
	 * Inbound mapping Id
	 */
	private Mapping eaiMappingByInboundMappingId;
	/**
	 * Outbound mapping Id
	 */
	private Mapping eaiMappingByOutboundMappingId;
	/**
	 * Transport Object
	 */
	private Transport eaiTransport;
	/**
	 * Event handler description
	 */
	private String eventHandlerDescription;
	/**
	 * Event handler name
	 */
	private String eventHandlerName;
	/**
	 * Event handler type
	 */
	private String eventHandlerType;
	/**
	 * Outbound file name
	 */
	private String outboundFilePattern;
	/**
	 * Event usage information
	 */
	private Integer usageInEvent;
	/**
	 * Inbound file pattern to identify the file
	 */
	private String inboundFilePattern;
	/**
	 * Set of event event hander
	 */
	private Set<EventEventHandler> eventEventHandlers = new HashSet<EventEventHandler>();
	
	public EventHandler() {
	}

	public EventHandler(String eventHandlerName, String eventHandlerType) {
		this.eventHandlerName = eventHandlerName;
		this.eventHandlerType = eventHandlerType;
	}

	public EventHandler(Mapping eaiMappingByInboundMappingId,
			Mapping eaiMappingByOutboundMappingId, Transport eaiTransport,
			String eventHandlerDescription, String eventHandlerName,
			String eventHandlerType, String outboundFilePattern,
			Integer usageInEvent, String inboundFilePattern,
			Set<EventEventHandler> eaiEventEventHandlers) {
		this.eaiMappingByInboundMappingId = eaiMappingByInboundMappingId;
		this.eaiMappingByOutboundMappingId = eaiMappingByOutboundMappingId;
		this.eaiTransport = eaiTransport;
		this.eventHandlerDescription = eventHandlerDescription;
		this.eventHandlerName = eventHandlerName;
		this.eventHandlerType = eventHandlerType;
		this.outboundFilePattern = outboundFilePattern;
		this.usageInEvent = usageInEvent;
		this.inboundFilePattern = inboundFilePattern;
		this.eventEventHandlers = eventEventHandlers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_handler_id", unique = true, nullable = false)
	public Long getEventHandlerId() {
		return this.eventHandlerId;
	}

	public void setEventHandlerId(Long eventHandlerId) {
		this.eventHandlerId = eventHandlerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inbound_mapping_id")
	public Mapping getEaiMappingByInboundMappingId() {
		return this.eaiMappingByInboundMappingId;
	}

	public void setEaiMappingByInboundMappingId(
			Mapping eaiMappingByInboundMappingId) {
		this.eaiMappingByInboundMappingId = eaiMappingByInboundMappingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outbound_mapping_id")
	public Mapping getEaiMappingByOutboundMappingId() {
		return this.eaiMappingByOutboundMappingId;
	}

	public void setEaiMappingByOutboundMappingId(
			Mapping eaiMappingByOutboundMappingId) {
		this.eaiMappingByOutboundMappingId = eaiMappingByOutboundMappingId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_transport_id")
	public Transport getEaiTransport() {
		return this.eaiTransport;
	}

	public void setEaiTransport(Transport eaiTransport) {
		this.eaiTransport = eaiTransport;
	}

	@Column(name = "event_handler_description", length = 80)
	public String getEventHandlerDescription() {
		return this.eventHandlerDescription;
	}

	public void setEventHandlerDescription(String eventHandlerDescription) {
		this.eventHandlerDescription = eventHandlerDescription;
	}

	@Column(name = "event_handler_name", unique = true, nullable = false, length = 80)
	public String getEventHandlerName() {
		return this.eventHandlerName;
	}

	public void setEventHandlerName(String eventHandlerName) {
		this.eventHandlerName = eventHandlerName;
	}

	@Column(name = "event_handler_type", nullable = false, length = 40)
	public String getEventHandlerType() {
		return this.eventHandlerType;
	}

	public void setEventHandlerType(String eventHandlerType) {
		this.eventHandlerType = eventHandlerType;
	}

	@Column(name = "outbound_file_pattern", length = 120)
	public String getOutboundFilePattern() {
		return this.outboundFilePattern;
	}

	public void setOutboundFilePattern(String outboundFilePattern) {
		this.outboundFilePattern = outboundFilePattern;
	}

	@Column(name = "usage_in_event")
	public Integer getUsageInEvent() {
		return this.usageInEvent;
	}

	public void setUsageInEvent(Integer usageInEvent) {
		this.usageInEvent = usageInEvent;
	}

	@Column(name = "inbound_file_pattern", length = 120)
	public String getInboundFilePattern() {
		return this.inboundFilePattern;
	}

	public void setInboundFilePattern(String inboundFilePattern) {
		this.inboundFilePattern = inboundFilePattern;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eventHandler", cascade = CascadeType.ALL)
	public Set<EventEventHandler> getEventEventHandlers() {
		return this.eventEventHandlers;
	}

	public void setEventEventHandlers(
			Set<EventEventHandler> eventEventHandlers) {
		this.eventEventHandlers = eventEventHandlers;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EventHandler)) {
			return false;
		}
		EventHandler castOther = (EventHandler) other;
		return new EqualsBuilder()
				.append(eventHandlerId, castOther.eventHandlerId)
				.append(eventHandlerName, castOther.eventHandlerName)
				.append(eventHandlerType, castOther.eventHandlerType)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(eventHandlerId)
				.append(eventHandlerName).append(eventHandlerType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("eventHandlerId", eventHandlerId)
				.append("eventHandlerName", eventHandlerName)
				.append("eventHandlerType", eventHandlerType).toString();
	}

}
