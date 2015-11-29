/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.services.security.LogoutService;
import com.lucas.services.util.HttpStatusCodes;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
	
    private static Logger LOG = LoggerFactory.getLogger(LogoutController.class);
	private final String MESSAGE = "Logout Successfull";
	
	@Inject
	public LogoutController(LogoutService logoutService) {
		LOG.debug("***LogoutController - Injecting LogoutService");
	}
	
	@ResponseBody 
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ApiResponse<Object> authenticate(HttpServletRequest request, HttpServletResponse response) {
			    
	    String token = request.getHeader("Authentication-token");
	    
	    // Clear Authentication Token
	    LOG.debug("***LogoutController - Clearing authentication Token  [" + ((token != null) ? token : "null") + "]");
	    response.setHeader("Authentication-token", StringUtils.EMPTY);
	    
	    // Return successful response (status: 200)
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		apiResponse.setMessage(MESSAGE);
		apiResponse.setCode(HttpStatusCodes.SUCCESS_200);
		
		return apiResponse;
	}
}