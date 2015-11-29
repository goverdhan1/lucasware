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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.lucas.eai.dto.FieldDetailsDTO;
import com.lucas.eai.dto.MessageDetailsDTO;
import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.eai.message.RecordType;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.services.eai.EaiFileConstants;

/**
 * This class provides the implementation for reading a CSV delimited flat file.
 * 
 * @author Prafull
 * 
 */
public class CSVDelimitedFileReader extends LucasFileReader {

	private static Logger LOG = LoggerFactory
			.getLogger(CSVDelimitedFileReader.class);

	public CSVDelimitedFileReader(ReadParameters readParameters) {
		super(readParameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.eai.reader.LucasFileReader#parseData(java.lang.String)
	 */
	@Override
	Future<?> processData(String line) {
		Future<?> future = getExecutor().submit(
				new CSVDelimitedFileWorker(line, getReadParameters()));
		return future;
	}

}

/**
 * This is a worker thread working on the CSV delimited line data, parsing and
 * sending to a channel
 * 
 * @author Prafull
 * 
 */
class CSVDelimitedFileWorker implements Runnable {

	private static Logger LOG = LoggerFactory
			.getLogger(CSVDelimitedFileWorker.class);

	private String line;
	private ReadParameters params;

	public CSVDelimitedFileWorker(String line, ReadParameters params) {
		this.line = line;
		this.params = params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		GenericMessageDTO messageDTO = params.getGenericMessageDTO();
		Set<MessageDefinition> definitions = params.getMessage()
				.getEaiMessageDefinitions();
		List<MessageDefinition> definitionsList = new ArrayList<MessageDefinition>();
		definitionsList.addAll(definitions);
		definitions = null;
		Collections.sort(definitionsList);
		Reader in = new StringReader(line);
		try (CSVParser parser = new CSVParser(in, CSVFormat.EXCEL);) {

			List<CSVRecord> recordsList = parser.getRecords();

			List<MessageDetailsDTO> messageDetails = new ArrayList<MessageDetailsDTO>(
					recordsList.size());

			for (CSVRecord csvRecord : (recordsList)) {
				MessageDetailsDTO messageDetailsDTO = new MessageDetailsDTO();
				messageDetailsDTO.setRecordType(RecordType.CSVDelimited);
				Map<String, FieldDetailsDTO> fieldsMap = new HashMap<String, FieldDetailsDTO>();
				for (MessageDefinition messageDefinition : definitionsList) {

					FieldDetailsDTO fieldDetails = new FieldDetailsDTO();

					fieldDetails.setMessageFieldName(messageDefinition
							.getMessageFieldName());

					fieldDetails.setMessageFieldType(messageDefinition
							.getMessageFieldType());
					fieldDetails.setMessageFieldValue(csvRecord
							.get(messageDefinition.getMessageFieldSeq()-1));
					fieldDetails.setMessageFieldSeq(messageDefinition.getMessageFieldSeq());
					fieldsMap.put(messageDefinition.getMessageFieldName(),
							fieldDetails);

				}
				messageDetailsDTO.setFields(fieldsMap);
				messageDetails.add(messageDetailsDTO);

			}
			
			synchronized (messageDTO) {
				// Add one at a time in a synchronized manner if the size
				// reaches to the max number of records put it on the channel
				messageDTO.setMessageDetails(messageDetails);
				if (messageDTO.getMessageDetails() != null
						&& !messageDTO.getMessageDetails().isEmpty()
						&& (messageDTO.getMessageDetails().size() == EaiFileConstants.CSV_DELIMITED_READER_MAX_RECORDS)) {
					GenericMessageDTO newaPyload = new GenericMessageDTO();
					newaPyload.setDestinationMessageId(messageDTO.getDestinationMessageId());
					newaPyload.setSourceMessageId(messageDTO.getSourceMessageId());
					newaPyload.setEventHandlerId(messageDTO.getEventHandlerId());
					newaPyload.setTransformationPaths(messageDTO.getTransformationPaths());
					newaPyload.setMessageDetails(messageDTO.getMessageDetails());
					Message<GenericMessageDTO> message = MessageBuilder
							.withPayload(newaPyload)
							.setHeader("noOfCounts",
									newaPyload.getMessageDetails().size())
							.build();
					params.getChannel().send(message);
					LOG.debug("**************** Sending no of messages to be transformed " + newaPyload.getMessageDetails().size());
					messageDTO.getMessageDetails().clear();
				
				}
			}

		} catch (IOException e) {
			LOG.error("Error occurred while reading the line " + line
					+ " from the file " + params.getFilePath(), e);
		}
	}

}
