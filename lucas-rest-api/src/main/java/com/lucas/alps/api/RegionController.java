/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.Region;
import com.lucas.services.region.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author sparupati
 * 
 */
@Controller
@RequestMapping(value = "/region")
public class RegionController {

	private static final Logger logger = LoggerFactory.
			getLogger(RegionController.class);

	public RegionService regionService;

	@Inject
	public RegionController(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * Deletes a record in Region table
	 * @param regionId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{regionId}")
	@ResponseBody
	public ApiResponse<Region> deleteRegion(@PathVariable("regionId") Long regionId) {

		ApiResponse<Region> apiResponse = new ApiResponse<Region>();
		Region returnedRegion = regionService.findByRegionId(regionId);

		if ( null != returnedRegion ) {
			regionService.deleteRegion(returnedRegion);
			apiResponse.setMessage("Region with Id "+regionId+" deleted");
			logger.debug("Region with Id "+regionId+" deleted successfully");
		} else {
			apiResponse.setMessage("Region with Id " + regionId
					+ " not found to delete");
			logger.debug("Region with Id "+ regionId+ " not found");
		}

		return apiResponse;
	}

	/**
	 * Updates a record in Region table
	 * @param region
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	@ResponseBody
	public ApiResponse<Region> updateRegion(@RequestBody Region region) {
	
		ApiResponse<Region> apiResponse = new ApiResponse<Region>();
	
		if ( null != region ) {
			Long regionId = region.getRegionId(); 
			Region returnedRegion = regionService.findByRegionId(regionId);
	
			if ( null != returnedRegion ) {
				regionService.updateRegion(region);
				logger.debug("Region with Id "+regionId+" updated successfully");
				apiResponse.setMessage("Region with Id "+ regionId+" updated");
			} else {
				apiResponse.setStatus("failure");
				apiResponse.setMessage("Could not update Region with Id: "+regionId);
				logger.debug("Could not update Region with Id: "+ regionId);
			}
		} else {
			apiResponse.setStatus("failure");
			apiResponse.setMessage("Could not update the Region ");
		}
		
		return apiResponse;

	}

	/**
	 * Inserts new record into Region table
	 * @param region
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	@ResponseBody
	public ApiResponse<Region> insertRegion(@RequestBody Region region) {

		ApiResponse<Region> apiResponse = new ApiResponse<Region>();
		
		if ( null != region ) {
			Region returnedRegion = regionService.createRegion(region);
			apiResponse.setMessage("Region with Id "+returnedRegion.getRegionId()+" created");
			apiResponse.setData(returnedRegion);
		} else {
			apiResponse.setStatus("failure");
			apiResponse.setMessage("Could not create Region ");
		}
		
		return apiResponse;
	}

}