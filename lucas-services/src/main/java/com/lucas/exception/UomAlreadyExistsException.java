package com.lucas.exception;

public class UomAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 4647995709172712736L;

	public UomAlreadyExistsException() { 
		super();
	}

	public UomAlreadyExistsException(String message) {
		super(message);
	}

	public UomAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public UomAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
