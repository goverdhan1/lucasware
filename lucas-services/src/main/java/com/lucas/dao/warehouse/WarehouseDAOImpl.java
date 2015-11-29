/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.warehouse;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.Region;
import com.lucas.entity.Warehouse;

@Repository
public class WarehouseDAOImpl extends ResourceDAO<Warehouse> implements WarehouseDAO {
	
	private static Logger logger = LoggerFactory.getLogger(WarehouseDAOImpl.class);

	@Override
	public List<Warehouse> getWarehouseList(Long regionId) {

		List<Warehouse> warehouseList = new ArrayList<Warehouse>(0);
		Session session = getSession();
	
		Criteria criteria = session.createCriteria(Region.class);
		criteria.add(Restrictions.eq("regionId", regionId));
		 
		Region region = (Region) criteria.uniqueResult();

		if ( null != region ) {			
			warehouseList = region.getWarehouseList();
			logger.debug(" WarehouseDAOImpl [ findByRegionId ] - Warehouse List size {} ", warehouseList.size());
		} else {
			logger.info(" WarehouseDAOImpl [ findByRegionId ] - Region is null ");
		}
		
		return warehouseList;
	}
	
	@Override
	public List<Warehouse> getWarehouseList(Long regionId, Long warehouseId) {

		List<Warehouse> warehouseList = new ArrayList<Warehouse>(0);
		Session session = getSession();
	
		Criteria criteria = session.createCriteria(Region.class);
		criteria.add(Restrictions.eq("regionId", regionId))
		 .createCriteria("warehouseList").add( Restrictions.eq("warehouseId", warehouseId) );
		 
		Region region = (Region) criteria.uniqueResult();

		if ( null != region ) {			
			warehouseList = region.getWarehouseList();
			logger.debug(" WarehouseDAOImpl [ findByRegionId ] - Warehouse List size {} ", warehouseList.size());
		} else {
			logger.info(" WarehouseDAOImpl [ findByRegionId ] - Region is null ");
		}
		
		return warehouseList;
	}

}