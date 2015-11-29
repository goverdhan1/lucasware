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

import com.lucas.entity.eai.transport.Transport;

/**
 * This class is the service layer interface class to provide all the business
 * logic needs to be performed on the transport domain
 * 
 * 
 */
public interface TransportService {

	/**
	 * The method returns the transport details based upon the transport id
	 * passed in.
	 * 
	 * @param transportId
	 * @return Transport
	 */
	Transport getTransportById(Long transportId);

	/**
	 * The method returns the transport details based upon the transport name
	 * passed in.
	 * 
	 * @param transportName
	 * @return Transport
	 */
	Transport getTransportByName(String transportName);
	
	/**
	 * This method returns all the Transport names from the db.
	 * 
	 * @return List<String> instance
	 * @throws ParseException
	 */
	public List<String> getAllTransportNames() throws ParseException;

}
