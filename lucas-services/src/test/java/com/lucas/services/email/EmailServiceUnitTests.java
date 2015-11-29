/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.email;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.ReflectionUtils;

import com.lucas.entity.EmailAddress;
import com.lucas.exception.LucasRuntimeException;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceUnitTests {
	
	
	List<EmailAddress> toEmailAddressList;
	List<EmailAddress> ccEmailAddressList;
	List<EmailAddress> bccEmailAddressList;
	EmailAddress fromEmailAddress;
	
	private static final String TO_EMAIL_ADDRESS = "tandon@lucasware.com";
	private static final String CC_EMAIL_ADDRESS = "tandon@lucasware.com";
	private static final String BCC_EMAIL_ADDRESS = "tandon@lucasware.com";
	private static final String FROM_EMAIL_ADDRESS = "tandon@lucasware.com";
	
	private String SUBJECT = "subject";
	private String MESSAGE = "message";
	
	@Mock
	private MailSender mailSender;
	
	@Mock
	private SimpleMailMessage mailMessage;
	
	@InjectMocks
	private EmailServiceImpl emailService;
	
	@Mock
	private Appender mockAppender;	
	
	private ArgumentCaptor<LoggingEvent> arguments;
	private Field suppressEmailField;
	
	@Before
	public void setup(){
		suppressEmailField = ReflectionUtils.findField(EmailServiceImpl.class, "suppressEmail");
		ReflectionUtils.makeAccessible(suppressEmailField);
		
		toEmailAddressList= new ArrayList<EmailAddress>();
		ccEmailAddressList= new ArrayList<EmailAddress>();
		bccEmailAddressList= new ArrayList<EmailAddress>();
		EmailAddress emailAddressTo = new EmailAddress(TO_EMAIL_ADDRESS);
		toEmailAddressList.add(emailAddressTo);
		EmailAddress emailAddressCC = new EmailAddress(CC_EMAIL_ADDRESS);
		ccEmailAddressList.add(emailAddressCC);
		EmailAddress emailAddressBCC = new EmailAddress(BCC_EMAIL_ADDRESS);
		bccEmailAddressList.add(emailAddressBCC);		
		fromEmailAddress = new EmailAddress(FROM_EMAIL_ADDRESS);
		
		Logger.getRootLogger().addAppender(mockAppender);
		arguments = ArgumentCaptor.forClass(LoggingEvent.class);
	}
	
    @After
    public void removeAppender() {
        Logger.getRootLogger().removeAppender(mockAppender);
    }
	
	@Test
	public void testSuppressedEmailWithFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.TRUE);
		emailService.sendEmail(fromEmailAddress, toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);
		verify(mailSender, never()).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Suppressed email to");
	}

	
	@Test
	public void testNotSuppressedEmailWithFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);
		
		emailService.sendEmail(fromEmailAddress, toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);		
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());	
		checkLogMessage("Sending");
	}
	
	@Test
	public void testNoValidToEmailAddressWithFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);

		//Change the to email address to be invalid
		EmailAddress invalidEmailAddress = toEmailAddressList.get(0);
		invalidEmailAddress.setEmailAddress("bogusEmail");
		
		emailService.sendEmail(fromEmailAddress, toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);		
		verify(mailSender, never()).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("There are no valid");
	}


	
	@Test
	public void testExceptionWhenSendingEmailWithFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);
		doThrow(new LucasRuntimeException("This is a test exception, don't panic!")).when(mailSender).send(any(SimpleMailMessage.class));
		
		emailService.sendEmail(fromEmailAddress, toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);		
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Exception");
	}
	
	@Test
	public void testSuppressedEmailWithoutFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.TRUE);
		emailService.sendEmail(toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);
		verify(mailSender, never()).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Suppressed email to");
	}
	
	@Test
	public void testNotSuppressedEmailWithoutFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);
		
		emailService.sendEmail(toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());	
		checkLogMessage("Sending");
	}
	
	@Test
	public void testNoValidToEmailAddressWithoutFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);

		//Change the to email address to be invalid
		EmailAddress invalidEmailAddress = toEmailAddressList.get(0);
		invalidEmailAddress.setEmailAddress("bogusEmail");
		
		emailService.sendEmail(toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);		
		verify(mailSender, never()).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("There are no valid");
	}


	
	@Test
	public void testExceptionWhenSendingEmailWithoutFromEmailAddress(){
		ReflectionUtils.setField(suppressEmailField, emailService, Boolean.FALSE);
		doThrow(new LucasRuntimeException("This is a test exception, don't panic!")).when(mailSender).send(any(SimpleMailMessage.class));
		
		emailService.sendEmail(toEmailAddressList, SUBJECT, EmailService.PLAIN, MESSAGE, ccEmailAddressList, bccEmailAddressList);		
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
		verify(mockAppender, atLeastOnce()).doAppend((LoggingEvent)arguments.capture());
		checkLogMessage("Exception");
	}
	private void checkLogMessage(String leadingLogMessageString) {
		LoggingEvent loggingEventValue = (LoggingEvent)arguments.getValue();
		Assert.assertNotNull(loggingEventValue);
		Assert.assertNotNull(loggingEventValue.getMessage());
		String lastLogMessage = (String)loggingEventValue.getMessage();
		Assert.assertTrue("Leading log message did not match. Expected: " + leadingLogMessageString + ", Actual: " + lastLogMessage, lastLogMessage.startsWith(leadingLogMessageString));
	}
}
