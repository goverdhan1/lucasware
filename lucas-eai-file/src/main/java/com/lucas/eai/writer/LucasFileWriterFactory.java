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
package com.lucas.eai.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.entity.eai.message.MessageFormat;

/**
 * This class is a factory class providing a suitable writer for writing to a
 * file
 * 
 * @author Prafull
 * 
 */
public class LucasFileWriterFactory {

	public LucasFileWriterFactory() {
	}

	private static Logger LOG = LoggerFactory
			.getLogger(LucasFileWriterFactory.class);

	/**
	 * This method returns a suitable writer based upon the message format and
	 * delimiter type, assigning the write parameters to the writer.
	 * 
	 * @param messageFormat
	 * @param delimiterType
	 * @param writeParameters
	 * @return
	 */
	public static LucasFileWriter getFileWriter(MessageFormat messageFormat,
			String delimiterType, WriteParameters writeParameters) {

		LucasFileWriter lucasFileWriter = null;

		switch (messageFormat) {
		case Delimited: {

			switch (delimiterType) {
			case ",":
				lucasFileWriter = new CSVDelimitedFileWriter(writeParameters);
				break;

			default:
				LOG.error("No writer found for the format "
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
			LOG.error("No writer found for the format "
					+ messageFormat.getName() + " mentioned in the message");
			break;
		}

		return lucasFileWriter;
	}
}
