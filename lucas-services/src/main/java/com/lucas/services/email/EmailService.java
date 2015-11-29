/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.email;

import java.util.List;

import com.lucas.entity.EmailAddress;

public interface EmailService {
	void sendEmail(EmailAddress fromEmailAddress, List<EmailAddress> toEmailAddressList, String subject,String format, String message, List<EmailAddress> ccEmailAddressList,List<EmailAddress> bccEmailAddressList);
	void sendEmail(List<EmailAddress> toEmailAddressList, String subject,String format, String message, List<EmailAddress> ccEmailAddressList,List<EmailAddress> bccEmailAddressList);
	
	String PLAIN = "text/plain";
	String HTML = "text/html";
	
}
