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

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.EventHandlerView;
import com.lucas.alps.view.EventSearchByColumnsView;
import com.lucas.alps.view.EventView;
import com.lucas.alps.view.UserView;
import com.lucas.alps.viewtype.EventDetailsView;
import com.lucas.alps.viewtype.EventSearchByColumnsDetailsView;
import com.lucas.entity.eai.event.Event;
import com.lucas.exception.EventAlreadyExistsException;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.eai.EventService;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * The controller API class for the event domain. This class would contain all
 * the endpoints to support operations regarding the event domain.
 * 
 * @author Prafull
 * 
 */
@Controller
public class EventController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventController.class);
	private EventService eventService;
	private QueueChannel userOutboundInputChannel;
	private final MessageSource messageSource;
	
	@Inject
	public EventController(EventService eventService,
			QueueChannel userOutboundInputChannel, MessageSource messageSource) {
		this.eventService = eventService;
		this.userOutboundInputChannel = userOutboundInputChannel;
		this.messageSource = messageSource;
	}
	
	 @RequestMapping(value="/users/logon", method = RequestMethod.POST)
	    public @ResponseBody ApiResponse<Object> userLogon(@RequestBody UserView user)
	            {
	    			ApiResponse<Object> response = null;
	    			eventService.startUserLogonEvent(user.getUserId(), userOutboundInputChannel);
	    			
	    			return response;
	            }
	 
	/**
	 * The controller endpoint method. This endpoint returns the data in a
	 * columnar way for the queried search and sort criteria
	 * 
	 * @param searchAndSortCriteria
	 * @return
	 */
	@ResponseView(EventSearchByColumnsDetailsView.class)
	@RequestMapping(method = RequestMethod.POST, value = "/events/eventlist/search")
	@ResponseBody
	public ApiResponse<EventSearchByColumnsView> getEventListBySearchAndSortCriteria(
			@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
		

		final ApiResponse<EventSearchByColumnsView> apiResponse = new ApiResponse<EventSearchByColumnsView>();
		List<Event> eventList = null;
		Long totalRecords = null;
		EventSearchByColumnsView eventSearchBycolumnsView = null;
		try {
			eventList = eventService
					.getEventListBySearchAndSortCriteria(searchAndSortCriteria);
			totalRecords = this.eventService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
			eventSearchBycolumnsView = new EventSearchByColumnsView(eventList, totalRecords);
		} catch (Exception e) {
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}
		apiResponse.setData(eventSearchBycolumnsView);

		LOG.debug("Size{}", eventList.size());
		return apiResponse;
	}
	 /**
	 * saveEvent() provide the functionality to create/update an event
	 *
	 * @param EventView eventView is accepted as the post request parameter
	 * @return apiResponse
	 */
	 @RequestMapping(method = RequestMethod.POST, value="/events/save")
	 public @ResponseBody ApiResponse<Object> saveEvent(@RequestBody EventView eventView) {
		 ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		 boolean flag = Boolean.TRUE;
	     final Event event = eventView.getEvent();
	     if (event.getEventId() != null) {
			 flag = Boolean.FALSE;
		 }
	     LOG.debug("**** EventController.saveEvent() **** Deserialise event object: ******* = " + ToStringBuilder.reflectionToString(event, ToStringStyle.MULTI_LINE_STYLE));
	     
	     try {
	            eventService.saveEvent(event);
	        } catch (EventAlreadyExistsException ex) {
	            LOG.debug("Exception thrown...", ex.getMessage());
	            apiResponse.setExplicitDismissal(Boolean.FALSE);
	            apiResponse.setLevel(Level.ERROR);
	            apiResponse.setCode("1082");
	            apiResponse.setStatus("failure");
	            apiResponse.setData("");
	            apiResponse.setMessage(messageSource.getMessage("1082", new Object[] {event.getEventName()}, LocaleContextHolder.getLocale()));
	            LOG.error(apiResponse.getMessage());
	            return apiResponse;
	       } catch (Exception ex) {
	            LOG.debug("Exception thrown...", ex.getMessage());
	            apiResponse.setExplicitDismissal(Boolean.FALSE);
	            apiResponse.setLevel(Level.ERROR);
	            apiResponse.setCode("1021");
	            apiResponse.setStatus("failure");
	            apiResponse.setData("");
	            apiResponse.setMessage(messageSource.getMessage("1021", new Object[]{event.getEventName()}, LocaleContextHolder.getLocale()));
	            LOG.error(apiResponse.getMessage());
	            return apiResponse;
	        }

	     apiResponse.setExplicitDismissal(Boolean.FALSE);
		 apiResponse.setLevel(Level.INFO);
	     if (flag) {
				apiResponse.setCode("1081");		
				apiResponse.setMessage(messageSource.getMessage("1081", new Object[] {event.getEventName()}, LocaleContextHolder.getLocale()));
	     } else {
				apiResponse.setCode("1084");		
				apiResponse.setMessage(messageSource.getMessage("1084", new Object[] {event.getEventName()}, LocaleContextHolder.getLocale()));
	     }
			LOG.debug(apiResponse.getMessage());
			return apiResponse;
	 }
	 
	/**
	 * deleteEvent() provide the functionality to delete an event
	 *
	 * @param EventView eventView is accepted as the post request parameter
	 * @return apiResponse
	*/
	@RequestMapping(method = RequestMethod.POST, value = "/events/delete")
	public @ResponseBody
	ApiResponse<Object> deleteEvent(@RequestBody EventView eventView) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();

		try {
			eventService.deleteEventById(eventView.getEventId());
		} catch (Exception ex) {
			LOG.debug("Exception thrown...", ex.getMessage());
			apiResponse.setExplicitDismissal(Boolean.FALSE);
			apiResponse.setLevel(Level.ERROR);
			apiResponse.setCode("1021");
			apiResponse.setStatus("failure");
			apiResponse.setData("");
			apiResponse.setMessage(messageSource.getMessage("1021",
					new Object[] { eventView.getEventId() },
					LocaleContextHolder.getLocale()));
			LOG.error(apiResponse.getMessage());
			return apiResponse;
		}

		apiResponse.setExplicitDismissal(Boolean.FALSE);
		apiResponse.setLevel(Level.INFO);
		apiResponse.setCode("1083");
		apiResponse.setMessage(messageSource.getMessage("1083",
				new Object[] { eventView.getEventId() },
				LocaleContextHolder.getLocale()));
		LOG.debug(apiResponse.getMessage());
		return apiResponse;
	}
	
	/**
	 * getEvent() provide the functionality to get an event details
	 *
	 * @param String eventName is accepted as the get request parameter
	 * @return apiResponse warped in EventView
	*/
	@RequestMapping(method = RequestMethod.GET, value="/events/{eventName}")
	@ResponseView(EventDetailsView.class)
	public @ResponseBody ApiResponse<EventView> getEvent(@PathVariable String eventName) {
		final ApiResponse<EventView> apiResponse = new ApiResponse<EventView>();
		try {
		final Event event = eventService.getEventByName(eventName);
		if (event == null) {
			throw new LucasRuntimeException("Event not Found for " + eventName);
		}
		apiResponse.setData(new EventView(event));
		} catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1088"
                        , new Object[]{ eventName }
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1088");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
		return apiResponse;
	}
	
	/**
	 * deleteEventHandler() provide the functionality to delete an event handler
	 * 
	 * @param eventHandlerView as post request parameter
	 * @return apiResponse
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eventhandlers/delete")
	 public @ResponseBody ApiResponse<Object> deleteEventHandler(@RequestBody EventHandlerView eventHandlerView) {
	 	ApiResponse<Object> apiResponse = new ApiResponse<Object>();
	 	boolean result = Boolean.FALSE;
	 	try {
	 		result = eventService.deleteEventHandlerById(eventHandlerView.getEventHandlerId());
	 	} catch (Exception ex) {
	 		LOG.debug("Exception thrown...", ex.getMessage());
	 		apiResponse.setExplicitDismissal(Boolean.FALSE);
	 		apiResponse.setLevel(Level.ERROR);
	 		apiResponse.setCode("1021");
	 		apiResponse.setStatus("failure");
	 		apiResponse.setData("");
	 		apiResponse.setMessage(messageSource.getMessage("1021",
	 				new Object[] { eventHandlerView.getEventHandlerId() },
	 				LocaleContextHolder.getLocale()));
	 		LOG.error(apiResponse.getMessage());
	 		return apiResponse;
	 	}
	 
	 	apiResponse.setExplicitDismissal(Boolean.FALSE);
	 	apiResponse.setLevel(Level.INFO);
	 	apiResponse.setData(result);
	 	if (result) {
		 	apiResponse.setCode("1089");
		 	apiResponse.setMessage(messageSource.getMessage("1089",
		 			new Object[] { eventHandlerView.getEventHandlerId() },
		 			LocaleContextHolder.getLocale()));
	 	} else {
		 	apiResponse.setCode("2009");
		 	apiResponse.setMessage(messageSource.getMessage("2009",
		 			new Object[] { eventHandlerView.getEventHandlerId() },
		 			LocaleContextHolder.getLocale()));
	 	}
	 	LOG.debug(apiResponse.getMessage());
	 	return apiResponse;
	 }
}
