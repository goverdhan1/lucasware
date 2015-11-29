package com.lucas.services.location;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lucas.clientxyz.entity.location.ClientXYZBay;
import com.lucas.clientxyz.entity.location.ClientXYZZone;
import com.lucas.entity.location.Aisle;
import com.lucas.entity.location.Level;
import com.lucas.entity.location.Location;
import com.lucas.entity.location.Slot;
import com.lucas.exception.InvalidLocationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/services-bootstrap-context.xml", 
		"/META-INF/spring/clientxyz-services-context.xml"}) //override with custom config
public class LocationServiceClientXYZFunctionalTests {
	
	@Inject
	private LocationService locationService;
	
	@Test
	public void locationCreation(){
		Location l1 = locationService.newLocation(new ClientXYZBay("bay3"), new Slot("101"), new Aisle("ail"));
		Assert.assertTrue("y3*ail*101".equals(l1.getId()));

		Location l2 = locationService.newLocation(new ClientXYZBay("bay3"), new Slot("101"), new Aisle("ail"), new ClientXYZZone("Z123"));
		Assert.assertTrue("y3*ail*101*12".equals(l2.getId()));
		
		Location l3 = locationService.newLocation(new ClientXYZBay("bay3"), new Slot("101"), new Aisle("ail"), new ClientXYZZone("Z1234"));
		Assert.assertTrue("y3*ail*101*Z1234".equals(l3.getId()));
	}
	
	@Test (expected = InvalidLocationException.class)
	public void locationCreationWithBadLength(){
		Location l = locationService.newLocation(new ClientXYZBay("BAY"), new Level("12"), new Aisle("ail"));
		l.getId();
	}
}
