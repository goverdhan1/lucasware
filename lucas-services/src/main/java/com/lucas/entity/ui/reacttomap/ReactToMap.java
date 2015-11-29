/**
 * 
 */
package com.lucas.entity.ui.reacttomap;

import com.lucas.entity.ui.dataurl.DataUrl;

/**
 * @author Prafull
 *
 */
public class ReactToMap {
	
	private DataUrl userName;
    private DataUrl groupName;
    private DataUrl hostLogin;
    private DataUrl productName;
    private DataUrl equipmentTypeName;
    private DataUrl equipmentManagerName;
    private DataUrl messageName;
	
	public ReactToMap(){
		
	}
	
	public ReactToMap(DataUrl userName){
		this.userName = userName;
	}
	
    public ReactToMap(DataUrl userName,  DataUrl groupName) {
        this.userName = userName;
        this.groupName = groupName;
    }

    public ReactToMap(DataUrl userName, DataUrl groupName, DataUrl hostLogin, DataUrl productName) {
        this.userName = userName;
        this.groupName = groupName;
        this.hostLogin = hostLogin;
        this.productName = productName;
    }

    public ReactToMap(DataUrl userName, DataUrl groupName, DataUrl hostLogin, DataUrl productName, DataUrl equipmentTypeName) {
        this.userName = userName;
        this.groupName = groupName;
        this.hostLogin = hostLogin;
        this.productName = productName;
        this.equipmentTypeName = equipmentTypeName;
    }
    
    public ReactToMap(DataUrl userName, DataUrl groupName, DataUrl hostLogin, DataUrl productName, DataUrl equipmentTypeName,DataUrl equipmentManager ) {
        this.userName = userName;
        this.groupName = groupName;
        this.hostLogin = hostLogin;
        this.productName = productName;
        this.equipmentTypeName = equipmentTypeName;
        this.equipmentManagerName = equipmentManager;
    }
    
    public ReactToMap(DataUrl userName, DataUrl groupName, DataUrl hostLogin, DataUrl productName,DataUrl equipmentTypeName,DataUrl equipmentManager, DataUrl messageName) {
        this.userName = userName;
        this.groupName = groupName;
        this.hostLogin = hostLogin;
        this.productName = productName;
        this.equipmentTypeName = equipmentTypeName;
        this.messageName = messageName;
    }



    public DataUrl getUserName() {
		return userName;
	}

	public void setUserName(DataUrl userName) {
		this.userName = userName;
	}

    public DataUrl getGroupName() {
        return groupName;
    }

    public void setGroupName(DataUrl groupName) {
        this.groupName = groupName;
    }

	public DataUrl getProductName() {
		return productName;
	}

	public void setProductName(DataUrl productName) {
		this.productName = productName;
	}

    public DataUrl getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(DataUrl equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }
}
