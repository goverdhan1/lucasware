/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.lucas.services.about.AboutServiceImpl;

@Service("logoutService")
public class LogoutServiceImpl implements LogoutService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AboutServiceImpl.class);

    @Override
    public void clearSecurityContextForLogout() {
        
        // Clears Spring Security Context for user logout
        LOG.debug("*** LogoutService - clearing security context");
        SecurityContextHolder.clearContext();
    }
}