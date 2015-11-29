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
package com.lucas.alps.api;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.eai.TransportService;

@Controller
public class TransportController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TransportController.class);
	private final TransportService transportService;
	
	@Inject
	public TransportController(TransportService transportService) {
		this.transportService = transportService;
	}

	/**
	 * getAllTransports() provides the functionality to fetch all available transport names from DB
	 * 
	 * @return apiResponse
	 */
	@RequestMapping(method = RequestMethod.GET, value="/transports/transport")
	public @ResponseBody ApiResponse<List<String>> getAllTransports() {
		LOG.debug("REST request to get all Transport Names");
		final ApiResponse <List <String>> apiResponse = new ApiResponse <List<String>>();
		try {
			apiResponse.setData(transportService.getAllTransportNames());
		} catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
		return apiResponse;
	}
}
