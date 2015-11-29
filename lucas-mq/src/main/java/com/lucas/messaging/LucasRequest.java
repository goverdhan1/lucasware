package com.lucas.messaging;

import java.io.Serializable;

/**
 * All requests should be serializable
 * This interface is used to manipulate all requests via the Spring Integration/messaging infrastructure.
 * @author Pankaj Tandon
 *
 */
public interface LucasRequest<T, S> extends Serializable{
	
	/**
	 * This is the payload that is optionally returned by the LucasRequest implementation. 
	 * It is <b>not</b> the payload that is extracted from the message wrapper
	 * by the spring integration infrastructure (using expressions maybe).
	 * @return
	 */
	T getLucasPayload();
	
	/**
	 * This returns an object that represents this request and co-relates it to a subsequent response.
	 * @return
	 */
	S getCorrelationId();

}
