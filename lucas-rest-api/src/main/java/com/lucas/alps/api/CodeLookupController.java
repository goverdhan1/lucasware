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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.exception.CodeLookupDoesNotExistException;
import com.lucas.exception.CodeLookupInvalidException;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.application.CodeLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/application")
public class CodeLookupController {

	private static final Logger LOG = LoggerFactory.getLogger(CodeLookupController.class);
	private final CodeLookupService codeLookupService;
	private final MessageSource messageSource;

    @Inject
	public CodeLookupController(CodeLookupService codeLookupService, MessageSource messageSource) {
		this.codeLookupService = codeLookupService;
		this.messageSource = messageSource;
	}


    /**
     * Get the lookup codes from the passed in list
     * @author DiepLe
     * @param codeLookupList
     * @return Map of lookup codes
     */
    @RequestMapping(method = RequestMethod.POST, value = "/codes")
    @ResponseBody
    @JsonRawValue
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //@JsonProperty(value = "jsondata")
    public ApiResponse<Map<String, Object>> getCodeLookupData(@RequestBody final List<String> codeLookupList) {
        LOG.debug("REST request to get lookup codes");
        final ApiResponse <Map<String, Object>> apiResponse = new ApiResponse <Map<String, Object>>();
        Map<String, Object> resultObj = null;

        if (codeLookupList != null && codeLookupList.size() != 0) {
            try {
                resultObj = codeLookupService.getCodeLookupData(codeLookupList);
            } catch (CodeLookupDoesNotExistException e){
                LOG.debug("ApplicationController: exception CodeLookupDoesNotExistException caught");
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1024");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1024", codeLookupList.toArray(), LocaleContextHolder.getLocale()));
                return apiResponse;
            } catch (CodeLookupInvalidException e){
                LOG.debug("ApplicationController: exception CodeLookupInvalidException caught");
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1028");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1028", codeLookupList.toArray(), LocaleContextHolder.getLocale()));
                return apiResponse;
            }
            catch (Exception e) {
                LOG.debug("ApplicationController: exception Exception caught");
                /*apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("500");
                apiResponse.setStatus("failure");
                return apiResponse;*/
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        else {
            LOG.debug("ApplicationController: empty lookup codes passed in");
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1027");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1027", codeLookupList.toArray(), LocaleContextHolder.getLocale()));
            return apiResponse;
        }

        LOG.debug("Request processed successfully");
        apiResponse.setLevel(Level.INFO);
        apiResponse.setStatus("success");
        apiResponse.setData(resultObj);

        return apiResponse;
    }

}