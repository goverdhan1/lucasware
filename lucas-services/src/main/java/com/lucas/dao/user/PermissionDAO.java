/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.entity.user.Permission;

import java.util.List;

public interface PermissionDAO {
	
	List<Permission> getAllPermissions();
	
	List<Permission> getPermission(List<Long> permissionId);

	List<Permission> getUserPermissionsByPermissionGroupName(
			String permissionGroupName);
	
	Permission getPermissionByName(String permissionName);
	
}
