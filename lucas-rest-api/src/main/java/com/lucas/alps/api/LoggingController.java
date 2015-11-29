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

import com.lucas.alps.rest.ApiResponse;
import com.lucas.dto.logging.LogMessageDTO;
import com.lucas.exception.Level;
import com.lucas.services.logging.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@RequestMapping(value="/log", method = RequestMethod.POST)
public class LoggingController {

	private static final Logger log = LoggerFactory.getLogger(LoggingController.class);
	private LoggingService logService;
	private final MessageSource messageSource;

	@Inject
	public LoggingController(LoggingService aboutService, MessageSource messageSource) {
		this.logService = aboutService;
		this.messageSource = messageSource;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value="/message")
	public ApiResponse<Object> saveLogMessage(@RequestBody LogMessageDTO logMessage) {
	    // Create ApiResponse and provide Build Information using the AboutService
		ApiResponse<Object> apiResponse = new ApiResponse<>();
		try {
			logService.saveLogMessage(logMessage);
		} catch (IOException e) {
			log.debug("Exception thrown...", e.getMessage());
			apiResponse.setExplicitDismissal(Boolean.FALSE);
			apiResponse.setLevel(Level.ERROR);
			apiResponse.setCode("1097");
			apiResponse.setStatus("failure");
			apiResponse.setData("");
			apiResponse.setMessage(messageSource.getMessage("1097", new Object[] {logMessage}, LocaleContextHolder.getLocale()));
			log.debug(apiResponse.getMessage());
			return apiResponse;
		}

		return apiResponse;
	}
}