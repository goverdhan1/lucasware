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

import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;

/**
 * This class is the service layer interface class to provide all the business
 * logic needs to be performed on the transformation domain
 * 
 * 
 */

public interface TransformationService {

	/**
	 * The method returns the transformation details based upon the
	 * transformation id passed in.
	 * 
	 * @param transformationId
	 * @return Transformation
	 */
	Transformation getTransformationById(Long transformationId);

	
}
