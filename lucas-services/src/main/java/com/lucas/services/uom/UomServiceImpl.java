package com.lucas.services.uom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.uom.UomDAO;
import com.lucas.entity.ItemTuple;
import com.lucas.entity.uom.Uom;
import com.lucas.exception.UomAlreadyExistsException;

@Service
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class UomServiceImpl implements UomService {  

	private static Logger LOG = LoggerFactory.getLogger(UomServiceImpl.class);
	private UomDAO uomDAO;
	
	@Inject
	public UomServiceImpl(UomDAO uomDAO) {
		this.uomDAO = uomDAO;
	}
	
	@Transactional
	@Override
	public void createUom(Uom uom) {
		Uom existingUom = uomDAO.findUomByName(uom.getName());
		if (existingUom != null){
			throw new UomAlreadyExistsException("A Uom with the name already exits. Existing Uom Name: " + existingUom.getName() + ", New Uom Name: " +   uom.getName());
		}
		uomDAO.save(uom);
	}
	
	@Override 
	public Uom findUomByName(String uomName){
		return uomDAO.findUomByName(uomName);
	}

	@Transactional
	@Override
	public void deleteUom(Long uomId) {
		if (uomId != null){
			Uom retrievedUom = uomDAO.load(uomId); 
			if (retrievedUom != null){
				uomDAO.delete(retrievedUom);
			} else {
				LOG.debug(String.format("Did not delete any uom as no uom with uomId %s found.", uomId));
			}
		} else {
			LOG.debug("Did not delete any uom as null uomId was passed in.");
		}
	}
	
	
	/**
	 * Returns how many Uoms are present in the given ItemTuple.
	 * For example:
	 * ItemTuple beerCase has quantity = 3 and Uom = InnerCase
	 * Uom is MiniCase
	 * Will return how many miniCases are present in beerCase.
	 *  
	 * @param itemTuple
	 * @param uom
	 * @return
	 */
	@Override
	public double findFactor(ItemTuple itemTuple, Uom uom){
		Uom tupleUom = itemTuple.getUom();
		
		double d = Uom.findFactor(tupleUom, uom);
		return d * itemTuple.getQuantity();
	}
	

	@Override
	public List<Uom> getUomList() {
		return uomDAO.loadAll();
	}
	
	

}
