/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.region;

import javax.inject.Inject;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lucas.entity.Region;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
/**
 * 
 * @author venkat
 * 
 */
@TransactionConfiguration(transactionManager="resourceTransactionManager")
public class RegionServiceFunctionalTests extends AbstractSpringFunctionalTests {
	
	@Inject
	private RegionService regionService;
	private Region region;
	
	@Before
	public void setup() {
		Region insertRegion = new Region();
		insertRegion.setRegionName("");
		region = regionService.createRegion(insertRegion);
	}
	
	@Test
	public void testFindRegionbyRegionId() {
		Region retrievedRegion = regionService.findByRegionId(region.getRegionId());
		Assert.assertNotNull(retrievedRegion);
		Assert.assertEquals(region.getRegionId(),retrievedRegion.getRegionId());
	}
	
	@After
	public void destroy() {
		regionService.deleteRegion(region.getRegionId());
	}
}
