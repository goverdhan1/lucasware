/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.exception;

public class PermissionGroupAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2581597417998618196L;

	public PermissionGroupAlreadyExistsException() { 
		super();
	}

	public PermissionGroupAlreadyExistsException(String message) {
		super(message);
	}

	public PermissionGroupAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public PermissionGroupAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
