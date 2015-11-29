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
package com.lucas.eai.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucas.entity.eai.message.MessageFormat;

/**
 * This class is a factory class providing a suitable reader for reading a file
 * 
 * @author Prafull
 * 
 */
@Component
public class LucasFileReaderFactory {

	private static Logger LOG = LoggerFactory
			.getLogger(LucasFileReaderFactory.class);

	public LucasFileReaderFactory() {
	}

	/**
	 * This method returns a suitable reader based upon the message format and
	 * delimiter type, assigning the read parameters to the reader.
	 * 
	 * @param messageFormat
	 * @param delimiterType
	 * @param readParameters
	 * @return
	 */
	public static LucasFileReader getFileReader(MessageFormat messageFormat,
			String delimiterType, ReadParameters readParameters) {

		LucasFileReader lucasFileReader = null;

		switch (messageFormat) {
		case Delimited: {

			switch (delimiterType) {
			case ",":
				lucasFileReader = new CSVDelimitedFileReader(readParameters);
				break;

			default:
				LOG.error("No reader found for the format "
						+ messageFormat.getName() + " and delimiter type "
						+ delimiterType + " mentioned in the message");
				break;
			}

		}
			break;
		case LucasPredefined: {
		}
			break;
		default:
			LOG.error("No reader found for the format "
					+ messageFormat.getName() + " mentioned in the message");
			break;
		}

		return lucasFileReader;
	}

}
