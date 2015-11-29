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
import com.lucas.entity.eai.mapping.MappingPath;

/**
 * This class is the data access object interface in the persistence layer providing a way
 * to perform operations on the underlying mapping path table
 * 
 * @author Prafull
 * 
 */
public interface MappingPathDao extends GenericDAO<MappingPath> {

	
	/**
	 * This method returns the list of mapping paths defined for a particular mapping name
	 * @param mappingName
	 * @return
	 */
	List<MappingPath> getMappingPathByMappingName(String mappingName);
}
