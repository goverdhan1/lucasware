/**
 * 
 */
package com.lucas.exception;

/**
 * @author Prafull
 *
 */
public class CanvasAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -1554708038389585253L;

	public CanvasAlreadyExistsException() {
		super();
	}

	public CanvasAlreadyExistsException(String message) {
		super(message);
	}

	public CanvasAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public CanvasAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
