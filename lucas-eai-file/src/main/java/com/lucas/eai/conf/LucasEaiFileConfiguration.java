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

package com.lucas.eai.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lucas.eai.JobExecutor;

/**
 * This class provides the comprehensive and programmatic way to register object
 * with the spring configuration
 * 
 * @author Prafull
 * 
 */
@Configuration
public class LucasEaiFileConfiguration {

	/*
	 * Keeping the following example for the future use
	 * 
	 * @Value("${batch.jdbc.driver}") private String driverClassName;
	 */

	/**
	 * The method creates a singleton object of the job executor and registers
	 * it to the spring application context
	 * 
	 * @return
	 */
	@Bean
	public JobExecutor jobExecutor() {
		return new JobExecutor();
	}

}
