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

import java.text.ParseException;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import com.lucas.entity.eai.transport.Transport;
import com.lucas.testsupport.AbstractSpringFunctionalTests;


@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class TransportServiceFunctionalTests extends
		AbstractSpringFunctionalTests {

	@Inject
	private TransportService transportService;

	private Long existingTransportId = 1L;

	private Long nonExistingTransportId = -1L;

	private String existingTransportName = "File inbound async";

	private String nonExistingTransportName = "aTransportName";

	private Transport transport;

	private static final Logger LOG = LoggerFactory
			.getLogger(TransportServiceFunctionalTests.class);

	@Before
	public void setUp() throws Exception {

		transport = new Transport(true, "Local directory", null, null, null,
				null, null, "File inbound async", null, null, null);
		transport.setTransportId(existingTransportId);
	}

	@Test
	public void testGetTransportByExistingId() {
		Transport retrievedTransport = transportService
				.getTransportById(existingTransportId);
		Assert.notNull(retrievedTransport,
				"Transport with an existing id is null");
		Assert.isTrue(
				retrievedTransport.getTransportName().equals(
						transport.getTransportName()),
				"Transport name for the existing transport is not the same as expected");
	}

	@Test
	public void testGetTransportByNonExistingId() {
		Transport retrievedTransport = transportService
				.getTransportById(nonExistingTransportId);
		Assert.isNull(retrievedTransport,
				"Transport with an non existing id is not null");
	}

	@Test
	public void testGetTransportByExistingName() {

		Transport retrievedTransport = transportService
				.getTransportByName(existingTransportName);
		Assert.notNull(retrievedTransport,
				"Transport with an existing name is null");
		Assert.isTrue(
				retrievedTransport.getTransportId() == 
						transport.getTransportId(),
				"Transport id for the existing transport is not the same as expected");
	
	}

	@Test
	public void testGetTransportByNonExistingName() {
		Transport retrievedTransport = transportService
				.getTransportByName(nonExistingTransportName);
		Assert.isNull(retrievedTransport,
				"Transport with an non existing name is not null");
	}
	
	@Test
	public void testGetAllTransports() throws ParseException {
		Assert.notNull(transportService.getAllTransportNames(), "getAllTransports return null");
		Assert.isTrue(transportService.getAllTransportNames().size() == 2, "Total numbers of transports is not as expected");
	}
}