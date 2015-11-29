/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.services.util.UniqueKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedController implements AccessDeniedHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);
	private final UniqueKeyService uniqueKeyService;
	private final static String FAILURE = "failure";
	
    @Inject
	public AccessDeniedController(UniqueKeyService uniqueKeyService) {
		this.uniqueKeyService = uniqueKeyService;
	}
    
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException e) throws IOException, ServletException {

		String uniqueKey = uniqueKeyService.generateUniqueId();
        ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        apiResponse.setCode(String.valueOf(HttpServletResponse.SC_FORBIDDEN));
        apiResponse.setMessage(e.getMessage());
        apiResponse.setUniqueKey(uniqueKey);
    	apiResponse.setStatus(FAILURE);
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), apiResponse);
    	
        logger.error(" Unique Key:{}, Exception message:{} 	",uniqueKey, e );
	}
}