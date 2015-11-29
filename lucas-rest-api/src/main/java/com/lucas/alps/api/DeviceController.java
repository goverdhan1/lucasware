/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.alps.api;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.DeviceMessageView;
import com.lucas.alps.viewtype.MobileMessageView;
import com.lucas.entity.device.DeviceMessageOption;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.device.DeviceService;

@Controller
@RequestMapping(value = "/mobileapp")
@SuppressWarnings({"rawtypes", "unchecked"})
public class DeviceController {


    private static final Logger LOG = LoggerFactory.getLogger(DeviceController.class);
    private final MessageSource messageSource;
    private final DeviceService deviceService;

    @Inject
    public DeviceController(MessageSource messageSource, DeviceService deviceService) {

        this.messageSource = messageSource;
        this.deviceService = deviceService;

    }


    /**
     * The controller method gives an end point to get device message option
     * 
     * @param deviceId
     * @return
     * @throws LucasRuntimeException
     * @throws Exception
     */

    @ResponseView(MobileMessageView.class)
    @RequestMapping(value = "/setoptions/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<DeviceMessageView> getDeviceSetOptions(@PathVariable String deviceId) throws LucasRuntimeException, Exception {

        final ApiResponse<DeviceMessageView> apiResponse = new ApiResponse<DeviceMessageView>();
        DeviceMessageOption deviceMsg = null;
        try {
            if (deviceId != null && !"".equals(deviceId)) {

                deviceMsg = this.deviceService.getMobileApplicationSetOptions(deviceId);
                apiResponse.setData(new DeviceMessageView(deviceMsg));
                apiResponse.setExplicitDismissal(Boolean.FALSE);
                apiResponse.setLevel(Level.INFO);
                apiResponse.setCode("1040");


            } else {
                apiResponse.setMessage(messageSource.getMessage("1038", new Object[] {deviceMsg}, LocaleContextHolder.getLocale()));
                apiResponse.setExplicitDismissal(Boolean.FALSE);
                apiResponse.setLevel(Level.INFO);
                apiResponse.setCode("1038");
            }
        } catch (Exception e) {
            apiResponse.setMessage(messageSource.getMessage("1039", new Object[] {deviceMsg}, LocaleContextHolder.getLocale()));
            apiResponse.setExplicitDismissal(Boolean.FALSE);
            apiResponse.setLevel(Level.INFO);
            apiResponse.setCode("1039");
        }

        return apiResponse;
    }



}
