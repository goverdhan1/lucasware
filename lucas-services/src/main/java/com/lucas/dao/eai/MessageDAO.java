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

import java.text.ParseException;
import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.message.Message;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * DAO class for message entity object
 * 
 * @author Naveen
 * 
 */
public interface MessageDAO extends GenericDAO<Message> {

	/**
	 * This method returns all the information related to the requested message
	 * Id
	 * 
	 * @param l
	 * @return
	 */
	Message getMessageById(Long id);

	/**
	 * This method returns all the information related to the requested message
	 * name
	 * 
	 * @param messageName
	 * @return
	 */
	Message getMessageByName(String messageName);

	/**
	 * This method returns all the information related to the message names list
	 * 
	 * @param messageNames
	 * @return
	 */
	List<Message> getMessageByNames(List<String> messageNames);

    /**
     * getMessageListBySearchAndSortCriteria() provide the specification for fetching the
     * list of the Message form database based on the SearchAndSortCriteria provided from Grid
     *
     * @param searchAndSortCriteria
     * @return
     */
    public List<Message> getMessageListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException ;
    
    /**
     * This method returns all available message names
     * 
     * @return List<String>
     * @throws ParseException
     */
    List<String> getAllMessageNames() throws ParseException;
}
