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
package com.lucas.eai.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.util.MessageTransformerComparator;
import com.lucas.entity.eai.mapping.MappingPath;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * This class represents a data transfer object for transferring the generic
 * message response to the message transformer .
 * 
 * @author Naveen
 * 
 */
public class GenericMessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PriorityQueue<MappingPath> transformationPaths = new PriorityQueue<MappingPath>(10,new MessageTransformerComparator());
	
	//private PriorityQueue<MappingPath> transformationPaths = new PriorityQueue<MappingPath>(10);

	Long sourceMessageId;

	Long destinationMessageId;
	
	Long eventHandlerId;

	List<MessageDetailsDTO> messageDetails = new ArrayList<MessageDetailsDTO>();

	public MappingPath getTransformationPath() {
		return this.transformationPaths.poll();
	}

	public void addTransformationPaths(MappingPath transformationPath) {
		//Collections.sort((List<MappingPath>) this.transformationPaths, new MessageTransformerComparator());
		this.transformationPaths.offer(transformationPath);
	}

	public void addAllTransformationPaths(List<MappingPath> mappingPaths) {
		
		this.transformationPaths.addAll(mappingPaths);
	}

	/**
	 * @return the messageDetails
	 */
	public List<MessageDetailsDTO> getMessageDetails() {
		return messageDetails;
	}

	/**
	 * @param messageDetails
	 *            the messageDetails to set
	 */
	public void setMessageDetails(List<MessageDetailsDTO> messageDetailsVal) {
		if(this.messageDetails == null){
			this.messageDetails = new ArrayList<MessageDetailsDTO>();
		}
		this.messageDetails.addAll(messageDetailsVal);
	}

	/*
	 * public void addMessageDefinition(MessageDetailsDTO messageDetails) {
	 * this.transformationPaths.offer(messageDetails); }
	 * 
	 * public void removeMessageDefinition(MessageDetailsDTO messageDetails) {
	 * this.transformationPaths.poll(); }
	 */
	/**
	 * @return the sourceMessageId
	 */
	public Long getSourceMessageId() {
		return sourceMessageId;
	}

	/**
	 * @param sourceMessageId
	 *            the sourceMessageId to set
	 */
	public void setSourceMessageId(Long sourceMessageId) {
		this.sourceMessageId = sourceMessageId;
	}

	/**
	 * @return the destinationMessageId
	 */
	public Long getDestinationMessageId() {
		return destinationMessageId;
	}

	/**
	 * @param destinationMessageId
	 *            the destinationMessageId to set
	 */
	public void setDestinationMessageId(Long destinationMessageId) {
		this.destinationMessageId = destinationMessageId;
	}

	public MappingPath getNextTransformation() {
		return transformationPaths.peek();
	}

	
	/**The method returns the event handler id for this event 
	 * 
	 * @return
	 */
	
	public Long getEventHandlerId() {
		return eventHandlerId;
	}

	public void setEventHandlerId(Long eventHandlerId) {
		this.eventHandlerId = eventHandlerId;
	}

	public PriorityQueue<MappingPath> getTransformationPaths() {
		return transformationPaths;
	}

	public void setTransformationPaths(
			PriorityQueue<MappingPath> transformationPaths) {
		this.transformationPaths = transformationPaths;
	}
	
	
	
}
