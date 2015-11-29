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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.MessageSearchByColumnsView;
import com.lucas.alps.view.MessageSegmentView;
import com.lucas.alps.view.MessageView;
import com.lucas.alps.view.SegmentSearchByColumnsView;
import com.lucas.alps.viewtype.MessageDetailsView;
import com.lucas.alps.viewtype.MessageSearchByColumnsDetailsView;
import com.lucas.alps.viewtype.SaveMessageSegmentDetailsView;
import com.lucas.alps.viewtype.SegmentSearchByColumnsDetailsView;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.Segments;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.eai.MessageService;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * @author Naveen
 * 
 */
@Controller
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;
    private MessageSource messageSource;
    private final String MESSAGE_SEGMENT_GRID_WIDGET = "message-segment-grid-widget";
    
    @Inject
    public MessageController(MessageService messageService, MessageSource messageSource) {

        this.messageService = messageService;
        this.messageSource = messageSource;

    }
	    
	/**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/segments/segmentlist/search
     * sample JSON expected as input to this method
	{
		  "pageSize": 3,
		  "pageNumber": 0,
		  "searchMap": {
		    "segmentName": [
		      "Control"
		    ]
		  
		  },
		  "sortMap": {
		    "segmentName": "ASC"
		  }
		}
	
	//************************************** Response Json ************************************
	
	{
    "status": "success",
    "code": "200",
    "message": "Request processed successfully",
    "level": null,
    "uniqueKey": null,
    "token": null,
    "explicitDismissal": null,
    "data": {
        "totalRecords": 1,
        "grid": {
            "1": {
                "values": [
                    "Control"
                ]
            },
            "2": {
                "values": [
                    "Control records for transfer order"
                ]
            },
            "3": {
                "values": [
                    "125"
                ]
            }
        }
    }
}
	
	*/
	
	@ResponseView(SegmentSearchByColumnsDetailsView.class)
	@RequestMapping(method = RequestMethod.POST, value = "/segments/segmentlist/search")
	public @ResponseBody
	ApiResponse<SegmentSearchByColumnsView> getSegmentListBySearchAndSortCriteria(
			@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

		final ApiResponse<SegmentSearchByColumnsView> apiResponse = new ApiResponse<SegmentSearchByColumnsView>();
		List<Segments> segmentList = null;
		Long totalRecords = null;
		SegmentSearchByColumnsView segmentSearchBycolumnsView = null;
		try {
			segmentList = messageService
					.getSegmentListBySearchAndSortCriteria(searchAndSortCriteria);
			totalRecords = messageService
					.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
			segmentSearchBycolumnsView = new SegmentSearchByColumnsView(
					segmentList, totalRecords);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}
		apiResponse.setData(segmentSearchBycolumnsView);

		LOG.debug("Size{}", segmentList.size());
		return apiResponse;
	}
	
	/**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/messages/messagelist/search
     * sample JSON expected as input to this method
     * 
                  {
              "pageSize":4,
              "pageNumber":0,
                "searchMap": {
                  "messageName": [
                    "Lucas user export host"
                  ]
                
                },
                "sortMap": {
                  "messageName": "ASC"
                }
            }
       ***********************Response Json***************
                   {
                "status": "success",
                "code": "200",
                "message": "Request processed successfully",
                "level": null,
                "uniqueKey": null,
                "token": null,
                "explicitDismissal": null,
                "data": {
                    "totalRecords": 1,
                    "grid": {
                        "1": {
                            "values": [
                                "Lucas user export host"
                            ]
                        },
                        "2": {
                            "values": [
                                "User's export outbound host message"
                            ]
                        },
                        "3": {
                            "values": [
                                "Delimited"
                            ]
                        },
                        "4": {
                            "values": [
                                "No"
                            ]
                        },
                        "5": {
                            "values": [
                                "usersExportOutboundLucasServiceChannel"
                            ]
                        }
                    }
                }
            }
 	 * */
	
    @ResponseView(MessageSearchByColumnsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/messages/messagelist/search")
    public @ResponseBody ApiResponse<MessageSearchByColumnsView> getMessageListBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
        final ApiResponse<MessageSearchByColumnsView> apiResponse = new ApiResponse<MessageSearchByColumnsView>();
        Long totalRecords = null;
        List<Message> messageList;
        MessageSearchByColumnsView messageSearchByColumnsView;
        try {

            messageList = this.messageService.getMessageListBySearchAndSortCriteria(searchAndSortCriteria);
            totalRecords = this.messageService.getTotalCountForMessageSearchAndSortCriteria(searchAndSortCriteria);
            messageSearchByColumnsView = new MessageSearchByColumnsView(messageList, totalRecords);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
        apiResponse.setData(messageSearchByColumnsView);

        LOG.debug("Size{}", messageList.size());
        return apiResponse;
    }
    
    /**
     * GET  -> getMessageandMessageDefinition
     *
     * @param messageName
     * @return
     * @throws LucasRuntimeException
     * @throws Exception

     */
    @ResponseView(MessageDetailsView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/messages/{messageName}/definitions")
    public @ResponseBody
    ApiResponse<MessageView> getMessageAndMessageDefinition(@PathVariable String messageName) {

        final ApiResponse<MessageView> apiResponse = new ApiResponse<MessageView>();
        Message message = null;
        MessageView messageView = null;
        try {
            message = messageService.getHydratedMessageByName(messageName);
            messageView = new MessageView(message);
            apiResponse.setData(messageView);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        return apiResponse;
    }


	
	@ResponseView(SaveMessageSegmentDetailsView.class)
	@RequestMapping(method = RequestMethod.POST, value = "/segments/definitions/save")
	public @ResponseBody
	ApiResponse<Object> saveMessageSegment(
			@RequestBody MessageSegmentView messageSegmentView) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		Boolean isCreated = null;
		final Segments segment = messageSegmentView.getSegment();
		LOG.debug("**** MessageController.saveMessageSegment() **** Deserialise Segments object: ******* = "
				+ ToStringBuilder.reflectionToString(segment,
						ToStringStyle.MULTI_LINE_STYLE));

		try {
			isCreated = messageService.createSegment(segment);
            apiResponse.setStatus("success");
            apiResponse.setCode("1090");
            apiResponse.setLevel(Level.INFO);
            apiResponse.setData(isCreated);
		} catch (Exception e) {
            apiResponse.setData(false);
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1091"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1091");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
		return apiResponse;
	}
	
	
	/**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/segments/{segmentName}/definitions
     * Sample : http://[HostName]:[PortNumber]/[Context/lucas-api]/segments/Control/definitions
     * 
	//************************************** Response Json ************************************
	
	{
    "status": "success",
    "code": "200",
    "message": "Request processed successfully",
    "level": null,
    "uniqueKey": null,
    "token": null,
    "explicitDismissal": null,
    "data": {
        "segmentDescription": "Control records for transfer order",
        "segmentLength": 125,
        "segmentName": "Control",
        "segmentId": 1,
        "segmentDefinitions": [
            {
                "segmentFieldId": 5,
                "segmentFieldDescription": "Doc Dt (YYYYMMDD)",
                "segmentFieldEnd": 36,
                "segmentFieldLength": 8,
                "segmentFieldName": "DocDate",
                "segmentFieldSeq": 5,
                "segmentFieldStart": 29,
                "segmentFieldType": "DateTime",
                "segmentFieldValue": null,
                "segmentSegmentId": null
            },
            {
                "segmentFieldId": 3,
                "segmentFieldDescription": "TabName",
                "segmentFieldEnd": 18,
                "segmentFieldLength": 3,
                "segmentFieldName": "ManDt",
                "segmentFieldSeq": 3,
                "segmentFieldStart": 16,
                "segmentFieldType": "String",
                "segmentFieldValue": null,
                "segmentSegmentId": null
            },
            {
                "segmentFieldId": 1,
                "segmentFieldDescription": "Segment Identifier",
                "segmentFieldEnd": 5,
                "segmentFieldLength": 5,
                "segmentFieldName": "SegmentID",
                "segmentFieldSeq": 1,
                "segmentFieldStart": 1,
                "segmentFieldType": "SegID",
                "segmentFieldValue": "HH",
                "segmentSegmentId": null
            },
            {
                "segmentFieldId": 2,
                "segmentFieldDescription": "TabName",
                "segmentFieldEnd": 15,
                "segmentFieldLength": 10,
                "segmentFieldName": "TabName",
                "segmentFieldSeq": 2,
                "segmentFieldStart": 6,
                "segmentFieldType": "String",
                "segmentFieldValue": null,
                "segmentSegmentId": null
            },
            {
                "segmentFieldId": 4,
                "segmentFieldDescription": "Document Number",
                "segmentFieldEnd": 28,
                "segmentFieldLength": 10,
                "segmentFieldName": "DocNum",
                "segmentFieldSeq": 4,
                "segmentFieldStart": 19,
                "segmentFieldType": "Integer",
                "segmentFieldValue": null,
                "segmentSegmentId": null
            }
        ]
    }
}
	
	*/
	
	@ResponseView(SaveMessageSegmentDetailsView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/segments/{segmentName}/definitions")
    public @ResponseBody
    ApiResponse<MessageSegmentView> getSegment(@PathVariable("segmentName") String segmentName) {
        final ApiResponse<MessageSegmentView> apiResponse = new ApiResponse<MessageSegmentView>();
        try {
            final Segments segment = messageService.getSegment(segmentName);
            if (segment == null) {
                throw new LucasRuntimeException("Segment Definitions not Found for " + segment);
            }
            final MessageSegmentView segmentView = new MessageSegmentView(segment);
            apiResponse.setData(segmentView);
        } catch (Exception e) {
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1091"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1091");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }

	/**
	 * Method to delete segment 
	 * 
	 * @param messageSegmentView.segmentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/segments/delete")
	public @ResponseBody
	ApiResponse<Object> deleteSegment(
			@RequestBody MessageSegmentView messageSegmentView) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();

		Boolean isDeleted = null;
		Long segmentId = messageSegmentView.getSegmentId();
		LOG.debug("**** MessageController.deleteMessageSegment() **** Deserialise Segments object: ******* = "
				+ ToStringBuilder.reflectionToString(segmentId,
						ToStringStyle.MULTI_LINE_STYLE));
	try {
		isDeleted = messageService.deleteSegment(segmentId);
            apiResponse.setStatus("success");
            apiResponse.setCode("1092");
            apiResponse.setLevel(Level.INFO);
            apiResponse.setData(isDeleted);
		} catch (Exception e) {
            apiResponse.setData(false);
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1091"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1091");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
		return apiResponse;
	}

	/**
	 * getAllMessageNames() provides the functionality to fetch all available message names from DB
	 * 
	 * @return apiResponse
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/messages/names")
	public @ResponseBody
	ApiResponse<List<String>> getAllMessageNames() {
		LOG.debug("REST request to get all Message Names");
		final ApiResponse<List<String>> apiResponse = new ApiResponse<List<String>>();
		try {
			apiResponse.setData(messageService.getAllMessageNames());
		} catch (Exception e) {
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}
		return apiResponse;
	}
}
