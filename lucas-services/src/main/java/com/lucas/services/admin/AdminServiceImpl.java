/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.lucas.entity.process.AMPM;
import com.lucas.entity.user.User;
import com.lucas.services.util.DateTimeService;

@Service
public class AdminServiceImpl implements AdminService {
	public final static String GREETING_GOOD_MORNING = "Good Morning!";
	public final static String GREETING_GOOD_EVENING = "Good Evening!";

	private final DateTimeService dateTimeService;

	@Inject
	public AdminServiceImpl(DateTimeService dateTimeService) {
		this.dateTimeService = dateTimeService;
	}

	@Override
	public String determineGreeting() {
		DateTime currentTime = new DateTime();
		String greeting = "";
		AMPM ampm = dateTimeService.getTimeOfDay(currentTime);

		if (AMPM.AM.equals(ampm)) {
			greeting = GREETING_GOOD_MORNING;
		} else if (AMPM.PM.equals(ampm)) {
			greeting = GREETING_GOOD_EVENING;
		}

		return greeting;
	}

	@Override
	public List<User> getAdminUsers() {
		List<User> adminUsersList = new ArrayList<User>();

		User user1 = new User();
		user1.setFirstName("Admin User");
		user1.setUserName("admin123");

		User user2 = new User();
		user2.setFirstName("Alankar Yannam");
		user2.setUserName("alankar");

		User user3 = new User();
		user3.setFirstName("Anand Hoysala");
		user3.setUserName("anand");

		User user4 = new User();
		user4.setFirstName("Pankaj Tandon");
		user4.setUserName("pankaj");

		adminUsersList.add(user1);
		adminUsersList.add(user2);
		adminUsersList.add(user3);
		adminUsersList.add(user4);
		
		return adminUsersList;
	}

}
