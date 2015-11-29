package com.lucas.exception;

public class EaiEventDoesNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public EaiEventDoesNotExistsException(){
		super();
	}

	public EaiEventDoesNotExistsException(String message){
		super(message);
	}
	
	public EaiEventDoesNotExistsException(Throwable cause){
		super(cause);
	}
	
	public EaiEventDoesNotExistsException(String message, Throwable cause){
		super(message, cause);
	}
}
