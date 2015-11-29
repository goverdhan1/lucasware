/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.entity;

import com.lucas.entity.uom.Uom;

/**
 * This class represents an item quantity and it's {@link Uom}.
 * @author Pankaj Tandon
 *
 */
public class ItemTuple {
	
	private Long quantity;
	private Uom uom;
	
	public ItemTuple(Long quantity, Uom uom){
		this.quantity = quantity;
		this.uom = uom;
	}
	
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Uom getUom() {
		return uom;
	}
	public void setUom(Uom uom) {
		this.uom = uom;
	}
	

}
