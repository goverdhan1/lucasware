/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.pick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.pick.Pickline;
import com.lucas.services.search.SearchAndSortCriteria;

@Repository
public class PicklineDAOImpl extends ResourceDAO<Pickline> implements
		PicklineDAO {

	private static final String KEY_COMPLETE = "Completed";
	private static final String KEY_TOTAL = "Total";

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<Object>> getPicklinesByWave(
			SearchAndSortCriteria searchAndSortCriteria) {

		Map<String, List<Object>> picklinesByWave = new HashMap<String, List<Object>>();
		final Session session = getSession();

		Criteria completedPicklinesByWaveCriteria = this.buildCriteria(session, searchAndSortCriteria);
		Criteria totalPicklinesByWaveCriteria = this.buildCriteria(session, searchAndSortCriteria);

		List<Object> completedPicklinesByWave = getPicklinesByCompletedAndByWave(
				completedPicklinesByWaveCriteria, Boolean.TRUE);
		List<Object> totalPicklinesByWave = getPicklinesByCompletedAndByWave(
				totalPicklinesByWaveCriteria, Boolean.FALSE);
		picklinesByWave.put(KEY_COMPLETE, completedPicklinesByWave);
		picklinesByWave.put(KEY_TOTAL, totalPicklinesByWave);

		return picklinesByWave;
	}

	private List<Object> getPicklinesByCompletedAndByWave(Criteria criteria,
			Boolean completed) {
		if (completed) {
			criteria.add(Restrictions.eq("completed", completed));
		}
		criteria.setProjection(Projections.projectionList()

		.add(Projections.groupProperty("wave"), "wave")
				.add(Projections.rowCount(), "picklineCountByWave"));

		return criteria.list();
	}

}
