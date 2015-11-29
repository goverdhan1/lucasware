package com.lucas.exception;

public class IncompatibleUOMException extends RuntimeException {

	private static final long serialVersionUID = -5043624003562276388L;

	public IncompatibleUOMException() { 
		super();
	}

	public IncompatibleUOMException(String message) {
		super(message);
	}

	public IncompatibleUOMException(Throwable cause) {
		super(cause);
	}

	public IncompatibleUOMException(String message, Throwable cause) {
		super(message, cause);
	}
}
