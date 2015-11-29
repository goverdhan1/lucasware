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
package com.lucas.eai.util;

import java.io.Serializable;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.entity.eai.mapping.MappingPath;

/**
 * @author Naveen
 * 
 */
public class MessageTransformerComparator implements Comparator<MappingPath>,
		Serializable {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageTransformerComparator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MappingPath mappingPath1, MappingPath mappingPath2) {
		if (mappingPath1 != null && mappingPath1.getMappingPathId() != null
				&& mappingPath2 != null
				&& mappingPath2.getMappingPathId() != null) {
			try {
				if (mappingPath1.getMappingPathId() > mappingPath2
						.getMappingPathId()) {
					return 1;
				} else
					return -1;
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
			}
		}
		return 0;
	}

}
