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

import org.springframework.integration.channel.QueueChannel;

import com.lucas.eai.message.GenericMessageDTO;
import com.lucas.entity.eai.message.Message;

/**
 * This class contains all the parameters needed to read a file by a reader.
 * 
 * @author Prafull
 * 
 */
public class ReadParameters {

	/**
	 * 
	 */

	private String filePath;
	private QueueChannel channel;
	private Message message;
	private GenericMessageDTO genericMessageDTO;

	/**
	 * This class is a builder for building the read parameters.
	 * 
	 * @author Prafull
	 * 
	 */
	public static class Builder {
		// Required parameters
		private String filePath;
		private QueueChannel channel;
		private Message message;
		private GenericMessageDTO genericMessageDTO;

		public Builder() {
		}

		// Optional parameters for future
		// private int <varName> = 0;
		public Builder filePath(String val) {
			filePath = val;
			return this;
		}

		public Builder channel(QueueChannel val) {
			channel = val;
			return this;
		}

		public Builder message(Message val) {
			message = val;
			return this;
		}

		public Builder genericMessage(GenericMessageDTO val) {
			genericMessageDTO = val;
			return this;
		}

		public ReadParameters build() {
			return new ReadParameters(this);
		}
	}

	private ReadParameters(Builder builder) {
		filePath = builder.filePath;
		channel = builder.channel;
		message = builder.message;
		genericMessageDTO = builder.genericMessageDTO;
	}

	public String getFilePath() {
		return filePath;
	}

	public QueueChannel getChannel() {
		return channel;
	}

	public Message getMessage() {
		return message;
	}

	public GenericMessageDTO getGenericMessageDTO() {
		return genericMessageDTO;
	}

}
