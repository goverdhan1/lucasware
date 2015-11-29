/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.about;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.lucas.entity.about.About;

@Service("aboutService")
public class AboutServiceImpl implements AboutService {
		
	private static final Logger log = LoggerFactory.getLogger(AboutServiceImpl.class);
	
	//class variables injected from common.properties
	@Value("${buildNumberProperty}") 
	private String buildNumber;

	@Value("${buildTimestamp}") 
	private String buildTimestamp; 
		
	//getters
	@Override
	public About getAbout() {
		log.debug("***Returning new About() bean");
		return new About(buildNumber, buildTimestamp);
	}
}