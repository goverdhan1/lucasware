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

package com.lucas.services.eai;

/**
 * This calss is having all the the constant properties being used in the project 
 * @author pkulshr
 *
 */
public class EaiFileConstants {

	public static final int CSV_DELIMITED_READER_MAX_RECORDS = 20;
	public static final int READER_THREADPOOL_EXECUTOR_SIZE = 5;
	public static final String USER_LOGON_VALUE = "Mobile device login";
	public static final String USER_LOGON_KEY = "eventName"; 
	public static final String KEY_USER_ID = "userId";
	public static final String PERIOD_DELIMITER = ".";
	public static final String COMMA_DELIMITER = ",";
	public static final String VALUE_NULL = "NULL";
	public static final String CLASS_NAME_SUPPORTED_LANGUAGE = "SupportedLanguage";
	public static final String CLASS_NAME_DATE = "Date";
	public static final String CLASS_NAME_SHIFT = "Shift";
	public static final String CLASS_NAME_BOOLEAN = "Boolean";
	public static final String EXPRESSION_SPLIT_PATTERN = "[\\(\\)\\,]";
	public static final String EXPRESSION_SUBSTRING_NAME = "substring";
	public static final String EXPRESSION_CONCAT_NAME = "concat";
	public static final String SUBSTRING_DOUBLE_DIGIT_PATTERN = "\\d[,]\\d";
	public static final String USER_LOGON_FILENAME_SPLIT_PATTERN = "[\\<\\>]";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String CSV_FILE_EXTENSION = ".csv";

}
