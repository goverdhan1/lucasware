package com.lucas.alps.api;

import java.util.List;
import java.util.SortedMap;

import javax.inject.Inject;

import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.security.SecurityService;

/**
 * @Author John Humenik
 * @Product Lucas Systems, Inc.
 * Created By: John Humenik
 * Created On Date: 02/04/2015
 * This class creates a REST end point for any UI to access security level information, like permissions. 
 */

@Controller
// NOTE: No global controller is defined here, all end points are defined below
//@RequestMapping(value="/security")
public class SecurityController {

	// Setup the logger based upon the security controller
	private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

	// Create an instance of the security service to call the lucas-services layer
	private final SecurityService securityService;
	
    @Inject
    // Base constructor
	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/permissions")
	public @ResponseBody ApiResponse<SortedMap<String, List<String>>> getAllPermissions() {
		SortedMap<String, List<String>> permissionsMap = null;
		//MultiMap permissionsMap = null;
		ApiResponse<SortedMap<String, List<String>>> apiResponse = new ApiResponse<SortedMap<String, List<String>>>();
		try {
			// Invoke the get permissions interface in the service layer to return data
			permissionsMap = securityService.getAllPermissionsByCategories();
		} catch (Exception e) {
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}
		
		apiResponse.setData(permissionsMap);
		return apiResponse;
	}
}