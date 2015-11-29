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
package com.lucas.services.eai;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * Service class for message entity object
 * 
 * @author Prafull
 * 
 */
public interface MessageService {

	/**
	 * This Service method returns all the information related to the requested
	 * message Id
	 * 
	 * @param messageid
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	Message getMessageById(long messageid);
	
	   /**
     * This Service method returns all the information related to the requested
     * message Id
     * 
     * @param messageid
     * @return
     */
    Message getHydratedMessage(long messageid);

	/**
	 * This Service method returns all the information related to the requested
	 * message name
	 * 
	 * @param messageName
	 * @return
	 * @throws JsonParseException
	 */
	Message getMessageByName(String messageName) throws JsonParseException;

	/**
	 * This Service method returns all the information related to the message
	 * names list
	 * 
	 * @param messageNames
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	List<Message> getMessageByNames(List<String> messageNames)
			throws JsonParseException, JsonMappingException, IOException;;

	/**
	 * This service method returns all message definition information identified
	 * by transformation field id
	 * 
	 * @param transformationDefinitionId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	MessageDefinition getMessageDefinitionByTransformationDefinitionId(
			Long transformationDefinitionId);
	
    /**
     * getMessageListBySearchAndSortCriteria()
     *
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
	public List<Message> getMessageListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws ParseException,Exception;

	/**
     * Service call to fetch the total records from UserDAO given search and sort criteria.
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public Long getTotalCountForMessageSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)throws ParseException ,Exception;


	/**
	 * 
	 * @param searchAndSortCriteria
	 * @return
	 * @throws ParseException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	List<Segments> getSegmentListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			IllegalArgumentException, Exception;

	/**
	 * 
	 * @param searchAndSortCriteria
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	Long getTotalCountForSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			Exception;
	/**
     * This Service method returns all the information related to the requested
     * message name
     * 
     * @param messageName
     * @throws ParseException
     * @return
     */
    Message getHydratedMessageByName(String messageName);

	/**
	 * @param segment
	 * @return
	 * @throws Exception
	 */
	boolean createSegment(Segments segment) throws Exception;

	/**
	 * @param segmentName
	 * @return
	 */
	Segments getSegment(String segmentName);

	/**
	 * @param segmentName
	 * @param dependancy
	 * @return
	 */
	Segments getSegmentAndSegmentDefinitions(String segmentName,
			boolean dependancy);
	
	/**
	 * @param segmentId
	 * @return
	 */
	boolean deleteSegment(Long segmentId);
	
	/**
	 * This method returns all the Message names from the DB.
	 * 
	 * @return List<String> instance
	 * @throws ParseException
	 */
	public List<String> getAllMessageNames() throws ParseException;
}
