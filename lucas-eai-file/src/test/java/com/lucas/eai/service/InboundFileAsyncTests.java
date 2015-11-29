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
package com.lucas.eai.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * File transformer test cases class.
 * @author Prafull
 *
 */
import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.event.outbound.UserOutboundInputService;
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.services.eai.EaiFileConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration("classpath*:eai-file-context.xml") })
@Ignore
public class InboundFileAsyncTests {

	@Inject
	QueueChannel userInboundMessageFromRabbit;

	@Inject
	QueueChannel filesIn;

	@Inject
	QueueChannel usersImportInboundLucasServiceChannel;

	@Inject
	QueueChannel userExpressionTransformationChannel;
	
	@Inject
	UserOutboundInputService userOutboundInputService;
	
	@Inject
	QueueChannel userOutboundInputChannel;

	private MappingPath transformationPath = new MappingPath();

	// private GenericMessageDTO messageDTO = new GenericMessageDTO();

	private Long destinationMessageId = 2L;

	private Long sourceMessageId = 1L;

	private Mapping eaiMapping;

	private Long fromTransformationId = null;

	private Long mappingPathId = 1L;

	private Integer mappingPathSeq = 1;

	private Long toTransformationId;

	private List<MessageDetailsDTO> messageDetails;

	private Message<GenericMessageDTO> message;

	private GenericMessageDTO payload = new GenericMessageDTO();

	private static final Logger LOG = LoggerFactory
			.getLogger(InboundFileAsyncTests.class);

	private static final String USER_DETAILS_FILE_PATH = "inboundFiles/users_details_20150515133020.csv";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		payload.setDestinationMessageId(destinationMessageId);
		payload.setSourceMessageId(sourceMessageId);

		transformationPath.setMapping(eaiMapping);
		transformationPath.setFromTransformation(null);
		transformationPath.setMappingPathId(mappingPathId);
		transformationPath.setMappingPathSeq(mappingPathSeq);
		transformationPath.setToTransformation(null);
		messageDetails = new ArrayList<MessageDetailsDTO>();
		payload.addTransformationPaths(transformationPath);
		payload.setMessageDetails(messageDetails);
		// payload = messageDTO;
		message = MessageBuilder.withPayload(payload).setHeader("foo", "bar")
				.build();
	}

	@Test
	public void testCallbackServiceForFileInoundAsync() throws IOException {
		/*Going forward when the entire flow would be executing we will complete this e2e test case. */
		  Resource expectedCSVFileResource = new ClassPathResource(
				USER_DETAILS_FILE_PATH);
		Message<File> message1 = MessageBuilder
				.withPayload(expectedCSVFileResource.getFile())
				.setHeader("testFile", "testFile").build();

		filesIn.send(message1);

		Message<GenericMessageDTO> message = (Message<GenericMessageDTO>) userExpressionTransformationChannel
				.receive(3000);

	}

	/**
	 * Test method for
	 * {@link com.lucas.eai.event.transformation.MessageTransformer#processMessageTransformation(org.springframework.integration.Message)}
	 * .
	 */
	@Test
	public void testProcessMessageTransformation() {
		/*
		 * Message<String> message1 = MessageBuilder
		 * .withPayload("Messgae from source transformer") .setHeader("foo",
		 * "bar").build();
		 * userExpressionTransformationChannel.send(message1);
		 */

		/*
		 * Message<String> message2 =
		 * MessageBuilder.fromMessage(message1).build();
		 * assertEquals("Messgae from source transformer",
		 * message2.getPayload()); assertEquals("bar",
		 * message2.getHeaders().get("foo"));
		 */
		Message<String> message1 = MessageBuilder
				.withPayload("Messgae from Rabbit").setHeader("foo", "bar")
				.build();

		userInboundMessageFromRabbit.send(message);

		Message<?> result = usersImportInboundLucasServiceChannel.receive(3000);

		/*
		 * assertEquals("Message details size is not equal to the expected value"
		 * ,((GenericMessageDTO)
		 * result.getPayload()).getMessageDetails().size()>0);
		 * assertEquals("Source message id  is not equal to the expected value",
		 * ((GenericMessageDTO) result.getPayload()).getSourceMessageId() ==
		 * sourceMessageId );
		 */
	}

		@Test
	public void testProcessOutboundEvent(){
		//Going forward we would write one separate e2e test case
		Map<String, String> payloadMap = new HashMap<String, String>();
		payloadMap.put(EaiFileConstants.KEY_USER_ID, Long.toString(3L));
		payloadMap.put(EaiFileConstants.USER_LOGON_KEY,
				EaiFileConstants.USER_LOGON_VALUE);
		Message<Map<String, String>> message = MessageBuilder.withPayload(
				payloadMap).build();
		userOutboundInputChannel.send(message);
	}
}
