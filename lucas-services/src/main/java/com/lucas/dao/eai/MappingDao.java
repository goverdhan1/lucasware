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
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * This class is the data access object in the persistence layer providing a way
 * to perform operations on the underlying mapping table
 * 
 * @author Prafull
 * 
 */
public interface MappingDao extends GenericDAO<Mapping> {

	/**
	 * The method returns the mapping details based upon the mapping id passed
	 * in.
	 * 
	 * @param mappingId
	 * @return Mapping
	 */
	Mapping getMappingById(Long mappingId);

	/**
	 * The method returns the mapping details based upon the mapping name passed
	 * in.
	 * 
	 * @param mappingName
	 * @return Mapping
	 */
	Mapping getMappingByName(String mappingName);

	/**
	 * This method returns the inbound mapping object based upon the event handler id it
	 * is associated with
	 * 
	 * @param eventHandlerId
	 * @return
	 */
	Mapping getInboundMappingByEventHandlerId(Long eventHandlerId);
	
	/**
	 * This method returns the outbound mapping object based upon the event handler id it
	 * is associated with
	 * 
	 * @param eventHandlerId
	 * @return
	 */
	Mapping getOutboundMappingByEventHandlerId(Long eventHandlerId);

	/**
	 * @param searchAndSortCriteria
	 * @return
	 * @throws ParseException
	 */
	List<Mapping> getMappingListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

	/**
	 * @param searchAndSortCriteria
	 * @return
	 * @throws ParseException
	 */
	Long getTotalCountForMappingSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;
    /**
     * getMappingsNames() provide the functionality for getting all
     * the mapping from the db tables without any filters.
     * @return the list of mappings names from db.
     */
    public List<String> getMappingsNames();


}
