/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.admin;

import java.util.List;

import com.lucas.entity.user.User;

public interface AdminService {
	String determineGreeting();
	List<User> getAdminUsers();
}
