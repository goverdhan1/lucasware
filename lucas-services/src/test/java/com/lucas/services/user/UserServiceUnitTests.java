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
package com.lucas.services.user;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.ui.CanvasDAO;
import com.lucas.dao.ui.CanvasLayoutDAO;
import com.lucas.dao.ui.OpenUserCanvasDAO;
import com.lucas.dao.ui.WidgetDAO;
import com.lucas.dao.ui.WidgetInstanceDAO;
import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.entity.user.WmsUser;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.UserDoesNotExistException;
import com.lucas.services.application.CodeLookupService;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.ui.UIService;
import com.lucas.services.ui.UIServiceImpl;
import com.lucas.services.util.MultiCompare;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class, StringUtils.class})
public class UserServiceUnitTests {

	@Mock
	private StandardPBEStringEncryptor stringEncryptor;	

	@Mock
	private UserDAO userDAO;
	
	@Mock
	private Appender mockAppender;
	
	@Mock
	private PermissionGroupDAO permissionGroupDAO;	
	
	@Mock
	private PermissionDAO permissionDAO;

	@Mock
	private WidgetDAO widgetDAO;

	@Mock
	private CanvasDAO canvasDAO;
	
	@Mock	
	private CanvasLayoutDAO canvasLayoutDAO;

	@Mock
	private WidgetInstanceDAO widgetInstanceDAO;

	@InjectMocks
	private UIServiceImpl uiServiceImpl;
	
	@Mock
	private UIService uiService;

    @Mock
    private ShiftService shiftService;

    @Mock
    private WmsUserService wmsUserService;

    @Mock
    private SupportedLanguageService supportedLanguageService;

    @Mock
    private MultiCompare multiCompare;

    @Mock
    private SecurityService securityService;
	
    @Mock
    private OpenUserCanvasDAO openUserCanvasDAO;

    @InjectMocks
	private ReloadableResourceBundleMessageSource messageSource;

    @Mock
	private UserService userService;

    @Mock
    private CodeLookupService codeLookupService;

    @Mock
    private GroupService groupService;
    
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    
    private static final String pageSize = "20";
	private static final String pageNumber = "0";
	private static String USERNAME = "jack123";
    private static String FIRSTNAME = "Jack";
	private static String PERMISSIONGROUPNAME = "aPermissionGroupName";
	private static String PERMISSIONNAME = "aPermissionName";
	private static String PLAIN_PASSWORD = "secret";
	private static String HASHED_PASSWORD = "aHashedPassword";
	private User user;
	private OpenUserCanvas openUserCanvas;
	private List<User> userList;
	private List<PermissionGroup> permissionGroupList;
	private List<Permission> permissionList;
	private PermissionGroup permissionGroup;
	private Permission permission;
	
