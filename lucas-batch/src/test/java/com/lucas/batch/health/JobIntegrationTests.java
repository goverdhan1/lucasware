
package com.lucas.batch.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class JobIntegrationTests {

	@Autowired
	private ListableJobLocator jobLocator;

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(jobLocator);
		assertEquals("[logPersistJob]", new TreeSet<String>(jobLocator.getJobNames()).toString());
	}

}
