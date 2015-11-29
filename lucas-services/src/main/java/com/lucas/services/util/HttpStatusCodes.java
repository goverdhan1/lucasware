/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.util;

/**
 * REST call error codes
 * @author Anand
 *
 */
public interface HttpStatusCodes {
	
	String SUCCESS_200 = "200";
	String ERROR_401 = "401";

	String ERROR_403 = "403";
	
	String ERROR_409 = "409";
	
	String ERROR_500 = "500";
	String ERROR_406 = "406";
	String ERROR_415 = "415";
	String ERROR_400 = "400";
	String ERROR_405 = "405";
	String ERROR_404 = "404";
	
	//Added custom error code, Having some issues with login page.
	public static final String ERROR_1001 = "1001";  
	
}
