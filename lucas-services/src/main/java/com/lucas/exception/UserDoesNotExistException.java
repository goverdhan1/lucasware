/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.exception;

public class UserDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1893875694123452075L;

	public UserDoesNotExistException() { 
		super();
	}

	public UserDoesNotExistException(String message) {
		super(message);
	}

	public UserDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public UserDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
