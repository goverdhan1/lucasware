/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.entity.support;

public interface Validatable<T,S> {
	String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&‚Äô*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	boolean validate();
	boolean validate(T validatable, S... supplementList);
}
