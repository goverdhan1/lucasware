/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.entity; 

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lucas.entity.EmailAddress;

public class EmailAddressUnitTests {
		
	private EmailAddress emailAddress;
	private static final String VALID_EMAIL = "vbuddavarapu@tripodtech.net";
	private static final String INVALID_EMAIL = "vbuddavaraputripodtech.net";
	private static final String EMAIL_INVALID_SPECIALCHARACTERS = ".!#$%&‚Äô*+/=?^_`{|}~-@tripodtech.net";
	private static final String EMAIL_INVALID_SPECIALCHARACTER_AT = "@@tripodtech.net";
	
	
	@Before
	public void setup(){
		emailAddress = new EmailAddress();
	}
	
	@Test
	public void testEmailValidOne(){
		this.emailAddress.setEmailAddress(VALID_EMAIL);
		Assert.assertEquals(true,this.emailAddress.validate());
	}
	
	@Test
	public void testEmailNotValid(){
		this.emailAddress.setEmailAddress(INVALID_EMAIL);
		Assert.assertEquals(false,this.emailAddress.validate());
	}
	@Test
	public void testEmailWithoutDomain(){
		this.emailAddress.setEmailAddress(INVALID_EMAIL);
		Assert.assertEquals(false,this.emailAddress.validate());
	}
	
	@Test
	public void testEmailAddressIsNull(){
		Assert.assertEquals(false,this.emailAddress.validate());
	}
	
	@Test
	public void testEmailAddressWithSpecialCharacterAt(){
		this.emailAddress.setEmailAddress(EMAIL_INVALID_SPECIALCHARACTER_AT);
		Assert.assertEquals(false,this.emailAddress.validate());
	}
	
	@Test
	public void testEmailAddressWithSpecialCharacters(){
		this.emailAddress.setEmailAddress(EMAIL_INVALID_SPECIALCHARACTERS);
		Assert.assertEquals(true,this.emailAddress.validate());
	}
	
}
