/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.services.util.UniqueKeyService;

/**
 * 
 * @author Anand Hoysala
 *
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private UniqueKeyService uniqueKeyService;
	private static final String FAILURE = "failure";
	private static final String LOGIN_REQUEST_MESSAGE = "Please log in";
	
	@Inject
	public CustomAuthenticationEntryPoint(UniqueKeyService uniqueKeyService) {
		this.uniqueKeyService = uniqueKeyService;
	}
	
	@Override
	public void commence(  HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
	
		String uniqueKey = uniqueKeyService.generateUniqueId();
        ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        apiResponse.setCode(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        apiResponse.setMessage(LOGIN_REQUEST_MESSAGE);
        apiResponse.setUniqueKey(uniqueKey);
    	apiResponse.setStatus(FAILURE);
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), apiResponse); 
	}
}
