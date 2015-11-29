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
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.EventHandlerView;
import com.lucas.alps.viewtype.EventHandlerDetailView;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.eai.EventHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 7/8/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for EAI EventHandler.
 */
@Controller
public class EventHandlerController {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerController.class);

    private EventHandlerService eventHandlerService;
    private MessageSource messageSource;

    @Inject
    public EventHandlerController(final MessageSource messageSource,
                                  final EventHandlerService eventHandlerService) {
        this.messageSource = messageSource;
        this.eventHandlerService = eventHandlerService;
    }


    /**
     * getEventHandlerByName() provide the functionality for getting the
     * the event Handler from db by using EventHandlerService and EventHandlerDAO internally
     *
     * @return the instance of EventHandler
     * @throws Exception
     * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName(String, Boolean)
     */
    @ResponseView(EventHandlerDetailView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/eventhandlers/{eventHandlerName}")
    public
    @ResponseBody
    ApiResponse<EventHandlerView> getEventHandlerByName(@PathVariable("eventHandlerName") String eventHandlerName) throws Exception {
        final ApiResponse<EventHandlerView> apiResponse = new ApiResponse<EventHandlerView>();
        try {
            if (eventHandlerName != null && !eventHandlerName.isEmpty()) {
                final EventHandler eventHandler = this.eventHandlerService.getEventHandlerByName(eventHandlerName, Boolean.TRUE);
                if (eventHandler != null) {
                    LOG.info("EventHandler is Found in Db ");
                    //EventHandler is not empty
                    apiResponse.setCode("1105");
                    apiResponse.setStatus("success");
                    apiResponse.setData(new EventHandlerView(eventHandler));
                    apiResponse.setMessage(messageSource.getMessage("1105"
                            , new Object[]{eventHandlerName}
                            , LocaleContextHolder.getLocale()));
                } else {
                    LOG.info("EventHandler isn't Found in DB ");
                    //EventHandler is not found
                    apiResponse.setCode("1106");
                    apiResponse.setStatus("failure");
                    apiResponse.setMessage(messageSource.getMessage("1106"
                            , new Object[]{eventHandlerName}
                            , LocaleContextHolder.getLocale()));
                }
            } else {
                LOG.info("In Sufficient Input for getting EventHandler  ");
                //input for getting EventHandler is not sufficient
                apiResponse.setCode("1107");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1107"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
            }

        } catch (Exception e) {
            LOG.error("Exception Generated while fetching EventHandler from db ", e);
            apiResponse.setCode("1108");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1108"
                    , new Object[]{}
                    , LocaleContextHolder.getLocale()));
        }
        return apiResponse;
    }


    /**
     * saveEventHandler() provide the functionality for persisting the EventHandler into the db
     * it uses internally below mentioned method.
     * @see com.lucas.services.eai.EventHandlerService#saveEventHandler(com.lucas.entity.eai.event.EventHandler)
     *
     * @param eventHandlerView is accepted by this rest end point
     * @return the status of the operation which is wrapped into the ApiResponse
     * @throws LucasRuntimeException
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eventhandlers/save")
    public
    @ResponseBody
    ApiResponse<Object> saveEventHandler(@RequestBody EventHandlerView eventHandlerView) throws LucasRuntimeException, Exception {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        try {
            if (eventHandlerView != null && eventHandlerView.getEventHandler() != null) {
                final EventHandler eventHandler = eventHandlerView.getEventHandler();
                final boolean result = this.eventHandlerService.saveEventHandler(eventHandler);
                if (result) {
                    apiResponse.setMessage(messageSource.getMessage("1109"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                    apiResponse.setCode("1109");
                    apiResponse.setData(result);
                } else {
                    apiResponse.setMessage(messageSource.getMessage("1110"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("failure");
                    apiResponse.setCode("1110");
                    apiResponse.setData(result);
                }
            } else {
                throw new LucasRuntimeException("EventHandler Information is Null or InSufficient ");
            }
        } catch (Exception e) {
            apiResponse.setData(false);
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1111"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1111");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }


}
