/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.support.security;

import com.lucas.entity.user.User;

public class AuthenticationResult {
	private String token;
	private User user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
