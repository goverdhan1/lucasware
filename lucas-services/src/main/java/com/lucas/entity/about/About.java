/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.entity.about;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class About {
	
    private static Logger log = LoggerFactory.getLogger(About.class);
    
    //class variables
    private String buildNumber;
    private String buildTimestamp;
	
    //constructor
    public About(String buildNumber, String buildTimestamp) {
        log.debug("***About - Constructor");
        this.buildNumber = buildNumber;
		this.buildTimestamp = buildTimestamp;
	}
    
    //dummy Constructor
    public About() {
        //required by AboutControllerFunctionalTest for parsing JSON
    }
    
    //getters
	public String getBuildNumber() {
		return this.buildNumber;
	}
	public String getBuildTimestamp() {
		return this.buildTimestamp;
	}
}
