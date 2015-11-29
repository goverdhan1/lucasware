/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.exception;

public class PermissionGroupDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1893875694123452075L;

	public PermissionGroupDoesNotExistException() { 
		super();
	}

	public PermissionGroupDoesNotExistException(String message) {
		super(message);
	}

	public PermissionGroupDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public PermissionGroupDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
