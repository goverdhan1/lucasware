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
package com.lucas.services.eai;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.eai.MessageDAO;
import com.lucas.dao.eai.MessageDefinitionDAO;
import com.lucas.dao.eai.SegmentDAO;
import com.lucas.dao.eai.SegmentDefinitionDAO;
import com.lucas.dao.ui.WidgetDAO;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.MessageFormat;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.services.security.SecurityService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(value = { StandardPBEStringEncryptor.class, StringUtils.class })
public class MessageServiceUnitTests {

	private static final int NUMBER_ONE = 1;

	private static final long messageId = 1L;

	private static final String messageName = "Lucas user import lucas";

	private static final String lucasPredefinedService = "usersImportInboundLucasService";

	private static final String messageDescription = "User's import inbound lucas message";

	private static final String messageFormat = "LucasPredefined";

	private static final int usageInEvents = NUMBER_ONE;

	private static final boolean lucasPredefined = false;

	private static final String messageFieldDelimitedCharacter = ",";

	private List<String> messageNames = new ArrayList<String>();

	// message definition variables

	private static final long messageFieldId = 1L;

	private static final String messageFieldDescription = "User's username";

	private int messageFieldEnds;

	private static final int messageFieldLength = 100;

	private static final String messageFieldName = "userName";

	private int messageFieldRepeat;

	private String messageFieldSeparator;

	private static final int messageFieldSeq = 1;

	private int messageFieldStarts;

	private static final String messageFieldType = "Text";

	private int messageSegmentId;

	private static final long transformationDefinitionId = 1L;

	private static final long nonExistingTransformationDefinitionId = 1000L;

	// private Set eaiMessageDefinitions = new HashSet(0);

	@Mock
	MessageDAO messageDao;

	@Mock
	MessageDefinitionDAO messageDefinitionDao;

	@Mock
	SegmentDAO segmentDAO;

	@Mock
	SecurityService securityService;

	@Mock
	WidgetDAO widgetDAO;
	
	@Mock
	SegmentDefinitionDAO segmentDefinitionDAO;

	@InjectMocks
	MessageServiceImpl messageService;

	private Message eaiMessage;
	private List<Message> eaiMessageList;
	private MessageDefinition messageDefinition;

	@Before
	public void setUp() throws Exception {
		messageService = Mockito.spy(new MessageServiceImpl(messageDao,
				messageDefinitionDao, widgetDAO, securityService, segmentDAO, segmentDefinitionDAO));
		eaiMessage = new Message();
		eaiMessage.setMessageId(messageId);
		eaiMessage.setMessageName(messageName);
		eaiMessage.setLucasPredefined(lucasPredefined);
		eaiMessage.setLucasPredefinedService(lucasPredefinedService);
		eaiMessage.setMessageFormat(MessageFormat.Delimited);
		eaiMessage.setMessageDescription(messageDescription);
		eaiMessage.setUsageInEvents(usageInEvents);
		eaiMessage
				.setMessageFieldDelimitedCharacter(messageFieldDelimitedCharacter);

		Segments eaiSegments = new Segments();
		messageDefinition = new MessageDefinition();
		messageDefinition.setEaiMessage(eaiMessage);
		messageDefinition.setMessageFieldId(messageFieldId);
		messageDefinition.setEaiSegments(eaiSegments);
		messageDefinition.setMessageFieldEnds(messageFieldEnds);
		messageDefinition.setMessageFieldDescription(messageFieldDescription);
		messageDefinition.setMessageFieldLength(messageFieldLength);
		messageDefinition.setMessageFieldName(messageFieldName);
		messageDefinition.setMessageFieldRepeat(messageFieldRepeat);
		messageDefinition.setMessageFieldSeparator(messageFieldSeparator);
		messageDefinition.setMessageFieldSeq(messageFieldSeq);
		messageDefinition.setMessageFieldStarts(messageFieldStarts);
		messageDefinition.setMessageFieldType(messageFieldType);
		messageDefinition.setMessageFieldType(messageFieldType);

		Set<TransformationDefinition> transformationDefinition = new HashSet<TransformationDefinition>();
		messageDefinition.setTransformationDefinition(transformationDefinition);

	}

	@Test
	public void testGetMessageById() throws JsonParseException,
			JsonMappingException, IOException {
		when(messageDao.getMessageById(anyLong())).thenReturn(eaiMessage);
		Message eaiMessageTest = messageService.getMessageById(messageId);
		Assert.notNull(eaiMessageTest);
		Assert.isTrue(eaiMessageTest.getMessageId() == eaiMessage
				.getMessageId());
		verify(messageDao, times(NUMBER_ONE)).getMessageById(1L);
	}

	@Test
	public void testGetMessageByName() throws JsonParseException,
			JsonMappingException, IOException {
		when(messageDao.getMessageByName(anyString())).thenReturn(eaiMessage);
		Message eaiMessageTest = messageService.getMessageByName(messageName);
		Assert.notNull(eaiMessageTest);
		Assert.isTrue(eaiMessageTest.getMessageId() == eaiMessage
				.getMessageId());
		verify(messageDao, times(NUMBER_ONE)).getMessageByName(messageName);
	}

	@Test
	public void testGetMessageByNames() throws JsonParseException,
			JsonMappingException, IOException {
		messageNames.add("Lucas user import lucas");

		when(messageDao.getMessageByNames(anyList()))
				.thenReturn(eaiMessageList);
		messageService.getMessageByNames(messageNames);
		verify(messageDao, times(NUMBER_ONE)).getMessageByNames(messageNames);
	}

	@Test
	public void testGetMessageDefinitionForTransformationDefinition()
			throws JsonParseException, JsonMappingException, IOException {
		when(
				messageDefinitionDao
						.getMessageDefinitionForTransformationDefinition(anyLong()))
				.thenReturn(messageDefinition);
		MessageDefinition messageDefinitionTest = messageService
				.getMessageDefinitionByTransformationDefinitionId(transformationDefinitionId);
		verify(messageDefinitionDao, times(NUMBER_ONE))
				.getMessageDefinitionForTransformationDefinition(
						transformationDefinitionId);
		Assert.notNull(messageDefinitionTest);
		Assert.isTrue(
				messageDefinitionTest.getMessageFieldName().equals(
						messageDefinition.getMessageFieldName()),
				"Message feild name for the existing transformation Definition is not the same as expected");

	}

	@Test
	public void testGetMessageDefinitionForTransformationDefinitionFail()
			throws JsonParseException, JsonMappingException, IOException {
		MessageDefinition messageDefinitionTest = messageService
				.getMessageDefinitionByTransformationDefinitionId(nonExistingTransformationDefinitionId);
		Assert.isNull(messageDefinitionTest);
	}
	
	@Test
	public void testGetAllMessageNames() throws ParseException {
		when(messageDao.getAllMessageNames()).thenReturn(new ArrayList<String>());
		messageService.getAllMessageNames();
		verify(messageDao, times(1)).getAllMessageNames();
	}
}
