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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.MessageFormat;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.eai.transformation.TransformationDefinition;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

/**
 * @author Naveen
 * 
 */
@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class MessageServiceFunctionalTests extends
		AbstractSpringFunctionalTests {

	private static final int NUMBER_ONE = 1;

	private static final long messageId = NUMBER_ONE;

	private static final String messageName = "Lucas user import lucas";

	private static final String lucasPredefinedService = "usersImportInboundLucasService";

	private static final String messageDescription = "User's import inbound lucas message";

	private static final int usageInEvents = NUMBER_ONE;

	private static final boolean lucasPredefined = false;

	private static final String messageFieldDelimitedCharacter = null;

	private List<String> messageNames = new ArrayList<String>();

	// Message Definition variables

	private static final long messageFieldId = 1L;

	private static final String messageFieldDescription = "User's username";

	private int messageFieldEnds;

	private static final int messageFieldLength = 100;

	private static final String messageFieldName = "emailAddress";

	private int messageFieldRepeat;

	private String messageFieldSeparator;

	private static final int messageFieldSeq = 1;

	private int messageFieldStarts;

	private static final String messageFieldType = "Text";

	private int messageSegmentId;

	private static final long transformationDefinitionId = 1L;

	private static final long nonExistingTransformationDefinitionId = 1000L;
	
	private static final String MESSAGE_NAME = "Lucas user import lucas";

	private static final String MESSAGE_DESCRIPTION = "User's import inbound lucas message";
	
	private Message eaiMessage;
	private List<Message> eaiMessageList;
	private MessageDefinition messageDefinition;

	@Inject
	MessageService messageService;

	private static String MESSAGE_NAME_DOES_NOT_EXISTS = "messageDoesNotExists";

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageServiceFunctionalTests.class);

	private static final Long segmentId = 1L;

	@Before
	public void setUp() throws Exception {

		eaiMessage = new Message();
		eaiMessage.setMessageId(messageId);
		eaiMessage.setMessageName(messageName);
		eaiMessage.setLucasPredefined(lucasPredefined);
		eaiMessage.setLucasPredefinedService(lucasPredefinedService);
		eaiMessage.setMessageFormat(MessageFormat.Delimited);
		eaiMessage.setMessageDescription(messageDescription);
		eaiMessage.setUsageInEvents(usageInEvents);
		eaiMessage.setMessageFieldDelimitedCharacter(",");

		Segments eaiSegments = new Segments();
		MessageDefinition eaiMessageDefinition = new MessageDefinition();
		/*
		 * eaiMessageDefinition.setEaiMessage(eaiMessage);
		 * eaiMessageDefinition.setMessageFieldId(1L);
		 * eaiMessageDefinition.setEaiSegments(eaiSegments);
		 */

		eaiMessageDefinition.setEaiMessage(eaiMessage);
		eaiMessageDefinition.setMessageFieldId(messageFieldId);
		eaiMessageDefinition.setEaiSegments(eaiSegments);
		eaiMessageDefinition.setMessageFieldEnds(messageFieldEnds);
		eaiMessageDefinition
				.setMessageFieldDescription(messageFieldDescription);
		eaiMessageDefinition.setMessageFieldLength(messageFieldLength);
		eaiMessageDefinition.setMessageFieldName(messageFieldName);
		eaiMessageDefinition.setMessageFieldRepeat(messageFieldRepeat);
		eaiMessageDefinition.setMessageFieldSeparator(messageFieldSeparator);
		eaiMessageDefinition.setMessageFieldSeq(messageFieldSeq);
		eaiMessageDefinition.setMessageFieldStarts(messageFieldStarts);
		eaiMessageDefinition.setMessageFieldType(messageFieldType);
		eaiMessageDefinition.setMessageFieldType(messageFieldType);

		Set<TransformationDefinition> transformationDefinition = new HashSet<TransformationDefinition>();
		eaiMessageDefinition
				.setTransformationDefinition(transformationDefinition);

		SegmentDefinition eaiSegmentDefinition = new SegmentDefinition();
		// eaiSegmentDefinition.setEaiSegments(eaiSegments);
	}

	@Test
	public void testIfMessageDoesNotExists() throws JsonParseException,
			JsonMappingException, IOException {
		Message eaiMessageTest = messageService
				.getMessageByName(MESSAGE_NAME_DOES_NOT_EXISTS);
		org.junit.Assert.assertNull(eaiMessageTest);

	}

	@Test
	public void testIfMessageExists() throws JsonParseException,
			JsonMappingException, IOException {
		Message eaiMessageTest = messageService.getMessageByName(messageName);
		String expectedMessageName = "Lucas user import lucas";
		Assert.isTrue(
				eaiMessageTest.getMessageName().equals(expectedMessageName),
				"Lucas user import lucas was expected but got "
						+ eaiMessageTest.getMessageName());
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIfMessageNameExistsByMessageNames()
			throws JsonParseException, JsonMappingException, IOException {

		final List<String> messageNames = new ArrayList<String>();

		messageNames.add("Lucas user import lucas");
		messageNames.add("Lucas user export lucas");

		final List<Message> eaiMessageListTest = this.messageService
				.getMessageByNames(messageNames);
		Assert.notNull(eaiMessageListTest, "Message list is empty");
		Assert.isTrue(eaiMessageListTest.size() >= 1);

	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIfMessageNameExistsByMessageId() throws JsonParseException,
			JsonMappingException, IOException {
		final Message eaiMessageTest = this.messageService
				.getMessageById(NUMBER_ONE);
		Assert.notNull(eaiMessageTest, "Message list is empty");
		Assert.isTrue(eaiMessageTest.getMessageName().equals(messageName));

	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIFMessageDefinitionExistsForTransformationDefinition()
			throws JsonParseException, JsonMappingException, IOException {

		final MessageDefinition messageDefinition = this.messageService
				.getMessageDefinitionByTransformationDefinitionId(transformationDefinitionId);
		Assert.notNull(messageDefinition, "Message definition does not exists");
		Assert.isTrue(messageDefinition.getMessageFieldName().equals(
				messageFieldName));
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testIFMessageDefinitionDoesNotExistsForTransformationDefinition()
			throws JsonParseException, JsonMappingException, IOException {

		final MessageDefinition messageDefinition = this.messageService
				.getMessageDefinitionByTransformationDefinitionId(nonExistingTransformationDefinitionId);
		Assert.isNull(messageDefinition, "Message definition does not exists");
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testGetSegmentListBySearchAndSortCriteria()
			throws ParseException, IOException, Exception {

		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		final Map<String, Object> searchMap = new HashMap<String, Object>();

		final LinkedHashMap<String, Object> segmentNameMap = new LinkedHashMap<String, Object>();
		segmentNameMap.put("filterType", FilterType.ALPHANUMERIC);
		segmentNameMap.put("values", Arrays.asList("Control"));
		searchMap.put("segmentName", segmentNameMap);

		/*
		 * // segment length numeric Range final LinkedHashMap<String, Object>
		 * lengthMap = new LinkedHashMap<String, Object>();
		 * lengthMap.put("filterType", FilterType.NUMERIC);
		 * lengthMap.put("values", Arrays.asList("0","300"));
		 * searchMap.put("segmentLength", lengthMap);
		 * 
		 * // segmentId numeric Range final LinkedHashMap<String, Object>
		 * segmentIdMap = new LinkedHashMap<String, Object>();
		 * segmentIdMap.put("filterType", FilterType.NUMERIC);
		 * segmentIdMap.put("values", Arrays.asList("0", "300"));
		 * searchMap.put("segmentId", segmentIdMap);
		 */

		final LinkedHashMap<String, Object> segmentDescriptionMap = new LinkedHashMap<String, Object>();
		segmentDescriptionMap.put("filterType", FilterType.ALPHANUMERIC);
		segmentDescriptionMap.put("values",
				Arrays.asList("Control records for transfer order"));
		searchMap.put("segmentDescription", segmentDescriptionMap);

		final LinkedHashMap<String, Object> lengthMap = new LinkedHashMap<String, Object>();
		lengthMap.put("filterType", FilterType.NUMERIC);
		lengthMap.put("values", Arrays.asList("0", "300"));
		searchMap.put("segmentLength", lengthMap);

		searchAndSortCriteria.setSearchMap(searchMap);
		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("segmentName", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(1000);
		final List<Segments> segmentList = this.messageService
				.getSegmentListBySearchAndSortCriteria(searchAndSortCriteria);
		Assert.notNull(segmentList);
		Assert.isTrue(segmentList.size() >= 1);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSegment() {
		final Segments segment = this.getSegmentObject();

		Throwable e = null;
		try {
			this.messageService.createSegment(segment);
		} catch (Throwable ex) {
			e = ex;
		}

		final Segments persistedSegment = this.messageService
				.getSegment(segment.getSegmentName());

		Assert.notNull(persistedSegment, "Segment is Not Persisted ");
		Assert.isTrue(
				persistedSegment.getSegmentName().equals(
						segment.getSegmentName()),
				"Segment Name isn't Persisted ");
		Assert.isTrue(
				persistedSegment.getSegmentDescription().equals(
						segment.getSegmentDescription()),
				"Segment Description isn't Persisted ");
	}

	/**
	 * @return
	 */
	private Segments getSegmentObject() {

		final Segments segment = new Segments();
		segment.setSegmentLength(100);
		segment.setSegmentName("Test4");
		segment.setSegmentDescription("Test record for segment persist");

		SegmentDefinition segmentDefinition = new SegmentDefinition();

		segmentDefinition.setSegmentFieldName("Test_definition");
		segmentDefinition
				.setSegmentFieldDescription("Test Record for segment definition persist");
		segmentDefinition.setSegmentFieldSeq(1);
		segmentDefinition.setSegmentFieldType("String");
		segmentDefinition.setSegmentFieldStart(1);
		segmentDefinition.setSegmentFieldEnd(50);

		Set<SegmentDefinition> eaiSegmentDefinition = new HashSet<SegmentDefinition>();
		eaiSegmentDefinition.add(segmentDefinition);

		segment.setEaiSegmentDefinitions(eaiSegmentDefinition);

		MessageDefinition messageDefinition = new MessageDefinition();

		// messageDefinition.setMessageFieldLength(100);
		messageDefinition.setMessageFieldId(1L);

		Set<MessageDefinition> eaiMessageDefinition = new HashSet<MessageDefinition>();
		eaiMessageDefinition.add(messageDefinition);
		segment.setEaiMessageDefinitions(eaiMessageDefinition);

		return segment;
	}

	/**
	 * Method to test saving an existing segment
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveAnExistingSegment() {
		final Segments segment = this.getSegmentObject();
		Throwable e = null;
		try {
			this.messageService.createSegment(segment);
		} catch (Throwable ex) {
			e = ex;
		}

		final Segments persistedSegment = this.messageService
				.getSegment(segment.getSegmentName());

		Assert.notNull(persistedSegment, "Segment is Not Persisted ");
		Assert.isTrue(
				persistedSegment.getSegmentName().equals(
						segment.getSegmentName()),
				"Segment Name isn't Persisted ");
		Assert.isTrue(
				persistedSegment.getSegmentDescription().equals(
						segment.getSegmentDescription()),
				"Segment Description isn't Persisted ");
	}

	/**
	 * Method to test segment definitions for segment
	 */
	@Transactional
	@Test
	public void testSegmentWithSegmentDefinitions() {
		final Segments segment = this.messageService.getSegment("Control");
		junit.framework.Assert.assertNotNull("Segment Definitons list is null",
				segment);
	}
	   
	   /**
	    * Test method to check getHydratedMessage(Long messageId)
	    * @throws JsonParseException
	    * @throws JsonMappingException
	    * @throws IOException
	    */
	   
	    @Test
		@Transactional
	    public void testGetHydratedMessage() throws JsonParseException, JsonMappingException, IOException {
	        final Message eaiMessageTest = this.messageService.getHydratedMessage(1L);
	        Set<MessageDefinition> msgDefSet = eaiMessageTest.getEaiMessageDefinitions();
	        Assert.notNull(eaiMessageTest, "Message list is empty");
	        Assert.isTrue(eaiMessageTest.getMessageName().equals(messageName));
	        Assert.notNull(msgDefSet, "MessageDefinition set is empty");
	        Assert.isTrue(msgDefSet.size() == 17);
	    }
	    
		   /**
		    * Test method to check getHydratedMessageByName(String messageName)
		    * @throws JsonParseException
		    * @throws JsonMappingException
		    * @throws IOException
		    */
		   
		    @Test
		    @Transactional
		    public void testGetHydratedMessageByName() throws JsonParseException, JsonMappingException, IOException {
		        final Message eaiMessageTest = this.messageService.getHydratedMessageByName(MESSAGE_NAME);
		        Set<MessageDefinition> msgDefSet = eaiMessageTest.getEaiMessageDefinitions();
		        Assert.notNull(eaiMessageTest, "Message Set is empty");
		        Assert.isTrue(eaiMessageTest.getMessageName().equals(MESSAGE_NAME));
		        Assert.isTrue(eaiMessageTest.getMessageDescription().equals(MESSAGE_DESCRIPTION));
		        Assert.notNull(msgDefSet, "MessageDefinition set is empty");
		        Assert.isTrue(msgDefSet.size() == 17);
		    }



	/**
	 * Test method to check getHydratedMessage(Long messageId)
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */

	@Test
	public void testgetHydratedMessage() throws JsonParseException,
			JsonMappingException, IOException {
		final Message eaiMessageTest = this.messageService
				.getHydratedMessage(1L);
		Set<MessageDefinition> msgDefSet = eaiMessageTest
				.getEaiMessageDefinitions();
		Assert.notNull(eaiMessageTest, "Message list is empty");
		Assert.isTrue(eaiMessageTest.getMessageName().equals(messageName));
		Assert.notNull(msgDefSet, "MessageDefinition set is empty");
	}

	/**
	 * Method to test segment delete by segment id
	 */
	@Transactional
	@Test
	@Rollback(true)
	public void testDeleteSegment() {
		final boolean isDeleted = this.messageService.deleteSegment(segmentId);
		junit.framework.Assert.assertTrue("Deletion of Segment Failed",
				isDeleted);
	}
	
	/**
	 * Method to get all available message names
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetAllMessageNames() throws ParseException {
		Assert.notNull(messageService.getAllMessageNames(), "getAllMessageNames return null");
		Assert.isTrue(messageService.getAllMessageNames().size() == 4, "Total numbers of messages is not as expected");
	}
}
