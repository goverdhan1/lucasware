/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.security;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.collections.MultiMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;

public interface SecurityService {
	
	//Method representing business logic
	boolean authenticate(String userId, String password);

	// group  operations
	void createPermissionGroup(PermissionGroup pg);
	void deletePermissionGroup(String groupName);

	//  group - Permission  operations
	int addPermissionsToGroup(String groupName, List<Permission> permissionList);
	void replacePermissionListForGroup(String groupName, List<Permission> permissionList);	
	void revokePermissionFromGroup(String groupName, Permission permission);
	void revokeAllPermissionsFromGroup(String groupName);
	MultiMap getPermissionListForGroup(String groupName);

	//  permission operations
	SortedMap<String,List<String>> getAllPermissionsByCategories();
	
    //  user - group  operations
	List<PermissionGroup> getUserPermissionGroupsByUsername(String username);
	int addGroupListToUser(String username, List<PermissionGroup> groupList);
	void replaceGroupListForUser(String username, List<PermissionGroup> groupList);
	void revokeGroupFromUser(String groupName, String username);
	void revokeAllGroupsFromUser(String username);

	//  user - permission  operations
	MultiMap getPermissionListForUser(String username);		
	
	List<Permission> getPermissionsForCurrentUser();

	void buildWidgetActionConfig(AbstractLicensableWidget widget) throws JsonParseException, JsonMappingException, IOException, Exception;
	/**
	 * This method returns the logged in user's username
	 * @return
	 */
	String getLoggedInUsername();

	/**
	 * This method is used for building the canvas action config.
	 * 
	 * @param canvas
	 * @throws Exception
	 */
	void buildCanvasActionConfig(Canvas canvas) throws Exception;

}
