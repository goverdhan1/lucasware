package com.lucas.entity.location;

import java.util.Iterator;

public interface LocationScheme<T> {
	String getSeparator();
	Iterator<T> getIterator();
	int getNumbeOfParts();
	String toString();
	
}
