package com.lucas.services.security;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.exception.PermissionGroupAlreadyExistsException;
import com.lucas.exception.UserDoesNotExistException;
import com.lucas.services.customer.CustomerService;
import com.lucas.services.user.UserServiceImpl;


@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class, StringUtils.class})
public class SecurityServiceUnitTests {

	@Mock
	public CustomerService customerService;

	@InjectMocks
	private SecurityServiceImpl securityService;
	
	@Mock
	private StandardPBEStringEncryptor stringEncryptor;	

	@Mock
	private UserDAO userDAO;
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private Appender mockAppender;
	
	@Mock
	private PermissionGroupDAO permissionGroupDAO;	
	
	@Mock
	private PermissionDAO permissionDAO;

	private static String USERNAME = "aUsername";
	private static String PERMISSIONGROUPNAME = "aPermissionGroupName";
	private static String PERMISSIONNAME = "aPermissionName";
	private static String HASHED_PASSWORD = "aHashedPassword";
	private User user;
	private List<PermissionGroup> permissionGroupList;
	private List<PermissionGroup> permissionGroupListNew;
	private List<Permission> permissionList;
	private List<Permission> permissionListNew;
	private PermissionGroup permissionGroup;
	private PermissionGroup permissionGroupNew;
	private Permission permission;
		
	@Before
	public void setup(){
		mockStatic(StandardPBEStringEncryptor.class);
		mockStatic(StringUtils.class);
		user = new User();
		user.setUserId(1L);
		user.setUserName(USERNAME);
		user.setHashedPassword(HASHED_PASSWORD);
		
		permissionGroup = new PermissionGroup();
		permissionGroup.setPermissionGroupId(1L);
		permissionGroup.setPermissionGroupName(PERMISSIONGROUPNAME);

		permissionGroupNew = new PermissionGroup();
		permissionGroupNew.setPermissionGroupId(1L);
		permissionGroupNew.setPermissionGroupName(PERMISSIONGROUPNAME+"New");
		permissionGroupListNew = new ArrayList<PermissionGroup>();
		permissionGroupListNew.add(permissionGroup);
		
		Set<User> userSet = new HashSet<User>();
		userSet.add(user);
		permissionGroup.setUserSet(userSet);
		
		permission = new Permission();
		permission.setPermissionId(1L);
		permission.setPermissionName(PERMISSIONNAME);
		
		permissionGroupList = new ArrayList<PermissionGroup>();
		permissionGroupList.add(permissionGroup);
		
		permissionList = new ArrayList<Permission>();
		permissionList.add(permission);
		permissionGroup.setPermissionGroupPermissionList(permissionList);
		user.setUserPermissionGroupList(permissionGroupList);

		permission = new Permission();
		permission.setPermissionId(1L);
		permission.setPermissionName(PERMISSIONNAME);
		
		permissionListNew = new ArrayList<Permission>();
		permissionListNew.add(permission);
		
	}	
	@Test
	public void testAuthenticationForPrivilegedCustomer(){

		when(customerService.isPrivilegedCustomer()).thenReturn(true);
		boolean b = securityService.authenticate("bob", "anyPassword");

		Assert.assertTrue(b);	
	}
	
	@Test
	public void testAuthenticationForNonPrivilegedCustomerGoodUser(){

		when(customerService.isPrivilegedCustomer()).thenReturn(false);
		boolean b = securityService.authenticate("bob", "anyPassword");
		Assert.assertTrue(b);	
	}
	
	@Test
	public void testAuthenticationForNonPrivilegedCustomerBadUser(){

		when(customerService.isPrivilegedCustomer()).thenReturn(false);
		boolean b = securityService.authenticate("jack123", "anyPassword");
		Assert.assertTrue(!b);	
	}

