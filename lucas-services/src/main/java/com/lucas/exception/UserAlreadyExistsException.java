/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2581597417998618196L;

	public UserAlreadyExistsException() { 
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
