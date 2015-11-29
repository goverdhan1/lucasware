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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.eai.dto.FieldDetailsDTO;
import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.services.eai.EaiFileConstants;

import edu.emory.mathcs.backport.java.util.Collections;
/**
 * This class provides the implementation for writing a CSV delimited flat file.
 * 
 * @author Prafull
 * 
 */
public class CSVDelimitedFileWriter extends LucasFileWriter {

	private WriteParameters writeParameters;

	private static Logger LOG = LoggerFactory
			.getLogger(CSVDelimitedFileWriter.class);

	public CSVDelimitedFileWriter(WriteParameters writeParameters) {
		this.writeParameters = writeParameters;
	}

	/* (non-Javadoc)
	 * @see com.lucas.eai.writer.LucasFileWriter#writeData()
	 */
	@Override
	void writeData() {

		String dirPath = writeParameters.getDirPath();
		String fileNamePattern = writeParameters.getFileNamePattern();
		GenericMessageDTO dto = writeParameters.getGenericMessageDTO();
		String[] fileNameArr = fileNamePattern
				.split(EaiFileConstants.USER_LOGON_FILENAME_SPLIT_PATTERN);

		StringBuffer data = new StringBuffer();
		
		StringBuffer header = new StringBuffer();

		MessageDetailsDTO detailsDTO = dto.getMessageDetails().get(0);

		Map<String, FieldDetailsDTO> fieldMap = detailsDTO.getFields();

		Collection<FieldDetailsDTO> values = fieldMap.values();

		List<FieldDetailsDTO> valuesList = new ArrayList<FieldDetailsDTO>(
				values);

		Collections.sort(valuesList);
		
		//Prepare the header and the data
		for (FieldDetailsDTO entry : valuesList) {
			
			header.append(entry.getMessageFieldName()).append(EaiFileConstants.COMMA_DELIMITER);
			data.append(entry.getMessageFieldValue()).append(EaiFileConstants.COMMA_DELIMITER);
		}
		
		//Create the full file path
		StringBuffer filePath = new StringBuffer(dirPath)
				.append(File.separator)
				.append(fieldMap.get(fileNameArr[1]).getMessageFieldValue())
				.append(fileNameArr[2]).append(EaiFileConstants.CSV_FILE_EXTENSION);

		File outBoundFile = new File(filePath.toString());
		
		//Write the header and data to the file
		try {
			FileUtils.writeStringToFile(outBoundFile, header.append(EaiFileConstants.LINE_SEPARATOR).toString());
			FileUtils.writeStringToFile(outBoundFile, data.toString(), true);
		} catch (IOException e) {
			LOG.error(
					"Unable to write a file for the exported values for the user "
							+ fieldMap.get("username").getMessageFieldValue(),
					e);
		}
		

	}

}
