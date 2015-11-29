package com.lucas.exception;

public class InvalidLocationException extends RuntimeException {

	private static final long serialVersionUID = -6889648195091788755L;

	public InvalidLocationException() { 
		super();
	}

	public InvalidLocationException(String message) {
		super(message);
	}

	public InvalidLocationException(Throwable cause) {
		super(cause);
	}

	public InvalidLocationException(String message, Throwable cause) {
		super(message, cause);
	}
}
