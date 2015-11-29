package com.lucas.services.security;  

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.services.ui.UIService;
import com.lucas.services.user.UserService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

public class SecurityServiceFunctionalTests extends AbstractSpringFunctionalTests{

	private static final String CANVAS_NAME = "aCanvas";

	@Inject
	private SecurityService securityService;

	@Inject
	private UserService userService;
	
	@Inject
	private UIService uiService;

	@Inject
	private PermissionGroupDAO permissionGroupDAO;

	@Inject
	private UserDAO userDAO;
	

	private User user;
	private static String HASHED_PASSWORD = "aHashedPassword";
	private List<PermissionGroup> permissionGroupList;
	private List<PermissionGroup> permissionGroupListNew;
	
	private List<Permission> permissionList;
	private List<Permission> permissionListNew;
	
	private PermissionGroup permissionGroup;
	private PermissionGroup permissionGroupNew;
	
	private Permission permission;
	private static String JACK_USERNAME = "jack123";
	private static String JACK_PASSWORD = "secret";
	private static String VIEW_CANVAS_ACTION = "view-canvas";
	private static String JACK_GROUP_NAME = "picker";
	private Authentication auth;
	private SimpleGrantedAuthority[] grantedAuthoritiesArr = {new SimpleGrantedAuthority("delete-user"), new SimpleGrantedAuthority("disable-user")};

