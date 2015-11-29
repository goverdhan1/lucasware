package com.lucas.alps.api;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.lucas.entity.Region;
import com.lucas.entity.Warehouse;
import com.lucas.services.region.RegionService;
import com.lucas.services.warehouse.WarehouseService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class WarehouseHandlerFunctionalTests {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseHandlerFunctionalTests.class);
	private static final String regionName = "Alaska"; 
	private static final String warehouseName = "Best Buy";
	
	@Inject
	private WebApplicationContext wac;	

	@Inject
	private WarehouseService warehouseService;
	
	@Inject
	private RegionService regionService;
	
	private Region region;
	
	private Warehouse warehouse;
	
	private List<Warehouse> expectedWarehouseList;
	
	private MockMvc mockMvc; 
	
	@Before
	public void setup() {
		//Build  the MVC context
		this.mockMvc = webAppContextSetup(this.wac).build();
		region = new Region();
		region.setRegionName(regionName);
		regionService.createRegion(region);
		
		warehouse = new Warehouse();
		warehouse.setWarehouseName(warehouseName);
		warehouse.setRegionId(region.getRegionId());
		warehouseService.createWarehouse(warehouse);
		
		expectedWarehouseList = new ArrayList<Warehouse>();
		expectedWarehouseList.add(warehouse);
	}
	
	@Test
	public void testWhenValidRegionIDthenReturn200HttpStatus() throws Exception {
		
		ResultActions resultActions = this.mockMvc.perform(get("/regions/" +region.getRegionId()+ "/warehouses/" + warehouse.getWarehouseId())
				.accept(MediaType.APPLICATION_JSON));
		logger.debug("controller returns: " + resultActions.andReturn().getResponse().getContentAsString()) ;
		resultActions.andExpect(status().isOk())
		.andExpect(content().string(containsString(String.valueOf(warehouse.getWarehouseId()))))
		.andExpect(content().string(containsString(String.valueOf(warehouse.getWarehouseName()))));
	}	
	
	//@Test
	public void testWhenNoRegionIdIsSetThenThrow404Error() throws Exception {
		
		ResultActions resultActions = this.mockMvc.perform(get("/warehouse/region/")
				.accept(MediaType.APPLICATION_JSON));
		resultActions.andExpect(status().is(404));
	}	
	
	@After
	public void destroy() {
		warehouseService.deleteWarehouse(warehouse);
		regionService.deleteRegion(region);
	}
}