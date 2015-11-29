/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.worktype;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.entity.process.LucasProcessBean;
import com.lucas.entity.support.Validatable;
import com.lucas.entity.user.User;

public class WorkTypeValidator implements JavaDelegate, Validatable<String, User> {

	
	private static Logger LOG = LoggerFactory.getLogger(WorkTypeValidator.class);
	public void execute(DelegateExecution execution) throws Exception {
		LOG.debug("Starting validation...");
		
		LucasProcessBean lpb = (LucasProcessBean)execution.getVariable("lpb");
		String responseWorkType = lpb.getWorkTypeProcess().getResponseWorkType();
		boolean boo = validate(responseWorkType, lpb.getUser());
		lpb.getWorkTypeProcess().setValid(boo);
	}

	
	@Override
	//Business logic here...
	public boolean validate(String workType, User... userList) {
		boolean boo = false;
		if (userList[0].getUserId().equals("jack")) {
			if (workType.equals("01")){
				boo = true;
			}
		} else {
			if (workType.equals("02")){
				boo = true;
			}
		}
		return boo;
	}


	@Override
	public boolean validate() {
		//Not implemented
		return false;
	}

}
