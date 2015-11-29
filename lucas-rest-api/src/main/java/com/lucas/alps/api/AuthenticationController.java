/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.security.TokenProvider;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.UserView;
import com.lucas.alps.viewtype.AuthenticationView;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.ui.UIService;
import com.lucas.services.user.UserService;
import com.lucas.services.util.HttpStatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Map;

@Controller
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	private final UserService userService;
	private final UIService uiService;
	private final TokenProvider tokenProvider;
	private final String STATUS = "failure";
	private final String MESSAGE = "Invalid Username or Password";
	private final String USERNAME = "username";
	private final String PASSWORD = "password";

	@Inject
	public AuthenticationController(UserService userService, TokenProvider tokenProvider, UIService uiService) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
		this.uiService = uiService;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ResponseView(AuthenticationView.class)
	@ResponseBody 
	public ApiResponse<UserView> authenticate(Locale locale, Model model,  
			@RequestBody Map<String,String> map) {

		logger.debug("Inside-AuthenticationController.java");

		String token = null;
		UserView userView = null;
		String username = map.get(USERNAME);
		String password = map.get(PASSWORD);
		ApiResponse<UserView> apiResponse = new ApiResponse<UserView>();

		User user = null;
		try {
			user = userService.findUserByPlainTextCredentials(username, password);
		} 
		catch (Exception e) {
			throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e); 
		}

		if (user != null) {
			userView = new UserView(user);
			token = tokenProvider.getToken(user);
			apiResponse.setToken(token);
			apiResponse.setData(userView);
		} else {
			apiResponse.setMessage(MESSAGE);
			apiResponse.setCode(HttpStatusCodes.ERROR_1001);
			apiResponse.setStatus(STATUS);
		}

		return apiResponse;
	}

 

}