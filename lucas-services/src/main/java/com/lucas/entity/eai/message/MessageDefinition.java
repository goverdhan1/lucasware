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

// Generated 18 May, 2015 4:12:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.entity.user.User;

/**
 * Entity class for the message definition domain, having details for the
 * message to execute the event in the system.
 */
@Entity
@Table(name = "eai_message_definition", catalog = "lucasdb")
public class MessageDefinition implements java.io.Serializable, Comparable<MessageDefinition> {

	private Long messageFieldId;
	private Message eaiMessage;
	private Segments eaiSegments;
	private String messageFieldDescription;
	private Integer messageFieldEnds;
	private int messageFieldLength;
	private String messageFieldName;
	private Integer messageFieldRepeat;
	private String messageFieldSeparator;
	private int messageFieldSeq;
	private Integer messageFieldStarts;
	private String messageFieldType;
	private Long messageSegmentId;
	private Set<TransformationDefinition> transformationDefinition = new HashSet<TransformationDefinition>();

	public MessageDefinition() {
	}

	public MessageDefinition(Message eaiMessage,
			String messageFieldDescription, int messageFieldLength,
			String messageFieldName, int messageFieldSeq,
			String messageFieldType) {
		this.eaiMessage = eaiMessage;
		this.messageFieldDescription = messageFieldDescription;
		this.messageFieldLength = messageFieldLength;
		this.messageFieldName = messageFieldName;
		this.messageFieldSeq = messageFieldSeq;
		this.messageFieldType = messageFieldType;
	}

	public MessageDefinition(Message eaiMessage, Segments eaiSegments,
			String messageFieldDescription, Integer messageFieldEnds,
			int messageFieldLength, String messageFieldName,
			Integer messageFieldRepeat, String messageFieldSeparator,
			int messageFieldSeq, Integer messageFieldStarts,
			String messageFieldType, Long messageSegmentId) {
		this.eaiMessage = eaiMessage;
		this.eaiSegments = eaiSegments;
		this.messageFieldDescription = messageFieldDescription;
		this.messageFieldEnds = messageFieldEnds;
		this.messageFieldLength = messageFieldLength;
		this.messageFieldName = messageFieldName;
		this.messageFieldRepeat = messageFieldRepeat;
		this.messageFieldSeparator = messageFieldSeparator;
		this.messageFieldSeq = messageFieldSeq;
		this.messageFieldStarts = messageFieldStarts;
		this.messageFieldType = messageFieldType;
		this.messageSegmentId = messageSegmentId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "message_field_id", unique = true, nullable = false)
	public Long getMessageFieldId() {
		return this.messageFieldId;
	}

	public void setMessageFieldId(Long messageFieldId) {
		this.messageFieldId = messageFieldId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id", nullable = false)
	public Message getEaiMessage() {
		return this.eaiMessage;
	}

	public void setEaiMessage(Message eaiMessage) {
		this.eaiMessage = eaiMessage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_segment_id")
	public Segments getEaiSegments() {
		return this.eaiSegments;
	}

	public void setEaiSegments(Segments eaiSegments) {
		this.eaiSegments = eaiSegments;
	}

	@Column(name = "message_field_description", nullable = false, length = 80)
	public String getMessageFieldDescription() {
		return this.messageFieldDescription;
	}

	public void setMessageFieldDescription(String messageFieldDescription) {
		this.messageFieldDescription = messageFieldDescription;
	}

	@Column(name = "message_field_ends")
	public Integer getMessageFieldEnds() {
		return this.messageFieldEnds;
	}

	public void setMessageFieldEnds(Integer messageFieldEnds) {
		this.messageFieldEnds = messageFieldEnds;
	}

	@Column(name = "message_field_length", nullable = false)
	public int getMessageFieldLength() {
		return this.messageFieldLength;
	}

	public void setMessageFieldLength(int messageFieldLength) {
		this.messageFieldLength = messageFieldLength;
	}

	@Column(name = "message_field_name", nullable = false, length = 80)
	public String getMessageFieldName() {
		return this.messageFieldName;
	}

	public void setMessageFieldName(String messageFieldName) {
		this.messageFieldName = messageFieldName;
	}

	@Column(name = "message_field_repeat")
	public Integer getMessageFieldRepeat() {
		return this.messageFieldRepeat;
	}

	public void setMessageFieldRepeat(Integer messageFieldRepeat) {
		this.messageFieldRepeat = messageFieldRepeat;
	}

	@Column(name = "message_field_separator", length = 1)
	public String getMessageFieldSeparator() {
		return this.messageFieldSeparator;
	}

	public void setMessageFieldSeparator(String messageFieldSeparator) {
		this.messageFieldSeparator = messageFieldSeparator;
	}

	@Column(name = "message_field_seq", nullable = false)
	public int getMessageFieldSeq() {
		return this.messageFieldSeq;
	}

	public void setMessageFieldSeq(int messageFieldSeq) {
		this.messageFieldSeq = messageFieldSeq;
	}

	@Column(name = "message_field_starts")
	public Integer getMessageFieldStarts() {
		return this.messageFieldStarts;
	}

	public void setMessageFieldStarts(Integer messageFieldStarts) {
		this.messageFieldStarts = messageFieldStarts;
	}

	@Column(name = "message_field_type", nullable = false, length = 80)
	public String getMessageFieldType() {
		return this.messageFieldType;
	}

	public void setMessageFieldType(String messageFieldType) {
		this.messageFieldType = messageFieldType;
	}

	@Column(name = "message_segment_id")
	public Long getMessageSegmentId() {
		return this.messageSegmentId;
	}

	public void setMessageSegmentId(Long messageSegmentId) {
		this.messageSegmentId = messageSegmentId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MessageDefinition)) {
			return false;
		}
		MessageDefinition castOther = (MessageDefinition) other;
		return new EqualsBuilder()
				.append(messageFieldLength, castOther.messageFieldLength)
				.append(messageFieldName, castOther.messageFieldName)
				.append(messageFieldType, castOther.messageFieldType)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(messageFieldLength)
				.append(messageFieldName).append(messageFieldType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("messageFieldLength", messageFieldLength)
				.append("messageFieldName", messageFieldName)
				.append("messageFieldType", messageFieldType).toString();
	}

	/**
	 * @return the transformationDefinition
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "messageDefinition")
	public Set<TransformationDefinition> getTransformationDefinition() {
		return transformationDefinition;
	}

	/**
	 * @param transformationDefinition
	 *            the transformationDefinition to set
	 */
	public void setTransformationDefinition(
			Set<TransformationDefinition> transformationDefinition) {
		this.transformationDefinition = transformationDefinition;
	}

	@Override
	public int compareTo(MessageDefinition o) {
		
		return (this.messageFieldSeq - o.messageFieldSeq);
	}

}
