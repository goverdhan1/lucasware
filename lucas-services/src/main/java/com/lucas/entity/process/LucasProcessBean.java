/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.process;

import com.lucas.entity.user.User;

/**
 * This class represents properties that are used by an instance of Lucas processes.
 * @author Tandon
 *
 */
public class LucasProcessBean implements java.io.Serializable{

	private static final long serialVersionUID = 849839567726235674L;
	
	private String id;
	private String name;
	private User user;
	private WorkTypeProcess workTypeProcess;
	private EquipmentCheckProcess equipmentCheckProcess;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public WorkTypeProcess getWorkTypeProcess() {
		return workTypeProcess;
	}
	public void setWorkTypeProcess(WorkTypeProcess workTypeProcess) {
		this.workTypeProcess = workTypeProcess;
	}
	public EquipmentCheckProcess getEquipmentCheckProcess() {
		return equipmentCheckProcess;
	}
	public void setEquipmentCheckProcess(EquipmentCheckProcess equipmentCheckProcess) {
		this.equipmentCheckProcess = equipmentCheckProcess;
	}

}
