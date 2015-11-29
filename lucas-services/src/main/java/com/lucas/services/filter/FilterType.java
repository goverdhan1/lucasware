/**
 * Lucas Systems Inc
 * 11279 Perry Highway
 * Wexford
 * PA 15090
 * United States
 *
 *
 * The information in this file contains trade secrets and confidential
 * information which is the property of Lucas Systems Inc.
 *
 * All trademarks, trade names, copyrights and other intellectual property
 * rights created, developed, embodied in or arising in connection with
 * this software shall remain the sole property of Lucas Systems Inc.
 *
 * Copyright (c) Lucas Systems, 2014
 * ALL RIGHTS RESERVED
 * 
 */
package com.lucas.services.filter;

/**
 * @author Ajay Gabriel
 * @Product Lucas.
 * Created By: Ajay Gabriel
 * Created On Date: Feb 2, 2015 3:34:20 PM
 * Enum class to Filter types for filtering from UI.
 */
public enum FilterType {

    // To be removed later when finished porting all widgets to use the new filter types
	NO_FILTER,
    TRUE,
    FALSE,
    // End of to be removed later

    EMPTY,
    NON_EMPTY,
    ENUMERATION,
    ALPHANUMERIC,
    NUMERIC,
    DATE,
    BOOLEAN,
	NONE;
}