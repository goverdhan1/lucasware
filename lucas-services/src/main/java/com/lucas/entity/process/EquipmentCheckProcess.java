/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.process;

/**
 * This class is used only for demo purposes by the lucas-process project.
 * @author Pankaj Tandon
 *
 */
public class EquipmentCheckProcess implements java.io.Serializable {
	
	private static final long serialVersionUID = 1485065638183054276L;
	private boolean bailOnFirstIncorrectAnswer;

	public boolean isBailOnFirstIncorrectAnswer() {
		return bailOnFirstIncorrectAnswer;
	}

	public void setBailOnFirstIncorrectAnswer(boolean bailOnFirstIncorrectAnswer) {
		this.bailOnFirstIncorrectAnswer = bailOnFirstIncorrectAnswer;
	}

}
