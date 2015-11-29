/**
 * Copyright (c) Lucas Systems LLC
 */
package org.com.lucas.designer.service;

import org.activiti.designer.integration.servicetask.AbstractCustomServiceTask;
import org.activiti.designer.integration.servicetask.PropertyType;
import org.activiti.designer.integration.servicetask.annotation.Help;
import org.activiti.designer.integration.servicetask.annotation.Property;
import org.activiti.designer.integration.servicetask.annotation.PropertyItems;
import org.activiti.designer.integration.servicetask.annotation.Runtime;

@Runtime(delegationClass = "com.lucas.services.userinteraction.UserInteractionServiceImpl")
@Help(displayHelpShort = "Processes a user interaction", displayHelpLong = "Processes a user interaction...")

public class UserInteractionServiceTask extends AbstractCustomServiceTask {

	private static final String HELP_JENNIFER_PROMPT = "Say a command, any command...";
	private static final String HELP_USER_ANSWER = "I command peace and quiet";
	private static final String HELP_VOICE_OPTION_ANSWER = "Is this user interaction voice enabled?";
	
	private static final String JEN_PROMPT_HI = "hi";
	private static final String JEN_VALUE_HI = "hi";
	private static final String JEN_PROMPT_HELLO = "hello";
	private static final String JEN_VALUE_HELLO = "hello";	
	
	@Property(type = PropertyType.COMBOBOX_CHOICE, displayName = "Jennifer Says", required = true, defaultValue = "Your wish is my command!")
	@Help(displayHelpShort = "Specify what Jennifer should say", displayHelpLong = HELP_JENNIFER_PROMPT)
	@PropertyItems({ JEN_PROMPT_HI, JEN_VALUE_HI, JEN_PROMPT_HELLO, JEN_VALUE_HELLO})
	private String whatJenniferShouldSay;
	
	@Property(type = PropertyType.TEXT, displayName = "User Says", required = true, defaultValue = "Get me some milk!")
	@Help(displayHelpShort = "Specify a command", displayHelpLong = HELP_USER_ANSWER)
	private String whatUserSays;
	
	@Property(type = PropertyType.BOOLEAN_CHOICE, displayName = "Voice?", required = true, defaultValue = "true")
	@Help(displayHelpShort = "Specify if interaction is voice enabled", displayHelpLong = HELP_VOICE_OPTION_ANSWER)
	private boolean voiceEnabled;	
	
	@Override
	public String contributeToPaletteDrawer() {
	  return "Lucas Corporation";
	}
	  
	@Override
	public String getName() {
		return "User Interaction Service Task";
	}
	
	@Override
	public String getSmallIconPath() {
	  return "icons/coins.png";
	}
	
}
