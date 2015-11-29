/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.entity; 

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

import com.lucas.entity.support.Validatable;
/**
 * This class validates email address.
 * @author Venkat
 *
 */
public class EmailAddress implements Validatable<Object, Object>,Serializable {
	
	private static final long serialVersionUID = 6686626735607731356L;
	private Pattern pattern;
	private Matcher matcher;
	private String emailId;
	
	public EmailAddress() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public EmailAddress(String emailId) {
		this();
		this.emailId = emailId;
	}
	
	public String getEmailAddress() {
		return emailId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailId = emailAddress;
	}

	/**
	 * Validates email address with regular expression
	 * 
	 * @return true valid email address, false invalid email address
	 */
	@Override
	public boolean validate() {
		boolean validEmailAddress = false; 
			if(!StringUtils.isEmpty(emailId)){
				matcher  = pattern.matcher(emailId);
				validEmailAddress = matcher.matches();
			}
			return validEmailAddress;
	}
	@Override
	public String toString() {
		return emailId;
	}

	@Override
	public boolean validate(Object validatable, Object... supplementList) {
		//Not implemented
		return false;
	}
}
