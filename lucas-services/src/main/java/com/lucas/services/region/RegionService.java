/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.region;

import com.lucas.entity.Region;
/**
 * 
 * @author venkat
 * 
 */
public interface RegionService {
	
	Region findByRegionId(Long regionId);
	
	Region createRegion(Region region) ;
	
	void updateRegion(Region region);
	
	void deleteRegion(Region region);
	
	void deleteRegion(Long regionId);
	
}
