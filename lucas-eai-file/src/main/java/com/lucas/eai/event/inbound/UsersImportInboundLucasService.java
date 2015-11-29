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

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;

import com.lucas.eai.dto.FieldDetailsDTO;
import com.lucas.eai.dto.MessageDetailsDTO;
/**
 * This class is a lucas predefined service. This will read the transformed
 * message map it to the lucas predefined message and and persist to the lucas
 * db
 * 
 * @author Prafull
 * 
 */
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.services.eai.EaiFileConstants;
import com.lucas.services.user.UserService;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.util.DateUtils;

@Component
public class UsersImportInboundLucasService {

	private static Logger LOG = LoggerFactory
			.getLogger(UsersImportInboundLucasService.class);

	@Inject
	private UserService userService;

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
	public void importUser(Message<GenericMessageDTO> message)
			throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {
		LOG.debug("UsersImportInboundLucasService class called "
				+ message.getPayload());

		List<MessageDetailsDTO> messageDetailsDTO = new ArrayList<MessageDetailsDTO>();
		messageDetailsDTO = message.getPayload().getMessageDetails();
		// Create the user object from the message passed in
		for (MessageDetailsDTO messageDetails : CollectionsUtilService
				.nullGuard(messageDetailsDTO)) {
			User user = new User();
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(user.getClass()).getPropertyDescriptors();
			Map<String, FieldDetailsDTO> fieldsMap = messageDetails.getFields();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				FieldDetailsDTO fieldsDetailsDTO = fieldsMap
						.get(propertyDescriptor.getName());
				if (fieldsDetailsDTO != null) {
					// fill in the user properties values
					populateUser(user, propertyDescriptor, fieldsDetailsDTO);
				}
			}
			try {
				userService.createUser(user);
			} catch (Exception e) {
				
				LOG.error("Error occurred while creating the using during user import ", e);
			}
		}

	}

	/**
	 * The method populates the user object properties with the help of the
	 * message fields sent in the message
	 * 
	 * @param user
	 * @param propertyDescriptor
	 * @param fieldsDetailsDTO
	 */
	private void populateUser(User user, PropertyDescriptor propertyDescriptor,
			FieldDetailsDTO fieldsDetailsDTO) {
		try {
			
			String fieldValue = fieldsDetailsDTO.getMessageFieldValue();
			
			String value = (fieldValue
					.equalsIgnoreCase(EaiFileConstants.VALUE_NULL)) ? null
					: fieldValue;
			Method method = propertyDescriptor.getWriteMethod();
			StringBuffer parameterType = new StringBuffer(propertyDescriptor
					.getPropertyType().getName());
			String parameterClassName = parameterType
					.substring(
							parameterType
									.lastIndexOf(EaiFileConstants.PERIOD_DELIMITER) + 1)
					.toString();
			switch (parameterClassName) {

			case EaiFileConstants.CLASS_NAME_SUPPORTED_LANGUAGE:

				SupportedLanguage supportedLanguage = new SupportedLanguage();
				supportedLanguage.setLanguageCode(value);
				method.invoke(user, supportedLanguage);

				break;

			case EaiFileConstants.CLASS_NAME_DATE:
				
				Date date = DateUtils.parseDate(value);
				method.invoke(user, date);
				
				break;

			case EaiFileConstants.CLASS_NAME_SHIFT:

				Shift shift = new Shift();
				shift.setShiftId(Long.valueOf(value));
				method.invoke(user, shift);
				break;

			case EaiFileConstants.CLASS_NAME_BOOLEAN:
				
				Boolean booleanValue = null;
				
				if(value != null){
					booleanValue = (value.equals("1"))?Boolean.TRUE:Boolean.FALSE;
				}
				
				method.invoke(user, booleanValue);
				break;

			default:
				LOG.debug("None of the parameter types qualify, assuming that the type is the string type");
				method.invoke(user, value);
				break;

			}
		} catch (Exception e) {
			LOG.error(
					"Error occurred while populating the user with the properties values ",
					e);
		}
	}

}
