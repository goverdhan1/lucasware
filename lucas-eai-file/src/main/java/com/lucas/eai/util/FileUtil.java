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

package com.lucas.eai.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class has the implementation of all the utility operations needed to be
 * performed on the files.
 * 
 * @author pkulshr
 * 
 */
public class FileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * The method copies the input file to the out put directory location
	 * 
	 * @param file
	 * @param outputDir
	 */
	public static void copyFile(File file, String outputDir) {
		try {

			File newFile = new File(outputDir.trim() + file.getName());
			if (newFile.exists()) {
				newFile.delete();
			}

			if (file.renameTo(new File(outputDir.trim() + file.getName()))) {
				LOG.info("File " + file.getPath()
						+ " is moved successfully! to the Dir: " + outputDir);
			} else {
				LOG.error("File " + file.getPath()
						+ " is failed to move! to the Dir: " + outputDir);
			}

		} catch (Exception e) {
			LOG.error(
					"Exception occrred while movinig the file" + file.getPath(),
					e);
		}
	}
	
	/**
	 * The method matches the string value passed as name to the regex pattern
	 * 
	 * @param name
	 * @param regex
	 * @return true/false based upon the match
	 */
	public static Boolean matches(String name, String regex) {

		if (name == null || regex == null) {
			return false;
		}

		return name.matches(regex);
	}

}
