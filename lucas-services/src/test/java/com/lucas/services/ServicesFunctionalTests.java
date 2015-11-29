package com.lucas.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.lucas.services.security.SecurityService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

public class ServicesFunctionalTests extends AbstractSpringFunctionalTests {

	@Value("${testEncryptedProperty}")
	private String testEncryptedProperty;
	
	@Inject
	private SecurityService securityService;
    @Test
	public void testAuthentication(){    	
    	boolean boo = securityService.authenticate("bob", "doesn'tmatter");
    	Assert.assertTrue(boo);
    	boo = securityService.authenticate("jack123", "doesn'tmatter");
    	Assert.assertTrue(!boo);
	}
    
    @Test
    public void testEncryption(){
    	Assert.assertTrue(testEncryptedProperty.equals("aSecretAndEncryptedPassword"));
    }

}
