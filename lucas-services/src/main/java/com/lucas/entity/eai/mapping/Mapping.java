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
package com.lucas.entity.eai.mapping;


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

import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.message.Message;

/**
 * Entity class for the mapping domain, having details for the mappings
 * to execute for the event in the system.
 * 
 * @author Prafull
 * 
 */
@Entity
@Table(name = "eai_mapping", uniqueConstraints = @UniqueConstraint(columnNames = "mapping_name"))
public class Mapping implements java.io.Serializable {

	/**
	 * The serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The mapping id for the mapping needed to be performed
	 */
	private Long mappingId;
	/**
	 * The destination message id for the mapping
	 */
	private Message destinationMessage;
	/**
	 * The description about the mapping
	 */
	private String mappingDescription;
	/**
	 * The mapping name
	 */
	private String mappingName;
	/**
	 * The source message id for the mapping
	 */
	private Message sourceMessage;
	/**
	 * Number of event handlers mapping is being used
	 */
	private Integer usageInEventsHandlers;
	/**
	 * Mapping path needed to be executed for the event
	 */
	private Set<MappingPath> mappingPaths = new HashSet<MappingPath>();
	/**
	 * The event handlers used in outbound mapping
	 */
	private Set<EventHandler> eaiEventHandlersForOutboundMappingId = new HashSet<EventHandler>();
	/**
	 * The event handlers used in inbound mapping
	 */
	private Set<EventHandler> eaiEventHandlersForInboundMappingId = new HashSet<EventHandler>();
	/**
	 * Mapping definitions needed to be executed for the event
	 */
	private Set<MappingDefinition> eaiMappingDefinitions = new HashSet<MappingDefinition>();

	public Mapping() {
	}

	public Mapping(String mappingDescription, String mappingName) {
		this.mappingDescription = mappingDescription;
		this.mappingName = mappingName;
	}

	public Mapping(Message destinationMessage, String mappingDescription,
			String mappingName, Message sourceMessage,
			Integer usageInEventsHandlers, Set<MappingPath> mappingPaths,
			Set<EventHandler> eaiEventHandlersForOutboundMappingId,
			Set<EventHandler> eaiEventHandlersForInboundMappingId, Set<MappingDefinition> eaiMappingDefinitions) {
		this.destinationMessage = destinationMessage;
		this.mappingDescription = mappingDescription;
		this.mappingName = mappingName;
		this.sourceMessage = sourceMessage;
		this.usageInEventsHandlers = usageInEventsHandlers;
		this.mappingPaths = mappingPaths;
		this.eaiEventHandlersForOutboundMappingId = eaiEventHandlersForOutboundMappingId;
		this.eaiEventHandlersForInboundMappingId = eaiEventHandlersForInboundMappingId;
		this.eaiMappingDefinitions = eaiMappingDefinitions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mapping_id", unique = true, nullable = false)
	public Long getMappingId() {
		return this.mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "destination_message_id")
	public Message getDestinationMessage() {
		return this.destinationMessage;
	}

	public void setDestinationMessage(Message destinationMessage) {
		this.destinationMessage = destinationMessage;
	}

	@Column(name = "mapping_description", nullable = false, length = 80)
	public String getMappingDescription() {
		return this.mappingDescription;
	}

	public void setMappingDescription(String mappingDescription) {
		this.mappingDescription = mappingDescription;
	}

	@Column(name = "mapping_name", unique = true, nullable = false, length = 80)
	public String getMappingName() {
		return this.mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "source_message_id")
	public Message getSourceMessage() {
		return this.sourceMessage;
	}

	public void setSourceMessage(Message sourceMessage) {
		this.sourceMessage = sourceMessage;
	}

	@Column(name = "usage_in_events_handlers")
	public Integer getUsageInEventsHandlers() {
		return this.usageInEventsHandlers;
	}

	public void setUsageInEventsHandlers(Integer usageInEventsHandlers) {
		this.usageInEventsHandlers = usageInEventsHandlers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mapping", cascade = CascadeType.ALL)
	public Set<MappingPath> getMappingPaths() {
		return this.mappingPaths;
	}

	public void setMappingPaths(Set<MappingPath> mappingPaths) {
		this.mappingPaths = mappingPaths;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiMappingByOutboundMappingId")
	public Set<EventHandler> getEaiEventHandlersForOutboundMappingId() {
		return this.eaiEventHandlersForOutboundMappingId;
	}

	public void setEaiEventHandlersForOutboundMappingId(
			Set<EventHandler> eaiEventHandlersForOutboundMappingId) {
		this.eaiEventHandlersForOutboundMappingId = eaiEventHandlersForOutboundMappingId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiMappingByInboundMappingId")
	public Set<EventHandler> getEaiEventHandlersForInboundMappingId() {
		return this.eaiEventHandlersForInboundMappingId;
	}

	public void setEaiEventHandlersForInboundMappingId(
			Set<EventHandler> eaiEventHandlersForInboundMappingId) {
		this.eaiEventHandlersForInboundMappingId = eaiEventHandlersForInboundMappingId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiMapping")
	public Set<MappingDefinition> getEaiMappingDefinitions() {
		return this.eaiMappingDefinitions;
	}

	public void setEaiMappingDefinitions(Set<MappingDefinition> eaiMappingDefinitions) {
		this.eaiMappingDefinitions = eaiMappingDefinitions;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Mapping)) {
			return false;
		}
		Mapping castOther = (Mapping) other;
		return new EqualsBuilder().append(mappingName, castOther.mappingName)
				.append(sourceMessage, castOther.sourceMessage)
				.append(destinationMessage, castOther.destinationMessage)
				.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(mappingName)
				.append(sourceMessage).append(destinationMessage)
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("mappingName", mappingName)
				.append("sourceMessageId", sourceMessage)
				.append("destinationMessageId", destinationMessage)
				.toString();
	}

}