	@Before
	public void setup() {

		Permission p1 = new Permission("create-assignment");
		Permission p2 = new Permission("view-report-productivity");
		Permission p3 = new Permission("configure-location");
		Permission p4 = new Permission("create-canvas");
		Permission p5 = new Permission("delete-canvas");
		Permission[] permissionArray = { p1,p2,p3,p4,p5};
		permissionList = new ArrayList<Permission>();
		permissionList.addAll(Arrays.asList(permissionArray));

		Permission[] permissionArrayNew = { p1,p2};
		permissionListNew = new ArrayList<Permission>();
		permissionListNew.addAll(Arrays.asList(permissionArrayNew));
		
		user = new User();
		user.setUserName(JACK_USERNAME);
		user.setPlainTextPassword(JACK_PASSWORD);
		user.setUserId(1L);
		user.setHashedPassword(HASHED_PASSWORD);
		
		permissionGroup = new PermissionGroup();
		permissionGroup.setPermissionGroupId(1L);
		permissionGroup.setPermissionGroupName(JACK_GROUP_NAME);
     	permissionGroup.setPermissionGroupPermissionList(permissionList);		
		Set<User> userSet = new HashSet<User>();
		userSet.add(user);
		permissionGroup.setUserSet(userSet);
		permissionGroupList = new ArrayList<PermissionGroup>();
		permissionGroupList.add(permissionGroup);
		
		permissionGroupNew = new PermissionGroup();
		permissionGroupNew.setPermissionGroupId(1L);
		permissionGroupNew.setPermissionGroupName("warehouse-manager");
		permissionGroupNew.setUserSet(userSet);
		permissionGroupListNew = new ArrayList<PermissionGroup>();
		permissionGroupListNew.add(permissionGroupNew);
		auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(grantedAuthoritiesArr));
	}

	@Test
	public void testAuthentication(){    	
    	boolean boo = securityService.authenticate("bob", "doesn'tmatter");
    	Assert.assertTrue(boo);
    	boo = securityService.authenticate("jack123", "doesn'tmatter");
    	Assert.assertTrue(!boo);
	}


	@Transactional
	@Rollback(true)
	@Test
	public void testAddGroupListToUser() {

		securityService.addGroupListToUser(JACK_USERNAME, permissionGroupListNew);
		User user = userDAO.getUser(JACK_USERNAME);
		org.junit.Assert.assertNotNull(user.getUserPermissionGroupList());	
	}
	

	@Transactional
	@Rollback(true)
	@Test
	public void testReplaceGroupListForUser() {
		securityService.replaceGroupListForUser(JACK_USERNAME, permissionGroupList);
		User user = userDAO.getUser(JACK_USERNAME);
		org.junit.Assert.assertNotNull(user.getUserPermissionGroupList());	
	}


	@Transactional
	@Rollback(true)
	@Test
	public void testAddPermissionsToGroupWhenPassedInGroupDoesNotExist(){
		securityService.addPermissionsToGroup(JACK_GROUP_NAME, permissionList);
		org.junit.Assert.assertNotNull(permissionGroup.getPermissionGroupPermissionList());
	}	

	@Transactional
	@Rollback(true)
	@Test
	public void testReplacePermissionListForGroup(){
		securityService.replacePermissionListForGroup(JACK_GROUP_NAME, permissionListNew);
		org.junit.Assert.assertNotNull(permissionGroup.getPermissionGroupPermissionList());
	}	

	@Transactional
	@Rollback(true)
	@Test
	public void testRevokePermissionFromGroup() {
		securityService.revokePermissionFromGroup(JACK_GROUP_NAME, permissionListNew.get(0));
		PermissionGroup permissionGroup  = permissionGroupDAO.getPermissionGroupByName(JACK_GROUP_NAME);
		org.junit.Assert.assertFalse(permissionGroup.getPermissionGroupPermissionList().contains(permissionGroup));
	}
	

	@Transactional
	@Rollback(true)
	@Test
	public void testRevokeAllPermissionsFromPermissionGroup() {
		securityService.revokeAllPermissionsFromGroup(JACK_GROUP_NAME);
		PermissionGroup permissionGroup  = permissionGroupDAO.getPermissionGroupByName(JACK_GROUP_NAME);
		org.junit.Assert.assertNull(permissionGroup.getPermissionGroupPermissionList());
	}	
	
	@Transactional
	@Rollback(true)
	@Test
	public void testDeletePermissionGroup(){
		securityService.deletePermissionGroup(JACK_GROUP_NAME);
		PermissionGroup retrievedGroup = permissionGroupDAO.getPermissionGroupByName(JACK_GROUP_NAME);
		org.junit.Assert.assertNull(retrievedGroup);
	}	
	
	@Transactional
	@Rollback(true)
	@Test
	public void testRevokeGroupFromUser() {
		securityService.revokeGroupFromUser(JACK_GROUP_NAME , JACK_USERNAME);
		User user = userService.getUser(JACK_USERNAME);
		org.junit.Assert.assertFalse(user.getUserPermissionGroupList().contains(permissionGroupDAO.getPermissionGroupByName(JACK_GROUP_NAME)));
	}
	

	@Transactional
	@Rollback(true)
	@Test
	public void testRevokeAllGroupsFromUser() {
		securityService.revokeAllGroupsFromUser(JACK_USERNAME);
		User user = userService.getUser(JACK_USERNAME);
		org.junit.Assert.assertNull(user.getUserPermissionGroupList());
	}
	
	@Test
	public void testGetPermissionsForCurrentUserWithAuthentication(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		List<Permission> permissions = securityService.getPermissionsForCurrentUser();
		org.junit.Assert.assertTrue("Permissions list not equal to the expected numbers", permissions.size() == 2);
	}

	@Test
	public void testGetPermissionsForCurrentUserWithoutAuthentication(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
		List<Permission> permissions = securityService.getPermissionsForCurrentUser();
		org.junit.Assert.assertTrue("Permissions list not equal to the expected numbers", permissions.size() == 0);
	}
	
	@Test
	public void testGetAllPermissionsByCategories(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
		SortedMap<String, List<String>> permissions = securityService.getAllPermissionsByCategories();
		org.junit.Assert.assertTrue("Get all permissions doesn't equal the expected number of: " + permissions.size(), permissions.size() >= 7);
	}
	
	@Test
	public void testGetLoggedInUsername(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		String uname = securityService.getLoggedInUsername();
		org.junit.Assert.assertNotNull("Logged in username should not be null", uname);
		org.junit.Assert.assertTrue(uname.contains(JACK_USERNAME) );
	}
	
	@Test
	public void testBuildCanvasActionConfig() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
		
		Canvas canvas = new Canvas(CANVAS_NAME);
		securityService.buildCanvasActionConfig(canvas);
		org.junit.Assert.assertNotNull("Canvas action config is null ", canvas.getActionConfig());
		org.junit.Assert.assertTrue("View canvas action should have been true in the action config ", canvas.getActionConfig().getActionConfig().get(new Permission(VIEW_CANVAS_ACTION)) == Boolean.TRUE);
	}
}