	@Before
	public void setup(){
		mockStatic(StandardPBEStringEncryptor.class);
		mockStatic(StringUtils.class);
		
		userService =  Mockito.spy(new UserServiceImpl(userDAO,
				permissionGroupDAO, 
				permissionDAO,
				uiService, 
				messageSource,
                shiftService,
                wmsUserService,
                supportedLanguageService,
                securityService,
                groupService, 
                openUserCanvasDAO,
                codeLookupService,
                passwordEncoder
				));
		
		user = new User();
		user.setUserId(4L);
		user.setUserName(USERNAME);
        user.setFirstName(FIRSTNAME);
		user.setHashedPassword(HASHED_PASSWORD);
		userList = new ArrayList<User>();
		userList.add(user);
		permissionGroup = new PermissionGroup();
		permissionGroup.setPermissionGroupId(1L);
		permissionGroup.setPermissionGroupName(PERMISSIONGROUPNAME);

        final SupportedLanguage language = new SupportedLanguage();
        language.setLanguageCode("EN_US");
        language.setAmdLanguage(true);
        language.setHhLanguage(true);
        language.setJ2uLanguage(true);
        language.setU2jLanguage(true);
        user.setAmdLanguage(language);
        user.setU2jLanguage(language);
        user.setJ2uLanguage(language);
        user.setHhLanguage(language);

        java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

        final Shift shift = new Shift();
        shift.setCreatedDateTime(sqlDate);
        shift.setUpdatedDateTime(sqlDate);
        shift.setShiftName("day");
        shift.setShiftId(2L);
        shift.setStartTime("9 AM");
        shift.setEndTime("6 PM");
        user.setShift(shift);

        final WmsUser wmsUser = new WmsUser();
        wmsUser.setId(1L);
        wmsUser.setHostLogin("system");
        wmsUser.setHostHashedPassword("PASSWORD");
        wmsUser.setCreatedByUserName("system");
        wmsUser.setCreatedDateTime(sqlDate);
        wmsUser.setUpdatedByUserName("system");
        wmsUser.setUpdatedDateTime(sqlDate);

        user.setWmsUser(wmsUser);
        
        Canvas canvas = new Canvas();
        canvas.setCanvasId(2L);
        user.setActiveCanvas(canvas);

        List<OpenUserCanvas> openUserCanvases = new ArrayList<OpenUserCanvas>();

        OpenUserCanvas openCanvas1 = new OpenUserCanvas();
        Canvas canvas1 = new Canvas();
        canvas1.setCanvasId(4L);
        canvas1.setCreatedByUserName(USERNAME);
        canvas1.setUpdatedByUserName(USERNAME);
        openCanvas1.setDisplayOrder(1);
        openCanvas1.setCanvas(canvas1);
        openCanvas1.setCreatedByUserName(USERNAME);
        openCanvas1.setUpdatedByUserName(USERNAME);
        openCanvas1.setCreatedDateTime(new Date());
        openCanvas1.setUpdatedDateTime(new Date());

        openUserCanvases.add(openCanvas1);

        OpenUserCanvas openCanvas2 = new OpenUserCanvas();
        Canvas canvas2 = new Canvas();
        canvas2.setCanvasId(4L);
        canvas2.setCreatedByUserName(USERNAME);
        canvas2.setUpdatedByUserName(USERNAME);
        openCanvas2.setDisplayOrder(1);
        openCanvas2.setCanvas(canvas1);
        openCanvas2.setCreatedByUserName(USERNAME);
        openCanvas2.setUpdatedByUserName(USERNAME);
        openCanvas2.setCreatedDateTime(new Date());
        openCanvas2.setUpdatedDateTime(new Date());

        user.setOpenUserCanvases(openUserCanvases);
        user.setUpdatedByUserName(USERNAME);
        user.setUpdatedDateTime(new Date());

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
		
				
	}
	
	@Test
	public void testFindByUserName(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		User retrievedUser = userService.findByUserName(USERNAME);
		Assert.notNull(retrievedUser);
		Assert.isTrue(retrievedUser.getId() == user.getId());
	}
	
	@Test
	public void testGetUserWithPermission(){
		when(userDAO.getUser(anyString())).thenReturn(user);
		User retrievedUser = userService.getUser(eq(USERNAME), anyBoolean());
		Assert.notNull(retrievedUser);
		Assert.isTrue(retrievedUser.getPermissionsSet().size() == 0);
	}
	
	@Test
	public void testGetUser(){
		when(userService.getUser(USERNAME,true)).thenReturn(user);
		User retrievedUser = userService.getUser(USERNAME);
		Assert.notNull(retrievedUser);
		Assert.isTrue(retrievedUser.getId() == user.getId());
		
	}
		
	@Test
	public void testFindUserByPlainTextCredentialsWithAMatch(){
		when(userDAO.getUser(anyString())).thenReturn(user);
		when(passwordEncoder.matches(PLAIN_PASSWORD, user.getHashedPassword())).thenReturn(Boolean.TRUE);
		User retrievedUser = userService.findUserByPlainTextCredentials(USERNAME, PLAIN_PASSWORD);
		Assert.notNull(retrievedUser);
		Assert.isTrue(retrievedUser.getId() == user.getId());
	}

