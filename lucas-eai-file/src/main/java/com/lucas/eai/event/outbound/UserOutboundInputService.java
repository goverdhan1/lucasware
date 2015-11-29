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

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.lucas.eai.dto.FieldDetailsDTO;
import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.eai.message.RecordType;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.services.eai.EaiFileConstants;
import com.lucas.services.eai.EventService;
import com.lucas.services.eai.MappingService;
import com.lucas.services.eai.MessageService;
import com.lucas.services.eai.TransformationService;
import com.lucas.services.user.UserService;
import com.lucas.services.util.CollectionsUtilService;

/**
 * This class is a lucas predefined service. This will read the transformed
 * message map it to the lucas predefined message and and persist to the lucas
 * db
 * 
 * @author Prafull
 * 
 */

@Component
public class UserOutboundInputService implements ApplicationContextAware {

	private static Logger LOG = LoggerFactory
			.getLogger(UserOutboundInputService.class);

	@Inject
	private EventService eventService;

	@Inject
	private MappingService mappingService;

	@Inject
	private MessageService messageService;

	@Inject
	private TransformationService transformationService;

	@Inject
	private UserService userService;

	private ApplicationContext applicationContext;

	public void processOutboundEvent(Message<Map<String, String>> message) {

		Long userId = Long.valueOf(message.getPayload().get(
				EaiFileConstants.KEY_USER_ID));

		String eventName = message.getPayload().get(
				EaiFileConstants.USER_LOGON_KEY);

		LOG.debug("UserOutboundInputService class called " + userId + " "
				+ eventName);
		Event event = eventService.getEventByName(eventName);
		User user = userService.findByUserID(userId);

		if (event != null && user != null) {
			// NOTE: In the future stories we might need to modify the logic for
			// iterating also
			// Get the valid event handler for the event
			EventHandler eventHandler = event.getEventEventHandlers()
					.iterator().next().getEventHandler();


			// Get the mapping for this event processing
			Mapping mapping = mappingService
					.getOutboundMappingByEventHandlerId(eventHandler
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
			genericMessageDTO.setEventHandlerId(eventHandler.getEventHandlerId());

			// Get the source message by id
			com.lucas.entity.eai.message.Message sourceMessage = messageService
					.getHydratedMessage(sourceMessageId);
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
			// Prepare the message and send to the channel

			// Get the message definition

			List<MessageDefinition> messageDefinitionList = new ArrayList<MessageDefinition>();
			messageDefinitionList.addAll(sourceMessage
					.getEaiMessageDefinitions());

			Map<String, FieldDetailsDTO> fields = new HashMap<String, FieldDetailsDTO>();

			for (MessageDefinition messageDefinition : CollectionsUtilService
					.nullGuard(messageDefinitionList)) {
				FieldDetailsDTO fieldDetailsDTO = new FieldDetailsDTO();
				fieldDetailsDTO.setMessageFieldType(messageDefinition
						.getMessageFieldType());
				fieldDetailsDTO.setMessageFieldSeq(messageDefinition
						.getMessageFieldSeq());
				try {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
							messageDefinition.getMessageFieldName(), User.class);
					if (propertyDescriptor != null) {
						Object value = propertyDescriptor.getReadMethod()
								.invoke(user);
						String stringValue = null;

						if (value != null) {
							stringValue = value.toString();
						}
						if (value instanceof Shift) {
							stringValue = ((Shift) value).getShiftId()
									.toString();
						}
						if (value instanceof SupportedLanguage) {
							stringValue = ((SupportedLanguage) value)
									.getLanguageCode();
						}
						fieldDetailsDTO.setMessageFieldValue(stringValue);
					}
				} catch (Exception e) {
					LOG.error("Error occured while accessing the user prperty "
							+ messageDefinition.getMessageFieldName(), e);
				}
				fields.put(messageDefinition.getMessageFieldName(),
						fieldDetailsDTO);
			}

			if (!fields.isEmpty()) {
				final MessageDetailsDTO detailsDTO = new MessageDetailsDTO();
				detailsDTO.setRecordType(RecordType.CSVDelimited);
				detailsDTO.setFields(fields);
				genericMessageDTO
						.setMessageDetails(new ArrayList<MessageDetailsDTO>() {
							{
								add(detailsDTO);
							}
						});
			}

			Message<GenericMessageDTO> eventMessage = MessageBuilder
					.withPayload(genericMessageDTO).build();
			
			channel.send(eventMessage);

		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		this.applicationContext = applicationContext;
	}

}
