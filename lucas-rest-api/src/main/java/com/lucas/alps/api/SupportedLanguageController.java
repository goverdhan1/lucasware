/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.user.SupportedLanguageService;
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
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value="/languages")
public class SupportedLanguageController {

	private static final Logger LOG = LoggerFactory.getLogger(SupportedLanguageController.class);
	private final SupportedLanguageService supportedLanguageService;

    @Inject
	public SupportedLanguageController(SupportedLanguageService supportedLanguageService) {
		this.supportedLanguageService = supportedLanguageService;
	}

    /**
     * GET  /:languageCode -> get the Language record by Id
     */
    @RequestMapping(value = "/{languageCode}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ApiResponse<SupportedLanguage> getOneLanguageByCode(@PathVariable String languageCode) {
        LOG.debug("REST request to get Language : {}", languageCode);

        final ApiResponse <SupportedLanguage> apiResponse = new ApiResponse <SupportedLanguage>();

        SupportedLanguage language = null;

        try {
            language = supportedLanguageService.findOneLanguageByCode(languageCode);
            apiResponse.setData(language);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;

    }


    /**
     * GET  -> get all the language records.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<SupportedLanguage>> getAllLanguages() {
        LOG.debug("REST request to get all languages");

        final ApiResponse <List <SupportedLanguage>> apiResponse = new ApiResponse <List<SupportedLanguage>>();

        List<SupportedLanguage> languageList = null;

        try {
            languageList = supportedLanguageService.findAllLanguages();
            apiResponse.setData(languageList);
            LOG.debug("Size{}", languageList.size());
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;

    }


}