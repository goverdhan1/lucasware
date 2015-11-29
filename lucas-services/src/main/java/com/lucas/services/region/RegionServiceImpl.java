/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.region;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lucas.dao.region.RegionDAO;
import com.lucas.entity.Region;
import com.lucas.exception.LucasRuntimeException;

/**
 * 
 * @author venkat
 * 
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {

	private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);
	
	private final RegionDAO regionDAO;

	@Inject
	public RegionServiceImpl(RegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}
	
	@Transactional(readOnly = true)
	@Override
	@PreAuthorize("hasRole('view-report-productivity')")
	public Region findByRegionId(Long regionId) {
		Region retrievedRegion = null;
		if ( null != regionId ) {
			retrievedRegion = regionDAO.findByRegionId(regionId);

			if ( null != retrievedRegion ) {
				logger.debug("Region with Id "+ retrievedRegion.getRegionId() + " found");
			} else {
				logger.debug("Did not create any region as invalid regionId was passed in.");
			}
		}else{
			logger.debug("Did not create any region as invalid regionId was passed in.");
		}
		return 	retrievedRegion;
	}
	
	@Transactional
	@Override
	@PreAuthorize("hasRole('create-assignment')")
	public Region createRegion(Region region) {
		Region retrievedRegion = null;
		if( null != region ) {
			retrievedRegion = regionDAO.save(region);
			logger.debug("Region with Id "+ retrievedRegion.getRegionId() + " created");
		}else{
			logger.debug("Did not create any region as null region was passed in.");
		}
		return retrievedRegion;
	}

	@Transactional
	@Override
	@PreAuthorize("hasRole('configure-location')")
	public void updateRegion(Region region)throws LucasRuntimeException {
		Region retrievedRegion = null;
		if ( null != region ) {
			retrievedRegion = regionDAO.load(region.getRegionId());
			if ( null != retrievedRegion) {
				regionDAO.save(region);
			} else {
				throw new LucasRuntimeException(
						String.format(
								"No region exists with regionname %s. Cannot update region!",
								region.getRegionName()));
			}
		} else {
			logger.debug("Did not create any region as null region was passed in.");
		}
	}

	@Transactional
	@Override
	public void deleteRegion(Region region) {
		if ( null != region ) {
			deleteRegion(region.getRegionId()); 
		}else {
			logger.debug("Did not delete any region as null region was passed in.");
		}
	}
	
	@Transactional
	@Override
	public void deleteRegion(Long regionId) {
		if ( null != regionId ) {
			Region retrievedRegion = regionDAO.load(regionId);
			if ( null != retrievedRegion ) {
				regionDAO.delete(retrievedRegion);
			} else {
				logger.debug(String.format("Did not delete any region as no region with regionId %s found.", regionId));
			}
		}else {
			logger.debug("Did not delete any region as null regionId was passed in.");
		}
		
	}
}