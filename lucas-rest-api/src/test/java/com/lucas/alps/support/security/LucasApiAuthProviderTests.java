package com.lucas.alps.support.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.lucas.entity.user.User;
import com.lucas.services.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class LucasApiAuthProviderTests {
	
	@Mock UserService userServiceMock;
	@Mock User userMock; 
	
	@InjectMocks
	private LucasApiAuthProvider lucasApiAuthProvider;
	
	UsernamePasswordAuthenticationToken inputAuthToken;
	
	private static final String SOME_USER_NAME = "SOME_USER";
	private static final String SOME_PASSWORD_HASH = "SOME_PASSWORD_HASH";
	
	@Before
	public void setup(){
		lucasApiAuthProvider.setUserService(userServiceMock);
		inputAuthToken = new UsernamePasswordAuthenticationToken(SOME_USER_NAME, SOME_PASSWORD_HASH);
	}
	
	@Test(expected=BadCredentialsException.class)
	public void testUserServiceFailsToGetUser() {
		when(userServiceMock.getUser(SOME_USER_NAME)).thenReturn(null);
		lucasApiAuthProvider.retrieveUser(SOME_USER_NAME,inputAuthToken);
	}
	
	@Test
	public void testUserServiceGetsUser(){
		
		when(userMock.getUserName()).thenReturn(SOME_USER_NAME);
		when(userMock.getHashedPassword()).thenReturn(SOME_PASSWORD_HASH);		
		when(userServiceMock.getUser(SOME_USER_NAME)).thenReturn(userMock);
		
		UserDetails user = lucasApiAuthProvider.retrieveUser(SOME_USER_NAME,inputAuthToken);
		Assert.assertNotNull(user);
		Assert.assertEquals(SOME_USER_NAME, user.getUsername());
		Assert.assertEquals(SOME_PASSWORD_HASH, user.getPassword());
		
		verify(userServiceMock).getUser(SOME_USER_NAME);
	}

}
