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

package com.lucas.eai.support;

import java.io.File;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.filters.AbstractFileListFilter;
import org.springframework.stereotype.Component;

/**
 * The filter making sure that the completely downloaded file is picked by the
 * inbound adapter
 * 
 * @author Prafull
 * 
 */
@Component
public class ReadCompleteFileFilter extends AbstractFileListFilter<File> {

	private static Logger LOG = LoggerFactory
			.getLogger(ReadCompleteFileFilter.class);

	public ReadCompleteFileFilter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.integration.file.filters.AbstractFileListFilter#accept
	 * (java.lang.Object)
	 */
	@Override
	protected boolean accept(File file) {
		try {
			FileInputStream fStream = new FileInputStream(file);
			// If file is entirely OK the stream can be created
			LOG.debug("File is ready to be processed:" + file.getName());
			fStream.close();
			return true;
		} catch (Exception e) {
			LOG.debug("File is not ready to be processed yet! "
					+ file.getName());
			return false;
		}
	}

}
