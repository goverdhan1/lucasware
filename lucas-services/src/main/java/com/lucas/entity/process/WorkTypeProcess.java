/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.process;

import java.io.Serializable;

/**
 * This class is used only for demo purposes by the lucas-process project.
 * @author Pankaj Tandon
 *
 */
public class WorkTypeProcess implements Serializable {

	private static final long serialVersionUID = 8581294896739828096L;
	private String promptWorkType;
	private String responseWorkType;
	private boolean valid;
	
	public String getPromptWorkType() {
		return promptWorkType;
	}
	public void setPromptWorkType(String promptWorkType) {
		this.promptWorkType = promptWorkType;
	}
	public String getResponseWorkType() {
		return responseWorkType;
	}
	public void setResponseWorkType(String responseWorkType) {
		this.responseWorkType = responseWorkType;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
