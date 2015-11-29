package com.lucas.batch.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.jdbc.core.JdbcTemplate;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class JobExecutionTests {
	
	private static Logger logger = LoggerFactory.getLogger(JobExecutionTests.class);

	@Autowired
	private JobLauncher jobLauncher;

	private JobParameters jobParameters;

	@Autowired
	@Qualifier("logPersistJob")
	private Job logPersistJob;


	private JdbcTemplate jdbcTemplate;

	@Before
	public void beforeEach(){
		jobParameters = new JobParametersBuilder()
							.addString("fail", "false")
							.addString("input.file", "classpath:batchStuff/testLog000.log")
							.toJobParameters();
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void testSimpleProperties() throws Exception {
		logger.debug("Starting testSimpleProperties");
		assertNotNull(jobLauncher);
	}

	@Test
	public void testLaunchJob() throws Exception {
		logger.debug("testLaunchJob starting");
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		JobExecution jobExecution = jobLauncher.run(logPersistJob, jobParameters);
		assertNotNull(jobExecution);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		assertEquals(before + 1, after);
		logger.debug("testLaunchJob finished");
	}

	@Test
	public void testFailedJob() throws Exception {
		logger.debug("testFailedJob starting");
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		jobParameters = new JobParametersBuilder()
				.addString("fail", "true")
				.toJobParameters();
		logger.info("The following exception stack trace is expected and is as a result of a failing job");
		JobExecution jobExecution = jobLauncher.run(logPersistJob, jobParameters);
		assertNotNull(jobExecution);
		assertEquals(BatchStatus.FAILED, jobExecution.getStatus());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		assertEquals(before + 1, after);
		logger.debug("testFailedJob finished");
	}

	@Test
	public void testLaunchTwoJobs() throws Exception {
		logger.debug("testLaunchTwoJobs starting");
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		long count = 0;
		JobExecution jobExecution1 = jobLauncher.run(logPersistJob, new JobParametersBuilder(jobParameters).addLong("run.id", count++)
				.toJobParameters());
		JobExecution jobExecution2 = jobLauncher.run(logPersistJob, new JobParametersBuilder(jobParameters).addLong("run.id", count++)
				.toJobParameters());
		assertEquals(BatchStatus.COMPLETED, jobExecution1.getStatus());
		assertEquals(BatchStatus.COMPLETED, jobExecution2.getStatus());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "BATCH_STEP_EXECUTION");
		assertEquals(before + 2, after);
		
		logger.debug("testLaunchTwoJobs finished");
	}

}
