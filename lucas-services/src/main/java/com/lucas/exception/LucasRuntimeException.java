/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.exception;

public class LucasRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9023084800009920237L;
	
	public static final String INTERNAL_ERROR = "Internal server processing error has occured";

	public LucasRuntimeException() { 
		super();
	}

	public LucasRuntimeException(String message) {
		super(message);
	}

	public LucasRuntimeException(Throwable cause) {
		super(cause);
	}

	public LucasRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}