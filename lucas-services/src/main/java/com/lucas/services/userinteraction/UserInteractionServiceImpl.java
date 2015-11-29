/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.userinteraction;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class is used only for demo purposes by the lucas-process project.
 * @author Pankaj Tandon
 *
 */
public class UserInteractionServiceImpl implements UserInteractionService,
		JavaDelegate {
	
	private static Logger LOG = LoggerFactory.getLogger(UserInteractionServiceImpl.class);
	
	public FixedValue whatUserSays;
	
	public FixedValue whatJenniferShouldSay;
	
	public FixedValue voiceEnabled;

	public void execute(DelegateExecution execution) throws Exception {
		LOG.info("whatUserSays: " + whatUserSays );
		LOG.info("whatJenniferShouldSay: " + whatJenniferShouldSay );
		LOG.info("voiceEnabled?: " + voiceEnabled );
	}

	public FixedValue getWhatUserSays() {
		return whatUserSays;
	}

	public void setWhatUserSays(FixedValue whatUserSays) {
		this.whatUserSays = whatUserSays;
	}

	public FixedValue getWhatJenniferShouldSay() {
		return whatJenniferShouldSay;
	}

	public void setWhatJenniferShouldSay(FixedValue whatJenniferShouldSay) {
		this.whatJenniferShouldSay = whatJenniferShouldSay;
	}

	public FixedValue getVoiceEnabled() {
		return voiceEnabled;
	}

	public void setVoiceEnabled(FixedValue voiceEnabled) {
		this.voiceEnabled = voiceEnabled;
	}

}
