package com.lucas.services.location;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lucas.entity.location.Aisle;
import com.lucas.entity.location.Bay;
import com.lucas.entity.location.Level;
import com.lucas.entity.location.Location;
import com.lucas.entity.location.LocationPartType;
import com.lucas.entity.location.LocationScheme;
import com.lucas.entity.location.Slot;
import com.lucas.entity.location.Zone;
import com.lucas.exception.InvalidConfigurationException;
import com.lucas.exception.InvalidLocationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/services-bootstrap-context.xml"})
public class LocationServiceFunctionalTests {
	
	@Inject
	private LocationService locationService;
	
	@Test
	public void locationCreationBySpecifyingParts(){
		Location l = locationService.newLocation();
		Assert.assertTrue(l.getId() != null);
		Assert.assertTrue(l.getId().length() == 0);
		
		Location l1 = locationService.newLocation(new Bay("bay"), new Level("101"), new Aisle("ail"));
		Assert.assertTrue("ail-bay-101".equals(l1.getId()));

		Location l2 = locationService.newLocation(new Bay("bay"), new Level("12"), new Aisle("ail"));
		Assert.assertTrue("ail-bay-1".equals(l2.getId()));
		
		Location l3 = locationService.newLocation(new Zone("z12") , new Bay("bay"), new Level("12"), new Aisle("ail"));
		Assert.assertTrue("z12-ail-bay-1".equals(l3.getId()));
		
		Location l4 = locationService.newLocation(new Zone("z12") , new Slot("123"), new Bay("bay"), new Level("12"), new Aisle("ail"));
		Assert.assertTrue("z12-ail-bay-1-123".equals(l4.getId()));
	}
	
	@Test (expected = InvalidConfigurationException.class)
	public void testLocationSchemeWithNoSeparator(){
		List<LocationPartType> partTypeList = new ArrayList<LocationPartType>();
		new DefaultLocationScheme(partTypeList, null);
	}
	
	@Test (expected = InvalidConfigurationException.class)
	public void testLocationSchemeWithDupliacteLocationPartType(){
		List<LocationPartType> partTypeList = new ArrayList<LocationPartType>();
		partTypeList.add(LocationPartType.AISLE);
		partTypeList.add(LocationPartType.BAY);
		partTypeList.add(LocationPartType.AISLE);
		
		new DefaultLocationScheme(partTypeList, "-");
	}	
	
	@Test(expected = InvalidLocationException.class)
	public void testLocationCoverageBySpecifyingBadId(){
		Location l = locationService.newLocation("AABB");
	}
	
	@Test
	public void testLocationCoverageBySpecifyingGoodId(){
		String id = "ZZZ-AAA-BBB-LLL-SSS";
		Location l = locationService.newLocation(id);
		Assert.assertTrue(id.equals(l.toString()));
	}
	
	//TODO: How to change the locationScheme on the fly and test?
	//@Test
	public void testWithModifiedLocationScheme(){
		List<LocationPartType> partTypeList = new ArrayList<LocationPartType>();
		partTypeList.add(LocationPartType.AISLE);
		partTypeList.add(LocationPartType.BAY);
		partTypeList.add(LocationPartType.ZONE);
		
		new DefaultLocationScheme(partTypeList, "-");
	}
}
