/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.dao.region;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.Region;
/**
 * 
 * @author venkat
 * 
 */
public interface RegionDAO extends GenericDAO<Region> {
	
	Region findByRegionId(Long regionId);
	
}