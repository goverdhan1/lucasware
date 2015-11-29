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

package com.lucas.eai.event.transformation;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.eai.dto.FieldDetailsDTO;
import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.services.eai.EaiFileConstants;
import com.lucas.services.eai.MessageService;
import com.lucas.services.eai.TransformationService;
import com.lucas.services.util.CollectionsUtilService;

/**
 * The expression transformation service. It applies the transformation logic on
 * the input message according to the transformation rules.
 * 
 * @author Prafull
 * 
 */

@Component
public class UserExpressionTransformer implements ApplicationContextAware {

	@Inject
	private TransformationService transformationService;

	@Inject
	private MessageService messageService;

	private static Logger LOG = LoggerFactory
			.getLogger(UserExpressionTransformer.class);
	ApplicationContext applicationContext;

	/**
	 * The method accept the message passed to it and applies the transformation
	 * logic defined in the database for the message
	 * 
	 * @param message
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @throws ParseException
	 */
	public void processMessageTransform(Message<GenericMessageDTO> message)

			throws JsonParseException, JsonMappingException, IOException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IntrospectionException, ParseException {
		LOG.debug("UserExpressionTransformer processing class called, #of messages to be processed are "
				+ message.getPayload().getMessageDetails().size());

		GenericMessageDTO incomingPayload = message.getPayload();

		List<MessageDetailsDTO> messageDetailsDTO = incomingPayload
				.getMessageDetails();

		Long destinationMessageId = message.getPayload()
				.getDestinationMessageId();

		Long eventHandlerId = message.getPayload().getEventHandlerId();
		MappingPath currentTransformationPath = message.getPayload()
				.getTransformationPath();

		// Get all message fields

		Message<GenericMessageDTO> messageAfterTransformation = null;
		Set<TransformationDefinition> transformationDefinitions = null;
		if (currentTransformationPath != null
				&& currentTransformationPath.getToTransformation() != null) {
			// Retrieve the correct transformation
			final Transformation transformation = transformationService
					.getTransformationById(currentTransformationPath
							.getToTransformation().getTransformationId());

			// Declare the DTOs going to be there in the outbound message after
			// the transformation
			List<MessageDetailsDTO> outboundDetailsDTOs = new ArrayList<MessageDetailsDTO>();
			if (transformation != null) {
				transformationDefinitions = transformation
						.getEaiTransformationDefinitions();
				// Apply the transformation
				for (MessageDetailsDTO inboundMessageDetailsDTO : CollectionsUtilService
						.nullGuard(messageDetailsDTO)) {
					Map<String, FieldDetailsDTO> incomingFieldsMap = inboundMessageDetailsDTO
							.getFields();
					MessageDetailsDTO outboundMessageDetailsDTO = new MessageDetailsDTO();
					Map<String, FieldDetailsDTO> outgoingFieldsMap = new HashMap<String, FieldDetailsDTO>();
					for (Map.Entry<String, FieldDetailsDTO> entry : incomingFieldsMap
							.entrySet()) {

						if (transformationExists(entry.getKey(),
								transformationDefinitions)) {
							outgoingFieldsMap.put(entry.getKey(),
									entry.getValue());
						}

					}

					// Check for the variable fields needed to be set before
					// sending the message
					transformVariableFields(outgoingFieldsMap,
							transformationDefinitions);
					outboundMessageDetailsDTO.setFields(outgoingFieldsMap);
					outboundMessageDetailsDTO
							.setRecordType(inboundMessageDetailsDTO
									.getRecordType());
					outboundDetailsDTOs.add(outboundMessageDetailsDTO);
				}
			}

			GenericMessageDTO payloadAfterTransformation = new GenericMessageDTO();
			payloadAfterTransformation.setMessageDetails(outboundDetailsDTOs);
			
			payloadAfterTransformation.setDestinationMessageId(destinationMessageId);

				payloadAfterTransformation.setEventHandlerId(eventHandlerId);
			// Build the message to be sent after the transformation
			messageAfterTransformation = MessageBuilder.withPayload(
					payloadAfterTransformation).build();
			
			

		}

		// find out the correct next channel
		QueueChannel nextChannel = null;
		MappingPath nextTransformationPath = incomingPayload
				.getNextTransformation();

		if (nextTransformationPath != null) {
			if (nextTransformationPath.getToTransformation() != null) {
				Transformation transformation = transformationService
						.getTransformationById(nextTransformationPath
								.getToTransformation().getTransformationId());
				nextChannel = applicationContext.getBean(
						transformation.getTransformationChannel(),
						QueueChannel.class);
			} else {
				// do the mapping on the previously built message
				// find the right channel from the destination message
				com.lucas.entity.eai.message.Message destinationMessage = messageService
						.getMessageById(destinationMessageId);
				performMapping(messageAfterTransformation,
						transformationDefinitions);
				nextChannel = applicationContext.getBean(
						destinationMessage.getLucasPredefinedService(),
						QueueChannel.class);
			}
		}

		if (nextChannel != null) {
			LOG.debug("*************next channel after transformtion ***************** "
					+ nextChannel);

			nextChannel.send(messageAfterTransformation);
		}
	}

