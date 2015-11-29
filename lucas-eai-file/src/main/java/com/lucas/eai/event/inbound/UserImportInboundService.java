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

package com.lucas.eai.event.inbound;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Component;

import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.eai.reader.LucasFileReader;
import com.lucas.eai.reader.LucasFileReaderFactory;
import com.lucas.eai.reader.ReadParameters;
import com.lucas.eai.util.FileUtil;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.services.eai.EventService;
import com.lucas.services.eai.MappingService;
import com.lucas.services.eai.MessageService;
import com.lucas.services.eai.TransformationService;

/**
 * This class is the first callback service getting file read by the inbound
 * adapter
 * 
 * @author Prafull
 * 
 */

@Component
public class UserImportInboundService implements ApplicationContextAware {

	@Inject
	private EventService eventService;

	@Inject
	private MappingService mappingService;

	@Inject
	private MessageService messageService;

	@Inject
	private TransformationService transformationService;

	private static Logger LOG = LoggerFactory
			.getLogger(UserImportInboundService.class);
	ApplicationContext applicationContext;

	private Map<String, String> eventNameAndFilePatternMap;

	/**
	 * Initializing the event name and file pattern map.
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		eventNameAndFilePatternMap = eventService
				.getEventNameAndInboundFilePattern();
	}

	public void processFile(File file) {

		LOG.debug("UserImportInboundService called for the file "
				+ file.getName());

		Event event = getEventByFileName(file.getName());

		if (event != null) {
			// NOTE: In the future stories we might need to modify the logic for
			// iterating also
			// Get the valid event handler for the event
			EventHandler eventHandler = event.getEventEventHandlers()
					.iterator().next().getEventHandler();

			// Get the mapping for this event processing
			Mapping mapping = mappingService
					.getInboundMappingByEventHandlerId(eventHandler
							.getEventHandlerId());
			// Get the source message id for this event
			Long sourceMessageId = mapping.getSourceMessage().getMessageId();

			// Get the destination message id for this event
			Long destinationMessageId = mapping.getDestinationMessage().getMessageId();

			// Get all the processing units for this event to be processed
			List<MappingPath> mappingPaths = mappingService
					.getMappingPathByMappingName(mapping.getMappingName());

			// Prepare the generic message DTO. Keep message details empty for
			// time being
			// Use builder pattern
			GenericMessageDTO genericMessageDTO = new GenericMessageDTO();

			genericMessageDTO.setSourceMessageId(sourceMessageId);
			genericMessageDTO.setDestinationMessageId(destinationMessageId);
			genericMessageDTO.addAllTransformationPaths(mappingPaths);

			// Get the source message by id
			Message sourceMessage = messageService
					.getMessageById(sourceMessageId);
			// Get the suitable reader based upon the message format and
			// delimiter type

			// Get the next transformation

			Transformation transformation = transformationService
					.getTransformationById(genericMessageDTO
							.getNextTransformation().getToTransformation().getTransformationId());

			// Get the queue channel for the next transformation
			QueueChannel channel = applicationContext.getBean(
					transformation.getTransformationChannel(),
					QueueChannel.class);
			// Create the read parameters
			ReadParameters readParameters = new ReadParameters.Builder()
					.filePath(file.getAbsolutePath()).channel(channel)
					.message(sourceMessage).genericMessage(genericMessageDTO)
					.build();

			LucasFileReader reader = LucasFileReaderFactory.getFileReader(
					sourceMessage.getMessageFormat(),
					sourceMessage.getMessageFieldDelimitedCharacter(),
					readParameters);

			reader.read();

		}

	}

	/**
	 * The method returns the event object based upon the inbound file name
	 * 
	 * @param name
	 * @return
	 */
	private Event getEventByFileName(String name) {
		String eventName = null;
		for (Map.Entry<String, String> entry : eventNameAndFilePatternMap
				.entrySet()) {
			if (FileUtil.matches(name, entry.getValue())) {
				eventName = entry.getKey();
				break;
			}
		}

		if (eventName != null) {
			return eventService.getEventByName(eventName);
		}

		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		this.applicationContext = applicationContext;
	}

}
