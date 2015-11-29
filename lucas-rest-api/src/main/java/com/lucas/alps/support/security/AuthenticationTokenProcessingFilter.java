/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.lucas.entity.user.User;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private static Logger LOG = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);

	@Inject 
	TokenProvider tokenProvider;
	
	AuthenticationManager authManager;
	SecurityContextProvider securityContextProvider;

    WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
	
	public AuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		this.securityContextProvider = new SecurityContextProvider();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug("Checking headers and parameters for authentication token...");
		
		String token = null;

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		if (httpServletRequest.getHeader("Authentication-token") != null) {
			token = httpServletRequest.getHeader("Authentication-token");
			LOG.debug("Found token '" + token + "' in request headers");
		}
		
		if (token != null) {
			if (tokenProvider.isTokenValid(token)) {
				User user = tokenProvider.getUserFromToken(token);
				LOG.debug("Inside-AuthenticationTokenProcessingFilter.java");
				authenticateUser(httpServletRequest, user);
			}
		} 
		chain.doFilter(request, response);
	}

	private void authenticateUser(HttpServletRequest request, User user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( user.getUserName(), user.getHashedPassword());
		authentication.setDetails(webAuthenticationDetailsSource.buildDetails(request));
		SecurityContext sc = securityContextProvider.getSecurityContext();
		sc.setAuthentication(authManager.authenticate(authentication));
	}
}