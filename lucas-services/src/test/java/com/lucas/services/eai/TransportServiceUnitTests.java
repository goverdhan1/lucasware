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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lucas.dao.eai.TransportDao;
import com.lucas.entity.eai.transport.Transport;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceUnitTests {

	@Mock
	private TransportDao transportDao;

	@InjectMocks
	private TransportServiceImpl transportService;

	private Transport transport;

	private Long transportId = 1L;

	private String transportHost = "aHost";

	private String transportName = "aTransportName";

	public TransportServiceUnitTests() {
	}

	@Before
	public void setup() throws Exception {
		transport = new Transport(true, transportHost, null, null, null, null,
				null, transportName, null, null, null);
		transport.setTransportId(transportId);
	}

	@Test
	public void testGetTransportById() {
		when(transportDao.getTransportById(anyLong())).thenReturn(transport);
		transportService.getTransportById(transportId);
		verify(transportDao, times(1)).getTransportById(anyLong());
	}

	@Test
	public void testGetTransportByNullId() {
		when(transportDao.getTransportById(anyLong())).thenReturn(null);
		transportService.getTransportById(null);
		verify(transportDao, times(1)).getTransportById(anyLong());
	}

	@Test
	public void testGetTransportByName() {
		when(transportDao.getTransportByName(anyString()))
				.thenReturn(transport);
		transportService.getTransportByName(transportName);
		verify(transportDao, times(1)).getTransportByName(anyString());
	}

	@Test
	public void testGetTransportByNullName() {
		when(transportDao.getTransportByName(anyString())).thenReturn(null);
		transportService.getTransportByName(null);
		verify(transportDao, times(1)).getTransportByName(anyString());
	}

	@Test
	public void testGetAllTransports() throws ParseException {
		when(transportDao.getAllTransportNames()).thenReturn(new ArrayList<String>());
		transportService.getAllTransportNames();
		verify(transportDao, times(1)).getAllTransportNames();
	}
}
