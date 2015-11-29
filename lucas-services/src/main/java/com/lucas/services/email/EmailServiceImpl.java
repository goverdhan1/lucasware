/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.email;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lucas.entity.EmailAddress;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.util.CollectionsUtilService;
/**
 * This class provides functionality to send emails.
 * @author Sindhu
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	private MailSender mailSender;
	private MailMessage mailMessage;

	@Value("${suppress.email}")
	private boolean suppressEmail;
	
	@Value("${from.email.address}")
	private String fromAddress;

	@Inject
	public EmailServiceImpl(MailSender mailSender, MailMessage mailMessage) {
		this.mailSender = mailSender;
		this.mailMessage = mailMessage;
	}
	@Override
	public void sendEmail(EmailAddress fromEmailAddress, List<EmailAddress> toEmailAddressList,
							String subject, 
							String format, 
							String message,
							List<EmailAddress> ccEmailAddressList,
							List<EmailAddress> bccEmailAddressList) {
		
		processSendEmail(fromEmailAddress, toEmailAddressList, subject,	message, ccEmailAddressList, bccEmailAddressList);
		
	}
	
	@Override
	public void sendEmail(List<EmailAddress> toEmailAddressList,
			String subject, String format, String message,
			List<EmailAddress> ccEmailAddressList,
			List<EmailAddress> bccEmailAddressList) {
		
		EmailAddress fromEmailAddress = new EmailAddress(fromAddress);
		processSendEmail(fromEmailAddress, toEmailAddressList, subject,	message, ccEmailAddressList, bccEmailAddressList);
		
	}	
	
	
	private void processSendEmail(EmailAddress fromEmailAddress,
			List<EmailAddress> toEmailAddressList, String subject,
			String message, List<EmailAddress> ccEmailAddressList,
			List<EmailAddress> bccEmailAddressList) {
		if(suppressEmail){
			logger.info("Suppressed email to: " + (toEmailAddressList==null?"null":toEmailAddressList.toString()) + " because of configuration.");
		} else {
			logger.debug("toEmailAddress size is "+ getValidEmailList(toEmailAddressList).length);
			if ( getValidEmailList(toEmailAddressList).length>0) {
				mailMessage.setFrom(fromEmailAddress.getEmailAddress());
				mailMessage.setTo(getValidEmailList(toEmailAddressList));
				mailMessage.setSubject(subject);
				mailMessage.setText(message);
				mailMessage.setBcc(getValidEmailList(bccEmailAddressList));
				mailMessage.setCc(getValidEmailList(ccEmailAddressList));
				try{
					logger.debug("Sending mail to: " +  mailMessage.toString());
					mailSender.send((SimpleMailMessage) mailMessage);
				}catch(LucasRuntimeException e){
					logger.error("Exception message : {} ", e.getMessage(), e);
				}
			} else {
				logger.debug("There are no valid email addresses specified to send the mail to.");
			}
		}
	}
	
	private String[] getValidEmailList(List<EmailAddress> mailIdsList) {
		List<String> validEmailAddressList = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(mailIdsList)) {
			for (EmailAddress emailAddress : CollectionsUtilService.nullGuard(mailIdsList)) {
				logger.debug(" Mail id: "+emailAddress.getEmailAddress());
				if (emailAddress.validate()) {
					validEmailAddressList.add(emailAddress.getEmailAddress());
				} else {
					logger.warn("Mail Id: " + emailAddress.getEmailAddress() + " in invalid!");
				}
			}
		}
		return validEmailAddressList.toArray(new String[validEmailAddressList.size()]);
	}
	

}