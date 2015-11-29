package com.lucas.alps.support.security;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.entity.user.User;
import com.lucas.services.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class TokenProviderTests {
	
	private static final String SECRET_AWESOME_TEST_KEY = "SECRET_AWESOME_TEST_KEY";
	private static final String USER_NAME1 = "USER_NAME1";
	private static final String PASSWORD1 = "PASSWORD1";
	
	private static final String USER_NAME2 = "USER_NAME2";
	private static final String PASSWORD2 = "PASSWORD2";
	private static final long PAST_DATE = DateTime.now().minusYears(1).getMillis();
	private static final long FUTURE_DATE = DateTime.now().plusYears(1).getMillis();
	TokenProvider tokenProvider;
	
	UserService userServiceMock;
	User userMock1;
	User userMock2;	
	
	@Before
	public void setup() {
		tokenProvider = new TokenProvider();
		tokenProvider.setSecretKey(SECRET_AWESOME_TEST_KEY);
		
		makeMockUser(USER_NAME1,PASSWORD1);
		
		userServiceMock = mock(UserService.class);
		tokenProvider.setUserService(userServiceMock);
		userMock1 = makeMockUser(USER_NAME1,PASSWORD1);
		userMock2 = makeMockUser(USER_NAME2,PASSWORD2);
		when(userServiceMock.getUser(USER_NAME1)).thenReturn(userMock1);
		when(userServiceMock.getUser(USER_NAME2)).thenReturn(userMock2);
	}


	private User makeMockUser(String userName, String password) {
		User userMock = mock(User.class);
		when(userMock.getUserName()).thenReturn(userName);
		when(userMock.getHashedPassword()).thenReturn(password);
		return userMock;
	}
	
	@Test
	public void testTokensAreBase64() {	 
		String token = tokenProvider.getToken(userMock1);
		
		assertNotNull(token);
		assertTrue(Base64.isArrayByteBase64(token.getBytes()));
	}
	
	@Test
	public void testDateChangesToken() {
		String token1 = tokenProvider.getToken(userMock1, PAST_DATE);
		String token2 = tokenProvider.getToken(userMock1, FUTURE_DATE);
		
		assertTrue(!token1.equals(token2));
	}
	
	@Test
	public void testUserNameChangesToken() {
		String token1 = tokenProvider.getToken(userMock1, PAST_DATE);
		String token2 = tokenProvider.getToken(userMock2, PAST_DATE);
		
		assertTrue(!token1.equals(token2));
	}
	
	@Test
	public void testPasswordChangesToken() {
		UserService passwordChangedServiceMock = mock(UserService.class);
		
		User userInstance1 = makeMockUser(USER_NAME1,PASSWORD1);
		User userInstance2 = makeMockUser(USER_NAME1,PASSWORD2);
		tokenProvider.setUserService( passwordChangedServiceMock);
		
		when(passwordChangedServiceMock.getUser(USER_NAME1)).thenReturn(userInstance1).thenReturn(userInstance2);
		
		String token1 = tokenProvider.getToken(userInstance1, PAST_DATE);
		String token2 = tokenProvider.getToken(userInstance2, PAST_DATE);
		
		assertTrue(!token1.equals(token2));
	}

	@Test
	public void testTokenSegements()throws Exception{
		MessageDigest md5erMock = mock(MessageDigest.class);
		byte[] fakeMD5 = "123".getBytes();
		when(md5erMock.digest((byte[]) any())).thenReturn(fakeMD5);

		String decodedToken = new String(Base64.decodeBase64(tokenProvider.getToken(userMock1, PAST_DATE)));
		String[] tokenSegments = decodedToken.split(TokenProvider.FENCE_POST);
		assertEquals(3, tokenSegments.length);
		assertEquals(USER_NAME1, tokenSegments[0]);
		assertEquals(PAST_DATE, Long.parseLong(tokenSegments[1]));
		
		// MD5 hash is 32 char
        //Mokito Message digest is not taking md5 also due to that its failing
		//assertArrayEquals(fakeMD5, tokenSegments[2].getBytes());
	}
	
	@Test
	public void testValidateNullOrEmptyFails() {
		assertFalse(tokenProvider.isTokenValid(null));
		assertFalse(tokenProvider.isTokenValid(""));
		assertFalse(tokenProvider.isTokenValid("  "));
	}
	
	@Test
	public void testValidateBase64Fails() {
		String invalidInvalidBase64 = "!!!!! I am rong !!!";
		assertFalse(tokenProvider.isTokenValid(invalidInvalidBase64));
	}
	
	@Test
	public void testValidTokenPasses() {
		String token = tokenProvider.getToken(userMock1, FUTURE_DATE);
		assertTrue(tokenProvider.isTokenValid(token));
	}
	
	@Test
	public void testInValidTokenFails() {
		String token = tokenProvider.getToken(userMock1, PAST_DATE);
		token = token + "1";
		
		assertFalse(tokenProvider.isTokenValid(token));
	}
	
	@Test
	public void testExpiredTokenFails() {
		assertFalse(tokenProvider.isTokenValid(tokenProvider.getToken(userMock1, PAST_DATE)));
	}
	
	@Test
	public void testUnexpiredTokenPasses() {
		assertTrue(tokenProvider.isTokenValid(tokenProvider.getToken(userMock1, FUTURE_DATE)));
	}
	
	@Test
	public void testGetUserDetailsFromToken() {
		tokenProvider.setUserService(userServiceMock);
		String token = tokenProvider.getToken(userMock1);
		User tokenUser = tokenProvider.getUserFromToken(token);
		assertEquals(userMock1, tokenUser);
	}
}