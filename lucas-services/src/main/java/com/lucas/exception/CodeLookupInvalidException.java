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

public class CodeLookupInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CodeLookupInvalidException() {
		super();
	}

	public CodeLookupInvalidException(String message) {
		super(message);
	}

	public CodeLookupInvalidException(Throwable cause) {
		super(cause);
	}

	public CodeLookupInvalidException(String message, Throwable cause) {
		super(message, cause);
	}
}
