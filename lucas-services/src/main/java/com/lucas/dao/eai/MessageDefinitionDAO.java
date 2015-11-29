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
package com.lucas.dao.eai;

import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.message.MessageDefinition;

/**
 * DAO class for message definition entity object
 * 
 * @author Naveen
 * 
 */
public interface MessageDefinitionDAO extends GenericDAO<MessageDefinition> {

	/*	*//**
	 * This method returns all the message definition information requested
	 * by message field id
	 * 
	 * @param messageFieldId
	 * @return
	 */
	/*
	 * MessageDefinition getMessageDefinitionByMessageFieldId(Long
	 * messageFieldId);
	 *//**
	 * This method returns all the message definition information requested
	 * by message field name
	 * 
	 * @param messageFieldName
	 * @return
	 */
	/*
	 * MessageDefinition getMessageDefinitionByMessageFieldName( String
	 * messageFieldName);
	 */

	/**
	 * This method returns all the message definitions related to the requested
	 * transformation field Id
	 * 
	 * @param transformationFieldId
	 * @return
	 */
	MessageDefinition getMessageDefinitionForTransformationDefinition(
			Long transformationDefinitionId);
	
	/**
	 * This method returns all the message definitions for the given messageId
	 * 
	 * @param messageId
	 * @return
	 */
	List<MessageDefinition> getMessageDefinitionsByMessageId(Long messageId);

}
