package com.lucas.services.uom;

import java.util.List;

import com.lucas.entity.ItemTuple;
import com.lucas.entity.uom.Uom;

public interface UomService {
	
	void createUom(Uom uom);
	
	void deleteUom(Long uomId);
	
	List<Uom> getUomList();
	
	Uom findUomByName(String uomName);

	double findFactor(ItemTuple itemTuple, Uom uom);
}
