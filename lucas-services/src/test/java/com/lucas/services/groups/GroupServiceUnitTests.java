/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.groups;

import com.lucas.dao.application.CodeLookupDAO;
import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.services.application.CodeLookupService;
import com.lucas.services.security.SecurityService;
import com.lucas.services.user.GroupService;
import com.lucas.services.user.GroupServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class, StringUtils.class})
public class GroupServiceUnitTests {

	@Mock
	private Appender mockAppender;

	private GroupService groupService;
	
	private List<String> permissionNameList = new ArrayList<>();

    @Mock
    private PermissionGroupDAO permissionGroupDAO;

    @Mock
    private PermissionDAO permissionDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    CodeLookupService codeLookupService;

    @Mock
    private SecurityService securityService;

	@Before
	public void setup(){

		groupService =  Mockito.spy(new GroupServiceImpl(permissionGroupDAO, permissionDAO,userDAO, securityService, codeLookupService));

        permissionNameList.add("admin");
        permissionNameList.add("picker");
        permissionNameList.add("supervisor");
        permissionNameList.add("warehouse-manager");

    }

    @Test
    public void testGetAllPermissionGroupNames() throws ParseException {
    	when(permissionGroupDAO.getPermissionGroupNames()).thenReturn(permissionNameList);
    	List<String> expectedPermissionList = groupService.getAllPermissionGroupNames();
        Assert.assertEquals(4, expectedPermissionList.size());
    }
}
