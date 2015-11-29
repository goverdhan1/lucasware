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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.services.eai.EaiFileConstants;

/**
 * This class is providing the super class for all the readers, reading an input
 * file.
 * 
 * @author Prafull
 * 
 */
public abstract class LucasFileReader {

	private static Logger LOG = LoggerFactory.getLogger(LucasFileReader.class);
	private final ReadParameters readParameters;
	private ExecutorService executor;

	public LucasFileReader(ReadParameters readParameters) {
		this.readParameters = readParameters;
		this.executor = Executors
				.newFixedThreadPool(EaiFileConstants.READER_THREADPOOL_EXECUTOR_SIZE);
	}

	/**
	 * The generic read method for reading a file line by line.
	 */
	public void read() {

		Path path = FileSystems.getDefault().getPath(
				readParameters.getFilePath());
		try (InputStream in = Files.newInputStream(path)) {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, StandardCharsets.UTF_8));

			String line = null;
			Boolean eof = false;
			Boolean firstRecord = true;
			List<Future<?>> futureList = new ArrayList<Future<?>>();
			while (!eof) {
				try {
					line = reader.readLine();
					if (line != null) {
						if (!firstRecord) {
							futureList.add(processData(line));
						}
					} else {
						eof = Boolean.TRUE;
					}
					firstRecord = false;
				} catch (IOException e) {
					LOG.error("Error reading the line from the file "
							+ readParameters.getFilePath(), e);
				}
			}

			// Check if all the tasks are complete
			for (Future<?> future : futureList) {
				
				future.get();
			}

			GenericMessageDTO messageDTO = readParameters
					.getGenericMessageDTO();
			// If all the tasks are complete send the final message
			if (!messageDTO.getMessageDetails().isEmpty()) {

				Message<GenericMessageDTO> message = MessageBuilder
						.withPayload(messageDTO)
						.setHeader("noOfCounts",
								messageDTO.getMessageDetails().size()).build();

				readParameters.getChannel().send(message);
			}

		} catch (IOException | InterruptedException | ExecutionException e) {
			LOG.error("Error occurred trying to process the file "
					+ readParameters.getFilePath(), e);
		}

	}
	
	/**
	 * This abstract method processes the data read by the reader 
	 * @param line
	 * @return
	 */
	abstract Future<?> processData(String line);

	public ReadParameters getReadParameters() {
		return readParameters;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

}
