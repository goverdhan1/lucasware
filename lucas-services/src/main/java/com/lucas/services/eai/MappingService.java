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

import java.text.ParseException;
import java.util.List;

import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * This class is the service layer interface class to provide all the business
 * logic needs to be performed for mapping
 * 
 * 
 */

/**
 * @author Naveen
 *
 */
public interface MappingService {
	/**
	 * The method returns the mapping details based upon the mapping id
	 * passed in.
	 * 
	 * @param mappingId
	 * @return Mapping
	 */
	Mapping getMappingById(Long mappingId);

	/**
	 * The method returns the mapping details based upon the mapping name
	 * passed in.
	 * 
	 * @param mappingName
	 * @return Mapping
	 */
	Mapping getMappingByName(String mappingName);
	
	/**
	 * This method returns all the mapping path details for a mapping name
	 * 
	 * @param mappingName
	 * @return
	 */
	List<MappingPath> getMappingPathByMappingName(String mappingName);
	
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
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	List<Mapping> getMappingListBySearchAndSortCriteria(
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
     * getMappingsNames() provide the functionality for getting all
     * the mapping from the db tables without any filters using MappingDao.getMappingsNames().
     * @return the list of mappings names from db.
     */
    public List<String> getMappingsNames();

    /**
     * getMappingByName() provide the functionality for getting
     * the mapping from the db tables based on mapping name
     *
     * @param mappingName is the argument to this method to map for selection.
     * @param dependency is the flag to load the other dependency of the
     *                   Mapping instance from db
     * @return the mappings instance from db.
     */
    public Mapping getMappingByName(final String mappingName,final boolean dependency);

	

	/**
	 * This Service method creates the Mapping
	 * 
	 * @param mapping
	 */
	void saveMapping(Mapping mapping);
	
	/**
	 * @param mappingId
	 * @return
	 */
	boolean deleteMapping (Long mappingId);
}
