/**
 * 
 */
package com.lucas.alps.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.UserGroupsPermissionsDetailsView;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @author Prafull
 *
 */
public class UserGroupsPermissionsView {
	
	private List<PermissionGroup> permissionGroupList;
	
	public UserGroupsPermissionsView() {
	}
	
	public UserGroupsPermissionsView(List<PermissionGroup> permissionGroupList) {
		this.permissionGroupList = permissionGroupList;
	}

	public List<PermissionGroup> getPermissionGroupList() {
		return permissionGroupList;
	}

	public void setPermissionGroupList(List<PermissionGroup> permissionGroupList) {
		this.permissionGroupList = permissionGroupList;
	}

	@JsonView(UserGroupsPermissionsDetailsView.class)
	public MultiMap getGroupPermissions(){
		MultiMap multiMap = new MultiValueMap();
		for(PermissionGroup permissionGroup:CollectionsUtilService.nullGuard(permissionGroupList) ){
			
			for(Permission permission:CollectionsUtilService.nullGuard(permissionGroup.getPermissionGroupPermissionList())){
				multiMap.put(permissionGroup.getPermissionGroupName(), permission.getPermissionName());
			}
			
		}
		return multiMap;
	}
	
	public void setGroupPermissions(MultiMap groupPermissions){
		
	}
	
	@JsonView(UserGroupsPermissionsDetailsView.class)
	public List<String> getAvailablePermissions(){
		List<String> uniquePermissionList = new ArrayList<String>();
		for(PermissionGroup permissionGroup:CollectionsUtilService.nullGuard(permissionGroupList) ){
			for(Permission permission:CollectionsUtilService.nullGuard(permissionGroup.getPermissionGroupPermissionList())) {
				if (!uniquePermissionList.contains(permission.getPermissionName())) {
					uniquePermissionList.add(permission.getPermissionName());
				}
			}
		}
		return uniquePermissionList;
	}
	
	public void setAvailablePermissions(List<Permission> availablePermissions){
		
	}

}
