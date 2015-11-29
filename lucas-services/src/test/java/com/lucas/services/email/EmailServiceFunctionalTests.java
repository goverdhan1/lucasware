/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.email;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.lucas.entity.EmailAddress;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

/**
 * This test actually sends out an email
 * We have a 500 emails a month limit on our mail server (socketlabs).
 * @author PankajTandon
 *
 */
 @Ignore
public class EmailServiceFunctionalTests extends AbstractSpringFunctionalTests {
	
	@Inject
	private EmailService emailService;
	
	@Value("${test.email.id}")
	private String testEmailId;

	@Value("${from.email.address}")
	private String fromAddress;	
	
	List<EmailAddress> toEmailAddressList;
	List<EmailAddress> ccEmailAddressList;
	List<EmailAddress> bccEmailAddressList;
	EmailAddress fromEmailAddress;
	private static Logger logger = LoggerFactory.getLogger(EmailServiceFunctionalTests.class);
	
	@Before
	public void setup(){
		 toEmailAddressList= new ArrayList<EmailAddress>();
		 ccEmailAddressList= new ArrayList<EmailAddress>();
		 bccEmailAddressList= new ArrayList<EmailAddress>();
		 EmailAddress emailAddressTo = new EmailAddress(testEmailId);
		 toEmailAddressList.add(emailAddressTo);
		 EmailAddress emailAddressCC = new EmailAddress(testEmailId);
		 ccEmailAddressList.add(emailAddressCC);
		 EmailAddress emailAddressBCC = new EmailAddress(testEmailId);
		 bccEmailAddressList.add(emailAddressBCC);
		 fromEmailAddress = new EmailAddress(fromAddress);
	}

	@Ignore
	@Test
	public void testSendEmailWithFromEmailAddressWithNoErrors() {
		boolean allgood = true;
		try {
			emailService.sendEmail(this.fromEmailAddress, toEmailAddressList, "subject", EmailService.PLAIN, "message", ccEmailAddressList, bccEmailAddressList);
		} catch (Exception e) { 
			logger.info("Error sending email", e);
			allgood = false; 
		}
		
		Assert.assertTrue(allgood);
	}	
	
	@Ignore
	@Test
	public void testSendEmailWithoutFromEmailAddressWithNoErrors() {
		boolean allgood = true;
		try {
			emailService.sendEmail(toEmailAddressList, "subject", EmailService.PLAIN, "message", ccEmailAddressList, bccEmailAddressList);
		} catch (Exception e) { 
			logger.info("Error sending email", e);
			allgood = false; 
		}
		
		Assert.assertTrue(allgood);
	}
	
}