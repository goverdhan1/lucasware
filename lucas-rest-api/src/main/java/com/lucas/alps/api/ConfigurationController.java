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
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.ui.configuration.CustomerConfiguration;
import com.lucas.entity.ui.configuration.LucasConfiguration;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.ui.configuration.CustomerConfigurationService;
import com.lucas.services.ui.configuration.LucasConfigurationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:54 PM
 * This Class provide the implementation for the functionality of..
 */
@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController {
    @Inject
    private CustomerConfigurationService customerConfigurationService;


    @Inject
    private LucasConfigurationService lucasConfigurationService;

    /*
      url = http://[HostName]:[PortNumber]/[Context/lucas-api]/configuration/image/customer-logo/{Caterpillar}
     */
    @RequestMapping(produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.GET, value = "/image/customer-logo/{name}")
    public
    @ResponseBody
    byte[] getCustomerLogo(@PathVariable String name) {
        byte[] logoImage = new byte[0];
        if (name != null && name.trim().length() > 0) {
            final CustomerConfiguration customerConfiguration =
                    this.customerConfigurationService.getByCustomerConfigurationByName(name);
            logoImage = customerConfiguration.getClientLogo();
        } else {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR);
        }
        return logoImage;
    }


    /*
     url = http://[HostName]:[PortNumber]/[Context/lucas-api]/configuration/image/lucas-logo}
    */
    @RequestMapping(produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.GET, value = "/image/lucas-logo")
    public
    @ResponseBody
    byte[] getLucasLogo() {
        byte[] logoImage = new byte[0];
        try {
            final LucasConfiguration lucasConfiguration =
                    this.lucasConfigurationService.getLucasConfigurationByUserId(4L);
            logoImage = lucasConfiguration.getLucasLogo();
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR);
        }
        return logoImage;
    }


    /*
    <form action="configuration/image/customer-logo" method="post" enctype="multipart/form-data">
           <input type="file" name="file" size="50" />
           <br />
           <input type="submit" value="Upload File" />
    </form>
    */
    @RequestMapping(method = RequestMethod.POST, value = "/image/customer-logo")
    public ApiResponse<Object> saveCustomerLogo(@RequestPart("file") MultipartFile file
            , @RequestBody(required=false) CustomerConfiguration customerConfiguration ) {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        if (!file.isEmpty()) {
            try {
                final byte[] bytes = file.getBytes();
                customerConfiguration.setClientLogo(bytes);
                this.customerConfigurationService.saveCustomerConfiguration(customerConfiguration);
                apiResponse.setExplicitDismissal(Boolean.TRUE);
                apiResponse.setMessage("Updated Information");
                apiResponse.setLevel(Level.INFO);
            } catch (Exception e) {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR);
            }
        }
        return apiResponse;
    }

}
