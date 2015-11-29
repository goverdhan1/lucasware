package com.lucas.exception;

public class InvalidConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 6478075679840136372L;

	public InvalidConfigurationException() { 
		super();
	}

	public InvalidConfigurationException(String message) {
		super(message);
	}

	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

	public InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}


}
