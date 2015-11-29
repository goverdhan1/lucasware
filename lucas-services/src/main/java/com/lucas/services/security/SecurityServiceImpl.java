/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

*/
package com.lucas.services.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.process.LucasProcessBean;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedCanvasActionConfig;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.exception.PermissionGroupAlreadyExistsException;
import com.lucas.exception.PermissionGroupDoesNotExistException;
import com.lucas.exception.UserDoesNotExistException;
import com.lucas.services.customer.CustomerService;
import com.lucas.services.util.CollectionsUtilService;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService, JavaDelegate {

	private static Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);
	public final CustomerService customerService;
	private final UserDAO userDAO;
	private final PermissionGroupDAO permissionGroupDAO;
	private final PermissionDAO permissionDAO;	
	private static final String VIEW_CANVAS_ACTION = "view-canvas";
	private static final Object WIDGET_ACCESS_MAP = "widget-access";

	@Inject
	public SecurityServiceImpl(CustomerService customerService, UserDAO userDAO,	PermissionGroupDAO permissionGroupDAO, PermissionDAO permissionDAO ) {
		this.customerService = customerService;
		this.userDAO = userDAO;
		this.permissionGroupDAO = permissionGroupDAO;
		this.permissionDAO = permissionDAO;
	}
	public boolean authenticate(String userId, String password) {	
		boolean boo = false;
		if (customerService.isPrivilegedCustomer()){
			boo = true;
		} else {
			//Check is for non-priviliged customers only
			if (userId.equals("bob")) {
				boo = true;
			} else {
				boo = false;
			}
		}
		return boo;
	}

	public void execute(DelegateExecution execution) throws Exception {
		LOG.debug("Starting execution...");
		LucasProcessBean lpb = (LucasProcessBean)execution.getVariable("lpb");
		lpb.getUser().setAuthenticated(authenticate(lpb.getUser().getUserName(), lpb.getUser().getHashedPassword())); 
	}

	/**
	 * creates group object along with permission associations if any.
	 * @Param  permisionGroup name
	 * @return 
    */
	@Transactional(readOnly = true)
	@Override
	public void createPermissionGroup(PermissionGroup permissionGroup)
			throws PermissionGroupAlreadyExistsException {
		if (permissionGroup != null) {
			PermissionGroup retrievedPermissionGroup = permissionGroupDAO
					.getPermissionGroupByName(permissionGroup.getPermissionGroupName());
			if (retrievedPermissionGroup == null) {
				permissionGroupDAO.save(permissionGroup);
			} else {
				LOG.debug(String.format("PermissionGroup already exists with name %s ",
						permissionGroup.getPermissionGroupName()));
				throw new PermissionGroupAlreadyExistsException(String.format(
						"PermissionGroup already exists with name %s ",
						permissionGroup.getPermissionGroupName()));
			}
		} else {
			LOG.debug("Did not create any PermissionGroup as, null PermissionGroup was passed in.");
		}
	}

	/**
	 * Removes group object and all it's user and permission associations.
	 * @Param  permisionGroup name
	 * @return 
    */	
	@Transactional(readOnly = true)
	@Override
	public void deletePermissionGroup(String permissionGroupName) {
		if (permissionGroupName != null) {
			PermissionGroup retrievedGroup = permissionGroupDAO.getPermissionGroupByName(permissionGroupName);
			if (retrievedGroup != null) {
				retrievedGroup.setPermissionGroupPermissionList(null);
				retrievedGroup.setUserSet(null);
				retrievedGroup.setPermissionsSet(null);
				permissionGroupDAO.save(retrievedGroup);
				permissionGroupDAO.delete(retrievedGroup);				
			} else {
				LOG.debug(String
						.format("Did not delete any Group as no group with groupname %s found.",
								permissionGroupName));
			}
		} else {
			LOG.debug("Did not delete any PermissionGroup as, null PermissionGroupName was passed in.");
		}		
	}
	
	/* 
	 * If groupName does not exist, it should create one.
	 * If it does, then add permissionList to group.
	 * Ignore duplicate permissions
	 * @return the number of unique permissions added 
	 * Note previous permission associations are not returned in this count even if they existed.
	 */
	@Transactional(readOnly = true)
	@Override
	public int addPermissionsToGroup(String permissionGroupName, List<Permission> permissionList) {
		PermissionGroup retrievedGroup = permissionGroupDAO.getPermissionGroupByName(permissionGroupName);
		Integer permissionsAddedCount = 0;
		if (retrievedGroup != null) {
			for(Permission PermissionToBeSaved : CollectionsUtilService.nullGuard(permissionList)){
	            Boolean toAdd = true;
				for(Permission existingPermission : retrievedGroup.getPermissionGroupPermissionList()){
					if(PermissionToBeSaved.equals(existingPermission)){
						toAdd = false;
						break;
					}
				}
					if (toAdd) {
						Permission hydratedPermission = permissionDAO.getPermissionByName(PermissionToBeSaved.getPermissionName());
						retrievedGroup.getPermissionGroupPermissionList().add(hydratedPermission);
						permissionGroupDAO.save(retrievedGroup);
						permissionsAddedCount++;
					}
				}
				
			
		} else{
			PermissionGroup newGroup = new PermissionGroup();
			newGroup.setPermissionGroupName(permissionGroupName);
			List<Permission> existingPermissionList = new ArrayList<Permission>();
			for(Permission p : permissionList){
				existingPermissionList.add(permissionDAO.getPermissionByName(p.getPermissionName()));	
			}
			newGroup.setPermissionGroupPermissionList(existingPermissionList);
			permissionsAddedCount = permissionList.size();
			permissionGroupDAO.save(newGroup);
		}
		return permissionsAddedCount.intValue();
	}

	/* 
	 * Removes all the existing permissions of the group and saves the permissions passed in
	 * as an argument
	 * @Param PermissionGroupName
	 * @Param permissionList
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public void replacePermissionListForGroup(String PermissionGroupName, List<Permission> permissionList) {
		PermissionGroup retrievedGroup = retriveExistingGroup(PermissionGroupName);
		retrievedGroup.setPermissionGroupPermissionList(null);
		List<Permission> existingPermissionList = new ArrayList<Permission>();
		for(Permission p : permissionList){
			existingPermissionList.add(permissionDAO.getPermissionByName(p.getPermissionName()));	
		}
		retrievedGroup.setPermissionGroupPermissionList(existingPermissionList);

		permissionGroupDAO.save(retrievedGroup);		
	}

	@Transactional(readOnly = true)	
	@Override
	public void revokePermissionFromGroup(String groupName,	Permission permission) {
		PermissionGroup retrievedGroup = retriveExistingGroup(groupName);
		Permission permissionToBeDeleted =  permissionDAO.getPermissionByName(permission.getPermissionName());
		if(retrievedGroup.getPermissionGroupPermissionList().contains(permissionToBeDeleted)) {
			retrievedGroup.getPermissionGroupPermissionList().remove(permissionToBeDeleted);
		}
		permissionGroupDAO.save(retrievedGroup);	
	}

	@Transactional(readOnly = true)
	@Override
	public void revokeAllPermissionsFromGroup(String groupName) {
		PermissionGroup retrievedGroup = retriveExistingGroup(groupName);
		retrievedGroup.setPermissionGroupPermissionList(null);
		permissionGroupDAO.save(retrievedGroup);
	}

	@Transactional(readOnly = true)
	@Override
	public MultiMap getPermissionListForGroup(String groupName) {
		PermissionGroup retrievedGroup = retriveExistingGroup(groupName);
		List<Permission> permissionList = retrievedGroup.getPermissionGroupPermissionList();
		MultiMap permissionMap = new MultiHashMap();
		for(Permission permission : CollectionsUtilService.nullGuard(permissionList)){
			permissionMap.put(permission.getPermissionCategory(), permission.getPermissionName());			
		}
		return permissionMap;
	}


	private PermissionGroup retriveExistingGroup(String permissionGroupName) {
		PermissionGroup retrievedGroup = permissionGroupDAO.getPermissionGroupByName(permissionGroupName);
		if (retrievedGroup == null) {
			throw new PermissionGroupDoesNotExistException(String.format(
					"No PermissionGroup exists with groupName %s.",
					permissionGroupName));
		}
		return retrievedGroup;
	}


	@Transactional(readOnly = true)
	@Override
	public List<PermissionGroup> getUserPermissionGroupsByUsername(String username) {
		return permissionGroupDAO.getUserPermissionGroupsByUsername(username);
	}

	@Transactional(readOnly = true)
	@Override
	public int addGroupListToUser(String username, List<PermissionGroup> permissionGroupList) {

		User retrievedUser = retrieveExistingUser(username);
		List<PermissionGroup> existingPermissionGroupList = retrievedUser.getUserPermissionGroupList();

		Integer groupsAddedCount = 0;
		for(PermissionGroup PermissionGroupToBeSaved : CollectionsUtilService.nullGuard(permissionGroupList)){ // looping through existing Groups
            Boolean toAdd = true;
			for(PermissionGroup existingPermissionGroup : CollectionsUtilService.nullGuard(existingPermissionGroupList)){
				if(PermissionGroupToBeSaved.equals(existingPermissionGroup)){
					toAdd = false;
					break;
				}
			}
				if (toAdd) {
					PermissionGroup PermissionGroupToBeSavedHydrated = permissionGroupDAO.getPermissionGroupByName(PermissionGroupToBeSaved.getPermissionGroupName());
					retrievedUser.getUserPermissionGroupList().add(PermissionGroupToBeSavedHydrated);
					userDAO.save(retrievedUser);					
					groupsAddedCount++;
				}
		}
		return groupsAddedCount;
	}

	@Transactional(readOnly = true)
	@Override
	public void replaceGroupListForUser(String username, List<PermissionGroup> permissionGroupList) {
		User retrievedUser = retrieveExistingUser(username);
		retrievedUser.setUserPermissionGroupList(null);

        if (permissionGroupList.size() == 0) {
            // all permission groups for the user is revoked
            userDAO.save(retrievedUser);
            return;
        }

		List<PermissionGroup> existingPermissionGroupList = new ArrayList<PermissionGroup>();
		for(PermissionGroup pg : CollectionsUtilService.nullGuard(permissionGroupList)){
            PermissionGroup curPermissionGroup =  permissionGroupDAO.getPermissionGroupByName(pg.getPermissionGroupName());
            if (curPermissionGroup != null) {
                existingPermissionGroupList.add(curPermissionGroup);
            }
		}		

        if (existingPermissionGroupList != null){
            retrievedUser.setUserPermissionGroupList(existingPermissionGroupList);
            userDAO.save(retrievedUser);
        }
	}


	@Transactional(readOnly = true)
	@Override
	public void revokeGroupFromUser(String permissionGroupName, String username) {
		User retrievedUser = retrieveExistingUser(username);
		PermissionGroup groupToBeDeleted =  permissionGroupDAO.getPermissionGroupByName(permissionGroupName);

		if(retrievedUser.getUserPermissionGroupList().contains(groupToBeDeleted)) {
			retrievedUser.getUserPermissionGroupList().remove(groupToBeDeleted);
		}

		userDAO.save(retrievedUser);		
	}


	@Transactional(readOnly = true)
	@Override
	public void revokeAllGroupsFromUser(String username) {
		User retrievedUser = retrieveExistingUser(username);
		retrievedUser.setUserPermissionGroupList(null);
		userDAO.save(retrievedUser);
	}
	
	@Transactional(readOnly = true)
	@Override
	public MultiMap getPermissionListForUser(String username) {
		User retrievedUser = retrieveExistingUser(username);
		List<PermissionGroup> groupList = retrievedUser.getUserPermissionGroupList();
		MultiMap permissionMap = new MultiHashMap();
		for(PermissionGroup retrievedGroup : CollectionsUtilService.nullGuard(groupList)){
			List<Permission> permissionList = retrievedGroup.getPermissionGroupPermissionList();

			for(Permission permission : CollectionsUtilService.nullGuard(permissionList)){
				permissionMap.put(permission.getPermissionCategory(), permission.getPermissionName());			
			}			
		}
		return permissionMap;	
	}


	private User retrieveExistingUser(String username) {
		User retrievedUser = userDAO.findByUserName(username);
		if (retrievedUser == null) {
			throw new UserDoesNotExistException(String.format(
					"No user exists with username %s. Cannot add Groups to user!",
					username));
		}
		return retrievedUser;
	}
	@Override
	public List<Permission> getPermissionsForCurrentUser() {
		
		List<Permission> permissionsList = new ArrayList<Permission>();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				@SuppressWarnings("unchecked")
				List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication
						.getAuthorities();
				for (GrantedAuthority authority : CollectionsUtilService.nullGuard(authorities)) {
					permissionsList
							.add(new Permission(authority.getAuthority()));
				}
			}
		}
		return permissionsList;
	}
	
	/**
	 * This method actually creates the action config for a widget
	 * @param widget The widget for which the action config needs to be created
	 * @throws Exception 
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
		public void buildWidgetActionConfig(
			AbstractLicensableWidget widget) throws Exception {
		
		MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfigurable = widget.getActionConfig();;
		List<Permission> permissions = getPermissionsForCurrentUser();
		mappedActionConfigurable.buildActionConfigurable(permissions);
		widget
		.setActionConfig(mappedActionConfigurable);
	}
	
	@Override
	@Transactional(readOnly = true)
	// Gets all the permissions in the database by category
	public SortedMap<String,List<String>> getAllPermissionsByCategories() {

		List<Permission> permissionsList = permissionDAO.getAllPermissions();
		
		Collections.sort(permissionsList);
		
		SortedMap<String, List<String>> sortedPermissionsMap = new TreeMap<String, List<String>>();
		
		for (Permission permission : CollectionsUtilService
				.nullGuard(permissionsList)) {
			String permissionCategory = permission.getPermissionCategory();
			if (!sortedPermissionsMap.containsKey(permissionCategory)) {
				sortedPermissionsMap.put(permissionCategory,
						new ArrayList<String>());
			}
			
			sortedPermissionsMap.get(permissionCategory).add(permission.getPermissionName());
		}
		
		return sortedPermissionsMap;
	}
	
	
	/* (non-Javadoc)
	 * @see com.lucas.services.security.SecurityService#getLoggedInUsername()
	 */
	@Override
	public String getLoggedInUsername() {

		String userName = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				userName = authentication.getName();
				return userName;
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.security.SecurityService#buildCanvasActionConfig(com
	 * .lucas.entity.ui.canvas.Canvas)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void buildCanvasActionConfig(Canvas canvas) throws Exception {
		boolean widgetAccessible = true;
		List<DefaultWidgetInstance> widgetInstances = canvas
				.getWidgetInstanceList();

		for (DefaultWidgetInstance defaultWidgetInstance : CollectionsUtilService
				.nullGuard(widgetInstances)) {
			AbstractLicensableWidget widget = defaultWidgetInstance.getWidget();
			buildWidgetActionConfig(widget);
			Map<Permission, Boolean> widgetAccessMap = widget.getActionConfig()
					.getActionConfig().get(WIDGET_ACCESS_MAP);
			if (widgetAccessMap != null && !widgetAccessMap.isEmpty()) {
				Iterator<Entry<Permission, Boolean>> it = widgetAccessMap
						.entrySet().iterator();
				Map.Entry<Permission, Boolean> entry = it.next();
				widgetAccessible = entry.getValue();
				if (widgetAccessible == Boolean.FALSE) {
					break;
				}
			}
		}
		Permission viewCanvasPermission = new Permission(VIEW_CANVAS_ACTION);
		Map<Permission, Boolean> actionConfigMap = new HashMap<Permission, Boolean>();
		actionConfigMap.put(viewCanvasPermission, widgetAccessible);
		
		MappedActionConfigurable<Permission, Boolean> mappedActionConfigurable = new PermissionMappedCanvasActionConfig(actionConfigMap);
		canvas.setActionConfig(mappedActionConfigurable);
		
	}

}
