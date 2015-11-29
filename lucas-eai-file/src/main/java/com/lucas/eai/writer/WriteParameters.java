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

import com.lucas.eai.message.GenericMessageDTO;

/**
 * This class contains all the parameters needed to write to a file by a writer.
 * 
 * @author Prafull
 * 
 */
public class WriteParameters {

	private String dirPath;
	private String fileNamePattern;
	private String fileType;
	private GenericMessageDTO genericMessageDTO;

	public String getDirPath() {
		return dirPath;
	}

	public String getFileNamePattern() {
		return fileNamePattern;
	}

	public String getFileType() {
		return fileType;
	}

	public GenericMessageDTO getGenericMessageDTO() {
		return genericMessageDTO;
	}

	/**
	 * This class is a builder for building the write parameters.
	 * 
	 * @author Prafull
	 * 
	 */
	public static class Builder {
		// Required parameters
		private String dirPath;
		private String fileNamePattern;
		private String fileType;
		private GenericMessageDTO genericMessageDTO;

		public Builder() {
		}

		// Optional parameters for future
		// private int <varName> = 0;
		public Builder dirPath(String val) {
			dirPath = val;
			return this;
		}

		public Builder fileNamePattern(String val) {
			fileNamePattern = val;
			return this;
		}

		public Builder fileType(String val) {
			fileType = val;
			return this;
		}

		public Builder genericMessage(GenericMessageDTO val) {
			genericMessageDTO = val;
			return this;
		}

		public WriteParameters build() {
			return new WriteParameters(this);
		}
	}

	private WriteParameters(Builder builder) {
		dirPath = builder.dirPath;
		fileNamePattern = builder.fileNamePattern;
		fileType = builder.fileType;
		genericMessageDTO = builder.genericMessageDTO;
	}

}