	@Test
	public void testCreatePermissionGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(null);
		securityService.createPermissionGroup(permissionGroup);
		verify(permissionGroupDAO, times(1)).save(anyString());
	}	
	
	@Test
	public void testCreatePermissionGroupByPassingNull(){
		securityService.createPermissionGroup(null);
		verify(permissionGroupDAO, times(0)).save(anyString());
	}	
	
	@Test(expected=PermissionGroupAlreadyExistsException.class)
	public void testCreatePermissionGroupThatAlreadyExists(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.createPermissionGroup(permissionGroup);
		verify(permissionGroupDAO, times(0)).save(anyString());
	}
	
	@Test
	public void testDeletePermissionGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(null);
		securityService.deletePermissionGroup(permissionGroup.getPermissionGroupName());
		verify(permissionGroupDAO, times(0)).delete(any(PermissionGroup.class));
	}	
	
	@Test
	public void testDeletePermissionGroupByPassingNull(){
		securityService.deletePermissionGroup(null);
		verify(permissionGroupDAO, times(0)).delete(any(PermissionGroup.class));
	}	
	
	@Test
	public void testdeletePermissionGroupThatAlreadyExists(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.deletePermissionGroup(permissionGroup.getPermissionGroupName());
		verify(permissionGroupDAO, times(1)).delete(any(PermissionGroup.class));

	}

	@Test
	public void testAddPermissionsToGroupWhenPassedInGroupDoesNotExist(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(null);
		securityService.addPermissionsToGroup(permissionGroup.getPermissionGroupName(), permissionList);
		verify(permissionGroupDAO, times(1)).save(any(PermissionGroup.class));
	}	
	
	@Test
	public void testAddPermissionsToGroupWhenPassedInGroupExistsAndAlreadyContainsSamePermission(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.addPermissionsToGroup(permissionGroup.getPermissionGroupName(), permissionList);
		verify(permissionGroupDAO, times(0)).save(any(PermissionGroup.class));
	}

	@Test
	public void testAddPermissionsToGroupWhenPassedInGroupExistsAndNewPermissionIsPassedIn(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroupNew);
		securityService.addPermissionsToGroup(permissionGroupNew.getPermissionGroupName(), permissionListNew);
		verify(permissionGroupDAO, times(1)).save(any(PermissionGroup.class));
	}
	
	@Test(expected=UserDoesNotExistException.class)
	public void testAddGroupListToUserWhenPassedInUserDoesNotExist(){
		when(userDAO.findByUserName(anyString())).thenReturn(null);
		securityService.addGroupListToUser(user.getUserName(), permissionGroupList);
		verify(userDAO, times(0)).save(any(User.class));
	}
	
	
	@Test
	public void testAddGroupListToUserWhenPassedInUserExistsButAlreadyContainsSameGroups(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		securityService.addGroupListToUser(user.getUserName(), permissionGroupList);
		verify(userDAO, times(0)).save(any(User.class));
	}

	@Test
	public void testAddGroupListToUserWhenPassedInUserExistsAndNewGroupIsPassedIn(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		securityService.addGroupListToUser(user.getUserName(), permissionGroupListNew);
		verify(userDAO, times(0)).save(any(User.class));
	}
	
	@Test
	public void testReplacePermissionListForGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.replacePermissionListForGroup(permissionGroup.getPermissionGroupName(), permissionList);
		verify(permissionGroupDAO, times(1)).save(any(PermissionGroup.class));
	}
		
	
	@Test
	public void testRevokePermissionFromGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		when(permissionDAO.getPermissionByName(anyString())).thenReturn(permission);
		securityService.revokePermissionFromGroup(permissionGroup.getPermissionGroupName(), permission);
		org.junit.Assert.assertFalse(permissionGroup.getPermissionGroupPermissionList().contains(permission));
	}	
	

	@Test
	public void testRevokeAllPermissionsFromGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.revokeAllPermissionsFromGroup(permissionGroup.getPermissionGroupName());
		org.junit.Assert.assertNull(permissionGroup.getPermissionGroupPermissionList());
	}	
	
	@Test
	public void testReplaceGroupListForUser(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		securityService.replaceGroupListForUser(user.getUserName(), permissionGroupList);
		verify(userDAO, times(1)).save(any(User.class));
	}
	
	@Test
	public void testRevokeGroupFromUser(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.revokeGroupFromUser(permissionGroup.getPermissionGroupName(), user.getUserName());
		org.junit.Assert.assertFalse(user.getUserPermissionGroupList().contains(permissionGroup));
	}
	
	@Test
	public void testRevokeAllGroupsFromUser(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		securityService.revokeAllGroupsFromUser(user.getUserName());
		org.junit.Assert.assertNull(user.getUserPermissionGroupList());
	}
	
	@Test
	public void testGetPermissionListForGroup(){
		when(permissionGroupDAO.getPermissionGroupByName(anyString())).thenReturn(permissionGroup);
		securityService.getPermissionListForGroup(permissionGroup.getPermissionGroupName());
		org.junit.Assert.assertFalse(permissionGroup.getPermissionGroupPermissionList().isEmpty());
	}
	
	
	
	@Test
	public void testGetPermissionListForUser(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		MultiMap multiMap = securityService.getPermissionListForUser(user.getUserName());
		org.junit.Assert.assertNotNull(multiMap);
	}
	
	
}
