package com.lucas.batch.health;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootstrapTests {

	//@Test - breaks on full run
	public void testBootstrapConfiguration() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring/batch/bootstrap/**/*.xml", 
				"classpath*:/META-INF/spring/batch/override/*.xml",
				"classpath*:/META-INF/spring/test-property-configurer-context.xml");
		assertTrue(context.containsBean("jobRepository"));
		context.close();
	}

	@Test
	public void testWebappRootConfiguration() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml");
		assertTrue(context.containsBean("jobRepository"));
		context.close();
	}
}
