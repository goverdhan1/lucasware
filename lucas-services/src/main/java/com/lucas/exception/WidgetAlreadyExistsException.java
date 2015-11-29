/**
 * 
 */
package com.lucas.exception;

/**
 * @author Prafull
 *
 */
public class WidgetAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = -7822634362405260288L;
	
	public WidgetAlreadyExistsException(){
		super();
	}
	
	public WidgetAlreadyExistsException(String message){
		super(message);
	}
	
	public WidgetAlreadyExistsException(Throwable cause){
		super(cause);
	}
	
	public WidgetAlreadyExistsException(String message, Throwable cause){
		super(message,cause);
	}
}
