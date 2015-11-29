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

import com.lucas.dao.user.UserDAO;
import com.lucas.dao.user.UserPreferencesDAO;
import com.lucas.entity.user.User;
import com.lucas.entity.user.UserPreferences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;

import java.io.IOException;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesServiceUnitTests {

    private static final Logger log = LoggerFactory.getLogger(UserPreferencesServiceUnitTests.class);

	@Mock
	private UserPreferencesDAO userPreferencesDAO;

    @InjectMocks
    private UserPreferencesServiceImpl userPreferencesService;

    @Mock
    private UserDAO userDao;

    private UserPreferences userPreferences;

    private Long JACK_USER_ID = 4L;
    private String DATE_FORMAT = "DD-MM-YYYY";
    private String TIME_FORMAT = "24HR";
    private Long DATA_REFRESH_FREQUENCY = 120L;

	public UserPreferencesServiceUnitTests() {
	}

	@Before
	public void setup() throws Exception {

        userPreferencesDAO = mock(UserPreferencesDAO.class);
        userPreferencesService = new UserPreferencesServiceImpl(userDao, userPreferencesDAO);

        userPreferences = new UserPreferences();
        userPreferences.setDateFormat(DATE_FORMAT);
        userPreferences.setTimeFormat(TIME_FORMAT);
        userPreferences.setDataRefreshFrequency(DATA_REFRESH_FREQUENCY);

    }

	@Test
	public void testGetUserPreferencesById() {
        Throwable e = null;
		when(userPreferencesDAO.findOneUserPreferenceById(anyLong())).thenReturn(userPreferences);
        try {
            userPreferencesService.findOneUserPreferencesById(JACK_USER_ID);
        } catch (Throwable ex) {
            log.debug("UserPreferencesServiceUnitTests.testGetUserPreferencesByUserId: Yep Exception did throw as expected");
            e = ex;
        }

        Assert.notNull(userPreferences, "testGetUserPreferencesByUserId: user preferences is Null");

        verify(userPreferencesDAO, times(1)).findOneUserPreferenceById(anyLong());
	}

	@Test
	public void testGetUserPreferencesByNonExistingId() throws IOException {
		when(userPreferencesDAO.findOneUserPreferenceById(anyLong())).thenReturn(null);
        UserPreferences userPref = userPreferencesService.findOneUserPreferencesById(null);
        Assert.isNull(userPref, "testGetUserPreferencesByNonExistingId: user preferences is Not Null");
	}

}
