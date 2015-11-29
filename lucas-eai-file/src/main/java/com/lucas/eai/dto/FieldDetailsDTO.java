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
package com.lucas.eai.dto;

import java.io.Serializable;

import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.eai.transformation.FilterCondition;
import com.lucas.entity.eai.transformation.JoinerCondition;
import com.lucas.entity.eai.transformation.Transformation;

/**
 * @author Naveen
 * 
 */
public class FieldDetailsDTO implements Serializable, Comparable<FieldDetailsDTO> {

	private String messageFieldDescription;
	private Integer messageFieldEnds;
	private Integer messageFieldRepeat;
	private String messageFieldSeparator;
	private int messageFieldSeq;
	private Integer messageFieldStarts;
	private Long messageSegmentId;
	private int messageFieldLength;
	private String messageFieldName;
	private String messageFieldType;
	private String messageFieldValue;
	private Message eaiMessage;
	private Segments eaiSegments;
	

	
	/**
	 * @return the eaiMessage
	 */
	public Message getEaiMessage() {
		return eaiMessage;
	}

	/**
	 * @param eaiMessage the eaiMessage to set
	 */
	public void setEaiMessage(Message eaiMessage) {
		this.eaiMessage = eaiMessage;
	}

	/**
	 * @return the eaiSegments
	 */
	public Segments getEaiSegments() {
		return eaiSegments;
	}

	/**
	 * @param eaiSegments the eaiSegments to set
	 */
	public void setEaiSegments(Segments eaiSegments) {
		this.eaiSegments = eaiSegments;
	}

	/**
	 * @return the messageFieldDescription
	 */
	public String getMessageFieldDescription() {
		return messageFieldDescription;
	}

	/**
	 * @param messageFieldDescription the messageFieldDescription to set
	 */
	public void setMessageFieldDescription(String messageFieldDescription) {
		this.messageFieldDescription = messageFieldDescription;
	}

	/**
	 * @return the messageFieldEnds
	 */
	public Integer getMessageFieldEnds() {
		return messageFieldEnds;
	}

	/**
	 * @param messageFieldEnds the messageFieldEnds to set
	 */
	public void setMessageFieldEnds(Integer messageFieldEnds) {
		this.messageFieldEnds = messageFieldEnds;
	}

	/**
	 * @return the messageFieldRepeat
	 */
	public Integer getMessageFieldRepeat() {
		return messageFieldRepeat;
	}

	/**
	 * @param messageFieldRepeat the messageFieldRepeat to set
	 */
	public void setMessageFieldRepeat(Integer messageFieldRepeat) {
		this.messageFieldRepeat = messageFieldRepeat;
	}

	/**
	 * @return the messageFieldSeparator
	 */
	public String getMessageFieldSeparator() {
		return messageFieldSeparator;
	}

	/**
	 * @param messageFieldSeparator the messageFieldSeparator to set
	 */
	public void setMessageFieldSeparator(String messageFieldSeparator) {
		this.messageFieldSeparator = messageFieldSeparator;
	}

	/**
	 * @return the messageFieldStarts
	 */
	public Integer getMessageFieldStarts() {
		return messageFieldStarts;
	}

	/**
	 * @param messageFieldStarts the messageFieldStarts to set
	 */
	public void setMessageFieldStarts(Integer messageFieldStarts) {
		this.messageFieldStarts = messageFieldStarts;
	}

	/**
	 * @return the messageSegmentId
	 */
	public Long getMessageSegmentId() {
		return messageSegmentId;
	}

	/**
	 * @param messageSegmentId the messageSegmentId to set
	 */
	public void setMessageSegmentId(Long messageSegmentId) {
		this.messageSegmentId = messageSegmentId;
	}

	/**
	 * @return the messageFieldName
	 */
	public String getMessageFieldName() {
		return messageFieldName;
	}

	/**
	 * @param messageFieldName
	 *            the messageFieldName to set
	 */
	public void setMessageFieldName(String messageFieldName) {
		this.messageFieldName = messageFieldName;
	}

	/**
	 * @return the messageFieldType
	 */
	public String getMessageFieldType() {
		return messageFieldType;
	}

	/**
	 * @param messageFieldType
	 *            the messageFieldType to set
	 */
	public void setMessageFieldType(String messageFieldType) {
		this.messageFieldType = messageFieldType;
	}

	

	/*private static final long serialVersionUID = 1L;
	private Long transformationDefinitionId;
	private Transformation eaiTransformation;
	private String transformationExpression;
	private Integer transformationFieldLength;
	private String transformationFieldName;
	private int transformationFieldSeq;
	private String transformationFieldType;
	private Boolean transformationFieldVar;*/
	/*
	 * private Set<FilterCondition> eaiFilterConditions = new
	 * HashSet<FilterCondition>(); private Set<JoinerCondition>
	 * eaiJoinerConditions = new HashSet<JoinerCondition>();
	 */
	//private MessageDefinition messageDefinition;

	

	/**
	 * @return the messageDefinition
	 *//*
	public MessageDefinition getMessageDefinition() {
		return messageDefinition;
	}

	*//**
	 * @param messageDefinition
	 *            the messageDefinition to set
	 *//*
	public void setMessageDefinition(MessageDefinition messageDefinition) {
		this.messageDefinition = messageDefinition;
	}
*/
	/**
	 * @return the messageFieldLength
	 */
	public int getMessageFieldLength() {
		return messageFieldLength;
	}

	/**
	 * @param messageFieldLength the messageFieldLength to set
	 */
	public void setMessageFieldLength(int messageFieldLength) {
		this.messageFieldLength = messageFieldLength;
	}

	/**
	 * @return the messageFieldValue
	 */
	public String getMessageFieldValue() {
		return messageFieldValue;
	}

	/**
	 * @param messageFieldValue the messageFieldValue to set
	 */
	public void setMessageFieldValue(String messageFieldValue) {
		this.messageFieldValue = messageFieldValue;
	}

	/**
	 * @return the messageFieldSeq
	 */
	public int getMessageFieldSeq() {
		return messageFieldSeq;
	}

	/**
	 * @param messageFieldSeq the messageFieldSeq to set
	 */
	public void setMessageFieldSeq(int messageFieldSeq) {
		this.messageFieldSeq = messageFieldSeq;
	}

	@Override
	public int compareTo(FieldDetailsDTO o) {
		return (this.messageFieldSeq - o.messageFieldSeq);
	}


}