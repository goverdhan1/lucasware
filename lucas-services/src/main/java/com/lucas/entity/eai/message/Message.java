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
package com.lucas.entity.eai.message;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Entity class for the message domain, having details for the message to
 * execute the event in the system.
 * 
 * @author Naveen
 * 
 */
@Entity
@Table(name = "eai_message", uniqueConstraints = @UniqueConstraint(columnNames = "message_name"))
public class Message implements java.io.Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Message Id for uniquely identifying the messages
	 */
	private Long messageId;
	/**
	 * Predefined Lucas messages
	 */
	private boolean lucasPredefined;
	/**
	 * Predefined Lucas Service
	 */
	private String lucasPredefinedService;
	/**
	 * Message Description
	 */
	private String messageDescription;
	/**
	 * Message Format
	 */
	private MessageFormat messageFormat;
	/**
	 * Message Name
	 */
	private String messageName;
	/**
	 * Identifies event using the messages
	 */
	private int usageInEvents;
	/**
	 * Message Delimiter
	 */
	private String messageFieldDelimitedCharacter;
	/**
	 * Message Definitions
	 */
	private Set<MessageDefinition> eaiMessageDefinitions = new HashSet<MessageDefinition>(
			);

	public Message() {
	}

	public Message(boolean lucasPredefined, String messageDescription,
			MessageFormat messageFormat, String messageName, int usageInEvents) {
		this.lucasPredefined = lucasPredefined;
		this.messageDescription = messageDescription;
		this.messageFormat = messageFormat;
		this.messageName = messageName;
		this.usageInEvents = usageInEvents;
	}

	public Message(boolean lucasPredefined, String lucasPredefinedService,
			String messageDescription, MessageFormat messageFormat,
			String messageName, int usageInEvents,
			String messageFieldDelimitedCharacter,
			Set<MessageDefinition> eaiMessageDefinitions) {
		this.lucasPredefined = lucasPredefined;
		this.lucasPredefinedService = lucasPredefinedService;
		this.messageDescription = messageDescription;
		this.messageFormat = messageFormat;
		this.messageName = messageName;
		this.usageInEvents = usageInEvents;
		this.messageFieldDelimitedCharacter = messageFieldDelimitedCharacter;
		this.eaiMessageDefinitions = eaiMessageDefinitions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "message_id", unique = true, nullable = false)
	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Column(name = "lucas_predefined", nullable = false)
	public boolean isLucasPredefined() {
		return this.lucasPredefined;
	}

	public void setLucasPredefined(boolean lucasPredefined) {
		this.lucasPredefined = lucasPredefined;
	}

	@Column(name = "lucas_predefined_service", length = 50)
	public String getLucasPredefinedService() {
		return this.lucasPredefinedService;
	}

	public void setLucasPredefinedService(String lucasPredefinedService) {
		this.lucasPredefinedService = lucasPredefinedService;
	}

	@Column(name = "message_description", nullable = false, length = 120)
	public String getMessageDescription() {
		return this.messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "message_format", nullable = false, length = 80)
	public MessageFormat getMessageFormat() {
		return this.messageFormat;
	}

	public void setMessageFormat(MessageFormat messageFormat) {
		this.messageFormat = messageFormat;
	}

	@Column(name = "message_name", unique = true, nullable = false, length = 80)
	public String getMessageName() {
		return this.messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	@Column(name = "usage_in_events", nullable = false)
	public int getUsageInEvents() {
		return this.usageInEvents;
	}

	public void setUsageInEvents(int usageInEvents) {
		this.usageInEvents = usageInEvents;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "message_field_delimited_character", length = 1)
	public String getMessageFieldDelimitedCharacter() {
		return this.messageFieldDelimitedCharacter;
	}

	public void setMessageFieldDelimitedCharacter(
			String messageFieldDelimitedCharacter) {
		this.messageFieldDelimitedCharacter = messageFieldDelimitedCharacter;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiMessage")
	public Set<MessageDefinition> getEaiMessageDefinitions() {
		return this.eaiMessageDefinitions;
	}

	public void setEaiMessageDefinitions(
			Set<MessageDefinition> eaiMessageDefinitions) {
		this.eaiMessageDefinitions = eaiMessageDefinitions;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Message)) {
			return false;
		}
		Message castOther = (Message) other;
		return new EqualsBuilder().append(messageId, castOther.messageId)
				.append(messageName, castOther.messageName)
				.append(lucasPredefined, castOther.lucasPredefined)
				.append(messageDescription, castOther.messageDescription)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(messageId).append(messageName)
				.append(lucasPredefined).append(messageDescription)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("messageId", messageId)
				.append("messageName", messageName)
				.append("lucasPredefined", lucasPredefined)
				.append("messageDescription", messageDescription).toString();
	}
}