	/**
	 * The method applies the mapping between the fields got after the
	 * transformation and the fields that should be there in the outgoing
	 * message for the predefined service to process.
	 * 
	 * @param messageAfterTransformation
	 * @param transformationDefinitions
	 */
	private void performMapping(
			Message<GenericMessageDTO> messageAfterTransformation,
			Set<TransformationDefinition> transformationDefinitions) {
		LOG.debug("*************performing mapping ***************** ");
		GenericMessageDTO genericMessageDTO = messageAfterTransformation
				.getPayload();
		List<MessageDetailsDTO> dtos = genericMessageDTO.getMessageDetails();
		for (TransformationDefinition transformationDefinition : CollectionsUtilService
				.nullGuard(transformationDefinitions)) {
			// Get the mapping to be applied
			MessageDefinition messageDefinition = messageService
					.getMessageDefinitionByTransformationDefinitionId(transformationDefinition
							.getTransformationDefinitionId());

			for (MessageDetailsDTO dto : CollectionsUtilService.nullGuard(dtos)) {

				// Map the field
				Map<String, FieldDetailsDTO> fieldsMap = dto.getFields();
				FieldDetailsDTO fieldDetailsDTO = fieldsMap
						.remove(transformationDefinition
								.getTransformationFieldName());
				if (messageDefinition != null) {
					String messageDefinitionFieldName = messageDefinition
							.getMessageFieldName();
					fieldDetailsDTO
							.setMessageFieldName(messageDefinitionFieldName);
					fieldDetailsDTO.setMessageFieldType(messageDefinition
							.getMessageFieldType());
					fieldDetailsDTO.setMessageFieldSeq(messageDefinition
							.getMessageFieldSeq());
					fieldsMap.put(messageDefinitionFieldName, fieldDetailsDTO);
				}
			}
		}
	}

	/**
	 * The method creates and appends the variable fields needed to be sent in
	 * the out going message after applying the transformation
	 * 
	 * @param outgoingFieldsMap
	 * @param transformationDefinitions
	 */
	private void transformVariableFields(
			Map<String, FieldDetailsDTO> outgoingFieldsMap,
			Set<TransformationDefinition> transformationDefinitions) {

		for (TransformationDefinition definition : CollectionsUtilService
				.nullGuard(transformationDefinitions)) {
			
			//Need to work only on the variable fields 
			if (definition.getTransformationFieldVar() == Boolean.TRUE) {
				FieldDetailsDTO detailsDTO = new FieldDetailsDTO();
				detailsDTO.setMessageFieldName(definition
						.getTransformationFieldName());
				detailsDTO.setMessageFieldType(definition
						.getTransformationFieldType());
				detailsDTO.setMessageFieldSeq(definition
						.getTransformationFieldSeq());
				//Get and set the value of the filed after transformation 
				detailsDTO.setMessageFieldValue(processTransform(
						definition.getTransformationExpression(),
						outgoingFieldsMap));
				outgoingFieldsMap.put(definition.getTransformationFieldName(),
						detailsDTO);
			}

		}

	}

	/**
	 * The method checks and return whether a transformation definition exists
	 * for this message field
	 * 
	 * @param key
	 * @param transformationDefinitions
	 * @return
	 */
	private boolean transformationExists(String key,
			Set<TransformationDefinition> transformationDefinitions) {
		for (TransformationDefinition transformationDefinition : CollectionsUtilService
				.nullGuard(transformationDefinitions)) {
			if (key.equals(transformationDefinition
					.getTransformationFieldName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The method performs actual transformation for example expression
	 * transformations like substring and concatenation
	 * 
	 * @param expression
	 * @param fieldName
	 */
	private String processTransform(String expression,
			Map<String, FieldDetailsDTO> outgoingFieldsMap) {

		String result = "";
		String evaluate = "";
		String[] exprArr = null;

		if (expression != null) {
			exprArr = expression
					.split(EaiFileConstants.EXPRESSION_SPLIT_PATTERN);
			if (expression.contains(EaiFileConstants.EXPRESSION_SUBSTRING_NAME)) {
				evaluate = EaiFileConstants.EXPRESSION_SUBSTRING_NAME;
			}

			if (expression.contains(EaiFileConstants.EXPRESSION_CONCAT_NAME)) {
				evaluate = EaiFileConstants.EXPRESSION_CONCAT_NAME;
			}
		}
		else{
			
			LOG.error("The transformation expression is null");
		}
		

		switch (evaluate) {

		case "substring":
			LOG.debug("Substring expression operation started");
			Pattern pattern = Pattern
					.compile(EaiFileConstants.SUBSTRING_DOUBLE_DIGIT_PATTERN);
			Matcher matcher = pattern.matcher(expression);
			String fieldName = exprArr[1];
			int from = Integer.parseInt(exprArr[2]);
			String manipulation = outgoingFieldsMap.get(fieldName)
					.getMessageFieldValue().trim();
			if (matcher.find()) {
				int to = Integer.parseInt(exprArr[3]);
				result = manipulation.substring(from - 1, to);
			} else {
				result = manipulation.substring(from);
			}
			break;

		case "concat":
			LOG.debug("Concatenation expression operation started");
			StringBuilder fieldValue = new StringBuilder(outgoingFieldsMap.get(
					exprArr[1]).getMessageFieldValue());

			result = fieldValue
					.append(" ")
					.append(outgoingFieldsMap.get(exprArr[2])
							.getMessageFieldValue()).toString();

			break;
		}

		return result;

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		this.applicationContext = applicationContext;
	}
}
