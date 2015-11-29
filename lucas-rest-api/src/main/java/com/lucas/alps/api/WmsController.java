/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.user.WmsUser;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.user.WmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value="/wms")
public class WmsController {

	private static final Logger LOG = LoggerFactory.getLogger(WmsController.class);
	private final WmsUserService wmsService;

    @Inject
	public WmsController(WmsUserService wmsService) {
		this.wmsService = wmsService;
	}

    /**
     * GET  /users/:userId -> get the wms_user record by Id
     */
    @RequestMapping(value = "/users/{userId}",
            method = RequestMethod.GET)
    public ApiResponse<WmsUser> getOneById(@PathVariable Long userId) {
        LOG.debug("REST request to get Wms : {}", userId);

        final ApiResponse<WmsUser> apiResponse = new ApiResponse<WmsUser>();

        WmsUser wmsUser = null;

        try {
            wmsUser = wmsService.findOneUserById(userId);
            apiResponse.setData(wmsUser);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;
    }


    /**
     * GET  users/all -> get all the wms user records.
     */
    @RequestMapping(value = "/users",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<WmsUser>> getAllWmsUsers() {
        LOG.debug("REST request to get all WMS users");
        final ApiResponse <List <WmsUser>> apiResponse = new ApiResponse <List<WmsUser>>();

        List<WmsUser> wmsUserList = null;

        try {
            wmsUserList = wmsService.findAllWmsUsers();
            apiResponse.setData(wmsUserList);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        LOG.debug("Size{}", wmsUserList.size());
        return apiResponse;
    }

}