/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.dao.region;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.Region;
/**
 * 
 * @author venkat
 * 
 */
@Repository
public class RegionDAOImpl extends ResourceDAO<Region> implements RegionDAO {

	@Override
	public Region findByRegionId(Long regionId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Region.class);

		criteria.add(Restrictions.eq("regionId", regionId));

		Region region = (Region) criteria.uniqueResult();
		
		return region;
	}
}