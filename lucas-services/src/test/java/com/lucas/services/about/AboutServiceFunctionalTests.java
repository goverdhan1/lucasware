/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.about;

import static org.junit.Assert.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import com.lucas.entity.about.About;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

public class AboutServiceFunctionalTests extends AbstractSpringFunctionalTests {

	private static final Logger log = LoggerFactory.getLogger(AboutServiceFunctionalTests.class);
		
	@Value("${buildNumberProperty}")
	private String bldNumber;
	@Value("${buildTimestamp}")
	private String bldTimestamp;
	
	private About about;
		
	@Inject
	private AboutService aboutService;
	
	@Before
	public void setup() {
		log.debug("***AboutServiceFunctionalTests - Test Setup");
		about = aboutService.getAbout();
	}
		
	@Test
	public void testGetBuildNumber() {
		log.debug("***AboutServiceFunctionalTests - Executing Test: 1.0");
	    log.debug("***AboutServiceFunctionalTests - Will match Build Number against: " + bldNumber);

		String buildNumber = about.getBuildNumber();
		log.debug("***AboutServiceFunctionalTests - About Build Number [" + buildNumber + "]");
		
		// Assert Build Number is not null, length > 0 and matches
		// build number from common.properties
		assertNotNull(buildNumber);
		assertTrue(buildNumber.length() > 0);
		assertEquals(buildNumber, bldNumber);
	}
	
	@Test
	public void testGetBuildTimestamp() {
		log.debug("***AboutServiceUnitTest - Running Test: 2.0");
        log.debug("***AboutServiceFunctionalTests - Will match Build Timestamp against: " + bldTimestamp);
		
		String buildTimestamp = about.getBuildTimestamp();
		log.debug("***AboutServiceUnitTest - About Build Timestamp [" + buildTimestamp + "]");
		
        // Assert Build Number is not null, length > 0 and matches
        // build number from common.properties
		assertNotNull(buildTimestamp);
		assertTrue(buildTimestamp.length() > 0);
		assertEquals(buildTimestamp, bldTimestamp);
	}
}