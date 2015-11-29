/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.benchmark;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.lucas.entity.process.LucasProcessBean;
import com.lucas.entity.process.WorkTypeProcess;
import com.lucas.entity.user.User;

/**
 * This class is written for a pilot with Apogee
 * It has purposely not been written as a test class because it is intended to be used on the CLI.
 * When we are packaging it, test dependencies are difficult to package in the final jar/s using the standard plugins.
 * Therefore it was implemented as a regular class.
 * 
 * 
 * @author PankajTandon
 * 
 */
public class Benchmark {

	private static Logger LOG = LoggerFactory
			.getLogger(Benchmark.class);

	public static void main(String[] args) {
		runBenchmark(args);
	}

	public static void runBenchmark(String[] args) {
		// APOGEE record time in main(), this does not include JRE initialization startup before main() method
		// is invoked by the JRE.
		long mainStartTime = System.currentTimeMillis();
		
		// APOGEE - add support for running a number of iterations that is a program argument
		// or 100 by default
		int iterations = 100;
		if (args.length > 0) {
			try {
				iterations = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		Benchmark.printEnv();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/processes-bootstrap-context.xml");

		ProcessEngine processEngine = applicationContext.getBean(
				"processEngine", ProcessEngine.class);

		// Get Activiti services
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		HistoryService historyService = processEngine.getHistoryService();

		// Deploy the process definition
		repositoryService.createDeployment()
				.addClasspathResource("diagrams/BenchmarkProcess.bpmn")
				.deploy();

		// APOGEE - do the workflow a number of iterations to see long run performance
		long totalTime = 0;
		long minTime = 100000;
		long maxTime = 0;
		for (int i = 0; i < iterations; i++) {
			
			TaskService taskService = processEngine.getTaskService();
	
			Map<String, Object> variableMap = new HashMap<String, Object>();
			LucasProcessBean lpb = Benchmark.bootstrapLucasProcessBean();
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey("benchmarkProcess", variableMap);
			
			// APOGEE add iteration id to log
			//LOG.debug("Starting process...");
			LOG.debug("Starting process " + i + "...");
			
			Task task = taskService.createTaskQuery()
					.executionId(processInstance.getProcessInstanceId())
					.singleResult();
	
			// Set a userId that will NOT be authenticated
	
			// APOGEE needed to modify the method names to work with older? version of 
			// com.lucas.entity.User in lucas-domain-1.0.0.CI-SNAPSHOT.jar that Apogee was provided?
			//lpb.getUser().setUserName("joe");
			lpb.getUser().setUserName("jack123");
			
			variableMap.put("lpb", lpb);
	
			// ...and pass to the task the variable map
			taskService.complete(task.getId(), variableMap);
			LOG.debug("Task: " + task.getName() + " completed! Task ExecutionId: "
					+ task.getExecutionId());
	
			// Ensure that the process looped back to the authentication step by
			// asserting that process has not finished
			HistoricProcessInstance historicProcessInstance = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.singleResult();
			Assert.isNull(historicProcessInstance.getEndTime());
			LOG.debug("Business process is still waiting to finish...");
	
			// Modify the userId to one that WILL be authenticated
			
			// APOGEE needed to modify the method names to work with older? version of 
			// com.lucas.entity.User in lucas-domain-1.0.0.CI-SNAPSHOT.jar that Apogee was provided?
			//lpb.getUser().setUserName("bob");
			lpb.getUser().setUserName("bob");
			
			variableMap.put("lpb", lpb);
			task = taskService.createTaskQuery()
					.executionId(processInstance.getProcessInstanceId())
					.singleResult();
			// Retry the authentication task.
			taskService.setVariables(task.getId(), variableMap);
			taskService.complete(task.getId());
			LOG.debug("Task: " + task.getName() + " completed!. Task ExecutionId: "
					+ task.getExecutionId());
	
			// Business process has finished because user was authenticated.
			Assert.isTrue(runtimeService.createProcessInstanceQuery().count()  == 0);
	
			// verify that the process is actually finished
			historicProcessInstance = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.singleResult();
			Assert.notNull(historicProcessInstance.getEndTime());
			
			Date startTime = historicProcessInstance.getStartTime();
			Date endTime = historicProcessInstance.getEndTime();;
			LOG.debug("Process instance end time: "
					+ endTime);
			LOG.debug("Process instance start time: "
					+ startTime);
			
			// APOGEE record this iteration
			//LOG.debug("Process time in ms: " + (endTime.getTime() - startTime.getTime()) );
			long time = endTime.getTime() - startTime.getTime();
			LOG.warn("Process time in ms: " + (time));
			totalTime += time;
			if (time > maxTime) {
				maxTime = time;
			}
			if (time < minTime) {
				minTime = time;
			}
		}
		
		// APOGEE print summary of iterations
		long mainEndTime = System.currentTimeMillis();
		
		LOG.error("Process time summary in ms:");
		LOG.error(" main() time     = " + (mainEndTime - mainStartTime));
		LOG.error(" iterations      = " + iterations);
		LOG.error(" cumulative time = " + totalTime);
		LOG.error(" average time    = " + (totalTime / iterations));
		LOG.error(" max time        = " + maxTime);
		LOG.error(" min time        = " + minTime);
	}

	private static LucasProcessBean bootstrapLucasProcessBean() {
		LucasProcessBean lpb = new LucasProcessBean();
		lpb.setId("1");
		lpb.setName("aProcessName");
		User user = new User();

		// APOGEE needed to modify the method names to work with older? version of 
		// com.lucas.entity.User in lucas-domain-1.0.0.CI-SNAPSHOT.jar that Apogee was provided?
		//user.setUserName("joeshmoe");
		//user.setHashedPassword("aPassword");
		user.setUserName("joeshmoe");
		user.setPlainTextPassword("aPassword");
		
		user.setNeedsAuthentication(true);
		lpb.setUser(user);

		WorkTypeProcess wtp = new WorkTypeProcess();
		lpb.setWorkTypeProcess(wtp);
		return lpb;

	}
	
	
	private static void printEnv(){
		LOG.debug("JAVA_CLASS_PATH: " + SystemUtils.JAVA_CLASS_PATH);
		LOG.debug("JAVA_CLASS_VERSION: " + SystemUtils.JAVA_CLASS_VERSION);
		LOG.debug("JAVA_COMPILER: " + SystemUtils.JAVA_COMPILER);
		LOG.debug("JAVA_HOME: " + SystemUtils.JAVA_HOME);
		LOG.debug("JAVA_RUNTIME_NAME: " + SystemUtils.JAVA_RUNTIME_NAME);
		LOG.debug("JAVA_RUNTIME_VERSION: " + SystemUtils.JAVA_RUNTIME_VERSION);
		LOG.debug("JAVA_SPECIFICATION_NAME: " + SystemUtils.JAVA_SPECIFICATION_NAME);
		LOG.debug("JAVA_VENDOR: " + SystemUtils.JAVA_VENDOR);
		LOG.debug("JAVA_VERSION: " + SystemUtils.JAVA_VERSION);
		LOG.debug("JAVA_VM_INFO: " + SystemUtils.JAVA_VM_INFO);
		LOG.debug("OS_ARCH: " + SystemUtils.OS_ARCH);
		LOG.debug("OS_NAME: " + SystemUtils.OS_NAME);
		LOG.debug("OS_VERSION: " + SystemUtils.OS_VERSION);
		
	}

}
