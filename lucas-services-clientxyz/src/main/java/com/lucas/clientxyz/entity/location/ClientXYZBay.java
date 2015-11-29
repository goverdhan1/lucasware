package com.lucas.clientxyz.entity.location;

import com.lucas.entity.location.Bay;
import com.lucas.exception.InvalidLocationException;
import com.mysql.jdbc.StringUtils;

public class ClientXYZBay extends Bay {
	
	public ClientXYZBay(String id){ 
		this.setId(id);
	}

	public String getLocationPart() {
		String s = null;
		if (!StringUtils.isNullOrEmpty(this.getId())) {
			if (this.getId().length() != 4){
				throw new InvalidLocationException("Bay length must be exactly 4 characters");
			} else {
				s = this.getId().substring(2,4); //show the last two characters on the location
			}
		}
		return s;
	}
}
