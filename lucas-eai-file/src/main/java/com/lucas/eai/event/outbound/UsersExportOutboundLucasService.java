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

package com.lucas.eai.event.outbound;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;

/**
 * This class is a lucas predefined service. This will read the transformed
 * message map it to the lucas predefined message and and persist to the lucas
 * db
 * 
 * @author Prafull
 * 
 */
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.eai.writer.LucasFileWriter;
import com.lucas.eai.writer.LucasFileWriterFactory;
import com.lucas.eai.writer.WriteParameters;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.services.eai.EventService;
import com.lucas.services.eai.MessageService;

@Component
public class UsersExportOutboundLucasService {

	private static Logger LOG = LoggerFactory
			.getLogger(UsersExportOutboundLucasService.class);

	@Inject
	private EventService eventService;
	
	@Inject
	private MessageService messageService;

	/**
	 * The method processes the message passed to it, retrieves the users
	 * details and stores in the database
	 * 
	 * @param message
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public void exportUser(Message<GenericMessageDTO> message)
			throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {
		LOG.debug("UsersExportOutboundLucasService class called "
				+ message.getPayload());
		
		GenericMessageDTO genericMessageDTO = message.getPayload();
		
		EventHandler eventHandler = eventService.getEventHandlerById(genericMessageDTO.getEventHandlerId());
		com.lucas.entity.eai.message.Message destinationMessage = messageService.getMessageById(genericMessageDTO.getDestinationMessageId());
		
		if(eventHandler!=null ){
			// Create the read parameters
						WriteParameters writeParameters = new WriteParameters.Builder()
								.dirPath(eventHandler.getEaiTransport().getRemoteDirectory())
								.fileNamePattern(eventHandler.getOutboundFilePattern())
								.fileType(destinationMessage.getMessageFormat().getName())
								.genericMessage(genericMessageDTO).build();

						LucasFileWriter writer = LucasFileWriterFactory.getFileWriter(
								destinationMessage.getMessageFormat(),
								destinationMessage.getMessageFieldDelimitedCharacter(),
								writeParameters);

						writer.write();

		}
	}


}
