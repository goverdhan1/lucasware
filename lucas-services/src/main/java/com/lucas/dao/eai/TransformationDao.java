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

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;

/**
 * This class is the data access object in the persistence layer providing a way
 * to perform operations on the underlying transformation table
 * 
 */
public interface TransformationDao extends GenericDAO<Transformation> {

	/**
	 * The method returns the transformation details based upon the
	 * transformation id passed in.
	 * 
	 * @param transformationId
	 * @return TransFormation
	 */
	Transformation getTransformationById(Long transformationId);

}
