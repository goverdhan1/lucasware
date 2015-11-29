package com.lucas.entity.support;

import java.util.List;
import java.text.ParseException;

public interface MultiEditable<T> {
	
	List<String> getMultiEditableFieldsList() throws ParseException;

}
