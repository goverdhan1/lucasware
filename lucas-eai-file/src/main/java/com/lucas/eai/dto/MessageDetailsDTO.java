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
package com.lucas.eai.dto;

import java.io.Serializable;
import java.util.Map;

import com.lucas.eai.message.RecordType;


/**
 * @author Naveen
 *
 */
public class MessageDetailsDTO implements Serializable{
	
	private RecordType recordType;
	
	private Map<String, FieldDetailsDTO> fields;

	/**
	 * @return the recordType
	 */
	public RecordType getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType the recordType to set
	 */
	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the fields
	 */
	public Map<String, FieldDetailsDTO> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(Map<String, FieldDetailsDTO> fields) {
		this.fields = fields;
	}
	
	

}
