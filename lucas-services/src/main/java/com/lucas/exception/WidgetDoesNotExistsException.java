/**
 * 
 */
package com.lucas.exception;

public class WidgetDoesNotExistsException extends RuntimeException{

	private static final long serialVersionUID = 3100994120530251640L;

	public WidgetDoesNotExistsException(){
		super();
	}
	
	public WidgetDoesNotExistsException(String message){
		super(message);
	}
	
	public WidgetDoesNotExistsException(Throwable cause){
		super(cause);
	}
	
	public WidgetDoesNotExistsException(String message, Throwable cause){
		super(message,cause);
	}
}
