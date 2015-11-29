/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter  {

	private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                    throws ServletException, IOException {
 
        response.addHeader("Access-Control-Allow-Origin", "*");
        logger.debug("Setting Accesss-Control-Allow-Origin to '*'");
        
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, x-xsrf-token, Authentication-token");
            logger.debug("Setting headers...");
        }
        filterChain.doFilter(request, response);
    }
}