	@Test
	public void testFindUserByPlainTextCredentialsWithNoMatch(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.matches(PLAIN_PASSWORD, user.getHashedPassword())).thenReturn(Boolean.FALSE);
		User retrievedUser = userService.findUserByPlainTextCredentials(USERNAME, PLAIN_PASSWORD);
		Assert.isNull(retrievedUser);
	}

	@Test
	public void testFindUserByHashedCredentials(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(StringUtils.equals(anyString(), anyString())).thenReturn(true);
		User retrievedUser = userService.findUserByHashedCredentials(USERNAME, HASHED_PASSWORD);
		Assert.notNull(retrievedUser);
		Assert.isTrue(retrievedUser.getId() == user.getId());
	}

	@Test
	public void testIsAuthenticateByHashedCredentials(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(StringUtils.equals(anyString(), anyString())).thenReturn(true);
		boolean boo = userService.isAuthenticateByHashedCredentials(USERNAME, HASHED_PASSWORD);
		Assert.isTrue(boo);
	}

	@Test
	public void testIsAuthenticateByPlainTextCredentials() {
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.matches(PLAIN_PASSWORD, user.getHashedPassword())).thenReturn(Boolean.TRUE);
		boolean boo = userService.isAuthenticateByPlainTextCredentials(USERNAME, PLAIN_PASSWORD);
		Assert.isTrue(boo);
	}
	
	@Test
	public void testUpdateNonNullUser() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
		when(userDAO.findByUserId(anyLong())).thenReturn(user);
		userService.updateUser(user);
		verify(userDAO, times(1)).save(any());
	}
	
	@Test (expected=UserDoesNotExistException.class)
	public void testUpdateNullUser() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
		when(userDAO.findByUserName(anyString())).thenReturn(null);
		userService.updateUser(user);
		verify(userDAO, never()).save(any());
	}

