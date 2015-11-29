/**
 * 
 */
package com.lucas.dto.user;

import java.util.List;

/**
 * @author Prafull
 *
 */
public class MultiUserDTO {
	
	private List<String> userNameList;
	private UserFormFieldsDTO multiEditFields;
	
	public MultiUserDTO(){
		
	}
	
	public MultiUserDTO(List<String> userNameList, UserFormFieldsDTO multiEditFields){
		
		this.userNameList = userNameList;
		this.multiEditFields = multiEditFields;
	}
	public List<String> getUserNameList() {
		return userNameList;
	}
	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}
	public UserFormFieldsDTO getMultiEditFields() {
		return multiEditFields;
	}
	public void setMultiEditFields(UserFormFieldsDTO multiEditFields) {
		this.multiEditFields = multiEditFields;
	}

	
}
