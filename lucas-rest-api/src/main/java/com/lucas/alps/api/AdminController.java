/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.entity.user.User;
import com.lucas.services.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private final AdminService adminService;
	
	@Inject
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String greeting(Locale locale, Model model) {
		logger.info("Welcome admin! The client locale is {}.", locale); 
		String message = adminService.determineGreeting();
		model.addAttribute("message", message );
		 
		List<User> adminList = adminService.getAdminUsers();
		model.addAttribute("adminUsersList", adminList );
		
		return "admin";
	}
}
