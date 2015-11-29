/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.profile;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Profiler {
	
	private static Logger LOG = LoggerFactory.getLogger(Profiler.class);

    @Pointcut("execution(* com.lucas.services.benchmark.*.*(..))")
    public void profileSecurityMethods() { }
    
//    @Pointcut("execution(* org.activiti.engine.*..*(..))")
//    public void profileActivitiMethods() { }
    
    @Around("profileSecurityMethods() ")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
            long start = System.currentTimeMillis();
            LOG.debug("Going to call the method: " + pjp.getSignature().toShortString());
            Object output = pjp.proceed();
            long elapsedTime = System.currentTimeMillis() - start; 
            LOG.debug("Method: " + pjp.getSignature().toShortString() + " execution time: " + elapsedTime + " milliseconds.");
            return output;
    }
}