/*	@Test (expected=LucasBusinessException.class)
	public void testCreateUserThatExists(){
		when(userDAO.findByUserName(anyString())).thenReturn(user);
		when(messageSource.getMessage(anyString(), new Object[] {anyString()}, new Locale(anyString()))).thenReturn("User already exists with username jack123. Cannot create user!");
		userService.createUser(user);
		verify(userDAO, never()).save(any());
	}*/	

	@Test
	public void testCreateUserThatDoesNotExist(){
		when(userDAO.findByUserName(anyString())).thenReturn(null);
		when(stringEncryptor.encrypt(PLAIN_PASSWORD)).thenReturn(HASHED_PASSWORD);
        try {
            userService.createUser(user);
            verify(userDAO, times(1)).save(any());
        }
        catch (Exception e) {
        }
	}
	
	@Test
    @Rollback
	public void testDeleteUserByIdThatDoesExist(){
		when(userDAO.load(anyLong())).thenReturn(user);
		Map result = userService.deleteUser(user.getUserId());
        Assert.isTrue(result.containsValue("Deleted Successfully"));
	}
	
	@Test
    @Rollback
	public void testDeleteUserByUsernameThatDoesExist(){
        try {
            userService.createUser(user);
        }
        catch (Exception e) {
        }
		when(userDAO.findByUserName(anyString())).thenReturn(user);
        Map result = userService.deleteUser(user.getUserName());
        Assert.isTrue(result.containsValue("Deleted Successfully"));
	}

	@Test
    @Rollback
	public void testDeleteUserByUserThatDoesExist(){
		when(userDAO.load(any(User.class))).thenReturn(user);
        Map result = userService.deleteUser(user.getUserId());
        Assert.isTrue(result.containsValue("Deleted Successfully"));
	}
	
	@Test
	public void testDeleteUserByIdThatDoesNotExist(){
		when(userDAO.load(anyLong())).thenReturn(null);
        Map result = userService.deleteUser((Long)null);
        Assert.isTrue(result.containsValue("BLANK_USERNAME_ERROR_CODE"));
	}
	
	@Test
	public void testDeleteUserByIdWhenUserDoesNotExist(){
		when(userDAO.load(anyLong())).thenReturn(null);
        Map result = userService.deleteUser(user.getUserId());
        Assert.isTrue(result.containsValue("INVALID_USERNAME_ERROR_CODE"));
	}
	
	@Test
	public void testDeleteUserByUsernameThatDoesNotExist(){
        Map result = userService.deleteUser((String)null);
        Assert.isTrue(result.containsValue("BLANK_USERNAME_ERROR_CODE"));
	}
	
	@Test
	public void testDeleteUserByUsernameWhenUserDoesNotExist(){
		when(userDAO.findByUserName(anyString())).thenReturn(null);
        Map result = userService.deleteUser(user.getUserName());
        Assert.isTrue(result.containsValue("INVALID_USERNAME_ERROR_CODE"));
	}
	
	@Test
	public void testDeleteUserByUserThatDoesNotExist(){
        Map result = userService.deleteUser((User)null);
        Assert.isTrue(result.containsValue("BLANK_USERNAME_ERROR_CODE"));
	}	
	@Test
	public void testGetUsercount(){
		when(userDAO.getUserCount()).thenReturn(10l);;
		userService.getUserCount();
		verify(userDAO, times(1)).getUserCount();
	}	

	@Test
	public void testGetUserList(){
		when(userDAO.getUserList(anyString(), anyString())).thenReturn(userList);;
		userService.getUserList(pageSize, pageNumber);
		verify(userDAO, times(1)).getUserList(pageSize, pageNumber);
	}
	
	@Test
	public void testGetFullyHydratedUser() throws JsonParseException, JsonMappingException, IOException {
		//when(userService.findUserByPlainTextCredentials(USERNAME, PLAIN_PASSWORD)).thenReturn(user);
		when(userDAO.getUser(anyString())).thenReturn(user);
		when(StringUtils.equals(anyString(), anyString())).thenReturn(true);
		when(stringEncryptor.decrypt(HASHED_PASSWORD)).thenReturn(PLAIN_PASSWORD);
		
		List<Canvas> cList = new ArrayList<Canvas>();
		cList.add(new Canvas("test"));

		List<DefaultWidgetInstance> wList = new ArrayList<DefaultWidgetInstance>();
		wList.add(new DefaultWidgetInstance());
		when(userDAO.findByUserName(USERNAME)).thenReturn(user);
		//when(uiService.getWidgetInstanceListForUsersFavoriteCanvases(USERNAME)).thenReturn(wList);
		userService.getFullyHydratedUser(USERNAME, PLAIN_PASSWORD);
		verify(userDAO, times(1)).getUser(user.getUserName());
	}

    @Test
    public void testGetUserListBySearchAndSortCriteria() throws ParseException,Exception {
        when(this.userDAO.getNewBySearchAndSortCriteria(new SearchAndSortCriteria())).thenReturn(this.userList);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        this.userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        verify(this.userDAO, times(1)).getNewBySearchAndSortCriteria(searchAndSortCriteria);
    }
    
	@SuppressWarnings("unchecked")
	@Test
    public void testGetUserFromUserNameList() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException,ParseException, LucasRuntimeException {
	    		
		List<String> userNameList = new ArrayList<String>();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("admin123");
		userList.add(user1);
        User user2 = new User();
        user1.setUserName("jill123");
        userList.add(user2);
        User user3 = new User();  
        user1.setUserName("jack123");
        userList.add(user3);
    	
    	userList.get(0).setTitle("MRS");
    	userList.get(1).setTitle("MISS");
    	userList.get(2).setTitle("MR");
    	when(userDAO.getUser(anyString())).thenReturn(user);
    	Map<String, String> userEditableFields = userService.getUserMutablePairMap(userNameList);
    	verify(userService, atLeastOnce()).getUserListFromUserNameList(anyList()); 
    	verify(multiCompare, atLeastOnce()).getMutablePairMap(anyList(), anyList());
	}
	
	@Test
	public void testGetPermissionGroupsBySearchAndSortCriteria() throws ParseException{
		when(this.permissionGroupDAO.getPermissionGroupsBySearchAndSortCriteria(any(SearchAndSortCriteria.class))).thenReturn(this.permissionGroupList);
		when(this.permissionDAO.getUserPermissionsByPermissionGroupName(anyString())).thenReturn(this.permissionList);
		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		this.userService.getPermissionGroupsBySearchAndSortCriteria(searchAndSortCriteria);
		verify(this.permissionGroupDAO, times(1)).getPermissionGroupsBySearchAndSortCriteria(any(SearchAndSortCriteria.class));
		verify(this.permissionDAO, atLeastOnce()).getUserPermissionsByPermissionGroupName(anyString());
	}
	
	@Test
	public void testGetPermissionGroupsBySearchAndSortCriteriaWithNullPGList() throws ParseException{
		when(this.permissionGroupDAO.getPermissionGroupsBySearchAndSortCriteria(any(SearchAndSortCriteria.class))).thenReturn(null);
		when(this.permissionDAO.getUserPermissionsByPermissionGroupName(anyString())).thenReturn(this.permissionList);
		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		this.userService.getPermissionGroupsBySearchAndSortCriteria(searchAndSortCriteria);
		verify(this.permissionGroupDAO, times(1)).getPermissionGroupsBySearchAndSortCriteria(any(SearchAndSortCriteria.class));
		verify(this.permissionDAO, times(0)).getUserPermissionsByPermissionGroupName(anyString());
	}

    @Test
    public void testUpdateUserActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        when(userDAO.findByUserId(anyLong())).thenReturn(user);
        userService.updateUserActiveCanvas(user);
        verify(userDAO, times(1)).save(any());
    }
    
    @Test (expected=UserDoesNotExistException.class)
    public void testUpdateNullUserActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserName(anyString())).thenReturn(null);
        userService.updateUserActiveCanvas(user);
        verify(userDAO, never()).save(any());
    }
    
    @Test
    public void testUpdateNonNullUserActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserId(anyLong())).thenReturn(user);
        userService.updateUserActiveCanvas(user);
        verify(userDAO, times(1)).save(any());
    }
    
    @Test (expected=UserDoesNotExistException.class)
    public void testUpdateNullUserNonActiveCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserName(anyString())).thenReturn(null);
        user.setActiveCanvas(null);
        userService.updateUserActiveCanvas(user);
        verify(userDAO, never()).save(any());
    }


    @Test
    public void testUpdateUserOpenCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        when(userDAO.findByUserId(anyLong())).thenReturn(user);
        userService.updateUserOpenCanvas(user);
        verify(userDAO, times(1)).save(any());
    }
    
    @Test (expected=UserDoesNotExistException.class)
    public void testUpdateNullUserOpenCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserName(anyString())).thenReturn(null);
        when(openUserCanvasDAO.findAllOpenUserCanvases()).thenReturn(null);
        userService.updateUserOpenCanvas(user);
        verify(userDAO, never()).save(any());
    }
    
    @Test
    public void testUpdateNonNullUserOpenCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserId(anyLong())).thenReturn(user);
        when(openUserCanvasDAO.findOneOpenUserCanvasId(anyLong(),anyLong())).thenReturn(openUserCanvas);
        userService.updateUserOpenCanvas(user);
        verify(userDAO, times(1)).save(any());
    }
    
    @Test (expected=UserDoesNotExistException.class)
    public void testUpdateNullUserNonOpenCanvas() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception{
        when(userDAO.findByUserName(anyString())).thenReturn(null);
        when(openUserCanvasDAO.findOneOpenUserCanvasId(anyLong(),anyLong())).thenReturn(null);
        userService.updateUserOpenCanvas(user);
        verify(userDAO, never()).save(any());
    }

    @Test
    public void testGetAllUsersProperties() throws Exception{
        when(userService.getUser(USERNAME,true)).thenReturn(user);
        List<String> propList = new ArrayList<>();
        propList.add("userName");
        propList.add("firstName");

        userDAO.getAllUsersProperties(propList);
        verify(this.userDAO, times(1)).getAllUsersProperties(propList);
    }
}
