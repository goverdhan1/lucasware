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

/**
 * This class is providing the super class for all the writers, writing an
 * output file.
 * 
 * @author Prafull
 * 
 */
public abstract class LucasFileWriter {

	/**
	 * This method triggers the write functionality uniformly for all the use
	 * cases.
	 */
	public void write() {
		writeData();
	}

	/**
	 * This abstract method is implemented by all the concrete classes to write
	 * the data according to the use case they are dealing with
	 */
	abstract void writeData();

}
