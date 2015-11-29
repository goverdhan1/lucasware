/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextProvider {
	
	public SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}

}