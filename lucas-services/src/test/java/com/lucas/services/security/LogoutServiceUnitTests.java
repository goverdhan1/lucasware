package com.lucas.services.security;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


@RunWith(PowerMockRunner.class)
public class LogoutServiceUnitTests {

    private static final Logger log = LoggerFactory.getLogger(LogoutServiceUnitTests.class);
    
    private final String expectedUser = "Bob";
    private final String password = "1234";
    
    private Authentication authentication;
    private SecurityContext securityContext;
        
    @InjectMocks
    private LogoutServiceImpl logoutService;
    
    
    @Before
    public void setUp() {
        log.debug("***Executing test setup");
        
        // We set up a dummy Spring SecurityContext layer
        // to test that the LogoutService clears it correctly
        log.debug("***Creating dummy SecurityContext");
        authentication = new UsernamePasswordAuthenticationToken(expectedUser, password);
        securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    
    @Test
    public void testClearSecurityContext() {              
        log.debug("***Executing test: testClearSecurityContext");
        
        // before testing anything, ensure the mock context has been created for user 'Bob'.
        String actualUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("***Asserting [" + actualUser + "] matches [" + expectedUser + "]");
        assertEquals(actualUser, expectedUser);
              
        // clear the context
        logoutService.clearSecurityContextForLogout();
      
        // SecurityContextHolder.getContext() never returns null, as per the Spring documentation.
        // So in order to test the security context has been cleared, we get the Authentication
        // object from the current context. If the context was properly cleared, the authentication 
        // object will be null... so this is what we will be asserting in this test
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        log.debug("***Asserting [null] Authentication object");
        assertNull(a);
    }
}
