/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.pick;

import java.util.List;
import java.util.Map;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.pick.Pickline;
import com.lucas.services.search.SearchAndSortCriteria;

public interface PicklineDAO extends GenericDAO<Pickline> {

	Map<String, List<Object>> getPicklinesByWave(
			SearchAndSortCriteria searchAndSortCriteria);

}
