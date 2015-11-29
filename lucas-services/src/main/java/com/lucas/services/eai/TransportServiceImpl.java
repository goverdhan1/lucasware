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
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.eai.TransportDao;
import com.lucas.entity.eai.transport.Transport;

/**
 * This class is the service layer implementation class to provide all the
 * business logic needs to be performed on the transport domain
 * 
 * 
 */
@Service("transportService")
public class TransportServiceImpl implements TransportService {

	private static Logger LOG = LoggerFactory
			.getLogger(TransportServiceImpl.class);

	private TransportDao transportDao;

	public TransportServiceImpl() {
	}

	@Inject
	public TransportServiceImpl(TransportDao transportDao) {
		this.transportDao = transportDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.TransportService#getTransportById(java.lang.String
	 * )
	 */
	@Transactional(readOnly = true)
	@Override
	public Transport getTransportById(Long transportId) {
		return transportDao.getTransportById(transportId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.TransportService#getTransportByName(java.lang.
	 * String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Transport getTransportByName(String transportName) {
		return transportDao.getTransportByName(transportName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.TransportService#getAllTransportNames()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getAllTransportNames() throws ParseException {
		return transportDao.getAllTransportNames();
	}

}
