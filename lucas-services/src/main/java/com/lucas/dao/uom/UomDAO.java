package com.lucas.dao.uom;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.uom.Uom;

public interface UomDAO extends GenericDAO<Uom>{ 
	public Uom findUomByName(String uomName);
}
