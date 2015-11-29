package com.lucas.exception;

public class EventAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EventAlreadyExistsException(){
		super();		
	}
	
	public EventAlreadyExistsException(String message){
		super(message);
	}
	
	public EventAlreadyExistsException(Throwable cause){
		super(cause);
	}

	public EventAlreadyExistsException(String message, Throwable cause){
		super(message,cause);
	}
}
