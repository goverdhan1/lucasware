package com.lucas.services.admin;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.entity.process.AMPM;
import com.lucas.entity.user.User;
import com.lucas.services.util.DateTimeService;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceUnitTests {
	
	@Mock
	private DateTimeService dateTimeService;
	
	@InjectMocks
	private AdminServiceImpl adminService;
	
	@Test
	public void testGreetingForAM(){
		when(dateTimeService.getTimeOfDay(any(DateTime.class))).thenReturn(AMPM.AM);
		String greeting = adminService.determineGreeting();
		Assert.assertEquals(AdminServiceImpl.GREETING_GOOD_MORNING, greeting);
	}
	
	@Test
	public void testGreetingForPM(){
		when(dateTimeService.getTimeOfDay(any(DateTime.class))).thenReturn(AMPM.PM);
		String greeting = adminService.determineGreeting();
		Assert.assertEquals(AdminServiceImpl.GREETING_GOOD_EVENING, greeting);
	}
	
	@Test
	public void testNoGreeting(){
		when(dateTimeService.getTimeOfDay(any(DateTime.class))).thenReturn(null);
		String greeting = adminService.determineGreeting();
		Assert.assertEquals("", greeting);
	}
	
	@Test
	public void testAdminUsersListCount()
	{
		List<User> adminList = adminService.getAdminUsers();		
		Assert.assertEquals(4, adminList.size());
	}
	
	/**
	 * Assume the first element is always admin user.
	 */
	@Test
	public void testGetAdminUser()
	{
		User u = adminService.getAdminUsers().get(0);
		Assert.assertEquals("admin123", u.getUserName());
	}
}
