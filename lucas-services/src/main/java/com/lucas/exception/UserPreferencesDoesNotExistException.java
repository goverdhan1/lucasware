/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States

The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
*/


/**
 * Exception class for the code lookup functionality
 *
 * @author DiepLe
 */
package com.lucas.exception;

public class UserPreferencesDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserPreferencesDoesNotExistException() {
		super();
	}

	public UserPreferencesDoesNotExistException(String message) {
		super(message);
	}

	public UserPreferencesDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public UserPreferencesDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
