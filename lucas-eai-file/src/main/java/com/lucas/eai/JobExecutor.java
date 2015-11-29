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

package com.lucas.eai;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is an entry point to the eai file processing project
 * 
 * @author pkulshr
 * 
 */
public class JobExecutor {

	/**
	 * The main method starting the execution
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath*:eai-file-context.xml");
		JobExecutor jobExecuter = (JobExecutor) ctx.getBean("jobExecutor");
	}

}
