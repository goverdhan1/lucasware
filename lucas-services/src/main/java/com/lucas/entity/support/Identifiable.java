/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.support;

public interface Identifiable<ID> {
	ID getId();
	
	void setId(ID id);
}
