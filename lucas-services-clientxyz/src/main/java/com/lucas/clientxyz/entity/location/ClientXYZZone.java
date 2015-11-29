package com.lucas.clientxyz.entity.location;

import com.lucas.entity.location.Zone;
import com.mysql.jdbc.StringUtils;

public class ClientXYZZone extends Zone{

	
	public ClientXYZZone(String id) {
		this.setId(id);
	}

	public String getLocationPart() {
		String s = null;
		if (!StringUtils.isNullOrEmpty(this.getId())) {
			if (this.getId().length() >= 5){
				s = this.getId().substring(0,5); //Show all characters
			} else if (this.getId().length() < 5){
				s = this.getId().substring(1,3); //Show second and third character
			}
		}
		return s;
	}

}
