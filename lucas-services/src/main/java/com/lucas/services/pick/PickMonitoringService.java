/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.pick;

import org.apache.commons.collections.MultiMap;

import com.lucas.services.search.SearchAndSortCriteria;

public interface PickMonitoringService {

	MultiMap getPicklinesByWave(SearchAndSortCriteria searchAndSortCriteria);

}
