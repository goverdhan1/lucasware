package com.lucas.alps.support.security;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.lucas.entity.user.User;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTokenProcessingFilterTests {
	
	private static final String VALID_TOKEN = "VALID_TOKEN";
	private static final String INVALID_TOKEN = "INVALID_TOKEN";
	
	@Mock HttpServletRequest requestMock;
	@Mock HttpServletResponse responseMock;
	@Mock FilterChain filterChainMock;
	@Mock ServletOutputStream servletOutputStreamMock;
	@Mock AuthenticationManager authenticationManagerMock;
	@Mock TokenProvider tokenProviderMock;
	@Mock SecurityContextProvider securityContextProviderMock;
	@Mock UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	
	@Mock User user;
	@Mock WebAuthenticationDetailsSource authenticationDetailsSource;
	@Mock WebAuthenticationDetails webAuthDetails;
	@Mock SecurityContext securityContext;
	
	@InjectMocks
	AuthenticationTokenProcessingFilter filter;
	
	@Test
	public void testShouldNotAuthenticateWhenNoTokenIsSupplied() throws IOException, ServletException {
		when(requestMock.getHeader("Authentication-token")).thenReturn(null);
		when(requestMock.getParameter("token")).thenReturn(null);
		
		when(authenticationManagerMock.authenticate(any(Authentication.class))).thenReturn(usernamePasswordAuthenticationToken);
		
		filter.securityContextProvider = securityContextProviderMock; 
		filter.doFilter(requestMock, responseMock, filterChainMock);
		
		//Should just call doFilter and that is it:
		verify(filterChainMock, times(1)).doFilter(requestMock, responseMock);
		
		//Should check for the header
		verify(requestMock, times(1)).getHeader(any(String.class));
		
		//No more interactions with the request or response:
		verifyNoMoreInteractions(requestMock);
		verifyNoMoreInteractions(responseMock);
		verifyNoMoreInteractions(filterChainMock);
		verifyZeroInteractions(securityContextProviderMock);
	}
	
	
	@Test
	public void testInvalidAuthWithHeaders() throws IOException, ServletException {
		testInvalidToken(INVALID_TOKEN);
	}
	
	@Test
	public void testValidAuthWithHeaders() throws IOException, ServletException {		
		testValidToken(VALID_TOKEN);
	}
	
	
	@Test
	public void testInvalidAuthWithQueryString() throws IOException, ServletException {
		testInvalidToken(INVALID_TOKEN);
	}
	
	@Test
	public void testValidAuthWithQueryString() throws IOException, ServletException {
		testValidToken(VALID_TOKEN);
	}
	

	private void testValidToken(String headerToken ) throws IOException, ServletException {
		when(requestMock.getHeader("Authentication-token")).thenReturn(headerToken);

		
		when(tokenProviderMock.isTokenValid(VALID_TOKEN)).thenReturn(true);
		when(tokenProviderMock.getUserFromToken(VALID_TOKEN)).thenReturn(user);
		when(authenticationDetailsSource.buildDetails(requestMock)).thenReturn(webAuthDetails);
		when(securityContextProviderMock.getSecurityContext()).thenReturn(securityContext);
		
		AuthenticationTokenProcessingFilter filter = new AuthenticationTokenProcessingFilter(authenticationManagerMock);
		filter.tokenProvider = tokenProviderMock;
		filter.webAuthenticationDetailsSource = authenticationDetailsSource;
		filter.securityContextProvider = securityContextProviderMock;
		
		filter.doFilter(requestMock, responseMock, filterChainMock);
		
		verify(tokenProviderMock).getUserFromToken(VALID_TOKEN);
		verify(authenticationDetailsSource).buildDetails(requestMock);
		verify(securityContext).setAuthentication((Authentication) anyObject());
		
		//Should just call doFilter and that is it:
		verify(filterChainMock, times(1)).doFilter(requestMock, responseMock);
	}
	

	private void testInvalidToken(String headerToken ) throws IOException, ServletException {
		when(requestMock.getHeader("Authentication-token")).thenReturn(headerToken);
		when(tokenProviderMock.isTokenValid(headerToken)).thenReturn(false);

		
		filter.tokenProvider = tokenProviderMock;
		filter.doFilter(requestMock, responseMock, filterChainMock);
		
		verify(tokenProviderMock).isTokenValid(INVALID_TOKEN);
		verifyNoMoreInteractions(tokenProviderMock);
		verifyNoMoreInteractions(authenticationManagerMock);
		
		//Should just call doFilter and that is it:
		verify(filterChainMock, Mockito.times(1)).doFilter(requestMock, responseMock);
	}
}