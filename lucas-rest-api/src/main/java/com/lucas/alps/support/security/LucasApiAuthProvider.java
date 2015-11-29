/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import com.lucas.entity.user.Permission;
import com.lucas.entity.user.User;
import com.lucas.services.user.UserService;


public class LucasApiAuthProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(LucasApiAuthProvider.class);
	
	@Inject 
	private UserService userService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
	}

	/**
	 * Implement this when JPA impl is ready
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		User user = userService.getUser(username);
		LOG.debug("Inside-LucasApiAuthProvider.java");
		
		if(null == user) {
			throw new BadCredentialsException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getHashedPassword(), getGrantedAuthorities(user));
	}

	public static Set<GrantedAuthority> getGrantedAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();  
		Set<Permission> permssionsSet = user.getPermissionsSet();
		if (!CollectionUtils.isEmpty(permssionsSet)) {
			for (Permission permission : permssionsSet) {
				LOG.debug("PermissionName-{}",permission);
				authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
			}
		}
        return authorities;  
    }  
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
