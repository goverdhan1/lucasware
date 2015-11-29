/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.about.About;
import com.lucas.services.about.AboutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping(value="/about", method = RequestMethod.GET)
public class AboutController {
	
	private static final Logger log = LoggerFactory.getLogger(AboutController.class);
	private AboutService aboutService;
		
	@Inject
	public AboutController(AboutService aboutService) {
		this.aboutService = aboutService;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/buildinfo")
	public ApiResponse<About> response() {
	    // Create ApiResponse and provide Build Information using the AboutService
		ApiResponse<About> apiResponse = new ApiResponse<About>();
		apiResponse.setData(aboutService.getAbout());
		
		log.debug("***About Controller - ApiResponse BuildNumber: " + apiResponse.getData().getBuildNumber());
		log.debug("***About Controller - ApiResponse BuildTimestamp: " + apiResponse.getData().getBuildTimestamp());
		return apiResponse;
	}
}